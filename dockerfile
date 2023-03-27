FROM maven:3.8.5-openjdk-18 as BUILD
RUN mkdir -p /app
WORKDIR /app
COPY . .
RUN mvn clean package spring-boot:repackage -DskipTests

FROM openjdk:18-slim
WORKDIR /app
COPY --from=BUILD /app/target/vigenereCoderServer*.jar server.jar
ENV spring.profiles.active="develop"
COPY ./target/vigenereCoderServer*.jar /server.jar
ENTRYPOINT ["java", "-jar", "server.jar"]
EXPOSE 9290