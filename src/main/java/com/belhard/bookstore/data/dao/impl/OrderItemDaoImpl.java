package com.belhard.bookstore.data.dao.impl;

import com.belhard.bookstore.data.dao.OrderItemDao;
import com.belhard.bookstore.data.dto.OrderItemDto;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Repository
public class OrderItemDaoImpl implements OrderItemDao {
    private static final String FIND_BY_ID = "SELECT orderitems.id id, orderitems.order_id order_id, orderitems.book_id book_id, orderitems.price price, orderitems.quantity quantity,FROM orderitems WHERE orderitems.id = ?  and orderitems.deleted = false";
    private static final String FIND_ALL = "SELECT orderitems.id id, orderitems.order_id order_id, orderitems.book_id book_id, orderitems.price price, orderitems.quantity quantity, FROM orderitems  WHERE orderitems.deleted = false";
    private static final String FIND_BY_ORDER = "SELECT orderitems.id id, orderitems.order_id order_id, orderitems.book_id book_id, orderitems.price price, orderitems.quantity quantity FROM orderitems WHERE orderitems.order_id = ? and orderitems.deleted = false";
    private static final String CREATE = "INSERT INTO orderitems (order_id, book_id, price, quantity, deleted)  VALUES (:bookId, :price, :quantity, false)" ;
    private static final String UPDATE = "UPDATE  orderitems SET  order_id = :orderId,  book_id = :bookId , price = :price, quantity = :quantity  where orderitems.id = :id and orderitems.deleted = false";
    private static final String DELETE = "UPDATE  orderitems SET deleted = true where orderitems.id = ? and orderitems.deleted = false";
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Nullable
    private OrderItemDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderItemDto order = new OrderItemDto();
        order.setOrderId(rs.getLong("order_id"));
        order.setPrice(rs.getBigDecimal("price"));
        order.setQuantity(rs.getInt("quantity"));
        log.debug(order.toString());
        return order;
    }

    private BeanPropertySqlParameterSource getBeanPropertySql(OrderItemDto order) {
        BeanPropertySqlParameterSource namedParameters = new BeanPropertySqlParameterSource(order);
        return namedParameters;
    }

    @Override
    public OrderItemDto findById(long id) {
        OrderItemDto orderItem = jdbcTemplate.queryForObject(FIND_BY_ID, this::mapRow, id);
        log.debug("Select FIND_BY_ID has been completed");
        return orderItem;
    }

    @Override
    public List<OrderItemDto> findByOrder(long OrderId) {
        List<OrderItemDto> orderItems = jdbcTemplate.query(FIND_BY_ORDER, this::mapRow, OrderId);
        log.debug("Select FIND_BY_ORDER has been completed");
        return orderItems;
    }

    @Override
    public List<OrderItemDto> findAll() {
        List<OrderItemDto> orderItems = jdbcTemplate.query(FIND_ALL, this::mapRow);
        log.debug("Select FIND_ALL has been completed");
        return orderItems;
    }

    @Override
    public OrderItemDto create(OrderItemDto orderItem) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        BeanPropertySqlParameterSource namedParameters = getBeanPropertySql(orderItem);
        namedParameterJdbcTemplate.update(CREATE, namedParameters, keyHolder, new String[]{"id"});
        log.debug("Update CREATE has been completed");
        return findById(keyHolder.getKey().longValue());
    }

    @Override
    public OrderItemDto update(OrderItemDto orderItem) {
        BeanPropertySqlParameterSource namedParameters = getBeanPropertySql(orderItem);
        namedParameterJdbcTemplate.update(UPDATE, namedParameters);
        log.debug("Update UPDATE has been completed");
        return findById(orderItem.getOrderId());
    }

    @Override
    public boolean delete(long orderId) {
        int i = jdbcTemplate.update(DELETE, orderId);
        log.debug("Update DELETE has been completed");
        return i == 1;
    }
}
