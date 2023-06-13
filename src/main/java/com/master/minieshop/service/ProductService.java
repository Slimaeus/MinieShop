package com.master.minieshop.service;

import com.master.minieshop.common.BaseEntityService;
import com.master.minieshop.entity.Product;
import com.master.minieshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseEntityService<Product, Integer, ProductRepository> {
    public ProductService(ProductRepository repository) {
        super(repository);
    }
    public Product findByName(String name) {
        return repository.findByName(name);
    }
}
