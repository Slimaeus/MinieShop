package com.master.minieshop.controller.admin;

import com.master.minieshop.dto.CreateProductDto;
import com.master.minieshop.entity.Image;
import com.master.minieshop.entity.Product;
import com.master.minieshop.model.ImageUpload;
import com.master.minieshop.service.CategoryService;
import com.master.minieshop.service.ImageService;
import com.master.minieshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("admin/products")
public class AdminProductsController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ImageService imageService;

    public AdminProductsController(ProductService productService, CategoryService categoryService, ImageService imageService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.imageService = imageService;
    }

    @GetMapping({"index", ""})
    public String index(Model model) {
        model.addAttribute("products", productService.getAll());
        return "admin/products/index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));
        model.addAttribute("images", imageService.getImagesByProductId(id));
        model.addAttribute("product", product);
        return "admin/products/details";
    }

    @GetMapping("/create")
    public String create(Model model) {
        CreateProductDto dto = new CreateProductDto();
        dto.setProduct(new Product());
        dto.setImageUpload(new ImageUpload());
        model.addAttribute("createProductDto", dto);
        model.addAttribute("categories", categoryService.getAll());
        return "admin/products/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("createProductDto") CreateProductDto createProductDto,
                         BindingResult result,
                         Model model) {
        Map uploadResult = imageService.uploadImage(createProductDto.getImageUpload()).orElse(null);

        if (result.hasErrors() || uploadResult == null || uploadResult.isEmpty()){

            CreateProductDto dto = new CreateProductDto();
            dto.setProduct(new Product());
            dto.setImageUpload(new ImageUpload());

            model.addAttribute("categories", categoryService.getAll());

//            model.addAttribute("product", new Product());
//            model.addAttribute("categories", categormodel.addAttribute("createProductDto", dto);yService.getAll());
            return "admin/products/create";
        }

        Product product = createProductDto.getProduct();
        productService.save(product);

        Image image = imageService.saveUploadedImage(createProductDto.getImageUpload(), uploadResult, product)
                .orElse(null);
        Set<Image> images = new HashSet<>();
        images.add(image);
        product.setImages(images);

        productService.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("product", product);
        return "admin/products/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, @ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));
        model.addAttribute("product", product);
        return "admin/products/delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }
}
