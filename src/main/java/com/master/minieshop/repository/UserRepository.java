package com.master.minieshop.repository;

import com.master.minieshop.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUserName(String userName);
    AppUser findByEmail(String email);
}
