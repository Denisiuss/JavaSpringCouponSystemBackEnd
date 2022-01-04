package com.jb.projectNo2.Login;

import com.jb.projectNo2.Config.ServiceConfiguration;
import com.jb.projectNo2.Repositories.CompanyRepo;
import com.jb.projectNo2.Repositories.CouponsRepo;
import com.jb.projectNo2.Repositories.CustomerRepo;
import com.jb.projectNo2.Services.AdminService;
import com.jb.projectNo2.Services.ClientService;
import com.jb.projectNo2.Services.CompanyService;
import com.jb.projectNo2.Services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;


@Service
@RequiredArgsConstructor
public class LoginManager {

    private final ServiceConfiguration config;

    /**
     * This method is in charge of the login of the client to the system.
     * @param email is the email of the client. who tries to login to the system.
     * @param password is the password of the client.
     * @param clientType is the type of the client: admin/company/customer.
     * @return access to all the authorized method of the client if exists if not returns null.
     *
     *
     */
    public ClientService login(String email, String password, ClientType clientType) throws SQLException, InterruptedException {
        switch (clientType) {
            case Administrator:
                AdminService adminService = config.initialiseAdminService();
                return adminService.login(email, password) ? adminService : null;
            case Company:
                CompanyService companyService = config.initialiseCompanyService();
                return companyService.login(email, password) ? companyService : null;
            case Customer:
                CustomerService customerService =  config.initialiseCustomerService();
                return customerService.login(email, password) ? customerService : null;
            default:
                return null;
        }
    }
}

