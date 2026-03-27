**SmartTravel 🌍**

Production-ready full-stack Java Spring Boot application exploring 50+ Indian cities with culture, food, hidden gems, and personalized travel recommendations. Currently, 12 cities are preloaded, and more can be added via the admin panel.

**✨ Features**
JWT Authentication — Register, login, BCrypt hashed passwords, role-based access (USER / ADMIN)
Favourites System — Add/remove cities per user session, fully private and isolated
City Categories — Mountains, Beaches, Heritage, Religious, Food & Street, Adventure, Party, Hidden Gems
Hidden Gems — Discover unexplored Indian places filtered by isHiddenGem flag
REST APIs — Pagination, sorting, fuzzy search by name/state/country
Swagger UI — Auto-generated interactive API docs with JWT auth support
Responsive UI — Dark/Light mode, real-time data with Fetch API
Testing — 10 JUnit + Mockito tests, 0 failures, H2 in-memory for test environment

**🛠 Tech Stack**
Layer	Technology
Backend	Java 17, Spring Boot 2.7.18
Security	Spring Security, JWT (jjwt 0.11.5), BCrypt
Database	MySQL 8.0 (production), H2 (test)
ORM	Spring Data JPA, Hibernate
Frontend	HTML5, CSS3, Vanilla JavaScript, Fetch API
API Docs	Swagger UI (springdoc-openapi 1.7.0)
Testing	JUnit 5, Mockito, MockMvc
Build	Maven

**🚀 Quick Start**
Prerequisites
Java 17+
Maven 3.8+
MySQL 8.0+
Run locally
git clone https://github.com/mppurswani/smarttravel.git
cd smarttravel

Update src/main/resources/application.properties with your database credentials:

spring.datasource.url=jdbc:mysql://localhost:3306/smarttravel
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

**Run the project:**

mvn clean install
mvn spring-boot:run
Service	URL
Frontend	http://localhost:8080

API Base	http://localhost:8080/api

Swagger UI	http://localhost:8080/swagger-ui.html
📋 API Endpoints
Auth (Public)
Method	Endpoint	Description
POST	/api/auth/register	Create account, returns JWT
POST	/api/auth/login	Login, returns JWT
Cities (Public)
Method	Endpoint	Description
GET	/api/cities/all	Get all cities
GET	/api/cities?page=0&size=10	Paginated cities
GET	/api/cities/{id}	Get city by ID
GET	/api/cities/search?name=Delhi	Fuzzy search
GET	/api/cities/category/BEACHES	Filter by category
GET	/api/cities/hidden-gems	Unexplored places
Cities (ADMIN only)
Method	Endpoint	Description
POST	/api/cities	Add new city
DELETE	/api/cities/{id}	Delete city
Favourites (Login required)
Method	Endpoint	Description
GET	/api/favourites	Get my favourites
POST	/api/favourites/{cityId}	Add to favourites
DELETE	/api/favourites/{cityId}	Remove from favourites


 **Architecture**
Controller → Service → Repository (JPA) → MySQL
     ↓
JwtAuthenticationFilter → SecurityConfig → Role-based access
     ↓
GlobalExceptionHandler → ResourceNotFoundException → Proper HTTP codes
     ↓
CorsConfig → Frontend integration
Package Structure
com.travel.smarttravel/
├── config/          — SecurityConfig, SwaggerConfig, CorsConfig
├── controller/      — CityController, AuthController, FavouriteController
├── dto/             — CityDTO, AuthRequest, AuthResponse
├── entity/          — City, User, FavouriteCity, CityCategory (enum)
├── exception/       — ResourceNotFoundException
├── repository/      — CityRepository, UserRepository, FavouriteCityRepository
├── security/        — JwtUtil, JwtAuthenticationFilter, UserDetailsServiceImpl
└── service/         — CityService, FavouriteService, impl/CityServiceImpl
🗺 City Categories
Category	Emoji	Examples
MOUNTAINS	⛰	Chandigarh
BEACHES	🏖	Chennai, Kochi
HERITAGE	🏛	Delhi, Kolkata, Jaipur, Ahmedabad
RELIGIOUS	🛕	Varanasi
FOOD_STREET	🍜	Hyderabad, Mumbai
PARTY	🎉	Bengaluru, Pune
HIDDEN_GEM	💎	Currently none — admin can add

Note: 12 cities preloaded; system supports 50+ cities via admin panel.

🔐 **Security**_
Passwords hashed with BCrypt — never stored as plain text
JWT tokens expire after 24 hours
Role-based access — only ADMIN can add/delete cities
Session isolation — each user's favourites are completely private
All sensitive endpoints protected — 401/403 returned without valid token

 **Testing**
mvn test
Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
├── CityControllerTest  — 4 tests (MockMvc integration)
├── CityServiceTest     — 5 tests (Mockito unit tests)
└── SmartTravelApplicationTests — 1 test (context load)

** Performance**
Pagination: ~50ms average response on 12-city dataset
Fuzzy search: MySQL LIKE with Spring Data JPA derived queries
JWT validation: stateless — no DB hit per request
Memory: <200MB heap usage
👨‍💻 Author

Mayank Purswani

GitHub: @mppurswani
Email: mayankhero2004@gmail.com
LinkedIn: Mayank Purswani
