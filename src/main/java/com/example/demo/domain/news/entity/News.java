package com.example.demo.domain.news.entity;

import com.example.demo.domain.InformationEntity;
import com.example.demo.domain.enums.Severity;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class News extends InformationEntity {

    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Severity")
    @Enumerated(EnumType.STRING)
    private Severity severity;

    @ManyToOne
    private User user;
}
