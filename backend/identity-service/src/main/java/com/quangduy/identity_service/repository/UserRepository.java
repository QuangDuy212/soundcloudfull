package com.quangduy.identity_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.quangduy.identity_service.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    Page<User> findAll(Pageable pageable);

    User findByUsername(String username);
}
