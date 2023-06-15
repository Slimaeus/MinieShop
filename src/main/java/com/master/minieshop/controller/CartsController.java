package com.master.minieshop.controller;

import com.master.minieshop.MinieShopApplication;
import com.master.minieshop.entity.*;
import com.master.minieshop.enumeration.PaymentMethod;
import com.master.minieshop.model.Cart;
import com.master.minieshop.model.MyUserPrincipal;
import com.master.minieshop.service.CartService;
import com.master.minieshop.service.OrderService;
import com.master.minieshop.service.ProductService;
import com.master.minieshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor

public class CartsController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    private static Logger logger = LoggerFactory.getLogger(MinieShopApplication.class);

    @GetMapping
    public String showCart(HttpSession session,
            @NotNull Model model) {
        model.addAttribute("cart", cartService.getCart(session));
        model.addAttribute("totalPrice",
                cartService.getSumPrice(session));
        model.addAttribute("totalQuantity",
                cartService.getSumQuantity(session));
        return "products/cart";
    }

    @GetMapping("/removeFromCart/{id}")
    public String removeFromCart(HttpSession session,
            @PathVariable Integer id) {
        var cart = cartService.getCart(session);
        cart.removeItems(id);
        return "redirect:/cart";
    }

    @GetMapping("/updateCart/{id}/{quantity}")
    public String updateCart(HttpSession session,
            @PathVariable Integer id,
            @PathVariable int quantity) {
        var cart = cartService.getCart(session);
        cart.updateItems(id, quantity);
        return "products/cart";
    }

    @GetMapping("/clearCart")
    public String clearCart(HttpSession session) {
        cartService.removeCart(session);
        return "redirect:/cart ";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(HttpSession session,
            @RequestParam Integer id,
            @RequestParam(defaultValue = "1") int quantity) {
        var product = productService.getById(id).orElseThrow();
        var cart = cartService.getCart(session);
        cart.addItems(new Item(id, product.getName(), product.getPrice(), quantity));
        cartService.updateCart(session, cart);

        // Logging statements
        logger.info("Item added to cart. ID: {}, Name: {}, Quantity: {}", id, product.getName(), quantity);

        return "redirect:/products";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session, Principal principal) {
        Cart cart = cartService.getCart(session);

        double totalPrice = cart.getCartItems()
                .stream()
                .map(x -> x.getPrice() * x.getQuantity())
                .mapToDouble(x -> x)
                .sum();

        double totalBill = totalPrice;

        Order order = new Order();
        order.setTotalBill(totalBill);
        order.setTotalPrice(totalPrice);
        if (principal != null) {
            AppUser user = userService.findByUsername(principal.getName());
            order.setCustomerName(user.getFullName());
            order.setPhoneNumber(user.getPhoneNumber());
            order.setGender(user.getGender());
        }

        order.setId(java.util.UUID.randomUUID().toString());
        order.setNote("Mua bánh");
        order.setPaymentMethod(PaymentMethod.Momo);

        setOrderDetailsFromCart(order, cart);

        model.addAttribute("order", order);
        return "orders/checkout";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, @Valid @ModelAttribute("order") Order order, Principal principal) {
        Cart cart = cartService.getCart(session);
        setOrderDetailsFromCart(order, cart);
        if (principal != null) {
            AppUser user = userService.findByUsername(principal.getName());
            order.setUser(user);
        }
        orderService.getSessionOrder(session);
        orderService.updateSessionOrder(session, order);
        switch (order.getPaymentMethod()) {
            case Momo:
                return "redirect:/orders/momo-pay";
            default:
                return "redirect:/orders/cash-pay";
        }

    }

    private void setOrderDetailsFromCart(@ModelAttribute("order") @Valid Order order, Cart cart) {
        Set<OrderDetail> orderDetails = new HashSet<>();

        cart.getCartItems().forEach(item -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(productService.getById(item.getProductId()).orElse(null));
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setTotalPrice(item.getQuantity() * item.getPrice());
            orderDetails.add(orderDetail);
        });

        order.setOrderDetails(orderDetails);
    }
}
