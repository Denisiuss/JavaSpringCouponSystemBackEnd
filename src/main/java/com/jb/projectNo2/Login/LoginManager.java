package com.jb.projectNo2.Login;

import com.jb.projectNo2.Repositories.CompanyRepo;
import com.jb.projectNo2.Repositories.CouponsRepo;
import com.jb.projectNo2.Repositories.CustomerRepo;
import com.jb.projectNo2.Services.AdminService;
import com.jb.projectNo2.Services.ClientService;
import com.jb.projectNo2.Services.CompanyService;
import com.jb.projectNo2.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

public class LoginManager {
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private CouponsRepo couponsRepo;
    @Autowired
    private CustomerRepo customerRepo;
    private static LoginManager instance = null;


    private LoginManager(){

    }

    public static LoginManager getInstance(){
        if (instance == null){
            synchronized (LoginManager.class){
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public ClientService login (String email, String password, ClientType clientType) throws SQLException, InterruptedException {
        switch (clientType){
            case Administrator:
                ClientService adminFacade = new AdminService(companyRepo, couponsRepo, customerRepo);
                if(!adminFacade.login(email, password)) {
                    System.out.println("wrong email or password");
                } else
                System.out.println("You have successfully logged in as: " + email);
                return adminFacade;

            case Company:
                ClientService companyFacade = new CompanyService(companyRepo, couponsRepo, customerRepo);
                if(!companyFacade.login(email, password)) {
                    System.out.println("wrong email or password");
                } else
                System.out.println("You have successfully logged in as: " + email);
                return companyFacade;

            case Customer:
                ClientService customerFacade = new CustomerService(companyRepo, couponsRepo, customerRepo);
                if(!customerFacade.login(email, password)) {
                    System.out.println("wrong email or password");
                    return null;
                } else
                System.out.println("You have successfully logged in as: " + email);
                return customerFacade;

            default:
                return null;
        }
    }
}

