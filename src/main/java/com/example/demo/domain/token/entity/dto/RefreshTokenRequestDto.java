package com.example.demo.domain.token.entity.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class RefreshTokenRequestDto {
    public String refreshToken;
}
