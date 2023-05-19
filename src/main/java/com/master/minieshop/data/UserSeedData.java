package com.master.minieshop.data;

import com.master.minieshop.entity.Category;
import com.master.minieshop.entity.Product;
import com.master.minieshop.entity.AppUser;
import com.master.minieshop.enumeration.Gender;
import com.master.minieshop.enumeration.ProductStatus;
import com.master.minieshop.enumeration.Role;
import com.master.minieshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserSeedData implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        String password = passwordEncoder.encode("P@ssw0rd");
        AppUser thai = createUser("thai", "thai@gmail.com", "Nguyen Hong Thai", Gender.Male, "0123456789", password, Role.Manager);
        userRepository.save(thai);

        AppUser mei = createUser("mei", "mei@gmail.com", "Truong Thuc Van", Gender.Female, "0987654321", password, Role.Manager);
        userRepository.save(mei);

    }

    private AppUser createUser(String username, String email, String fullName, Gender gender, String phoneNumber, String password, Role role) {
        AppUser user = new AppUser();
        user.setUserName(username);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setGender(gender);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }
}
