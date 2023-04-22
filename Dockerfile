FROM openjdk
WORKDIR job4j_cinema
ADD target/job4j_cinema-1.0.jar app.jar
ENTRYPOINT java -jar app.jar