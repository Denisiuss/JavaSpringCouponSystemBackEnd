package com.jb.projectNo2.Services;

import com.jb.projectNo2.Beans.Coupons;
import com.jb.projectNo2.Beans.Customers;
import com.jb.projectNo2.Repositories.CustomerRepo;
import com.jb.projectNo2.Repositories.GuestRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@RequiredArgsConstructor
@Service
public class GuestService {
    private final GuestRepo guestRepo;
    private final CustomerRepo customerRepo;

    public ArrayList<Coupons> getAllCoupons (){
        ArrayList<Coupons> coupons = guestRepo.findAll();
        System.out.println(coupons);
        return coupons;
    }

    public Coupons getOneFullCoupon(long id){
        Coupons coupons = guestRepo.findById(id);
        System.out.println(coupons);
        return coupons;
    }

    public void addCustomer (Customers customer){
        ArrayList<Customers> customers = customerRepo.findAll();
        for (Customers item: customers){
            if (item.getEmail().equals(customer.getEmail())){
                System.out.println("Customer with this email is already exists");
                return;
            }
        }
        customerRepo.save(customer);
        System.out.println("customer " + customer.getFirst_name() + " successfully added");
    }
}
