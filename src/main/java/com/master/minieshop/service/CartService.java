package com.master.minieshop.service;

import com.master.minieshop.entity.Cart;
import com.master.minieshop.entity.Item;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class CartService {
    private static final String CART_SESSION_KEY = "cart";

    public Cart getCart(@NotNull HttpSession session) {
        return Optional.ofNullable((Cart) session.getAttribute(CART_SESSION_KEY))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    session.setAttribute(CART_SESSION_KEY, cart);
                    return cart;
                });
    }

    public void updateCart(@NotNull HttpSession session, Cart cart) {
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void removeCart(@NotNull HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }

    public int getSumQuantity(@NotNull HttpSession session) {
        return getCart(session).getCartItems().stream()
                .mapToInt(item -> ((Item) item).getQuantity())
                .sum();
    }

    public double getSumPrice(@NotNull HttpSession session) {
        return getCart(session).getCartItems().stream()
                .mapToDouble(item -> ((Item) item).getPrice() *
                        ((Item) item).getQuantity())
                .sum();
    }

}
