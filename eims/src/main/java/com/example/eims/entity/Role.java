package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "userrole")
public class Role {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "roleId")
    private long id;
    @Column(name = "roleName")
    private String name;
    @Column(name = "status")
    private boolean status;
}
