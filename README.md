# 🧑‍🎓 Student Management System (Spring Boot)

A simple CRUD (Create, Read, Update, Delete) REST API built with Spring Boot to manage student data. This system allows storing and retrieving student details with basic validation and email uniqueness check.

---

## 🚀 Features

- Add a new student
- Update student details
- View all students
- View a student by ID
- Delete a student
- Email uniqueness check
- In-memory or MySQL database support
- Spring Security (CSRF disabled for ease of use in development)

---

## 📦 Technologies Used

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security (lightweight)
- MySQL
- Maven

---

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/student/management/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── config/
│   └── resources/
│       ├── application.properties
├── test/
│   └── java/com/student/management/
│       └── service/
```

---

## ⚙️ Setup Instructions

### 🛠️ Prerequisites

- Java 17+
- Maven
- MySQL
- Postman or curl (for testing)

### 💾 Database Setup

1. Create a MySQL database:

```sql
CREATE DATABASE studentdb;
```

2. Update your `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 🧪 API Endpoints

### 🔸 Add Student (POST)

```http
POST /api/students
Content-Type: application/json

{
  "name": "Nageena Sulthana",
  "email": "nageena.sulthana@example.com",
  "course": "Math"
}
```

Returns: `201 Created` or `403 Forbidden` if email exists.

---

### 🔸 Update Student (PUT)

```http
PUT /api/students/{id}
```

---

### 🔸 Get All Students (GET)

```http
GET /api/students
```

---

### 🔸 Get Student by ID (GET)

```http
GET /api/students/{id}
```

---

### 🔸 Delete Student (DELETE)

```http
DELETE /api/students/{id}
```

---

## 🔒 Security

- CSRF disabled for easier API testing.
- Spring Security allows all requests to `/api/students/**`.

---

## 🧪 Unit Testing

- Unit tests available for `StudentService`.
- Run tests using:

```bash
mvn test
```