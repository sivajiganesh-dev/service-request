FROM openjdk:11.0
WORKDIR /app
COPY build/libs/service-request-0.0.1.jar app.jar
ENV JAVA_VERSION = 11.0
ENTRYPOINT ["java", "-jar", "app.jar"]