package com.example.apotheke.controller;

import com.example.apotheke.config.GetTopGitHubRepositoryListApiInfo;
import com.example.apotheke.model.GitHubRepositoryDto;
import com.example.apotheke.model.TopRepositoryQuery;
import com.example.apotheke.service.TopRepositoryInterface;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/repositories")
@Tag(name = "Top GitHub Repositories Controller", description = "This is top GitHub repositories controller, that discovers popular repositories on GitHub.")
public class TopGitHubRepositoriesController {

    private final TopRepositoryInterface topRepositoryInterface;

    @GetMapping
    @GetTopGitHubRepositoryListApiInfo
    public Flux<GitHubRepositoryDto> getTopGitHubRepositoryList(
        @RequestParam(name = "per_page", defaultValue = "50", required = false) Integer perPage,
        @RequestParam(name = "language", required = false) String language,
        @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        var topRepositoryQuery = TopRepositoryQuery.builder()
            .language(language)
            .perPage(perPage)
            .date(date)
            .build();

        return topRepositoryInterface.getGitHubRepositoryResponseFlux(topRepositoryQuery);
    }
}
