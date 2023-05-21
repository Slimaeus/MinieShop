package com.master.minieshop.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class PromotionDetailKey implements Serializable {
    @Column(name = "user_id")
    public Long userId;

    @Column(name = "order_id")
    public Long promotionId;
}
