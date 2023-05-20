package com.master.minieshop;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.master.minieshop.entity.AppUser;
import com.master.minieshop.entity.Category;
import com.master.minieshop.entity.Image;
import com.master.minieshop.entity.Product;
import com.master.minieshop.enumeration.Gender;
import com.master.minieshop.enumeration.ImageStatus;
import com.master.minieshop.enumeration.ProductStatus;
import com.master.minieshop.enumeration.Role;
import com.master.minieshop.repository.ProductRepository;
import com.master.minieshop.repository.UserRepository;
import com.master.minieshop.service.CategoryService;
import com.master.minieshop.service.ImageService;
import com.master.minieshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class MinieShopApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    public static void main(String[] args) {
        SpringApplication.run(MinieShopApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner() {
        if (userRepository.count() > 0
        || categoryService.getAll().size() > 0
        || productService.getAll().size() > 0
        || imageService.getAll().size() > 0)
            return args -> {};
        return args -> {
            Random random = new Random();

            //region User Seed Data
            String password = passwordEncoder.encode("P@ssw0rd");
            AppUser thai = createUser("thai", "thai@gmail.com", "Nguyen Hong Thai", Gender.Male, "0123456789", password, Role.Manager);
            userRepository.save(thai);

            AppUser mei = createUser("mei", "mei@gmail.com", "Truong Thuc Van", Gender.Female, "0987654321", password, Role.Manager);
            userRepository.save(mei);
            //endregion

            //region Category Seed Data
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

            categoryService.save(cake);
            categoryService.save(iceCream);
            categoryService.save(cookies);
            categoryService.save(pie);
            categoryService.save(pudding);
            categoryService.save(chocolate);
            categoryService.save(tart);
            categoryService.save(mousse);
            categoryService.save(parfait);
            categoryService.save(trifle);

            //endregion

            //region Product Seed Data
            List<Category> categories = categoryService.getAll();

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
            //endregion

            //region Image Seed Data
            List<Product> products = productService.getAll();

            Image image1 = createImage("image1", "https://images.pexels.com/photos/16307711/pexels-photo-16307711/free-photo-of-red-cabrio-car-driving-in-the-desert.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Image 1", ImageStatus.Open,
                    getRandomProduct(products, random));
            imageService.save(image1);

            Image image2 = createImage("image2", "https://images.pexels.com/photos/16494849/pexels-photo-16494849/free-photo-of-wood-light-dawn-landscape.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Image 2", ImageStatus.Closed,
                    getRandomProduct(products, random));
            imageService.save(image2);
            //endregion
        };
    }

    private Category getRandomCategory(List<Category> categories, Random random) {
        int index = random.nextInt(categories.size());
        return categories.get(index);
    }

    private Product getRandomProduct(List<Product> products, Random random) {
        int index = random.nextInt(products.size());
        return products.get(index);
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

    private Image createImage(String id, String link, String title, ImageStatus status, Product product) {
        Image image = new Image();
        image.setId(id);
        image.setLink(link);
        image.setTitle(title);
        image.setStatus(status);
        image.setProduct(product);

        return image;
    }

    private Category createCategory(String title, String name, String description) {
        Category category = new Category();
        category.setTitle(title);
        category.setName(name);
        category.setDescription(description);
        return category;
    }

    private AppUser createUser(String username, String email, String fullName, Gender gender, String phoneNumber, String password, Role role) {
        AppUser user = new AppUser();
        user.setUserName(username);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setGender(gender);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }
}
