package com.master.minieshop.model;

import com.cloudinary.StoredFile;
import com.cloudinary.Singleton;
import com.cloudinary.Transformation;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUpload extends StoredFile {
    private String title;
    private MultipartFile file;

    public String getUrl() {
        if (version != null && format != null && publicId != null) {
            return Singleton.getCloudinary().url()
                    .resourceType(resourceType)
                    .type(type)
                    .format(format)
                    .version(version)
                    .generate(publicId);
        } else return null;
    }

    public String getThumbnailUrl() {
        if (version != null && format != null && publicId != null) {
            return Singleton.getCloudinary().url().format(format)
                    .resourceType(resourceType)
                    .type(type)
                    .version(version).transformation(new Transformation().width(150).height(150).crop("fit"))
                    .generate(publicId);
        } else return null;
    }

}
