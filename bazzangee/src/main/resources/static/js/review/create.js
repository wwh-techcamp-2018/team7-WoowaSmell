import {fetchManager} from "/js/util/utils.js";

function $_(selector) {
    return document.querySelector(selector);
}

function onSuccessImageUpload(result) {
    result.json().then(result => {
        const card = $_('.add-food-image');
        card.querySelector('.card-img-top').src = result.url;
        card.querySelector('.btn-danger').classList.toggle('invisible', false);
        card.classList.toggle('add-food-image', false);
    })
}

function onFailLoad(result) {
    alert(result.message);
    document.querySelectorAll('.orderfood-li').forEach( (element) => {
        element.classList.toggle('submitted-li', false);
        if(element.querySelector('.card') != null) {
            element.querySelector('.card').classList.toggle('add-food-image', false);
            element.querySelector(".btn-primary").value = "";
        }

    });
}

function imageUploadHandler(evt) {
    const card = evt.target.closest(".card");
    card.classList.toggle('add-food-image', true);
    var formData = new FormData();
    formData.append('data', card.querySelector(".btn-primary").files[0]);
    fetchManager({
        url: '/api/reviews/upload',
        method: 'POST',
        body: formData,
        callback: onSuccessImageUpload,
        errCallback: onFailLoad
    });
}

function imageDeleteHandler(evt) {
    const card = evt.target.closest(".card");
    card.querySelector('.card-img-top').src = '';
    card.querySelector('.btn-danger').classList.toggle('invisible', true);
    card.querySelector(".btn-primary").value = '';
}

function onSuccessWrite(response) {
    response.json().then(reviewDto => {
            var reviewDtoHTML = HtmlGenerator.getCreateReviewHTML(reviewDto);
            let reviewBox = $_(".submitted-li");
            reviewBox.innerHTML = '';
            reviewBox.innerHTML = reviewDtoHTML;
            reviewBox.classList.toggle('submitted-li', false);
            $(".rate").rate();
        })
}

function reviewSubmitHandler(evt) {
    const orderFood = evt.target.closest('li');
    orderFood.classList.toggle('submitted-li', true);
    const card = evt.target.closest(".card");
    var formData = new FormData();
    formData.append('orderFoodId', orderFood.getAttribute('data-id'));
    formData.append('contents', card.querySelector('.card-text').value);
    formData.append("starPoint", card.querySelector('.rate').getAttribute('data-rate-value'));
    if(card.querySelector(".btn-primary").files.length != 0) {
        formData.append('image', card.querySelector(".btn-primary").files[0]);
    }

    fetchManager({
        url: '/api/reviews',
        method: 'POST',
        body: formData,
        callback: onSuccessWrite,
        errCallback: onFailLoad
    });
}

function reviewWriteHandler(evt) {
    document.querySelectorAll('.card').forEach( (element) => {
        element.classList.toggle('invisible', true);
        element.parentElement.querySelector(".orderfood-review-write").classList.toggle('invisible', false);
    });

    const card = evt.target.closest('li').querySelector('.card');
    card.classList.toggle('invisible', false);
    card.classList.toggle('card-selected', true);
    evt.target.classList.toggle('invisible', true);
    $(".rate").rate();

    card.querySelector('.btn-primary').addEventListener("change", imageUploadHandler);
    card.querySelector('.btn-danger').addEventListener("click", imageDeleteHandler);
    card.querySelector('.btn-review-submit').addEventListener("click", reviewSubmitHandler);
}

function onSuccessUpdateForm(result) {
    result.json().then(result => {
        document.querySelectorAll("li").forEach(function (li) {
            if(li.getAttribute("data-id") == result.id) {
                li.innerHTML = "";
                li.innerHTML = HtmlGenerator.getReviewUpdateFormHTML(result);
                $('.rate').rate();
            }
        })
    });
}

function onFailUpdateForm(result) {
    alert(result.message);
}

function reviewUpdateFormHandler(evt) {
    const orderFoodId = evt.target.closest("li").getAttribute("data-id");
    fetchManager({
        url: '/api/reviews/' + orderFoodId,
        method: 'GET',
        callback: onSuccessUpdateForm,
        errCallback: onFailUpdateForm
    });
}

function onSuccessDelete(result) {
    result.json().then(result => {
        document.querySelectorAll("li").forEach(function (li) {
            if(li.getAttribute("data-id") == result.id) {
                li.innerHTML = "";
                li.innerHTML = HtmlGenerator.getDeleteReviewHTML(result);
            }
        })
    });
}

function onFailDelete(result) {
    alert(result.message);
}

function reviewDeleteHandler(evt) {
    const orderFoodId = evt.target.closest("li").getAttribute("data-id");
    fetchManager({
        url: '/api/reviews/' + orderFoodId,
        method: 'DELETE',
        callback: onSuccessDelete,
        errCallback: onFailDelete
    });
}

