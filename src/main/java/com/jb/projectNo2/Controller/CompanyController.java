package com.jb.projectNo2.Controller;

import com.jb.projectNo2.Beans.Categories;
import com.jb.projectNo2.Beans.Companies;
import com.jb.projectNo2.Beans.Coupons;
import com.jb.projectNo2.Beans.UserDetails;
import com.jb.projectNo2.Exceptions.CouponException;
import com.jb.projectNo2.Login.ClientType;
import com.jb.projectNo2.Login.LoginManager;
import com.jb.projectNo2.Services.AdminService;
import com.jb.projectNo2.Services.CompanyService;
import com.jb.projectNo2.Utils.JWTutil;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("company")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CompanyController {
    CompanyService companyService;
    @Autowired
    private JWTutil jwtUtil;
    @Autowired
    private LoginManager loginManager;
    private long companyId;

    private HttpHeaders getHeaders(String token){
        //create new userDetail and DI
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(jwtUtil.extractEmail(token));
        userDetails.setUserType((String)jwtUtil.extractAllClaims(token).get("userType"));
        //send ok with header of new token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization",jwtUtil.generateToken(userDetails));
        return httpHeaders;
    }

    @PostMapping("Login")
    private ResponseEntity<?> userLogin (@RequestBody UserDetails userDetails) throws SQLException, InterruptedException {
        if (userDetails.getUserType().equals(ClientType.Company.toString())) {
            companyService = (CompanyService) loginManager.login(userDetails.getEmail(), userDetails.getPassword(), ClientType.Company);
            if (companyService != null) {
                userDetails.setId(companyId=companyService.getCompanyId(userDetails.getEmail()));
                String token = jwtUtil.generateToken(userDetails);
                return ResponseEntity.accepted().header(jwtUtil.extractEmail(token)).body(token);
            }
        }
        return new ResponseEntity<>("Incorrect login", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("addCoupon")
    public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupons coupons) throws MalformedJwtException, CouponException {
        if (jwtUtil.validateToken(token)) {
            companyService.addCoupon(coupons);
        }
        return ResponseEntity.ok().headers(getHeaders(token)).body("Coupon was added");
    }
    /*
    @PostMapping("addCoupon")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCoupon(@RequestBody Coupons coupons){companyService.addCoupon(coupons);}
    */

    @PostMapping("updateCoupon")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupons coupons) throws MalformedJwtException, CouponException {
        if (jwtUtil.validateToken(token)) {
            companyService.updateCoupon(coupons);
        }
        return ResponseEntity.ok().headers(getHeaders(token)).body("Customer has been updated!");
    }
    /*
    @PostMapping("updateCoupon")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCoupon(@RequestBody Coupons coupons) {companyService.updateCoupon(coupons);}
    */

    @DeleteMapping("deleteCoupon/{couponId}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable long couponId) throws MalformedJwtException, CouponException {
        if (jwtUtil.validateToken(token)) {
            companyService.deleteCoupon(couponId);
        }
        return ResponseEntity.ok().headers(getHeaders(token)).body("Coupon was deleted Successfully");
    }
    /*
    @DeleteMapping("deleteCoupon/{couponId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCoupon(@PathVariable long couponId) {companyService.deleteCoupon(couponId);}
    */

    @GetMapping("getCoupons")
    public ResponseEntity<?> getAllCompanyCoupons (@RequestHeader(name = "Authorization") String token) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(companyService.getCompanyCoupons());
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
    /*
    @GetMapping("getCoupons")
    public ResponseEntity<?> getCompanyCoupons () {
        return new ResponseEntity<>(companyService.getCompanyCoupons(), HttpStatus.ACCEPTED);
    }*/

    @GetMapping("getOneCouponById/{id}")
    public ResponseEntity<?> getOneCouponById(@RequestHeader(name = "Authorization") String token, @PathVariable long id) throws MalformedJwtException, CouponException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(companyService.getCompanyCouponById(id));
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
    /*
    @GetMapping("getOneCouponById/{id}")
    public ResponseEntity<?> getOneCouponById(@PathVariable long id){
        return new ResponseEntity<>(companyService.getCompanyCouponById(id), HttpStatus.ACCEPTED);
    }*/

    @GetMapping("findByCategories/{categories}")
    public ResponseEntity<?> getCompanyCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable("categories") Categories categories) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(companyService.getCompanyCouponsByCategory(categories));
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
    /*
    @GetMapping("findByCategories/{categories}")
    public ResponseEntity<?> getCompanyCouponsByCategory(@PathVariable("categories") Categories categories) {
        return new ResponseEntity<>(companyService.getCompanyCouponsByCategory(categories), HttpStatus.ACCEPTED);
    }*/

    @GetMapping("coupons/{maxPrice}")
    public ResponseEntity<?> getCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(companyService.getCompanyCouponsByMaxPrice(maxPrice));
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
    /*
    @GetMapping("coupons/{maxPrice}")
    public ResponseEntity<?> getCouponsByMaxPrice(@PathVariable double maxPrice){
        return new ResponseEntity<>(companyService.getCompanyCouponsByMaxPrice(maxPrice), HttpStatus.ACCEPTED);
    }*/

    @GetMapping("getDetails")
    public ResponseEntity<?> getDetails(@RequestHeader(name = "Authorization") String token) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(companyService.getCompanyDetails());
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
    /*
    @GetMapping("getDetails")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> getDetails(){
        return new ResponseEntity<>(companyService.getCompanyDetails(), HttpStatus.ACCEPTED);
    }*/


}
