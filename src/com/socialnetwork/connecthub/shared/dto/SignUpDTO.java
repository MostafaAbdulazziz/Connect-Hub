package com.socialnetwork.connecthub.shared.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SignUpDTO {
    private String email;
    private String username;
    private String password;
    private Date dateOfBirth;

}