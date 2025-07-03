## ❄️ Using Snowflake Free Tier and Sample Data

Snowflake offers a **free trial** with credits to explore its features and run real-world queries using public datasets.

### 🆓 What’s included in the Free Tier?

* **\$400–\$500 USD in credits** (depending on region and provider)
* **30-day trial period**
* Access to **all core features** (warehouses, databases, roles, etc.)
* No credit card required to start

---

### 🚀 How to Get Started

1. Go to: [https://signup.snowflake.com/](https://signup.snowflake.com/)
2. Choose your preferred **cloud provider** (AWS, Azure, or GCP) and **region**.
3. Register using your email.
4. You’ll receive immediate access to your Snowflake account with trial credits.

---

### 📦 What is `SNOWFLAKE_SAMPLE_DATA`?

All Snowflake accounts (including free trials) come with access to the **`SNOWFLAKE_SAMPLE_DATA`** database. It contains **preloaded datasets** designed for learning, testing, and building data pipelines.

One of the most relevant schemas for this exam is:

| Schema          | Description                                                                                                                                                                                    |
| --------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `TPCDS_SF10TCL` | A rich, complex e-commerce dataset based on the TPC-DS benchmark. It includes tables like `CUSTOMER`, `CUSTOMER_ADDRESS`, `STORE_SALES`, etc., useful for simulating real analytics and joins. |

---

### 🧪 Example Queries

```sql
-- Show available schemas
SHOW SCHEMAS;

-- Use the TPCDS sample schema
USE SCHEMA SNOWFLAKE_SAMPLE_DATA.TPCDS_SF10TCL;

-- Explore tables
SHOW TABLES;

-- Preview data
SELECT * FROM CUSTOMER LIMIT 100;
SELECT * FROM CUSTOMER_ADDRESS LIMIT 100;
```

---

## Running the `docker-compose.yml` (First-Time Setup)

If you're running `docker-compose.yml` for the first time and don't have the required images locally, Docker will need to **download them**. This may take several minutes, especially for Kafka and MongoDB.

### Step-by-step instructions

1. **Open a terminal** and navigate to the project folder where `docker-compose.yml` is located.

2. **Pull the images (optional but recommended):**

```bash
docker compose pull
```

3. **Start the services in detached mode:**

```bash
docker compose up -d
```

> This will start Kafka, Zookeeper, MongoDB, and Mongo Express in the background.

4. **Check running containers:**

```bash
docker compose ps
```

You should see services like `kafka`, `mongo`, `zookeeper`, and `mongo-express` with `Up` status.

5. **Access Mongo Express in your browser:**

```
http://localhost:8081
```

* Username: `admin`
* Password: `admin123`

---

### Stopping the environment

To stop all services:

```bash
docker compose down
```

To stop and **remove all volumes** (persistent data):

```bash
docker compose down -v
```

---

### 💡 Notes

* If you run `docker compose up` without `-d`, Docker will show logs in your terminal. You can press `CTRL + C` to stop showing logs, but the containers may remain running in the background.
* Use `docker compose logs kafka` or `mongo` to inspect logs of a specific service.

---

