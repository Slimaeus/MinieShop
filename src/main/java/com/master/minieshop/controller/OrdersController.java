package com.master.minieshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.master.minieshop.entity.Order;
import com.master.minieshop.entity.Product;
import com.master.minieshop.enumeration.OrderStatus;
import com.master.minieshop.enumeration.PaymentMethod;
import com.master.minieshop.enumeration.PaymentStatus;
import com.master.minieshop.model.MomoResponse;
import com.master.minieshop.model.MomoResult;
import com.master.minieshop.service.CartService;
import com.master.minieshop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @GetMapping({"", "index"})
    public String myOrders(Principal principal, Model model) {
        if (principal != null) {

            model.addAttribute("orders", orderService.getByUserName(principal.getName()));
            return "orders/me";
        }
        return "redirect:/home";
    }
    @GetMapping("details/{id}")
    public String details(@PathVariable("id") String id, Model model) {
        Order order = orderService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order id: " + id));
        model.addAttribute("order", order);
        return "orders/details";
    }

    @GetMapping("cash-pay")
    public String cashPay(HttpSession session, Model model) {
        Order order = orderService.getSessionOrder(session);
        if (order != null) {
            order.setPaymentMethod(PaymentMethod.Cash);
            order.setStatus(OrderStatus.Pending);
            order.setPaymentStatus(PaymentStatus.Unpaid);
            orderService.save(order);
        }

        cartService.removeCart(session);
        orderService.removeSessionOrder(session);
        model.addAttribute("order", order);
        return "orders/cash-result";
    }
    @GetMapping("momo-pay")
    public ResponseEntity<Void> momoPay(HttpSession session) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {

        Order sessionOrder = orderService.getSessionOrder(session);
        orderService.updateSessionOrder(session, sessionOrder);

        MomoResponse response = orderService.createMomoPayment(sessionOrder);

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

        Order order = orderService.getSessionOrder(session);
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
        orderService.removeSessionOrder(session);

        model.addAttribute("momoResult", result);

        return "orders/result";
    }

}
