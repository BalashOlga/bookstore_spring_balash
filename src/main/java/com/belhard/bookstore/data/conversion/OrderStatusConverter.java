package com.belhard.bookstore.data.conversion;

import com.belhard.bookstore.data.entity.OrderStatus;
import com.belhard.bookstore.data.entity.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrderStatus orderStatus) {
        return orderStatus.ordinal();
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer integer) {

        return OrderStatus.values()[integer];
    }
}
