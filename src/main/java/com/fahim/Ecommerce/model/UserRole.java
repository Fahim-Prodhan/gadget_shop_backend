package com.fahim.Ecommerce.model;


import jakarta.persistence.*;

@Entity

public class UserRole {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long userRoleId;

    //user
    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser user;

    @ManyToOne
    private Role role;

    public UserRole() {
    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
