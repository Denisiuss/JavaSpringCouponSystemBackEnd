package com.jb.projectNo2.Controller;

import com.jb.projectNo2.Beans.Categories;
import com.jb.projectNo2.Beans.Coupons;
import com.jb.projectNo2.Beans.UserDetails;
import com.jb.projectNo2.Exceptions.CouponException;
import com.jb.projectNo2.Exceptions.CustomerUserException;
import com.jb.projectNo2.Login.ClientType;
import com.jb.projectNo2.Login.LoginManager;
import com.jb.projectNo2.Services.CustomerService;
import com.jb.projectNo2.Utils.JWTutil;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController {
    CustomerService customerService;
    @Autowired
    private JWTutil jwtUtil;
    @Autowired
    private LoginManager loginManager;
    private long customerId;

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
        if (userDetails.getUserType().equals(ClientType.Customer.toString())) {
            customerService = (CustomerService) loginManager.login(userDetails.getEmail(), userDetails.getPassword(), ClientType.Customer);
            if (customerService != null) {
                userDetails.setId(customerId=customerService.getCustomerId(userDetails.getEmail(), userDetails.getPassword()));
                String token = jwtUtil.generateToken(userDetails);
                return ResponseEntity.accepted().header(jwtUtil.extractEmail(token)).body(token);
            }
        }
        return new ResponseEntity<>("Incorrect login", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("purchaseCoupon")
    private ResponseEntity<?> purchaseCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupons coupons) throws MalformedJwtException, CouponException {
        if (jwtUtil.validateToken(token)) {
            customerService.purchaseCoupon(coupons);
        }
        return ResponseEntity.ok().headers(getHeaders(token)).body("Coupon "+coupons.getTitle()+" purchased");
    }
    /*
    @PostMapping("purchaseCoupon")
    private ResponseEntity<?>purchaseCoupon(@RequestBody Coupons coupons) throws CouponException {
        try {
            customerService.purchaseCoupon(coupons);
            return new ResponseEntity<>("Coupon "+coupons.getTitle()+" purchased", HttpStatus.OK);
        }catch (CouponException e){
            throw new CouponException(e.getMessage());
        }
    }*/

    @GetMapping("myCoupons/{maxPrice}")
    private ResponseEntity<?> getCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(customerService.getCustomerCouponsByMaxPrice(maxPrice));
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
    /*
    /**
     * This is a getter method by customer id and coupons max price.
     * @param maxPrice of the coupons
     * @return ArrayList of all the coupons that 1 customer bought by customer id number and max price of the coupon.
     * @throws CustomerUserException
     *
    @GetMapping("myCoupons/{maxPrice}")
    public ResponseEntity<?> getCouponsByMaxPrice(@PathVariable double maxPrice){
        return new ResponseEntity<>(customerService.getCustomerCouponsByMaxPrice(maxPrice), HttpStatus.ACCEPTED);
    }*/

    @GetMapping("myCouponsByCategory/{categories}")
    private ResponseEntity<?> getCustomerCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable("categories") Categories categories) throws MalformedJwtException, CustomerUserException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(customerService.getCustomerCouponsByCategory(categories));
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
    /*
    /**
     * This verb enable the customer to get all coupons by category from the system from external server.
     * @param categories of the coupon
     * @return ResponseEntity<> with list of all coupons and HTTP status.
     * @throws CustomerUserException
     *
    @GetMapping("myCouponsByCategory/{categories}")
    private ResponseEntity<?>getCustomerCouponsByCategory(@PathVariable("categories") Categories categories) throws CustomerUserException {
        try {
            return new ResponseEntity<>(customerService.getCustomerCouponsByCategory(categories),HttpStatus.OK);
        } catch (CustomerUserException e) {
            throw new CustomerUserException(e.getMessage());
        }
    }*/

    @GetMapping("/getMyCoupons")
    private ResponseEntity<?>getMyCoupons(@RequestHeader(name = "Authorization") String token) throws MalformedJwtException, CustomerUserException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(customerService.getCustomerCoupons());
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
    /*
    /**
     * This verb enable the customer to get all coupons that were purchased from external server.
     *
     * @return ResponseEntity<> with list of all purchased coupons and HTTP status.
     * @throws CustomerUserException
     *
    @GetMapping("/getMyCoupons")
    private ResponseEntity<?>getMyCoupons() throws CustomerUserException {
        try {
            return new ResponseEntity<>(customerService.getCustomerCoupons(),HttpStatus.OK);
        } catch (CustomerUserException e) {
            throw new CustomerUserException(e.getMessage());
        }
    }*/

    @GetMapping("/CustomerDetails")
    private ResponseEntity<?> getCustomerDetails(@RequestHeader(name = "Authorization") String token) throws MalformedJwtException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().headers(getHeaders(token)).body(customerService.getCustomerDetails());
        }
        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
    /*
    /**
     * This verb enable the customer to get all customer details from external server.
     *
     * @return ResponseEntity<> with list of all customer details and HTTP status.
     *
     *
    @GetMapping("/CustomerDetails")
    private ResponseEntity<?>getCustomerDetails(){
        return new ResponseEntity<>(customerService.getCustomerDetails(),HttpStatus.OK);
    }*/

}
