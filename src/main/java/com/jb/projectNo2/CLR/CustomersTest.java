package com.jb.projectNo2.CLR;

import com.jb.projectNo2.Beans.Categories;
import com.jb.projectNo2.Beans.Coupons;
import com.jb.projectNo2.Beans.Customers;
import com.jb.projectNo2.Exceptions.CouponException;
import com.jb.projectNo2.Exceptions.CustomerUserException;
import com.jb.projectNo2.Login.ClientType;
import com.jb.projectNo2.Login.LoginManager;
import com.jb.projectNo2.Services.CustomerService;
import com.jb.projectNo2.Utils.ArtUtils;
import com.jb.projectNo2.Utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.sql.Date;

//@Component
@Order(3)
@RequiredArgsConstructor
public class CustomersTest implements CommandLineRunner {
    private final LoginManager loginManager;
    private CustomerService customerService;
    @Override
    public void run(String... args) throws Exception {

        Date now = new Date(new java.util.Date().getTime());
        Date inOneDay = new Date(now.getTime()+24*60*60*1000);

        Coupons coupon1 = new Coupons(6,2, Categories.Beauty,"15% discount", "get 15% discount only on wednesday", now, inOneDay, 7, 8, "15%%%%");
        Coupons coupon2 = new Coupons(7,2,Categories.Fashion,"Get 5% cashback", "5% cashback for our club members", now, inOneDay,10,11,"CASHBACK");
        Coupons coupon3 = new Coupons(8,2,Categories.Events,"1+1", "Buy pizza and get one mor fo free", Date.valueOf("2020-06-15"), Date.valueOf("2021-06-28"), 8, 6, "1+1");
        Coupons coupon4 = new Coupons(9,2,Categories.Electronic,"free alcohol", "free alcohol from 12pm on fridays", now, inOneDay, 0, 7, "FREE");

        try {
            customerService = (CustomerService) loginManager.login("qwe@qwe", "1234", ClientType.Customer);
        } catch (Exception e){
            e.getMessage();
        }


        //SUCCESSFUL login
        try {
            customerService = (CustomerService) loginManager.login("Katy@mail.com", "147852", ClientType.Customer);
        } catch (Exception e) {
            e.getMessage();
        }

        System.out.println(ArtUtils.Customer_Facade);
        System.out.println(ArtUtils.dottedLine);

        //GET CUSTOMER ID
        //customerService.getCustomerId("Katy@mail.com","147852");

        //PURCHASE COUPON
        System.out.println("Purchasing coupon "+ DateUtils.getLocalDateTime()+"\n");
        customerService.purchaseCoupon(coupon1);
        customerService.purchaseCoupon(coupon2);
        System.out.println(ArtUtils.dottedLine);

        //purchase coupon if amount <1 and if coupon expired
        System.out.println("Trying to purchase coupons with amount<1 and expired coupon "+ DateUtils.getLocalDateTime()+"\n");
        try {
            customerService.purchaseCoupon(coupon3);
        }catch (CouponException e){
            System.out.println(e.getMessage());
        }

        try {
            customerService.purchaseCoupon(coupon4);
        }catch (CouponException e){
            System.out.println(e.getMessage());
        }
        //customerService.purchaseCoupon(coupon3);
        //customerService.purchaseCoupon(coupon4);
        System.out.println(ArtUtils.dottedLine);

        //GET CUSTOMER's COUPONS
        System.out.println("Getting customer's coupons "+ DateUtils.getLocalDateTime()+"\n");
        customerService.getCustomerCoupons();
        System.out.println(ArtUtils.dottedLine);

        //GET CUSTOMER'S COUPONS BY CATEGORY
        System.out.println("Getting customer's coupons by category "+ DateUtils.getLocalDateTime()+"\n");
        try {
            customerService.getCustomerCouponsByCategory(Categories.Beauty);
        }catch (CustomerUserException e){
            System.out.println(e.getMessage());
        }
        //customerService.getCustomerCouponsByCategory(Categories.Beauty);
        System.out.println(ArtUtils.dottedLine);

        //GET CUSTOMER COUPONS BY MAX PRICE
        System.out.println("Getting customer's coupons by Max Price "+ DateUtils.getLocalDateTime()+"\n");
        customerService.getCustomerCouponsByMaxPrice(10);
        System.out.println(ArtUtils.dottedLine);

        //GET CUSTOMER DETAILS
        System.out.println("Getting customer details "+ DateUtils.getLocalDateTime()+"\n");
        customerService.getCustomerDetails();


    }
}
