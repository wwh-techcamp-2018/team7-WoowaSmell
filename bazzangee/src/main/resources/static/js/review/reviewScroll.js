import {$, fetchManager} from "/js/utils.js";

export class ReviewScroll{
    constructor() {
        this.currentPage = 0;
        this.canLoad = true;
        document.addEventListener("DOMContentLoaded", this.onLoadDocument.bind(this));
        document.addEventListener('scroll', this.onScrollDown.bind(this));
    }

    onScrollDown() {
        if((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
            this.loadReviews();
        }
    }

    throttle(callback, wait) {
        let time = Date.now();
        return function() {
            if ((time + wait - Date.now()) < 0) {
                callback();
                time = Date.now();
            }
        }
    }

    onLoadDocument() {
        this.loadReviews();
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
        console.log('onSuccessLoad');

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

    onSuccessUpdateGood() {
        alert('좋아요 성공');
    }

    onclickGoodButton(reviewId) {
        fetchManager({
            url: '/api/reviews/' + reviewId + '/good',
            method: 'GET',
            headers: { 'content-type': 'application/json'},
            callback: this.onSuccessUpdateGood.bind(this),
            errCallback: this.onFailUpdateGood.bind(this)
        });
    }

    appendReviewHTML(reviewDto) {
        let written = reviewDto.review.writtenTime;
        let writtenDate = written.slice(0, 10)
        let writtenTime = written.slice(11, 16)
        let reviewHTML = `<li>
                            <time class="cbp_tmtime" datetime="${reviewDto.review.writtenTime}"><span>${writtenDate}</span> <span>${writtenTime}</span></time>
                            <div class="cbp_tmicon cbp_tmicon-phone"></div>
                            <div class="cbp_tmlabel" style="display: flex">
                                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                                    <img src="/img/honey_combo.png" width="100%">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding: 0px; margin-top: 10px">
                                        <button class="btn btn-warning width100 good-container" style="display: flex" onclick="reviewScroll.onclickGoodButton(${reviewDto.review.id});">
                                            <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12"><img src="/img/sad_emoji.png" width="20px"></div>
                                            <div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
                                                <span style="color: whitesmoke; font-size: 1vw">부러워요</span>
                                                <span style="color: #6b0f24; font-size: 1.2vw">${reviewDto.goodCount}</span>
                                            </div>
                                        </button>
                                    </div>
                                </div>
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8" style="padding: 0px">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <h4>${reviewDto.restaurant.name} - ${reviewDto.foodName}</h4>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                        <p>평점 : ${reviewDto.review.starPoint}</p>
                                        <p>${reviewDto.review.contents}</p>
                                        <p class="t-right fs07em">${reviewDto.userName}</p>
                                        <p class="t-right">
                                            <a href="#" style="font-weight: 600; color: aliceblue">
                                                <i class="fa fa-map-marker"></i>${reviewDto.restaurant.address}
                                            </a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </li>`;
        $("#timeline_standard").insertAdjacentHTML("beforeend", reviewHTML);
    }

    onFailLoad() {
    }

    onFailUpdateGood() {
        alert('좋아요 실패');
    }
}