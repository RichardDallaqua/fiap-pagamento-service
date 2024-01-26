FROM maven:3.9.2-amazoncorretto-17 AS builder
COPY ./pom.xml /tmp
COPY src/ /tmp/src/
WORKDIR /tmp
RUN mvn package -Dmaven.test.skip=true
FROM amazoncorretto:17.0.7-al2023-headless
COPY --from=builder /tmp/target/payment-ms-0.0.1-SNAPSHOT.jar /tmp/
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/tmp/payment-ms-0.0.1-SNAPSHOT.jar"]