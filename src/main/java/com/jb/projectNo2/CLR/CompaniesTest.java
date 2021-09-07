package com.jb.projectNo2.CLR;

import com.jb.projectNo2.Beans.Categories;
import com.jb.projectNo2.Beans.Coupons;
import com.jb.projectNo2.Login.ClientType;
import com.jb.projectNo2.Login.LoginManager;
import com.jb.projectNo2.Repositories.CompanyRepo;
import com.jb.projectNo2.Services.CompanyService;
import com.jb.projectNo2.Utils.ArtUtils;
import com.jb.projectNo2.Utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Order(2)
@RequiredArgsConstructor
public class CompaniesTest implements CommandLineRunner {
    private final CompanyService companyService;

    @Override
    public void run(String... args) throws Exception {
        Date now = new Date(new java.util.Date().getTime());
        Date inOneDay = new Date(now.getTime()+24*60*60*1000);

        Coupons coupon = new Coupons(0,2, Categories.Beauty, "10% discount", "get 10% discount in happy hour 10:00-12:00", now, inOneDay, 5, 7, "10%Discount");
        Coupons coupon1 = new Coupons(0,2, Categories.Electronic,"15% discount", "get 15% discount only on wednesday", now, inOneDay, 7, 5, "15%%%%");
        Coupons coupon2 = new Coupons(0,2, Categories.Events,"Get 5% cashback", "5% cashback for our club members", now, inOneDay,10,11,"CASHBACK");
        Coupons coupon3 = new Coupons(0,2, Categories.Fashion,"1+1", "Buy pizza and get one mor fo free", Date.valueOf("2020-06-15"), Date.valueOf("2021-06-28"), 8, 6, "1+1");
        Coupons coupon4 = new Coupons(0,2, Categories.Travel,"free alcohol", "free alcohol from 12pm on fridays", now, inOneDay, 0, 7, "FREE");

        Coupons coupon5 = new Coupons(0,2, Categories.Beauty,"20% discount", "get 20% discount only on wednesday", now, inOneDay, 7, 8, "15%%%%");
        Coupons coupon6 = new Coupons(0,2,Categories.Fashion,"Get 8% cashback", "8% cashback for our club members", now, inOneDay,10,11,"CASHBACK");
        Coupons coupon7 = new Coupons(0,2,Categories.Events,"2+1", "Buy 2 pizza and get one mor fo free", Date.valueOf("2020-06-15"), Date.valueOf("2021-06-28"), 8, 6, "1+1");
        Coupons coupon8 = new Coupons(0,2,Categories.Electronic,"free cocktails", "free alcohol from 12pm on fridays", now, inOneDay, 0, 7, "FREE");
        //login failed
        try {
            CompanyService companyFacade = (CompanyService) LoginManager.getInstance().login("vasss", "1234", ClientType.Company);
        }catch (Exception e){
            e.getMessage();
        }

        //successful login
        try{
        CompanyService companyFacade1 = (CompanyService) LoginManager.getInstance().login("pasta@email.com", "pasta321", ClientType.Company);
        }catch (Exception e){
            e.getMessage();
        }
        companyService.getCompanyId("pasta@email.com");

        System.out.println(ArtUtils.Company_Facade);
        System.out.println(ArtUtils.dottedLine);

        //ADD COUPON
        System.out.println("Adding coupon "+ DateUtils.getLocalDateTime()+"\n");
        companyService.addCoupon(coupon);
        companyService.addCoupon(coupon1);
        companyService.addCoupon(coupon2);
        companyService.addCoupon(coupon3);
        companyService.addCoupon(coupon4);
        companyService.addCoupon(coupon5);
        companyService.addCoupon(coupon6);
        companyService.addCoupon(coupon7);
        companyService.addCoupon(coupon8);
        System.out.println(ArtUtils.dottedLine);

        //ADD COUPON WITH TITLE THAT ALREADY EXISTS
        System.out.println("Getting coupon with title that already exists "+ DateUtils.getLocalDateTime()+"\n");
        companyService.addCoupon(coupon);
        System.out.println(ArtUtils.dottedLine);

        System.out.println("Updating coupon " +DateUtils.getLocalDateTime()+"\n");

        //UPDATE COUPON
        coupon.setAmount(14);
        coupon1.setPrice(8);
        companyService.updateCoupon(coupon);
        companyService.updateCoupon(coupon1);
        System.out.println(ArtUtils.dottedLine);

        //GET COMPANY COUPONS
        System.out.println("Getting company coupons " + DateUtils.getLocalDateTime()+"\n");
        companyService.getCompanyCoupons();
        System.out.println(ArtUtils.dottedLine);

        //GET COMPANY COUPONS BY CATEGORY
        System.out.println("Getting company coupons by category" + DateUtils.getLocalDateTime()+"\n");
        companyService.getCompanyCouponsByCategory(Categories.Travel);
        System.out.println(ArtUtils.dottedLine);

        //GET COUPONS BY MAX PRICE
        System.out.println("Getting company coupons by Max Price (For Example 9)" + DateUtils.getLocalDateTime()+"\n");
        companyService.getCompanyCouponsByMaxPrice(9);
        System.out.println(ArtUtils.dottedLine);

        //GET COMPANY DETAILS
        System.out.println("Getting company details" + DateUtils.getLocalDateTime()+"\n");
        companyService.getCompanyDetails();
        System.out.println(ArtUtils.dottedLine);

        //DELETE COUPON
        System.out.println("Deleting coupon " + DateUtils.getLocalDateTime()+"\n");
        companyService.deleteCoupon(1);

    }
}
