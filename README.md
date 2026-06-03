# 🎬 ScreenMatch

Aplicação desenvolvida em Java com Spring Boot para consulta, tradução e persistência de informações sobre séries utilizando APIs externas.

O projeto consome dados da API OMDb, realiza o processamento das informações recebidas, traduz sinopses automaticamente e armazena os dados em um banco PostgreSQL.

---

## 🚀 Funcionalidades

* Buscar séries através da API OMDb
* Converter respostas JSON para objetos Java utilizando Jackson
* Traduzir sinopses automaticamente para português
* Persistir séries no banco de dados PostgreSQL
* Armazenar informações como:

    * Título
    * Ano de lançamento
    * Avaliação IMDb
    * Gênero
    * Atores
    * Poster
    * Sinopse traduzida
* Consultar temporadas e episódios
* Trabalhar com categorias utilizando Enum

---

## 🛠️ Tecnologias Utilizadas

### Backend

* Java
* Spring Boot
* Spring Data JPA
* Hibernate

### Banco de Dados

* PostgreSQL

### APIs Externas

* OMDb API
* MyMemory Translation API
* OpenAI API (implementação alternativa para tradução)

### Bibliotecas

* Jackson
* HttpClient (Java 11+)

---

## 📂 Estrutura do Projeto

```text
src
├── model
│   ├── Serie
│   ├── Episodio
│   ├── DadosSerie
│   ├── DadosTemporada
│   ├── DadosEpisodio
│   └── Categoria
│
├── repository
│   └── SerieRepository
│
├── service
│   ├── ConsumoApi
│   ├── ConverteDados
│   ├── ConsultaChatGPT
│   └── traducao
│       ├── ConsultaMyMemory
│       ├── DadosTraducao
│       └── DadosResposta
│
└── principal
```

---

## 🏗️ Conceitos Aplicados

* Programação Orientada a Objetos (POO)
* Injeção de Dependência
* Repository Pattern
* DTOs
* Records
* Generics
* Consumo de APIs REST
* Conversão JSON ↔ Objetos Java
* Persistência com JPA/Hibernate
* Variáveis de Ambiente
* Tratamento de Exceções
* Integração com serviços externos

---

## ⚙️ Configuração

### Variáveis de Ambiente

Configure as seguintes variáveis:

```env
DB_HOST=localhost
DB_NAME=screenmatch
DB_USER=postgres
DB_PASSWORD=sua_senha
OPENAI_APIKEY=sua_chave
```

### Banco de Dados

Certifique-se de que o PostgreSQL esteja em execução.

Exemplo de configuração:

```properties
spring.datasource.url=jdbc:postgresql://${DB_HOST}:5433/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
```

---

## ▶️ Executando o Projeto

Clone o repositório:

```bash
git clone https://github.com/seu-usuario/screenmatch.git
```

Acesse a pasta do projeto:

```bash
cd screenmatch
```

Execute a aplicação:

```bash
./mvnw spring-boot:run
```

Ou execute a classe principal pela IDE de sua preferência.

---

## 📚 Aprendizados

Durante o desenvolvimento deste projeto foram praticados conceitos fundamentais de desenvolvimento backend com Java, incluindo:

* Consumo de APIs REST
* Manipulação de JSON com Jackson
* Persistência de dados com JPA/Hibernate
* Integração com serviços externos
* Uso de Records e Generics
* Modelagem de entidades
* Configuração de variáveis de ambiente
* Integração com banco de dados PostgreSQL

---

## 🚧 Melhorias Futuras

* Transformar a aplicação em uma API REST
* Implementar Controllers
* Criar DTOs para requisições e respostas
* Adicionar paginação
* Implementar testes unitários com JUnit e Mockito
* Dockerizar a aplicação
* Adicionar autenticação com JWT
* Criar documentação com Swagger/OpenAPI

---

## 👨‍💻 Autor

**Hiuri Marques Rocha**

* GitHub: https://github.com/HiuriMR
* LinkedIn: https://linkedin.com/in/hiuri-rocha
