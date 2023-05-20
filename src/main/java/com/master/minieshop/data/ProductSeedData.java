package com.master.minieshop.data;

import com.master.minieshop.entity.Category;
import com.master.minieshop.entity.Product;
import com.master.minieshop.enumeration.ProductStatus;
import com.master.minieshop.service.CategoryService;
import com.master.minieshop.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

//@Component
//@DependsOn("categorySeedData")
public class ProductSeedData implements CommandLineRunner {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductSeedData(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) {
        System.out.println("Product Seed");

        List<Category> categories = categoryService.getAll();
        Random random = new Random();

        Product pannaCotta = createProduct("Panna Cotta", "panna-cotta", "Creamy Italian dessert with vanilla flavor", ProductStatus.Closed, 20000,
                getRandomCategory(categories, random));
        productService.save(pannaCotta);

        Product flan = createProduct("Flan", "classic-flan", "Smooth and caramelized custard dessert", ProductStatus.Open, 20000,
                getRandomCategory(categories, random));
        productService.save(flan);

        Product tiramisu = createProduct("Tiramisu", "tiramisu", "Layered Italian dessert with coffee and mascarpone", ProductStatus.Closed, 20000,
                getRandomCategory(categories, random));
        productService.save(tiramisu);

        Product chocolateCake = createProduct("Chocolate Cake", "chocolate-cake", "Rich and moist chocolate cake", ProductStatus.Closed, 20000,
                getRandomCategory(categories, random));
        productService.save(chocolateCake);


        // Add more products as needed
    }


    private Category getRandomCategory(List<Category> categories, Random random) {
        int index = random.nextInt(categories.size());
        return categories.get(index);
    }

    private Product createProduct(String title, String name, String description, ProductStatus status, double price, Category category) {
        Product product = new Product();
        product.setTitle(title);
        product.setName(name);
        product.setDescription(description);
        product.setStatus(status);
        product.setPrice(price);
        product.setCategory(category);
        return product;
    }
}
