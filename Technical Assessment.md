## 🧪 Technical Assessment: **"Customer Data Pipeline: Snowflake → Kafka → MongoDB"**

---

### 🧠 Overview

> Develop a microservice using **Java 21 with Spring Boot 3** that performs the following:
>
> 1. Retrieves customer and address information from **Snowflake** using a **paginated SQL query**.
> 2. Sends the selected customer data (by ID) to a **Kafka topic** using a **Kafka Producer**.
> 3. A **Kafka Consumer** listens to the topic and stores the received data in a MongoDB collection named `customer_data`, either on **MongoDB Atlas** or a **local MongoDB instance**.


---


### 📊 Data Source

For the purposes of this assessment, customer data can be obtained from the `SNOWFLAKE_SAMPLE_DATA.TPCDS_SF100TCL` schema using the following SQL commands:

```sql
SHOW SCHEMAS;
USE SCHEMA SNOWFLAKE_SAMPLE_DATA.TPCDS_SF100TCL;
SHOW TABLES;
SELECT COUNT(*) FROM CUSTOMER;
SELECT * FROM CUSTOMER LIMIT 100;
SELECT * FROM CUSTOMER_ADDRESS LIMIT 100;
```

---

### 📦 Technical Workflow

1. **`GET /api/customers/`**

   * Executes a paginated query in Snowflake to retrieve customer data.
   * Returns the list of customers in a paginated response.

2. **`GET /api/customers/fetch/{id}`**

   * Executes a query in Snowflake to fetch a customer by their ID.
   * Sends the retrieved customer data as a JSON message to a Kafka topic named `customer-topic`.

3. **Kafka Consumer**

   * Listens to the `customer-topic`.
   * Transforms each message into a valid JSON document.
   * Adds a `receivedAt` timestamp field.
   * Inserts the document into the `customer_data` collection in MongoDB Atlas (or local MongoDB).

4. **`GET /api/customers/mongo`**

   * Returns the list of customers stored in MongoDB.

---

### 💡 Sample JSON structure sent via Kafka

```json
{
  "customerId": 123,
  "firstName": "Jane",
  "lastName": "Doe",
  "birthDay": 12,
  "birthMonth": 8,
  "birthYear": 8,  
  "email": "jane.doe@example.com",
  "address": {
    "street": "Main St",
    "number": "456",
    "city": "Springfield",
    "state": "TX",
    "country": "USA"
  },
  "receivedAt": "2025-09-30T18:00:00Z"  
}
```

---

### 🔧 Technologies to Use

| Technology                  | Purpose                                         |
| --------------------------- | ----------------------------------------------- |
| **Java 21 + Spring Boot 3** | To build the REST API and service logic         |
| **Snowflake JDBC**          | To connect and query data from Snowflake        |
| **Apache Kafka**            | For asynchronous communication between services |
| **MongoDB Atlas**           | To store the final customer data                |
| **Docker Compose**          | To run Kafka and MongoDB locally (optional)     |
| **JUnit + Mockito**         | For unit testing                                |


---

### 📋 Expected API Endpoints

| Method | Endpoint                    | Description                                                                 |
| ------ | --------------------------- | --------------------------------------------------------------------------- |
| `GET`  | `/api/customers/`           | Retrieves a paginated list of customers from Snowflake.                     |
| `GET`  | `/api/customers/fetch/{id}` | Fetches a specific customer by ID from Snowflake and publishes it to Kafka. |
| `GET`  | `/api/customers/mongo`      | Returns all customer records stored in MongoDB. (for verification)          |


---

### 📄 Deliverables

* A public repository URL (GitHub, GitLab, or Bitbucket) containing the following for each project:

  * Full source code
  * A `README.md` file written in English, including:

    * Instructions on how to run the microservice
    * Required environment variables and configuration details (e.g., Kafka, MongoDB URI)
    * Example requests using `curl` or Postman


---

## 📁 Suggested Repository Structure

```
customer-data-pipeline/
├── README.md                       # General documentation for the project
├── docker-compose.yml             # Docker services for Kafka, MongoDB, etc.
├── .env                           # Example environment variables (if applicable)

├── customer-api/                  # Kafka Producer + REST API (queries Snowflake and sends to Kafka)
│   ├── README.md                  # Documentation specific to this service
│   ├── pom.xml                    # Maven dependencies and configuration
│   └── src/                       # Source code

├── kafka-consumer/                # Kafka Consumer microservice (consumes from Kafka and writes to MongoDB)
│   ├── README.md
│   ├── pom.xml
│   └── src/

├── scripts/                       # SQL scripts, Postman collections, sample payloads
│   ├── query.sql                  # Sample query for Snowflake
│   ├── postman_collection.json    # Optional
│   └── ejemplos.json              # Example JSON messages
```


---

## 📄 What should each `README.md` include?

* **`customer-api/README.md`**:

  * How to connect to Snowflake
  * How to connect to MongoDB
  * How to start the microservice
  * Required environment variables (Snowflake URL, credentials, Kafka configuration)

* **`kafka-consumer/README.md`**:

  * Kafka and MongoDB configuration
  * How to start the consumer service
  * MongoDB URI and collection details

* **`root README.md` (main repository)**:

  * Overview and purpose of the system
  * Simple architecture diagram (optional, can be PlantUML or an image)
  * Prerequisites
  * Instructions to start all services using `docker-compose`
  * How to run tests
  * List of main API endpoints

---


### ✅ Evaluation Criteria

| Criteria                                                       |
| -------------------------------------------------------------- |
| Correct connection to Snowflake and successful query execution |
| Proper implementation of Kafka Producer with error handling    |
| Kafka Consumer functionality and insertion into MongoDB Atlas  |
| Well-designed JSON structure and use of DTOs                   |
| Clean, readable code following best practices                  |
| Unit testing coverage                                          |
| Clear and complete documentation in English (`README.md`)      |

---

### 💥 Bonus

* Create a small Python script that consumes the MongoDB API endpoint and saves the response to a local text file.
* Add interactive API documentation using Swagger UI (`springdoc-openapi`).




