FROM openjdk:8
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Xmx512m","-Dserver.port=${PORT}","-jar","app.jar"]