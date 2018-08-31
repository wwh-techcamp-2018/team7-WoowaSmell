import {fetchManager} from "/js/util/utils.js";

function $_(selector) {
    return document.querySelector(selector);
}

export class Chartjs {
    constructor() {
        this.CHART_HEIGHT = 120;
        this.CHART_WIDTH = 200;
        this.FOOD_ID = 1;
    }

    addChartListener() {
        document.querySelectorAll(".statistics").forEach((button) => {
            button.addEventListener("click", (event) => {
                event.preventDefault();
                fetchManager({
                    url: '/api/statistics/restaurants/' + event.target.value,
                    method: 'GET',
                    headers: { 'content-type': 'application/json'},
                    callback: this.onSuccessChartData.bind(this),
                    errCallback: this.onFailChartData.bind(this)
                });
            })
        })
    }

    onSuccessChartData(response) {
        response.json().then(response => {

            $_(".x_title").innerHTML = `<button id="chart-exit" style="float:right" class="btn btn-danger">X</button><span><h2>${response.restaurantName}</h2></span>
                <div><div class="rate" data-rate-value="${response.starPoint}" style="float:left; pointer-events:none;"></div><div style="padding-left:75px"> ${response.starPoint}</div style="float:left; padding-left:5px;"></div>`
            $(".rate").rate();

            this.makeRestaurantFoodsChart(response.foodPoints);
            this.showChart(true);
            document.addEventListener("click", this.onClickChartExitListener.bind(this));
        })
    }

    makeRestaurantFoodsChart(foodPoints) {
        let data = {
             labels: [],
             datasets: [
                 {
                     label: [],
                     data: [],
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

        let options = {
             legend: {
                 display: false,
             },
             scales: {
                 yAxes: [{
                     ticks: {
                         max: 5,
                         min: 0,
                         stepSize: 0.5
                     }
                 }],
                 xAxes: [{
                    barPercentage : 0.8
                 }]
             }
         };

        data.labels = [];
        data.datasets[0].data = [];

        for (var i = 0; i < foodPoints.length; i++) {
//            data.labels[i] = foodPoints[i].name;
//            data.datasets[0].data[i] = foodPoints[i].avg_point;
            data.labels[i] = foodPoints[i].NAME;
            data.datasets[0].data[i] = foodPoints[i].AVG_POINT;
        }

        var ctx = document.getElementById("foodChart");
        ctx.height = this.CHART_HEIGHT;
        ctx.width = this.CHART_WIDTH;
        var myBarChart = new Chart(ctx, {
            type: 'bar',
            data: data,
            options: options
        });
        myBarChart.update();
    }

    onClickChartExitListener({target}) {
        if (target != $_("#chart-exit")) {
            return ;
        }
        this.showChart(false);
        document.removeEventListener("click", this.onClickChartExitListener);
        $_("canvas#foodChart").remove();
        $_("div#chart_div").lastElementChild.insertAdjacentHTML('beforeend', '<canvas id="foodChart" class="chart"></canvas>');
    }

    onFailChartData(response) {
//        response.json().then(response => {
            $_(".x_title").innerHTML = `<button id="chart-exit" style="float:right" class="btn btn-danger">X</button><span><h2>데이터가 없습니다. T.T<small></small></h2></span>`
            this.showChart(true);
            document.addEventListener("click", this.onClickChartExitListener.bind(this));
//        })
    }

    showChart(isVisible) {
         if(isVisible) $_("#chart_div").classList.add("visible");
         else $_("#chart_div").classList.remove("visible");
    }
}
