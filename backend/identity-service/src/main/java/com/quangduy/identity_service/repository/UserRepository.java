package com.quangduy.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quangduy.identity_service.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
