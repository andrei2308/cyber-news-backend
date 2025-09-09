package com.example.demo.domain.news.entity;

import com.example.demo.domain.InformationEntity;
import com.example.demo.domain.enums.Severity;
import com.example.demo.domain.newsLikes.entity.NewsLikes;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NamedEntityGraph(
        name = "News.withUserAndLikes",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode(value = "userLikes", subgraph = "userLikes.user")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "userLikes.user",
                        attributeNodes = @NamedAttributeNode("likedUser")
                )
        }
)
public class News extends InformationEntity {

    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Severity")
    @Enumerated(EnumType.STRING)
    private Severity severity;

    @Column(name = "Score")
    private double score;

    @Column(name = "Affected_systems")
    private String affectedSystems;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "likedPost", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<NewsLikes> userLikes = new HashSet<>();
}
