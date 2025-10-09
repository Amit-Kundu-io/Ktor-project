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



# ---- Stage 1: Build ----
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app

# Copy Gradle and project configs first (for dependency caching)
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle.properties .

# Make Gradle executable
RUN chmod +x ./gradlew

# Pre-download dependencies (cache)
RUN ./gradlew build -x test --no-daemon || true

# Copy source code
COPY src ./src

# Clean and build fat jar
RUN ./gradlew clean build -x test --no-daemon

# ---- Stage 2: Runtime ----
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy built jar from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose Render port (Render automatically provides $PORT)
EXPOSE 8080

# Set environment variables (optional but safe defaults)
ENV APP_ENV=prod \
    JAVA_OPTS="-Xmx512m -Dfile.encoding=UTF-8"

# Run the app
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]


