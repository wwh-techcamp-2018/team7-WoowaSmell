import {$, fetchManager} from "/js/util/utils.js";

document.addEventListener("DOMContentLoaded", addChartListener);
const FOOD_ID = 1;

// 어디에 띄울까 하다가 현아가 만든 다음버전에 준비중입니다 보고 거기에 div 만들어놓음
// index화면 ajax로 가져온 후 통계에 리스너 붙이기
//     => 현재는 1초후에 리스너 부르는데 chartjs.js를 모듈식으로 만들어서 ajax 콜백에 넣으면 될것같다.
// 누르면 ajax로 통계 데이터 요청
// 빠른구현을 위해 서비스는 아직 만들지않았고 바로 repository를 controller에서 호출함
// repository에서 raw query로 해당 레스토랑의 메뉴들에 대한 평균점수를 구함 ( 실데이터 넣으면 top 5 정도로 줄이면 될듯 )
//     => 여러가지 통계에 대한 쿼리를 짜면 더 보여줄만한 것이 많을것 같다.
// fetchmanager 콜백에서 데이터 Chart에 넣고 그려준후 통계 div 보이게함
// 외부 클릭했을때 다시 안보이게 하면 될것같은데 아직 구현 안함.

var options = {
    animation: {
        animateScale: true
    },
    responsive: false,
    scales: {
        yAxes: [
            {
                ticks: {
                    beginAtZero: true
                }
            }
        ]
    }
};
var options2 = {
    scales: {
        yAxes: [{
            ticks: {
                max: 5,
                min: 0,
                stepSize: 0.5
            }
        }]
    }

};


var data = {
    labels: [
        "1", "2", "3", "4", "5"
    ],
    datasets: [
        {
            label: '평균 평점',
            data: [
                0, 0, 0, 0, 0
            ],
            backgroundColor: [
                "#455C73",
                "#9B59B6",
                "#BDC3C7",
                "#26B99A",
                "#3498DB"
            ],
            hoverBackgroundColor: [
                "#34495E",
                "#B370CF",
                "#CFD4D8",
                "#36CAAB",
                "#49A9EA"
            ],
            borderWidth: 1
        }
    ]
};


function addChartListener() {
    setTimeout(function () {
        document.querySelectorAll(".statistics").forEach((v) => {
            v.addEventListener("click", (event) => {
                event.preventDefault();
                fetchManager({
                    url: '/api/statistics/foods/' + event.target.value,
                    method: 'GET',
                    headers: { 'content-type': 'application/json'},
                    callback: onSuccess,
                    errCallback: onFail
                });
            })
        })
    }, 1000);

}

function onSuccess(response) {
    response.json().then(response => {

        console.log("success response : ", response);

        data.labels = [];
        data.datasets[0].data = [];
        for (var i = 0; i < response.length; i++) {
        console.log(i+1 + " is " + response[i].NAME + " and " + response[i].AVG_POINT);
            data.labels[i] = response[i].NAME;
            data.datasets[0].data[i] = response[i].AVG_POINT;
        }

        var ctx = document.getElementById("mychart");
        var myBarChart = new Chart(ctx, {
            type: 'bar',
            data: data,
            options: options2
        });
        myBarChart.update();
    });

    showChart(true);
    $("#mychart").addEventListener("click", onClickChartListener);
}

function onClickChartListener() {
    showChart(false);
        $("canvas#mychart").remove();
        $("div#chart_div").lastElementChild.insertAdjacentHTML('beforeend', '<canvas id="mychart" class="chart"></canvas>');
}

function onFail(response) {
    console.log("fail response : ", response);
}

function showChart(isVisible) {
     if(isVisible) $("#chart_div").classList.add("visible");
     else $("#chart_div").classList.remove("visible");
 }

