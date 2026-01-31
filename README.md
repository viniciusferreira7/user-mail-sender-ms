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

(Documentation to be added)

## License

(To be specified)
