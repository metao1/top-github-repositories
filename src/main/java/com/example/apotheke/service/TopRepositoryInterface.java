package com.example.apotheke.service;

import com.example.apotheke.model.GitHubRepositoryDto;
import com.example.apotheke.model.TopRepositoryQuery;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@Validated
public interface TopRepositoryInterface {

    Flux<GitHubRepositoryDto> getGitHubRepositoryResponseFlux(@Valid TopRepositoryQuery repositoryQuery);
}

