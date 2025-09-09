package com.example.demo.domain.token.repository;

import com.example.demo.domain.token.entity.Token;
import com.example.demo.domain.token.enums.TokenType;
import com.example.demo.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {

    @EntityGraph("Token.withUser")
    Optional<Token> findByTokenAndValidity(String token, Boolean validity);

    Optional<Token> findByTokenAndTypeAndValidity(String token, TokenType type, Boolean validity);

    List<Token> findByUserAndValidity(User user, Boolean validity);

    List<Token> findByUserAndTypeAndValidity(User user, TokenType type, Boolean validity);

    List<Token> findByUser(User user);

    void deleteByValidityFalse();
}
