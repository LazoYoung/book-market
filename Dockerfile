FROM eclipse-temurin:17-jdk-jammy@sha256:8e41a856a8f2ee10235c3cfb6eda62082ad81662300920ea54c014e7f3fd4628
WORKDIR /book-market

COPY build.gradle ./build.gradle
COPY gradlew ./gradlew
COPY gradle ./gradle
COPY src ./src
RUN chmod u+x ./gradlew
RUN ./gradlew build

CMD ["./gradlew", "bootRun"]