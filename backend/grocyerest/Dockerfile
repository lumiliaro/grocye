FROM gradle:jdk21-alpine as BUILD
WORKDIR /backend
COPY . .
RUN gradle clean build --no-daemon

FROM eclipse-temurin:21-jdk-alpine as BACKEND
ENV JAR_NAME=grocyerest-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/backend
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME/build/libs/$JAR_NAME .
EXPOSE 8080
ENTRYPOINT ["java","-jar","/backend/grocyerest-0.0.1-SNAPSHOT.jar"]