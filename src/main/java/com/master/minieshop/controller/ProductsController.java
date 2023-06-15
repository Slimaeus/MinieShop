package com.master.minieshop.controller;

import com.master.minieshop.entity.Image;
import com.master.minieshop.entity.Product;
import com.master.minieshop.service.CategoryService;
import com.master.minieshop.service.ImageService;
import com.master.minieshop.service.ProductService;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductsController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ImageService imageService;

    public ProductsController(ProductService productService, CategoryService categoryService, ImageService imageService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.imageService = imageService;
    }

    @GetMapping("desserts/{name}")
    public String getProduct(@PathVariable("name") String name, Model model) {
        Product product = productService.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product name: " + name));
        model.addAttribute("product", product);
        return "products/info";
    }

    @GetMapping({"products/index", "products"})
    public String index(Model model,
                        @RequestParam("index") @Nullable Integer index,
                        @RequestParam("size") @Nullable Integer size,
                        @RequestParam("category") @Nullable Integer categoryId) {
        int pageIndex = index == null || index <= 0 ? 1 : index;
        int pageSize = size == null || size <= 0 ? 10 : size;
        Pageable paging = PageRequest.of(pageIndex, pageSize, Sort.by("title"));

        List<Product> products = new ArrayList<>();
        if (categoryId != null) {
            products = productService.getByCategoryId(categoryId);
            model.addAttribute("selectedCategory", categoryService.getById(categoryId).orElseThrow());
        } else {
            products = productService.getAll();
        }
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("products/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));
        // List<Image> images = product.getImages().stream().toList();
        model.addAttribute("images", imageService.getImagesByProductId(id));
        model.addAttribute("product", product);
        return "products/details";
    }

    @GetMapping("products/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "products/create";
    }

    @PostMapping("products/create")
    public String create(@ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("products/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("product", product);
        return "products/edit";
    }

    @PostMapping("products/edit/{id}")
    public String edit(@PathVariable("id") Integer id, @ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("products/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));
        model.addAttribute("product", product);
        return "products/delete";
    }

    @PostMapping("products/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}
