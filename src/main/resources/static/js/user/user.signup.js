(function () {

    new Vue({
        el: '#user_signup',
        data: {
            // userId: "",
            // password: "",
            // passwordCopy: ""

            visibleMessage: false,
            message: "",

            userId: "user@kakao.com",
            password: "user1234",
            passwordCopy: "user1234"
        },
        methods: {
            // == Click Signup ==
            clickSignup() {
                // 1) 이메일검증

                // 2) 패스워드검증
                if(this.password != this.passwordCopy) {
                    this.popupSignupFail("패스워드가 일치하지 않습니다.");
                    return
                }

                this.reqUserSignup()
            },
            // == Request Signup ==
            reqUserSignup() {
                // == FormData 형식처리 ==
                var signupForm = new FormData();
                signupForm.set("userId", this.userId);
                signupForm.set("password", this.password);

                var that = this;
                axios({
                    method : "POST",
                    url : "/user/signup",
                    config : {
                        headers: {
                            "Content-Type" : "multipart/form-data"
                        }
                    },
                    data : signupForm
                }).then(function (res) {
                    that.visibleMessage = false;
                    that.popupSignupSuccess();
                }).catch(function (err) {
                    if(err.response) {
                        that.visibleMessage = true;
                        switch(err.response.status) {
                            case 400: // == BAD_REQUEST ==
                                that.message = "ID - 이메일 형식<br>PW - 영문과 숫자가 조합된 8자리 이상 문자";
                                that.popupSignupFail("형식이 올바르지 않습니다.");
                                break;
                            case 409: // == CONFLICT ==
                                that.message = "이미 존재하는 아이디 입니다.";
                                that.popupSignupFail("이미 존재하는 아이디 입니다.");
                                break;
                            default:
                                that.message = "문제가 발생했습니다. 관리자에게 문의해주세요.";
                                that.popupSignupFail("관리자에게 문의해주세요.");
                        }
                    } else {
                        console.log(err);
                    }
                });
            },
            // == Popup Signup Success ==
            popupSignupSuccess() {
                swal({
                    title :"가입성공",
                    text : "로그인하여 서비스를 이용할 수 있습니다.",
                    type : "success",
                    confirmButtonText: "확인"
                })
            },
            // == Popup Signup Fail ==
            popupSignupFail(message) {
                swal({
                    title :"가입실패",
                    text : message,
                    type : "error",
                    confirmButtonText: "확인"
                })
            }
        } // methods

    })

}());