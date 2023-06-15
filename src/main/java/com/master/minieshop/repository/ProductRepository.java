package com.master.minieshop.repository;

import com.master.minieshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {
    public Optional<Product> findByName(String name);
    public List<Product> findByCategory_Id(Integer categoryId);
}
