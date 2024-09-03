package com.example.bankms.Service;

import com.example.bankms.Api.ApiException;
import com.example.bankms.Model.User;
import com.example.bankms.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=authRepository.findUserByUsername(username);
        if(user==null){
            throw new ApiException("wrong username or password");
        }
        return user;
    }
}
