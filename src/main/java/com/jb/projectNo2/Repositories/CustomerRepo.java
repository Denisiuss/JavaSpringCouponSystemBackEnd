package com.jb.projectNo2.Repositories;

import com.jb.projectNo2.Beans.Coupons;
import com.jb.projectNo2.Beans.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CustomerRepo extends JpaRepository<Customers, Long> {
    Customers findByEmailAndPassword(String email, String password);
    //ArrayList<Coupons> findById (long customer_id);
    //ArrayList<Coupons> findByPriceLessThanEqualAndId (double maxPrice, long customerId);
    ArrayList<Customers> findAll();
    Customers findById(long id);
    boolean existsByEmailAndPassword (String email, String password);




}
