package com.jb.projectNo2.Beans;

import lombok.*;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Scope("prototype")
@Entity
@Table(name = "companies")
public class Companies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    private String email;
    private String password;

    @Singular
    @OneToMany (cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "company_id")
    private List <Coupons> coupons;


}
