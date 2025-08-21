# Bookstore_Spring Evolution Project
## Author: Olga Balash

## Project Description
This project demonstrates the architectural evolution of a Bookstore application:
1. **Initial version**: Pure Jakarta EE (Servlets + JSP)
2. **Spring Migration**: Transition to Spring Framework (Spring MVC, JDBC Template)
3. **Modern version**: Spring Boot with Hibernate and security

Key evolutionary improvements:
- Implemented authentication/authorization
- Added shopping cart functionality
- Migrated from raw JDBC to Hibernate ORM
- Adopted Spring Boot conventions

## Technology Stack Evolution

| Version         | Key Technologies                         | Database Access     |
|-----------------|------------------------------------------|---------------------|
| Initial         | Jakarta EE, Servlets, JSP                | Raw JDBC            |
| Spring          | Spring MVC, JDBC Template                | JDBC Template       |
| Spring Boot     | Spring Boot, Hibernate, Spring Security  | Hibernate JPA       |

## Current Stack (Spring Boot)

| Component       | Technology                         | Port  |
|-----------------|------------------------------------|-------|
| Language        | Java 21                            | -     |
| Framework       | Spring Boot                        | -     |
| Database        | PostgreSQL                         | 5432  |
| ORM             | Hibernate                          | -     |
| Build Tool      | Apache Maven 3.9.6                 | -     |
| Logging         | Log4j                              | -     |

## Application Access
- **Running Port**: 8080 (default Spring Boot port)
- **Base URL**: `http://localhost:8080/home`

## Enhanced Features

### Core Functionality
- User authentication (login/logout)
- Role-based authorization (USER/ADMIN)
- Shopping cart system
- Order processing workflow


### API Evolution

| Feature          | Initial Version              | Current Version                |
|------------------|------------------------------|--------------------------------|
| Routing          | `?command=` parameters       | `@GetMapping` annotations      |
| DB Access        | Manual JDBC                  | Hibernate Repository           |

## Installation Guide
### 1.Database setup.
Create database using parameters from:
`src/main/resources/application.properties`


### 2. Clone repository
```cmd
git clone https://github.com/BalashOlga/bookstore_spring_balash

cd \path\to\bookstore_spring_balash(this project)
mvn clean package
mvn spring-boot:run
