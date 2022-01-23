package com.jb.projectNo2.Repositories;

import com.jb.projectNo2.Beans.Categories;
import com.jb.projectNo2.Beans.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface CouponsRepo extends JpaRepository<Coupons, Long> {


    ArrayList<Coupons> findByCompanyId (long company_id);
    ArrayList<Coupons> findByCompanyIdAndCategories (long company_id, Categories categories);
    ArrayList<Coupons> findByCompanyIdAndPriceLessThanEqual (long id, double maxPrice);
    Coupons findByCompanyIdAndId(long company_id, long id);
    @Transactional
    @Modifying (clearAutomatically = true)
    @Query(value = "INSERT INTO customers_vs_coupons (customer_id, coupon_id) values(:customerId, :couponId)", nativeQuery = true)
    void addCouponPurchase(@Param("customerId") long customerId, @Param("couponId") long couponId);
    @Transactional
    @Modifying (clearAutomatically = true)
    @Query(value = "SELECT * FROM coupons WHERE ID IN (SELECT COUPON_ID FROM customers_vs_coupons WHERE CUSTOMER_ID = :customerId)", nativeQuery = true)
    ArrayList<Coupons> findByCustomerId (@Param("customerId") long customerId);
    ArrayList<Coupons> findByCategories(Categories categories);
    @Transactional
    @Modifying (clearAutomatically = true)
    @Query(value = "SELECT * FROM coupons WHERE ID IN (SELECT COUPON_ID FROM customers_vs_coupons WHERE CUSTOMER_ID = :customerId) AND CATEGORIES = :categories", nativeQuery = true)
    ArrayList<Coupons> findCustomerCouponsByCategory(@Param("customerId") long customerId, @Param("categories") String categories);
    @Transactional
    @Modifying (clearAutomatically = true)
    @Query (value = "SELECT * FROM coupons WHERE ID IN (SELECT COUPON_ID FROM customers_vs_coupons WHERE CUSTOMER_ID = :customerId) AND PRICE<= :maxPrice", nativeQuery = true)
    ArrayList<Coupons> findByCustomerIdAndMaxPrice (@Param("customerId") long customerId,@Param("maxPrice") double maxPrice);
    @Transactional
    @Modifying (clearAutomatically = true)
    @Query (value = "DELETE FROM coupons WHERE END_DATE< NOW()"
            //"Delete from couponsno2.coupons WHERE end_date<=CURRENT_DATE"
            , nativeQuery = true)
    void deleteByEndDate(@Param("now") Date now);

}
