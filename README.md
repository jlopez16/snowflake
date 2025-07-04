# Recluit Customer Data Pipeline

This project implements a microservices-based data pipeline to process customer data from an analytical database into an operational store, using **Snowflake**, **Apache Kafka**, and **MongoDB**, powered by **Java 21** and **Spring Boot 3**.

---

## 🧱 Project Structure

### 1. **customer-api**
Service responsible for extracting data from Snowflake and publishing it to a Kafka topic. REST API for querying customer data stored in MongoDB.

- **Technologies**: Spring Boot 3, Spring Data MongoDB, Spring Web, Spring for Apache Kafka, Snowflake JDBC.
- **Features**:
  - Exposes a RESTful interface to fetch customer records (pagination, filtering, etc.).
  - MongoDB configuration via `application.yml`.
  - Connects to Snowflake via JDBC.
  - Performs paginated queries to fetch customer data.
  - Publishes messages to a Kafka topic (e.g., `customer-topic`).
- **External Dependencies**:
  - Snowflake credentials and access.
  - Kafka broker running locally or in Docker.


---

### 2. **kafka-consumer**
Service that consumes messages from Kafka and stores them into MongoDB.

- **Technologies**: Spring Boot 3, Spring for Apache Kafka, Spring Data MongoDB.
- **Features**:
  - Subscribes to the customer topic.
  - Processes and persists incoming events to MongoDB.
- **Notes**: Ensure proper MongoDB connectivity when running in Docker (`mongo:27017`).

---

### 4. **docker-compose (local dev environment)**
The root `docker-compose.yml` orchestrates the services required to run the entire data pipeline locally.

- **Services**:
  - Kafka + Zookeeper (Bitnami or Confluent)
  - MongoDB
  - Mongo Express (optional)
  - Kafka UI (for topic inspection)
  - customer-api (optional, as a container)
- **Shared Network**: `backend`

---

## Data Flow

```text
[Snowflake] → customer-api → [Kafka Topic] → kafka-consumer → [MongoDB] → customer-api
