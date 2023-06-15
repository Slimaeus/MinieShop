package com.master.minieshop.repository;

import com.master.minieshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    public List<Order> findByUser_UserName(String username);
}
