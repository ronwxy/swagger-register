from jdk:8
ENV PROFILE=dev
RUN mkdir /app /logs
COPY ./target/swagger-register-1.0.0-SNAPSHOT.jar /app/app.jar
WORKDIR /app
EXPOSE 11090
CMD ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "app.jar"]
