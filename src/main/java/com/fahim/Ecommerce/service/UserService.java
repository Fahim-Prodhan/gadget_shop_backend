package com.fahim.Ecommerce.service;


import com.fahim.Ecommerce.model.AppUser;
import com.fahim.Ecommerce.model.UserRole;

import java.util.List;
import java.util.Set;

public interface UserService {
    AppUser createUser(AppUser user, Set<UserRole> userRoles) throws Exception;

    AppUser getSingleUser(String username) ;

    void deleteUser(Long id);

    AppUser updateUser(AppUser user);

    List<AppUser> getAllUsers();

    long getTotalUser();

}
