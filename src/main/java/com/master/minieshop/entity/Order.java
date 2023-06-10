package com.master.minieshop.entity;

import com.master.minieshop.common.TimeStampEntity;
import com.master.minieshop.enumeration.Gender;
import com.master.minieshop.enumeration.OrderStatus;
import com.master.minieshop.enumeration.PaymentMethod;
import com.master.minieshop.enumeration.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return getId() != null && Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
