package com.master.minieshop.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class OrderDetailKey implements Serializable {
    @Column(name = "product_id")
    public Integer productId;

    @Column(name = "order_id")
    public String orderId;
}
