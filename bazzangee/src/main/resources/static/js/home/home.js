import { ReviewScroll } from "/js/review/reviewScroll.js";
import { Chat } from "/js/chat/chat.js";
import { Chartjs } from "/js/chartjs/chartjs.js"
import { JoinValidator } from "/js/user/joinValidator.js";

$(document).ready(function(){
    $('#birth-container').datetimepicker({
        format: 'YYYY-MM-DD'
    });
});

function hideModal(modalId) {
    $("#" + modalId).modal('hide');
}

function showModal(modalId) {
    $("#" + modalId).modal('show');
}

const chartjs = new Chartjs();
const chat = new Chat({
    showModalFunc: showModal
});
const joinValidator = new JoinValidator({
    hideModalFunc: hideModal,
    showModalFunc: showModal
});
const reviewScroll = new ReviewScroll({
    foodCategoryId : 0,
    chatobj : chat,
    chartobj : chartjs
});

function setDomHeightHandler () {
    document.querySelector(".chat-history").setAttribute("style", "height : " + (window.innerHeight - Number(document.querySelector("#chat-container").offsetTop) - 220) + "px");
}

document.addEventListener("DOMContentLoaded", function() {
    setDomHeightHandler();
    window.addEventListener("resize", setDomHeightHandler);
});

