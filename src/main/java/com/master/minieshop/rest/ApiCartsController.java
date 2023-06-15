package com.master.minieshop.rest;

import com.master.minieshop.entity.Item;
import com.master.minieshop.model.Cart;
import com.master.minieshop.service.CartService;
import com.master.minieshop.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class ApiCartsController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/api/cart/")
    public Cart showCart(HttpSession session) {
        return cartService.getCart(session);
    }

    @GetMapping("/api/cart/add-to-cart/{id}/{quantity}")
    public void addToCart(HttpSession session, @PathVariable(value = "id") Integer id, @PathVariable("quantity") Integer quantity) {
        var product = productService.getById(id).orElseThrow();
        var cart = cartService.getCart(session);
        cart.addItems(new Item(product.getId(), product.getName(), product.getPrice(), quantity));
        cartService.updateCart(session, cart);
    }

    @GetMapping("/api/cart/updateCart/{id}/{quantity}")
    public void updateCart(HttpSession session,
                             @PathVariable Integer id,
                             @PathVariable int quantity) {
        var cart = cartService.getCart(session);
        cart.updateItems(id, quantity);
    }
}
