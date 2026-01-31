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

The microservice follows an event-driven architecture where:
1. User-related events are published to RabbitMQ queues
2. Email service consumes messages from the queue
3. User service provides necessary user data for email processing
4. Emails are sent asynchronously, ensuring system reliability and scalability

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.9+
- Docker and Docker Compose
- Git

### Environment Setup

1. Copy the example environment file:
```bash
cp .env.example .env
```

2. Update the `.env` file with your configuration:
   - Database credentials
   - RabbitMQ credentials
   - SMTP/Email credentials (for Gmail, use App Password)

### Running with Docker Compose

1. Build and start all services:
```bash
docker-compose up --build
```

2. Stop all services:
```bash
docker-compose down
```

3. Stop and remove volumes (clean database):
```bash
docker-compose down -v
```

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

1. Start PostgreSQL and RabbitMQ using Docker:
```bash
docker-compose up user-db email-db rabbitmq
```

2. Run the User Service:
```bash
cd user
./mvnw spring-boot:run
```

3. Run the Email Service:
```bash
cd email
./mvnw spring-boot:run
```

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
