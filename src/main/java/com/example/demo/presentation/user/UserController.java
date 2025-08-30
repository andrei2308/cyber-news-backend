package com.example.demo.presentation.user;

import com.example.demo.domain.constants.Constants;
import com.example.demo.domain.news.dtos.NewsCreateVM;
import com.example.demo.domain.news.dtos.NewsDto;
import com.example.demo.domain.token.entity.dto.RefreshTokenRequestDto;
import com.example.demo.domain.token.entity.dto.TokenRefreshDto;
import com.example.demo.domain.user.dtos.LoginVM;
import com.example.demo.domain.user.dtos.RegisterVM;
import com.example.demo.domain.user.dtos.UserDto;
import com.example.demo.domain.user.dtos.UserProfile;
import com.example.demo.service.news.NewsService;
import com.example.demo.service.user.UserService;
import com.example.demo.service.userFollow.UserFollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = UserController.API_USER_ROUTE)
public class UserController {
    public static final String API_USER_ROUTE = Constants.DEFAULT_ROUTE + "/user";
    private final UserService userService;
    private final UserFollowService userFollowService;
    private final NewsService newsService;

    public UserController(UserService userService, UserFollowService userFollowService, NewsService newsService) {
        this.userService = userService;
        this.userFollowService = userFollowService;
        this.newsService = newsService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getUser(@PathVariable String userId) {
        UserProfile userDto = userService.findUserById(userId);

        return ResponseEntity.ok(userDto);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginVM loginVM) {
        UserDto userDto = userService.login(loginVM);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody RegisterVM registerVM) {
        userService.register(registerVM);
        return ResponseEntity.ok("Registered successfully !");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenRefreshDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshToken) {
        TokenRefreshDto tokenRefreshDto = userService.refreshToken(refreshToken.getRefreshToken());
        return ResponseEntity.ok(tokenRefreshDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        userService.logout(token);
        return ResponseEntity.ok("Logout successfully !");
    }

    @PostMapping("/{userId}/create-news")
    public ResponseEntity<NewsDto> createNewsForUser(@PathVariable String userId, @RequestBody NewsCreateVM newsCreateVM) {
        return ResponseEntity.ok(newsService.createNewsForUser(userId, newsCreateVM));
    }

    @PostMapping("{userId}/follow")
    public ResponseEntity<String> follow(@PathVariable String userId) {
        userService.follow(userId);
        return ResponseEntity.ok("User followed !");
    }

    @PostMapping("{userId}/unfollow")
    public ResponseEntity<String> unfollow(@PathVariable String userId) {
        userService.unfollow(userId);
        return ResponseEntity.ok("User unfollowed !");
    }
}
