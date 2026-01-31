# User Mail Sender Microservice

> **🚧 WIP (Work In Progress)** - This project is currently under development

## Overview

This is a microservice built with Java Spring and RabbitMQ for handling user mail sending operations through messaging and queues.

## Technologies

- **Java Spring** - Core framework for the microservice
- **RabbitMQ** - Message broker for handling asynchronous messaging and queue management

## Description

This microservice handles user mail sending operations using a message-driven architecture. It leverages RabbitMQ for reliable message queuing and asynchronous processing, enabling scalable and decoupled communication between services.

## Services

### Email Service
- Handles email composition and delivery
- Processes email sending requests asynchronously from the message queue
- Manages email templates and formatting
- Provides email delivery status tracking

### User Service
- Manages user information and preferences
- Retrieves user data for email personalization
- Handles user notification preferences
- Integrates with the email service for user-specific communications

## Architecture

### Overview

This project implements a **microservices architecture** using event-driven communication patterns. The system is designed for scalability, reliability, and loose coupling between services.

```
┌─────────────────┐         ┌──────────────┐         ┌─────────────────┐
│                 │         │              │         │                 │
│  User Service   │────────▶│   RabbitMQ   │────────▶│  Email Service  │
│                 │ Publish │   (Broker)   │ Consume │                 │
│  Port: 8080     │         │              │         │  Port: 8081     │
│                 │         │  Port: 5672  │         │                 │
└────────┬────────┘         └──────────────┘         └────────┬────────┘
         │                                                      │
         │                                                      │
         ▼                                                      ▼
┌─────────────────┐                                   ┌─────────────────┐
│   PostgreSQL    │                                   │   PostgreSQL    │
│   (User DB)     │                                   │   (Email DB)    │
│   Port: 5432    │                                   │   Port: 5433    │
└─────────────────┘                                   └─────────────────┘
```

### System Components

#### 1. User Service
- **Responsibility**: Manages user data and publishes email notification events
- **Database**: PostgreSQL (userdb)
- **Port**: 8080
- **Key Features**:
  - User CRUD operations
  - User preference management
  - Event publishing to RabbitMQ
  - RESTful API endpoints
  - Data validation with Bean Validation

#### 2. Email Service
- **Responsibility**: Consumes email events and sends emails via SMTP
- **Database**: PostgreSQL (emaildb)
- **Port**: 8081
- **Key Features**:
  - Message consumption from RabbitMQ
  - Email template processing
  - SMTP integration (Gmail, SendGrid, etc.)
  - Email delivery tracking and status
  - Retry mechanism for failed emails
  - Dead Letter Queue (DLQ) handling

#### 3. RabbitMQ Message Broker
- **Responsibility**: Asynchronous message routing between services
- **Ports**: 5672 (AMQP), 15672 (Management UI)
- **Components**:
  - **Exchange**: `email.exchange` (Topic/Direct)
  - **Queue**: `email.send` (Main queue)
  - **DLQ**: `email.send.dlq` (Dead Letter Queue)
  - **Routing Key**: `email.send.key`

#### 4. PostgreSQL Databases
- **User Database**: Stores user information and preferences
- **Email Database**: Stores email history, templates, and delivery status
- **Migration**: Flyway for version-controlled schema management

### Communication Patterns

#### Event-Driven Communication
- **Pattern**: Publisher-Subscriber (Pub/Sub)
- **Protocol**: AMQP (Advanced Message Queuing Protocol)
- **Message Format**: JSON

#### Flow Example:
1. User Service receives an API request to send a notification
2. User Service publishes an event to RabbitMQ with user details and email content
3. RabbitMQ routes the message to the `email.send` queue
4. Email Service consumes the message from the queue
5. Email Service processes and sends the email via SMTP
6. Email Service updates the delivery status in its database
7. If sending fails, message is retried or sent to DLQ

### Technology Stack Details

#### Backend Framework
- **Spring Boot 4.0.2**: Modern Java framework with auto-configuration
- **Spring MVC**: RESTful API development
- **Spring Data JPA**: Database access and ORM
- **Spring AMQP**: RabbitMQ integration
- **Spring Mail**: Email sending capabilities

#### Data Layer
- **PostgreSQL 16**: Relational database for persistent storage
- **Hibernate**: ORM implementation for JPA
- **Flyway**: Database migration and version control
- **HikariCP**: High-performance JDBC connection pool

#### Messaging
- **RabbitMQ 3.13**: Message broker with management plugin
- **AMQP Protocol**: Reliable message delivery
- **Publisher Confirms**: Message acknowledgment
- **Consumer Acknowledgment**: At-least-once delivery

#### Observability
- **Spring Actuator**: Health checks and metrics
- **Prometheus**: Metrics collection (ready)
- **Logging**: SLF4J with Logback

