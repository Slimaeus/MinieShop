package com.master.minieshop.dto;

import com.master.minieshop.entity.Product;
import com.master.minieshop.model.ImageUpload;
import lombok.Data;

@Data
public class CreateProductDto {
    private Product product;
    private ImageUpload imageUpload;
}
