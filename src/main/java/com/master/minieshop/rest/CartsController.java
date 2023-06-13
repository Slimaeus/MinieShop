package com.master.minieshop.rest;

import com.master.minieshop.entity.Item;
import com.master.minieshop.service.CartService;
import com.master.minieshop.service.OrderService;
import com.master.minieshop.service.ProductService;
import com.master.minieshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/carts")
public class CartsController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Transactional

    @PostMapping("/add-to-cart")
    public void addToCart(HttpSession session,
                            @RequestParam Integer id,
                            @RequestParam(defaultValue = "1") int quantity) {
        var product = productService.getById(id).orElseThrow();
        var cart = cartService.getCart(session);
        cart.addItems(new Item(id, product.getName(), product.getPrice(), quantity));
        cartService.updateCart(session, cart);
    }

    @Transactional
    @GetMapping("/updateCart/{id}/{quantity}")
    public void updateCart(HttpSession session,
                             @PathVariable Integer id,
                             @PathVariable int quantity) {
        var cart = cartService.getCart(session);
        cart.updateItems(id, quantity);
    }
}
