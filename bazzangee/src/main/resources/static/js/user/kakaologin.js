const KAKAO_LOGIN_KEY = '71d9a63c6cb420517c5f389449b74bd2';

Kakao.init(KAKAO_LOGIN_KEY);
Kakao.Auth.createLoginButton({
  container: '#kakao-login-btn',
  success: function(authObj) {
    Kakao.API.request({
      url: '/v1/user/me',
      success: function(res) {
        kakaoLogin(res);
      },
      fail: function(error) {
        alert(JSON.stringify(error));
      }
    });
  },
  fail: function(err) {
    alert(JSON.stringify(err));
  }
});

function kakaoLogin({id, properties}) {
    console.log(properties);
    fetchManager({
        url: '/api/users/login/kakao',
        method: 'POST',
        body: JSON.stringify({
            userId : id,
            name : properties.nickname,
            imageUrl : properties.thumbnail_image
        }),
        headers: { 'content-type': 'application/json'},
        callback: onSuccess,
        errCallback: onFailure
    });
}

function onSuccess(res) {
        console.log(res);
//    location.href = "/";
}

function onFailure(res) {
    alert("KakaoTalk error!!");
}

import {$, fetchManager} from "/js/utils.js";

