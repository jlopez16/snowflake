package org.recluit.customerapi.service;

import org.recluit.customerapi.dto.AddressDto;
import org.recluit.customerapi.dto.CustomerDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnowflakeCustomerService {

    private final JdbcTemplate jdbcTemplate;

    public SnowflakeCustomerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CustomerDto> getCustomers(int page, int size) {
        int offset = (page - 1) * size;

        String sql = """
            SELECT\s
                C.C_CUSTOMER_ID AS "customer_id",
                C.C_FIRST_NAME AS "first_name",
                C.C_LAST_NAME AS "last_name",
                C.C_BIRTH_DAY AS "birth_day",
                C.C_BIRTH_MONTH AS "birth_month",
                C.C_BIRTH_YEAR AS "birth_year",
                C.C_EMAIL_ADDRESS AS "email",
                A.CA_STREET_NAME AS "street",
                A.CA_CITY AS "city",
                A.CA_STATE AS "state",
                A.CA_COUNTRY AS "country",
                A.CA_SUITE_NUMBER AS "number"
            FROM SNOWFLAKE_SAMPLE_DATA.TPCDS_SF10TCL.CUSTOMER C
            JOIN SNOWFLAKE_SAMPLE_DATA.TPCDS_SF10TCL.CUSTOMER_ADDRESS A
                ON C.C_CURRENT_ADDR_SK = A.CA_ADDRESS_SK
            ORDER BY C.C_CUSTOMER_ID
            LIMIT ? OFFSET ?
        """;

        return jdbcTemplate.query(sql, new Object[]{size, offset}, (rs, rowNum) -> {
            AddressDto address = new AddressDto(
                    rs.getString("street"),
                    rs.getString("number"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("country")
            );

            return new CustomerDto(
                    rs.getString("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getInt("birth_day"),
                    rs.getInt("birth_month"),
                    rs.getInt("birth_year"),
                    rs.getString("email"),
                    address
            );
        });
    }
}
