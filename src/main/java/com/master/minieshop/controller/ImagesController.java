package com.master.minieshop.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.master.minieshop.entity.Image;
import com.master.minieshop.model.ImageUpload;
import com.master.minieshop.repository.ImageRepository;
import com.master.minieshop.service.ImageService;
import com.master.minieshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/images")
public class ImagesController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;
    @RequestMapping(value = "/upload_form", method = RequestMethod.GET)
    public String uploadPhotoForm(ModelMap model) {
        model.addAttribute("imageUpload", new ImageUpload());
        return "images/upload_form";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadPhoto(@ModelAttribute ImageUpload imageUpload, BindingResult result, ModelMap model) throws IOException {
//        ImageUploadValidator validator = new Image()UploadValidator();
//        validator.validate(imageUpload, result);
        Map uploadResult = imageService.uploadImage(imageUpload).orElse(null);

        if (result.hasErrors() || uploadResult == null || uploadResult.isEmpty()){
            model.addAttribute("imageUpload", imageUpload);
            return "images/upload_form";
        } else {
            Image image = imageService.saveUploadedImage(imageUpload, uploadResult, productService.getAll().stream().findFirst().orElse(null))
                    .orElse(null);
            model.addAttribute("upload", uploadResult);
            model.addAttribute("image", image);
            return "images/upload";
        }
    }
}
