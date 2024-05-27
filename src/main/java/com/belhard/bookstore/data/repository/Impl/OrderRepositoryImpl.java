package com.belhard.bookstore.data.repository.Impl;

import com.belhard.bookstore.data.conversion.DataConversion;
import com.belhard.bookstore.data.dao.BookDao;
import com.belhard.bookstore.data.dao.OrderDao;
import com.belhard.bookstore.data.dao.OrderItemDao;
import com.belhard.bookstore.data.dao.UserDao;
import com.belhard.bookstore.data.dto.OrderDto;
import com.belhard.bookstore.data.dto.OrderItemDto;
import com.belhard.bookstore.data.dto.UserDto;
import com.belhard.bookstore.data.entity.Order;
import com.belhard.bookstore.data.entity.OrderItem;
import com.belhard.bookstore.data.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final BookDao bookDao;
    private final UserDao userDao;
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final DataConversion dataConversion;


    @Override
    public Order findById(long id) {
        OrderDto orderDto = orderDao.findById(id);
        return buildOrder(orderDto);
    }

    private Order buildOrder (OrderDto orderDto) {
       Order order = dataConversion.toEntity(orderDto);
       UserDto byId = userDao.findById(orderDto.getUserId());
       order.setUser(dataConversion.toEntity(byId));

    List<OrderItemDto> orderItemsDto = orderItemDao.findByOrder(orderDto.getId());
    List<OrderItem> orderItems = new ArrayList<>();
        orderItemsDto.forEach(dto -> {
        OrderItem entity = dataConversion.toEntity(dto);
        entity.setBook(dataConversion.toEntity(bookDao.findById(dto.getBookId())));
        orderItems.add(entity);
    });
        order.setItems(orderItems);
        return order;
    }


    @Override
    public List<Order> findAll() {
        return orderDao.findAll()
                .stream()
                .map(this::buildOrder)
                .toList();
    }

    @Override
    public Order create(Order order) {
        OrderDto savedDto = orderDao.create(dataConversion.toDto(order));
        order.getItems().forEach(orderItem ->{
            orderItemDao.create(dataConversion.toDto(orderItem));
        });
        return buildOrder(savedDto);
    }

    @Override
    public Order update(Order order) {
        orderItemDao.delete(order.getId());
        OrderDto updateDto = orderDao.update(dataConversion.toDto(order));
        order.getItems().forEach(orderItem ->{
            orderItemDao.create(dataConversion.toDto(orderItem));
        });
        return buildOrder(updateDto);
    }

    @Override
    public boolean delete(long orderId) {
        orderItemDao.delete(orderId);
        return orderDao.delete(orderId);
    }

}
