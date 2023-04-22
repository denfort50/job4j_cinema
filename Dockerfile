FROM openjdk
ARG JAR_FILE=target/job4j_cinema-1.0.jar
WORKDIR job4j_cinema
COPY ${JAR_FILE} app.jar
CMD java -jar app.jar