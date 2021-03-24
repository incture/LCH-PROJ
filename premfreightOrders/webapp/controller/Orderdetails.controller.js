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

			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			oMdlCommon.setProperty("/bFilter", false);
			oThisController.fnPremfreightstable(1);
			oThisController.fnGetcarrierdetails();

		},

		fnPremfreightstable: function (pgNo) {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			oMdlCommon.setProperty("/pageNo", pgNo);
			oMdlCommon.setProperty("/bFilter", false);

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
					"pageNumber": pgNo,
					"noOfEntry": "",
					"reasonCode": ""
				};
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {

						oMdlCommon.setProperty("/aPremfreightorders", oXHR.responseJSON.premiumFreightOrderDtos);
						oMdlCommon.setProperty("/countOfRecords", oXHR.responseJSON.count);
						var aPremfreightorders = oMdlCommon.getProperty("/aPremfreightorders");
						for (var i = 0; i < aPremfreightorders.length; i++) {
							aPremfreightorders[i].enablecarriermode = false;
						}
					}
					oMdlCommon.refresh();
				} catch (e) {
					// console.log(e);
				}
			}, oPayload);
			oThisController.fnButtonVisibility();

		},

		fnButtonVisibility: function () {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			var pageNo = oMdlCommon.getProperty("/pageNo");
			pageNo = Number(pageNo);

			var count = oMdlCommon.getProperty("/countOfRecords"),
				lastPageNo = Math.ceil(count / 10); // [(totalEntries)/perPageEntries]+1 if lastPage isn't an integer Number.isInteger()
			oMdlCommon.setProperty("/lastPageNo", lastPageNo);
			if (pageNo <= lastPageNo) {
				if (pageNo === 1) {
					oThisController.byId("firstPage").setEnabled(false);
					oThisController.byId("previousPage").setEnabled(false);
					oThisController.byId("nextPage").setEnabled(true);
					oThisController.byId("lastPage").setEnabled(true);
				} else if (pageNo === lastPageNo) {
					oThisController.byId("firstPage").setEnabled(true);
					oThisController.byId("previousPage").setEnabled(true);
					oThisController.byId("nextPage").setEnabled(false);
					oThisController.byId("lastPage").setEnabled(false);
				} else {
					oThisController.byId("firstPage").setEnabled(true);
					oThisController.byId("previousPage").setEnabled(true);
					oThisController.byId("nextPage").setEnabled(true);
					oThisController.byId("lastPage").setEnabled(true);
				}
			}
		},

		fnPagination: function (select) {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			var count = oMdlCommon.getProperty("/countOfRecords");
			var pageNo = oMdlCommon.getProperty("/pageNo"),
				lastPageNo = Math.ceil(count / 10);
			oMdlCommon.setProperty("/lastPageNo", lastPageNo);
			pageNo = Number(pageNo);
			var bFilter = oMdlCommon.getProperty("/bFilter");

			if (pageNo <= lastPageNo) {
				switch (select) {
				case 1:
					//first
					pageNo = 1;
					break;
				case 2:
					//prev
					pageNo = pageNo - 1;
					break;
				case 3:
					//next
					pageNo = pageNo + 1;
					break;
				case 4:
					//last
					pageNo = lastPageNo;
					break;
				case 5:
					//jump
					pageNo = pageNo;

					break;
				}

				if (bFilter === false) {
					oThisController.fnPremfreightstable(pageNo);
				} else if (bFilter === true) {
					oThisController.onSearch(pageNo);
				}

			} else {
				if (bFilter === false) {
					pageNo = lastPageNo;
					oMdlCommon.refresh();
					oThisController.fnPremfreightstable(pageNo);
				} else if (bFilter === true) {
					pageNo = lastPageNo;
					oMdlCommon.refresh();
					oThisController.onSearch(pageNo);
				}
			}
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
			oMdlCommon.refresh();
			if (oEvent.getSource().getSelectedItem()) {
				var bpNumber = oEvent.getSource().getSelectedItem().getText();
			} else {
				bpNumber = "";
			}
			var aPremfreightorders = oMdlCommon.getProperty("/aPremfreightorders");
			var carrierselected = parseInt(oEvent.getSource().getParent().getId().split("__table0-rows-row")[1]);

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
							aPremfreightorders[carrierselected].enablecarriermode = true;

						}
						oMdlCommon.refresh();

					} catch (e) {
						// console.log(e);
					}
				}, oPayload);
			} else {
				aPremfreightorders[carrierselected].enablecarriermode = false;
				aPremfreightorders[carrierselected].selectedCarriermode = "";
				oMdlCommon.refresh();
			}
		},

		onClearFilter: function () {
			var oThisController = this;
			var oMdlCommon = oThisController.getModel("mCommon");
			oMdlCommon.setProperty("/bFilter", false);
			oThisController.fnPremfreightstable(1);
		},

		onSearch: function (pgNo) {
			var oThisController = this;
			var oMdlCommon = oThisController.getModel("mCommon");
			oMdlCommon.setProperty("/bFilter", true);
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
					"pageNumber": pgNo,
					"noOfEntry": ""

				};
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {

						oMdlCommon.setProperty("/aPremfreightorders", oXHR.responseJSON.premiumFreightOrderDtos);

					}

					oMdlCommon.refresh();
					console.log(oMdlCommon);
				} catch (e) {
					// console.log(e);
				}
			}, oPayload);
			oThisController.fnButtonVisibility();

		},
		onReject: function () {
				var aIndices = this.byId("premfreightstable").getSelectedIndices();
				var oThisController = this;
				var oMdlCommon = oThisController.getModel("mCommon"),
					arr = [];
				var apremfreights = oMdlCommon.getProperty("/aPremfreightorders");
				for (var i = 0; i < aIndices.length; i++) {
					arr.push(apremfreights[oThisController.aindices[i]].adhocOrderId);
				}

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