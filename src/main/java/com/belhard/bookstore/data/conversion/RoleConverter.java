package com.belhard.bookstore.data.conversion;

import com.belhard.bookstore.data.entity.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter <Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return  role.ordinal();
    }

    @Override
    public Role convertToEntityAttribute(Integer integer) {

        return Role.values()[integer];
    }
}
