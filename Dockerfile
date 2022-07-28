FROM openjdk:11
COPY ./target/raw-demo.jar raw-demo.jar
CMD ["java","-jar","raw-demo.jar"]
EXPOSE 8080