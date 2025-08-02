package com.example.demo.domain.news.entity;

import com.example.demo.domain.InformationEntity;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class News extends InformationEntity {

    @Column(name = "Description")
    private String description;

    @ManyToOne
    private User user;
}
