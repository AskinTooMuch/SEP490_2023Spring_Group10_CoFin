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
    private Long userId = 100L;
    private Long roleId;
    private Long facilityId;
    private String username;
    private Date dob;
    private String phone;
    private String email;
    private Float salary;
    private String password;
    private String address;
    private boolean status;

/*    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;*/

}
