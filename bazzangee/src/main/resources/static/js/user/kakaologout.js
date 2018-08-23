import {fetchManager} from "/js/util/utils.js";

export function logoutListener() {
const KAKAO_LOGIN_KEY = '71d9a63c6cb420517c5f389449b74bd2';

Kakao.init(KAKAO_LOGIN_KEY)

    const token = Kakao.Auth.getAccessToken();
    console.log("token : ", token);

    if(token != null) {
        Kakao.Auth.logout((res) => {
            console.log("logout response : ", res);
            Kakao.cleanup();
        });
    }
}

