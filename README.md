# PosTechFiapFastFood

## Stacks utilizadas:
 - Docker
 - Docker-compose
 - Banco de dados - Amazon DocumentDB
 - Migration de dados - Flyway
 - Spring boot
 - Swagger

## Ambiente de Desenvolvimento:

Ao executar a aplicação utilizando o comando do Maven. 

`.\mvnw spring-boot:run`

Automaticamente será baixado a imagem Docker do MariaDB e criado um container com o Banco de Dados que será consumido pela 
Aplicação. Isso torna-se possível por causa da dependencia no pom.xml: `org.springframework.boot:spring-boot-docker-compose`.

## Ambiente de Produção

Para o ambiente de Produção, disponibilizamos um arquivo `docker-compose.yaml` para que automatize o processo de download
da imagem da aplicação assim como do banco de dados que será consumido pela aplicação.

## Geração de Imagem Docker

Visando a agilidade no desenvolvimento, geração de imagem docker, etc, foi adicionado um Shell Script na pasta raiz do
projeto chamado `atualiza-docker-image.sh` que contém todos os comandos necessários para geração e push da imagem docker.
Por ser um Shell Script, funciona apenas nos sistemas operacionais que contam com Shell "SH" como Linux, BSD, MacOS, etc.

Para gerar nova imagem docker, basta executar o script com o comando:

`./atualiza-docker-image.sh`

Ao executar o script, já será possível ver o retorno dos comandos. Caso erro de permissão de execução ocorra, será necessário
adicionar permissão de execução no script com o comando: `chmod +x ./atualiza-docker-image.sh`. Após o comando, tente executar
o script novamente.

## Documentação dos Endpoints: 

A aplicação possui Swagger que pode ser acessado através da URL `http://localhost:8080/swagger-ui/index.html`.

---
Instruções para utilizar os endpoints disponíveis:
O projeto já contém registros de pessoas e produtos previamente cadastradas.
	📁 Localização da carga:
	src/main/resources/db/migration/V5__CargaInicialDados.sql

### PESSOAS
👤 Cadastrar Pessoa
Endpoint: POST /api/v1/pessoas
**Requisição (JSON):**
{
  "cdDocPessoa": "12345678901",         // CPF válido (somente números)
  "nmPessoa": "João da Silva",          // Nome completo
  "tpPessoa": "CLIENTE",                // deve obrigatoriamente ser "CLIENTE".
  "dsEmail": "joao.silva@email.com"     // E-mail válido
}

🔍  Buscar Pessoa por CPF
Endpoint: GET /api/v1/pessoas/{cdDocPessoa}
**Requisição :**O CPF (cdDocPessoa) deve ter sido previamente cadastrado via API ou constar na carga inicial de dados.

# Preparando o ambiente para o K8s

1. Instalar o Docker Desktop

2. Instalar o WSL

3. Instalar o Ubuntu no WSL

4. Iniciar o Kubernetes no Docker Desktop

5. Acessar o terminal do Ubuntu, navegar até a pasta do projeto e verificar a instalação do kubectl:
   ```bash
   kubectl version
   kubectl cluster-info
6. Instalar o kind usando
   ```bash
   curl -Lo ./kind https://kind.sigs.k8s.io/dl/v0.29.0/kind-linux-amd64 
   chmod +x ./kind 
   sudo mv ./kind /usr/local/bin/kind

# Rodando o projeto com Kubernets
1. criar um cluster usando 
   ```bash
   kind create cluster --name fiap-fastfood --config ./k8s/cluster/kind-cluster.yaml
2. verificar se a criação ocorreu com sucesso usando 
   ```bash
   kubectl cluster-info --context kind-fiap-fastfood
3. instalar o metrics-server utilizando 
   ```bash
   kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
4. aplique cada um dos arquivos k8s
   ```bash
   kubectl apply -f ./k8s/configmap.yaml
   kubectl apply -f ./k8s/database/database-secrets.yaml
   kubectl apply -f ./k8s/database/database-service.yaml
   kubectl apply -f ./k8s/database/database-deployment.yaml
   kubectl apply -f ./k8s/application/application-secrets.yaml
   kubectl apply -f ./k8s/application/application-service.yaml
   kubectl apply -f ./k8s/application/application-deployment.yaml
   kubectl apply -f ./k8s/hpa/hpa.yaml
5. validar se a aplicação está de pé usando
   ````bash
   kubectl get services
   kubectl get pods
6. rodar
   ````bash
   kubectl port-forward service/app-service 30080:8080
## Desenho de Arquitetura

O arquido do desenho de arquitetura nova econtra-se no drive (https://drive.google.com/file/d/1lfFRoWELXDzc1qfbsgWVOna_1l8oJOjV/view?pli=1) e da arquitetura antiga (https://drive.google.com/file/d/1lfFRoWELXDzc1qfbsgWVOna_1l8oJOjV/view?usp=sharing)

## Vídeo
https://youtu.be/oA8CdS8MSWY
