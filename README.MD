# Task Manager API

A RESTful Task Management application built using **Java, Spring Boot, Spring Data JPA, PostgreSQL, and Spring Security with JWT authentication**.

The project demonstrates backend development concepts including REST API design, layered architecture, database persistence, authentication, authorization, exception handling, and Hibernate ORM.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot | Backend application framework |
| Spring Web | REST API development |
| Spring Data JPA | Database access and persistence |
| Hibernate | ORM and entity management |
| PostgreSQL | Relational database |
| Spring Security | Authentication and authorization |
| JWT (JSON Web Token) | Stateless authentication |
| BCrypt | Password hashing |
| Gradle | Dependency management and build automation |

## Features

- **User Authentication**
  - User registration and login
  - JWT-based authentication
  - BCrypt password hashing
  - Stateless session management

- **Task Management**
  - Create, retrieve, update, and delete tasks
  - RESTful API endpoints
  - Request and response DTOs

- **Database Management**
  - PostgreSQL integration
  - Spring Data JPA repositories
  - Hibernate entity mapping
  - One-to-Many, Many-to-One, One-to-One, and Many-to-Many relationship concepts

- **Backend Engineering**
  - Controller-Service-Repository architecture
  - Constructor-based dependency injection
  - Bean management through Spring IoC
  - Global exception handling
  - Request validation
  - Transaction management
  - Lazy loading and N+1 query considerations

## Project Architecture

The application follows a layered architecture to separate business logic, API handling, and database operations.

```text
src/main/java/com/example/taskmanager/
│
├── config/
│   ├── SecurityConfig.java
│   └── PasswordConfig.java
│
├── controller/
│   ├── TaskController.java
│   └── AuthController.java
│
├── dto/
│   ├── TaskRequestDTO.java
│   ├── TaskResponseDTO.java
│   ├── RegisterRequestDTO.java
│   ├── LoginRequestDTO.java
│   └── AuthResponseDTO.java
│
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── TaskNotFoundException.java
│
├── model/
│   ├── Task.java
│   ├── User.java
│   └── UserProfile.java
│
├── repository/
│   ├── TaskRepository.java
│   └── UserRepository.java
│
├── security/
│   └── JwtAuthenticationFilter.java
│
├── service/
│   ├── TaskService.java
│   ├── TaskServiceImpl.java
│   ├── AuthService.java
│   ├── JwtService.java
│   └── CustomUserDetailsService.java
│
└── TaskManagerApplication.java
```

## Authentication Flow

The application uses JWT tokens to secure protected API endpoints.

1. A user registers with their name, email, and password.
2. The password is hashed using BCrypt before being stored in PostgreSQL.
3. The user logs in with their credentials.
4. Spring Security's AuthenticationManager verifies the credentials.
5. A JWT is generated and returned to the client.
6. The client sends the JWT in the Authorization header for protected requests.
7. A custom JWT filter validates the token and establishes authentication in the SecurityContextHolder.
8. Spring Security checks authorization before allowing access to the controller.

### JWT Request Header

```http
Authorization: Bearer <your-jwt-token>
```

## API Endpoints

Base URL: `http://localhost:8080`

### Authentication

| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/api/auth/register` | Register a new user | Public |
| POST | `/api/auth/login` | Authenticate and receive JWT | Public |

### Tasks

| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/api/tasks` | Create a task | Authenticated |
| GET | `/api/tasks` | Retrieve tasks | Authenticated |
| GET | `/api/tasks/{id}` | Retrieve a task by ID | Authenticated |
| PUT | `/api/tasks/{id}` | Update a task | Authenticated |
| DELETE | `/api/tasks/{id}` | Delete a task | Authenticated |


## Getting Started

### Prerequisites

Install the following:

- Java 21
- PostgreSQL
- Git
- Postman (recommended for API testing)

### 1. Clone the repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd <YOUR_PROJECT_DIRECTORY>
```

### 2. Create the PostgreSQL database

Open PostgreSQL and execute:

```sql
CREATE DATABASE taskmanager;
```

### 3. Configure application properties

Update `src/main/resources/application.properties`:

```properties
spring.application.name=taskmanager

spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

jwt.secret=${JWT_SECRET}
jwt.expiration=3600000
```

Set the required environment variables before starting the application:

```bash
export DB_USERNAME=your_postgres_username
export DB_PASSWORD=your_postgres_password
export JWT_SECRET=your_base64_encoded_secret
```

Generate a secure Base64 JWT secret:

```bash
openssl rand -base64 32
```

### 4. Build the application

```bash
./gradlew clean build
```

### 5. Run the application

```bash
./gradlew bootRun
```

The application should start at:

```text
http://localhost:8080
```

## Testing the API with Postman

Follow this sequence:

**Step 1: Register**

```http
POST /api/auth/register
Content-Type: application/json
```

Example request:

```json
{
  "name": "John",
  "email": "john@example.com",
  "password": "password123"
}
```

**Step 2: Login**

```http
POST /api/auth/login
Content-Type: application/json
```

Example request:

```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

The response should contain a JWT:

```json
{
  "token": "<your-jwt-token>",
  "tokenType": "Bearer"
}
```

**Step 3: Access protected endpoints**

```http
GET /api/tasks
Authorization: Bearer <your-jwt-token>
```

A valid authenticated request should be allowed to reach the task API.

## Key Concepts Demonstrated

This project was built to practice and demonstrate important Spring Boot backend engineering concepts:

- Dependency Injection and Inversion of Control
- Spring Bean lifecycle and component scanning
- RESTful API development
- Spring Data JPA and Hibernate
- Entity relationships and ownership
- DTO pattern and request validation
- Global exception handling
- Transaction management and ACID properties
- Persistence context and dirty checking
- Lazy versus eager loading
- N+1 query problem
- Spring Security filter chain
- AuthenticationManager and UserDetailsService
- JWT generation, parsing, and validation
- SecurityContextHolder and stateless authentication

## Future Improvements

- Role-based authorization (USER and ADMIN)
- User-specific task ownership and access control
- Pagination and sorting
- API documentation with Swagger / OpenAPI
- Docker containerization
- CI/CD pipeline integration
- Cloud deployment on AWS

## Author

Built as a hands-on backend engineering project to strengthen practical knowledge of Java, Spring Boot, REST APIs, database management, and secure backend development.