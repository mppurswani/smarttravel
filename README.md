# SmartTravel – City Exploration REST API

SmartTravel is a Spring Boot–based REST API that allows users to search
for cities and explore their culture, traditional food, and popular
tourist attractions.

This project follows a clean layered architecture and demonstrates
real-world backend development practices.

## Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- REST APIs
- Maven
- H2 / MySQL (configurable)

## Features
- Search cities by name
- RESTful API design
- DTO-based architecture
- Global exception handling
- Clean service–repository structure

## API Endpoints
- `GET /api/cities` – Fetch all cities
- `GET /api/cities/{name}` – Search city by name

## Project Structure
- controller – Handles API requests
- service – Business logic
- repository – Database operations
- dto – Data Transfer Objects
- exception – Global exception handling

## How to Run the Project
1. Clone the repository  
2. Navigate to the project directory  
3. Run the application using:

   `mvn spring-boot:run`

4. API will start at `http://localhost:8080`

## Future Enhancements
- Pagination and sorting
- Swagger API documentation
- MySQL integration
- Authentication and authorization

---

Developed by **Mayank Purswani**
