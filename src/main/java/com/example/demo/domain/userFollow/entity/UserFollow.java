package com.example.demo.domain.userFollow.entity;

import com.example.demo.domain.InformationEntity;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_follows")
public class UserFollow extends InformationEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private User following;

    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }
}
