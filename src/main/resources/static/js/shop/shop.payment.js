(function () {

    new Vue({
        el: '#shop_payment',
        data: {
            tel: "",
            address: ""
        },
        methods: {
            // == Popup Payment ==
            clickPayment() {
                // == Front-End Validation ==
                if(this.tel == "") {
                    this.popup("전화번호를 입력해주세요.");
                    return;
                }
                if(this.address == "") {
                    this.popup("주소를 입력해주세요.");
                    return;
                }
                this.popupPayment();
            },
            // == Popup Payment ==
            popupPayment() {
                var that = this;
                swal({
                    title: "결제진행",
                    text: "결제를 진행하시겠습니까?",
                    type: "warning",
                    confirmButtonText: "결제",
                    showCancelButton: true,
                    cancelButtonText: "취소"
                }).then(function (result) {
                    if (result.value) that.reqCreatePurchase();
                });
            },
            // == Popup Payment Success ==
            popupPaymentSuccess() {
                swal({
                    title :"결제완료",
                    type : "success",
                    confirmButtonText: "구매내역확인",
                    allowOutsideClick: false
                }).then(function (result) {
                    location.href = "/shop/purchase";
                });
            },
            // == Popup Payment Fail ==
            popupPaymentFail(message) {
                swal({
                    title :"결제실패",
                    text : message,
                    type : "error",
                    confirmButtonText: "확인",
                    allowOutsideClick: false
                }).then(function (result) {
                    location.href = "/shop/basket";
                });
            },
            // == Request Purchase ==
            reqCreatePurchase() {
                var that = this;
                axios.post("/api/purchase", {
                    "tel" :this.tel,
                    "address" : this.address
                }).then(function (res) {
                    that.popupPaymentSuccess();
                }).catch(function (err) {
                    if(err.response) {
                        switch(err.response.status) {
                            case 404:
                                that.popupPaymentFail("장바구니 아이템을 찾을 수 없습니다.");
                                break;
                            case 410:
                                that.popupPaymentFail("재고가 소진됬습니다.");
                                break;
                            default:
                                that.popupPaymentFail("관리자에게 문의해주세요.");
                        }
                    }

                });
            },
            // == Popup ==
            popup(message) {
                swal({ title : message, confirmButtonText: "확인" });
            }
        },
        mounted() {

        }
    })

}());