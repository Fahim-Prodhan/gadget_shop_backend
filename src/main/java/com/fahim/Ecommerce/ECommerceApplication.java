package com.fahim.Ecommerce;

import com.fahim.Ecommerce.model.AppUser;
import com.fahim.Ecommerce.model.Role;
import com.fahim.Ecommerce.model.UserRole;
import com.fahim.Ecommerce.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ECommerceApplication implements CommandLineRunner {
	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting Code");

		try {
			AppUser user = new AppUser();

			user.setFirstName("Fahim");
			user.setLastName("Prodhan");
			user.setUsername("admin");
			user.setPassword(this.bCryptPasswordEncoder.encode("111"));
			user.setEmail("abc@gmail.com");
			user.setProfile("default.png");

			Role role1 = new Role();
			role1.setRoleId(1L);
			role1.setRoleName("ADMIN");

			Set<UserRole> userRoleSet = new HashSet<>();
			UserRole userRole = new UserRole();
			userRole.setRole(role1);
			userRole.setUser(user);

			userRoleSet.add(userRole);
			AppUser user1 = this.userService.createUser(user, userRoleSet);

			System.out.println(user1.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
