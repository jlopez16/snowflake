package org.recluit.customerapi.service;

import lombok.extern.slf4j.Slf4j;
import org.recluit.customerapi.domain.CustomerRowMapper;
import org.recluit.customerapi.dto.CustomerDto;
import org.recluit.customerapi.exception.DatabaseException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class SnowflakeCustomerService {

    private static final String SELECT_CUSTOMER_BASE = """
        SELECT 
            C.C_CUSTOMER_ID AS "customer_id",
            C.C_FIRST_NAME AS "first_name",
            C.C_LAST_NAME AS "last_name",
            C.C_EMAIL_ADDRESS AS "email",
            C.C_BIRTH_DAY AS "birth_day",
            C.C_BIRTH_MONTH AS "birth_month",
            C.C_BIRTH_YEAR AS "birth_year",
            A.CA_STREET_NAME AS "street",
            A.CA_CITY AS "city",
            A.CA_STATE AS "state",
            A.CA_COUNTRY AS "country",
            A.CA_SUITE_NUMBER AS "number"
        FROM SNOWFLAKE_SAMPLE_DATA.TPCDS_SF10TCL.CUSTOMER C
        JOIN SNOWFLAKE_SAMPLE_DATA.TPCDS_SF10TCL.CUSTOMER_ADDRESS A
            ON C.C_CURRENT_ADDR_SK = A.CA_ADDRESS_SK
    """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public SnowflakeCustomerService(NamedParameterJdbcTemplate jdbcTemplate,
                                    CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    public List<CustomerDto> getCustomers(int page, int size) {
        int offset = (page - 1) * size;
        String sql = SELECT_CUSTOMER_BASE + """
            ORDER BY C.C_CUSTOMER_ID
            LIMIT :limit OFFSET :offset
        """;

        Map<String, Object> params = new HashMap<>();
        params.put("limit", size);
        params.put("offset", offset);

        try {
            return jdbcTemplate.query(sql, params, customerRowMapper);
        } catch (Exception e) {
            log.error("Error fetching customers: ", e);
            throw new DatabaseException("Error fetching customers", e);
        }
    }

    public Optional<CustomerDto> getCustomerById(String id) {
        String sql = SELECT_CUSTOMER_BASE + "WHERE C.C_CUSTOMER_ID = :customerId";

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", id);

        try {
            List<CustomerDto> results = jdbcTemplate.query(sql, params, customerRowMapper);
            return results.stream().findFirst();
        } catch (Exception e) {
            log.error("Error fetching customer with ID {}: ", id, e);
            throw new DatabaseException("Error fetching customer", e);
        }
    }
}
