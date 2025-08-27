package com.example.demo.domain.news.dtos.nistMappingDtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Nodes {
    private List<CpeMatch> cpeMatch;
}
