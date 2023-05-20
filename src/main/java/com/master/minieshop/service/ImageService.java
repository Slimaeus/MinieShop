package com.master.minieshop.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.master.minieshop.common.BaseEntityService;
import com.master.minieshop.entity.Image;
import com.master.minieshop.entity.Product;
import com.master.minieshop.model.ImageUpload;
import com.master.minieshop.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class ImageService extends BaseEntityService<Image, String, ImageRepository> {
    @Autowired
    private Cloudinary cloudinary;
    public ImageService(ImageRepository repository) {
        super(repository);
    }

    public Optional<Map> uploadImage(ImageUpload imageUpload) {
        if (imageUpload.getFile() == null || imageUpload.getFile().isEmpty())
            return Optional.empty();
        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(imageUpload.getFile().getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
        } catch (IOException e) {
            return Optional.empty();
        }
        imageUpload.setPublicId((String) uploadResult.get("public_id"));
        Object version = uploadResult.get("version");
        if (version instanceof Integer) {

            imageUpload.setVersion(Long.valueOf((Integer) version));
        } else {
            imageUpload.setVersion((Long) version);
        }

        imageUpload.setSignature((String) uploadResult.get("signature"));
        imageUpload.setFormat((String) uploadResult.get("format"));
        imageUpload.setResourceType((String) uploadResult.get("resource_type"));

        return Optional.of(uploadResult);
    }

    public Optional<Image> saveUploadedImage(ImageUpload imageUpload, Map uploadResult, Product product) {
        Image image = new Image();
        image.setId(imageUpload.getPublicId());
        image.setLink((String) uploadResult.get("url"));
        image.setTitle(imageUpload.getTitle());
        image.setProduct(product);
//            image.setUpload(imageUpload);
        super.repository.save(image);

        return Optional.of(image);
    }
}
