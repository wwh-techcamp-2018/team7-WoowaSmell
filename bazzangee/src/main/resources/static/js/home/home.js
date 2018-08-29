import { ReviewScroll } from "/js/review/reviewScroll.js";
import { Chat } from "/js/chat/chat.js";
import { Chartjs } from "/js/chartjs/chartjs.js"


const chat = new Chat();
const chartjs = new Chartjs();
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

