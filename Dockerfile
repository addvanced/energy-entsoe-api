FROM --platform=linux/amd64 openjdk:17
EXPOSE 80
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]