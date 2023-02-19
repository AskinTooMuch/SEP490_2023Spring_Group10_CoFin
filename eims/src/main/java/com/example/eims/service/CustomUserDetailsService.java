package com.example.eims.service;

import com.example.eims.entity.UserRole;
import com.example.eims.entity.User;
import com.example.eims.repository.UserRoleRepository;
import com.example.eims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with phone [" + phone + "]"));
        System.out.println(user);
        UserRole userRole = new UserRole();
        if (userRoleRepository.findByRoleId(user.getRoleId()).isPresent()){
            userRole = userRoleRepository.findByRoleId(user.getRoleId()).get();
        }
        System.out.println(userRole.getRoleName());
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(userRole.getRoleName()));
//        authorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

}
