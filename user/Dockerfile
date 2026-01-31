# syntax=docker/dockerfile:1

# Build stage
FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Step 1: Copy only pom.xml for dependency resolution (cache layer)
COPY pom.xml .

# Step 2: Download dependencies (cached if pom.xml unchanged)
RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:go-offline -B

# Step 3: Copy source code
COPY src ./src

# Step 4: Build application (skip tests for faster builds)
RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -DskipTests -B

# Step 5: Extract JAR layers for better caching
RUN mkdir -p target/extracted && \
    java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted

# Runtime stage
FROM eclipse-temurin:17-jre-alpine AS runtime

# Step 6: Install required packages
RUN apk add --no-cache curl

# Step 7: Create non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

# Step 8: Copy JAR layers from build stage (better caching)
COPY --from=build --chown=spring:spring /app/target/extracted/dependencies/ ./
COPY --from=build --chown=spring:spring /app/target/extracted/spring-boot-loader/ ./
COPY --from=build --chown=spring:spring /app/target/extracted/snapshot-dependencies/ ./
COPY --from=build --chown=spring:spring /app/target/extracted/application/ ./

# Step 9: Switch to non-root user
USER spring:spring

# Step 10: Expose application port
EXPOSE 8080

# Step 11: Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Step 12: Run application using Spring Boot launcher
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "org.springframework.boot.loader.launch.JarLauncher"]
