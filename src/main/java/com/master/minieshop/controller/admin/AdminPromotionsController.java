package com.master.minieshop.controller.admin;

import com.master.minieshop.entity.Promotion;
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

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("promotion", new Promotion());
        return "admin/promotions/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("promotion") Promotion promotion) {
        promotionService.save(promotion);
        return "redirect:/admin/promotions";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Promotion promotion = promotionService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid promotion id: " + id));
        model.addAttribute("promotion", promotion);
        return "admin/promotions/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, @ModelAttribute("promotion") Promotion promotion) {
        promotionService.save(promotion);
        return "redirect:/admin/promotions";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        Promotion promotion = promotionService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid promotion id: " + id));
        model.addAttribute("promotion", promotion);
        return "admin/promotions/delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        promotionService.deleteById(id);
        return "redirect:/admin/promotions";
    }
}
