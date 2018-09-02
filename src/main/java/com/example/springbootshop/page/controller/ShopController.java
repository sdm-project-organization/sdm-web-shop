package com.example.springbootshop.page.controller;

import com.example.springbootshop.rest.model.entity.Basket;
import com.example.springbootshop.rest.service.BasketService;
import com.example.springbootshop.rest.service.GoodsService;
import com.example.springbootshop.rest.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    BasketService basketService;

    @Autowired
    PurchaseService purchaseService;

    @Value("${goods.pagenation.item.size}")
    int goodsItemSize;

    @Value("${goods.pagenation.btn.size}")
    int goodsBtnSize;

    @Value("${basket.pagenation.item.size}")
    int basketItemSize;

    @Value("${basket.pagenation.btn.size}")
    int basketBtnSize;

    @Value("${purchase.pagenation.item.size}")
    int purchaseItemSize;

    @Value("${purchase.pagenation.btn.size}")
    int purchaseBtnSize;

    // == 상품화면 ==
    @RequestMapping("/main")
    public String main(Model model) {
        model.addAttribute("maxItem", goodsService.countAll());
        model.addAttribute("sizeItem", goodsItemSize);
        model.addAttribute("sizeBtn", goodsBtnSize);
        return "component/shop/goods";
    }

    // == 장바구니 ==
    @RequestMapping("/basket")
    public String basket(Model model) {
        model.addAttribute("maxItem", basketService.countByUserNo());
        model.addAttribute("sizeItem", basketItemSize);
        model.addAttribute("sizeBtn", basketBtnSize);
        return "component/shop/basket";
    }

    // == 결제화면 ==
    @RequestMapping("/payment")
    public String payment(Model model) {
        List<Basket> baskets = basketService.findAllByUserNo();
        if(baskets.size() != 0) {
            model.addAttribute("listBasket", basketService.findAllByUserNo());
            return "component/shop/payment";
        } else {
            return "redirect:/shop/main";
        }
    }

    // == 구매목록 ==
    @RequestMapping("/purchase")
    public String purchase(Model model) {
        model.addAttribute("maxItem", purchaseService.countByUserNo());
        model.addAttribute("sizeItem", purchaseItemSize);
        model.addAttribute("sizeBtn", purchaseBtnSize);
        return "component/shop/purchase";
    }

    // == 관리모드 ==
    @RequestMapping("/admin")
    public String admin(Model model) {
        return "component/shop/admin";
    }


}
