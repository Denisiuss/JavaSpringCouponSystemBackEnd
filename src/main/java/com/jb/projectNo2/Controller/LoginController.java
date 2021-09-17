package com.jb.projectNo2.Controller;

import com.jb.projectNo2.Login.ClientType;
import com.jb.projectNo2.Login.LoginManager;
import com.jb.projectNo2.Services.AdminService;
import com.jb.projectNo2.Services.ClientService;
import com.jb.projectNo2.Services.CompanyService;
import com.jb.projectNo2.Services.CustomerService;
import com.jb.projectNo2.Utils.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("login")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

    private final LoginManager loginManager;
    //private final JWTutil jwTutil;

    @PostMapping("login")
    public ResponseEntity <?> login (String email, String password, ClientType clientType) throws SQLException, InterruptedException {
        ClientService clientService = loginManager.login(email, password, clientType);
        if (clientService != null){
            switch (clientType){
                case Administrator:
                    return new ResponseEntity<>((AdminService) clientService, HttpStatus.CREATED);
                case Company:
                    return new ResponseEntity<>((CompanyService) clientService, HttpStatus.CREATED);
                case Customer:
                    return new ResponseEntity<>((CustomerService) clientService, HttpStatus.CREATED);
            }
        }
        return null; // TODO: 18.09.2021 LoginExeption
    }
}
