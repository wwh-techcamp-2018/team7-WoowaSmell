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
                                    <img src="${reviewDto.review.imageUrl}" width="100%">
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
                                        <div class="rate" data-rate-value="${reviewDto.review.starPoint}" style="pointer-events:none;"></div>
                                        <p>${reviewDto.review.contents}</p>
                                        <p class="t-right fs07em">${reviewDto.userName}</p>
                                        <p class="t-right">
                                            <a href="#" style="font-weight: 600; color: aliceblue">
                                                <i class="fa fa-map-marker"></i>${reviewDto.restaurant.address}
                                                <button class="statistics" value="${reviewDto.restaurant.id}">(통계)</button>
                                            </a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </li>`;
            return reviewHTML;
        },

        // 첫 화면 로딩 시 호출, 리뷰 그려주기시 사용
        getOrderFoodHTML(orderFood) {
            let orderTime = orderFood.orderTime;
            let orderedDate = orderTime.slice(0, 10);
            let orderedTime = orderTime.slice(11, 16);
            let orderFoodHTML = ``;
            // 리뷰가 있는 경우
            if(orderFood.review) {
                const review = orderFood.review;
                    orderFoodHTML = `<li class="orderfood-li" data-id="${orderFood.id}">
                        <time class="cbp_tmtime" datetime="${orderFood.orderTime}"><span>${orderedDate}</span> <span>${orderedTime}</span></time>
                        <div class="cbp_tmicon cbp_tmicon-phone"></div>
                        <div class="cbp_tmlabel" style="display: flex">
                            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                                <img src="${review.imageUrl}" width="100%">
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding: 0px; margin-top: 10px">
                                    <button class="btn btn-warning width100 good-btn" style="text-align: center" data-value="${review.id}" >
                                        <span style="color: whitesmoke; font-size: 1vw" data-value="${review.id}"><i class="fa fa-thumbs-up" data-value="${review.id}"></i>부러워요</span>
                                        <span style="color: #6b0f24; font-size: 1.2vw" data-value="${review.id}">${review.goods.length}</span>
                                    </button>
                                </div>
                            </div>
                            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8" style="padding: 0px">
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <h4>${orderFood.food.restaurant.name} - ${orderFood.food.name}</h4>
                                </div>
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <div class="rate" data-rate-value="${review.starPoint}" style="pointer-events:none;"></div>
                                    <p>${review.contents}</p>
                                    <p class="t-right fs07em">${orderFood.orderedUser.name}</p>
                                    <p class="t-right">
                                        <a href="#" style="font-weight: 600; color: aliceblue">
                                            <i class="fa fa-map-marker"></i>${orderFood.food.restaurant.address}
                                        </a>
                                    </p>
                                    <div class="update-delete-btn">
                                        <input type="button" class="btn-review-update-form btn btn-danger" value="수정">
                                        <input type="button" class="btn-review-delete btn btn-danger" value="삭제">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>`;
            }
            // 리뷰가 없는 경우
            else {
                orderFoodHTML = `<li class="orderfood-li" data-id="${orderFood.id}">
                    <time class="cbp_tmtime" datetime="${orderFood.orderTime}"><span>${orderedDate}</span> <span>${orderedTime}</span></time>
                    <div class="cbp_tmicon cbp_tmicon-phone"></div>
                    <div class="cbp_tmlabel" style="display: flex">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding: 0px; margin-top: 10px">
                            </div>
                        </div>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8" style="padding: 0px">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <h4>${orderFood.food.restaurant.name} - ${orderFood.food.name}</h4>
                            </div>
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <button type="button" class="orderfood-review-write">리뷰 쓰기</button>
                                <div class="card invisible" style="width:400px ">
                                    <img class="card-img-top" src="" style="width:200px">
                                    <div class="card-body">
                                        <input type="text" name="contents" class="card-text"> <br/><br/>
                                        <input type="file" name="image" class="btn btn-primary" >
                                        <div class="rate" data-rate-value="0"></div>
                                        <button type="button" class="btn btn-danger invisible">삭제</button>
                                        <input type="button" class="btn-review-submit" value="저장">
                                        <input type="button" class="btn-review-submit-cancel btn btn-danger" value="취소">
                                    </div>
                                </div>
                                <p class="t-right fs07em">${orderFood.orderedUser.name}</p>
                                <p class="t-right">
                                    <a href="#" style="font-weight: 600; color: aliceblue">
                                        <i class="fa fa-map-marker"></i>${orderFood.food.restaurant.address}
                                    </a>
                                </p>
                            </div>
                        </div>
                    </div>
                </li>`;
            }
            return orderFoodHTML;
        },

        // 자신 옷장에서 리뷰 추가하고 난뒤 HTML
        getCreateReviewHTML(reviewDto) {
           let orderTime = reviewDto.orderedTime;
           let orderedDate = orderTime.slice(0, 10);
           let orderedTime = orderTime.slice(11, 16);
           let reviewId = reviewDto.review.id;
           const reviewDtoHTML = `
               <time class="cbp_tmtime" datetime="${orderTime}"><span>${orderedDate}</span> <span>${orderedTime}</span></time>
               <div class="cbp_tmicon cbp_tmicon-phone"></div>
               <div class="cbp_tmlabel" style="display: flex">
                   <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                       <img src="${reviewDto.review.imageUrl}" width="100%">
                       <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding: 0px; margin-top: 10px">
                           <button class="btn btn-warning width100 good-btn" style="text-align: center" data-value="${reviewId}" >
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
                           <div class="rate" data-rate-value="${reviewDto.review.starPoint}" style="pointer-events:none;"></div>
                           <p>${reviewDto.review.contents}</p>
                           <p class="t-right fs07em">${reviewDto.userName}</p>
                           <p class="t-right">
                               <a href="#" style="font-weight: 600; color: aliceblue">
                                   <i class="fa fa-map-marker"></i>${reviewDto.restaurant.address}
                               </a>
                           </p>
                            <div class="update-delete-btn">
                              <input type="button" class="btn-review-update-form btn btn-danger" value="수정">
                              <input type="button" class="btn-review-delete btn btn-danger" value="삭제">
                            </div>
                       </div>
                   </div>
               </div>`;
            return reviewDtoHTML;
        },


        getDeleteReviewHTML(orderFood) {
            let orderTime = orderFood.orderTime;
            let orderedDate = orderTime.slice(0, 10);
            let orderedTime = orderTime.slice(11, 16);
            const orderFoodHTML = `
                   <time class="cbp_tmtime" datetime="${orderFood.orderTime}"><span>${orderedDate}</span> <span>${orderedTime}</span></time>
                    <div class="cbp_tmicon cbp_tmicon-phone"></div>
                    <div class="cbp_tmlabel" style="display: flex">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding: 0px; margin-top: 10px">
                            </div>
                        </div>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8" style="padding: 0px">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <h4>${orderFood.food.restaurant.name} - ${orderFood.food.name}</h4>
                            </div>
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <button type="button" class="orderfood-review-write">리뷰 쓰기</button>
                                <div class="card invisible" style="width:400px ">
                                    <img class="card-img-top" src="" style="width:200px">
                                    <div class="card-body">
                                        <input type="text" name="contents" class="card-text"> <br/><br/>
                                        <input type="file" name="image" class="btn btn-primary" >
                                        <div class="rate" data-rate-value="0"></div>
                                        <button type="button" class="btn btn-danger invisible">삭제</button>
                                        <input type="button" class="btn-review-submit" value="저장">
                                        <input type="button" class="btn-review-submit-cancel btn btn-danger" value="취소">
                                    </div>
                                </div>
                                <p class="t-right fs07em">${orderFood.orderedUser.name}</p>
                                <p class="t-right">
                                    <a href="#" style="font-weight: 600; color: aliceblue">
                                        <i class="fa fa-map-marker"></i>${orderFood.food.restaurant.address}
                                    </a>
                                </p>
                            </div>
                        </div>
                    </div>
                `;
            return orderFoodHTML;
        },

        // 자신의 옷장 수정 폼
        getReviewUpdateFormHTML(orderFood) {
            let orderTime = orderFood.orderTime;
            let orderedDate = orderTime.slice(0, 10);
            let orderedTime = orderTime.slice(11, 16);
            const review = orderFood.review;
            const orderFoodHTML = `
                        <time class="cbp_tmtime" datetime="${orderFood.orderTime}"><span>${orderedDate}</span> <span>${orderedTime}</span></time>
                        <div class="cbp_tmicon cbp_tmicon-phone"></div>
                        <div class="cbp_tmlabel" style="display: flex">
                            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                                <img src="${review.imageUrl}" width="100%" id="update-image-area">
                                <input type="hidden" class="exist-image-url" name="existImageUrl" value="${review.imageUrl}">
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding: 0px; margin-top: 10px">
                                    <button class="btn btn-warning width100 good-btn" style="text-align: center" data-value="${review.id}" >
                                        <span style="color: whitesmoke; font-size: 1vw" data-value="${review.id}"><i class="fa fa-thumbs-up" data-value="${review.id}"></i>부러워요</span>
                                        <span style="color: #6b0f24; font-size: 1.2vw" data-value="${review.id}">${review.goods.length}</span>
                                    </button>
                                </div>
                            </div>
                            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8" style="padding: 0px">
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <h4>${orderFood.food.restaurant.name} - ${orderFood.food.name}</h4>
                                </div>
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 card">
                                    <div class="rate" data-rate-value="${review.starPoint}"></div>
                                    <p><input type="text" name="contents" class="card-text" value="${orderFood.review.contents}"> <br/><br/></p>
                                     <input type="file" name="image" class="btn-image-update-upload btn btn-primary" >
                                    <p class="t-right fs07em">${orderFood.orderedUser.name}</p>
                                    <p class="t-right">
                                        <a href="#" style="font-weight: 600; color: aliceblue">
                                            <i class="fa fa-map-marker"></i>${orderFood.food.restaurant.address}
                                        </a>
                                    </p>
                                    <div class="update-delete-btn">
                                        <input type="button" class="btn-review-update btn btn-danger" value="수정">
                                        <input type="button" class="btn-review-update-cancel btn btn-danger" value="취소">
                                    </div>
                                </div>
                            </div>
                        </div>
                    `
            ;
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