package org.recluit.customerapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class SnowflakeConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("net.snowflake.client.jdbc.SnowflakeDriver");
        ds.setUrl("jdbc:snowflake://BSTKSKS-UV61897.snowflakecomputing.com/?db=SNOWFLAKE_SAMPLE_DATA&schema=TPCDS_SF10TCL");
        ds.setUsername("rarezas");
        ds.setPassword("fKs222bshM8Jr-wfUCHp");
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

