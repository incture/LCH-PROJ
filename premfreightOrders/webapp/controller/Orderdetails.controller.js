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
			debugger;
			var oThisController = this;
			var oMdlCommon = oThisController.getModel("mCommon");
			oMdlCommon.setProperty("/bFilter", false);
			console.log(oMdlCommon.getProperty("/currRole"));
			if (oMdlCommon.getProperty("/currRole") === "Planner") {
				oThisController.fnPremfreightstable(1);
			} else if (oMdlCommon.getProperty("/currRole") === "Carrier_admin") {
				oThisController.fnPremfreightstable(1);
			} else if (oMdlCommon.getProperty("/currRole") === "Manager") {
				oThisController.fnPremfreightstableManager();
			}
			/*	oThisController.fnPremfreightstable(1);*/
			oThisController.fnPremfreightstableManager();
			oThisController.fnGetcarrierdetails();
			oThisController.fnFilterTest();

		},

		onButtonPress: function (oEvent) {
			var oButton = oEvent.getSource();
			this.byId("actionSheet").openBy(oButton);
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
			/*	 sUrl = "/lch_services/premiumOrders/getAllPremiumOrders1";*/
			sUrl = "/lch_services/premiumOrders/getAllPremiumOrders1";
			if (oMdlCommon.getProperty("/currRole") === "Planner") {
				sUrl = "/lch_services/premiumOrders/getAllPremiumOrders1";
			} else if (oMdlCommon.getProperty("/currRole") === "Carrier_admin") {
				sUrl = "/lch_services/premiumOrders/getAllCarrierOrders";
			}
			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json"
			};
			var oPayload = {
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
						if (oXHR.responseJSON.premiumFreightDto1) {
							/*	oMdlCommon.setProperty("/aPremfreightorders", oXHR.responseJSON.premiumFreightOrderDtos);*/
							oMdlCommon.setProperty("/aPremfreightorders", oXHR.responseJSON.premiumFreightDto1);
						} else {
							oMdlCommon.setProperty("/aPremfreightorders", oXHR.responseJSON.premiumFreightChargeDetails);
						}
						oMdlCommon.setProperty("/countOfRecords", oXHR.responseJSON.count);
						console.log(oMdlCommon);
						var aPremfreightorders = oMdlCommon.getProperty("/aPremfreightorders");
						for (var i = 0; i < aPremfreightorders.length; i++) {
							debugger;
							aPremfreightorders[i].enablecarriermode = false;
							if (aPremfreightorders[i].bpNumber && aPremfreightorders[i].carrierMode) {
								aPremfreightorders[i].selectedCarrier = aPremfreightorders[i].bpNumber;
								aPremfreightorders[i].selectedCarriermode = aPremfreightorders[i].carrierMode;
								aPremfreightorders[i].enablecarrier = false;
							} else {
								aPremfreightorders[i].selectedCarrier = "";
								aPremfreightorders[i].selectedCarriermode = "";
							}
						}
						oMdlCommon.refresh();
						oThisController.fnButtonVisibility();
					}

				} catch (e) {
					// console.log(e);
				}
			}, oPayload);

		},

		fnPremfreightstableManager: function () {
			debugger;
			var oThisController = this;
			oThisController.byId("pagination").setVisible(false);
			var oMdlCommon = this.getModel("mCommon"),
				aPremFreightOrders = [],
				aTaskDetails = [],
				sUrl = "/lch_services/premiumOrders/getManagerOrders/";
			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json",
				"userId": ""
			};
			/*oHeader.user = oMdlCommon.userDetails.id;*/
			oHeader.userId = "P000331";
			sUrl += oHeader.userId;

			oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {

						for (var i = 0; i < oXHR.responseJSON.length; i++) {
							aPremFreightOrders[i] = oXHR.responseJSON[i].premiumFreightDto1;
							aTaskDetails[i] = oXHR.responseJSON[i].taskDetailsDto.taskId;
						}
						oMdlCommon.setProperty("/aPremfreightorders", aPremFreightOrders);
						oMdlCommon.setProperty("/aTaskDetails", aTaskDetails);
						oMdlCommon.refresh();
						console.log(oMdlCommon);

					}

				} catch (e) {
					// console.log(e);
				}
			});

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

			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			var pageNo = oMdlCommon.getProperty("/pageNo");
			pageNo = Number(pageNo);
			var count = oMdlCommon.getProperty("/countOfRecords"),
				lastPageNo = Number(Math.ceil(count / 10));
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
			var index = oThisController.byId("premfreightstable").getSelectedIndices(),
				selectedRecord = [];
			var apremfreights = oMdlCommon.getProperty("/aPremfreightorders");
			selectedRecord.push(apremfreights[index].orderId);
			console.log(selectedRecord);
			if (selectedRecord.length === 0) {
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
							oPayload = selectedRecord;
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
		onForwardtoCA: function () {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				pageNo = oMdlCommon.getProperty("/pageNo");
			oMdlCommon.setProperty("/flag", false);
			var flag = oMdlCommon.getProperty("/flag");
			var index = oThisController.byId("premfreightstable").getSelectedIndices(),
				selectedRecord = [];
			var apremfreights = oMdlCommon.getProperty("/aPremfreightorders")
			selectedRecord.push({
				"orderId": apremfreights[index].orderId,
				"bpNumber": apremfreights[index].selectedCarrier,
				"carrierMode": apremfreights[index].selectedCarriermode,
				"carrierDetails": "",
				"carrierScac": "",
				"charge": ""
			});
			if (selectedRecord.bpNumber === "" || selectedRecord.carrierMode === "") {
				oThisController.showMessage("Please choose the carrier details of the selected fields", "E", function () {
					console.log("error");
				});
				oMdlCommon.setProperty("/flag", true);
				oMdlCommon.refresh();
				flag = oMdlCommon.getProperty("/flag");
			}
			if (apremfreights[index].status === "Pending with Carrier Admin") {
				oThisController.showMessage("Selected Record already forwarded to CA", "E", function () {
					console.log("error");
				});
				oMdlCommon.setProperty("/flag", true);
				oMdlCommon.refresh();
				flag = oMdlCommon.getProperty("/flag");
			}

			if (selectedRecord.length === 0) {
				oThisController.showMessage("Please select the records to perform this action", "E", function () {
					console.log("error");
				});
			} else if (flag === false) {
				var sUrl = "/lch_services/premiumOrders/setCarrierDetails",
					oHeader = {
						"Content-Type": "application/json",
						"Accept": "application/json"
					},
					oPayload = selectedRecord;
				oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
					try {
						if (oXHR && oXHR.responseJSON) {
							console.log(oXHR);
							oThisController.byId("premfreightstable").clearSelection();
							oThisController.fnPremfreightstable(pageNo);
							oThisController.showMessage("Successfuly forwarded to Carrier Admin", "I", function () {
								//success
							});
						}

						oMdlCommon.refresh();
						console.log(oMdlCommon);
					} catch (e) {
						// console.log(e);
					}
				}, oPayload);

			}
		},
		onForwardtoPlanner: function () {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				pageNo = oMdlCommon.getProperty("/pageNo");
			var index = oThisController.byId("premfreightstable").getSelectedIndices(),
				selectedRecord = [];
			var apremfreights = oMdlCommon.getProperty("/aPremfreightorders");
			selectedRecord.push({
				"orderId": apremfreights[index].orderId,
				"bpNumber": apremfreights[index].bpNumber,
				"carrierMode": apremfreights[index].carrierMode,
				"carrierDetails": apremfreights[index].carrierDetails,
				"carrierScac": apremfreights[index].carrierScac,
				"charge": apremfreights[index].charge
			});
			console.log(selectedRecord);
			if (selectedRecord.length === 0) {
				oThisController.showMessage("Please select the records to perform this action", "E", function () {
					console.log("error");
				});
			} else {
				var sUrl = "/lch_services/premiumOrders/setCharge",
					oHeader = {
						"Content-Type": "application/json",
						"Accept": "application/json"
					},
					oPayload = selectedRecord;
				oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
					try {
						if (oXHR) {
							debugger;
							console.log(oXHR);
							oThisController.byId("premfreightstable").clearSelection();
							oThisController.fnPremfreightstable(pageNo);
							oThisController.showMessage("Successfuly forwarded to Planner", "I", function () {
								//success
							});
						}
						oMdlCommon.refresh();
						console.log(oMdlCommon);
					} catch (e) {
						// console.log(e);
					}
				}, oPayload);
			}
		},

		onForwardtoManager: function () {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				pageNo = oMdlCommon.getProperty("/pageNo");
			oMdlCommon.setProperty("/flag", false);
			var flag = oMdlCommon.getProperty("/flag");
			var index = oThisController.byId("premfreightstable").getSelectedIndices();
			var apremfreights = oMdlCommon.getProperty("/aPremfreightorders");
			var oPayload = {
				"premiumRequestDtoList": [{
					"orderId": apremfreights[index].orderId

				}],
				"userDetailInfo": {
					"userId": "P000331"
				}
			};
			if (apremfreights[index].charge === 0) {
				oThisController.showMessage("No charge set for the selected record", "E", function () {

				});
				oMdlCommon.setProperty("/flag", true);
				oMdlCommon.refresh();
				flag = oMdlCommon.getProperty("/flag");

			}

			/*	if (selectedRecord.length === 0) {
					oThisController.showMessage("Please select the records to perform this action", "E", function () {
						console.log("error");
					});
				} else*/
			if (flag === false) {
				var sUrl = "/lch_services/premiumOrders/forwardToApprover",
					/*	var sUrl = "/lch_services/premiumOrders/getManagerOrders/",*/
					oHeader = {
						"Content-Type": "application/json",
						"Accept": "application/json"
							/*	"userId" : ""*/
					};

				/*oHeader.userId = "P000330";
				sUrl += oHeader.userId;*/
				oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
					try {
						if (oXHR) {
							debugger;
							console.log(oXHR);
							oThisController.byId("premfreightstable").clearSelection();
							oThisController.fnPremfreightstable(pageNo);
							oThisController.showMessage("Successfuly forwarded to Manager", "I", function () {
								//success
							});
						}
						oMdlCommon.refresh();
						console.log(oMdlCommon);
					} catch (e) {
						// console.log(e);
					}
				}, oPayload);
			}
		},

		onApprove: function (action) {
			debugger;
			console.log(action);
			var oThisController = this;
			var oMdlCommon = oThisController.getModel("mCommon"),
				pageNo = oMdlCommon.getProperty("/pageNo");
			var index = oThisController.byId("premfreightstable").getSelectedIndices();

			var apremfreights = oMdlCommon.getProperty("/aPremfreightorders");
			var aTaskDetails = oMdlCommon.getProperty("/aTaskDetails");
			/*selectedRecord.push(apremfreights[index].orderId);
			console.log(selectedRecord);
			if (selectedRecord.length === 0) {
				oThisController.showMessage("Please select the records to perform this action", "E", function () {
					console.log("error");
				});
			} else {*/
			oThisController.confirmUserAction("Do you want to approve the selected order?", "W", function (sAction) {
				if (sAction === "YES") {
					debugger;
					var sUrl = "/lch_services/premiumOrders/completeManagerTask",
						oHeader = {
							"Content-Type": "application/json",
							"Accept": "application/json"
						},
						oPayload = {
							"accountantDto": {
								"carrier": "",
								"comment": "",
								"debitAmount": "",
								"debitBy": "",
								"debitCurrency": "",
								"debitPostStatus": "",
								"debitTo": "",
								"glCode": "",
								"orderId": "",
								"plannerEmail": "",
								"proNumber": "",
								"profitCenter": "",
								"quantity": "",
								"totalCost": "",
								"trailerNumber": ""
							},
							/*"orderId": apremfreights[index].orderId,
							"status": action,
							"taskIdDetails": aTaskDetails[index]*/
							"orderId": "ADHLe21042100007",
							"status": "Approved",
							"taskIdDetails": "5f083c6f-a323-11eb-9cb2-eeee0a9b61db"
						};
					oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
						try {
							if (oXHR && oXHR.responseJSON) {

								console.log(oXHR);
								oThisController.byId("premfreightstable").clearSelection();
								oThisController.fnPremfreightstableManager();
								oMdlCommon.refresh();
								console.log(oMdlCommon);

							}

						} catch (e) {
							// console.log(e);
						}
					}, oPayload);

				} else {
					console.log("cancel");
				}

			});

		}

	});
});