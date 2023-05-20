package com.master.minieshop;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Cloudinary cloudinary() {

        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dlrcjwqxz",
                "api_key", "714585465511457",
                "api_secret", "AaE_KcC4WNK4eJOLe4A5lV7ATLI",
                "secure", true));
    }
}
