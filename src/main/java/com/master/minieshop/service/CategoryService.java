package com.master.minieshop.service;

import com.cloudinary.Cloudinary;
import com.master.minieshop.common.BaseEntityService;
import com.master.minieshop.entity.Category;
import com.master.minieshop.repository.CategoryRepository;
import com.master.minieshop.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseEntityService<Category, Integer, CategoryRepository> {
    public CategoryService(CategoryRepository repository) {
        super(repository);
    }
}
