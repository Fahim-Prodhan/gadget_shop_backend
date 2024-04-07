package com.fahim.Ecommerce.controller;


import com.fahim.Ecommerce.model.AppUser;
import com.fahim.Ecommerce.model.Role;
import com.fahim.Ecommerce.model.UserRole;
import com.fahim.Ecommerce.repo.UserRepository;
import com.fahim.Ecommerce.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;



    @PostMapping("/register")
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser user) throws Exception {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<UserRole> userRoleSet = new HashSet<>();

        Role role = new Role();
        role.setRoleId(2L);
        role.setRoleName("NORMAL");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        userRoleSet.add(userRole);

        return ResponseEntity.ok(userService.createUser(user,userRoleSet));
    }

    @PostMapping("/admin-register")
    public ResponseEntity<AppUser> createAdmin(@RequestBody AppUser user) throws Exception {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<UserRole> userRoleSet = new HashSet<>();

        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleName("ADMIN");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        userRoleSet.add(userRole);

        return ResponseEntity.ok(userService.createUser(user,userRoleSet));
    }


    @GetMapping("/{username}")
    public ResponseEntity<AppUser> getSingleUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getSingleUser(username));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping
    public ResponseEntity<AppUser> updateUser (@RequestBody AppUser user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String > changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Principal principal) {
        String username = principal.getName();
        AppUser currentUser = userService.getSingleUser(username);

        if (passwordEncoder.matches(oldPassword,currentUser.getPassword()) && newPassword.equals(confirmPassword)) {
            currentUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(currentUser);
           return ResponseEntity.ok("Success");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password change failed");
    }
    @GetMapping("/count")
    public long getContOfUsers() {
        return this.userService.getTotalUser();
    }



}
