package com.jb.projectNo2.Controller;

import com.jb.projectNo2.Beans.Categories;
import com.jb.projectNo2.Beans.Companies;
import com.jb.projectNo2.Beans.Coupons;
import com.jb.projectNo2.Beans.UserDetails;
import com.jb.projectNo2.Login.ClientType;
import com.jb.projectNo2.Login.LoginManager;
import com.jb.projectNo2.Services.AdminService;
import com.jb.projectNo2.Services.CompanyService;
import com.jb.projectNo2.Utils.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private JWTutil jwTutil;
    @Autowired
    private LoginManager loginManager;
    private long companyId;

    @PostMapping("Login")
    private ResponseEntity<?> userLogin (@RequestBody UserDetails userDetails) throws SQLException, InterruptedException {
        if (userDetails.getUserType().equals(ClientType.Company.toString())) {
            companyService = (CompanyService) loginManager.login(userDetails.getEmail(), userDetails.getPassword(), ClientType.Company);
            if (companyService != null) {
                userDetails.setId(companyId=companyService.getCompanyId(userDetails.getEmail()));
                String token = jwTutil.generateToken(userDetails);
                return ResponseEntity.accepted().header(jwTutil.extractEmail(token)).body(token);
            }
        }
        return new ResponseEntity<>("Incorrect login", HttpStatus.UNAUTHORIZED);
    }


    @PostMapping("addCoupon")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCoupon(@RequestBody Coupons coupons){companyService.addCoupon(coupons);}

    @PostMapping("updateCoupon")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCoupon(@RequestBody Coupons coupons) {companyService.updateCoupon(coupons);}

    @DeleteMapping("deleteCoupon/{couponId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCoupon(@PathVariable long couponId) {companyService.deleteCoupon(couponId);}

    @GetMapping("getCoupons")
    public ResponseEntity<?> getCompanyCoupons () {
        return new ResponseEntity<>(companyService.getCompanyCoupons(), HttpStatus.ACCEPTED);
    }

    @GetMapping("getOneCouponById/{id}")
    public ResponseEntity<?> getOneCouponById(@PathVariable long id){
        return new ResponseEntity<>(companyService.getCompanyCouponById(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("findByCategories/{categories}")
    public ResponseEntity<?> getCompanyCouponsByCategory(@PathVariable("categories") Categories categories) {
        return new ResponseEntity<>(companyService.getCompanyCouponsByCategory(categories), HttpStatus.ACCEPTED);
    }

    @GetMapping("coupons/{maxPrice}")
    public ResponseEntity<?> getCouponsByMaxPrice(@PathVariable double maxPrice){
        return new ResponseEntity<>(companyService.getCompanyCouponsByMaxPrice(maxPrice), HttpStatus.ACCEPTED);
    }

    @GetMapping("getDetails")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> getDetails(){
        return new ResponseEntity<>(companyService.getCompanyDetails(), HttpStatus.ACCEPTED);
    }


}
