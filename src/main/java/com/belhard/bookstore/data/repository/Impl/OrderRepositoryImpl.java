package com.belhard.bookstore.data.repository.Impl;

import com.belhard.bookstore.data.conversion.DataConversion;
import com.belhard.bookstore.data.dao.BookDao;
import com.belhard.bookstore.data.dao.OrderDao;
import com.belhard.bookstore.data.dao.OrderItemDao;
import com.belhard.bookstore.data.dao.UserDao;
import com.belhard.bookstore.data.dto.BookDto;
import com.belhard.bookstore.data.dto.OrderDto;
import com.belhard.bookstore.data.dto.OrderItemDto;
import com.belhard.bookstore.data.dto.UserDto;
import com.belhard.bookstore.data.entity.Order;
import com.belhard.bookstore.data.entity.OrderItem;
import com.belhard.bookstore.data.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderRepositoryImpl implements OrderRepository {
    private final BookDao bookDao;
    private final UserDao userDao;
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final DataConversion dataConversion;

    @PersistenceContext
    private EntityManager manager;


    @Override
    public Order findById(long id) {
        try {
            OrderDto orderDto = orderDao.findById(id);
            return buildOrder(orderDto);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private Order buildOrder(OrderDto orderDto) {
        Order order = null;
        order = dataConversion.toEntity(orderDto);
        UserDto byId = userDao.findById(orderDto.getUserId());
        order.setUser(dataConversion.toEntity(byId));

        List<OrderItemDto> orderItemsDto = orderItemDao.findByOrder(orderDto.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        orderItemsDto.forEach(dto -> {
            OrderItem entity = dataConversion.toEntity(dto);
            BookDto bookDto = bookDao.findById(dto.getBookId());
            entity.setBook(dataConversion.toEntity(bookDto));
            System.out.println("444");
            orderItems.add(entity);
        });
        order.setItems(orderItems);
        return order;
    }


    @Override
    public List<Order> findAll() {
        try {
            return orderDao.findAll()
                    .stream()
                    .map(this::buildOrder)
                    .toList();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Order create(Order order) {
        try {
            OrderDto savedDto = orderDao.create(dataConversion.toDto(order));
            order.getItems().forEach(orderItem -> {
                orderItemDao.create(dataConversion.toDto(orderItem));
            });
            return buildOrder(savedDto);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Order update(Order order) {
        try {
            orderItemDao.delete(order.getId());
            OrderDto updateDto = orderDao.update(dataConversion.toDto(order));
            order.getItems().forEach(orderItem -> {
                orderItemDao.create(dataConversion.toDto(orderItem));
            });
            return buildOrder(updateDto);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean delete(long orderId) {
        orderItemDao.delete(orderId);
        return orderDao.delete(orderId);
    }

}
