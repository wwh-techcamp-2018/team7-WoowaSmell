import {$, fetchManager} from "/js/util/utils.js";

export class ClosetReviewScroll{
    constructor(id) {
        this.foodCategoryId = id;
        this.filterId = 0;
        document.addEventListener("DOMContentLoaded", this.loadReviews.bind(this));
        $("#buttons").addEventListener("click", this.onClickCategories.bind(this));
        $("#timeline-align-container").addEventListener("click", this.onClickRadios.bind(this));
    }

    onClickRadios({target}) {
        if(target.type != "radio") {
            return;
        }
        this.canLoad = true;
        console.log("now is ", target.value);
        if(target.value == this.filterId) {
            return;
        }
        this.filterId = target.value;
        this.currentPage = 0;
        console.log("target", target);
        this.removeAllTimelines();
        this.loadReviews();
    }

    onClickCategories({target}) {
        if(target.type != "submit") {
            return;
        }
        console.log("now is ", target.value);
        if(target.value == this.foodCategoryId) {
            return;
        }
        $("#buttons").children[this.foodCategoryId].classList.toggle("clicked");
        target.classList.toggle("clicked");
        this.foodCategoryId = target.value;
        console.log("target", target);
        this.removeAllTimelines();
        this.loadReviews();
    }

    removeAllTimelines() {
        $("#timeline_standard").innerHTML = '';
    }

    throttle(callback, wait) {
         var time = Date.now();
         return function() {
             if ((time + wait - Date.now()) < 0) {
               callback();
               time = Date.now();
             }
         }
     }

    loadReviews() {
        $("#loader").classList.toggle("invisible");
        if(this.foodCategoryId == 0) {
            fetchManager({
                url: '/api/orderfoods/?filterId=' + this.filterId,
                method: 'GET',
                headers: { 'content-type': 'application/json'},
                callback: this.onSuccessLoad.bind(this),
                errCallback: this.onFailLoad.bind(this)
             });
        } else {
            fetchManager({
                url: '/api/orderfoods/categories/?categoryId=' + this.foodCategoryId + '&filterId=' + this.filterId,
                method: 'GET',
                headers: { 'content-type': 'application/json'},
                callback: this.onSuccessLoad.bind(this),
                errCallback: this.onFailLoad.bind(this)
             });
        }
    }

    onSuccessLoad(response) {
        console.log('onSuccessLoad');
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