package com.master.minieshop.entity;

import com.master.minieshop.common.TimeStampEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;

import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Category extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    @Column
    private String name;
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Product> products;
}
