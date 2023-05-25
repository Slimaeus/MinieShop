package com.master.minieshop.entity;

import com.master.minieshop.common.TimeStampEntity;
import com.master.minieshop.enumeration.PromotionStatus;
import com.master.minieshop.enumeration.PromotionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "promotions", uniqueConstraints = {@UniqueConstraint(columnNames = "code")})
@EqualsAndHashCode(callSuper = true)
public class Promotion extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String title;
    private double value;
    private int quantity;
    private int remain;
    private PromotionStatus status = PromotionStatus.Closed;
    private PromotionType type = PromotionType.Global;
    @Temporal(TemporalType.DATE)
    private LocalDate startedAt;
    @Temporal(TemporalType.DATE)
    private LocalDate endedAt;

    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Order> orders;

    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<PromotionDetail> promotionDetails;
}
