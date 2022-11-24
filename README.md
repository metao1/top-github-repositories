# Top Repositories GitHub API
Web API gateway for popular repositories on GitHub.

#### Get top repositories sorted by stars

```http
  GET /api/v1/repositories
```

| Parameter  | Type      | Description                |
|:-----------|:----------| :------------------------- |
| `date`     | `string`  | **Required**. matches repositories that were created before the date, YYYY-MM-DD |
| `language` | `string`  | matches repositories by language|
| `per_page` | `Integer` | number of entries to fetch|


## Features

- SpringBoot5 WebFlux non-blocking I/O
- Spring caching
- Fault tolerance support - Resilience4j
- Includes Integration test
- API documentation

## Test & Run

```
gradle clean build
gradle bootRun
```

## API documentation is available at http://localhost:8080/swagger-ui.html 