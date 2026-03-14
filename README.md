# User Mail Sender Microservice

> **WIP (Work In Progress)** - This project is currently under development

## Overview

A microservices system built with Java Spring Boot and RabbitMQ for handling asynchronous user email notifications. When a user registers, the User Service publishes an event to RabbitMQ, and the Email Service consumes that event and sends a welcome email via SMTP.

## Technologies

- **Java 17** + **Spring Boot 4.0.2**
- **RabbitMQ 3.13** - Asynchronous message broker (AMQP)
- **PostgreSQL 16** - Relational database (one per service)
- **Flyway** - Database schema migrations
- **Spring Mail** - SMTP email delivery
- **SpringDoc OpenAPI 3** - API documentation (Swagger UI)
- **Spring Actuator** - Health checks and metrics
- **Docker + Docker Compose** - Containerization
- **Lombok** - Boilerplate reduction
- **dotenv-java** - Environment variable loading

## Architecture

```
┌─────────────────┐         ┌──────────────┐         ┌─────────────────┐
│                 │         │              │         │                 │
│  User Service   │────────▶│   RabbitMQ   │────────▶│  Email Service  │
│                 │ Publish │   (Broker)   │ Consume │                 │
│  Port: 8080     │         │              │         │  Port: 8081     │
│                 │         │  Port: 5672  │         │                 │
└────────┬────────┘         └──────────────┘         └────────┬────────┘
         │                                                      │
         ▼                                                      ▼
┌─────────────────┐                                   ┌─────────────────┐
│   PostgreSQL    │                                   │   PostgreSQL    │
│   (userdb)      │                                   │   (emaildb)     │
│   Port: 5432    │                                   │   Port: 5433    │
└─────────────────┘                                   └─────────────────┘
```

### Message Flow

1. Client sends `POST /api/users` to User Service
2. User Service saves user to PostgreSQL and publishes a `UserCreatedDto` event to RabbitMQ
3. RabbitMQ routes the message via `email.exchange` → `email-queue`
4. Email Service consumes the event and sends a welcome email via SMTP
5. Email Service persists the email record and status to its own PostgreSQL database

### RabbitMQ Configuration

| Component    | Value            |
|--------------|------------------|
| Exchange     | `email.exchange` (Direct) |
| Queue        | `email-queue` (durable) |
| Routing Key  | `email.routingKey` |
| DLQ          | `email.send.dlq` |

### Message Format (`UserCreatedDto`)

```json
{
  "userId": "uuid",
  "name": "John Doe",
  "email": "john@example.com",
  "createdAt": "2026-01-31T10:00:00Z"
}
```

## Services

### User Service (`/user`)

- **Port:** 8080
- **Database:** `userdb` on port 5432
- **Responsibilities:** User registration, validation, and event publishing
- **Key classes:**
  - `UserController` — `POST /api/users`
  - `UserService` — business logic + triggers event
  - `UserProducer` — publishes `UserCreatedDto` to RabbitMQ
  - `UserModel` — JPA entity (`users` table)

### Email Service (`/email`)

- **Port:** 8081
- **Database:** `emaildb` on port 5433
- **Responsibilities:** Consuming user events and sending emails via SMTP
- **Key classes:**
  - `EmailConsumer` — `@RabbitListener` on `email-queue`
  - `EmailModel` — JPA entity (`emails` table)
  - `EmailStatus` — enum: `PENDING`, `SENDING`, `SENT`, `FAILED`, `RETRYING`

### Database Schema

**users table** (User Service)
```sql
id         UUID PRIMARY KEY
name       VARCHAR(255) NOT NULL
email      VARCHAR(255) NOT NULL UNIQUE
created_at TIMESTAMPTZ
updated_at TIMESTAMPTZ
```

**emails table** (Email Service)
```sql
id         UUID PRIMARY KEY
user_id    UUID
email_to   VARCHAR(255)
email_from VARCHAR(255)
subject    VARCHAR(255)
body       VARCHAR(600)
status     email_status (PENDING/SENDING/SENT/FAILED/RETRYING)
send_at    TIMESTAMP
```

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.9+
- Docker and Docker Compose

### Environment Setup

Each service has its own `.env` file for configuration isolation:

```bash
cp user/.env.example user/.env
cp email/.env.example email/.env
```

Edit each `.env` file with your settings. The email service requires valid SMTP credentials (Gmail, Outlook, SendGrid, etc.).

### Running with Docker Compose

Each service can be started independently:

```bash
# User Service (PostgreSQL + RabbitMQ + service)
cd user && docker-compose up --build

# Email Service (PostgreSQL + RabbitMQ + service)
cd email && docker-compose up --build
```

To start only the infrastructure (databases + RabbitMQ) and run the Java services from your IDE:

```bash
# User Service — infrastructure only
cd user && docker-compose up -d user-db rabbitmq

# Email Service — infrastructure only
cd email && docker-compose up -d email-db rabbitmq
```

Or use the convenience script:

```bash
./scripts/up-containers.sh
```

### Local Development (without Docker for the app)

```bash
# User Service
cd user && ./mvnw spring-boot:run

# Email Service
cd email && ./mvnw spring-boot:run
```

### Build

```bash
cd user  && ./mvnw clean package
cd email && ./mvnw clean package
```

## Service Endpoints

| Service | URL |
|---------|-----|
| User Service API | http://localhost:8080 |
| User Swagger UI | http://localhost:8080/swagger-ui.html |
| User Health | http://localhost:8080/actuator/health |
| Email Service API | http://localhost:8081 |
| Email Swagger UI | http://localhost:8081/swagger-ui.html |
| Email Health | http://localhost:8081/actuator/health |
| RabbitMQ Management | http://localhost:15672 (admin/admin123) |

## Design Patterns

- **Event-Driven Architecture** — asynchronous pub/sub via RabbitMQ
- **Database per Service** — independent databases for data isolation
- **Repository Pattern** — Spring Data JPA abstraction
- **Retry + DLQ** — failed messages retried, then routed to dead letter queue
- **DTO Mapping** — clean separation between API, domain, and event layers

## Security

- Non-root container users in Docker images
- Environment variables via dotenv (secrets excluded from version control)
- SMTP with TLS/STARTTLS
- Database credentials isolated per service

## License

To be specified
