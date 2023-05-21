package com.master.minieshop.entity;

import com.master.minieshop.common.TimeStampEntity;
import com.master.minieshop.enumeration.CategoryStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@Entity
@Table(name = "categories", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
@EqualsAndHashCode(callSuper = true)
public class Category extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String name;
    private String description;
    private CategoryStatus status = CategoryStatus.Closed;

    @OneToMany(mappedBy = "category",
            cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Product> products;
}
