FROM amazoncorretto:21-al2023-jdk
RUN useradd -ms /bin/bash postech-fastfood
USER postech-fastfood
COPY ./target/postech-fastfood.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]