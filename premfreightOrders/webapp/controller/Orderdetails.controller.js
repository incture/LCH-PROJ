sap.ui.define([
	"com/incture/lch/premfreightOrders/controller/BaseController",
	"sap/m/Token",
	"sap/ui/unified/Calendar",
	"com/incture/lch/premfreightOrders/libs/xl",
	"com/incture/lch/premfreightOrders/libs/xls"
], function (BaseController, Token, Calendar, xljs, xlsjs) {
	"use strict";

	return BaseController.extend("com.incture.lch.premfreightOrders.controller.Orderdetails", {

		onInit: function () {
			/*	this.bFirst = true;
				this._oRouter = this.getRouter();*/
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			oThisController.fnPremfreightstable();
			oThisController.fnGetcarrierdetails();
			/*this._oRouter.attachRoutePatternMatched(function (oEvent) {
				if (oEvent.getParameter("name") === "Orderdetails") {
					oMdlCommon.setProperty("/selNavigationKey", "Orders");
					/*oThisController.fnSetAvailableActions("Orderdetails", "E");
					if (!oThisController.bFirst) {
						oThisController.fnManageSrvCall();
					}
					oThisController.bFirst = false;
				}
			}, this);
*/
		},

		fnPremfreightstable: function () {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			var sUrl = "/lch_services/premiumOrders/getAllPremiumOrders",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				},
				oPayload = {
					"adhocOrderId": "",
					"fromDate": "",
					"toDate": "",
					"plannerEmail": "test@email.com",
					"status": "",
					"originName": "",
					"destinationName": "",
					"pageNumber": "1",
					"noOfEntry": "",
					"reasonCode": ""
				};
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {

						oMdlCommon.setProperty("/aPremfreightorders", oXHR.responseJSON);

					}
					oMdlCommon.refresh();
				} catch (e) {
					// console.log(e);
				}
			}, oPayload);

		},

		fnGetcarrierdetails: function () {
			var oThisController = this;
			var oMdlCommon = oThisController.getModel("mCommon");
			var sUrl = "/lch_services/premiumOrders/getAllCarrierDetails",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {

						oMdlCommon.setProperty("/aCarrierdetails", oXHR.responseJSON);

					}
					oMdlCommon.refresh();
				} catch (e) {
					// console.log(e);
				}
			});

		},

		onChangeCarrier: function (oEvent) {
			debugger;
			var oThisController = this;
			var oMdlCommon = oThisController.getModel("mCommon");
			oMdlCommon.setProperty("/aCarriermodes", []);
			oMdlCommon.setProperty("/enable/carrierMode", false);
			oMdlCommon.refresh();
			var bpNumber = oEvent.getSource().getSelectedItem().getText();
			if (bpNumber) {
				var sUrl = "/lch_services/premiumOrders/getMode",
					oHeader = {
						"Content-Type": "application/json",
						"Accept": "application/json"
					},
					oPayload = {
						"bpNumber": bpNumber
					};

				oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
					try {
						if (oXHR && oXHR.responseJSON) {
							oMdlCommon.setProperty("/aCarriermodes", oXHR.responseJSON);
							oMdlCommon.setProperty("/enable/carrierMode", true);
						}
						oMdlCommon.refresh();
						console.log(oMdlCommon);
					} catch (e) {
						// console.log(e);
					}
				}, oPayload);
			} 
		},

		onSearch: function () {
				var oThisController = this;
				var oMdlCommon = oThisController.getModel("mCommon");
				var originFilter = oMdlCommon.getProperty("/originFilter"),
					destinationFilter = oMdlCommon.getProperty("/destinationFilter"),
					statusFilter = oMdlCommon.getProperty("/statusFilter"),
					fromDateFilter = oMdlCommon.getProperty("/fromDateFilter"),
					toDateFilter = oMdlCommon.getProperty("/toDateFilter"),
					reasoncodeFilter = oMdlCommon.getProperty("/reasoncodeFilter");
				var sUrl = "/lch_services/premiumOrders/getAllPremiumOrders",
					oHeader = {
						"Content-Type": "application/json",
						"Accept": "application/json"
					},
					oPayload = {
						"adhocOrderId": "",
						"fromDate": fromDateFilter,
						"toDate": toDateFilter,
						"plannerEmail": "test@email.com",
						"status": statusFilter,
						"originName": originFilter,
						"destinationName": destinationFilter,
						"reasonCode": reasoncodeFilter,
						"pageNumber": "1",
						"noOfEntry": ""

					};
				oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
					try {
						if (oXHR && oXHR.responseJSON) {

							oMdlCommon.setProperty("/aPremfreightorders", oXHR.responseJSON);

						}

						oMdlCommon.refresh();
						console.log(oMdlCommon);
					} catch (e) {
						// console.log(e);
					}
				}, oPayload);

			}
			/*	onGetCost: function () {
					var oThisController = this;
					var oMdlCommon = this.getModel("mCommon");
					var sUrl = "/lch_services/premiumOrders/getCharge",
						oHeader = {
							"Content-Type": "application/json",
							"Accept": "application/json"
						},
						oPayload = {
							"adhocOrderId":,
							"bpNumber": ,
							"carrierScac": ,
							"carrierDetails": ,
							"carrierMode": ,
							"charge": ""

						};
					oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
						try {
							if (oXHR && oXHR.responseJSON) {

								oMdlCommon.setProperty("/aPremfreightorders", oXHR.responseJSON);

							}

							oMdlCommon.refresh();
							console.log(oMdlCommon);
						} catch (e) {
							// console.log(e);
						}
					}, oPayload);

				}*/

	});
});