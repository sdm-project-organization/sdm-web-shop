package com.example.springbootshop.rest.model.body;

import java.util.List;

public class PurchaseBody {

    private List<BasketBody> baskets;

    private String tel;

    private String address;

    public List<BasketBody> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<BasketBody> baskets) {
        this.baskets = baskets;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static class BasketBody {
        private int basketNo;
        private int goodsNo;
        private int count;

        public int getBasketNo() {
            return basketNo;
        }

        public void setBasketNo(int basketNo) {
            this.basketNo = basketNo;
        }

        public int getGoodsNo() {
            return goodsNo;
        }

        public void setGoodsNo(int goodsNo) {
            this.goodsNo = goodsNo;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

}
