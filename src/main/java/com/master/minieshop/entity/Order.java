package com.master.minieshop.entity;

import com.master.minieshop.common.TimeStampEntity;
import com.master.minieshop.enumeration.Gender;
import com.master.minieshop.enumeration.OrderStatus;
import com.master.minieshop.enumeration.PaymentMethod;
import com.master.minieshop.enumeration.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@Entity
@Table(name = "orders")
@EqualsAndHashCode(callSuper = true)
public class Order extends TimeStampEntity {
    @Id
    private String id;
    private String customerName;
    private String phoneNumber;
    private Gender gender = Gender.Male;
    private String address;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus = PaymentStatus.Unpaid;
    private String note;
    private double totalPrice;
    private double discountPrice;
    private double shippingCost;
    private double totalBill;
    private OrderStatus status = OrderStatus.Pending;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "promotion_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Promotion promotion;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OrderDetail> orderDetails;
}
