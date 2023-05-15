package com.master.minieshop.entity;

import com.master.minieshop.common.TimeStampEntity;
import com.master.minieshop.enumeration.ProductStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Product extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    @Column(unique = true)
    private String name;
    private String description;
    private ProductStatus status;
    private double price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Override
    public void onCreated() {
        super.onCreated();
        if (status == null)
            status = ProductStatus.Closed;
    }
}
