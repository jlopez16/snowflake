# Kafka Consumer Service

This microservice listens to a Kafka topic for customer data and inserts the received records into a MongoDB collection.

---

## Kafka and MongoDB Configuration

The application uses `application.yml` file to set up the configuration parameters.

Ensure the following environment variables are set them correctly:

```properties
spring.kafka.bootstrap-servers: localhost:9092
kafka.topic: "customer-topic"
kafka.group-id: "customer-group"
```

Ensure the following environment variables for MongoDB are set in the `application.yml` file:

```properties
spring.data.mongodb.uri: jdbc:snowflake://<your_account>.snowflakecomputing.com/?db=SNOWFLAKE_SAMPLE_DATA&schema=TPCDS_SF10TCL
```
---

## How to start the application

1. Set environment variables or update `src/main/resources/application.yml`
2. Build the project:
```bash
mvn clean package
```
3.Run the service:
```bash
 java -jar target/kafka-consumer-0.0.1-SNAPSHOT.jar
```