# lode-manutencoes

Sistema de gestão de equipamentos para manutenção de poços artesianos.

## Tecnologias

- **Backend:** Java 21 + Spring Boot + Spring Data JPA + H2
- **Frontend:** Vue 3 + Vite + Vue Router

## Pré-requisitos

- Java 21+
- Node.js 18+
- Maven (ou use o `./mvnw` incluído no projeto)

## Executando o projeto

O backend e o frontend precisam rodar simultaneamente, cada um em um terminal separado.

### Backend (Spring Boot)

```bash
./mvnw spring-boot:run
```

Disponível em `http://localhost:8080/api`

### Frontend (Vue)

```bash
cd frontend
npm install   # apenas na primeira vez
npm run dev
```

Disponível em `http://localhost:5173`

## Banco de dados

O projeto utiliza Spring Data JPA para abstrair ações de acesso e persistência em bancos de dados, permitindo assim
o desacoplamento de um banco de dados específico.

Por hora ele usa o banco de dados H2 em memória — nenhuma instalação necessária. Os dados são resetados a cada reinicialização do backend.

As configurações padrão são as seguintes:


| Campo    | Valor                      |
|----------|----------------------------|
| JDBC URL | `jdbc:h2:mem:equipamentosdb` |
| Username | `sa`                       |
| Password | *(vazio)*                  |


## Endpoints principais

| Método | Rota                                                | Descrição       |
|--------|-----------------------------------------------------|-----------------|
| GET    | `/api/equipamentos`                                 | Lista todos     |
| POST   | `/api/equipamentos`                                 | Cria novo       |
| PUT    | `/api/equipamentos/{id}`                            | Atualiza        |
| PATCH  | `/api/equipamentos/{id}/status?status={novostatus}` | Atualiza status |
| DELETE | `/api/equipamentos/{id}`                            | Remove          |

### Status válidos

| Valor                  | Descrição           |
|------------------------|---------------------|
| `OPERACIONAL`          | Operacional         |
| `MANUTENCAO_NECESSARIA`| Manutenção necessária |
| `CRITICO`              | Crítico             |
