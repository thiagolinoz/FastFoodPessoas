FROM amazoncorretto:21-al2023-jdk
RUN dnf update -y && dnf install -y shadow-utils \
    && dnf clean all
RUN useradd -ms /bin/bash postech-fastfood
USER postech-fastfood
COPY ./target/postech-fastfood.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]