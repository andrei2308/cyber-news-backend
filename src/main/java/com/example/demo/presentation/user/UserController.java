package com.example.demo.presentation.user;

import com.example.demo.domain.constants.Constants;
import com.example.demo.domain.user.dtos.LoginVM;
import com.example.demo.domain.user.dtos.RegisterVM;
import com.example.demo.domain.user.dtos.UserDto;
import com.example.demo.service.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = UserController.API_USER_ROUTE)
public class UserController {
    public static final String API_USER_ROUTE = Constants.DEFAULT_ROUTE + "/user";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
}
