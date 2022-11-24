package com.example.apotheke.service;

import com.example.apotheke.model.GitHubRepositoryDto;
import com.example.apotheke.model.GitHubRepositoryResponseDto;
import com.example.apotheke.model.TopRepositoryQuery;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.retry.annotation.Retry;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopRepositoryService implements TopRepositoryInterface {

    private static final String GitHub_API = "https://api.GitHub.com/search/repositories?q=";
    private static final String SORT_BY_RATING_WITH_DEFAULT_COUNT = "sort=stars&order=desc";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final int DEFAULT_PER_PAGE = 50;

    private final CircuitBreaker circuitBreaker;

    private final WebClient client;

    /**
     *
     */
    @Override
    @Cacheable(cacheNames = "repositories", keyGenerator = "keyGenerator")
    @Retry(name = "search Repos form GitHub open API", fallbackMethod = "returnEmptyList")
    public Flux<GitHubRepositoryDto> getGitHubRepositoryResponseFlux(TopRepositoryQuery repositoryQuery) {
        return client.get()
            .uri(getEndPoint(repositoryQuery))
            .retrieve()
            .bodyToMono(GitHubRepositoryResponseDto.class)
            .flatMapIterable(GitHubRepositoryResponseDto::getItems)
            .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
            .subscribeOn(Schedulers.boundedElastic())
            .publishOn(Schedulers.boundedElastic());
    }

    /**
     * Constructs Git hub API end points
     */
    private String getEndPoint(TopRepositoryQuery repositoryQuery) {
        var language = repositoryQuery.getLanguage();
        var perPage = repositoryQuery.getPerPage();
        var date = repositoryQuery.getDate();
        var apiBuilder = new StringBuilder(GitHub_API);

        if (!ObjectUtils.isEmpty(language)) {
            apiBuilder.append("language:").append(language).append("+");
        }
        if (!ObjectUtils.isEmpty(date)) {
            apiBuilder.append("created:>").append(LocalDate.parse(date.toString(), DATE_TIME_FORMATTER)).append("&");
        }
        if (!ObjectUtils.isEmpty(perPage)) {
            apiBuilder.append("per_page=").append(perPage).append("&");
        } else {
            apiBuilder.append("per_page=").append(DEFAULT_PER_PAGE).append("&");
        }

        apiBuilder.append(SORT_BY_RATING_WITH_DEFAULT_COUNT);
        log.debug("GitHub repo search api constructed: {}", apiBuilder);
        return apiBuilder.toString();
    }

    /**
     * In case of GitHub API failiures, this fallback method will get executed.
     */
    public Flux<GitHubRepositoryResponseDto> returnEmptyList(
        TopRepositoryQuery repositoryQuery,
        @NotNull Throwable e
    ) {
        log.error("GitHub repository search Api call failed with exception: {}, returning empty list", e.getMessage());
        return Flux.error(e);
    }

}
