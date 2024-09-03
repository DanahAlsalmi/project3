package com.example.bankms.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "varchar(10) not null unique")
    private String username;


//    @Column(columnDefinition = "varchar (25) not null")
//    @Check(constraints = "LENGTH(password) >= 6")
//    @Check(constraints = "password ~ '^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$'")
    private String password;


    @Column(columnDefinition = "varchar(30) not null unique")
    private String name;



    @Column(columnDefinition = "varchar(40) not null unique ")
    //@Check(constraints = "email LIKE '%@%'")
    private String email;

    @NotEmpty(message = "Role is required")
    @Pattern(regexp = "CUSTOMER|EMPLOYEE|ADMIN", message = "role must be CUSTOMER|EMPLOYEE|ADMIN")
    @Column(columnDefinition = "varchar(12) not null")
    //@Check(constraints = "role IN ('CUSTOMER','EMPLOYEE','ADMIN')")
    private String role;


    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Employee employee;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Customer customer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
