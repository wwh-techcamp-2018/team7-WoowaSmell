import {$, fetchManager} from "/js/util/utils.js";

export class Chartjs {
    constructor() {
        this.FOOD_ID = 1;
        this.options = {
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
        this.data = {
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
                    errCallback: this.onFailChartData
                });
            })
        })
    }

    onSuccessChartData(response) {
        response.json().then(response => {

            console.log("success response : ", response);

            this.data.labels = [];
            this.data.datasets[0].data = [];
            for (var i = 0; i < response.length; i++) {
            console.log(i+1 + " is " + response[i].NAME + " and " + response[i].AVG_POINT);
                this.data.labels[i] = response[i].NAME;
                this.data.datasets[0].data[i] = response[i].AVG_POINT;
            }

            var ctx = document.getElementById("foodChart");
            var myBarChart = new Chart(ctx, {
                type: 'bar',
                data: this.data,
                options: this.options
            });
            myBarChart.update();
        });

        this.showChart(true);
        document.addEventListener("click", this.onClickChartListener.bind(this));
    }

    onClickChartListener({target}) {
        if (target == $("#foodChart")) {
            return ;
        }
        this.showChart(false);
        document.removeEventListener("click", this.onClickChartListener);
        $("canvas#foodChart").remove();
        $("canvas#restaurantChart").remove();
        $("div#chart_div").lastElementChild.insertAdjacentHTML('beforeend', '<canvas id="foodChart" class="chart"></canvas>');
        $("div#chart_div").lastElementChild.insertAdjacentHTML('beforeend', '<canvas id="restaurantChart" class="chart"></canvas>');
    }

    onFailChartData(response) {
        console.log("fail response : ", response);
    }

    showChart(isVisible) {
         if(isVisible) $("#chart_div").classList.add("visible");
         else $("#chart_div").classList.remove("visible");
    }
}
