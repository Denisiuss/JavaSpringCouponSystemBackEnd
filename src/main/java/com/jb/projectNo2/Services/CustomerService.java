package com.jb.projectNo2.Services;

import com.jb.projectNo2.Beans.Categories;
import com.jb.projectNo2.Beans.Coupons;
import com.jb.projectNo2.Beans.Customers;
import com.jb.projectNo2.Exceptions.CouponException;
import com.jb.projectNo2.Exceptions.CustomerUserException;
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


    @Override
    public boolean login(String email, String password)  {
        try {
            customer_id = customerRepo.findByEmailAndPassword(email, password).getId();
            System.out.println("You have successfully logged in");
            return true;
        }catch (NullPointerException e){
            System.out.println("wrong email or password");
        }
        return false;
    }

    /**
     * this method add coupon purchased by customer in MYSQL server
     * @param coupons
     */
    public void purchaseCoupon(Coupons coupons) throws CouponException {
        if (coupons.getAmount()<1){
            throw new CouponException("Coupon is out of stock");
        }
        if(coupons.getEnd_date().before(new java.sql.Date(System.currentTimeMillis()))){
            throw new CouponException("Coupon has expired!");
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
    public ArrayList<Coupons> getCustomerCoupons() throws CustomerUserException{
        if(customerRepo.findById(customer_id).getCoupons().isEmpty()){
            throw new CustomerUserException("This customer has no coupons");
        }
        ArrayList<Coupons> coupons = couponsRepo.findByCustomerId(customer_id);
        System.out.println(coupons);
        return coupons;
    }

    /**
     * this method gets customer's coupon by category from MYSQL server
     * @param categories
     * @return
     */
    public ArrayList<Coupons> getCustomerCouponsByCategory(Categories categories) throws CustomerUserException {
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
     * @param date
     */
    public void deleteCouponByDate(Date date) {
        couponsRepo.deleteByEndDate(date);
    }
}
