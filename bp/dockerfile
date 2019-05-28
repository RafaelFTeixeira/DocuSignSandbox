FROM maven:3.5.2-jdk-8-alpine AS build
COPY pom.xml /tmp/
RUN mvn -B dependency:go-offline -f /tmp/pom.xml -s /usr/share/maven/ref/settings-docker.xml
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml verify

FROM java:8-jre-alpine
WORKDIR /app
EXPOSE 8080
COPY --from=build /tmp/target/*.jar /app/bp.jar
COPY --from=build /tmp/src/main/resources/*.pdf /app/

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/bp.jar"]