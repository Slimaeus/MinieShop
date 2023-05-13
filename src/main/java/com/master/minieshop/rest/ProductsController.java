package com.master.minieshop.rest;

import com.master.minieshop.entity.Product;
import com.master.minieshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> get() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> getById(@PathVariable("id") Integer id) {
        return productService.getById(id);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable("id") Integer id, @RequestBody Product product) {
        Optional<Product> existingProduct = productService.getById(id);
        if (existingProduct.isPresent()) {
            product.setId(id);
            return productService.save(product);
        } else {
            // Handle case when product with given id does not exist
            throw new IllegalArgumentException("Product with id " + id + " does not exist");
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        productService.deleteById(id);
    }

}
