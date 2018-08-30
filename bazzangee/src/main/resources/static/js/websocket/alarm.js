import {$} from '/js/util/utils.js';

const UNLIKE = "UNLIKE";
const LIKE   = "LIKE";

export class Alarm{
    constructor({me, socketManager}) {
        this.me = me;
        this.socketManager = socketManager;
        this.clickedTarget = null;
        this.subscribeGoodTopic();
        this.addEventListener();

        if(this.me != null) {
            this.initAlarmUI();
            this.subscribeMyAlarm();
        }
    }

    addEventListener() {
        $("#my-alarm").addEventListener("click", this.onClickAlarmButton.bind(this));
    }

    onClickAlarmButton() {
        let alarms = localStorage.getItem("myAlarm");
        alarms = alarms ? JSON.parse(alarms) : [];
        alarms.forEach((alarm) => {
            alarm["isShow"] = true;
        });
        $("#my-alarm-badge").innerText = 0;
        localStorage.setItem("myAlarm", JSON.stringify(alarms));
    }

    onclickGoodButton(target) {
        this.clickedTarget = target;
        this.socketManager.sendMessage("/good", {}, this.clickedTarget.getAttribute("data-value"));
    }

    subscribeGoodTopic() {
        this.socketManager.subscribe("/user/queue/good", this.onCompleteGoodEvent.bind(this));
    }

    subscribeMyAlarm() {
        this.initAlarmUI();
        this.socketManager.subscribe("/queue/" + this.me.id + "/good", this.onReceiveAlarm.bind(this));
    }

    onCompleteGoodEvent(response) {
        const result = JSON.parse(response.body);
        if(result.message === LIKE || result.message === UNLIKE) {
            this.clickedTarget.closest(".good-btn").lastElementChild.innerHTML = result.goodCount;
        } else {
            alert(result.message);
        }
    }

    onReceiveAlarm(response) {
        const result = JSON.parse(response.body);
        this.updateAlarmUI(result);
        this.saveAlarmInfo(result);
    }

    initAlarmUI() {
        let alarms = localStorage.getItem("myAlarm");
        alarms = alarms ? JSON.parse(alarms) : [];

        if(alarms.length > 0) $("#no-alarm-p").classList.add("invisible");

        let notShowCount = 0;
        $("#my-alarm-list").innerHTML = null;
        alarms.forEach((alarm) => {
            if(!Boolean(alarm.isShow)) notShowCount++;
            $("#my-alarm-list").insertAdjacentHTML("afterbegin", HtmlGenerator.getMyAlarmHTML(alarm))
        });
        $("#my-alarm-badge").innerText = notShowCount;
    }

    updateAlarmUI(goodResponseDto) {
        $("#no-alarm-p").classList.add("invisible");
        $("#my-alarm-badge").innerText = Number($("#my-alarm-badge").innerText) + 1;
        $("#my-alarm-list").insertAdjacentHTML("afterbegin", HtmlGenerator.getMyAlarmHTML(goodResponseDto));
    }

    saveAlarmInfo(info) {
        let alarms = localStorage.getItem("myAlarm");
        alarms = alarms ? JSON.parse(alarms) : [];
        info["isShow"] = false;
        alarms.push(info);

        localStorage.setItem("myAlarm", JSON.stringify(alarms));
    }
}