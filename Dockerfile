FROM openjdk:11-jre
CMD ["./mvnw", "clean", "package"]
ARG JAR_FILE_PATH=/build/libs/*.jar
COPY ${JAR_FILE_PATH} ReservationSite-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "ReservationSite-0.0.1-SNAPSHOT.jar"]