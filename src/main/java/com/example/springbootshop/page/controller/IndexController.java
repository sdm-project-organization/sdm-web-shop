package com.example.springbootshop.page.controller;

import com.example.springbootshop.user.exception.UserNotLoginException;
import com.example.springbootshop.user.model.ShopUserDetails;
import com.example.springbootshop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    // == index ==
    @RequestMapping(value = "")
    public String index() {
        try {
            ShopUserDetails user = userService.getShopUserDetails();
            return "redirect:/shop/main";
        } catch (UserNotLoginException e) {
            return "layout/layout_user";
        }
    }
}
