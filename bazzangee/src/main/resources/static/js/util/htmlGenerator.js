//const iconPath = '/img/category_image_no_name/';
//const iconImages = ['chicken.png', 'pizza.png', 'hansik.png', 'jungsik.png', 'jokbal.png', 'ilsik.png', 'yasik.png']
const HtmlGenerator = (function () {
    const iconPath = '/img/category_image_no_name/';
    const iconImages = ['chicken.png', 'pizza.png', 'hansik.png', 'jungsik.png', 'jokbal.png', 'ilsik.png', 'yasik.png']
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
                                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 card-contents" style="padding: 0px">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <h4>${reviewDto.foodName}</h4>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                        <div class="rate" data-rate-value="${reviewDto.review.starPoint}" style="pointer-events:none;"></div>
                                        <p>${reviewDto.review.contents}</p>
                                        <p class="t-right fs07em">${reviewDto.userName}</p>
                                        <p class="t-right">
                                            <a href="#" style="font-weight: 600; color: aliceblue">
                                                <i class="fa fa-map-marker"> ${reviewDto.restaurant.name}</i>
                                            </a>
                                            <button class="statistics" value="${reviewDto.restaurant.id}"></button>
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
            if (orderFood.review) {
                const review = orderFood.review;
                let contents = orderFood.review.contents.replace(/(?:\r\n|\r|\n)/g, '<br/>');
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
                            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 card-contents" style="padding: 0px">
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <h4>${orderFood.food.name}</h4>
                                </div>
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <div class="rate" data-rate-value="${review.starPoint}" style="pointer-events:none;"></div>
                                    <p>${contents}</p>
                                    <p class="t-right fs07em">${orderFood.orderedUser.name}</p>
                                    <p class="t-right">
                                        <a href="#" style="font-weight: 600; color: aliceblue">
                                            <i class="fa fa-map-marker"> ${orderFood.food.restaurant.name}</i>
                                        </a>
                                        <button class="statistics" value="${orderFood.food.restaurant.id}"></button>
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
                            <button type="button" class="btn btn-danger btn-remove-image invisible">X</button>
                            <img class="review_upload_image" src="" width="100%">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding: 0px; margin-top: 10px">
                            </div>
                        </div>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8" style="padding: 0px">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <h4>${orderFood.food.name}</h4>
                            </div>
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="review-create-btn" align="center">
                                    <button type="button" class="orderfood-review-write btn">리뷰 <img class="review-create-btn-image" src="/img/review_icon.png" > 쓰기</button>
                                </div>
                                <div class="card card-position invisible">
                                    <div class="card-body">
                                        <div class="rate" data-rate-value="0"></div>
                                        <textarea rows="4" cols="50" name="contents" class="card-text" placeholder="리뷰를 작성해주세요."></textarea>
                                        <input type="file" name="image" class="btn btn-primary" accept="image/*">
                                        <div class="submit-cancel-btn">
                                            <input type="button" class="btn-review-submit btn btn-danger" value="저장">
                                            <input type="button" class="btn-review-submit-cancel btn btn-danger" value="취소">
                                        </div>
                                    </div>
                                </div>
                                <p class="t-right fs07em">${orderFood.orderedUser.name}</p>
                                <p class="t-right">
                                    <a href="#" style="font-weight: 600; color: aliceblue">
                                        <i class="fa fa-map-marker"> ${orderFood.food.restaurant.name}</i>
                                    </a>
                                    <button class="statistics" value="${orderFood.food.restaurant.id}"></button>
                                </p>
                            </div>
                        </div>
                    </div>
                </li>`;
            }
            return orderFoodHTML;
        },
        getOrderFoodWithReviewHTML(orderFood) {
            let orderTime = orderFood.orderTime;
            let orderedDate = orderTime.slice(0, 10);
            let orderedTime = orderTime.slice(11, 16);
            let orderFoodHTML = ``;
            // 리뷰가 있는 경우
            const review = orderFood.review;
            let contents = orderFood.review.contents.replace(/(?:\r\n|\r|\n)/g, '<br/>');
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
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 card-contents" style="padding: 0px">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <h4>${orderFood.food.name}</h4>
                            </div>
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="rate" data-rate-value="${review.starPoint}" style="pointer-events:none;"></div>
                                <p>${contents}</p>
                                <p class="t-right">
                                    <a href="#" style="font-weight: 600; color: aliceblue">
                                        <i class="fa fa-map-marker"> ${orderFood.food.restaurant.name}</i>
                                    </a>
                                    <button class="statistics" value="${orderFood.food.restaurant.id}"></button>
                                </p>
                                <div class="update-delete-btn">
                                    <input type="button" class="btn-review-update-form btn btn-danger" value="수정">
                                    <input type="button" class="btn-review-delete btn btn-danger" value="삭제">
                                </div>
                            </div>
                        </div>
                    </div>
                </li>`;
            return orderFoodHTML;
        },
        getOrderFoodWithoutReviewHTML(orderFood) {
            let defaultImageURL = iconPath + iconImages[orderFood.food.restaurant.foodCategory.id - 1]
            let orderTime = orderFood.orderTime;
            let orderedDate = orderTime.slice(0, 10);
            let orderedTime = orderTime.slice(11, 16);
            let orderFoodHTML = ``;
            // 리뷰 없는경우
            orderFoodHTML = `<li class="orderfood-li" data-id="${orderFood.id}">
                <time class="cbp_tmtime" datetime="${orderFood.orderTime}"><span>${orderedDate}</span> <span>${orderedTime}</span></time>
                <div class="cbp_tmicon cbp_tmicon-phone"></div>
                <div class="cbp_tmlabel" style="display: flex">
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <img src="${defaultImageURL}" width="100%">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding: 0px; margin-top: 10px">
                        </div>
                    </div>
                    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8" style="padding: 0px">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <h4>${orderFood.food.name}</h4>
                        </div>
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <button type="button" class="orderfood-review-write">리뷰 쓰기</button>
                            <div class="card card-position invisible">
                                <img class="card-img-top" src="" style="width:200px">
                                <button type="button" class="btn btn-danger btn-remove-image invisible">X</button>
                                <div class="card-body">
                                    <textarea rows="4" cols="50" name="contents" class="card-text"></textarea>
                                    <input type="file" name="image" class="btn btn-primary" accept="image/*">
                                    <div class="rate" data-rate-value="0"></div>
                                    <input type="button" class="btn-review-submit btn btn-danger" value="저장">
                                    <input type="button" class="btn-review-submit-cancel btn btn-danger" value="취소">
                                </div>
                            </div>
                            <p class="t-right" style="margin-top:10px">
                                <a href="#" style="font-weight: 600; color: aliceblue">
                                    <i class="fa fa-map-marker"> ${orderFood.food.restaurant.name}</i>
                                </a>
                                <button class="statistics" value="${orderFood.food.restaurant.id}"></button>
                            </p>
                        </div>
                    </div>
                </div>
            </li>`;
            return orderFoodHTML;
        },

        // 자신 옷장에서 리뷰 추가하고 난뒤 HTML
        getCreateReviewHTML(reviewDto) {
            let orderTime = reviewDto.orderedTime;
            let orderedDate = orderTime.slice(0, 10);
            let orderedTime = orderTime.slice(11, 16);
            let reviewId = reviewDto.review.id;
            let contents = reviewDto.review.contents.replace(/(?:\r\n|\r|\n)/g, '<br/>');
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
                   <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 card-contents" style="padding: 0px">
                       <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                       <h4>${reviewDto.foodName}</h4>
                       </div>
                       <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                           <div class="rate" data-rate-value="${reviewDto.review.starPoint}" style="pointer-events:none;"></div>
                           <p>${contents}</p>
                           <p class="t-right fs07em">${reviewDto.userName}</p>
                           <p class="t-right">
                               <a href="#" style="font-weight: 600; color: aliceblue">
                                   <i class="fa fa-map-marker"> ${reviewDto.restaurant.name}</i>
                               </a>
                               <button class="statistics" value="${reviewDto.restaurant.id}"></button>
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
                            <button type="button" class="btn btn-danger btn-remove-image invisible">X</button>
                            <img class="review_upload_image" src="" width="100%">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding: 0px; margin-top: 10px">
                            </div>
                        </div>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8" style="padding: 0px">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <h4>${orderFood.food.name}</h4>
                            </div>
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="review-create-btn" align="center">
                                    <button type="button" class="orderfood-review-write btn">리뷰 <img class="review-create-btn-image" src="/img/review_icon.png" > 쓰기</button>
                                </div>
                                <div class="card card-position invisible">
                                    <div class="card-body">
                                        <div class="rate" data-rate-value="0"></div>
                                        <textarea rows="4" cols="50" name="contents" class="card-text" ></textarea>
                                        <input type="file" name="image" class="btn btn-primary" accept="image/*">
                                        <div class="submit-cancel-btn">
                                            <input type="button" class="btn-review-submit btn btn-danger" value="저장">
                                            <input type="button" class="btn-review-submit-cancel btn btn-danger" value="취소">
                                        </div>
                                    </div>
                                </div>
                                <p class="t-right fs07em">${orderFood.orderedUser.name}</p>
                                <p class="t-right">
                                    <a href="#" style="font-weight: 600; color: aliceblue">
                                        <i class="fa fa-map-marker"> ${orderFood.food.restaurant.name}</i>
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
            let contents = orderFood.review.contents;
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
                                <h4>${orderFood.food.name}</h4>
                                </div>
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 card">
                                    <div class="rate" data-rate-value="${review.starPoint}"></div>
                                    <p><textarea rows="4" cols="50" name="contents" class="card-text">${contents}</textarea></p>
                                     <input type="file" name="image" class="btn-image-update-upload btn btn-primary" accept="image/*">
                                    <p class="t-right fs07em">${orderFood.orderedUser.name}</p>
                                    <p class="t-right">
                                        <a href="#" style="font-weight: 600; color: aliceblue">
                                            <i class="fa fa-map-marker"> ${orderFood.food.restaurant.name}</i>
                                        </a>
                                        <button class="statistics" value="${orderFood.food.restaurant.id}"></button>
                                    </p>
                                    <div>
                                        <div class="update-cancel-btn">
                                            <input type="button" class="btn-review-update btn btn-danger" value="수정">
                                            <input type="button" class="btn-review-update-cancel btn btn-danger" value="취소">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `
            ;
            return orderFoodHTML;
        },

        getChatMessageHTML(messageDto, username) {
            let hour = ("0" + messageDto.writtenTime.hour).slice(-2);
            let minute = ("0" + messageDto.writtenTime.minute).slice(-2);
            let second = ("0" + messageDto.writtenTime.second).slice(-2);
            let contents = messageDto.contents.replace(/(?:\r\n|\r|\n)/g, '<br/>');
            console.log(contents);
            if (messageDto.username !== username) {
                return `<li>
                    <div class="message-data">
                        <img src="${messageDto.profileImgURL}" width="41">
                        <span class="message-data-name"> ${messageDto.username}</span>
                        <span class="message-data-time">${hour}:${minute}:${second}</span>
                    </div>
                    <div class="message other-message">${contents}</div>
                </li>`;
            } else {
                return `<li class="clearfix">
                    <div class="message-data align-right">
                        <img src="${messageDto.profileImgURL}" width="41">
                        <span class="message-data-name" >${messageDto.username}</span>
                        <span class="message-data-time" >${hour}:${minute}:${second}</span> &nbsp; &nbsp;
                    </div>
                    <div class="message my-message float-right">${contents}</div>
                    </li>
                <li>`;
            }
        },

        getChatImageHTML(messageDto, username) {
            let hour = ("0" + messageDto.writtenTime.hour).slice(-2);
            let minute = ("0" + messageDto.writtenTime.minute).slice(-2);
            let second = ("0" + messageDto.writtenTime.second).slice(-2);
            if (messageDto.username !== username) {
                return `<li>
                    <div class="message-data">
                         <img src="${messageDto.profileImgURL}" width="41">
                        <span class="message-data-name">${messageDto.username}</span>
                        <span class="message-data-time">${hour}:${minute}:${second}</span>
                    </div>
                    <div class="message other-message"><img src="${messageDto.imageURL}" width="150" height="150"></div>
                </li>`;
            } else {
                return `<li class="clearfix">
                    <div class="message-data align-right">
                        <img src="${messageDto.profileImgURL}" width="41">
                        <span class="message-data-name" >${messageDto.username}</span>
                        <span class="message-data-time" >${hour}:${minute}:${second}</span> &nbsp; &nbsp;
                    </div>
                    <div class="message my-message float-right"><img src="${messageDto.imageURL}" width="150" height="150"></div>
                    </li>
                <li>`;
            }
        },

        getMyAlarmHTML(goodResponseDto) {
            return `<a class="dropdown-item alarm-item" href="#">
                <p style="margin-bottom: 2px">${goodResponseDto.userName} 님이 부러워요를 눌렀습니다.</p>
                <p style="margin-bottom: 0px">리뷰 : ${goodResponseDto.reviewTitle}</p>
            </a>`;
        }
    }
})();

