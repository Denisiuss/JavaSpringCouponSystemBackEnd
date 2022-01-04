package com.jb.projectNo2.Beans;

import com.jb.projectNo2.Login.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDetails {
    private long id;
    private String email;
    private String password;
    private String userType;


}
