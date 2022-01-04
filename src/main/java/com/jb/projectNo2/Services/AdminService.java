package com.jb.projectNo2.Services;


import com.jb.projectNo2.Beans.Companies;
import com.jb.projectNo2.Beans.Customers;
import com.jb.projectNo2.Repositories.CompanyRepo;
import com.jb.projectNo2.Repositories.CouponsRepo;
import com.jb.projectNo2.Repositories.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class AdminService extends ClientService {


    @Override
    public boolean login(String email, String password) throws SQLException, InterruptedException {
        if(email.toLowerCase().equals("admin@admin.com") & password.toLowerCase().equals("admin")){
            System.out.println("you have successfully logged in as Admin");
            return true;
        } else{
            System.out.println("wrong email or password");
            return false;
        }
    }


    /**
     * this method add company in MYSQL server
     * @param company
     */
    public void addCompany (Companies company){
        ArrayList<Companies> companies = companyRepo.findAll();
        for (Companies item:companies){
            if (item.getName().equals(company.getName())){
                System.out.println("Company name is already exists");
                return;
            }
            if (item.getEmail().equals(company.getEmail())){
                System.out.println("Email is already taken");
                return;
            }
        }
        companyRepo.save(company);
        System.out.println("company " + company.getName() + " successfully added");
    }

    /**
     * this method will update company in mySQL server
     * @param companies
     */
    public void updateCompany (Companies companies){
        companyRepo.saveAndFlush(companies);
        System.out.println("company " + companies.getName() + " has updated");
    }

    /**
     * this method delete company in MYSQL server
     * @param id
     */
    public void deleteCompany (long id){companyRepo.deleteById(id);
        System.out.println("successfully deleted");
    }

    /**
     * this method get all companies from MYSQL server
     * @return
     */
    public ArrayList<Companies> getAllCompanies (){
        ArrayList<Companies> companies = companyRepo.findAll();
        System.out.println(companies);
        return companies;
    }

    /**
     * this method get company by companyID from MYSQL server
     * @param id
     * @return
     */
    public Companies getOneCompany (long id) {
        Companies companies = companyRepo.findById(id);
        System.out.println(companies);
        return companies;
    }

    /**
     * this method add customer in MYSQL server
     * @param customer
     */
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

    /**
     * this method update customer in MYSQL server
     * @param customers
     */
    public void updateCustomer (Customers customers){
        customerRepo.saveAndFlush(customers);
        System.out.println("customer " + customers.getFirst_name() + " successfully updated");
    }

    /**
     * this method delete customer in MYSQL server
     * @param id
     */
    public void deleteCustomer (long id){
        customerRepo.deleteById(id);
        System.out.println("successfully deleted");
    }

    /**
     * this method gets all customers in MYSQL server
     * @return
     */
    public ArrayList<Customers> getAllCustomers (){
        ArrayList<Customers> customers =customerRepo.findAll();
        System.out.println(customers);
        return customerRepo.findAll();
    }

    /**
     * this method gets customer by its ID in MYSQL server
     * @param id
     * @return
     */
    public Customers getOneCustomer(long id){
        Customers customers = customerRepo.findById(id);
        System.out.println(customers);
        return customers;
        } //check

}
