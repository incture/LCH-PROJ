sap.ui.define([
	"com/incture/lch/yardmanagement/controller/BaseController"
], function (BaseController) {
	"use strict";

	return BaseController.extend("com.incture.lch.yardmanagement.controller.GeoMap", {

		/**
		 * Called when a controller is instantiated and its View controls (if available) are already created.
		 * Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
		 * @memberOf com.incture.lch.yardmanagement.view.GeoMap
		 */
		onInit: function () {
			var oThisController = this;
			_googleMaps.loadApi(
				"https://maps.googleapis.com/maps/api/js?key=AIzaSyDPC6EWrun3iC8X9Ji4OCBC73iPNZl-vVs & sensor=false & libraries=geometry");

			oThisController.getRouter().attachRoutePatternMatched(function (oEvent) {
				var oMdlCommon = oThisController.getParentModel("mCommon");
				var viewName = oEvent.getParameter("name");
				if (viewName === "YardHistory") {
					oMdlCommon.setProperty("/visible/bVisibleSave", false);
					oMdlCommon.setProperty("/sTntSelectedKey", "yardHistory");
				}
				if (viewName === "TrailerList") {
					oThisController.fnSetHistoryDefault();
					oThisController.fnManageSrvCall();
					oMdlCommon.setProperty("/sTntSelectedKey", "yardOverview");
				}
				if (viewName === "GeoMap") {
					oMdlCommon.setProperty("/sTntSelectedKey", "geoMap");
					_googleMaps.removeAllMarkers(oThisController);
					oThisController.fnInitializeMap();
				}
			}, oThisController);
		},

		/**
		 * Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered
		 * (NOT before the first rendering! onInit() is used for that one!).
		 * @memberOf com.incture.lch.yardmanagement.view.GeoMap
		 */
		//	onBeforeRendering: function() {
		//
		//	},

		/**
		 * Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here.
		 * This hook is the same one that SAPUI5 controls get after being rendered.
		 * @memberOf com.incture.lch.yardmanagement.view.GeoMap
		 */
		onAfterRendering: function () {
			var oThisController = this;
			oThisController.openBusyDialog();
			oThisController.fnDelayedCall(function () {
				oThisController.fnInitializeMap();
				oThisController.closeBusyDialog();
			}, 2400);
		},

		/**
		 * Called when the Controller is destroyed. Use this one to free resources and finalize activities.
		 * @memberOf com.incture.lch.yardmanagement.view.GeoMap
		 */
		//	onExit: function() {
		//
		//	}
		
		fnInitializeMap: function (oEvent) {
			var oThisController = this;
			var oOptions = {
				"lat": 34.8424922,
				"lgn": -95.6964972,
				"maptype": "ROADMAP",
				"zoom": 5
			};
			_googleMaps.init(oThisController, "map_canvas", oOptions);
		},

		fnSetMarkers: function () {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var aShipments = $.extend(true, [], oMdlCommon.getProperty("/aShipments"));
			var bNoMarkers = true;

			for (var i = 0; i < aShipments.length; i++) {
				var oShipment = aShipments[i];
				if (oShipment.latitude && oShipment.longitude) {
					var oOptions = {
						"lat": Number(oShipment.latitude),
						"lng": Number(oShipment.longitude),
						"id": Number(oShipment.freightOrderNo),
						"title": oShipment.freightOrderNo
					};
					
					if(oShipment.yardId && oShipment.yardLocation){
						oOptions.title += ("--" + oShipment.yardId + "--" + oShipment.yardLocation);
					} else if(oShipment.yardId){
						oOptions.title += ("--" + oShipment.yardId);
					} else if(oShipment.yardLocation){
						oOptions.title += ("--" + oShipment.yardLocation);
					}
					
					_googleMaps.addMarker(oThisController, oOptions);
					bNoMarkers = false;
				}
			}
			
			if(bNoMarkers){
				oThisController.showMessage(oThisController.getMessage("NO_MARKERS"), "I");
			}

			// var oOptions = {
			// 	"lat": 18.511034,
			// 	"lng": 73.777389,
			// 	"id": aShipments[1].freightOrderNo,
			// 	"title": (aShipments[1].yardId + "--" + aShipments[1].yardLocation)
			// };
			// _googleMaps.addMarker(oThisController, oOptions);
		}

	});

});