package com.master.minieshop.service;

import com.master.minieshop.common.BaseEntityService;
import com.master.minieshop.entity.Order;
import com.master.minieshop.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends BaseEntityService<Order, String, OrderRepository> {
    public OrderService(OrderRepository repository) {
        super(repository);
    }
}
