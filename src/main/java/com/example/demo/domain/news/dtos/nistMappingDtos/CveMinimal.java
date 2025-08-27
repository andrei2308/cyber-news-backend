package com.example.demo.domain.news.dtos.nistMappingDtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CveMinimal {
    private Metrics metrics;
    private List<Configurations> configurations;
}
