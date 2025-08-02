package com.example.demo.domain.token.entity;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.token.enums.TokenType;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Token extends BaseEntity {

    @ManyToOne
    @JoinColumn(referencedColumnName = "Username")
    private User user;

    @Column(name = "Token")
    private String token;

    @Column(name = "Type")
    @Enumerated(EnumType.STRING)
    private TokenType type;

    @Column(name = "Validity")
    private Boolean validity;
}
