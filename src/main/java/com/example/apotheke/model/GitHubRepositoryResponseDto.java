package com.example.apotheke.model;

import java.util.List;
import lombok.Data;

@Data
public class GitHubRepositoryResponseDto {

    List<GitHubRepositoryDto> items;
}
