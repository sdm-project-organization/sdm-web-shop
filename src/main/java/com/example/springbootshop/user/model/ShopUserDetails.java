package com.example.springbootshop.user.model;

import com.example.springbootshop.user.model.entity.ShopUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// == 유저정보 ==
public class ShopUserDetails implements UserDetails {

    private final ShopUser user;
    private final Collection<GrantedAuthority> authorities;
    private int userNo;
    private String userId;
    private String password;

    public ShopUserDetails(ShopUser user, Collection<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
        this.userNo = user.getUserNo();
        this.userId = user.getUserId();
        this.password = user.getPassword();
    }

    public ShopUser getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getUserNo() {
        return userNo;
    }

}
