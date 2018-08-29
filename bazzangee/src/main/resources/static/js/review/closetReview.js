import { ClosetReviewScroll } from "/js/review/closetReviewScroll.js";
import { Chartjs } from "/js/chartjs/chartjs.js"

const chartjs = new Chartjs();
const closetReviewScroll = new ClosetReviewScroll({
    foodCategoryId : 0,
    chartobj : chartjs
});
