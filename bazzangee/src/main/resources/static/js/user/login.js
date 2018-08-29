function goHome() {
    location.href = "/";
}

function onSuccess() {
    goHome();
}

function onError(result) {
    $("#login-validate-span").innerText = result.message;
}

function checkValidForLogin(form) {
    if (!form.userId.value.trim()) {
        $("#login-validate-span").innerText = "아이디를 입력하세요";
        form.userId.focus();
        return false;
    }

    if (!form.password.value.trim()) {
        $("#login-validate-span").innerText = "비밀번호를 입력하세요";
        form.password.focus();
        return false;
    }
    $("#login-validate-span").innerText = null;
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
});

import {$, fetchManager} from "/js/util/utils.js";