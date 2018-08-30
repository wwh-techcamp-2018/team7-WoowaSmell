import {SocketManager} from '/js/websocket/socketManager.js';
import {$} from '/js/util/utils.js';
import {Alarm} from "/js/websocket/alarm.js";

const CHAT_ROOM_NAME = "/smell-closet";

export class ClosetWebSocket {
    constructor() {
        this.me = null;
        this.socketManager = new SocketManager();
        this.alarm = null;
        this.clickedTarget = null;
        document.addEventListener("DOMContentLoaded", this.onLoadDocument.bind(this));
    }

    onLoadDocument() {
        this.loadChat();
    }

    onclickGoodButton({target}) {
        if (!target.classList.contains("good-btn") && !target.parentElement.classList.contains("good-btn")
            && !target.parentElement.parentElement.classList.contains("good-btn"))
            return;
        this.alarm.onclickGoodButton(target);
    }

    loadChat() {
        this.socketManager.createSocket(CHAT_ROOM_NAME);
        this.socketManager.connect(this.onConnect.bind(this));
    }

    onConnect() {
        this.subscribes();
        this.socketManager.sendMessage("/me", {}, {});
    }

    subscribes() {
        this.socketManager.subscribe("/user/queue/me", this.onReceiveMyInfo.bind(this));
    }

    onReceiveMyInfo(response) {
        this.me = JSON.parse(response.body);
        this.alarm = new Alarm({
            me: this.me,
            socketManager: this.socketManager
        });
    }
}