FROM maven:3.8.7 as build
COPY . .
RUN mvn -B clean package -DskipTests

FROM openjdk:17
COPY --from=build target/*.jar GithubStats.jar
EXPOSE 2050

ENTRYPOINT ["java", "-jar", "-Dserver.port=2050", "github-stats.jar"]