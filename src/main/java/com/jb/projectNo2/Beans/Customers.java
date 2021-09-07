package com.jb.projectNo2.Beans;

import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Scope("prototype")
@Entity
@Table(name = "customers")
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;

    @Singular
    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "customers_vs_coupons", joinColumns = @JoinColumn(name="customer_id"),
    inverseJoinColumns = @JoinColumn(name="coupon_id"))
    private List<Coupons> coupons;
}
