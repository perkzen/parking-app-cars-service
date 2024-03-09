FROM maven:3.9-ibm-semeru-21-jammy  as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-slim-bookworm
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "--enable-preview", "-jar", "app.jar"]




