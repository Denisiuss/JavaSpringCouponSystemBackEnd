package com.jb.projectNo2.Controller;

import com.jb.projectNo2.Beans.Customers;
import com.jb.projectNo2.Services.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("guest")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GuestController {
    private final GuestService guestService;

    @GetMapping("allCoupons")
    public ResponseEntity<?> getAllCoupons(){return new ResponseEntity<>(guestService.getAllCoupons(), HttpStatus.ACCEPTED);}

    @GetMapping("oneCouponById/{id}")
    public ResponseEntity<?> getOneFullCoupon(@PathVariable long id){
        return new ResponseEntity<>(guestService.getOneFullCoupon(id), HttpStatus.ACCEPTED);
    }

    @PostMapping("registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomer (@RequestBody Customers customers){
        guestService.addCustomer(customers);
    }
}
