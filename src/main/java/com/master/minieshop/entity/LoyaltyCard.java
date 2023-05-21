package com.master.minieshop.entity;

import com.master.minieshop.common.TimeStampEntity;
import com.master.minieshop.enumeration.LoyaltyCardStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "loyalty_cards")
@EqualsAndHashCode(callSuper = true)
public class LoyaltyCard extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int point;
    private LoyaltyCardStatus status;

    @OneToOne(mappedBy = "loyaltyCard", fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AppUser user;
}
