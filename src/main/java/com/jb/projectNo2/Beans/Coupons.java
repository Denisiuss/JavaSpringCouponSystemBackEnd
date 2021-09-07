package com.jb.projectNo2.Beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Scope("prototype")
@Entity
@Table(name = "coupons")
public class Coupons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "company_id")
    private long companyId;

    @Enumerated(EnumType.STRING)
    private Categories categories;

    private String title;
    private String description;
    private Date start_date;
    private Date end_date;
    private int amount;
    private double price;
    private String image;

}
