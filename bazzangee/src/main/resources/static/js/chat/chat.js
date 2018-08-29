import { SocketManager } from '/js/util/socketManager.js';
import { CHAT_ROOM } from '/js/util/enum.js';
import { $, fetchManager } from '/js/util/utils.js';

const CHAT_ROOM_START_INDEX = Object.keys(CHAT_ROOM).length; // 전체 카테고리 인덱스

export class Chat{
    constructor({showModalFunc}) {
        this.showModalFunc = showModalFunc;
        this.me = null;
        this.chatRoomId = CHAT_ROOM_START_INDEX;
        this.socketManager = new SocketManager();
        this.clickedTarget = null;
        document.addEventListener("DOMContentLoaded", this.onLoadDocument.bind(this));
    }

    onLoadDocument() {
        this.addEventListeners();
    }

    addEventListeners() {
        $("#chat-message-send").addEventListener("keydown", this.onKeyDownChatTextArea.bind(this));
        $("#chat-send-image").addEventListener("click", this.onClickImageButton.bind(this));
        $("#chat-send-file").addEventListener("click", this.onClickFileButton.bind(this));
        $("#chat-send-btn").addEventListener("click", this.onClickSendingButton.bind(this));
        $("#timeline_standard").addEventListener("click", this.onclickGoodButton.bind(this));
    }

    onClickFileButton() {
        this.showPopup(true);
        setTimeout(function () {
            this.showPopup(false);
        }.bind(this), 1000);
        // if(this.me == null) {
        // }
        // file 전송 구현
    }

    onClickImageButton() {
        this.showPopup(true);
        setTimeout(function () {
            this.showPopup(false);
        }.bind(this), 1000);
        // if(this.me == null) {
        // }
        // file 전송 구현
    }

    onClickSendingButton() {
        if(this.me == null) {
            this.showModalFunc("loginModal");
            return;
        }
        this.socketManager.sendMessage("/chat", {}, { message: $("#chat-message-send").value, roomId: this.chatRoomId });
        $("#chat-message-send").value = null;
    }

    onclickGoodButton({target}) {
        if (!target.classList.contains("good-btn") && !target.parentElement.classList.contains("good-btn")
            && !target.parentElement.parentElement.classList.contains("good-btn"))
            return;

        this.clickedTarget = target;
        this.socketManager.sendMessage("/good", {}, this.clickedTarget.getAttribute("data-value"));
    }

    onKeyDownChatTextArea({keyCode, target}) {
        if(keyCode === 13) {
            this.socketManager.sendMessage("/chat", {}, { message: target.value, roomId: this.chatRoomId });
            $("#chat-message-send").value = null;
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
        $("#chat-message-container").insertAdjacentHTML(
            "beforeend",
            HtmlGenerator.getChatMessageHTML(message, (this.me != null ? this.me.name : ""))
        );
        $(".chat-history").scrollTop = $(".chat-history").scrollHeight;
    }

    changeChatRoom(newRoomId) {
        this.chatRoomId = (newRoomId == 0 ? CHAT_ROOM_START_INDEX : newRoomId);
        this.socketManager.disconnect(this.loadChat.bind(this));
    }

    initAlarmUI() {
        let alarms = localStorage.getItem("myAlarm");
        alarms = alarms ? JSON.parse(alarms) : [];

        $("#my-alarm-badge").innerText = alarms.length;
        $("#my-alarm-list").innerHTML = null;

        alarms.forEach((alarm) => $("#my-alarm-list").insertAdjacentHTML("afterbegin", HtmlGenerator.getMyAlarmHTML(alarm)));
    }

    updateUI() {
        const isLogin = (this.me != null);
        $("#chat-message-send").disabled = !isLogin;
        if(isLogin) {
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
        if(isVisible) $("#dialog").classList.add("visible");
        else $("#dialog").classList.remove("visible");
    }
}