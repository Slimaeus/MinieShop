package com.master.minieshop.service;

import com.master.minieshop.entity.AppUser;
import com.master.minieshop.enumeration.Role;
import com.master.minieshop.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
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

    public void delete(Long id){
        userRepository.deleteById(id);
    }
    public AppUser getById(Long id){return userRepository.getReferenceById(id);}
    public AppUser findByUsername(String username) { return userRepository.findByUserName(username);}
    public void updateResetPasswordToken(String token, String email) throws NotFoundException {
        AppUser customer = userRepository.findByEmail(email);
        if (customer != null) {
            customer.setResetPasswordToken(token);
            userRepository.save(customer);
        } else {
            throw new NotFoundException("Could not find any customer with the email " + email);
        }
    }

    public AppUser getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(AppUser customer, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedPassword);

        customer.setResetPasswordToken(null);
        userRepository.save(customer);
    }
}
