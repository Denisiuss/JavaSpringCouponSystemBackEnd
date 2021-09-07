package com.jb.projectNo2.Services;

import com.jb.projectNo2.Repositories.CompanyRepo;
import com.jb.projectNo2.Repositories.CouponsRepo;
import com.jb.projectNo2.Repositories.CustomerRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
public abstract class ClientService {
    protected final CompanyRepo companyRepo;
    protected final CouponsRepo couponsRepo;
    protected final CustomerRepo customerRepo;


    public abstract boolean login(String email, String password) throws SQLException, InterruptedException;

}
