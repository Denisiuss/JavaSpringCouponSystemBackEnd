package com.jb.projectNo2.Services;

import com.jb.projectNo2.Repositories.CompanyRepo;
import com.jb.projectNo2.Repositories.CouponsRepo;
import com.jb.projectNo2.Repositories.CustomerRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;


public abstract class ClientService {

    @Autowired
    protected CompanyRepo companyRepo;
    @Autowired
    protected CouponsRepo couponsRepo;
    @Autowired
    protected CustomerRepo customerRepo;


    public abstract boolean login(String email, String password) throws SQLException, InterruptedException;

}
