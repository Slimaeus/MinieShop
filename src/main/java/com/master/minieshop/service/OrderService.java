package com.master.minieshop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.minieshop.common.BaseEntityService;
import com.master.minieshop.encryption.MomoSecurity;
import com.master.minieshop.entity.Order;
import com.master.minieshop.model.Cart;
import com.master.minieshop.model.MomoItem;
import com.master.minieshop.model.MomoRequest;
import com.master.minieshop.model.MomoResponse;
import com.master.minieshop.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService extends BaseEntityService<Order, String, OrderRepository> {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Environment env;
    private static final String ORDER_SESSION_KEY = "order";
    public OrderService(OrderRepository repository) {
        super(repository);
    }
    public List<Order> getByUserName(String username) { return repository.findByUser_UserName(username);}
    public MomoResponse createMomoPayment(Order order) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        String endPoint = env.getProperty("payment.momo.endpoint");
        String partnerCode = env.getProperty("payment.momo.partner-code");
        String accessKey = env.getProperty("payment.momo.access-key");
        String secretKey = env.getProperty("payment.momo.secret-key");
        String redirectUrl = "http://localhost:8080/orders/momo-result";
        String ipnUrl = env.getProperty("payment.momo.ipnUrl");
        String requestType = "captureWallet";
        String orderInfo = "Khách hàng: " + order.getCustomerName() + " thanh toán";
        String amount = String.valueOf(order.getTotalBill()).replaceAll("[^\\d]", ""); // Xóa dấu phẩy
        String orderId = order.getId();
        String requestId = java.util.UUID.randomUUID().toString();
        String extraData = "";
        List<MomoItem> items = order.getOrderDetails().stream().map(x -> new MomoItem(x.getProduct().getId().toString(), x.getProduct().getName())).toList();

        String rawHash = "accessKey=" + accessKey +
                "&amount=" + amount +
                "&extraData=" + extraData +
                "&ipnUrl=" + ipnUrl +
                "&orderId=" + orderId +
                "&orderInfo=" + orderInfo +
                "&partnerCode=" + partnerCode +
                "&redirectUrl=" + redirectUrl +
                "&requestId=" + requestId +
                "&requestType=" + requestType;

        MomoSecurity crypto = new MomoSecurity();
        // Sign signature SHA256
        String signature = crypto.signSHA256(rawHash, secretKey);

        MomoRequest momoMessage = new MomoRequest();
        momoMessage.setPartnerCode(partnerCode);
        momoMessage.setPartnerName("MinieShop");
        momoMessage.setStoreId("MinieShop");
        momoMessage.setRequestId(requestId);
        momoMessage.setAmount(amount);
        momoMessage.setOrderId(orderId);
        momoMessage.setOrderInfo(orderInfo);
        momoMessage.setRedirectUrl(redirectUrl);
        momoMessage.setIpnUrl(ipnUrl);
        momoMessage.setLang("vi");
        momoMessage.setExtraData(extraData);
        momoMessage.setRequestType(requestType);
        momoMessage.setItems(items);
        momoMessage.setSignature(signature);

        String result = restTemplate.postForObject(endPoint, momoMessage, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(result, MomoResponse.class);
    }

    public Order getSessionOrder(@NotNull HttpSession session) {
        return Optional.ofNullable((Order) session.getAttribute(ORDER_SESSION_KEY))
                .orElseGet(() -> {
                    Order order = new Order();
                    session.setAttribute(ORDER_SESSION_KEY, order);
                    return order;
                });
    }
    public void updateSessionOrder(@NotNull HttpSession session, Order order) {
        session.setAttribute(ORDER_SESSION_KEY, order);
    }
    public void removeSessionOrder(@NotNull HttpSession session) {
        session.removeAttribute(ORDER_SESSION_KEY);
    }
}
