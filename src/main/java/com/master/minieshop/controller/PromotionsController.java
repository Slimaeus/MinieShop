package com.master.minieshop.controller;

import com.master.minieshop.service.PromotionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("promotions")
public class PromotionsController {
    private final PromotionService promotionService;

    public PromotionsController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping({"index", ""})
    public String index(Model model) {
        model.addAttribute("promotions", promotionService.getAll());
        return "promotions/index";
    }
}
