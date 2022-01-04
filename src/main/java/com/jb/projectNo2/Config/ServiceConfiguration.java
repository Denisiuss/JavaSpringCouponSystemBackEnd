package com.jb.projectNo2.Config;


import com.jb.projectNo2.Services.AdminService;
import com.jb.projectNo2.Services.CompanyService;
import com.jb.projectNo2.Services.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * This class is in charge of initializing  all the service classes.
 * by creating 3 objects of type service which will give us an accesses to all our CRUD methods.
 */

@Configuration
public class ServiceConfiguration {
    @Bean
    public AdminService initialiseAdminService() {
        return new AdminService();
    }
    @Bean
    @Primary
    public CompanyService initialiseCompanyService() {
        return new CompanyService();
    }
    @Bean
    @Primary
    public CustomerService initialiseCustomerService() {
        return new CustomerService();
    }
}

