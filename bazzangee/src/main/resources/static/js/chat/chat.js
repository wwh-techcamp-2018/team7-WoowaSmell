import {SocketManager} from '/js/util/socketManager.js';
import {CHAT_ROOM} from '/js/util/enum.js';
import {$, fetchManager} from '/js/util/utils.js';

const CHAT_ROOM_START_INDEX = Object.keys(CHAT_ROOM).length; // 전체 카테고리 인덱스

export class Chat {
    constructor() {
        this.me = null;
        this.chatRoomId = CHAT_ROOM_START_INDEX;
        this.socketManager = new SocketManager();
        this.clickedTarget = null;
        document.addEventListener("DOMContentLoaded", this.onLoadDocument.bind(this));
    }

    onLoadDocument() {
        $("#chat-message-send").addEventListener("keyup", this.onKeyUpChatTextArea.bind(this));
        $("#chat-send-image").addEventListener("change", this.changeImage.bind(this));
        this.addEventListeners();
    }

    addEventListeners() {
        $("#chat-message-send").addEventListener("keyup", this.onKeyUpChatTextArea.bind(this));
        $("#chat-send-image").addEventListener("click", this.changeImage.bind(this));
        $("#chat-send-btn").addEventListener("click", this.onClickSendingButton.bind(this));
        $("#timeline_standard").addEventListener("click", this.onclickGoodButton.bind(this));
    }

    onClickFileButton() {
        this.showPopup(true);
        setTimeout(function () {
            this.showPopup(false);
        }.bind(this), 1000);
    }

    changeImage(evt) {
        this.imageUploadHandler(evt);
    }

    // 이미지 업로드 Ajax
    imageUploadHandler(evt) {
        var formData = new FormData();
        formData.append('data', evt.target.files[0]);

        fetchManager({
            url: '/api/reviews/upload',
            method: 'POST',
            body: formData,
            callback: this.onSuccessImageUpload.bind(this)
        });
    }


    onSuccessImageUpload(result) {
        result.json().then(result => {
            this.socketManager.sendMessage("/img", {}, {imageURL: result.url, roomId: this.chatRoomId});
        })
    }

    onClickSendingButton() {
        if (this.me == null) {
            location.href = "/login";
            return;
        }

        // 채팅 창에 입력된 내용이 없이 엔터칠 경우
        if($("#chat-message-send").value.trim().length === 0) {
            return false;
        }
        this.socketManager.sendMessage("/chat", {}, {message: $("#chat-message-send").value, roomId: this.chatRoomId});
        $("#chat-message-send").value = null;
    }

    onclickGoodButton({target}) {
        if (!target.classList.contains("good-btn") && !target.parentElement.classList.contains("good-btn")
            && !target.parentElement.parentElement.classList.contains("good-btn"))
            return;

        this.clickedTarget = target;
        this.socketManager.sendMessage("/good", {}, this.clickedTarget.getAttribute("data-value"));
    }

    onConnect(frame) {
        this.subscribes();
        $("#chat-message-container").innerHTML = null;
        $("#chat-name").innerText = CHAT_ROOM[this.chatRoomId].name + " 채팅";
        this.socketManager.sendMessage("/info", {}, {roomId: this.chatRoomId});
    }


    onKeyUpChatTextArea(evt) {
        if (evt.keyCode === 13) {
            if (!evt.shiftKey) {
                // 채팅 창에 입력된 내용이 없이 엔터칠 경우
                if ($("#chat-message-send").value.trim().length === 0) {
                    return false;
                }
                this.socketManager.sendMessage("/chat", {}, {message: evt.target.value, roomId: this.chatRoomId});
                $("#chat-message-send").value = null;
            }
        }
    }

    onCompleteGoodEvent(response) {
        const result = JSON.parse(response.body);
        if(result.message === "LIKE" || result.message === "UNLIKE") {
            this.clickedTarget.closest(".good-btn").lastElementChild.innerHTML = result.goodCount;
        } else {
            alert(result.message);
        }
    }

    onReceiveMessage(response) {
        this.addNewMessage(JSON.parse(response.body));
    }

    onReceiveAlarm(response) {
        const result = JSON.parse(response.body);
        this.updateAlarmUI(result);
        this.saveAlarmInfo(result);
    }

    onReceiveInitialInfo(response) {
        const result = JSON.parse(response.body);
        this.me = result.loginUser;
        if(this.me != null) {
            this.initAlarmUI();
            this.subscribeMyAlarm();
        }
        result.chatMessageResponseDtos.forEach(this.addNewMessage.bind(this));
        this.updateUI();
    }

    loadChat() {
        this.socketManager.createSocket(CHAT_ROOM[this.chatRoomId].url);
        this.socketManager.connect(this.onConnect.bind(this));
    }

    onConnect(frame) {
        this.subscribes();
        $("#chat-message-container").innerHTML = null;
        $("#chat-name").innerText = CHAT_ROOM[this.chatRoomId].name + " 채팅";
        this.socketManager.sendMessage("/info", {}, { roomId: this.chatRoomId });
    }

    subscribes() {
        this.socketManager.subscribe("/topic/message", this.onReceiveMessage.bind(this));
        this.socketManager.subscribe("/user/queue/info", this.onReceiveInitialInfo.bind(this));
        this.socketManager.subscribe("/user/queue/good", this.onCompleteGoodEvent.bind(this));
    }

    subscribeMyAlarm() {
        this.socketManager.subscribe("/queue/" + this.me.id + "/good", this.onReceiveAlarm.bind(this));
    }

    addNewMessage(message) {
        if(message.imageURL == null) {
            $("#chat-message-container").insertAdjacentHTML(
                "beforeend",
                HtmlGenerator.getChatMessageHTML(message, (this.me != null ? this.me.name : ""))
            );
        } else {
            $("#chat-message-container").insertAdjacentHTML(
                "beforeend",
                HtmlGenerator.getChatImageHTML(message,  (this.me != null ? this.me.name : ""))
            );
        }
        $(".chat-history").scrollTop = $(".chat-history").scrollHeight;
    }

    changeChatRoom(newRoomId) {
        this.chatRoomId = (newRoomId == 0 ? CHAT_ROOM_START_INDEX : newRoomId);
        this.socketManager.disconnect(this.loadChat.bind(this));
    }

    sendBye() {
        const message = "";
        this.socketManager.sendMessage("/bye", {}, {message: message});
    }

    initAlarmUI() {
        let alarms = localStorage.getItem("myAlarm");
        alarms = alarms ? JSON.parse(alarms) : [];

        console.log(alarms);
        $("#my-alarm-badge").innerText = alarms.length;
        $("#my-alarm-list").innerHTML = null;

        alarms.forEach((alarm) => $("#my-alarm-list").insertAdjacentHTML("afterbegin", HtmlGenerator.getMyAlarmHTML(alarm)));
    }

    updateUI() {
        const isLogin = (this.me != null);
        $("#chat-message-send").disabled = !isLogin;
        if (isLogin) {
            $("#chat-avatar").setAttribute("src", this.me.imageUrl || "/img/avatar.png");
            $("#chat-message-send").classList.remove("disabled-ui");
        } else {
            $("#chat-avatar").setAttribute("src", "/img/avatar.png");
            $("#chat-message-send").classList.add("disabled-ui");
        }
        $("#chat-send-btn").innerText = (isLogin ? "보내기" : "로그인하기");
    }

    updateAlarmUI(goodResponseDto) {
        $("#my-alarm-badge").innerText = Number($("#my-alarm-badge").innerText) + 1;
        $("#my-alarm-list").insertAdjacentHTML("afterbegin", HtmlGenerator.getMyAlarmHTML(goodResponseDto));
    }

    saveAlarmInfo(info) {
        let alarms = localStorage.getItem("myAlarm");
        alarms = alarms ? JSON.parse(alarms) : [];
        alarms.push(info);
        localStorage.setItem("myAlarm", JSON.stringify(alarms));
    }

    showPopup(isVisible) {
        if (isVisible) $("#dialog").classList.add("visible");
        else $("#dialog").classList.remove("visible");
    }

}

class ChatControll {

}