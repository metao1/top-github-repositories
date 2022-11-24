package com.example.apotheke.model;

import java.time.LocalDate;
import javax.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TopRepositoryQuery {

    private LocalDate date;
    @Min(message = "Page size value should be greater than 0", value = 0)
    private Integer perPage;
    private String language;
}