#### API Documentation
- **SpringDoc OpenAPI 3**: Auto-generated API documentation
- **Swagger UI**: Interactive API explorer

### Design Patterns

#### 1. Event-Driven Architecture
- Asynchronous communication between services
- Loose coupling and independent scaling
- Event sourcing for audit trail

#### 2. Database per Service
- Each microservice has its own database
- Data independence and isolation
- Service autonomy

#### 3. Retry Pattern
- Automatic retry for transient failures
- Exponential backoff strategy
- Dead Letter Queue for permanent failures

#### 4. Health Check Pattern
- Liveness and readiness probes
- Graceful degradation
- Circuit breaker ready

#### 5. Repository Pattern
- Data access abstraction with Spring Data JPA
- Clean separation of business logic and data access

### Message Queue Structure

#### Queue Configuration
```yaml
Exchange: email.exchange (type: direct/topic)
  └── Binding: email.send.key
      └── Queue: email.send
          ├── Consumer: Email Service
          ├── Max Retries: 3
          ├── Retry Interval: 3s (exponential backoff)
          └── DLQ: email.send.dlq (for failed messages)
```

#### Message Format
```json
{
  "userId": "123",
  "recipientEmail": "user@example.com",
  "subject": "Welcome to Our Service",
  "body": "Email content here",
  "templateId": "welcome-template",
  "metadata": {
    "priority": "high",
    "timestamp": "2026-01-31T10:00:00Z"
  }
}
```

### Scalability Considerations

#### Horizontal Scaling
- Stateless services enable multiple instances
- RabbitMQ distributes messages across consumers
- Database connection pooling for concurrent requests
- Docker Compose ready for container orchestration

#### Vertical Scaling
- Configurable JVM heap size
- Connection pool sizing
- Thread pool configuration

#### Performance Optimization
- Docker layer caching for faster builds
- Maven dependency caching with BuildKit
- Database indexing (to be implemented)
- Message batching capability (future enhancement)

### Security Features

- Non-root container users
- Environment variable management with dotenv
- Secrets excluded from version control (.gitignore)
- Database credential isolation
- SMTP authentication with TLS/STARTTLS

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.9+
- Docker and Docker Compose
- Git

### Environment Setup

Each service has its own environment configuration for complete isolation:

1. **Copy environment files for each service:**
```bash
# User Service
cp user/.env.example user/.env

# Email Service
cp email/.env.example email/.env
```

2. **Update each `.env` file with your configuration:**
   - **user/.env**: User service database and RabbitMQ settings
   - **email/.env**: Email service database, RabbitMQ, and SMTP settings

### Running with Docker Compose

Each service can be run independently with its own Docker Compose file:

#### User Service

```bash
cd user

# Start all (database + rabbitmq + service)
docker-compose up --build

# Start only infrastructure (database + rabbitmq)
docker-compose up -d user-db rabbitmq

# Stop
docker-compose down
```

#### Email Service

```bash
cd email

# Start all (database + rabbitmq + service)
docker-compose up --build

# Start only infrastructure (database + rabbitmq)
docker-compose up -d email-db rabbitmq

# Stop
docker-compose down
```

#### Quick Start (Infrastructure Only)

Use the provided script to start infrastructure for both services:

```bash
./scripts/up-containers.sh
```

This starts all databases and RabbitMQ instances, allowing you to run the Java services from your IDE.

### Service Endpoints

Once running, the services will be available at:

- **User Service**: http://localhost:8080
  - API Docs: http://localhost:8080/swagger-ui.html
  - Health: http://localhost:8080/actuator/health

- **Email Service**: http://localhost:8081
  - API Docs: http://localhost:8081/swagger-ui.html
  - Health: http://localhost:8081/actuator/health

- **RabbitMQ Management**: http://localhost:15672
  - Default credentials: admin/admin123

### Local Development

To run services locally without Docker:

1. **Start infrastructure services:**
```bash
docker-compose -f docker-compose.dev.yaml up -d
```

2. **Set up environment files:**
```bash
cp user/.env.example user/.env
cp email/.env.example email/.env
# Edit the .env files with your configuration
```

3. **Run the User Service:**
```bash
cd user
./mvnw spring-boot:run
```

4. **Run the Email Service:**
```bash
cd email
./mvnw spring-boot:run
```

Each service will read its own `.env` file using the dotenv-java library.

### Building Individual Services

```bash
# Build User Service
cd user
./mvnw clean package

# Build Email Service
cd email
./mvnw clean package
```

### Docker Build Optimization

The Dockerfiles use:
- Multi-stage builds for smaller images
- BuildKit cache mounts for faster Maven dependency downloads
- JAR layer extraction for optimal caching
- Non-root user for security

## License

(To be specified)
