package com.example.demo.domain.token.entity.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class TokenRefreshDto {
    public String accessToken;
    public String refreshToken;
    public String username;
    public String tokenType = "Bearer";
}
