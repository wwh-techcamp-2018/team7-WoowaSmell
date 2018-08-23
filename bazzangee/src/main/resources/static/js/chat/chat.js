import { SocketManager } from '/js/util/socketManager.js';
import { CHAT_ROOM } from '/js/util/enum.js';
import { $, fetchManager } from '/js/util/utils.js';

const CHAT_ROOM_START_INDEX = 1;

export class Chat{
    constructor() {
        this.chatRoomid = CHAT_ROOM_START_INDEX;
        this.socketManager = new SocketManager();
        document.addEventListener("DOMContentLoaded", this.onLoadDocument.bind(this));
        this.loadChat();
    }

    onLoadDocument() {
        $("#chat-message-send").addEventListener("keydown", this.onKeyDownChatTextArea.bind(this));
    }

    onClickTabButton({target}) {
        this.changeChatRoom(target.value);
    }

    loadChat() {
        this.socketManager.createSocket(CHAT_ROOM[this.chatRoomid]);
        this.socketManager.connect(this.onConnect.bind(this));
    }

    loadMessageList() {
        fetchManager({
            url: '/api/chats/' + this.chatRoomid,
            method: 'GET',
            headers: { 'content-type': 'application/json'},
            callback: this.onLoadMessageList.bind(this),
            errCallback: this.onFailMessageList.bind(this)
        });
    }

    onLoadMessageList(response) {
        response.json().then((messages) => {
            messages.forEach(this.addNewMessage);
        });
    }

    onFailMessageList() {

    }

    onConnect(frame) {
        console.log(frame);
        this.subscribes();
        this.loadMessageList(CHAT_ROOM[this.chatRoomid]);
    }

    onKeyDownChatTextArea({keyCode, target}) {
        if(keyCode === 13) {
            this.socketManager.sendMessage("/chat", {}, { message: target.value });
            target.value = null;
        }
    }

    onReceiveMessage(response) {
        this.addNewMessage(JSON.parse(response.body));
    }

    onExitOtherUser(response) {
        console.log('onExitOtherUser', JSON.parse(response.body));
    }

    addNewMessage(message) {
        $("#chat-message-container").insertAdjacentHTML("beforeend", HtmlGenerator.getChatMessageHTML(message));
        $(".chat-history").scrollTop = $(".chat-history").scrollHeight;
    }

    changeChatRoom(newRoomId) {
        this.chatRoomId = newRoomId;
        this.socketManager.disconnect(this.sendBye.bind(this), this.loadChat.bind(this));
    }

    sendBye() {
        const message = "";
        this.socketManager.sendMessage("/bye", {}, { message: message });
    }

    subscribes() {
        this.socketManager.subscribe("/topic/message", this.onReceiveMessage.bind(this));
        this.socketManager.subscribe("/topic/bye", this.onExitOtherUser.bind(this));
    }
}