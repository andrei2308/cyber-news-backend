package com.example.demo.entities.news;

import com.example.demo.entities.InformationEntity;
import com.example.demo.entities.user.User;
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
