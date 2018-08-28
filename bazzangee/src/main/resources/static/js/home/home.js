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