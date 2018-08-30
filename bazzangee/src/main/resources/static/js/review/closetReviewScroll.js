import {fetchManager} from "/js/util/utils.js";

function $_(selector) {
    return document.querySelector(selector);
}

export class ClosetReviewScroll{
    constructor({foodCategoryId, chartobj}) {
        this.foodCategoryId = foodCategoryId;
        this.chart = chartobj;
        this.filterId = 0;
        $_("#buttons").addEventListener("click", this.onClickCategories.bind(this));
        $_("#timeline-align-container").addEventListener("click", this.onClickRadios.bind(this));
        this.loadReviews();
    }

    onClickRadios({target}) {
        if(target.type != "radio") {
            return;
        }
        this.canLoad = true;
        if(target.value == this.filterId) {
            return;
        }
        this.filterId = target.value;
        this.currentPage = 0;
        this.removeAllTimelines();
        this.loadReviews();
    }

    onClickCategories({target}) {
        if(target.type != "submit") {
            return;
        }
        if(target.value == this.foodCategoryId) {
            return;
        }
        $_("#buttons").children[this.foodCategoryId].classList.toggle("clicked");
        target.classList.toggle("clicked");
        this.foodCategoryId = target.value;
        this.removeAllTimelines();
        this.loadReviews();
    }

    removeAllTimelines() {
        $_("#timeline_standard").innerHTML = '';
        $_("#receipt_standard").innerHTML = '';
    }

    loadReviews() {
        $_("#loader").classList.toggle("invisible");
        const url = (this.foodCategoryId == 0) ? '/api/orderfoods/?filterId=' + this.filterId
                        : '/api/orderfoods/categories/?categoryId=' + this.foodCategoryId + '&filterId=' + this.filterId;
        fetchManager({
            url: url,
            method: 'GET',
            headers: { 'content-type': 'application/json'},
            callback: this.onSuccessLoad.bind(this),
            errCallback: this.onFailLoad.bind(this)
         });
    }

    onSuccessLoad(response) {
        response.json().then((orderFoods) => {
        var orderFoodsWithReview = [];
        var orderFoodsWithoutReview = [];
        orderFoods.forEach((orderfood) => {
            if(orderfood.review) {
                orderFoodsWithReview.push(orderfood);
            } else {
                orderFoodsWithoutReview.push(orderfood);
            }
        })
//            console.log("WithReviews : ", orderFoodsWithReview)
//            console.log("WithoutReviews : ", orderFoodsWithoutReview)
            orderFoodsWithReview.forEach(this.appendOrderFoodsWithReviewHTML);
            orderFoodsWithoutReview.forEach(this.appendOrderFoodsWithoutReviewHTML);
            $_("#loader").classList.toggle("invisible");
            $(".rate").rate();
            this.chart.addChartListener();
        })
    }

    // 옷장 첫 로딩시 호출
    appendOrderFoodsWithReviewHTML(orderFood) {
//    console.log(orderFood)

        var OrderFoodWithReviewHTML = HtmlGenerator.getOrderFoodWithReviewHTML(orderFood);
       $_("#timeline_standard").insertAdjacentHTML("beforeend", OrderFoodWithReviewHTML);
    }

    appendOrderFoodsWithoutReviewHTML(orderFood) {
//    console.log(orderFood)

        var OrderFoodWithoutReviewHTML = HtmlGenerator.getOrderFoodWithoutReviewHTML(orderFood);
       $_("#receipt_standard").insertAdjacentHTML("beforeend", OrderFoodWithoutReviewHTML);
    }

    onFailLoad(msg) {
        console.log("Error Message : {}", msg);
        $_("#loader").classList.toggle("invisible");
        var noImageHTML = `<img src="/img/noImage.png" width="500" height="auto"/>`;
        $_("#timeline_standard").insertAdjacentHTML("beforeend", noImageHTML);
    }
}