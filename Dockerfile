FROM maven:3.9.6-amazoncorretto-17-al2023
COPY ./pom.xml /tmp
COPY src/ /tmp/src/
WORKDIR /tmp
RUN mvn package -Dmaven.test.skip=true
FROM openjdk:21-ea-17-slim-buster
COPY --from=builder /tmp/target/payment-ms-0.0.1-SNAPSHOT.jar /tmp/
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/tmp/payment-ms-0.0.1-SNAPSHOT.jar"]