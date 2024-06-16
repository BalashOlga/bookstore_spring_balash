package com.belhard.bookstore.service.dto;

import com.belhard.bookstore.data.entity.User;
import lombok.Data;

import java.util.Map;

@Data
public class OrderCreateDto {
    private final UserDtoWithoutPassword user;
    private final Map<Long, Integer> cart;
}
