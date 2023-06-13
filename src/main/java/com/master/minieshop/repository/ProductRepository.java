package com.master.minieshop.repository;

import com.master.minieshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {
    public Product findByName(String name);
}
