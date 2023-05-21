package com.master.minieshop.entity;

import com.master.minieshop.key.PromotionDetailKey;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "promotion_details")
public class PromotionDetail {
    @EmbeddedId
    private PromotionDetailKey id;

    private int quantity;
    private int remain;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("promotionId")
    @JoinColumn(name = "promotion_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Promotion promotion;
}
