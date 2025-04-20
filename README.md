#  Sistema de Cadastro de Unidades de Atendimento

Este projeto faz parte do *Tech Challenge - Hackaton Fase 5*, nele foi construido um Sistema de Cadastro de Unidades de Atendimento com sua construção baseada na estrutura *MVC*, utilizando tecnologias modernas como *Java*, *Spring Boot*, *Spring Security* e *Docker*, com foco na usabilidade e na escalabilidade. O sistema permite a gestão de cadastro de unidades de atendimento.

## Tecnologias Utilizadas


- **Java 17**: Versão de linguagem utilizada.
- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **Spring Security**: Fornece autenticação, autorização e outros recursos de segurança.
- **JWT (JSON Web Token)**: Utilizado para autenticação baseada em tokens, garantindo segurança e controle de acesso nas requisições.
- **Swagger**: Para documentação e testes das APIs.
- **JUnit, AssertJ**: Para criação de testes unitários e integrados.

## Instruções para Acesso à Aplicação

A aplicação se encontra disponível no seguinte endereço:

URL LOCAL: [[http://localhost:9090/swagger-ui.html](http://localhost:9090/swagger-ui.html)]

## Para executar a aplicação via Docker, siga os comandos abaixo:

1. **Faça login no Docker:**
   ```bash
   docker login
    ```
2. **Execute o seguinte comando para subir o serviço em um ambiente Docker:**
     ```bash
    docker compose up -d
    ```
## Instruções para Execução dos Testes

- Comando para execução dos **Testes Unitários**:
   ```bash
    mvn test
    ```
- Comando para execução dos **Testes Integrados**:
   ```bash
    mvn test -P integration-test
    ```

## Documentação da API

A documentação da API é gerada automaticamente pelo Swagger. Você pode acessá-la inserindo /swagger-ui/index.html ao final da url ou no seguinte endereço após iniciar a aplicação localmente:

URL LOCAL: [[http://localhost:9090/swagger-ui.html](http://localhost:9090/swagger-ui.html)]

Consulte a documentação do Swagger UI para ver todos os endpoints disponíveis e detalhes sobre cada um deles.

## Banco de Dados

A aplicação se conecta a um banco de dados MariaDB, um banco de dados relacional, utilizado para armazenar e organizar todas os dados da aplicação.
