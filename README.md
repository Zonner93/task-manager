# 📋 Task Manager API

Welcome to **Task Manager API** — a simple and efficient REST service to manage tasks and users!  
Built with **Spring Boot**, **Spring Data JPA**, and **PostgreSQL**.

---

## 🚀 Features

- CRUD operations for **Users** and **Tasks**
- Assign users to tasks
- Filter and sort users and tasks
- Validation and exception handling

---

## 🔥 Technologies

- Java 21
- Spring Boot 3.4.5
- Spring Data JPA
- PostgreSQL
- Maven
---
## 📚 API Endpoints

### Users

| Method | Endpoint        | Description                                              |
|--------|-----------------|----------------------------------------------------------|
| POST   | /users          | Create a new user                                        |
| GET    | /users/{id}     | Get user by ID                                           |
| GET    | /users          | Get list of users with filtering, sorting and pagination |
|GET     | /all            | Get all users                                            |
| DELETE | /users/{id}     | Delete user by ID                                        |

---

### Tasks

| Method | Endpoint                      | Description                                              |
|--------|-------------------------------|----------------------------------------------------------|
| POST   | /tasks                        | Create a new task                                        |
| GET    | /tasks/{id}                   | Get task by ID                                           |
| GET    | /tasks                        | Get list of tasks with filtering, sorting and pagination |
| PUT    | /tasks/{id}                   | Update task by ID                                        |
| DELETE | /tasks/{id}                   | Delete task by ID                                        |
| PATCH  | /tasks/{taskId}/assign/{userId} | Assign user to task                                      |
| PATCH  | /tasks/{id}/status            | Change task status                                       |
