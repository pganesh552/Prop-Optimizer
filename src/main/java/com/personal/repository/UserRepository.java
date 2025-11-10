package com.personal.repository;

import com.personal.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo,Long> {

    public Optional<UserInfo> findByEmail(String email);

    public boolean existsByEmail(String email);
}
