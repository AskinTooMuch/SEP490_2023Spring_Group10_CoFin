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
                loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new ResponseEntity<>("User signed-in successfully.", HttpStatus.OK);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO){
        //Check if already Existed: username or email
        if(userRepository.existsByUsername(signUpDTO.getUsername())){
            return new ResponseEntity<>("Username already taken.", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUpDTO.getEmail())){
            return new ResponseEntity<>("Email already taken.", HttpStatus.BAD_REQUEST);
        }

        //If not existed: Create new user, add into database and return.
        User user = new User();
        //user.setUsername(signUpDTO.getName());
        user.setUsername(signUpDTO.getUsername());
        user.setEmail(signUpDTO.getEmail());

        // demo infor
        user.setFacilityId(Long.parseLong("1"));
        String sDate1="31-12-1998";
        Date date1 = null;
        try {
            date1 = new Date(
                    ((java.util.Date)new SimpleDateFormat("dd-MM-yyyy").parse(sDate1)).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        user.setDob(date1);
        user.setPhone("0123456789");
        user.setAddress("hai duong");
        user.setSalary(999.F);
        user.setStatus(true);

        //Encode password
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
///----------------------------
        //Role roles = roleRepository.findByName("ROLE_ADMIN").get();

        /*Role role = roleRepository.findByName("Owner").get();
        user.setRoleId(role.getId());*/

        user.setRoleId(Long.parseLong("2"));
        System.out.println(user.toString());
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
