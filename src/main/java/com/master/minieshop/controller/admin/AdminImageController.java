package com.master.minieshop.controller.admin;

import com.master.minieshop.service.CategoryService;
import com.master.minieshop.service.ImageService;
import com.master.minieshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/images")
public class AdminImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping
    public String index(Model model){
        var currentImage = imageService.getAll();
        model.addAttribute("Image",currentImage);
        return "admin/Image/index";
    }

    @GetMapping("/create")
    public String Image(Model model){
        model.addAttribute("image",new image());
        return "admin/Image/create";
    }

    @PostMapping("/create")
    public String Image(@ModelAttribute("image") image image) {
        imageService.save(image);
        return "redirect:/admin/Image";
    }

    @GetMapping("/edit/{id}")
    public String Image(@PathVariable("id") Integer id, Model model) {
        image image = imageService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid image id: " + id));
        model.addAttribute("image", image);
        return "admin/Image/edit";
    }

    @PostMapping("/edit/{id}")
    public String Image(@PathVariable("id") Integer id, @ModelAttribute("image") image image) {
        imageService.save(image);
        return "redirect:/admin/Image";
    }

    @GetMapping("/delete/{id}")
    public String Image(@PathVariable("id") Integer id, Model model) {
        image image = imageService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid image id: " + id));
        model.addAttribute("image", image);
        return "admin/Image/delete";
    }

    @PostMapping("/delete/{id}")
    public String Image(@PathVariable("id") Integer id) {
        imageService.deleteById(id);
        return "redirect:/admin/Image";
    }

}
