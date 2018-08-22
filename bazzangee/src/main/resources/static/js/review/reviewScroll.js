import {$, fetchManager, throttle} from "/js/util/utils.js";

export class ReviewScroll{
    constructor() {
        this.currentPage = 0;
        this.canLoad = true;
        this.clickedTarget = null;
        document.addEventListener("DOMContentLoaded", this.onLoadDocument.bind(this));
        document.addEventListener('scroll', this.onScrollDown.bind(this));
        $("#timeline_standard").addEventListener("click", this.onclickGoodButton.bind(this));
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
        $("#loader").classList.toggle("invisible");
        fetchManager({
            url: '/api/reviews?page=' + this.currentPage,
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
            $("#loader").classList.toggle("invisible");
            return;
        }
        this.currentPage += 1;
        reviews.forEach(this.appendReviewHTML);
        $("#loader").classList.toggle("invisible");
        this.canLoad = true;
    })
    }

    onSuccessUpdateGood(response) {
        response.json().then((reviewDto) => {
            this.clickedTarget.closest(".good-btn").lastElementChild.innerHTML = reviewDto.goodCount;
        });
    }

    appendReviewHTML(reviewDto) {
        $("#timeline_standard").insertAdjacentHTML("beforeend", HtmlGenerator.getReviewHTML(reviewDto));
    }

    onFailLoad() {
    }

    onFailUpdateGood(error) {
        alert(error.message);
    }
}