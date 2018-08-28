import { $ } from "/js/util/utils.js";

export class SocketManager {
    constructor() {
        this.socket = null;
        this.stompClient = null;
    }

    createSocket(socketName) {
        if (this.socket != null) this.disconnect();
        this.socket = new SockJS(socketName);
    }

    connect(callback) {
        this.stompClient = Stomp.over(this.socket);
        this.stompClient.connect({}, callback);
    }

    subscribe(url, callback) {
        this.stompClient.subscribe(url, callback);
    }

    sendMessage(url, contentType, dataObject) {
        if(this.stompClient == null) return;
        this.stompClient.send(url, contentType, JSON.stringify(dataObject));
    }

    disconnect(preFunction, callback) {
        if(preFunction != null) preFunction();
        if (this.stompClient != null) {
            this.stompClient.disconnect();
            this.socket = null;
        }
        if(callback != null) callback();
    }
}