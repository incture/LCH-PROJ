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
			var oMdlCommon = oThisController.getModel("mCommon");
			oMdlCommon.setProperty("/bFilter", false);
			oThisController.fnPremfreightstable(1);
			oThisController.fnGetcarrierdetails();
			oThisController.fnFilterTest();

		},

		fnPremfreightstable: function (pageNo) {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				sUrl;
			oMdlCommon.setProperty("/pageNo", pageNo);
			oMdlCommon.setProperty("/bFilter", false);
			/*	pgNo=Number(pgNo);
			
			 */
			if (oMdlCommon.getProperty("/currRole") === "Planner") {
				sUrl = "/lch_services/premiumOrders/getAllPremiumOrders";
			} else if (oMdlCommon.getProperty("/currRole") === "Carrier_admin") {
				sUrl = "/lch_services/premiumOrders/getAllCarrierOrders";
			}
			var oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				},
				oPayload = {
					"orderId": "",
					"fromDate": "",
					"toDate": "",
					"plannerEmail": "test@email.com",
					"status": "",
					"originName": "",
					"destinationName": "",
					"pageNumber": pageNo,
					"noOfEntry": "",
					"reasonCode": ""
				};
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {
						if (oXHR.responseJSON.premiumFreightOrderDtos) {
							oMdlCommon.setProperty("/aPremfreightorders", oXHR.responseJSON.premiumFreightOrderDtos);
						} else {
							oMdlCommon.setProperty("/aPremfreightorders", oXHR.responseJSON.premiumFreightChargeDetails);
						}
						oMdlCommon.setProperty("/countOfRecords", oXHR.responseJSON.count);
						var aPremfreightorders = oMdlCommon.getProperty("/aPremfreightorders");
						for (var i = 0; i < aPremfreightorders.length; i++) {
							aPremfreightorders[i].enablecarriermode = false;
							aPremfreightorders[i].selectedCarrier = "";
							aPremfreightorders[i].selectedCarriermode = "";
						}
						oMdlCommon.refresh();
						oThisController.fnButtonVisibility();
					}

				} catch (e) {
					// console.log(e);
				}
			}, oPayload);

		},

		fnFilterTest: function () {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			oMdlCommon.setProperty("/originFilter", "");
			oMdlCommon.setProperty("/destinationFilter", "");
			oMdlCommon.setProperty("/statusFilter", "");
			oMdlCommon.setProperty("/fromDateFilter", null);
			oMdlCommon.setProperty("/toDateFilter", null);
			oMdlCommon.setProperty("/reasoncodeFilter", "");
		},

		fnButtonVisibility: function () {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			var pageNo = oMdlCommon.getProperty("/pageNo");
			pageNo = Number(pageNo);
			var count = oMdlCommon.getProperty("/countOfRecords"),
				lastPageNo = Number(Math.ceil(count / 10)); // [(totalEntries)/perPageEntries]+1 if lastPage isn't an integer Number.isInteger()
			oMdlCommon.setProperty("/lastPageNo", lastPageNo);
			if (pageNo <= lastPageNo) {
				if (pageNo === 1 && pageNo !== lastPageNo) {
					oThisController.byId("firstPage").setEnabled(false);
					oThisController.byId("previousPage").setEnabled(false);
					oThisController.byId("nextPage").setEnabled(true);
					oThisController.byId("lastPage").setEnabled(true);
				} else if (pageNo !== 1 && pageNo === lastPageNo) {
					oThisController.byId("firstPage").setEnabled(true);
					oThisController.byId("previousPage").setEnabled(true);
					oThisController.byId("nextPage").setEnabled(false);
					oThisController.byId("lastPage").setEnabled(false);
				} else if (pageNo === 1 && pageNo === lastPageNo) {
					oThisController.byId("firstPage").setEnabled(false);
					oThisController.byId("previousPage").setEnabled(false);
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
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			var count = oMdlCommon.getProperty("/countOfRecords");
			var pageNo = oMdlCommon.getProperty("/pageNo"),
				lastPageNo = Number(Math.ceil(count / 10));
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
					if (pageNo !== 1) {
						pageNo = pageNo - 1;
					}
					break;
				case 3:
					//next
					if (pageNo !== lastPageNo) {
						pageNo = pageNo + 1;
					}
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
				oMdlCommon.setProperty("/pageNo", pageNo);
				oMdlCommon.refresh();
				if (bFilter === false) {
					oThisController.fnPremfreightstable(pageNo);
				} else if (bFilter === true) {
					oThisController.onSearch(pageNo);
				}
			} else {
				pageNo = lastPageNo;
				oMdlCommon.setProperty("/pageNo", pageNo);
				oMdlCommon.refresh();
				if (bFilter === false) {
					oThisController.fnPremfreightstable(pageNo);
				} else if (bFilter === true) {
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
			var carrierselected = parseInt(oEvent.getSource().getParent().getId().split("__xmlview0--premfreightstable-rows-row")[1]);

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
			oThisController.fnFilterTest();
		},

		onSearch: function () {
			debugger;
			var oThisController = this;
			var oMdlCommon = oThisController.getModel("mCommon"),
				pageNo = oMdlCommon.getProperty("/pageNo");
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
					"orderId": "",
					"fromDate": fromDateFilter,
					"toDate": toDateFilter,
					"plannerEmail": "test@email.com",
					"status": statusFilter,
					"originName": originFilter,
					"destinationName": destinationFilter,
					"reasonCode": reasoncodeFilter,
					"pageNumber": pageNo,
					"noOfEntry": ""

				};
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {

						oMdlCommon.setProperty("/aPremfreightorders", oXHR.responseJSON.premiumFreightOrderDtos);
						oMdlCommon.refresh();
						oThisController.fnButtonVisibility();

					}

					oMdlCommon.refresh();
					console.log(oMdlCommon);
				} catch (e) {
					// console.log(e);
				}
			}, oPayload);
			/*	oThisController.fnButtonVisibility();*/
		},
		onReject: function () {
			debugger;
			var oThisController = this;
			var oMdlCommon = oThisController.getModel("mCommon"),
				pageNo = oMdlCommon.getProperty("/pageNo");
			var aIndices = oThisController.byId("premfreightstable").getSelectedIndices(),
				selectedRecords = [];
			var apremfreights = oMdlCommon.getProperty("/aPremfreightorders");
			for (var i = 0; i < aIndices.length; i++) {
				selectedRecords.push(apremfreights[aIndices[i]].orderId);
			}
			console.log(selectedRecords);
			if (selectedRecords.length === 0) {
				oThisController.showMessage("Please select the records to perform this action", "E", function () {
					console.log("error");
				});
			} else {
				oThisController.confirmUserAction("Do you want to reject the selected orders?", "W", function (sAction) {
					if (sAction === "YES") {

						var sUrl = "/lch_services/premiumOrders/rejectPremiumOrder",
							oHeader = {
								"Content-Type": "application/json",
								"Accept": "application/json"
							},
							oPayload = selectedRecords;
						oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
							try {
								if (oXHR && oXHR.responseJSON) {

									console.log(oXHR);
									oThisController.byId("premfreightstable").clearSelection();
									oThisController.fnPremfreightstable(pageNo);

								}

								oMdlCommon.refresh();
								console.log(oMdlCommon);
							} catch (e) {
								// console.log(e);
							}
						}, oPayload);

					} else {
						console.log("cancel");
					}

				});
			}
		},
		onGetCost: function () {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				pageNo = oMdlCommon.getProperty("/pageNo");
			oMdlCommon.setProperty("/flag", false);
			var flag = oMdlCommon.getProperty("/flag");
			var aIndices = oThisController.byId("premfreightstable").getSelectedIndices(),
				selectedRecords = [];
			var apremfreights = oMdlCommon.getProperty("/aPremfreightorders");
			for (var i = 0; i < aIndices.length; i++) {
				/*selectedRecords[i]*/
				selectedRecords.push({
					"orderId": apremfreights[aIndices[i]].orderId,
					"bpNumber": apremfreights[aIndices[i]].selectedCarrier,
					"carrierMode": apremfreights[aIndices[i]].selectedCarriermode,
					"carrierDetails": "",
					"carrierScac": "",
					"charge": ""

				});
				if (selectedRecords[i].bpNumber === "" && selectedRecords[i].carrierMode === "") {
					oThisController.showMessage("Please choose the carrier details of the selected fields", "E", function () {
						console.log("error");
					});
					oMdlCommon.setProperty("/flag", true);
					flag = oMdlCommon.getProperty("/flag");
					break;
				}
			}

			if (selectedRecords.length === 0) {
				oThisController.showMessage("Please select the records to perform this action", "E", function () {
					console.log("error");
				});
			} else if (flag === false) {
				var sUrl = "/lch_services/premiumOrders/setCarrierDetails",
					oHeader = {
						"Content-Type": "application/json",
						"Accept": "application/json"
					},
					oPayload = selectedRecords;
				oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
					try {
						if (oXHR && oXHR.responseJSON) {
							console.log(oXHR);
							oThisController.byId("premfreightstable").clearSelection();
							oThisController.fnPremfreightstable(pageNo);
							oThisController.showMessage("Successfuly forwarded to Carrier Admin", "I", function () {
							//success
							});

							/*oMdlCommon.setProperty("/aPremfreightorders", oXHR.responseJSON);*/

						}

						oMdlCommon.refresh();
						console.log(oMdlCommon);
					} catch (e) {
						// console.log(e);
					}
				}, oPayload);

			}
		},
		onSetCost: function () {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				pageNo = oMdlCommon.getProperty("/pageNo");
			var aIndices = oThisController.byId("premfreightstable").getSelectedIndices(),
				selectedRecords = [];
			var apremfreights = oMdlCommon.getProperty("/aPremfreightorders");
			for (var i = 0; i < aIndices.length; i++) {
				selectedRecords.push({
					"orderId": apremfreights[aIndices[i]].orderId,
					"bpNumber": apremfreights[aIndices[i]].bpNumber,
					"carrierMode": apremfreights[aIndices[i]].carrierMode,
					"carrierDetails": apremfreights[aIndices[i]].carrierDetails,
					"carrierScac": apremfreights[aIndices[i]].carrierScac,
					"charge": apremfreights[aIndices[i]].charge

				});
			}

			console.log(selectedRecords);
			var sUrl = "/lch_services/premiumOrders/setCharge",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				},
				oPayload = selectedRecords;
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {
						console.log(oXHR);
						oThisController.fnPremfreightstable(pageNo);

						/*oMdlCommon.setProperty("/aPremfreightorders", oXHR.responseJSON);*/

					}

					oMdlCommon.refresh();
					console.log(oMdlCommon);
				} catch (e) {
					// console.log(e);
				}
			}, oPayload);
			oThisController.byId("premfreightstable").clearSelection();

		}

	});
});