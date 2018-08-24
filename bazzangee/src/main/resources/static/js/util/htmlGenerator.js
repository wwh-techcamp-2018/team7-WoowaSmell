const HtmlGenerator = (function () {
    return {
        getReviewHTML(reviewDto) {
            let written = reviewDto.review.writtenTime;
            let writtenDate = written.slice(0, 10);
            let writtenTime = written.slice(11, 16);
            let reviewId = reviewDto.review.id;
            let reviewHTML = `<li>
                            <time class="cbp_tmtime" datetime="${reviewDto.review.writtenTime}"><span>${writtenDate}</span> <span>${writtenTime}</span></time>
                            <div class="cbp_tmicon cbp_tmicon-phone"></div>
                            <div class="cbp_tmlabel" style="display: flex">
                                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                                    <img src="/img/honey_combo.png" width="100%">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding: 0px; margin-top: 10px">
                                        <button class="btn btn-warning width100 good-btn" style="text-align: center" data-value="${reviewId}">
                                            <span style="color: whitesmoke; font-size: 1vw" data-value="${reviewId}"><i class="fa fa-thumbs-up" data-value="${reviewId}"></i>부러워요</span>
                                            <span style="color: #6b0f24; font-size: 1.2vw" data-value="${reviewId}">${reviewDto.goodCount}</span>
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
            return reviewHTML;
        },

        getOrderFoodHTML(orderFood) {
            let orderFoodHTML = `<li>
                                내용을 넣어주세요.
                        </li>`;
            return orderFoodHTML;
        },

        getChatMessageHTML(messageDto, username) {
            if(messageDto.username !== username) {
                return `<li>
                    <div class="message-data">
                        <span class="message-data-name"> ${messageDto.username}</span>
                        <span class="message-data-time">${messageDto.writtenTime}</span>
                    </div>
                    <div class="message other-message">${messageDto.contents}</div>
                </li>`;
            } else {
                return `<li class="clearfix">
                    <div class="message-data align-right">
                        <span class="message-data-time" >${messageDto.writtenTime}</span> &nbsp; &nbsp;
                        <span class="message-data-name" >${messageDto.username}</span>
                    </div>
                    <div class="message my-message float-right">${messageDto.contents}</div>
                    </li>
                <li>`;
            }
        }
    }
})();