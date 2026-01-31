#!/bin/bash

# Start infrastructure services for both user and email services
echo "Starting infrastructure services..."

# Start User Service infrastructure
cd user && docker-compose --env-file .env up -d user-db rabbitmq

# Start Email Service infrastructure
cd ../email/ && docker-compose --env-file .env up -d email-db rabbitmq

echo "Infrastructure services started successfully!"
echo ""
echo "Services running:"
echo "  - User DB: localhost:5432"
echo "  - Email DB: localhost:5433"
echo "  - RabbitMQ (User): localhost:5672"
echo "  - RabbitMQ (Email): localhost:5672"
echo "  - RabbitMQ Management (User): localhost:15674"
echo "  - RabbitMQ Management (Email): localhost:15673"
