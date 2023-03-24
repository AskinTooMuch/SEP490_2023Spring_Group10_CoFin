/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE          Version    Author           DESCRIPTION<br>
 * 22/03/2023    1.0        ChucNV           First Deploy<br>
 */
package com.example.eims.service.impl;

import com.example.eims.entity.User;
import com.example.eims.entity.UserDetailsImpl;
import com.example.eims.repository.UserRepository;
import com.example.eims.service.AuthRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetailsImpl loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = null;
        if (userRepository.existsByPhone(phone)) {
            user = userRepository.findByPhone(phone).get();
        } else {
            throw new AuthRequestException("User with phone number: " + phone + " not found.");
        }
        UserDetailsImpl customUserDetail = new UserDetailsImpl();
        customUserDetail.setUser(user);
        return customUserDetail;
    }

}
