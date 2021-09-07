package com.jb.projectNo2.Repositories;

import com.jb.projectNo2.Beans.Companies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CompanyRepo extends JpaRepository<Companies, Long> {
    boolean findByEmailAndPassword (String email, String password);
    Companies findByEmail (String email);
    Companies findById(long id);
    ArrayList<Companies> findAll();
}
