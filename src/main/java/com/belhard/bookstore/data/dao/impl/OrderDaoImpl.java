package com.belhard.bookstore.data.dao.impl;

import com.belhard.bookstore.data.dao.OrderDao;
import com.belhard.bookstore.data.dto.OrderDto;
import com.belhard.bookstore.data.entity.OrderStatus;
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
import java.sql.Types;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class OrderDaoImpl implements OrderDao {
    private static final String FIND_BY_ID = "SELECT orders.id id, orders.user_id user_id, orders.cost cost, orderstatuses.name status FROM orders JOIN orderstatuses ON orderstatuses.id = orders.status_id WHERE orders.id = ?  and orders.deleted = false";
    private static final String FIND_ALL = "SELECT orders.id id, orders.user_id user_id, orders.cost cost,orderstatuses.name status FROM orders JOIN orderstatuses ON orderstatuses.id = orders.status_id WHERE orders.deleted = false";
    private static final String FIND_BY_USER = "SELECT orders.id id, orders.user_id user_id, orders.cost cost,orderstatuses.name status FROM orders JOIN orderstatuses ON orderstatuses.id = orders.status_id WHERE orders.user_id = ? and orders.deleted = false";
    private static final String CREATE = "INSERT INTO orders (user_id, cost, status_id, deleted)  SELECT :userId , :cost, orderstatuses.id, false from orderstatuses where orderstatuses.name = :status";
    private static final String UPDATE = "UPDATE  orders SET user_id = :userId, cost = :cost, status_id = (SELECT orderstatuses.id from orderstatuses where orderstatuses.name = :status) WHERE orders.id = ?";
    private static final String DELETE = "UPDATE  orders SET deleted = true where orders.id = ? and orders.deleted = false";
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Nullable
    private OrderDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderDto order = new OrderDto();
        order.setId(rs.getLong("id"));
        order.setUserId(rs.getLong("user_id"));
        order.setCost(rs.getBigDecimal("cost"));
        order.setStatus(OrderStatus.valueOf(rs.getString("status")));
        log.debug(order.toString());
        return order;
    }

    private BeanPropertySqlParameterSource getBeanPropertySql(OrderDto order) {
        BeanPropertySqlParameterSource namedParameters = new BeanPropertySqlParameterSource(order);
        namedParameters.registerSqlType("status", Types.VARCHAR);
        return namedParameters;
    }
    
    @Override
    public OrderDto findById(long id) {
        OrderDto order = jdbcTemplate.queryForObject(FIND_BY_ID, this::mapRow, id);
        log.debug("Select FIND_BY_ID has been completed");
        return order;
    }

    @Override
    public List<OrderDto> findAll() {
        List<OrderDto> orders = jdbcTemplate.query(FIND_ALL, this::mapRow);
        log.debug("Select FIND_ALL has been completed");
        return orders;
    }

    @Override
    public List<OrderDto> findByUser(long userId) {
        List<OrderDto> orders = jdbcTemplate.query(FIND_BY_USER, this::mapRow, userId);
        log.debug("Select FIND_BY_USER has been completed");
        return orders;
    }

    @Override
    public OrderDto create(OrderDto order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        BeanPropertySqlParameterSource namedParameters = getBeanPropertySql(order);
        namedParameterJdbcTemplate.update(CREATE, namedParameters, keyHolder, new String[]{"id"});
        log.debug("Update CREATE has been completed");
        return findById(keyHolder.getKey().longValue());
    }

    @Override
    public OrderDto update(OrderDto order) {
        BeanPropertySqlParameterSource namedParameters = getBeanPropertySql(order);
        namedParameterJdbcTemplate.update(UPDATE, namedParameters);
        log.debug("Update UPDATE has been completed");
        return findById(order.getId());
    }

    @Override
    public boolean delete(long id) {
        int i = jdbcTemplate.update(DELETE, id);
        log.debug("Update DELETE has been completed");
        return i == 1;
    }
}
