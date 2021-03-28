FROM gradle:6.8.3-jdk11 as build

WORKDIR /app

COPY src /app/src
COPY *.gradle /app/

RUN gradle build

FROM openjdk:11-jre-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/kotlin-consumer.jar

ENTRYPOINT ["java", "-jar", "kotlin-consumer.jar"]