# Build stage
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /build

COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -q package \
    && mvn -B -q dependency:copy-dependencies -DincludeScope=runtime -DoutputDirectory=target/lib

# Runtime stage
FROM eclipse-temurin:25-jre
WORKDIR /app

COPY --from=build /build/target/classes/ /app/classes/
COPY --from=build /build/target/lib/ /app/lib/

RUN useradd --create-home --shell /bin/bash appuser \
    && mkdir -p /app/db \
    && chown -R appuser:appuser /app

USER appuser

ENTRYPOINT ["java", "-cp", "/app/lib/*:/app/classes", "com.progys.interview.quiz.FigureIntersection"]
