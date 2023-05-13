package com.master.minieshop.data;

import com.master.minieshop.entity.Product;
import com.master.minieshop.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductSeedData implements CommandLineRunner {

    private final ProductService productService;

    public ProductSeedData(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) {
        // Create and save product instances
        Product pannaCotta = new Product();
        pannaCotta.setTitle("Panna Cotta");
        pannaCotta.setName("panna-cotta");
        pannaCotta.setDescription("Creamy Italian dessert with vanilla flavor");
        productService.save(pannaCotta);

        Product flan = new Product();
        flan.setTitle("Flan");
        flan.setName("classic-flan");
        flan.setDescription("Smooth and caramelized custard dessert");
        productService.save(flan);

        Product tiramisu = new Product();
        tiramisu.setTitle("Tiramisu");
        tiramisu.setName("tiramisu");
        tiramisu.setDescription("Layered Italian dessert with coffee and mascarpone");
        productService.save(tiramisu);

        Product chocolateCake = new Product();
        chocolateCake.setTitle("Chocolate Cake");
        chocolateCake.setName("chocolate-cake");
        chocolateCake.setDescription("Rich and moist chocolate cake");
        productService.save(chocolateCake);

        // Add more products as needed
    }
}
