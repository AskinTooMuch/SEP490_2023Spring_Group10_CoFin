/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 16/02/2023    1.0        DuongVV          First Deploy<br>
 * 19/02/2023    2.0        DuongVV          Fix notation, id filed<br>
 * 22/03/2023    3.0        ChucNV           Add accounts arraylist<br>
 */

package com.example.eims.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId = 100L;
    private String roleName;
    private boolean status;
    @ManyToMany(mappedBy="roles")
    private List<User> accounts = new ArrayList<>();

    public Role(long roleId, String roleName, boolean status) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.status = status;
    }

    public Role() {

    }
}
