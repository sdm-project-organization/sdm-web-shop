package com.example.springbootshop.user.service;

import com.example.springbootshop.common.util.MessageUtil;
import com.example.springbootshop.rest.model.entity.Goods;
import com.example.springbootshop.rest.service.GoodsService;
import com.example.springbootshop.user.exception.UserDataIntegrityViolationException;
import com.example.springbootshop.user.exception.UserExistException;
import com.example.springbootshop.user.exception.UserNotLoginException;
import com.example.springbootshop.user.model.ShopUserDetails;

import com.example.springbootshop.user.model.entity.ShopUser;
import com.example.springbootshop.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(GoodsService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageUtil messageUtil;

    @Value("${user.authority.user}")
    private String authorityUser;

    @Value("${dummy.user.file.name}")
    private String dummyUserFileName;

    // == User 초기화 ==
    @PostConstruct
    public void init() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<ShopUser> initUserList = Arrays.asList(objectMapper.readValue(
                    getClass().getClassLoader().getResourceAsStream(dummyUserFileName),
                    ShopUser[].class));
            for(ShopUser user : initUserList) {
                createUser(user);
            }
        } catch (Exception e) {
            log.error("[Exception] " + e.getClass() + " : " + e.getMessage());
        }
    }

    // == User 조회 ==
    public ShopUser findByUserId(String userId) {
        ShopUser user = userRepository.findByUserId(userId);

        if(user == null)
            throw new UsernameNotFoundException(userId + " is not found.");

        return user;
    }

    // == 로그인 ==
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        ShopUser user = this.findByUserId(userId);

        return new ShopUserDetails(user, getAuthorities(user));
    }

    // == 회원가입 ==
    @Transactional
    public void createUser(ShopUser user) {

        // == 중복확인 ==
        ShopUser existUser = userRepository.findByUserId(user.getUserId());
        if(existUser != null)
            throw new UserExistException(); /*409*/

        // == BCrypt ==
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserDataIntegrityViolationException();
        }
    }

    private Collection<GrantedAuthority> getAuthorities(ShopUser user)  {
        switch(user.getAuthority()) {
            case "admin": /*messageUtil.getMessage("user.authority.admin")*/
                return AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_EMPLOYEE", "ROLE_ADMIN");
            case "employee": /*messageUtil.getMessage("user.authority.employee")*/
                return AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_EMPLOYEE");
            default: /*messageUtil.getMessage("user.authority.user")*/
                return AuthorityUtils.createAuthorityList("ROLE_USER");
        }
    }

    // == 로그인유저확인 ==
    public ShopUserDetails getShopUserDetails() throws UserNotLoginException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof ShopUserDetails) {
            ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
            return userDetails;
        }
        throw new UserNotLoginException();
    }


}
