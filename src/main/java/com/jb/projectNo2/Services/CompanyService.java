package com.jb.projectNo2.Services;

import com.jb.projectNo2.Beans.Categories;
import com.jb.projectNo2.Beans.Companies;
import com.jb.projectNo2.Beans.Coupons;
import com.jb.projectNo2.Repositories.CompanyRepo;
import com.jb.projectNo2.Repositories.CouponsRepo;
import com.jb.projectNo2.Repositories.CustomerRepo;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
//@RequiredArgsConstructor
public class CompanyService extends ClientService {
    private long company_id;

    public CompanyService(CompanyRepo companyRepo, CouponsRepo couponsRepo, CustomerRepo customerRepo) {
        super(companyRepo, couponsRepo, customerRepo);
    }

    @Override
    public boolean login(String email, String password) {
        return companyRepo.findByEmailAndPassword(email, password);
    }

    /**
     * this method add coupon in MYSQL server
     * @param coupon
     */
    public void addCoupon(Coupons coupon){
        ArrayList<Coupons> coupons = couponsRepo.findByCompanyId(company_id);
        for (Coupons item: coupons){
            if (item.getTitle().equals(coupon.getTitle())){
                System.out.println("you already have coupon with the same title");
                return;
            }
        }
        couponsRepo.save(coupon);
        System.out.println("successfully added");
    }

    /**
     * this method gets company ID in MYSQL server
     * @param email
     * @return companyId
     */
    public long getCompanyId(String email){
        Companies companies = companyRepo.findByEmail(email);
        company_id = companies.getId();
        return company_id;
        }

    /**
     * this method update coupon in MYSQL server
     * @param coupons
     * @return coupon
     */
    public Coupons updateCoupon  (Coupons coupons) {
        System.out.println("successfully updated");
        return couponsRepo.saveAndFlush(coupons);
    }

    /**
     * this method delete coupon in MYSQL server
     * @param id
     */
    public void deleteCoupon (long id) {couponsRepo.deleteById(id);
        System.out.println("successfully deleted");}

    /**
     * this method gets company's coupon in MYSQL server
     * @return list od coupons
     */
    public List<Coupons> getCompanyCoupons(){
        ArrayList<Coupons> coupons = couponsRepo.findByCompanyId(company_id);
        System.out.println(coupons);
        return coupons;
    }

    /**
     * this method gets company's coupon by category in MYSQL server
     * @param categories
     * @return list of coupons
     */
    public List<Coupons> getCompanyCouponsByCategory(Categories categories){
        ArrayList<Coupons> coupons = couponsRepo.findByCompanyIdAndCategories(company_id, categories);
        System.out.println(coupons);
        return coupons;
    }

    /**
     * this method gets company's coupon by Max Price in MYSQL server
     * @param maxPrice
     * @return list of coupons
     */
    public List <Coupons> getCompanyCouponsByMaxPrice(double maxPrice){
        ArrayList<Coupons> coupons = couponsRepo.findByCompanyIdAndPriceLessThanEqual(company_id, maxPrice);
        System.out.println(coupons);
        return coupons;
    }

    /**
     * this method gets company's details in MYSQL server
     * @return company details
     */
    public Companies getCompanyDetails(){
        Companies companies = companyRepo.findById(company_id);
        return companies;
    }

}
