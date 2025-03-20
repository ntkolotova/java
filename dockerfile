FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

RUN git clone -b main https://github.com/ntkolotova/java.git

WORKDIR java/task

RUN mvn clean package


FROM openjdk:21-slim

COPY --from=build /app/java/task/target/stub.jar /app/stub.jar

COPY --from=build /app/java/task/jolokia-agent.jar /app/jolokia-agent.jar

WORKDIR /app

EXPOSE 8080, 8778

ENTRYPOINT ["java", "-javaagent:jolokia-agent.jar=port=8778,host=0.0.0.0", "-jar", "stub.jar"]