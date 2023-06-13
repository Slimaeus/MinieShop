package com.master.minieshop.service;

import com.master.minieshop.entity.AppUser;
import com.master.minieshop.enumeration.Role;
import com.master.minieshop.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void save(AppUser user) {
        userRepository.save(user);
    }

    public void saveOauthUser(String email, @NotNull String username) {
        if (userRepository.findByUserName(username) != null) return;
        var user = new AppUser();
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(username));
//        user.setProvider(Provider.GOOGLE.value);
        user.setRole(Role.LoyalCustomer);
        userRepository.save(user);
    }

    public AppUser getById(Long id){return userRepository.getReferenceById(id);}
    public AppUser findByUsername(String username) { return userRepository.findByUserName(username);}
}
