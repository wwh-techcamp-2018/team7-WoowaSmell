import {fetchManager, throttle, addDropdownListener} from "/js/util/utils.js";
import {logoutListener} from "/js/user/kakaologout.js";

function $_(selector) {
    return document.querySelector(selector);
}

export class ReviewScroll{
    constructor(id) {
        this.foodCategoryId = id;
        this.filterId = 0;
        this.currentPage = 0;
        this.canLoad = true;
        this.clickedTarget = null;
        document.addEventListener("DOMContentLoaded", this.onLoadDocument.bind(this));
        document.addEventListener('scroll', this.onScrollDown.bind(this));
        $_("#timeline_standard").addEventListener("click", this.onclickGoodButton.bind(this));
        $_("#buttons").addEventListener("click", this.onClickCategories.bind(this));
        $_("#timeline-align-container").addEventListener("click", this.onClickRadios.bind(this));
        addDropdownListener();
        $_("#logout").addEventListener("click", logoutListener);
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

    onclickGoodButton({target}) {
        if (!target.classList.contains("good-btn") && !target.parentElement.classList.contains("good-btn")
            && !target.parentElement.parentElement.classList.contains("good-btn"))
            return;

        this.clickedTarget = target;
        fetchManager({
            url: '/api/reviews/' + this.clickedTarget.getAttribute("data-value") + '/good',
            method: 'GET',
            headers: { 'content-type': 'application/json'},
            callback: this.onSuccessUpdateGood.bind(this),
            errCallback: this.onFailUpdateGood.bind(this)
        });
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
        })
    }

    onSuccessUpdateGood(response) {
        response.json().then((reviewDto) => {
            this.clickedTarget.closest(".good-btn").lastElementChild.innerHTML = reviewDto.goodCount;
        });
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