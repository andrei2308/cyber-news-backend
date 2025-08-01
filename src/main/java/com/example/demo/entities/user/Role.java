package com.example.demo.entities.user;

import com.example.demo.entities.InformationEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Role extends InformationEntity {

    @Column(name = "Code")
    private String code;

    @Column(name = "Description")
    private String description;
}
