package com.master.minieshop.repository;

import com.master.minieshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    public Product findByName(String name);
}
