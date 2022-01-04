package com.jb.projectNo2.Threads;

import com.jb.projectNo2.Repositories.CouponsRepo;
import com.jb.projectNo2.Services.ClientService;
import com.jb.projectNo2.Services.CustomerService;
import com.jb.projectNo2.Utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class Job {

    private final CustomerService customerService;

    @Async
    @Scheduled(fixedRate = 1000*60)
    public void deleteCoupon(){
        try {
            customerService.deleteCouponByDate(new java.sql.Date(System.currentTimeMillis()));
            System.out.println("Expired coupon deleted");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
