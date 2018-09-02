(function () {

    new Vue({
        el: '#shop_basket_list',
        data: {
            listBasket: [],
            originListBasket :[] // == 캐싱배열 ==
        },
        methods: {
            // == Basket 리스트 요청 ==
            reqGetBasketList(page) {
                var that = this;
                axios.get("/api/basket?page=" + page).then(function (res) {
                    that.listBasket = res.data;
                }).catch(function (err) {
                    if(err.response) {
                        switch(err.response.status) {
                            case 404:
                                break;
                            default:
                                that.popupRefresh("관리자에게 문의해 주세요.");
                        }
                    } else console.log(err);
                });
            },
            // == Basket 삭제 ==
            reqDeleteBasket(id) {
                var that = this;
                axios.delete("/api/basket/" + id).then(function (res) {
                    that.listBasket.forEach(function(basket, i) {
                       if(basket.basketNo === id)
                           that.listBasket.splice(i, 1);
                    });
                    that.popupRefresh("제거완료");
                }).catch(function (err) {
                    if(err.response) {
                        switch(err.response.status) {
                            case 404:
                                that.popupRefresh("제거할 대상이 없습니다.");
                                break;
                            default:
                                that.popupRefresh("관리자에게 문의해 주세요.");
                        }
                    } else console.log(err);
                });
            },
            // == Basket 수량변경 ==
            reqUpdateBasketCount(id, count) {
                var that = this;
                axios.put("/api/basket/" + id, {
                    count : count
                }).then(function (res) {
                    that.popup("수량변경 완료");
                }).catch(function (err) {
                    if(err.response) {
                        switch(err.response.status) {
                            case 404:
                                that.popupRefresh("없는 데이터입니다.");
                                break;
                            case 410:
                                that.popupRefresh("재고가 부족합니다.");
                                break;
                            case 416:
                                that.popupRefresh("올바르지 않은 수량입니다.");
                                break;
                            default:
                                that.popupRefresh("관리자에게 문의해 주세요.");
                        }
                    } else console.log(err);
                });
            },
            // == Popup ==
            popup(message) {
                swal({ title : message, confirmButtonText: "확인" });
            },
            // == Popup Refresh ==
            popupRefresh(message) {
                swal({ title : message, confirmButtonText: "확인", allowOutsideClick: false }).then(function (result) {
                    location.href = "/shop/basket";
                });
            }
        },
        // == 감시자 ==
        watch : {
            // == 깊은감시 ==
            listBasket: {
                handler: function(newValue) {
                    var that = this;
                    if(newValue.length != that.originListBasket.length) {
                        that.originListBasket = JSON.parse(JSON.stringify(newValue));
                        return;
                    }
                    newValue.forEach(function(v, i) {
                        console.log(v.count, that.originListBasket[i].count);
                        if(v.count != that.originListBasket[i].count) {
                            that.reqUpdateBasketCount(v.basketNo, v.count);
                        }
                    });
                    that.originListBasket = JSON.parse(JSON.stringify(newValue));
                },
                deep :true
            }
        },
        mounted() {
            this.reqGetBasketList(0); // == init ==
            window.pagenationMethod = this.reqGetBasketList; // == pagenation connect ==
        }
    })

}());