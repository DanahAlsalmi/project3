package com.example.bankms.Service;


import com.example.bankms.Api.ApiException;
import com.example.bankms.Model.User;
import com.example.bankms.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    //Get All
    public List<User> findAll(){
        return authRepository.findAll();
    }

//    public User registerUser(User user) {
//        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
//        user.setPassword(hash);
//        return authRepository.save(user);
//    }

//    //Register
//    public void register(User user) {
//        user.setRole("USER");
//        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
//        user.setPassword(hash);
//        authRepository.save(user);
//    }

//    //Update
//    public void update(Integer id,User user) {
//        User u=authRepository.findUserById(id);
//        if(u==null){
//            throw new ApiException("User not found");
//        }
//        u.setUsername(user.getUsername());
//        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
//        u.setPassword(hash);
//        authRepository.save(u);
//
//    }
//
//    //Delete
//    public void delete(Integer id) {
//        User u=authRepository.findUserById(id);
//        if(u==null){
//            throw new ApiException("User not found");
////        }
//    }
}

