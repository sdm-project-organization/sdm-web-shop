(function() {

    // == Pagenation Sample ==
    // |ㅁㅁㅁㅁㅁ|ㅁㅁㅁㅁㅁ|ㅁㅁㅁㅁㅁ|ㅁㅁㅁㅁㅁ| <= Button
    // |   ㅁ   |   ㅁ   |   ㅁ   |   ㅁ   | <= List

    new Vue({
        el: '#shop_pagenation',
        data: {
            // == const ==
            PAGE_ITEM_COUNT : Number(document.getElementById("hidden_size_item").value), // == 아이템개수 ==
            PAGE_BTN_COUNT : Number(document.getElementById("hidden_size_btn").value), // == 버튼개수 ==
            MAX_ITEM  : Number(document.getElementById("hidden_max_item").value), // == 상품개수 ==

            // == variable ==
            maxBtn : 0, // == 전체 버튼개수 ==
            maxList : 0, // == 젠체 리스트개수 ==
            nowList : 1,
            nowBtn : 1,
            btnList : [],

            // == status ==
            availablePrevious : false,
            availableNext : false
        },
        methods: {
            // == 이전 버튼 ==
            clickPrevious() {
                if(this.nowList == 1) return;
                this.initBtnList(--this.nowList);
                this.clickBtn(this.nowList * this.PAGE_BTN_COUNT); // == 이전 리스트의 최대값 ==
            },
            // == 다음 버튼 ==
            clickNext() {
                if(this.nowList == this.maxList) return;
                this.initBtnList(++this.nowList);
                this.clickBtn((this.nowList-1) * this.PAGE_BTN_COUNT + 1); // == 이전 리스트의 최소값 ==
            },
            // == 이동 버튼 ==
            clickBtn(nowBtn) {
                if(1 > nowBtn || nowBtn > this.maxBtn) return;
                this.nowBtn = nowBtn;
                window.pagenationMethod(nowBtn-1);
            },
            // == 버튼초기화 ==
            initBtnList(nowList) {
                this.btnList = [];
                for(var i=1; i<=this.PAGE_BTN_COUNT; i++) {
                    var num = (nowList-1) * this.PAGE_BTN_COUNT + i;
                    if(num <= this.maxBtn)
                        this.btnList.push(num);
                }
                this.availablePrevious = this.nowList != 1;
                this.availableNext = this.nowList < this.maxList;
            }
        },
        mounted() {
            this.maxBtn = Math.ceil(this.MAX_ITEM  / this.PAGE_ITEM_COUNT);
            this.maxList = Math.ceil(this.maxBtn / this.PAGE_BTN_COUNT);

            this.initBtnList(this.nowList);
        }
    });

}());