# Smart Travel Explorer

🌏 **Smart Travel Explorer** is a Java Spring Boot web application to explore Indian cities with details on culture, food, and tourist spots. It includes REST APIs, search, and pagination features.

## Features
- REST APIs for city management
- Pagination and sorting
- Search cities by name, state, or country
- Frontend using HTML, CSS, and JavaScript
- Dynamic rendering of city cards

## Technology Stack
- **Backend:** Java 17, Spring Boot 2.7.18, Spring Data JPA, Maven  
- **Frontend:** HTML, CSS, JavaScript  
- **Database:** H2 / MySQL  
- **Version Control:** Git, GitHub  

## Setup Instructions
1. Clone the repository: `git clone https://github.com/mppurswani/smarttravel.git`
2. Navigate to project directory: `cd smarttravel`
3. Build the project: `mvn clean install`
4. Run the application: `mvn spring-boot:run`
5. Open `index.html` in a browser for the frontend.

## API Endpoints
### Get All Cities (Paginated)
`GET http://localhost:8080/api/cities?page=0&size=5&sortBy=name&sortDir=asc`

### Get City By ID
`GET http://localhost:8080/api/cities/{id}`

### Search Cities
`GET http://localhost:8080/api/cities/search?name=Delhi`  
`GET http://localhost:8080/api/cities/search?state=Karnataka`  
`GET http://localhost:8080/api/cities/search?country=India`

## Frontend Usage
- Use search box to filter cities.
- Click **Show All Cities** to view all cities.
- Each city card shows: Name, State, Country, Culture, Tourist Spots, Food

## Exception Handling
- `ResourceNotFoundException` is returned if a city is not found.
- Global exception handling via `GlobalExceptionHandler`.

## License
MIT License © 2025 Mayank Purswani
