FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG WAR_FILE=target/cpyd.war
ADD ${WAR_FILE} cpyd.war
ENTRYPOINT ["java","-jar","/cpyd.war"]
