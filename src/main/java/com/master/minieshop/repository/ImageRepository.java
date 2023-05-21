package com.master.minieshop.repository;

import com.master.minieshop.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String> {
    public List<Image> findByProduct_Id(Integer id);
}
