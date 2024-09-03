package com.example.bankms.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotEmpty(message = "Account Number cannot be null")
//    @Pattern(regexp = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")

    @Column(columnDefinition = "varchar(50) unique")
    private String accountNumber;

    @NotNull(message = "Balance is required")
    @Positive
    @Column(columnDefinition = "decimal(10,2) not null")
    private Double balance;

    private Boolean isActive = false;

    @ManyToOne
    @JsonIgnore
    private Customer customer;
}
