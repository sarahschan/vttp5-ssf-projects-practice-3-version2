FROM eclipse-temurin:23-noble AS builder

WORKDIR /src

# copy files
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

# make mvnw executable
RUN chmod a+x mvnw && /src/mvnw package -Dmaven.test.skip=true


## Stage 2
FROM eclipse-temurin:23-jre-noble

WORKDIR /app

COPY --from=builder /src/target/august-2022-assessment-practice-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8888
ENV MY_API_PASS_KEY=

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar
