export function $(selector) {
    return document.querySelector(selector);
}

export function fetchManager({ url, method, body, headers, callback, errCallback }) {
    fetch(url, {method,body,headers,credentials: "same-origin"})
        .then((response) => {
            if(response.status === 200 || response.status === 201 || response.status === 302) {
                callback(response);
                return false;
            }
            response.json().then(res => {
                errCallback(res, response.status);
            })
        })
}

export function throttle(callback, wait) {
    let time = Date.now();
    return function() {
        if ((time + wait - Date.now()) < 0) {
            callback();
            time = Date.now();
        }
    }
}