package com.jb.projectNo2.Controller;

import com.jb.projectNo2.Beans.Companies;
import com.jb.projectNo2.Beans.Customers;
import com.jb.projectNo2.Beans.UserDetails;
import com.jb.projectNo2.Exceptions.CompanyUserException;
import com.jb.projectNo2.Login.ClientType;
import com.jb.projectNo2.Login.LoginManager;
import com.jb.projectNo2.Repositories.CompanyRepo;
import com.jb.projectNo2.Repositories.CouponsRepo;
import com.jb.projectNo2.Repositories.CustomerRepo;
import com.jb.projectNo2.Services.AdminService;
import com.jb.projectNo2.Utils.JWTutil;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

import static java.lang.String.valueOf;

@RestController
@RequestMapping("admin")
//@RequiredArgsConstructor
@CrossOrigin (origins = "*", allowedHeaders = "*")
public class AdminController extends ClientController {
    private AdminService adminService;
    @Autowired
    private JWTutil jwtUtil;
    @Autowired
    private LoginManager loginManager;

    /**
     * a method to get HttpHeaders in the ResponseEntity Body
     * @param token the JWT token we're passing
     * @return HttpHeaders
     */

    private HttpHeaders getHeaders(String token) {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(jwtUtil.extractEmail(token));
        userDetails.setUserType((String) jwtUtil.extractAllClaims(token).get("userType"));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", jwtUtil.generateToken(userDetails));
        return httpHeaders;
    }

    @Override
    public boolean login(String email, String password) throws SQLException, InterruptedException {
        return adminService.login(email, password);
    }


    @PostMapping("Login")
    private ResponseEntity<?> userLogin (@RequestBody UserDetails userDetails) throws SQLException, InterruptedException {
        if (userDetails.getUserType().equals(ClientType.Administrator.toString())) {
            adminService = (AdminService) loginManager.login(userDetails.getEmail(), userDetails.getPassword(), ClientType.Administrator);
            if (adminService != null) {
                String token = jwtUtil.generateToken(userDetails);
                return ResponseEntity.accepted().header(jwtUtil.extractEmail(token)).body(token);
            }
        }
        return new ResponseEntity<>("Incorrect login", HttpStatus.UNAUTHORIZED);
    }
    /*
    @PostMapping("addCompany")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCompany(@RequestBody Companies companies) {

        adminService.addCompany(companies);
    }*/

    /**
     * Controller for adding new Company
     * @param token JWt for authorization
     * @param companies the Company we're adding
     * @return httpStatus + new JWT
     * @throws MalformedJwtException for wrong JWT
     * @throws CompanyUserException if exists by name or email
     */
    @PostMapping("addCompany")
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Companies companies) throws MalformedJwtException, CompanyUserException {
        if (jwtUtil.validateToken(token)) {
            adminService.addCompany(companies);
        }
        return ResponseEntity.ok().headers(getHeaders(token)).body("Company was added.");
    }

    @PostMapping("updateCompany")
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

    @PostMapping("updateCustomer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCustomer (@RequestBody Customers customers) {adminService.updateCustomer(customers);}

    @DeleteMapping("deleteCustomer/{customerId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCustomer(@PathVariable long customerId) {adminService.deleteCustomer(customerId);}

    @GetMapping("allCustomers")
    public ResponseEntity<?> getAllCustomers(){return new ResponseEntity<>(adminService.getAllCustomers(), HttpStatus.ACCEPTED);}

    @GetMapping("oneCustomer/{customerId}")
    public ResponseEntity <?> getOneCustomers(@PathVariable long customerId){
        return new ResponseEntity<>(adminService.getOneCustomer(customerId), HttpStatus.ACCEPTED);
    }



}
