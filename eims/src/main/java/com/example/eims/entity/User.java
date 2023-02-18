package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
/*
* , uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userName"}),
        @UniqueConstraint(columnNames = {"phone"}),
        @UniqueConstraint(columnNames = {"email"})
}
* */
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId")
    private Long id;
    @Column(name = "roleId")
    private Long roleId;
    @Column(name = "facilityId")
    private Long facilityId;
    @Column(name = "userName")
    private String username;
    @Column(name = "dob")
    private Date dob;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "salary")
    private Float salary;
    @Column(name = "password")
    private String password;
    @Column(name = "address")
    private String address;
    @Column(name = "status")
    private Long status;

/*    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;*/

}
