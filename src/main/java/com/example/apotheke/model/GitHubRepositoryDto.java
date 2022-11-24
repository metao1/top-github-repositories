package com.example.apotheke.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Data;

@Data
public class GitHubRepositoryDto {

    private String name;
    @JsonProperty("full_name")
    private String fullName;
    private String url;
    @JsonProperty("html_url")
    private String htmlUrl;
    private String language;
    @JsonProperty("created_at")
    private LocalDate createdDate;
}
