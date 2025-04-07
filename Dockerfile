FROM openjdk:17-jdk-slim

COPY build/libs/test-case-helper-1.0.0.jar testCaseHelper.jar

EXPOSE 4141

ENTRYPOINT ["java", "-jar", "testCaseHelper.jar"]
