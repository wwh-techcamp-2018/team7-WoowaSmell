import {$, fetchManager, addDropdownListener} from "/js/util/utils.js";

export class ClosetReviewScroll{
    constructor(id) {
        this.foodCategoryId = id;
        this.filterId = 0;
        document.addEventListener("DOMContentLoaded", this.loadReviews.bind(this));
        $("#buttons").addEventListener("click", this.onClickCategories.bind(this));
        $("#timeline-align-container").addEventListener("click", this.onClickRadios.bind(this));
        addDropdownListener();
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
        $("#buttons").children[this.foodCategoryId].classList.toggle("clicked");
        target.classList.toggle("clicked");
        this.foodCategoryId = target.value;
        this.removeAllTimelines();
        this.loadReviews();
    }

    removeAllTimelines() {
        $("#timeline_standard").innerHTML = '';
    }

    loadReviews() {
        $("#loader").classList.toggle("invisible");
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
            $("#loader").classList.toggle("invisible");
        })
    }

    appendOrderFoodHTML(orderFood) {
        var orderFoodHTML = HtmlGenerator.getOrderFoodHTML(orderFood);
       $("#timeline_standard").insertAdjacentHTML("beforeend", orderFoodHTML);
    }

    onFailLoad(msg) {
        console.log("Error Message : {}", msg);
        $("#loader").classList.toggle("invisible");
        if(!$("#timeline_standard").hasChildNodes()) {
            var noImageHTML = `<img src="/img/noImage.png" width="500" height="auto"/>`;
            $("#timeline_standard").insertAdjacentHTML("beforeend", noImageHTML);
        }
    }
}