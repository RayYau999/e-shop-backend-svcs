# Dockerfile
# syntax=docker/dockerfile:1.4

FROM gradle:8.5-jdk21 AS builder
WORKDIR /workspace
ENV GRADLE_USER_HOME=/home/gradle/.gradle

# copy the modules from the repository root (build context must be repo root)
COPY eshop-pymt-svc ./eshop-pymt-svc
COPY security-library ./security-library
COPY payment-library ./payment-library

# ensure wrapper is executable and available with its gradle/wrapper JAR
RUN chmod +x ./gradlew || true

# warm Gradle cache and verify wrapper
RUN --mount=type=cache,target=/home/gradle/.gradle ./eshop-pymt-svc/gradlew --no-daemon --version

# build libraries (use 'jar' if they are not Spring Boot apps)
RUN --mount=type=cache,target=/home/gradle/.gradle ./eshop-pymt-svc/gradlew --no-daemon -x test -p ./security-library publishToMavenLocal
RUN --mount=type=cache,target=/home/gradle/.gradle ./eshop-pymt-svc/gradlew --no-daemon -x test -p ./payment-library publishToMavenLocal

# build only the gateway jar (skip tests)
WORKDIR /workspace/eshop-pymt-svc
RUN chmod +x ./gradlew
RUN --mount=type=cache,target=/home/gradle/.gradle ./gradlew \
  bootJar \
  --no-daemon -x test

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# copy gateway jar
COPY --from=builder /workspace/eshop-pymt-svc/build/libs/*.jar app.jar
# copy library jars
RUN mkdir -p /app/libs
COPY --from=builder /workspace/security-library/build/libs/*.jar /app/libs/
COPY --from=builder /workspace/payment-library/build/libs/*.jar /app/libs/

ENV JAVA_OPTS="-Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]