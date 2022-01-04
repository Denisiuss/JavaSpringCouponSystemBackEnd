package com.jb.projectNo2.Controller;

import com.jb.projectNo2.Beans.UserDetails;
import com.jb.projectNo2.Login.ClientType;
import com.jb.projectNo2.Login.LoginManager;
import com.jb.projectNo2.Services.AdminService;
import com.jb.projectNo2.Services.ClientService;
import com.jb.projectNo2.Services.CompanyService;
import com.jb.projectNo2.Services.CustomerService;
import com.jb.projectNo2.Utils.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

import static com.jb.projectNo2.Login.ClientType.*;

@RestController
@RequestMapping("login")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController  {

    private final LoginManager loginManager;
    private final JWTutil jwTutil;


    protected AdminService adminService;
    protected CompanyService companyService;
    protected CustomerService customerService;



    @PostMapping("login")
    public ResponseEntity <?> login (@RequestBody UserDetails userDetails) throws SQLException, InterruptedException {

        if (userDetails != null){
            switch (userDetails.getUserType()){
                case ("Administrator"):
                    adminService = (AdminService) loginManager.login(userDetails.getEmail(), userDetails.getPassword(), Administrator);
                    if (adminService != null) {
                        String token = jwTutil.generateToken(userDetails);
                        return ResponseEntity.accepted().header(jwTutil.extractEmail(token)).body(token);
                    }
                        return new ResponseEntity<>("Incorrect login", HttpStatus.UNAUTHORIZED);

                case ("Company"):
                    companyService = (CompanyService) loginManager.login(userDetails.getEmail(), userDetails.getPassword(), Company);
                    if (companyService != null) {
                        String token = jwTutil.generateToken(userDetails);
                        return ResponseEntity.accepted().header(jwTutil.extractEmail(token)).body(token);
                    }

                        return new ResponseEntity<>("Incorrect login", HttpStatus.UNAUTHORIZED);
                case ("Customer"):
                    customerService = (CustomerService) loginManager.login(userDetails.getEmail(), userDetails.getPassword(), Customer);
                    if (customerService != null) {
                        String token = jwTutil.generateToken(userDetails);
                        return ResponseEntity.accepted().header(jwTutil.extractEmail(token)).body(token);
                    }

                    return new ResponseEntity<>("Incorrect login", HttpStatus.UNAUTHORIZED);
            }
        }
        return null; // TODO: 18.09.2021 LoginExeption
    }


}
