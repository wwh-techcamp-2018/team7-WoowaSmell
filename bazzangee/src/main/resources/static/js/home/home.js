import { ReviewScroll } from "/js/review/reviewScroll.js";
import { Chat } from "/js/chat/chat.js";
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

const chat = new Chat({
    showModalFunc: showModal
});
const reviewScroll = new ReviewScroll({
    foodCategoryId : 0,
    chatobj : chat
});
const joinValidator = new JoinValidator({
    hideModalFunc: hideModal,
    showModalFunc: showModal
});