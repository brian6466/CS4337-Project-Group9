FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /app

# Copy build files
COPY settings.gradle ./settings.gradle
COPY build.gradle ./build.gradle
COPY contentApi/build.gradle ./contentApi/build.gradle
COPY contentApi/src ./contentApi/src

# Run
RUN gradle :contentApi:clean :contentApi:bootJar -x test

FROM openjdk:21-jdk-slim
WORKDIR /app
LABEL authors="craig"

COPY --from=builder /app/contentApi/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]

#FROM openjdk:21-jdk-slim
#COPY build/libs/contentApi.jar app.jar
#ENTRYPOINT [ "java" , "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" , "-jar" , "app.jar" ]