function onSuccessUpdateCancel(result) {
    result.json().then(result => {
        document.querySelectorAll("li").forEach(function (li) {
            if(li.getAttribute("data-id") == result.id) {
                li.innerHTML = "";
                li.innerHTML = HtmlGenerator.getOrderFoodHTML(result);
                $('.rate').rate();
            }
        })
    });
}
function onFailUpdateCancel(result) {
    alert(result.message);
}

function reviewUpdateCancelHandler(evt) {
    const orderFoodId = evt.target.closest("li").getAttribute("data-id");
    fetchManager({
        url: '/api/reviews/' + orderFoodId,
        method: 'GET',
        callback: onSuccessUpdateCancel,
        errCallback: onFailUpdateCancel
    });
}

function onSuccessImageUpdateUpload(result) {
    const card = $_('.add-update-image');
    result.json().then(result => {
        card.querySelector("#update-image-area").src = result.url;
        card.querySelector('.exist-image-url').value = result.url;
    });
    card.classList.toggle('add-update-image', false);
}


function imageUpdateUploadHandler(evt) {
    const card = evt.target.closest("li");
    var formData = new FormData();
    card.classList.toggle('add-update-image', true);
    formData.append('data', card.querySelector(".btn-image-update-upload").files[0]);
    fetchManager({
        url: '/api/reviews/upload',
        method: 'POST',
        body: formData,
        callback: onSuccessImageUpdateUpload,
        errCallback: onFailLoad
    });
}

function onSuccessUpdate(result) {
    result.json().then(result => {
        document.querySelectorAll("li").forEach(function (li) {
            if(li.getAttribute("data-id") == result.id) {
                li.innerHTML = "";
                li.innerHTML = HtmlGenerator.getOrderFoodHTML(result);
                $('.rate').rate();
            }
        })
    });
}

function onFailUpdate(result) {
    alert(result.message);
}

function reviewUpdateHandler(evt) {
    const target = evt.target.closest("li");
    const orderFoodId = target.getAttribute("data-id");

    fetchManager({
        url: '/api/reviews/update',
        method: 'POST',
        headers: { 'content-type': 'application/json'},
        body: JSON.stringify({
            "orderFoodId": orderFoodId,
            "contents": target.querySelector('.card-text').value,
            "starPoint": target.querySelector('.rate').getAttribute('data-rate-value'),
            "savedImageUrl": target.querySelector(".exist-image-url").value
        }),
        callback: onSuccessUpdate,
        errCallback: onFailUpdate
    });

}

function onSuccessUpdateGood(result) {
    const target = document.querySelector('.add-review-good');
    target.classList.toggle('add-review-good', false);
    result.json().then(result => {
        target.closest(".good-btn").lastElementChild.innerHTML = result.goodCount;
    })
}

function onFailUpdateGood(result) {
    alert(result.message);
}

function reviewGoodHandler(evt) {
    evt.target.classList.toggle('add-review-good', true);
    fetchManager({
        url: '/api/reviews/' + evt.target.getAttribute("data-value") + '/good',
        method: 'GET',
        headers: { 'content-type': 'application/json'},
        callback: onSuccessUpdateGood,
        errCallback: onFailUpdateGood
    });
}

function reviewWriteCancelHandler(evt) {
    const card = evt.target.closest('li').querySelector('.card');
    card.classList.toggle('invisible', true);
    card.parentElement.querySelector(".orderfood-review-write").classList.toggle("invisible", false);
}

document.addEventListener("DOMContentLoaded", function() {
    // const reviewScroll = new ReviewScroll();
    document.querySelector('.cbp_tmtimeline').addEventListener("click", (evt) => {
        if(evt.target.className === 'orderfood-review-write') {
            reviewWriteHandler(evt);
        }

        if(evt.target.classList.contains('btn-review-submit-cancel')) {
            reviewWriteCancelHandler(evt);
        }
        // 수정폼 버튼
        if(evt.target.classList.contains('btn-review-update-form')) {
            reviewUpdateFormHandler(evt);
        }
        //수정 취소 버튼
        if(evt.target.classList.contains('btn-review-update-cancel')) {
            reviewUpdateCancelHandler(evt);
        }
        // 수정 버튼
        if(evt.target.classList.contains('btn-review-update')) {
            reviewUpdateHandler(evt);
        }
        // 삭제 버튼
        if(evt.target.classList.contains('btn-review-delete')) {
            reviewDeleteHandler(evt);
        }
        // 업데이트폼에서 이미지 첨부 버튼
        if(evt.target.classList.contains('btn-image-update-upload')) {
            evt.target.closest('li').querySelector('.btn-image-update-upload').addEventListener("change", imageUpdateUploadHandler);
        }

        if(evt.target.classList.contains('good-btn') || evt.target.parentElement.classList.contains("good-btn")) {
            reviewGoodHandler(evt);
        }

    });
});





