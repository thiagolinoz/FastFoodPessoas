FROM eclipse-temurin:21-jdk-jammy

# 1. Copia o certificado CA da AWS para o contêiner
# Certifique-se de que o arquivo 'global-bundle.pem' está na mesma pasta do Dockerfile
COPY global-bundle.pem /tmp/global-bundle.pem

# 2. Comando para importar o certificado CA para o Keystore (cacerts) do Java
# O 'keytool' adiciona o certificado para que o JRE confie nele.
RUN keytool -import -trustcacerts -file /tmp/global-bundle.pem -alias DocumentDBRootCA -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -noprompt

WORKDIR /app

# Copia o JAR do seu projeto
COPY target/postech-fastfood.jar /app/app.jar

# ENTRYPOINT limpo: o problema de confiança já foi resolvido acima.
ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080
