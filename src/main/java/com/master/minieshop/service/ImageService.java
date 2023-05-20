package com.master.minieshop.service;

import com.master.minieshop.common.BaseEntityService;
import com.master.minieshop.entity.Image;
import com.master.minieshop.repository.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService extends BaseEntityService<Image, String, ImageRepository> {
    public ImageService(ImageRepository repository) {
        super(repository);
    }
}
