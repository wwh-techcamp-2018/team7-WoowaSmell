import { SocketManager } from '/js/util/socketManager.js';
import { CHAT_ROOM } from '/js/util/enum.js';
import { $, fetchManager } from '/js/util/utils.js';

const CHAT_ROOM_START_INDEX = 9;

export class Chat{
    constructor() {
        this.me = null;
        this.chatRoomId = CHAT_ROOM_START_INDEX;
        this.socketManager = new SocketManager();
        document.addEventListener("DOMContentLoaded", this.onLoadDocument.bind(this));
    }

    onLoadDocument() {
        $("#chat-message-send").addEventListener("keydown", this.onKeyDownChatTextArea.bind(this));
        $("#chat-send-image").addEventListener("click", this.onClickImageButton.bind(this));
        $("#chat-send-file").addEventListener("click", this.onClickFileButton.bind(this));
        $("#chat-send-btn").addEventListener("click", this.onClickSendingButton.bind(this));
    }

    onClickTabButton({target}) {
        this.changeChatRoom(target.value);
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

    onClickSendingButton({target}) {
        if(this.me == null) {
            location.href = "/login";
            return;
        }
        this.socketManager.sendMessage("/chat", {}, { message: target.value, roomId: this.chatRoomId });
        target.value = null;
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

    onKeyDownChatTextArea({keyCode, target}) {
        if(keyCode === 13) {
            this.socketManager.sendMessage("/chat", {}, { message: target.value, roomId: this.chatRoomId });
            target.value = null;
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
    }

    onExitOtherUser(response) {
        console.log('onExitOtherUser', JSON.parse(response.body));
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
        this.socketManager.disconnect(this.sendBye.bind(this), this.loadChat.bind(this));
    }

    sendBye() {
        const message = "";
        this.socketManager.sendMessage("/bye", {}, { message: message });
    }

    subscribes() {
        this.socketManager.subscribe("/topic/message", this.onReceiveMessage.bind(this));
        this.socketManager.subscribe("/topic/bye", this.onExitOtherUser.bind(this));
        this.socketManager.subscribe("/user/queue/info", this.onReceiveInitialInfo.bind(this));
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

    showPopup(isVisible) {
        if(isVisible) $("#dialog").classList.add("visible");
        else $("#dialog").classList.remove("visible");
    }
}

class ChatControll{

}