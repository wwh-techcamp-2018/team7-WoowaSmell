function goHome() {
    location.href = "/";
}

function onSuccess() {
    alert("어서와~ 배민찬은 처음이지?");
    goHome();
}

function onError(result) {
    let message = result.message;
    // if (result.hasOwnProperty("errors")) {
    //     message = result.errors[0].errorMessage;
    // }
    alert(message);
}

function checkValidForLogin(form) {
    if (!form.userId.value.trim()) {
        alert("아이디를 입력하세요");
        form.userId.focus();
        return false;
    }

    if (!form.password.value.trim()) {
        alert("비밀번호를 입력하세요");
        form.password.focus();
        return false;
    }
    return true;
}

function loginButtonHandler(evt) {
    evt.preventDefault();

    let form = evt.target.closest("form");
    if(!checkValidForLogin(form)) return;

    fetchManager({
        url: '/api/users/login',
        method: 'POST',
        headers: { 'content-type': 'application/json'},
        body: JSON.stringify({
            "userId": form.userId.value,
            "password": form.password.value
        }),
        callback: onSuccess,
        errCallback: onError
    })
}

function initializeEventListener() {
    $("#btnLogin").addEventListener("click", loginButtonHandler);
}

document.addEventListener("DOMContentLoaded", () => {
    initializeEventListener();
})

import {$, fetchManager} from "/js/utils.js";