package com.master.minieshop.data;

import com.master.minieshop.entity.Image;
import com.master.minieshop.entity.Product;
import com.master.minieshop.enumeration.ImageStatus;
import com.master.minieshop.service.ImageService;
import com.master.minieshop.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

//@Component
//@DependsOn("productSeedData")
public class ImageSeedData implements CommandLineRunner {
    private final ImageService imageService;
    private final ProductService productService;

    public ImageSeedData(ImageService imageService, ProductService productService) {
        this.imageService = imageService;
        this.productService = productService;
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Image Seed");

        List<Product> products = productService.getAll();
        Random random = new Random();

        Image image1 = createImage("image1", "https://images.pexels.com/photos/16307711/pexels-photo-16307711/free-photo-of-red-cabrio-car-driving-in-the-desert.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Image 1", ImageStatus.Open,
                getRandomProduct(products, random));
        imageService.save(image1);

        Image image2 = createImage("image2", "https://images.pexels.com/photos/16494849/pexels-photo-16494849/free-photo-of-wood-light-dawn-landscape.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Image 2", ImageStatus.Closed,
                getRandomProduct(products, random));
        imageService.save(image2);

    }

    private Product getRandomProduct(List<Product> products, Random random) {
        int index = random.nextInt(products.size());
        return products.get(index);
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
}
