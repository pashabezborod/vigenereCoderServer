FROM openjdk:18-slim
ENV spring.profiles.active="develop"
COPY ./target/vigenereCoderServer*.jar /server.jar
ENTRYPOINT ["java", "-jar", "/server.jar"]
EXPOSE 9290