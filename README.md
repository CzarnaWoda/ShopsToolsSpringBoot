# 🧐 Tools and Shops API
### Dokumentacja API do zarządzania sklepami i narzędziami

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1-green)
![Java](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/badge/Maven-3.8-orange)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-316192)
![Redis](https://img.shields.io/badge/Cache-Redis-red)
---

## 📈 **Opis projektu**
  * Projekt został stworzony w celu użycia testów jednostkowych oraz stworzenia automatycznej dokumentacji za pomocą [OpenAPI](https://springdoc.org/)

**Tools and Shops API** to RESTful API do zarządzania sklepami i narzędziami, umożliwiające:
- 📦 **Dodawanie** nowych sklepów i narzędzi
- 🔄 **Aktualizację** istniejących zasobów
- 🔍 **Pobieranie** informacji o narzędziach i sklepach
- ❌ **Usuwanie** sklepów i narzędzi

API wykorzystuje **Spring Boot, Spring Data JPA, PostgreSQL, Redis do cache, Hibernate oraz OpenAPI 3.1 (Swagger UI).**

---

## 🚀 **Instalacja i konfiguracja**
### 1️⃣ **Wymagania**
- Java 17+
- Maven 3.8+
- PostgreSQL 14+ (lub H2 dla testów)
- Redis 

### 2️⃣ **Klonowanie repozytorium**
```bash
git clone https://github.com/CzarnaWoda/ShopsToolsSpringBoot
cd ShopsToolsSpringBoot
```

### 3️⃣ **Konfiguracja bazy danych**
Edytuj `src/main/resources/application.properties`:

```properties
#Database configuration
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=Użytkownik
spring.datasource.password=Hasło
spring.jpa.generate-ddl=true
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop

```

Jeśli chcesz używać **H2 w testach**, dodaj:
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
```

### 4️⃣ **Uruchomienie aplikacji**
#### 💻 **Bezpośrednio z Mavena**
```bash
mvn spring-boot:run
```

#### 🐥 **Uruchomienie z Dockerem (opcjonalnie)**
Jeśli masz Docker, możesz uruchomić aplikację w kontenerze PostgreSQL:
```bash
docker-compose up -d
```
**Plik `docker-compose.yml`:**
```yaml
version: '3.1'
services:
  db:
    image: postgres:14
    restart: always
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: yourpassword
      POSTGRES_DB: tools_db
    ports:
      - "5432:5432"
```

---

## 📖 **API Reference**
### 🔹 **Swagger UI**
Po uruchomieniu aplikacji możesz zobaczyć pełną dokumentację API w **Swagger UI**:  
🔗 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### 🔹 **OpenAPI JSON**
🔗 [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 📈 **Endpoints**

### 🔹 **1. Shops API**
| Metoda | Endpoint | Opis |
|--------|---------|------|
| `POST` | `/api/v1/shop/create` | Tworzy nowy sklep |
| `PUT` | `/api/v1/shop/update/{id}` | Aktualizuje dane sklepu |
| `GET` | `/api/v1/shop/id/{id}` | Pobiera sklep po ID |
| `GET` | `/api/v1/shop/name/{name}` | Pobiera sklep po nazwie |
| `GET` | `/api/v1/shop/shops?page=0&size=10&sortBy=id&sortDir=asc` | Pobiera listę sklepów (paginacja) |
| `DELETE` | `/api/v1/shop/delete/{id}` | Usuwa sklep |

### 🔹 **2. Tools API**
| Metoda | Endpoint | Opis |
|--------|---------|------|
| `POST` | `/api/v1/tool/create` | Tworzy nowe narzędzie |
| `PUT` | `/api/v1/tool/update/{id}` | Aktualizuje narzędzie po ID |
| `GET` | `/api/v1/tool/id/{id}` | Pobiera narzędzie po ID |
| `GET` | `/api/v1/tool/name/{name}` | Pobiera narzędzie po nazwie |
| `GET` | `/api/v1/tool/shop/{shopId}` | Pobiera narzędzia dla danego sklepu |
| `DELETE` | `/api/v1/tool/delete/{id}` | Usuwa narzędzie |

---

## 📈 **Przykłady requestów**
### 1️⃣ **Tworzenie nowego sklepu**
#### 🟢 **Request (POST `/api/v1/shop/create`)**
```json
{
  "name": "Super Tool Store",
  "email": "contact@toolstore.com"
}
```
#### 🟢 **Response (201 CREATED)**
```json
{
  "timeStamp": "2024-02-11T12:34:56",
  "status": "201 CREATED",
  "statusCode": 201,
  "message": "Shop has been created",
  "data": {
    "shop": {
      "id": 1,
      "name": "Super Tool Store",
      "email": "contact@toolstore.com"
    }
  }
}
```

---

## 🔧 **Autorzy**
- **[CzarnaWoda](https://github.com/CzarnaWoda)**


