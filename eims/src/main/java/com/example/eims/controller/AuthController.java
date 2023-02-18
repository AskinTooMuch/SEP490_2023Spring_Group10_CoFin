package com.example.eims.controller;

import com.example.eims.dto.LoginDTO;
import com.example.eims.dto.SignUpDTO;
import com.example.eims.entity.Role;
import com.example.eims.entity.User;
import com.example.eims.repository.RoleRepository;
import com.example.eims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getPhone(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new ResponseEntity<>("User signed-in successfully.", HttpStatus.OK);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO){
        //Check if already Existed: username or email
        if(userRepository.existsByPhone(signUpDTO.getPhone())){
            return new ResponseEntity<>("Phone already registered.", HttpStatus.BAD_REQUEST);
        }

        //If not existed: Create new user, add into database and return.
        User user = new User();
        user.setUsername(signUpDTO.getUsername());
        user.setEmail(signUpDTO.getEmail());

        //String sDate="31-12-1998";
        String sDate=signUpDTO.getDob();
        Date date;
        try {
            date = new Date(
                    (new SimpleDateFormat("dd-MM-yyyy").parse(sDate)).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        user.setDob(date);
//
//        user.setPhone("0123456789");
//        user.setAddress("hai duong");
        user.setPhone(signUpDTO.getPhone());
        user.setAddress(signUpDTO.getAddress());
        user.setStatus(false);
        //Encode password
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
///----------------------------
        //Role roles = roleRepository.findByName("ROLE_ADMIN").get();

        /*Role role = roleRepository.findByName("Owner").get();
        user.setRoleId(role.getId());*/

        user.setRoleId(signUpDTO.getRoleId());
        System.out.println(user.toString());
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
