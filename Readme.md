# Course Management System

A Spring Boot REST API that manages Students, Courses, and Enrollments, supporting pagination, logging, Swagger documentation, global exception handling, and unit testing.


## Features

Student management (create, fetch)

Course management (create,fetch,count)

Student enrollment into courses

Fetch students enrolled in a specific course

Search enrollments by student name

Pagination support for all list APIs

Global exception handling

Centralized logging (SLF4J + Logback)

Swagger / OpenAPI documentation

Unit testing with JUnit 5 & Mockito

## Tech Stack


**Java:**
Java 17

**Spring Boot**
Spring Web MVC,
Spring Data JPA,
Spring Boot DevTools

**Dependencies:**
Lombok,
MySQL Connector,
Junit+Mockito(via Spring Starter),
Swagger (Springdoc OpenAPI)

**Database:**
MySQL

## Project Structure

```
com.youngsoft.assignment
│
├── controller        # REST controllers
├── service           # Business logic
├── repository        # JPA repositories
├── entity            # JPA entities
├── dto               # Request & Response DTOs
├── exception         # Custom & global exceptions
└── config            # Swagger & other configurations
```

## Installation & Run Steps

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL
- IDE (IntelliJ / Eclipse)

### Steps to Run
1. Extract the project ZIP
2. Open the project in IDE
3. Configure database in `application.properties` as mentioned in the Database setup part.
4. Build the project:
5. Run the application by running the main method or right click on the main application and run it.
6. Application will start on http://localhost:8080.
7. Now your tables will be created in the database.
## Database Setup
MySQL is used as the database for this project,you can also use H2.
#### Step 1:-Create a database named student_course in MySql
````
CREATE DATABASE student_course_db;
````
#### Step 2:-Inside appliction.properties file
````
spring.datasource.url=jdbc:mysql://localhost:3306/student_course
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
````
## Swagger API Documentation

After running the application, access Swagger UI:

http://localhost:8080/swagger-ui/index.html
## Global Exception Handling

- Implemented using `@RestControllerAdvice`
- Handles custom exceptions such as:
    - EmailAlreadyExistsException
    - Already Enrolled Exception
    - CourseNotFoundException
    - StudentNotFoundException
- Returns clean JSON error responses

## API Reference

### Student API's
#### 1) Create Student

| Method | Endpoint                   | Description                |
|:-------|:---------------------------| :------------------------- |
| `POST` | `/students/create_student` | Create new student with unique email.  |

#### 2) Get Student details along with enrolled courses

| Method | EndPoint                | Description |
|:-------|:------------------------|:------------|
| `GET`  | `/student/with_courses` |     Get Student details along with enrolled courses        |


### Course API's

#### 1) Create a new Course

| Method | EndPoint                 | Description         |
|:-------|:-------------------------|:--------------------|
| `POST` | `/courses/create_course` | Create a new Course |

#### 2) Get Course Details of the requested course

| Method                                | EndPoint                              | Description                                                         |
|:--------------------------------------|:--------------------------------------|:--------------------------------------------------------------------|
| `GET`                                 | `/courses/details/{title}`            | Get Course Details of the given title. **title** field is mandatory |

#### 3)Get Student Count of the specified course

| Method | EndPoint                             | Description                                   |
|:-------|:-------------------------------------|:----------------------------------------------|
| `GET`  | `/courses/course_count/{courseName}` | Get the count of students in the given course |


### Enrollment API's
#### 1) Create a new Enrollment

| Method | EndPoint             | Description                    |
|:-------|:---------------------|:-------------------------------|
| `POST` | `/enrollment/enroll` | Enroll a student into a course |

#### 2) Get all the available course details

| Method | EndPoint              | Description                          |
|:-------|:----------------------|:-------------------------------------|
| `GET`  | `/enrollment/get_all` | Get all the avaiable Courses details |

#### 3) Get all the student details enrolled for this course

| Method | EndPoint                                   | Description                                                  |
|:-------|:-------------------------------------------|:-------------------------------------------------------------|
| `GET`  | `/enrollments/get_by_course/{courseTitle}` | Return the enrolled students details of the specified course |

#### 4) Get all the courses in which the specified student has enrolled

| Method | EndPoint                                   | Description                                                  |
|:-------|:-------------------------------------------|:-------------------------------------------------------------|
| `GET`  | `/enrollments/get_by_course/{studentName}` | Return the enrolled students details of the specified course |



## Sample API calls
#### 1)Create STUDENT:
````
POST : /students/create

RequestBody:StudentRequest DTO
{
  "name": "Jack",
  "email": "jack@gmail.com"
}
````
#### 2)Create COURSE:
````
POST:  /courses/create_course

{
  "title": "Java",
  "description": "Java basics and fundamentals",
  "department": "Computer Science"
}
````
#### 3)Enroll Student into Course:
````
POST: /enrollments/enroll
{
  "studentId": 1,
  "courseId": 1
}
````
#### 4)Get Students Enrolled in a Course (Pagination)
````
GET: /enrollement/get_by_course/Database%20Management%20Systems?page=0&size=2
````
#### 5)Get Course details where student has enrolled
````
GET: enrollement/search/alpha?page=0&size=2
````
#### 6)Get Details of all the Courses
````
GET: /enrollement/get_all?page=0&size=10
````
#### 7)Get Students and their enrolled courses

````
GET : /student/with_courses?page=1&size=5
````