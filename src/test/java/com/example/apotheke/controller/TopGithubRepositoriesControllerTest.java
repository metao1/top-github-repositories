package com.example.apotheke.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.apotheke.config.ApiConfig;
import com.example.apotheke.config.ApiCircuitBreakerConfig;
import com.example.apotheke.model.GitHubRepositoryDto;
import com.example.apotheke.service.TopRepositoryService;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@TestInstance(Lifecycle.PER_CLASS)
@WebFluxTest(controllers = {TopGitHubRepositoriesController.class})
@Import({ApiCircuitBreakerConfig.class, TopRepositoryService.class, ApiConfig.class})
class TopGitHubRepositoriesControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void topGitHubRepositoriesControllerTest() {
        webTestClient.get()
            .uri("/api/v1/repositories")
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testAllRepositories() {
        webTestClient
            .get()
            .uri("/api/v1/repositories?date=2022-01-11")
            .exchange()
            .expectStatus()
            .isOk()//expect return status code is 200 OK
            .expectBodyList(GitHubRepositoryDto.class)
            .hasSize(50);//expect default size is 50
    }

    @Test
    public void testAllRepositoriesWithLanguageParam() {
        String language = "java";
        var returnList = webTestClient
            .get()
            .uri("/api/v1/repositories?date=2022-01-11&language=" + language)
            .exchange()
            .expectStatus()
            .isOk()//expect return status code is 200 OK
            .expectBodyList(GitHubRepositoryDto.class)
            .returnResult()
            .getResponseBody();
        assertThat(returnList)
            .extracting(GitHubRepositoryDto::getLanguage)
            .map(String::toLowerCase)
            .contains(language, Index.atIndex(0));
    }

    @Test
    public void testAllRepositoriesWithPageSizeParam() {
        int count = 2;

        var returnList = webTestClient
            .get()
            .uri("/api/v1/repositories?date=2022-01-11&per_page=" + count)
            .exchange()
            .expectStatus()
            .isOk()//expect return status code is 200 OK
            .expectBodyList(GitHubRepositoryDto.class)
            .returnResult()
            .getResponseBody();
        assertThat(returnList)
            .hasSize(2);
    }

    @Test
    public void testAllRepositoriesWithInvalidPageSizeParam() {
        int invalidPageCount = -1;
        webTestClient
            .get()
            .uri("/api/v1/repositories?date=2022-01-11&per_page=" + invalidPageCount)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testAllRepositoriesWithInvalidDateParam() {
        String dateString = "2022-01-11111";
        webTestClient
            .get()
            .uri("/api/v1/repositories?date=" + dateString)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

}