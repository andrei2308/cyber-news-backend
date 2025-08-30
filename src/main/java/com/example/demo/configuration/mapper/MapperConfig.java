package com.example.demo.configuration.mapper;

import com.example.demo.domain.news.dtos.NewsDto;
import com.example.demo.domain.news.entity.News;
import com.example.demo.domain.user.dtos.UserProfile;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.userFollow.dtos.UserFollowDto;
import com.example.demo.domain.userFollow.entity.UserFollow;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class MapperConfig {
    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<News, NewsDto>() {
            @Override
            protected void configure() {
                map(source.getUser().getId(), destination.getUserId());
                map(source.getUser().getUsername(), destination.getAuthorUsername());
            }
        });

        modelMapper.addMappings(new PropertyMap<User, UserProfile>() {
            @Override
            protected void configure() {
                using(followersConverter()).map(source.getFollowers(), destination.getFollowers());
                using(followingConverter()).map(source.getFollowing(), destination.getFollowing());
            }
        });

        return modelMapper;
    }

    private Converter<Set<UserFollow>, Set<UserFollowDto>> followersConverter() {
        return context -> {
            Set<UserFollow> userFollows = context.getSource();
            if (userFollows == null) return null;

            return userFollows.stream()
                    .map(follow -> new UserFollowDto(
                            follow.getFollower().getId(),
                            follow.getFollower().getUsername(),
                            follow.getFollower().getEmail()
                    ))
                    .collect(Collectors.toSet());
        };
    }

    private Converter<Set<UserFollow>, Set<UserFollowDto>> followingConverter() {
        return context -> {
            Set<UserFollow> userFollows = context.getSource();
            if (userFollows == null) return null;

            return userFollows.stream()
                    .map(follow -> new UserFollowDto(
                            follow.getFollowing().getId(),
                            follow.getFollowing().getUsername(),
                            follow.getFollowing().getEmail()
                    ))
                    .collect(Collectors.toSet());
        };
    }
}