package com.bridgelabz.addressBookApp.repository;

import com.bridgelabz.addressBookApp.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AuthUserRepository extends JpaRepository<AuthUser,Long> {
}
