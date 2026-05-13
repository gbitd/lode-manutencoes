# lode-manutencoes

Sistema de gestão de equipamentos para manutenção de poços artesianos.

## Tecnologias

- **Backend:** Java 21 + Spring Boot + Spring Data JPA + H2
- **Frontend:** Vue 3 + Vite + Vue Router

## Pré-requisitos
 
- Java 21+
- Node.js 18+
- Docker e Docker Compose
- Maven (ou use o `./mvnw` incluído no projeto)

## Executando o projeto
 
O banco, o backend e o frontend precisam rodar simultaneamente.
 
### Banco de dados (Docker)
 
```bash
docker compose up -d
```

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
 
Atualmente o projeto usa PostgreSQL via Docker. As credenciais padrão estão no `docker-compose.yml` e no `application.properties`:
 
| Campo    | Valor                |
|----------|----------------------|
| Host     | `localhost:5432`     |
| Database | `lode_manutencoes`   |
| Username | `lode`               |
| Password | `lode123`            |
 
Os dados persistem entre reinicializações graças ao volume `lode_pgdata`. Para apagar tudo:
 
```bash
docker compose down -v
```


## Endpoints principais

| Método | Rota                                                | Descrição       |
|--------|-----------------------------------------------------|-----------------|
| GET    | `/api/equipamentos`                                 | Lista todos     |
| GET    | `/api/equipamentos?page={pagina}&size={tamanho}`    | Lista paginada  |
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
