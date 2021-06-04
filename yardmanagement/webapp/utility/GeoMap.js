var _googleMaps = (function () {
	var map;
	var markers = [];
	return {
		/*	
			Important:
			1) must call 'loadApi()' function in onInit() function so api file will load before map Initilize 
			2) must call 'init()' function in onAfterRendering() function 
			3) must use setTimeout for 'init()' method so it will load properlly
				Example:
					var that = this;
					setTimeout(function () {
						var oOptions = {
							"lat": -6.121435,
							"lgn": 106.774124,
							"maptype": "ROADMAP",
							"zoom": 8
						}
						_gmaps.init(that, "map_canvas", oOptions);
					}, 1000);
		*/

		/** 
		 * Function for initilize google API
		 * Please read my guide to know how to fix error ‘For development purposes only’ on google map visit given link:
		 * @see {@link http://plugins.g5plus.net/ere/knowledge-base/google-maps-shows-error-for-development-purposes-only/} - Google Maps is no longer free
		 * @param googleApi {String} - google Api URL
		 */
		loadApi: function (googleApi) {
			if (googleApi && (typeof google !== 'object' || typeof google.maps !== 'object')) {
				var script = document.createElement('script');
				script.type = 'text/javascript';
				script.src = googleApi;
				document.body.appendChild(script);
			}
		},

		/** 
		 * Function for initilize google map on view
		 * @param that {pointer} - this refrence of the controller
		 * @param domId {domElementID} - Document Element Id Where Map Will Load
		 * @param options {Object} - 
		 * Example:
		 *		oOptions = {
					"lat": -6.121435,
					"lgn": 106.774124,
					"maptype": "ROADMAP",
					"zoom": 8
				}
		 */
		init: function (that, domId, options) {

			try {
				if (typeof google === 'object' && typeof google.maps === 'object') {
					var latlng = new google.maps.LatLng(options.lat, options.lgn);
					var mapType;
					switch (options.maptype) {
					case "ROADMAP":
						mapType = google.maps.MapTypeId.ROADMAP
						break;
					case "SATELLITE":
						mapType = google.maps.MapTypeId.SATELLITE
						break;
					default:
						mapType = google.maps.MapTypeId.ROADMAP
					}
					var mapOptions = {
						center: latlng,
						zoom: options.zoom,
						mapTypeId: mapType,
					};
					map = new google.maps.Map(that.getView().byId(domId).getDomRef(),
						mapOptions);
				}
			} catch (e) {
				console.log(e);
			}

		},
		/** 
		 * Function to add a marker on map
		 * @param that {pointer} - This refrence of the controller
		 * @param oOptions {Object} - 
		 * Example : 
		 *		oOptions = {
						"lat": -6.121435,
						"lgn": 106.774124,
						"id": 123,
						"title": "Mumbai"
					};
		 * @returns {Array} - Gives array of marker objects ploted on the map 
		 */
		addMarker: function (that, oOptions) {
			try {
				var location = {
					lat: oOptions.lat,
					lng: oOptions.lng
				};
				// Plot Marker In Map
				if (typeof google === 'object' && typeof google.maps === 'object') {
					var oPosMarker = new google.maps.Marker({
						id: oOptions.id,
						position: location,
						title: oOptions.title,
						// animation: google.maps.Animation.BOUNCE,
						// icon: "https://plumbingcouncil.org/images/m/2.png",
						// zIndex	 : 10,
						map: map
					});
					markers.push(oPosMarker);

					// Set Bounds To Show All Marker Visible 
					if (!that._oCrtTripLatLngBounds) {
						that._oCrtTripLatLngBounds = new google.maps.LatLngBounds();
					}
					that._oCrtTripLatLngBounds.extend(new google.maps.LatLng(location.lat, location.lng));
					map.setCenter(that._oCrtTripLatLngBounds.getCenter());
					map.fitBounds(that._oCrtTripLatLngBounds);
					google.maps.event.addListenerOnce(map, 'bounds_changed', function (event) {
						if (map.getZoom() > 15) {
							map.setZoom(14);
						}
						if (map.getZoom() < 2) {
							map.setZoom(4);
						}
					});
				}
				return markers;
			} catch (e) {
				console.log(e);
			}
		},

		/** 
		 * Function for remove marker from map
		 * @param that {pointer} - This refrence of the controller.
		 * @param oMarker {object} - Marker Object which return by 'addMarker' function.
		 * @see {@link addMarker} - object return by 'addMarker' function.
		 */
		removeMarker: function (that, oMarker) {

			try {
				if (typeof google === 'object' && typeof google.maps === 'object') {
					if (oMarker instanceof google.maps.Marker) {
						oMarker.setMap(null);
					}
					markers = markers.filter(function (obj) {
						return obj.id !== oMarker.id;
					})

					if (!that._oCrtTripLatLngBounds) {
						that._oCrtTripLatLngBounds = new google.maps.LatLngBounds();
						map.fitBounds(that._oCrtTripLatLngBounds);
					}
					if (!that._oCrtTripLatLngBounds.isEmpty()) {
						map.setCenter(that._oCrtTripLatLngBounds.getCenter());
						map.fitBounds(that._oCrtTripLatLngBounds);
					}
					google.maps.event.addListenerOnce(map, 'bounds_changed', function (event) {
						if (map.getZoom() > 15) {
							map.setZoom(14);
						}
						if (map.getZoom() < 2) {
							map.setZoom(4);
						}
					});
				}
			} catch (e) {
				console.log(e);
			}

		},

		removeAllMarkers: function (that) {
			var aTempMarkers = $.extend(true, [], markers);
			for (var i = 0; i < aTempMarkers.length; i++) {
				this.removeMarker(that, aTempMarkers[i]);
			}
		}
	};
})();