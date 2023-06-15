package com.master.minieshop.service;

import com.master.minieshop.entity.AppUser;
import com.master.minieshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
