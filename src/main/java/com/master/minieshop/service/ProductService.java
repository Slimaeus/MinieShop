package com.master.minieshop.service;

import com.master.minieshop.common.BaseEntityService;
import com.master.minieshop.entity.Product;
import com.master.minieshop.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService extends BaseEntityService<Product, Integer, ProductRepository> {
    public ProductService(ProductRepository repository) {
        super(repository);
    }
    public Optional<Product> findByName(String name) {
        return repository.findByName(name);
    }
    public Page<Product> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
