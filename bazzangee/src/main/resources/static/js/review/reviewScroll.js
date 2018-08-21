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
         var time = Date.now();
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

    appendReviewHTML(reviewDto) {
        var written = reviewDto.review.writtenTime;
        var writtenDate = written.slice(0, 10)
        var writtenTime = written.slice(11, 16)
        var reviewHTML = `<li>
                           <time class="cbp_tmtime" datetime="${reviewDto.review.writtenTime}"><span>${writtenDate}</span> <span>${writtenTime}</span></time>
                           <div class="cbp_tmicon cbp_tmicon-phone"></div>
                           <div class="cbp_tmlabel">
                               <h2>${reviewDto.restaurant.name} - ${reviewDto.foodName}</h2>
                               <p>${reviewDto.review.starPoint}</p>
                               <p>${reviewDto.review.contents}</p>
                               <p>${reviewDto.userName}</p>
                               <p>${reviewDto.restaurant.address}</p>
                           </div>
                       </li>`;
       $("#timeline_standard").insertAdjacentHTML("beforeend", reviewHTML);
    }

    onFailLoad() {
    }
}