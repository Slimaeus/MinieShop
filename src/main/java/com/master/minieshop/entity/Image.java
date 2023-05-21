package com.master.minieshop.entity;

import com.master.minieshop.common.TimeStampEntity;
import com.master.minieshop.enumeration.ImageStatus;
import com.master.minieshop.enumeration.ProductStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "images")
@EqualsAndHashCode(callSuper = true)
public class Image extends TimeStampEntity {
    @Id
    private String id;
    private String title;
    private String link;
    private ImageStatus status = ImageStatus.Closed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Product product;

}
