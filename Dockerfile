# spring-boot server

# build:
#       docker build --force-rm -t sb-server .
# run:
#       docker run -d --rm -it -p 8080:8080 --name sb-server sb-server

FROM openjdk:17-jdk-alpine as builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM openjdk:17-jdk-alpine
COPY --from=builder build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
EXPOSE 8080

