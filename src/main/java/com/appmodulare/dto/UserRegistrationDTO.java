package com.appmodulare.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

    private String fullName;
    private String username;
    private String password;
    private Boolean enabled;
    private Integer roleId;
    private String photoUrl;
}
