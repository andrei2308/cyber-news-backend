package com.example.demo.configuration.mapper;

import com.example.demo.domain.news.dtos.NewsDto;
import com.example.demo.domain.news.entity.News;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

        return modelMapper;
    }
}
