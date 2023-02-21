package com.example.uploadexcelfile.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetails {
    private int id;
    private String userName;
    private String password;
    private String role;
}
