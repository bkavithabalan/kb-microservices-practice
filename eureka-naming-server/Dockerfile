# syntax=docker/dockerfile:1
# build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

#copy pom for better layering, to download dependencies 
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B

# copy source and build
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests -B

#Run Stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser:appgroup

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8762
ENTRYPOINT ["java", "-jar", "app.jar"]
