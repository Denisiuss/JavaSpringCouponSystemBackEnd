package com.jb.projectNo2.Controller;

import com.jb.projectNo2.Beans.Companies;
import com.jb.projectNo2.Beans.Customers;
import com.jb.projectNo2.Beans.UserDetails;
import com.jb.projectNo2.Login.ClientType;
import com.jb.projectNo2.Login.LoginManager;
import com.jb.projectNo2.Services.AdminService;
import com.jb.projectNo2.Utils.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
@CrossOrigin (origins = "*", allowedHeaders = "*")
public class AdminController {
    AdminService adminService;
    private final JWTutil jwTutil;
    //private final LoginManager loginManager;

    /*
    @PostMapping("Login")
    private ResponseEntity<?> userLogin (@RequestBody UserDetails userDetails) {
        if (userDetails.getUserType().equals(ClientType.Administrator.toString())) {
            adminService = (AdminService) loginManager.login(userDetails.getEmail(), userDetails.getPassword(), ClientType.Administrator);
            if (adminService != null) {
                String token = jwTutil.generateToken(userDetails);
                return ResponseEntity.accepted().header().body(token);
            }
        }
        return new ResponseEntity<>("Incorrect login", HttpStatus.UNAUTHORIZED);
    }*/

    @PostMapping("addCompany")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCompany(@RequestBody Companies companies) {adminService.addCompany(companies);}

    @PostMapping("update company")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCompany(@RequestBody Companies companies) {adminService.updateCompany(companies);}

    @DeleteMapping("deleteCompany/{companyId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCompany(@PathVariable long companyId) {adminService.deleteCompany(companyId);}

    @GetMapping("allCompanies")
    public ResponseEntity <?> getAllCompanies(){return new ResponseEntity<>(adminService.getAllCompanies(), HttpStatus.ACCEPTED);}

    @GetMapping("oneCompany/{companyId}")
    public ResponseEntity <?> getOneCompany(@PathVariable long companyId){
        return new ResponseEntity<>(adminService.getOneCompany(companyId), HttpStatus.ACCEPTED);
    }

    @PostMapping("addCustomer")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomer (@RequestBody Customers customers) {adminService.addCustomer(customers);}


}
