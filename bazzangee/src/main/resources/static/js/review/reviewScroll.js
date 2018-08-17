import {$, fetchManager} from "/js/utils.js";

export class ReviewScroll{
    constructor() {
        this.currentPage = 0;
        document.addEventListener("DOMContentLoaded", this.onLoadDocument.bind(this));
        document.addEventListener('scroll', this.onScrollDown.bind(this));
    }

    onScrollDown() {
        if(window.scrollY == document.documentElement.scrollHeight - document.documentElement.clientHeight) {
            console.log(window.scrollY);
            this.loadReviews();
        }
    }

    onLoadDocument() {
        this.loadReviews();
    }

    loadReviews() {
    console.log('loadReviews');
        $("#loader").classList.toggle("invisible");
        setTimeout(() => {
            fetchManager({
              url: '/api/reviews?page=' + this.currentPage,
              method: 'GET',
              headers: { 'content-type': 'application/json'},
              callback: this.onSuccessLoad.bind(this),
              errCallback: this.onFailLoad.bind(this)
             });
        }, 2000);
    }

    onSuccessLoad(response) {
        response.json().then((reviews) => {
            this.currentPage += 1;
            var reviewHTML = ``;
            reviews.forEach((review) => {
                reviewHTML += `<li>
                                   <time class="cbp_tmtime" datetime="${review.writtenTime}"><span>4/10/13</span> <span>18:30</span></time>
                                   <div class="cbp_tmicon cbp_tmicon-phone"></div>
                                   <div class="cbp_tmlabel">
                                       <h2>${review.title}</h2>
                                       <p>${review.contents}</p>
                                   </div>
                               </li>`;
//
//                reviewHTML += `<div>
//                                 <h1>${review.title}</h1>
//                              <h2>${review.contents}</h2>
//                                <img height="50" width="50" src="https://st2.depositphotos.com/7520316/12164/v/950/depositphotos_121647576-stock-illustration-fried-chicken-icon-flat-design.jpg" alt="">
//                                </div>`;
            })
            $("#timeline_standard").insertAdjacentHTML("beforeend", reviewHTML);
            console.log(reviews);
            $("#loader").classList.toggle("invisible");
        })
    }


    onFailLoad() {
    }
}