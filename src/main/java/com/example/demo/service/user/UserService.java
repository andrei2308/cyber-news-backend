package com.example.demo.service.user;

import com.example.demo.domain.token.entity.dto.TokenRefreshDto;
import com.example.demo.domain.user.dtos.LoginVM;
import com.example.demo.domain.user.dtos.RegisterVM;
import com.example.demo.domain.user.dtos.UserDto;
import com.example.demo.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void register(RegisterVM registerVM);
    UserDto login(LoginVM loginVM);

    TokenRefreshDto refreshToken(String refreshToken);

    void logout(String token);
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
