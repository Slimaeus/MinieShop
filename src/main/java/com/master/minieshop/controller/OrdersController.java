package com.master.minieshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.master.minieshop.entity.Cart;
import com.master.minieshop.entity.Order;
import com.master.minieshop.entity.OrderDetail;
import com.master.minieshop.enumeration.OrderStatus;
import com.master.minieshop.enumeration.PaymentMethod;
import com.master.minieshop.enumeration.PaymentStatus;
import com.master.minieshop.model.MomoResponse;
import com.master.minieshop.model.MomoResult;
import com.master.minieshop.model.MyUserPrincipal;
import com.master.minieshop.service.CartService;
import com.master.minieshop.service.OrderService;
import com.master.minieshop.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;


    @GetMapping("momo-pay")
    public ResponseEntity<Void> momoPay(HttpSession session, @AuthenticationPrincipal MyUserPrincipal userPrincipal) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        Cart cart = cartService.getCart(session);

        double totalPrice = cart.getCartItems()
                .stream()
                .map(x -> x.getPrice() * x.getQuantity())
                .mapToDouble(x -> x)
                .sum();

        double totalBill = totalPrice;

        Order order = new Order();
        order.setId(java.util.UUID.randomUUID().toString());
        order.setTotalBill(totalBill);
        order.setTotalPrice(totalPrice);
        order.setCustomerName(userPrincipal.getUsername());
        order.setNote("Mua bánh");
        order.setPaymentMethod(PaymentMethod.Momo);
        Set<OrderDetail> orderDetails = new HashSet<>();

        cart.getCartItems().forEach(item -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(productService.getById(item.getProductId()).orElse(null));
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setTotalPrice(item.getQuantity() * item.getPrice());
            orderDetails.add(orderDetail);
        });

        order.setOrderDetails(orderDetails);

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProduct(productService.getAll().stream().findFirst().orElse(null));
        orderDetails.add(orderDetail1);
        order.setOrderDetails(orderDetails);

        orderService.save(order);

        MomoResponse response = orderService.createMomoPayment(order);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", response.getPayUrl())
                .build();
    }
    @GetMapping("momo-result")
    public String result(Model model, HttpSession session,
                         @RequestParam("partnerCode") String partnerCode,
                         @RequestParam("orderId") String orderId,
                         @RequestParam("requestId") String requestId,
                         @RequestParam("amount") String amount,
                         @RequestParam("orderInfo") String orderInfo,
                         @RequestParam("orderType") String orderType,
                         @RequestParam("transId") String transId,
                         @RequestParam("resultCode") int resultCode,
                         @RequestParam("message") String message,
                         @RequestParam("payType") String payType,
                         @RequestParam("responseTime") String responseTime,
                         @RequestParam("extraData") String extraData,
                         @RequestParam("signature") String signature) {

        MomoResult result = new MomoResult();
        result.setPartnerCode(partnerCode);
        result.setOrderId(orderId);
        result.setRequestId(requestId);
        result.setAmount(amount);
        result.setOrderInfo(orderInfo);
        result.setOrderType(orderType);
        result.setTransId(transId);
        result.setResultCode(resultCode);
        result.setMessage(message);
        result.setPayType(payType);
        result.setResponseTime(responseTime);
        result.setExtraData(extraData);
        result.setSignature(signature);

        Order order = orderService.getById(result.getOrderId()).orElse(null);
        if (order != null) {
            order.setPaymentMethod(PaymentMethod.Momo);
            if (result.getResultCode() == 0)
            {
                order.setStatus(OrderStatus.Completed);
                order.setPaymentStatus(PaymentStatus.Paid);
            }
            else {
                order.setStatus(OrderStatus.Failed);
                order.setPaymentStatus(PaymentStatus.Unpaid);
            }
            orderService.save(order);
        }

        cartService.removeCart(session);

        model.addAttribute("momoResult", result);

        return "orders/result";
    }

}
