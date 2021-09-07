package com.jb.projectNo2.Services;

import com.jb.projectNo2.Beans.Categories;
import com.jb.projectNo2.Beans.Coupons;
import com.jb.projectNo2.Beans.Customers;
import com.jb.projectNo2.Repositories.CompanyRepo;
import com.jb.projectNo2.Repositories.CouponsRepo;
import com.jb.projectNo2.Repositories.CustomerRepo;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

@Service
public class CustomerService extends ClientService {
    private long customer_id;

    public CustomerService(CompanyRepo companyRepo, CouponsRepo couponsRepo, CustomerRepo customerRepo) {
        super(companyRepo, couponsRepo, customerRepo);
    }

    @Override
    public boolean login(String email, String password) {
        return customerRepo.existsByEmailAndPassword(email, password);
    }

    /**
     * this method add coupon purchased by customer in MYSQL server
     * @param coupons
     */
    public void purchaseCoupon(Coupons coupons){
        if (coupons.getAmount()<1){
            System.out.println("Coupon is out of stock");
            return;
        }
        if (coupons.getEnd_date().before(Calendar.getInstance().getTime())){
            System.out.println("Coupon is not available");
            return;
        }
        couponsRepo.addCouponPurchase(customer_id, coupons.getId());
        coupons.setAmount(coupons.getAmount()-1);
        couponsRepo.saveAndFlush(coupons);
        System.out.println("successfully purchased");
    }

    /**
     *
     * @param email
     * @param password
     * @return
     */
    public long getCustomerId (String email, String password){
        Customers customer = customerRepo.findByEmailAndPassword(email, password);
        if (customer.getEmail().equals(email)&&customer.getPassword().equals(password)){
            customer_id = customer.getId();
            return customer_id;
        }
        return customer_id=0;
    }

    /**
     * this method gets customer ID from MYSQL server
     * @return
     */
    public ArrayList<Coupons> getCustomerCoupons(){
        ArrayList<Coupons> coupons = couponsRepo.findByCustomerId(customer_id);
        System.out.println(coupons);
        return coupons;
    }

    /**
     * this method gets customer's coupon by category from MYSQL server
     * @param categories
     * @return
     */
    public ArrayList<Coupons> getCustomerCouponsByCategory(Categories categories){
        ArrayList<Coupons> coupons = couponsRepo.findCustomerCouponsByCategory(customer_id, categories.toString());
        System.out.println(coupons);
        return coupons;
    }

    /**
     * this method gets customer's coupon by Max Price from MYSQL server
     * @param maxPrice
     * @return
     */
    public ArrayList<Coupons> getCustomerCouponsByMaxPrice (double maxPrice) {
        ArrayList<Coupons> coupons = couponsRepo.findByCustomerIdAndMaxPrice(customer_id, maxPrice);
        System.out.println(coupons);
        return coupons;
    }

    /**
     * this method gets customer's details from MYSQL server
     * @return
     */
    public Customers getCustomerDetails(){
        Customers customer = customerRepo.findById(customer_id);
        System.out.println(customer);
        return customer;
    }

    /**
     * this method deletes expired coupons from MYSQL server
     * @param now
     */
    public void deleteCouponByDate(Date now) {
        couponsRepo.deleteByEndDate();
        System.out.println("Expired coupon deleted");
    }
}
