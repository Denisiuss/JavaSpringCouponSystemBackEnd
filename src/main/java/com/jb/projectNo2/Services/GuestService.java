package com.jb.projectNo2.Services;

import com.jb.projectNo2.Beans.Coupons;
import com.jb.projectNo2.Repositories.GuestRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@RequiredArgsConstructor
@Service
public class GuestService {
    private final GuestRepo guestRepo;

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
}
