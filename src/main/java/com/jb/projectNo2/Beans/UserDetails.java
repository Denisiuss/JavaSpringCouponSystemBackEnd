package com.jb.projectNo2.Beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDetails {
    private String email;
    private String password;
    private String userType;


}
