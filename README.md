# Energy Network

Este projeto é uma aplicação para gerenciar redes de energia, composta por um backend em Java com Spring Boot, um frontend em Vue.js e um banco de dados gerenciado via Docker.

## Instruções para iniciar o projeto

### 1. Iniciar o banco de dados
O banco de dados está localizado na pasta `infra` e é gerenciado via Docker.

1. Navegue até a pasta `infra`:
    ```bash
    cd infra
    ```
2. Inicie o container do banco de dados:
    ```bash
    docker-compose up -d
    ```

### 2. Iniciar o backend
O backend é desenvolvido em Java com Spring Boot.

1. Navegue até a pasta do backend:
    ```bash
    cd backend
    ```
2. Compile e inicie o servidor:
    ```bash
    ./mvnw spring-boot:run
    ```

### 3. Iniciar o frontend
O frontend é desenvolvido em Vue.js.

1. Navegue até a pasta do frontend:
    ```bash
    cd frontend
    ```
2. Instale as dependências:
    ```bash
    npm install
    ```
3. Inicie o servidor de desenvolvimento:
    ```bash
    npm run dev
    ```

### 4. Acessar a aplicação
- O frontend estará disponível em: `http://localhost:5174/`
- O backend estará disponível em: `http://localhost:8080`
- Certifique-se de que o banco de dados está em execução antes de iniciar o backend.
