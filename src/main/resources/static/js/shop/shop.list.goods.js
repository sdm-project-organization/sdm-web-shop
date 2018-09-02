(function () {

    new Vue({
        el: '#shop_goods_list',
        data: { listGoods: [] },
        methods: {
            // == Goods 요청 ==
            reqGetGoodsList(num) {
                var that = this;
                axios.get("/api/goods?page=" + num).then(function (res) {
                    that.listGoods = res.data;
                }).catch(function (err) {
                    if(err.response) {
                        switch(err.response.status) {
                            case 404:
                                break;
                            default:
                                that.popup("관리자에게 문의해 주세요.");
                        }
                    } else console.log(err);
                });
            },
            // == Basket 등록 ==
            reqCreateBasket(num) {
                var that = this;
                axios.post("/api/basket", {
                    "goodsNo" : num,
                    "count" : "1"
                }).then(function (res) {
                    that.popupBasketSuccess();
                }).catch(function (err) {
                    if(err.response) {
                        switch(err.response.status) {
                            case 403:
                                location.href = "/";
                                break;
                            case 409:
                                that.popup("장바구니에 이미 데이터가 있습니다.");
                                break;
                            case 410:
                                that.popup("재고가 부족합니다.");
                                break;
                            case 416:
                                that.popup("최대 100개까지 담을 수 있습니다.");
                                break;
                            default:
                                that.popup("관리자에게 문의해 주세요.");
                        }
                    } else {
                        console.log(err);
                    }
                });
            },
            // == Popup Basket Success ==
            popupBasketSuccess() {
                swal({
                    title :"장바구니 담기완료",
                    type : "success",
                    confirmButtonText: "장바구니",
                    showCancelButton: true,
                    cancelButtonText: "계속쇼핑"
                }).then(function (result) {
                    if (result.value) location.href = "/shop/basket";
                });
            },
            // == Popup ==
            popup(message) {
                swal({ title : message, confirmButtonText: "확인" });
            }
        },
        mounted() {
            this.reqGetGoodsList(0); // == init ==
            window.pagenationMethod = this.reqGetGoodsList; // == pagenation connect ==
        }
    })

}());