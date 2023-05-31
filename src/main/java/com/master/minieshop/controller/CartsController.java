package com.master.minieshop.controller;

import com.master.minieshop.entity.Cart;
import com.master.minieshop.entity.Image;
import com.master.minieshop.service.CartService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/carts")

public class CartsController {
    @Autowired
    private CartService cartService;

    public CartsController() {
    }

    @GetMapping({ "index", "" })
    public List<Cart> index(Model model, HttpSession session) {
        var result = cartService.getItems(session);
        model.addAttribute("products", result);
        return result;
    }

    @PostMapping("/addItem")
    public String addItemToCart(HttpSession session, @RequestParam("code") String code) {
        Cart item = new Cart("a", code, new Image(), 1, 1);
        cartService.add(session, item);
        return "redirect:/index";
    }
// TODO: FIX decrease and remove item in cart.
// TODO: calculate the quantity when modifying
// TODO: report: sum of money, supcription.
    @PostMapping("/decreaseItem")
    public String decreaseItemFromCart(HttpSession session, @RequestParam("code") String code) {
        Cart item = new Cart("a", code, new Image(), 1, 1);
        cartService.decrease(session, item);
        return "redirect:/index";
    }
    @PostMapping("/removeItem")
    public String removeItemFromCart(HttpSession session, @RequestParam("code") String code) {
        Cart item = new Cart("a", code, new Image(), 1, 1);
        cartService.decrease(session, item);
        return "redirect:/index";
    }

    public List<Cart> get(HttpSession session) {
        return cartService.getItems(session);
    }

}
