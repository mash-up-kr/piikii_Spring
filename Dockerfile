FROM openjdk:21-jdk
ENV PROD_DB_URL=${PROD_DB_URI}
ENV PROD_DB_USERNAME=${PROD_DB_USERNAME}
ENV PROD_DB_PASSWORD=${PROD_DB_PASSWORD}

ARG JAR_FILE=piikii-input-http/build/libs/piikii-input-http-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} piikii-http.jar
ENTRYPOINT ["java", "-jar", "/piikii-http.jar"]
