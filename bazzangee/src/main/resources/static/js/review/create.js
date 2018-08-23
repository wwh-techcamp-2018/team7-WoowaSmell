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
    document.querySelectorAll('orderfood-li').forEach( (element) => {
            element.classList.toggle('submitted-li', false);
            element.querySelector('.card').classList.toggle('add-food-image', false);
    });
    console.log(result);
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


document.addEventListener("DOMContentLoaded", function() {
    document.querySelector('.cbp_tmtimeline').addEventListener("click", (evt) => {
        if(evt.target.className === 'orderfood-review-write') {
            reviewWriteHandler(evt);
        }
    });
});
