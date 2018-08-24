import {fetchManager, addDropdownListener} from "/js/util/utils.js";

function $_(selector) {
    return document.querySelector(selector);
}

export class ClosetReviewScroll{
    constructor(id) {
        this.foodCategoryId = id;
        this.filterId = 0;
        $_("#buttons").addEventListener("click", this.onClickCategories.bind(this));
        $_("#timeline-align-container").addEventListener("click", this.onClickRadios.bind(this));
        addDropdownListener();
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
            orderFoods.forEach(this.appendOrderFoodHTML);
            $_("#loader").classList.toggle("invisible");
            $(".rate").rate();
        })
    }

    appendOrderFoodHTML(orderFood) {
        var orderFoodHTML = HtmlGenerator.getOrderFoodHTML(orderFood);
       $_("#timeline_standard").insertAdjacentHTML("beforeend", orderFoodHTML);
    }

    onFailLoad(msg) {
        console.log("Error Message : {}", msg);
        $_("#loader").classList.toggle("invisible");
        var noImageHTML = `<img src="/img/noImage.png" width="500" height="auto"/>`;
        $_("#timeline_standard").insertAdjacentHTML("beforeend", noImageHTML);
    }
}