package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.data.entity.*;
import com.belhard.bookstore.data.repository.BookRepository;
import com.belhard.bookstore.data.repository.OrderRepository;
import com.belhard.bookstore.data.repository.UserRepository;
import com.belhard.bookstore.service.OrderService;
import com.belhard.bookstore.service.dto.OrderCreateDto;
import com.belhard.bookstore.service.dto.OrderDto;
import com.belhard.bookstore.service.dto.OrderDtoLazy;
import com.belhard.bookstore.service.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUser(order.getUser());
        orderDto.setItems(order.getItems());
        orderDto.setCost(order.getCost());
        orderDto.setStatus(order.getStatus());
        return orderDto;
    }

    private OrderDtoLazy toDtoLazy(Order order) {
        OrderDtoLazy orderDtoLazy = new OrderDtoLazy();

        orderDtoLazy.setId(order.getId());
        orderDtoLazy.setUser(order.getUser());
        orderDtoLazy.setCost(order.getCost());
        orderDtoLazy.setStatus(order.getStatus());
        return orderDtoLazy;
    }

    private Order toOrder(OrderDto orderDto) {
        Order order = new Order();

        order.setId(orderDto.getId());
        order.setUser(orderDto.getUser());
        order.setItems(orderDto.getItems());
        order.setCost(orderDto.getCost());
        order.setStatus(orderDto.getStatus());
        return order;
    }



    @Override
    @Transactional
    public OrderDto getById(long id) {
        log.debug("Calling getById");

        Order order = orderRepository.findById(id);

        if (order == null) {
            throw new NotFoundException("Order whith id = " + id + " is not found!");
        } else {
            return toDto(order);
        }
    }

    @Override
    @Transactional
    public List<OrderDto> getByUserId(long id) {
        log.debug("Calling getByUser");

        List<Order> orders =  orderRepository.findByUserId(id);
        if (orders == null){
            throw new NotFoundException("Orders by usersId = " + id + "are not found!");
        } else {
            return orders
                    .stream()
                    .map(this::toDto)
                    .toList();
        }
    }

    @Override
    public List<OrderDtoLazy> getAll() {
        log.debug("Calling getAll");

        List<Order> orders =  orderRepository.findAll();
        if (orders == null){
            throw new NotFoundException("Orders are not found!");
        } else {
            return orders
                    .stream()
                    .map(this::toDtoLazy)
                    .toList();
        }
    }

    @Override
    public OrderDto create(OrderCreateDto orderCreateDto) {
        List<OrderItem> items = new ArrayList<>();
        Map<Long, Integer> cart = orderCreateDto.getCart();
        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            OrderItem orderItem = new OrderItem();
            Long bookId = entry.getKey();
            Book book = bookRepository.findById(bookId);
            orderItem.setBook(book);
            Integer quantity = entry.getValue();
            orderItem.setQuantity(quantity);
            items.add(orderItem);
        }

        Order order = new Order();
        User user = userRepository.findById(orderCreateDto.getUser().getId());
        order.setUser(user);
        order.setItems(items);
        order.setStatus(OrderStatus.valueOf("ISSUED"));

        return toDto(orderRepository.save(order));
    }

}

