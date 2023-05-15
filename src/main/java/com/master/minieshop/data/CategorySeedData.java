package com.master.minieshop.data;

import com.master.minieshop.entity.Category;
import com.master.minieshop.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategorySeedData implements CommandLineRunner {

    private final CategoryService categoryService;

    public CategorySeedData(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) {
        // Create and save category instances
        Category cake = createCategory("Cake", "cake", "Variety of delicious cakes");
        Category iceCream = createCategory("Ice Cream", "ice-cream", "Creamy frozen desserts");
        Category cookies = createCategory("Cookies", "cookies", "Delightful cookie treats");
        Category pie = createCategory("Pie", "pie", "Flaky and flavorful pies");
        Category pudding = createCategory("Pudding", "pudding", "Smooth and creamy puddings");
        Category chocolate = createCategory("Chocolate", "chocolate", "Decadent chocolate treats");
        Category tart = createCategory("Tart", "tart", "Sweet and tangy tart desserts");
        Category mousse = createCategory("Mousse", "mousse", "Light and airy mousse desserts");
        Category parfait = createCategory("Parfait", "parfait", "Layered and delightful parfaits");
        Category trifle = createCategory("Trifle", "trifle", "Trifles with layers of flavors");

        List<Category> categories = new ArrayList<>();
        categories.add(cake);
        categories.add(iceCream);
        categories.add(cookies);
        categories.add(pie);
        categories.add(pudding);
        categories.add(chocolate);
        categories.add(tart);
        categories.add(mousse);
        categories.add(parfait);
        categories.add(trifle);

        categoryService.saveAll(categories);

        // Add more categories as needed
    }

    private Category createCategory(String title, String name, String description) {
        Category category = new Category();
        category.setTitle(title);
        category.setName(name);
        category.setDescription(description);
        return category;
    }
}
