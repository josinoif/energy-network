# Energy Network

- **Descrição do Projeto**:
    - Aplicação para gerenciar redes de energia.
    - Tecnologias utilizadas:
        - Backend: Java com Spring Boot.
        - Frontend: Vue.js.
        - Banco de dados gerenciado via Docker.

## Instruções para Iniciar o Projeto

- **Banco de Dados**:
    - Localização: Pasta `infra`.
    - Gerenciamento: Docker.
    - Passos:
        1. Navegar até a pasta `infra`:
             ```bash
             cd infra
             ```
        2. Iniciar o container:
             ```bash
             docker-compose up -d
             ```
    - Observação: O banco de dados já inicia com um usuário pré-configurado:
        - **Usuário**: `admin`
        - **Senha**: `admin`
- **Backend**:
    - Tecnologia: Java com Spring Boot.
    - Passos:
        1. Navegar até a pasta do backend:
            ```bash
            cd backend
            ```
        2. Rodar os testes automatizados:
            ```bash
            ./mvnw test
            ```
        3. Compilar e iniciar o servidor:
            ```bash
            ./mvnw spring-boot:run
            ```
    - Observação: O backend realiza autenticação baseada em token JWT, implementada utilizando Spring Security.

- **Frontend**:
    - Tecnologia: Vue.js.
    - Passos:
        1. Navegar até a pasta do frontend:
             ```bash
             cd frontend
             ```
        2. Instalar dependências:
             ```bash
             npm install
             ```
        3. Iniciar o servidor de desenvolvimento:
             ```bash
             npm run dev
             ```

- **Acessar a Aplicação**:
    - Frontend: `http://localhost:5174/`
    - Backend: `http://localhost:8080`
    - Documentação da API: `http://localhost:8080/swagger-ui/index.html`

## Funcionalidades Implementadas

- **Backend**:
    - Gerenciamento de Subestações:
        - Listar, criar, atualizar e excluir subestações.
    - Gerenciamento de Redes MT:
        - Listar, adicionar, atualizar e excluir redes de média tensão.
    - Autenticação e Autorização:
        - Login via JWT, validação de token e gerenciamento de usuários.
    - Documentação da API:
        - Swagger UI e especificação OpenAPI.
    - Segurança:
        - Spring Security com autenticação JWT e configuração de CORS.
    - Integração com Banco de Dados:
        - Persistência com JPA/Hibernate e migrações.
    - Tratamento de Erros:
        - Mensagens personalizadas e validação de dados.
    - Testes Automatizados:
        - Testes unitários e de integração.

- **Frontend**:
    - Gerenciamento de Subestações:
        - Listar, criar, editar e excluir subestações.
    - Gerenciamento de Redes MT:
        - Listar, adicionar, editar e excluir redes MT.
    - Autenticação e Autorização:
        - Login, validação de sessão e logout.
    - Integração com Backend:
        - Consumo de APIs, exibição de dados e mensagens de feedback.
    - Interface do Usuário:
        - Design responsivo, componentes Vue.js e navegação com Vue Router.
    - Tratamento de Erros

- **Pontos de melhoria:**
    - Implementação de testes para o frontend (testes de unidade e integração)
    - Melhoria no casos de tratamento de erro