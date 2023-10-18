package com.fahim.Ecommerce.controller;

import com.fahim.Ecommerce.config.JwtAuthenticationFilter;
import com.fahim.Ecommerce.config.JwtUtils;
import com.fahim.Ecommerce.model.AppUser;
import com.fahim.Ecommerce.model.JwtRequest;
import com.fahim.Ecommerce.model.JwtResponse;
import com.fahim.Ecommerce.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthenticateController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private JwtUtils jwtUtils;

    //generate token
    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        try {
            authenticate(jwtRequest.getUsername(),jwtRequest.getPassword());
        }catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("User Not found");
        }
        // user is authenticated
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException e) {
            throw new Exception("User Disable"+e.getMessage());
        }catch (BadCredentialsException e) {
            throw new Exception("Invalid Credential"+e.getMessage());
        }

    }

    @GetMapping("/current-user")
    public AppUser getCurrentUser(Principal principal)  {
        return ((AppUser)userDetailsService.loadUserByUsername(principal.getName()));
    }


    @GetMapping("/test")
    public String testing(){
        return "test success";
    }
}
