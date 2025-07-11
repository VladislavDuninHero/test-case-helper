FROM eclipse-temurin:17-jdk-jammy

# Устанавливаем системные зависимости
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    libfreetype6 \
    fontconfig \
    && rm -rf /var/lib/apt/lists/*

COPY build/libs/test-case-helper-1.0.0.jar testCaseHelper.jar

EXPOSE 4141

ENTRYPOINT ["java", "-jar", "testCaseHelper.jar"]
