package com.example.springbootshop.user.controller;

import com.example.springbootshop.common.util.MessageUtil;
import com.example.springbootshop.user.exception.UserDataIntegrityViolationException;
import com.example.springbootshop.user.exception.UserExistException;
import com.example.springbootshop.user.model.body.SignupBody;
import com.example.springbootshop.user.model.entity.ShopUser;
import com.example.springbootshop.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private UserService userService;

    @Value("${user.authority.user}")
    private String authorityUser;

    // == [POST] /user/login (로그인) ==
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(ShopUser user) {
        return "";
    }

    // == [POST] /user/signup (회원가입) ==
    @RequestMapping(value = "/signup", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity signUp(HttpServletRequest req, @Valid SignupBody userBody, BindingResult result) {

        // == Validation 형식 ==
        // > 패스워드는 암호화 처리되기 때문에 Entity에 형식을 적용할 수 없음
        if(result.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST) /*400*/
                    .body(messageUtil.getMessage("user.signup.validation"));
        }

        try {
            ShopUser user = new ShopUser();
            user.setUserId(userBody.getUserId());
            user.setPassword(userBody.getPassword());
            user.setAuthority(authorityUser);
            userService.createUser(user);
            return ResponseEntity
                    .status(HttpStatus.OK) /*200*/
                    .body(messageUtil.getMessage("user.signup.complete"));
        } catch (UserExistException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) /*409*/
                    .body(messageUtil.getMessage("user.signup.userId.duplication"));
        } catch (UserDataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST) /*400*/
                    .body(messageUtil.getMessage("user.signup.validation"));
        } catch (Exception e) {
            log.error("[Exception] " + e.getClass() + " : " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) /*500*/
                    .body(messageUtil.getMessage("internal.server.error"));
        }

    }

}
