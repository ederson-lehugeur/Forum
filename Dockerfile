FROM openjdk:8
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Xmx512m","-jar","app.jar"]