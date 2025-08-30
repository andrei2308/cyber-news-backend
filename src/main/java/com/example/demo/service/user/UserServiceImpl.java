package com.example.demo.service.user;

import com.example.demo.configuration.security.JwtUtil;
import com.example.demo.domain.token.entity.Token;
import com.example.demo.domain.token.entity.dto.TokenRefreshDto;
import com.example.demo.domain.token.enums.TokenType;
import com.example.demo.domain.token.repository.TokenRepository;
import com.example.demo.domain.user.dtos.LoginVM;
import com.example.demo.domain.user.dtos.RegisterVM;
import com.example.demo.domain.user.dtos.UserDto;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.service.userFollow.UserFollowService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final UserFollowService userFollowService;
    private final ModelMapper modelMapper;

    public UserServiceImpl(JwtUtil jwtUtil, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, TokenRepository tokenRepository, UserFollowService userFollowService, ModelMapper modelMapper) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.userFollowService = userFollowService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void register(RegisterVM registerVM) {
        if (userRepository.existsByEmail(registerVM.email)) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        if (userRepository.existsByUsername(registerVM.username)) {
            throw new IllegalArgumentException("Username is already taken");
        }

        User user = new User();
        user.setUsername(registerVM.username);
        user.setEmail(registerVM.email);
        user.setPassword(passwordEncoder.encode(registerVM.password));
        user.setUserRole("USER");
        user.setCreatedDate(new Date());

        userRepository.save(user);
    }

    @Override
    public UserDto login(LoginVM loginVM) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginVM.username,
                            loginVM.password
                    )
            );

            User user = userRepository.findByUsername(loginVM.username).orElse(null);
            invalidateAllUserTokens(user);

            String token = jwtUtil.generateToken(authentication.getName());
            String refreshToken = jwtUtil.generateRefreshToken(authentication.getName());

            saveToken(user, token, TokenType.TOKEN);
            saveToken(user, refreshToken, TokenType.REFRESH_TOKEN);

            UserDto userDto = modelMapper.map(user, UserDto.class);

            userDto.setToken(token);
            userDto.setRefreshToken(refreshToken);

            return userDto;
        } catch (AuthenticationException ae) {
            throw new BadCredentialsException(ae.getMessage());
        }
    }

    @Override
    public TokenRefreshDto refreshToken(String refreshToken) {
        Token storedRefreshToken = tokenRepository.findByTokenAndTypeAndValidity(
                refreshToken, TokenType.REFRESH_TOKEN, true).orElseThrow(() -> new InvalidBearerTokenException("Invalid refresh token"));

        if (isRefreshTokenExpired(storedRefreshToken)) {
            storedRefreshToken.setValidity(false);
            tokenRepository.save(storedRefreshToken);
            throw new InvalidBearerTokenException("Refresh token expired");
        }

        User user = storedRefreshToken.getUser();

        invalidateUserTokensByType(user, TokenType.TOKEN);
        String newAccessToken = jwtUtil.generateToken(user.getUsername());

        String newRefreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        storedRefreshToken.setValidity(false);
        tokenRepository.save(storedRefreshToken);

        saveToken(user, newAccessToken, TokenType.TOKEN);
        saveToken(user, newRefreshToken, TokenType.REFRESH_TOKEN);

        TokenRefreshDto refreshTokenDto = new TokenRefreshDto();
        refreshTokenDto.setRefreshToken(newRefreshToken);
        refreshTokenDto.accessToken = newAccessToken;
        refreshTokenDto.username = user.getUsername();

        return refreshTokenDto;
    }

    @Override
    public void logout(String token) {
        Token storedToken = tokenRepository.findByTokenAndValidity(token, true)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
        User user = storedToken.getUser();
        invalidateAllUserTokens(user);
    }

    @Override
    public UserDto findUserById(String userId) {
        return modelMapper.map(userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Could not find user with provided id !")), UserDto.class);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void follow(String userId) {
        final User currentUser = getCurrentUser();
        final User userToFollow = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        checkNotAlreadyFollowing(currentUser, userToFollow);
        checkNotFollowingItself(currentUser, userToFollow);

        userFollowService.followUser(currentUser, userToFollow);
    }

    @Override
    public void unfollow(String userId) {
        final User currentUser = getCurrentUser();
        final User userToUnfollow = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        checkIsFollowing(currentUser, userToUnfollow);
        checkNotUnfollowingItself(currentUser, userToUnfollow);

        userFollowService.unfollowUser(currentUser, userToUnfollow);
    }

    private User getCurrentUser() {
        String username = getCurrentUsername();

        return userRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("Access denied"));
    }

    private String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Access is denied !");
        }

        final Object principal = authentication.getPrincipal();

        if (principal instanceof User user) {
            return user.getUsername();
        }

        if (principal instanceof Jwt jwt) {
            return jwt.getSubject();
        }

        return principal.toString();
    }

    private void invalidateAllUserTokens(User user) {
        List<Token> validTokens = tokenRepository.findByUserAndValidity(user, true);
        validTokens.forEach(token -> {
            token.setValidity(false);
        });
        tokenRepository.saveAll(validTokens);
    }

    private void invalidateUserTokensByType(User user, TokenType type) {
        List<Token> validTokens = tokenRepository.findByUserAndTypeAndValidity(user, type, true);
        validTokens.forEach(token -> {
            token.setValidity(false);
        });
        tokenRepository.saveAll(validTokens);
    }

    private boolean isRefreshTokenExpired(Token storedRefreshToken) {
        long refreshTokenValidity = 7 * 24 * 60 * 60 * 1000L; // 7 days
        Date expirationDate = new Date(storedRefreshToken.getCreatedDate().getTime() + refreshTokenValidity);
        return new Date().after(expirationDate);
    }

    private void saveToken(User user, String jwtToken, TokenType tokenType) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setType(tokenType);
        token.setValidity(true);
        token.setCreatedDate(new Date());
        tokenRepository.save(token);
    }

    private void checkNotAlreadyFollowing(User currentUser, User userToFollow) {
        boolean alreadyFollowing = userFollowService.checkAlreadyFollowing(currentUser.getId(), userToFollow.getId());
        if (alreadyFollowing) {
            throw new IllegalStateException("Already following this user !");
        }
    }

    private void checkNotFollowingItself(User currentUser, User userToFollow) {
        if (currentUser.getId().equals(userToFollow.getId())) {
            throw new IllegalStateException("You can not follow yourself !");
        }
    }

    private void checkNotUnfollowingItself(User currentUser, User userToUnfollow) {
        if (currentUser.getId().equals(userToUnfollow.getId())) {
            throw new IllegalStateException("You can not unfollow yourself !");
        }
    }

    private void checkIsFollowing(User currentUser, User userToFollow) {
        boolean notFollowing = userFollowService.checkNotFollowing(currentUser.getId(), userToFollow.getId());
        if (!notFollowing) {
            throw new IllegalStateException("You can not unfollow a user you don't follow !");
        }
    }
}
