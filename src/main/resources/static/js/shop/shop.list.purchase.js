(function () {

    new Vue({
        el: '#shop_purchase_list',
        data: { listPurchase: [] },
        methods: {
            // == Purchase List 요청 ==
            reqGetPurchaseList(num) {
                var that = this;
                axios.get("/api/purchase/user?page=" + num)
                    .then(function (res) {
                        // TODO == TEST ==
                        console.log(res.data);
                        that.listPurchase = res.data;
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
            // == Click Payment Cancel ==
            clickPaymentCancel(id) {
                this.popupPurchaseCancel(id);
            },
            // == Popup Purchase Cancel ==
            popupPurchaseCancel(id) {
                var that = this;
                swal({
                    title: "구매취소",
                    text: "구매한 내역을 취소하시겠습니까? \n 모든 상품이 취소됩니다.",
                    type: "warning",
                    confirmButtonText: "확인",
                    showCancelButton: true,
                    cancelButtonText: "취소"
                }).then(function (result) {
                    if (result.value) that.reqCancelPurchase(id);
                });
            },
            // == Request Cancel Purchase ==
            reqCancelPurchase(id) {
                var that = this;
                axios.put("/api/purchase/cancel/" + id).then(function (res) {
                    that.listPurchase.forEach(function(v) {
                       if(v.purchaseNo == id) v.payment = false;
                    });
                    that.popupPaymentCancelSuccess();
                }).catch(function (err) {
                    if(err.response) {
                        switch(err.response.status) {
                            case 404:
                                that.popupPaymentCancelFail("구매내역을 찾을 수 없습니다.");
                                break;
                            case 406:
                                that.popupPaymentCancelFail("취소할 수 없는 상품입니다.");
                                break;
                            default:
                                that.popupPaymentCancelFail("관리자에게 문의해주세요.");
                        }
                    } else console.log(err);
                });
            },
            // == Popup Payment Complete ==
            popupPaymentCancelSuccess() {
                swal({
                    title :"구매취소 완료",
                    type : "success",
                    confirmButtonText: "확인"
                })
            },
            // == Popup Payment Fail ==
            popupPaymentCancelFail(message) {
                swal({
                    title :"구매취소 실패",
                    text : message,
                    type : "error",
                    confirmButtonText: "확인",
                    allowOutsideClick: false
                }).then(function (result) {
                    location.href = "/shop/purchase";
                });
            }

        },
        mounted() {
            this.reqGetPurchaseList(0); // == init ==
            window.pagenationMethod = this.reqGetPurchaseList; // == pagenation connect ==
        }
    })

}());