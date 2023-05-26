package com.master.minieshop.controller.admin;

import com.master.minieshop.entity.Image;
import com.master.minieshop.service.ImageService;
import com.master.minieshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/images")
public class AdminImagesController {
    private final ImageService imageService;
    private final ProductService productService;

    public AdminImagesController(ImageService imageService, ProductService productService) {
        this.imageService = imageService;
        this.productService = productService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("images", imageService.getAll());
        return "admin/images/index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") String id, Model model) {
        Image image = imageService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid image id: " + id));
        model.addAttribute("image", image);
        return "admin/images/details";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("image", new Image());
        model.addAttribute("products", productService.getAll());
        return "admin/images/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("image") Image image) {
        imageService.save(image);
        return "redirect:/admin/images";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model) {
        Image image = imageService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid image id: " + id));
        model.addAttribute("image", image);
        model.addAttribute("products", productService.getAll());
        return "admin/images/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, @ModelAttribute("image") Image image) {
        imageService.save(image);
        return "redirect:/admin/images";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, Model model) {
        Image image = imageService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid image id: " + id));
        model.addAttribute("image", image);
        return "admin/images/delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        imageService.deleteById(id);
        return "redirect:/admin/images";
    }

}
