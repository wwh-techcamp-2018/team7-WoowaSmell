function onSuccess(result) {
    result.json().then(result => {
        const card = $('.add-food-image');
        card.querySelector('.card-img-top').src = result.url;
        card.querySelector('.btn-danger').classList.toggle('invisible', false);
        card.classList.toggle('add-food-image', false);
    })
}

function onFailLoad(result) {
    card.classList.toggle('add-food-image', false);
    console.log(result);
}

function imageUploadHandler(evt) {
    const card = evt.target.closest(".card");
    var formData = new FormData();
    formData.append('data', card.querySelector(".btn-primary").files[0]);
    card.classList.toggle('add-food-image', true);
    fetchManager({
        url: '/api/reviews/upload',
        method: 'POST',
        body: formData,
        callback: onSuccess,
        errCallback: onFailLoad
    });
}

function imageDeleteHandler(evt) {
    const card = evt.target.closest(".card");
    card.querySelector('.card-img-top').src = '';
    card.querySelector('.btn-danger').classList.toggle('invisible', true);
    card.querySelector(".btn-primary").value = '';
}

function onSuccessWrite() {
    location.href = "/orderfoods";
}

function imageSubmitHandler(evt) {
    const oderFood = evt.target.closest('li');
    const card = evt.target.closest(".card");

    var formData = new FormData();
    formData.append('orderFoodId', oderFood.getAttribute('data-id'));
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
    const oderFood = evt.target.closest('li');
    const oderFoodId = oderFood.getAttribute('data-id');
    const card = oderFood.querySelector('.card');
    card.classList.toggle('invisible', false);
    evt.target.classList.toggle('invisible', true);

    card.querySelector('.btn-primary').addEventListener("change", imageUploadHandler);
    card.querySelector('.btn-danger').addEventListener("click", imageDeleteHandler);
    card.querySelector('.btn-review-submit').addEventListener("click", imageSubmitHandler);
}


document.addEventListener("DOMContentLoaded", function() {
    document.querySelector('.cbp_tmtimeline').addEventListener("click", (evt) => {
        if(evt.target.className === 'orderfood-review-write') {
            reviewWriteHandler(evt);
        }
    });
});

import {$, fetchManager} from "/js/utils.js";