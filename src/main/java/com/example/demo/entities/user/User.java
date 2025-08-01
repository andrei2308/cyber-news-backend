package com.example.demo.entities.user;

import com.example.demo.entities.InformationEntity;
import com.example.demo.entities.news.News;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class User extends InformationEntity {

    @Column(name = "UserRole")
    private String userRole;

    @Column(name = "Username", nullable = false, unique = true)
    private String username;

    @Column(name = "Password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<News> news = new HashSet<>();

}
