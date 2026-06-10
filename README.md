# 🎬 ScreenMatch API

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen" />
  <img src="https://img.shields.io/badge/PostgreSQL-Database-blue" />
  <img src="https://img.shields.io/badge/JPA-Hibernate-yellow" />
  <img src="https://img.shields.io/badge/REST-API-red" />
</p>

API REST desenvolvida com **Java 17** e **Spring Boot** para consulta e gerenciamento de séries, temporadas e episódios.

A aplicação consome dados da API **OMDb**, realiza tradução automática de sinopses utilizando a API **MyMemory**, persiste os dados em banco PostgreSQL e disponibiliza endpoints REST para consumo por aplicações frontend.

---

# 📸 Demonstração

## Tela Inicial

![Lançamentos de Série](https://raw.githubusercontent.com/HiuriMR/screenmatch_2.0/main/docs/images/lancamentosSerie.png)

## Detalhes da Série

![Lançamentos de Série](https://github.com/HiuriMR/screenmatch_2.0/blob/main/docs/images/detalheSerie.png)



# 🚀 Tecnologias Utilizadas

## Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* Jackson

## APIs Externas

* OMDb API
* MyMemory Translation API

## Ferramentas

* Git
* GitHub
* IntelliJ IDEA / Eclipse

---

# 🏗️ Arquitetura

A aplicação segue o padrão de arquitetura em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

Além disso, utiliza DTOs para evitar exposição direta das entidades da aplicação.

---

# 📋 Funcionalidades

### Séries

✅ Listagem de todas as séries

✅ Consulta das séries mais bem avaliadas

✅ Consulta dos lançamentos mais recentes

✅ Busca por categoria

✅ Consulta de detalhes da série

### Episódios

✅ Listagem de todos os episódios

✅ Consulta por temporada

✅ Top 5 episódios mais bem avaliados

### Integrações

✅ Consumo da OMDb API

✅ Tradução automática de sinopses

✅ Persistência em PostgreSQL

---

# 🗄️ Modelagem

## Série

```java
Serie
```

| Campo           | Tipo      |
| --------------- | --------- |
| id              | Long      |
| titulo          | String    |
| totalTemporadas | Integer   |
| avaliacao       | Double    |
| genero          | Categoria |
| atores          | String    |
| poster          | String    |
| sinopse         | String    |

---

## Episódio

```java
Episodio
```

| Campo          | Tipo      |
| -------------- | --------- |
| id             | Long      |
| temporada      | Integer   |
| titulo         | String    |
| numeroEpisodio | Integer   |
| avaliacao      | Double    |
| dataLancamento | LocalDate |

---

## Relacionamento

```text
Serie (1)
    │
    │
    ▼
Episodio (N)
```

---

# 🔗 Endpoints

## Séries

| Método | Endpoint                      |
| ------ | ----------------------------- |
| GET    | /series                       |
| GET    | /series/top5                  |
| GET    | /series/lancamentos           |
| GET    | /series/{id}                  |
| GET    | /series/categoria/{categoria} |

---

## Episódios

| Método | Endpoint                         |
| ------ | -------------------------------- |
| GET    | /series/{id}/temporadas/todas    |
| GET    | /series/{id}/temporadas/{numero} |
| GET    | /series/{id}/temporadas/top      |

---

# ⚙️ Como Executar

## 1 - Clone o projeto

```bash
git clone https://github.com/SEU-USUARIO/screenmatch-api.git
```

---

## 2 - Entre na pasta

```bash
cd screenmatch-api
```

---

## 3 - Configure o PostgreSQL

Crie um banco:

```sql
CREATE DATABASE screenmatch;
```

Configure seu:

```properties
application.properties
```

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/screenmatch
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

---

## 4 - Execute

```bash
mvn spring-boot:run
```

ou

```bash
./mvnw spring-boot:run
```

---

# 📂 Estrutura do Projeto

```text
src
└── main
    ├── java
    │   └── br.com.alura.screenmatch
    │       ├── config
    │       ├── controller
    │       ├── dto
    │       ├── model
    │       ├── repository
    │       ├── service
    │       └── service.traducao
    │
    └── resources
```

---

# 🎯 Conceitos Aplicados

* Programação Orientada a Objetos
* Spring Boot
* Spring Data JPA
* Hibernate
* DTO Pattern
* Consumo de APIs REST
* Relacionamentos JPA
* JPQL
* Records (Java 17)
* Stream API
* Persistência com PostgreSQL
* Arquitetura em Camadas

---

# 🔮 Melhorias Futuras

* [ ] Swagger/OpenAPI
* [ ] Docker
* [ ] Spring Security + JWT
* [ ] Testes Unitários (JUnit e Mockito)
* [ ] Tratamento Global de Exceções
* [ ] Deploy em Cloud

---

# 🌐 Frontend

O frontend da aplicação encontra-se em um repositório separado:

```text
screenmatch-front
```

Responsável por consumir os endpoints da API e apresentar as informações ao usuário.

---

# 👨‍💻 Autor

**Hiuri**

Desenvolvedor Java

LinkedIn: SEU_LINKEDIN

GitHub: https://github.com/SEU_USUARIO
