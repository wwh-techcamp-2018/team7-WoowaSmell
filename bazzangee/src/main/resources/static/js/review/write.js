function onSuccess(result) {
    console.log(result);
}

function onError(result) {
    console.log(result);
}

function checkValidForReview(form) {
    if (!form.contents.value.trim()) {
        alert("내용을 입력하세요");
        form.contents.focus();
        return false;
    }
    return true;
}

function getFormData(form) {
    let formData = new FormData();
    formData.append("contents", form.contents.value);
    console.log(form.image.files[0]);
//    if(form.image.files[0]) == undefined)
//        formData.append("image", null);
//    else
        formData.append("image", form.image.files[0]);
    formData.append("starPoint", form.querySelector('.rate').getAttribute('data-rate-value'));
    return formData;
}

function reviewUpdateButtonHandler(evt) {
    evt.preventDefault();

    let form = evt.target.closest("form");
    if(!checkValidForReview(form)) return;

    let formData = getFormData(form);

    fetchManager({
        url: '/api/reviews/1',
        method: 'POST',
        body: formData,
        callback: onSuccess,
        errCallback: onError
    })
}

function reviewCreateButtonHandler(evt) {
    evt.preventDefault();

    let form = evt.target.closest("form");
    if(!checkValidForReview(form)) return;

    let formData = getFormData(form);

    fetchManager({
        url: '/api/reviews',
        method: 'POST',
        body: formData,
        callback: onSuccess,
        errCallback: onError
    })
}

function initializeEventListener() {
    if($(".review-create-btn"))
        $(".review-create-btn").addEventListener("click", reviewCreateButtonHandler);
    if($(".review-update-btn"))
        $(".review-update-btn").addEventListener("click", reviewUpdateButtonHandler);
}

document.addEventListener("DOMContentLoaded", () => {
    initializeEventListener();
})

import {$, fetchManager} from "/js/utils.js";