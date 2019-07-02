from openjdk:8-jdk-alpine
ENV PROFILE=dev
RUN mkdir /app /logs
COPY ./target/swagger-register-1.0.0-SNAPSHOT.jar /app/app.jar
WORKDIR /app
VOLUME /register-data
EXPOSE 11090
CMD ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "app.jar"]
