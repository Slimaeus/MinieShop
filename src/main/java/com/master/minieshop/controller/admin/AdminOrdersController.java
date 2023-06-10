package com.master.minieshop.controller.admin;

import com.master.minieshop.entity.Order;
import com.master.minieshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/orders")
public class AdminOrdersController {
    @Autowired
    private OrderService orderService;

    @GetMapping({"index", ""})
    public String index(Model model) {
        model.addAttribute("orders", orderService.getAll());
        return "admin/orders/index";
    }

    @GetMapping ("/details/{id}")
    public String details(@PathVariable("id") String id, Model model) {
        Order order = orderService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order id: " + id));
        model.addAttribute("order", order);
        return "admin/orders/details";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("order", new Order());
        return "admin/orders/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("order") Order order) {
        orderService.save(order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model) {
        Order order = orderService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order id: " + id));
        model.addAttribute("order", order);
        return "admin/orders/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, @ModelAttribute("order") Order order) {
        orderService.save(order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, Model model) {
        Order order = orderService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order id: " + id));
        model.addAttribute("order", order);
        return "admin/orders/delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        orderService.deleteById(id);
        return "redirect:/admin/orders";
    }
}
