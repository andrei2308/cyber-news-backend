package com.example.demo.domain.news.dtos.nistMappingDtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Metrics {
    private List<CvssMetricV31> cvssMetricV31;
    private List<CvssMetricV2> cvssMetricV2;
}
