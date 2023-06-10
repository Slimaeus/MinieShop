package com.master.minieshop.controller;

import com.master.minieshop.entity.Cart;
import com.master.minieshop.entity.Item;
import com.master.minieshop.entity.Image;
import com.master.minieshop.service.CartService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;

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

    @GetMapping
    public String showCart(HttpSession session,
            @NotNull Model model) {
        model.addAttribute("cart", cartService.getCart(session));
        model.addAttribute("totalPrice",
                cartService.getSumPrice(session));
        model.addAttribute("totalQuantity",
                cartService.getSumQuantity(session));
        return "book/cart";
    }

    @GetMapping("/removeFromCart/{id}")
    public String removeFromCart(HttpSession session,
            @PathVariable String id) {
        var cart = cartService.getCart(session);
        cart.removeItems(id);
        return "redirect:/cart";
    }

    @GetMapping("/updateCart/{id}/{quantity}")
    public String updateCart(HttpSession session,
            @PathVariable String id,
            @PathVariable int quantity) {
        var cart = cartService.getCart(session);
        cart.updateItems(id, quantity);
        return "book/cart";
    }

    @GetMapping("/clearCart")
    public String clearCart(HttpSession session) {
        cartService.removeCart(session);
        return "redirect:/cart ";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(HttpSession session,
            @RequestParam String id,
            @RequestParam String name,
            @RequestParam double price,
            @RequestParam(defaultValue = "1") int quantity) {
        var cart = cartService.getCart(session);
        cart.addItems(new Item(id, name, price, quantity));
        cartService.updateCart(session, cart);
        return "redirect:/books";
    }
}
