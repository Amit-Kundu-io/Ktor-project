## Stage 1: Build and run inside Docker
#FROM eclipse-temurin:17-jdk-jammy
#
#WORKDIR /app
#
## Copy project files
#COPY gradlew .
#COPY gradle ./gradle
#COPY build.gradle.kts .
#COPY settings.gradle.kts .
#COPY gradle.properties .
#COPY src ./src
#
## Make Gradle wrapper executable
#RUN chmod +x ./gradlew
#
## Pre-download dependencies and build
#RUN ./gradlew build -x test --no-daemon
#
## Expose the port
#EXPOSE 8080
#
## Run the app using Gradle
#CMD ["./gradlew", "run"]



FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy Gradle configs first (to cache dependencies)
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle.properties .

RUN chmod +x ./gradlew
RUN ./gradlew build -x test --no-daemon || true

# Copy source code
COPY src ./src

# Build the project
RUN ./gradlew build -x test --no-daemon

# Expose port
EXPOSE 8080

# Run the app with Gradle (no daemon)
CMD ["./gradlew", "run", "--no-daemon"]
