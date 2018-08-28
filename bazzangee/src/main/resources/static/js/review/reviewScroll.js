import {fetchManager, throttle} from "/js/util/utils.js";
import {logoutListener} from "/js/user/kakaologout.js";

function $_(selector) {
    return document.querySelector(selector);
}

export class ReviewScroll{
    constructor({foodCategoryId, chatobj, chartobj}) {
        this.foodCategoryId = foodCategoryId;
        this.chat = chatobj;
        this.chart = chartobj;
        this.filterId = 0;
        this.currentPage = 0;
        this.canLoad = true;
        this.clickedTarget = null;
        document.addEventListener("DOMContentLoaded", this.onLoadDocument.bind(this));
        document.addEventListener('scroll', this.onScrollDown.bind(this));
        $_("#buttons").addEventListener("click", this.onClickCategories.bind(this));
        $_("#timeline-align-container").addEventListener("click", this.onClickRadios.bind(this));
        // $_("#logout").addEventListener("click", logoutListener);
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
        this.canLoad = true;
        if(target.value == this.foodCategoryId) {
            return;
        }
        $_("#buttons").children[this.foodCategoryId].classList.toggle("clicked");
        target.classList.toggle("clicked");
        this.foodCategoryId = target.value;
        this.currentPage = 0;
        this.removeAllTimelines();
        this.loadReviews();
    }

    removeAllTimelines() {
        $_("#timeline_standard").innerHTML = '';
    }

    onScrollDown() {
        if((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
            this.loadReviews();
        }
    }

    onLoadDocument() {
        this.loadReviews();
    }

    loadReviews() {
        if(!this.canLoad) return;
        this.canLoad = false;
        $_("#loader").classList.toggle("invisible");
        const url = (this.foodCategoryId == 0) ? '/api/reviews?page=' + this.currentPage + '&filterId=' + this.filterId
                        : '/api/reviews/categories/?page=' + this.currentPage + '&categoryId=' + this.foodCategoryId + '&filterId=' + this.filterId;
        fetchManager({
          url: url,
          method: 'GET',
          headers: { 'content-type': 'application/json'},
          callback: this.onSuccessLoad.bind(this),
          errCallback: this.onFailLoad.bind(this)
         });
    }

    onSuccessLoad(response) {
        response.json().then((reviews) => {
            this.chat.changeChatRoom(this.foodCategoryId);
            if(reviews.length === 0) {
                this.canLoad = false;
                document.removeEventListener('scroll', this.onScrollDown.bind(this));
                $_("#loader").classList.toggle("invisible");
                return;
            }
            this.currentPage += 1;
            reviews.forEach(this.appendReviewHTML);
            $_("#loader").classList.toggle("invisible");
            this.canLoad = true;
            $(".rate").rate();
            this.chart.addChartListener();
        })
    }

    appendReviewHTML(reviewDto) {
        $_("#timeline_standard").insertAdjacentHTML("beforeend", HtmlGenerator.getReviewHTML(reviewDto));
    }

    onFailLoad(msg) {
        console.log("Error Message : {}", msg);
        $_("#loader").classList.toggle("invisible");
        if(!$_("#timeline_standard").hasChildNodes()) {
            var noImageHTML = `<img src="/img/noImage.png" width="500" height="auto"/>`;
            $_("#timeline_standard").insertAdjacentHTML("beforeend", noImageHTML);
        }
    }

    onFailUpdateGood(error) {
        alert(error.message);
    }
}