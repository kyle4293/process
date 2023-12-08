package com.example.process.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<User, String> {     //spring data jpa

}
