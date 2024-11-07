package com.quangduy.identity_service.entity;

import java.time.Instant;

import org.apache.catalina.security.SecurityUtil;

import com.quangduy.identity_service.constant.PredefinedRole;
import com.quangduy.identity_service.constant.TypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String _id;

    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String username;
    String password;

    @Column(name = "email", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String email;

    @Column(columnDefinition = "MEDIUMTEXT")
    String refreshToken;

    String address;
    boolean isVerify;
    @Enumerated(EnumType.STRING)
    TypeEnum type;
    String name;
    String role;
    String gender;
    int age;

    Instant createdAt;
    Instant updatedAt;
    String createdBy;
    String updatedBy;

}
