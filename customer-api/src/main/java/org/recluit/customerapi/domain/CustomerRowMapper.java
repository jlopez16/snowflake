package org.recluit.customerapi.domain;

import org.recluit.customerapi.dto.AddressDto;
import org.recluit.customerapi.dto.CustomerDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper<CustomerDto> {
    @Override
    public CustomerDto mapRow(ResultSet rs, int rowNum) throws SQLException {
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
    }
}
