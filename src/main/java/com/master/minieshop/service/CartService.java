package com.master.minieshop.service;

import com.master.minieshop.entity.Cart;
import com.master.minieshop.entity.Image;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CartService {

    public void add(HttpSession session, Cart item) {
        List<Cart> cart = get(session);

        // Check if the item already exists in the cart
        for (Cart cartItem : cart) {
            if (cartItem.getName().equals(item.getName())) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                updateSessionCart(session, cart);
                return;
            }
        }

        // If the item does not exist, add it to the cart
        item.setQuantity(1);
        cart.add(item);
        updateSessionCart(session, cart);
    }

    public void decrease(HttpSession session, Cart item) {
        List<Cart> cart = get(session);

        // Check if the item exists in the cart
        for (Cart cartItem : cart) {
            if (cartItem.getName().equals(item.getName())) {
                if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                } else {
                    cart.remove(cartItem);
                }
                updateSessionCart(session, cart);
                return;
            }
        }
    }

    public List<Cart> getItems(HttpSession session) {
        return get(session);
    }

    private List<Cart> get(HttpSession session) {
        List<Cart> cart = (List<Cart>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            cart.add(new Cart("a", "a1", new Image(), 10, 2));
            cart.add(new Cart("b", "b1", new Image(), 11, 2));
            updateSessionCart(session, cart);
        }
        return cart;
    }

    private void updateSessionCart(HttpSession session, List<Cart> cart) {
        session.setAttribute("cart", cart);
    }
}
