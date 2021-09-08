package com.jb.projectNo2.CLR;

import com.jb.projectNo2.Beans.Companies;
import com.jb.projectNo2.Beans.Customers;
import com.jb.projectNo2.Login.ClientType;
import com.jb.projectNo2.Login.LoginManager;
import com.jb.projectNo2.Services.AdminService;
import com.jb.projectNo2.Utils.ArtUtils;
import com.jb.projectNo2.Utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
@Order(1)
@RequiredArgsConstructor
public class AdminTest implements CommandLineRunner {
    private final AdminService adminService;

    @Override
    public void run(String... args) throws Exception {


        Companies pizzaComp = Companies.builder()
                .name("PizzaCorp")
                .email("pizzaCorp@email.com")
                .password("1234567")
                .build();
        Companies pastaBasta = Companies.builder()
                .name("PastaBasta")
                .email("pasta@email.com")
                .password("pasta321")
                .build();
        Customers customers1 = Customers.builder()
                .first_name("Denis")
                .last_name("Vasly")
                .email("Denis@denis.com")
                .password("789654")
                .build();
        Customers customers2 = Customers.builder()
                .first_name("katy")
                .last_name("Lenov")
                .email("Katy@mail.com")
                .password("147852")
                .build();




        //login
        AdminService adminService1 = (AdminService) LoginManager.getInstance().login("admin@admin.com", "adminnn", ClientType.Administrator);
        System.out.println(ArtUtils.dottedLine);

        AdminService adminService2 = (AdminService) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.Administrator);
        System.out.println(ArtUtils.Admin_FACADE);
        System.out.println(ArtUtils.dottedLine);


        //Add Companies
        adminService.addCompany(pizzaComp);
        adminService.addCompany(pastaBasta);
        System.out.println(ArtUtils.dottedLine);

        //NAME ALREADY EXISTS
        System.out.println("Trying to add companies with name and email that already exist " + DateUtils.getLocalDateTime()+"\n");
        adminService.addCompany(pizzaComp);
        //email already exists
        pizzaComp.setName("pizzzaaaa");
        adminService.addCompany(pizzaComp);
        System.out.println(ArtUtils.dottedLine);

        //update Company
        System.out.println("Updating company " + DateUtils.getLocalDateTime()+"\n");
        pizzaComp.setPassword("852465");
        pizzaComp.setEmail("qwerty@mail.ru");
        adminService.updateCompany(pizzaComp);
        System.out.println(ArtUtils.dottedLine);

        //get all companies
        System.out.println("Getting all Companies " + DateUtils.getLocalDateTime()+"\n");
        adminService.getAllCompanies();
        System.out.println(ArtUtils.dottedLine);

        //get one Company
        System.out.println("Getting one company by ID " + DateUtils.getLocalDateTime());
        adminService.getOneCompany(1);
        System.out.println(ArtUtils.dottedLine);

        //Add Customer
        System.out.println("Adding customers " + DateUtils.getLocalDateTime()+"\n");
        adminService.addCustomer(customers1);
        adminService.addCustomer(customers2);
        System.out.println(ArtUtils.dottedLine);

        //add customer fail - email already exists
        System.out.println("trying to add customers exist" + DateUtils.getLocalDateTime()+"\n");
        adminService.addCustomer(customers1);

        //update customer
        System.out.println("Updating customer " + DateUtils.getLocalDateTime()+"\n");
        customers1.setFirst_name("Dmitry");
        adminService.updateCustomer(customers1);
        System.out.println(ArtUtils.dottedLine);

        //get one customer
        System.out.println("Getting one customer by ID " + DateUtils.getLocalDateTime()+"\n");
        adminService.getOneCustomer(1);
        System.out.println(ArtUtils.dottedLine);

        //delete company
        System.out.println("Deleting company "+ DateUtils.getLocalDateTime()+"\n");
        adminService.deleteCompany(1);
        System.out.println(ArtUtils.dottedLine);

        //delete customer
        System.out.println("Deleting customer "+ DateUtils.getLocalDateTime()+"\n");
        adminService.deleteCustomer(1);
        System.out.println(ArtUtils.dottedLine);


        //get all companies and customers
        System.out.println("GETTING ALL COMPANIES AND CUSTOMERS " + DateUtils.getLocalDateTime()+"\n");
        adminService.getAllCompanies();
        adminService.getAllCustomers();
        System.out.println(ArtUtils.dottedLine);
    }
}
