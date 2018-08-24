import { ReviewScroll } from "/js/review/reviewScroll.js";
import { Chat } from "/js/chat/chat.js";

const chat = new Chat();
const reviewScroll = new ReviewScroll({
    foodCategoryId : 0,
    chatobj : chat
});