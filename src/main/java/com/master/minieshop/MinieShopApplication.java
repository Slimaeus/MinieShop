package com.master.minieshop;

import com.master.minieshop.entity.Product;
import com.master.minieshop.repository.ProductRepository;
import com.master.minieshop.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MinieShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinieShopApplication.class, args);
    }
}
