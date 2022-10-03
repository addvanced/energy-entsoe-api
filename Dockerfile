FROM openjdk:17
EXPOSE 80
COPY /build/libs/energydata-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]