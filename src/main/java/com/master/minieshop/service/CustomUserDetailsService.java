package com.master.minieshop.service;

import com.master.minieshop.entity.AppUser;
import com.master.minieshop.model.MyUserPrincipal;
import com.master.minieshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public Optional<MyUserPrincipal> findByUsername(String username) {
        final AppUser user = userRepository.findByUserName(username);
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(new MyUserPrincipal(user));
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AppUser user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        MyUserPrincipal userDetails = new MyUserPrincipal(user);
        return userDetails;
    }
}
