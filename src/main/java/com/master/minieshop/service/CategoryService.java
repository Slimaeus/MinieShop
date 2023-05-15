package com.master.minieshop.service;

import com.master.minieshop.common.BaseEntityService;
import com.master.minieshop.entity.Category;
import com.master.minieshop.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseEntityService<Category, Integer, CategoryRepository> {
    public CategoryService(CategoryRepository repository) {
        super(repository);
    }
}
