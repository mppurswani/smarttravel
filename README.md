# SmartTravel 🌍

A production-style Spring Boot backend system for travel discovery and city exploration, designed with focus on scalability, caching optimization, secure authentication, and clean backend architecture.

This project demonstrates real-world backend engineering practices including JWT-based security, Redis caching, dynamic filtering with JPA Specifications, pagination, and layered system design.

---

## 🚀 System Highlights 
-⚡ **Redis Caching Layer** for high-frequency city queries (reduced DB load + faster response time)

-🔐 **JWT-based Stateless Authentication** with role-based access control

-🔍 **Dynamic Filtering System** using JPA Specifications (keyword + category + country)

-📄 **Paginated API Design** for scalable data handling

-🧠 **DTO-based Clean Architecture** (no entity exposure)

-🧹 **Cache Invalidation Strategy** on create/update/delete operations

-🧪  **Unit Testing Layer** using JUnit 5 + Mockito

-🧱 **Layered architecture** (Controller → Service → Repository) following backend best practices


--- 

## ✨ Features
### 🔐 Authentication & Security
- JWT authentication system
  
- BCrypt password encryption
  
- Role-based access control (ROLE_USER, ROLE_ADMIN)
  
- Stateless session management (no server-side sessions)
  

--- 

### 🌍 City Discovery Engine
- Browse cities with rich metadata (culture, food, attractions, etc.)
  
- Advanced filtering:
  
  - Keyword search
    
  - Category filter
    
  - Country filter

    
- Case-insensitive search across multiple fields
  
- Paginated responses for scalable API performance
  
---



### ⚡ Redis Caching (Performance Optimization Layer)
- Redis caching for frequently accessed city queries
  
- Cached paginated + filtered API responses
  
- Reduced database load on repeated requests
  
- Cache eviction strategy:
  
  - Add city
    
  - Update city
    
  - Delete city
    

---

## ⭐ Favorites System
- Users can save/remove favorite cities
  
- Fully user-isolated data using JWT identity
  
- Secure access control per user
  

---

## 🛠 Tech Stack
 | Layer | Technology |
|------|-----------|
| Backend | Java 17, Spring Boot 2.7.18 |
| Security | Spring Security, JWT, BCrypt |
| Database | MySQL 8 |
| Cache | Redis |
| ORM | Spring Data JPA, Hibernate |
| Testing | JUnit 5, Mockito |
| Build Tool | Maven |

---

## System Flow
```text
Frontend → Controller → Service → Repository → MySQL
                     ↓
               Redis Cache Layer
                     ↓
              JWT Security Filter
```

## Quick Start

## Clone Repository
git clone https://github.com/mppurswani/smarttravel.git

cd smarttravel

## Configure Databases
spring.datasource.url=jdbc:mysql://localhost:3306/smarttravel
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

## Run Application
mvn clean install

mvn spring-boot:run

## API ENDPOINTS

## AUTH APIs
| Method | Endpoint           | Description       |
| ------ | ------------------ | ----------------- |
| POST   | /api/auth/register | Register user     |
| POST   | /api/auth/login    | Login + JWT token |

## CITY APIs
| Method | Endpoint                       | Description            |
| ------ | ------------------------------ | ---------------------- |
| GET    | /api/cities                    | Paginated city listing |
| GET    | /api/cities/{id}               | Get city by ID         |
| GET    | /api/cities?keyword=delhi      | Search cities          |
| GET    | /api/cities?category=ADVENTURE | Filter by category     |



## FAVOURITE APIs
| Method | Endpoint                 | Description         |
| ------ | ------------------------ | ------------------- |
| GET    | /api/favourites          | Get user favourites |
| POST   | /api/favourites/{cityId} | Add favourite       |
| DELETE | /api/favourites/{cityId} | Remove favourite    |


## Security Flow
Client → JWT Token → JwtAuthenticationFilter
       → Token Validation
       → Security Context Setup
       → Role-based Access Control

       
## ⚡ Performance Optimizations
- Redis caching reduces repeated DB queries
  
- Pagination prevents large payload overhead
  
- Indexed search queries improve filtering speed
  
- Stateless authentication improves scalability

---

## Testing
mvn test

JUnit 5 unit tests
Mockito-based service testing
Spring Boot context validation tests

All tests passing


## 🐳 Docker Support
docker build -t smarttravel .
docker run -p 8080:8080 smarttravel


## Author

Mayank Purswani






