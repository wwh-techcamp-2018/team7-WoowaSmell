import {SocketManager} from '/js/websocket/socketManager.js';
import {CHAT_ROOM} from '/js/util/enum.js';
import {$, fetchManager} from '/js/util/utils.js';
import {Alarm} from "/js/websocket/alarm.js";

const CHAT_ROOM_START_INDEX = Object.keys(CHAT_ROOM).length; // 전체 카테고리 인덱스

export class Chat{
    constructor({showModalFunc}) {
        this.showModalFunc = showModalFunc;
        this.me = null;
        this.chatRoomId = CHAT_ROOM_START_INDEX;
        this.socketManager = new SocketManager();
        this.clickedTarget = null;
        this.alarm = null;
        document.addEventListener("DOMContentLoaded", this.onLoadDocument.bind(this));
    }

    onLoadDocument() {
        $("#chat-message-send").addEventListener("keyup", this.onKeyUpChatTextArea.bind(this));
        $("#chat-send-image").addEventListener("change", this.changeImage.bind(this));
        this.addEventListeners();
    }

    addEventListeners() {
        $("#chat-send-btn").addEventListener("click", this.onClickSendingButton.bind(this));
        $("#timeline_standard").addEventListener("click", this.onclickGoodButton.bind(this));
    }

    changeImage(evt) {
        if(evt.target.files[0] == undefined) {
            return false;
        }
        this.imageUploadHandler(evt);
    }

    // 이미지 업로드 Ajax
    imageUploadHandler(evt) {
        let formData = new FormData();
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
        if(this.me == null) {
            this.showModalFunc("loginModal");
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
        this.alarm.onclickGoodButton(target);
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

    onReceiveMessage(response) {
        this.addNewMessage(JSON.parse(response.body));
    }


    onReceiveInitialInfo(response) {
        const result = JSON.parse(response.body);
        this.me = result.loginUser;
        result.chatMessageResponseDtos.forEach(this.addNewMessage.bind(this));
        this.updateUI();
        this.alarm = new Alarm({
            me: this.me,
            socketManager: this.socketManager
        });
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
}