package com.master.minieshop.entity;

import com.master.minieshop.common.TimeStampEntity;
import com.master.minieshop.enumeration.ImageStatus;
import com.master.minieshop.enumeration.ProductStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Image extends TimeStampEntity {
    @Id
    private String id;

    private String title;
    private String link;
    private ImageStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Override
    public void onCreated() {
        super.onCreated();
        if (status == null)
            status = ImageStatus.Closed;
    }

}
