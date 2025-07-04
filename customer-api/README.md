# Customer API Microservice

This microservice provides REST endpoints to fetch customer data from **Snowflake**, publish it to **Kafka**, and retrieve it from **MongoDB Atlas**.

---

## Features

- Fetch paginated customer records from Snowflake
- Retrieve a customer by ID and publish it to Kafka (`customer-topic`)
- Consume messages and store customer data in MongoDB Atlas
- Access MongoDB-stored customers via REST API

---

## How to Connect to Snowflake

The application uses the [Snowflake JDBC driver](https://docs.snowflake.com/en/user-guide/jdbc) to connect to a public dataset in Snowflake.

Ensure the following environment variables are set them in `application.yml`:

```properties
spring.datasource.driver-class-name=net.snowflake.client.jdbc.SnowflakeDriver
spring.datasource.url=jdbc:snowflake://<account>.snowflakecomputing.com/?db=SNOWFLAKE_SAMPLE_DATA&schema=TPCDS_SF10TCL
spring.datasource.username=<YOUR_USERNAME>
spring.datasource.password=<YOUR_PASSWORD>
```

## How to Connect to Snowflake

Ensure the following environment variables for MongoDB are set in `application.yml` file:

```properties
  data.mongodb.uri: mongodb://admin:admin123@localhost:27017/customerdb?authSource=admin
```

## How to start the application

1. Set environment variables or update `src/main/resources/application.yml`
2. Build the project:
```bash
mvn clean package
```
3.Run the service:
```bash
 java -jar target/customer-api-0.0.1-SNAPSHOT.jar
```
**Note:** if Snowflakes´s JDBC driver throws an error related to Apache Arrow (accesing to certain fields from JDK), add this flag:
```bash
java --add-opens=java.base/java.nio=org.apache.arrow.memory.core,ALL-UNNAMED -jar target/customer-api-0.0.1-SNAPSHOT.jar
```
## Testing the microservice

To test the endpoints, the request should be sent by any client: Postman, CURL, web browser, etc. The available endpoints are:

| Method | Endpoint                    | Description                                                                 |
| ------ | --------------------------- | --------------------------------------------------------------------------- |
| `GET`  | `/api/customers/`           | Retrieves a paginated list of customers from Snowflake.                     |
| `GET`  | `/api/customers/fetch/{id}` | Fetches a specific customer by ID from Snowflake and publishes it to Kafka. |
| `GET`  | `/api/customers/mongo`      | Returns all customer records stored in MongoDB. (for verification)          |
