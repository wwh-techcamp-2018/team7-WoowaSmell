import { SocketManager } from '/js/chat/socketManager.js';

export class Chat{
    constructor() {
        this.socketManager = new SocketManager();
    }

    loadChat(roomName, callback) {
        this.socketManager.createSocket(roomName);
        this.socketManager.connect(this.onConnect.bind(this));
    }

    onConnect(frame) {
        const message = "";
        this.socketManager.sendMessage("/hello", { message: message });
        this.subscribes();
    }

    onEnterOtherUser(response) {

    }

    onReceiveMessage(response) {

    }

    onExitOtherUser(response) {

    }

    changeChatRoom(newRoomName) {
        this.socketManager.disconnect(function () {
            const message = "";
            this.socketManager.sendMessage("/hello", { message: message });
        }, function () {
            this.socketManager.createSocket(newRoomName);
            this.socketManager.connect(this.onConnect.bind(this));
        }.bind(this));
    }

    onClickTabButton({target}) {
        this.changeChatRoom(target.value);
    }

    onEnterKeyPressed() {
        const message = "";
        this.socketManager.sendMessage("/chat", { message: message });
    }

    subscribes() {
        this.socketManager.subscribe("/topic/hello", this.onEnterOtherUser.bind(this));
        this.socketManager.subscribe("/topic/message", this.onReceiveMessage.bind(this));
        this.socketManager.subscribe("/topic/bye", this.onExitOtherUser.bind(this));
    }
}