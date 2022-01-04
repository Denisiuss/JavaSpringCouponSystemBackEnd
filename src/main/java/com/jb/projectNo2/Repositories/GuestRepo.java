package com.jb.projectNo2.Repositories;

import com.jb.projectNo2.Beans.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface GuestRepo extends JpaRepository<Coupons, Long> {
    ArrayList<Coupons> findAll();
    Coupons findById(long id);
}
