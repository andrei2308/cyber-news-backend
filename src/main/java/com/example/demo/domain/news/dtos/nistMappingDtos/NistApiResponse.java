package com.example.demo.domain.news.dtos.nistMappingDtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NistApiResponse {
    private int resultsPerPage;
    private List<VulnerabilityWrapper> vulnerabilities;
}
