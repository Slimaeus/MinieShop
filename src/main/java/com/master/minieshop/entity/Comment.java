package com.master.minieshop.entity;

import com.master.minieshop.common.TimeStampEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "comments")
@EqualsAndHashCode(callSuper = true)
public class Comment extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int rate;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
}
