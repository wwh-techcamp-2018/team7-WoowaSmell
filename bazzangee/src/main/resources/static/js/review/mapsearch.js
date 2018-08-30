function mapSearchHandler(evt) {
    evt.preventDefault();
    const searchText = evt.target.innerText;
    $("#daum-map-search").classList.toggle('visible', true);
    $("#daum-map-search .map-restaurant").innerText = searchText;
    $("#map-close-btn").addEventListener("click", () => {
        $("#daum-map-search").classList.toggle('visible', false);
    });

    var infowindow = new daum.maps.InfoWindow({zIndex:1});
    var mapContainer = document.getElementById('map'),
        mapOption = {
            center: new daum.maps.LatLng(37.515139, 127.103319),
            level: 3
        };
    var map = new daum.maps.Map(mapContainer, mapOption);
    var ps = new daum.maps.services.Places();

    ps.keywordSearch(searchText, placesSearchCB);

    function placesSearchCB (data, status, pagination) {
        if (status === daum.maps.services.Status.OK) {
            var bounds = new daum.maps.LatLngBounds();

            for (var i=0; i<data.length; i++) {
                displayMarker(data[i]);
                bounds.extend(new daum.maps.LatLng(data[i].y, data[i].x));
            }

            map.setBounds(bounds);
        }
        else {
            $("#daum-map-search .map-restaurant").innerText += " - 검색된 결과가 없습니다. ";
        }
    }

    function displayMarker(place) {

        var marker = new daum.maps.Marker({
            map: map,
            position: new daum.maps.LatLng(place.y, place.x)
        });

        daum.maps.event.addListener(marker, 'click', function() {
            infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
            infowindow.open(map, marker);
        });
    }
}

document.addEventListener("DOMContentLoaded", function() {
    document.querySelector('.cbp_tmtimeline').addEventListener("click", (evt) => {
        if(evt.target.className === 'fa fa-map-marker') {
            mapSearchHandler(evt);
        }
    });
});

import {$, fetchManager} from "/js/util/utils.js";