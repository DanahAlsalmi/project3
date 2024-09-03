package com.example.bankms.Repository;

import com.example.bankms.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User,Integer> {

    User findUserById(Integer id);
    User findUserByUsername(String username);
}
