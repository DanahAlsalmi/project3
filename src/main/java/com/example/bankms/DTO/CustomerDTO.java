package com.example.bankms.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {

    //قفلت كل الفاليديشن لانها تطلع لي ايرور
//    @NotEmpty(message = "username not empty")
//    @Size(min = 4, max = 10, message = "Title must be between 5 and 100 characters")
    private String username;

//    @NotEmpty(message = "Password is mandatory")
//    @Size(min = 6, message = "Password must be at least 6 characters")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).*$",
//            message = "Password must contain at least one digit, one letter, and one special character")

    private String password;

//    @NotEmpty(message = "Name not empty")
//    @Size(min = 6, message = "Name At least 6 characters")
    private String name;

//    @Email(message = "Email should be valid")
//    @NotEmpty(message = "Email is required")
    private String email;

//    @NotNull(message = "Phone number is required")
//    @Pattern(regexp = "^(\\+9665|05|5)([0-9]{8})$", message = "Invalid phone number format")
    private String phoneNumber;

}
