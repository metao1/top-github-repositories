package com.example.apotheke.config;

import com.example.apotheke.model.GitHubRepositoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@RouterOperations({
    @RouterOperation(
        method = RequestMethod.POST,
        operation =
        @Operation(
            description = "Get list of top repositories in GitHub sorted based-on stars in descending order",
            operationId = "getTopGitHubRepositoryList",
            tags = "getTopGitHubRepositoryList",
            method = "GET",
            responses = {
                @ApiResponse(
                    responseCode = "200",
                    description = "Add player response",
                    content = {
                        @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GitHubRepositoryDto.class))
                    }),
                @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request response"
                )
            }))
})
public @interface GetTopGitHubRepositoryListApiInfo {}