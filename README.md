# kb-microservices-practice

A Spring Cloud microservices practice project demonstrating service discovery, centralized configuration, API gateway routing with JWT authentication, circuit breaking, and inter-service communication via Feign.

## Architecture

```
                        ┌─────────────────────┐
                        │  eureka-naming-server │  (Service Registry, :8762)
                        └───────────▲───────────┘
                                    │ registers
                        ┌───────────┴───────────┐
                        │     config-server      │  (:8888, backed by a local Git config repo)
                        └───────────▲───────────┘
                                    │ pulls config
             ┌──────────────────────┼──────────────────────┐
             │                      │                       │
      ┌──────┴──────┐      ┌────────┴────────┐    ┌─────────┴─────────┐
      │ api-gateway  │──────▶ currency-exchange│    │ currency-conversion│
      │   (:8100)    │      │  -service (:8090)│◀──▶│  -service (:8070) │
      └──────────────┘      └─────────────────┘  Feign└────────────────┘
```

| Service | Port | Role |
|---|---|---|
| eureka-naming-server | 8762 | Service registry |
| config-server | 8888 | Centralized config (Git-backed) |
| api-gateway | 8100 | Entry point — JWT auth filter, routing, circuit breaker, aggregates Swagger docs |
| currency-exchange-service | 8090 | Exchange rate lookup (JPA/PostgreSQL) |
| currency-conversion-service | 8070 | Currency conversion — calls exchange service via Feign (JPA/PostgreSQL) |

`kb-parent` is the shared parent POM (dependency/version management only, no code).

## Tech Stack

Java 21 · Spring Boot 4.1 · Spring Cloud 2025.1.2 · Netflix Eureka · Spring Cloud Gateway (WebFlux) · Spring Cloud Config · Resilience4j · OpenFeign · Spring Data JPA + PostgreSQL · Micrometer/OpenTelemetry (Zipkin) · springdoc-openapi · Docker Compose · GitHub Actions (per-service CI)

## Run Locally

**Docker Compose (recommended):**
```bash
docker-compose up --build
```

## API (via gateway, `:8100`)

| Method | Path | Description |
|---|---|---|
| GET | `/currency-exchange/from/{from}/to/{to}` | Get exchange rate |
| GET | `/curr-conversion/from/{from}/to/{to}/quantity/{quantity}` | Convert currency |

Swagger UI: `http://localhost:8100/swagger-ui.html`

## CI/CD

Each service has its own GitHub Actions workflow under `.github/workflows/`, scoped by path filters so only the changed service rebuilds. Each runs `mvn clean verify` against JDK 21.

## Roadmap

- [ ] Externalize the JWT secret out of `api-gateway/application.yaml`
- [ ] Add CI workflow for `auth-service` (in progress)
- [ ] Add Kubernetes manifests for deployment beyond Docker Compose
