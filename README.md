A hands-on Spring Cloud microservices project demonstrating service discovery, centralized configuration, API gateway routing with JWT authentication, circuit breaking, and inter-service communication via Feign.

Architecture
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
eureka-naming-server — Netflix Eureka service registry all other services register with.
config-server — Spring Cloud Config Server; serves shared configuration from a local Git-backed config repo.
api-gateway — Spring Cloud Gateway (WebFlux) entry point. Applies a JWT authentication filter, a request logging filter, and a Resilience4j circuit breaker; aggregates Swagger/OpenAPI docs from downstream services.
currency-exchange-service — Exposes exchange rate lookups; backed by PostgreSQL via JPA.
currency-conversion-service — Calls currency-exchange-service via OpenFeign to perform currency conversion; also backed by PostgreSQL via JPA.
kb-parent — Shared parent POM (dependency/version management only, no code) that all services inherit from.
Tech Stack
Java 21
Spring Boot 4.1.0 / Spring Cloud 2025.1.2
Spring Cloud Gateway (WebFlux), Netflix Eureka, Spring Cloud Config
Resilience4j (circuit breaker), OpenFeign (inter-service calls)
Spring Data JPA + PostgreSQL
Micrometer + OpenTelemetry (Zipkin exporter) for tracing
springdoc-openapi (Swagger UI)
Maven (multi-module), Docker / Docker Compose
GitHub Actions (per-service CI)
Prerequisites
JDK 21
Maven 3.9+
Docker & Docker Compose (for containerized run)
PostgreSQL (for currency-exchange-service and currency-conversion-service, unless run via Docker Compose)
A local Git repository to serve as the Config Server's config-repo (see Configuration)
Project Structure
Microservices/
├── kb-parent/                     # Shared parent POM
├── eureka-naming-server/          # Service registry
├── config-server/                 # Centralized config
├── api-gateway/                   # Gateway + JWT auth + routing
├── currency-exchange-service/     # Exchange rate service
├── currency-conversion-service/   # Conversion service (Feign client of exchange service)
├── docker-compose.yml
└── .github/workflows/             # Per-service CI pipelines
