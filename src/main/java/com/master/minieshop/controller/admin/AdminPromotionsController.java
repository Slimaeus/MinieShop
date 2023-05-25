package com.master.minieshop.controller.admin;

import com.master.minieshop.entity.Product;
import com.master.minieshop.entity.Promotion;
import com.master.minieshop.service.CategoryService;
import com.master.minieshop.service.ImageService;
import com.master.minieshop.service.ProductService;
import com.master.minieshop.service.PromotionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/promotions")
public class AdminPromotionsController {
    private final PromotionService promotionService;

    public AdminPromotionsController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping({"index", ""})
    public String index(Model model) {
        model.addAttribute("promotions", promotionService.getAll());
        return "admin/promotions/index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        Promotion promotion = promotionService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid promotion id: " + id));
        model.addAttribute("promotion", promotion);
        return "admin/promotions/details";
    }


}
