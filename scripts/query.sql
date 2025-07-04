-- In Snowflake execute these lines:

use role ACCOUNTADMIN;

USE SCHEMA SNOWFLAKE_SAMPLE_DATA.TPCDS_SF10TCL;

-- If SNOWFLAKE_SAMPLE_DATA does not exist, execute these commands:

create or replace database SNOWFLAKE_SAMPLE_DATA from share SFC_SAMPLES.SAMPLE_DATA;

grant imported privileges on database SNOWFLAKE_SAMPLE_DATA to role sysadmin; 

-- Then one more time: 

USE SCHEMA SNOWFLAKE_SAMPLE_DATA.TPCDS_SF10TCL;

-- Explore tables
SHOW TABLES;

-- Preview data
SELECT * FROM CUSTOMER LIMIT 100;
SELECT * FROM CUSTOMER_ADDRESS LIMIT 100;

DESCRIBE CUSTOMER;
