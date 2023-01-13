FROM openjdk:17-alpine
WORKDIR /app
ARG JAR_FILE=./build/libs/sbb-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "./app.jar"]
