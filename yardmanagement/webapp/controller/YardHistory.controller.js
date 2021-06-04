sap.ui.define([
	"com/incture/lch/yardmanagement/controller/BaseController"
], function (BaseController) {
	"use strict";

	return BaseController.extend("com.incture.lch.yardmanagement.controller.YardHistory", {

		/**
		 * Called when a controller is instantiated and its View controls (if available) are already created.
		 * Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
		 * @memberOf com.incture.lch.yardmanagement.view.YardHistory
		 */
		onInit: function () {
			var oThisController = this;
			this._oRouter = this.getRouter();
			this._oRouter.attachRoutePatternMatched(function (oEvent) {
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
				}
			});
		},

		//function to set Default values:
		fnSetHistoryDefault: function () {
			var oThisController = this;
			var oMdlCommon = oThisController.getParentModel("mCommon");
			var oHistoryFilters = {
				"freightOrderNo": "",
				"carrier": "",
				"trailer": "",
				"location": "",
				"supplier": "",
				"fromDateFilter": null,
				"toDateFilter": null
			};
			oMdlCommon.setProperty("/aHistoryShipments", []);
			oMdlCommon.setProperty("/aBckHistoryShipments", []);
			oMdlCommon.setProperty("/iHistoryYardLoaded", 0);
			oMdlCommon.setProperty("/iHistoryTotalLength", 0);
			oMdlCommon.setProperty("/oHistoryFilters", oHistoryFilters);
			oMdlCommon.setProperty("/oValState/sHisFromDateValState", "None");
			oMdlCommon.setProperty("/oValState/sHisToDateValState", "None");
			oMdlCommon.refresh();
		},

		//function to handle History filter
		onGetHistoryData: function () {
			var oThisController = this;
			oThisController.openBusyDialog();
			var oMdlCommon = this.getParentModel("mCommon");
			var oHistoryFilters = $.extend(true, {}, oMdlCommon.getProperty("/oHistoryFilters"));
			var oValState = $.extend(true, {}, oMdlCommon.getProperty("/oValState"));
			var sUserId = oThisController._oCommon.userDetails.name.familyName;
			var sRole = oThisController.fnSetPendingWith();
		/*	var sUserId = "bindhu";
			var sRole = "LCH_MATERIAL_HANDLER"*/
			if (!sRole) {
				oThisController.showMessage(oThisController.getMessage("NO_ROLE_INFO"), "E");
				oThisController.closeBusyDialog();
				return;
			}
			var oDataPayload = {
				"carrier": "",
				// "createdBy": sUserId,
				"freightOrderNo": "",
				"fromDate": "",
				"location": "",
				"supplier": "",
				"status": ["Out Gate"],
				"trailer": "",
				"toDate": ""
			};

			let aValueStateChk = [
				oValState.sHisFromDateValState,
				oValState.sHisToDateValState
			];
			if (aValueStateChk.indexOf("Error") !== -1) {
				oThisController.showMessage(oThisController.getMessage("FILTER_ERROR_INFO"), "E");
				oThisController.closeBusyDialog();
				return;
			}

			if (!oHistoryFilters.fromDateFilter && !oHistoryFilters.toDateFilter) {
				oThisController.showMessage(oThisController.getMessage("MANDATORY_INFO"), "E");
				oThisController.closeBusyDialog();
				return;
			}

			if (oHistoryFilters.fromDateFilter) {
				var fromDate = oMdlCommon.getProperty("/oHistoryFilters/fromDateFilter");
				var dFromDateFormat = oThisController.dateHelper.getBackEndDate(fromDate);
				dFromDateFormat = dFromDateFormat.slice(0, 10);
			}

			if (oHistoryFilters.toDateFilter) {
				var toDate = oMdlCommon.getProperty("/oHistoryFilters/toDateFilter");
				var dToDateFormat = oThisController.dateHelper.getBackEndDate(toDate);
				dToDateFormat = dToDateFormat.slice(0, 10);
			}

			oDataPayload.fromDate = dFromDateFormat + " 00:00:00";
			oDataPayload.toDate = dToDateFormat + " 23:59:59";

			oDataPayload.freightOrderNo = (oHistoryFilters.filterByKey === "F1" && oHistoryFilters.filterByText) ? oHistoryFilters.filterByText :
				"";
			oDataPayload.carrier = (oHistoryFilters.filterByKey === "F3" && oHistoryFilters.filterByText) ? oHistoryFilters.filterByText : "";
			oDataPayload.trailer = (oHistoryFilters.filterByKey === "F2" && oHistoryFilters.filterByText) ? oHistoryFilters.filterByText : "";
			oDataPayload.yardLocation = (oHistoryFilters.yardLocation) ? oHistoryFilters.yardLocation : "";
			// oDataPayload.yardId = (oHistoryFilters.yardId) ? oHistoryFilters.yardId : "";
			oDataPayload.supplier = (oHistoryFilters.supplierId) ? oHistoryFilters.supplierId : "";

			this.fnGetYardHistoryData(oDataPayload);
		},

		//Funciton call to get Yard History Data
		fnGetYardHistoryData: function (oDataPayload) {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var sShipmentsViewType = oMdlCommon.getProperty("/shipmentsViewType");
			var sUrl = "/lch_services/yardHistory/getYardManagementHistory";
			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json"
			};
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				if (status === "success" && oXHR.status === 200) {
					if (oXHR.responseJSON) {
						var aHistoryShipments = oThisController.fnSetFormatYardData(oXHR.responseJSON);
						oMdlCommon.setProperty("/aHistoryShipments", aHistoryShipments);
						oMdlCommon.setProperty("/aBckHistoryShipments", aHistoryShipments);
						oMdlCommon.setProperty("/iHistoryYardLoaded", aHistoryShipments.length);
						oMdlCommon.setProperty("/iHistoryTotalLength", aHistoryShipments.length);
					} else {
						oMdlCommon.setProperty("/aHistoryShipments", []);
					}
				} else {
					oThisController.showMessage(oThisController.getMessage("REQUEST_OPERATION_FAILED"), "E");
				}
				oMdlCommon.refresh();
				oThisController.closeBusyDialog();
			}, oDataPayload);
		},

		//Function to handle clear Filter
		onHistoryClearYrdFilter: function () {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var oHistoryFilters = {
				"fromDateFilter": null,
				"toDateFilter": null,
			};

			oMdlCommon.setProperty("/oHistoryFilters", oHistoryFilters);
			oMdlCommon.setProperty("/dHistoryMinTDate", null);
			oMdlCommon.setProperty("/dHistoryMaxTDate", null);
			oMdlCommon.setProperty("/oValState/sHisFromDateValState", "None");
			oMdlCommon.setProperty("/oValState/sHisToDateValState", "None");
			oMdlCommon.setProperty("/aHistoryShipments", []);
			oMdlCommon.refresh();

		},

		//Function to handle Change Todate
		onHistoryToDateCBox: function (oEvent) {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var oHistoryFilters = $.extend(true, {}, oMdlCommon.getProperty("/oHistoryFilters"));
			var dFromDate = (oHistoryFilters.fromDateFilter) ? new Date(oHistoryFilters.fromDateFilter) : "";
			var dToDate = (oHistoryFilters.toDateFilter) ? new Date(oHistoryFilters.toDateFilter) : "";
			if (dFromDate && dToDate) {
				if (dToDate.getTime() < dFromDate.getTime()) {
					oMdlCommon.setProperty("/oValState/sHisFromDateValState", "Error");
					oMdlCommon.setProperty("/oValState/sHisToDateValState", "Error");
				} else {
					oMdlCommon.setProperty("/oValState/sHisFromDateValState", "None");
					oMdlCommon.setProperty("/oValState/sHisToDateValState", "None");
				}
			}
			oMdlCommon.refresh();
		},

		onHistoryFromDateCBox: function () {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var oHistoryFilters = $.extend(true, {}, oMdlCommon.getProperty("/oHistoryFilters"));
			var dFromDate = (oHistoryFilters.fromDateFilter) ? new Date(oHistoryFilters.fromDateFilter) : "";
			var dToDate = (oHistoryFilters.toDateFilter) ? new Date(oHistoryFilters.toDateFilter) : "";

			if (dFromDate && dToDate) {
				if (dFromDate.getTime() > dToDate.getTime()) {
					oMdlCommon.setProperty("/oValState/sHisFromDateValState", "Error");
					oMdlCommon.setProperty("/oValState/sHisToDateValState", "Error");
				} else {
					oMdlCommon.setProperty("/oValState/sHisFromDateValState", "None");
					oMdlCommon.setProperty("/oValState/sHisToDateValState", "None");
				}
			}
			if (dFromDate) {
				let dCurDate = dFromDate;
				let dHistoryMaxTDate = dCurDate;
				dHistoryMaxTDate.setDate(dHistoryMaxTDate.getDate() + 50);
				oMdlCommon.setProperty("/dHistoryMaxTDate", dHistoryMaxTDate);
			}
			oMdlCommon.refresh();
		},

		onDownloadHistory: function (oEvent) {
			let oThisController = this;
			let oMdlCommon = this.getParentModel("mCommon");
			let sURL = "/lch_services/yardHistory/downloadYardHistoryDetails";
			let aHistoryShipments = $.extend(true, [], oMdlCommon.getProperty("/aHistoryShipments"));

			let oPayload = [
				/* {
				        "carrier": "1000000335",
				        "trailer": "",
				        "sealNo": "",
				        "licencePlateNo": "",
				        "status": "INGATE",
				        "supplier": "PD01",
				        "destId": "",
				        "plannedShipDate": ""
				    }*/
			];
			
			if(aHistoryShipments.length === 0){
				oThisController.fnShowMessage(oThisController.getMessage("NO_DATA_AVAILABLE"),"E");
				return;
			}

			for (let i = 0; i < aHistoryShipments.length; i++) {
				let oHistoryShipment = $.extend(true, {}, aHistoryShipments[i]);
				oHistoryShipment["carrier"] = oHistoryShipment.carrierKey;
				oHistoryShipment.plannedShipDate = oThisController.dateHelper.getBackEndDate(oHistoryShipment.plannedShipDate);
				oPayload.push(oHistoryShipment);
			}

			oThisController.onBase64FileDownload("HISTORY", sURL, oPayload);
		},

		/**
		 * Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered
		 * (NOT before the first rendering! onInit() is used for that one!).
		 * @memberOf com.incture.lch.yardmanagement.view.YardHistory
		 */
		//	onBeforeRendering: function() {
		//
		//	},

		/**
		 * Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here.
		 * This hook is the same one that SAPUI5 controls get after being rendered.
		 * @memberOf com.incture.lch.yardmanagement.view.YardHistory
		 */
		//	onAfterRendering: function() {
		//
		//	},

		/**
		 * Called when the Controller is destroyed. Use this one to free resources and finalize activities.
		 * @memberOf com.incture.lch.yardmanagement.view.YardHistory
		 */
		//	onExit: function() {
		//
		//	}

	});

});