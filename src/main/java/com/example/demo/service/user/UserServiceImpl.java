package com.example.demo.service.user;

import com.example.demo.configuration.security.JwtUtil;
import com.example.demo.domain.user.dtos.LoginVM;
import com.example.demo.domain.user.dtos.RegisterVM;
import com.example.demo.domain.user.dtos.UserDto;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(JwtUtil jwtUtil, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
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
            String token = jwtUtil.generateToken(authentication.getName());
            UserDto userDto = new UserDto();
            userDto.username = loginVM.username;
            userDto.token = token;
            return userDto;
        } catch (AuthenticationException ae) {
            throw new BadCredentialsException(ae.getMessage());
        }
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
}
