sap.ui.define([
	"com/incture/lch/premfreightOrders/controller/BaseController",
	"sap/m/Token",
	"sap/ui/unified/Calendar",
	"sap/m/Dialog", "sap/m/DialogType", "sap/m/Label", "sap/m/Button", "sap/m/ButtonType", "sap/m/TextArea",
	"com/incture/lch/premfreightOrders/libs/xl",
	"com/incture/lch/premfreightOrders/libs/xls"
], function (BaseController, Token, Calendar, Dialog, DialogType, Label, Button, ButtonType, TextArea, xljs, xlsjs) {
	"use strict";

	return BaseController.extend("com.incture.lch.premfreightOrders.controller.Orderdetails", {

		onInit: function () {
			var oThisController = this;
			var oMdlCommon = oThisController.getModel("mCommon");
			oThisController.fnGetcarrierdetails();

		},

		onForwardtoManager: function () {

			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				pageNo = oMdlCommon.getProperty("/pageNo");
			var index = oThisController.byId("premfreightstable").getSelectedIndices();
			var apremfreights = oMdlCommon.getProperty("/aPremfreightorders");
			if (index.length === 0) {
				oThisController.showMessage("Please select the records to perform this action", "E", function () {
					//console.log("error");
				});
				return;
			}
			var oPayload = {
				"premiumRequestDtoList": [{
					"orderId": apremfreights[index].orderId

				}],
				"userDetailInfo": {
					"userId": oMdlCommon.getProperty("/userDetails/id")
				}
			};
			if (apremfreights[index].charge === 0) {
				oThisController.showMessage("No charge set for the selected record", "E", function () {

				});
				return;
			}

			var sUrl = "/lch_services/premiumOrders/forwardToApprover",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};

			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR) {

						oThisController.byId("premfreightstable").clearSelection();
						oThisController.fnPremfreightstable(pageNo);
						oThisController.showMessage("Successfuly forwarded to Manager", "I", function () {
							//success
						});
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

		onReject: function () {
			debugger;
			var oThisController = this;
			var oMdlCommon = oThisController.getModel("mCommon"),
				pageNo = oMdlCommon.getProperty("/pageNo");
			var index = oThisController.byId("premfreightstable").getSelectedIndices(),
				selectedRecord = [];
			var apremfreights = oMdlCommon.getProperty("/aPremfreightorders");
			if (index.length === 0) {
				oThisController.showMessage("Please select the records to perform this action", "E", function () {

				});
			} else {
				selectedRecord.push(apremfreights[index].orderId);
				if (!this.oSubmitDialog) {
					this.oSubmitDialog = new Dialog({
						type: DialogType.Message,
						icon: "sap-icon://message-information",
						title: "Confirm",
						content: [
							new Label({
								text: "Do you want to perform the selected action ?",
								labelFor: "commentsPlanner"
							}),
							new TextArea("commentsPlanner", {
								width: "100%",
								placeholder: "Enter comments here"
							})
						],
						endButton: new Button({
							type: ButtonType.Emphasized,
							text: "Yes",
							press: function () {
								var sText = sap.ui.getCore().byId("commentsPlanner").getValue();
								oMdlCommon.setProperty("/commentsPlanner", sText);
								var sUrl = "/lch_services/premiumOrders/rejectPremiumOrder",
									oHeader = {
										"Content-Type": "application/json",
										"Accept": "application/json"
									},
									oPayload = {
										"adhocorderIds": selectedRecord,
										"comment": sText
									};
								oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
									try {
										if (oXHR && oXHR.responseJSON) {

											oThisController.byId("premfreightstable").clearSelection();
											oThisController.fnPremfreightstable(pageNo);

										}

										oMdlCommon.refresh();

									} catch (e) {
										// console.log(e);
									}
								}, oPayload);

								this.oSubmitDialog.close();
							}.bind(this)
						}),
						beginButton: new Button({
							text: "Cancel",
							press: function () {
								this.oSubmitDialog.close();
							}.bind(this)
						})
					});
				}
				sap.ui.getCore().byId("commentsPlanner").setValue("");
				this.oSubmitDialog.open();
			}
		},
		onForwardtoCA: function () {

			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				pageNo = oMdlCommon.getProperty("/pageNo");
			var index = oThisController.byId("premfreightstable").getSelectedIndices(),
				selectedRecord = [];
			var apremfreights = oMdlCommon.getProperty("/aPremfreightorders");
			if (index.length === 0) {
				oThisController.showMessage("Please select the records to perform this action", "E", function () {

				});
				return;
			}
			selectedRecord.push({
				"orderId": apremfreights[index].orderId,
				"bpNumber": apremfreights[index].selectedCarrier,
				"carrierMode": apremfreights[index].selectedCarriermode,
				"carrierDetails": "",
				"carrierScac": "",
				"charge": ""
			});
			if (selectedRecord[0].bpNumber === "" || selectedRecord[0].carrierMode === "") {
				oThisController.showMessage("Please choose the carrier details of the selected fields", "E", function () {

				});
				return;
			}
			if (apremfreights[index].status === "Pending with Carrier Admin") {
				oThisController.showMessage("Selected Record already forwarded to CA", "E", function () {

				});
				return;
			}
			var sUrl = "/lch_services/premiumOrders/setCarrierDetails",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				},
				oPayload = selectedRecord;
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {

						oThisController.byId("premfreightstable").clearSelection();
						oThisController.fnPremfreightstable(pageNo);
						oThisController.showMessage("Successfuly forwarded to Carrier Admin", "I", function () {
							//success
						});
					}

					oMdlCommon.refresh();

				} catch (e) {
					// console.log(e);
				}
			}, oPayload);

		},
		onForwardtoPlanner: function () {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				pageNo = oMdlCommon.getProperty("/pageNo");
			var index = oThisController.byId("premfreightstable").getSelectedIndices(),
				selectedRecord = [];
			var apremfreights = oMdlCommon.getProperty("/aPremfreightorders");
			if (index.length === 0) {
				oThisController.showMessage("Please select the records to perform this action", "E", function () {

				});
				return;
			}
			selectedRecord.push({
				"orderId": apremfreights[index].orderId,
				"bpNumber": apremfreights[index].bpNumber,
				"carrierMode": apremfreights[index].carrierMode,
				"carrierDetails": apremfreights[index].carrierDetails,
				"carrierScac": apremfreights[index].carrierScac,
				"charge": apremfreights[index].charge
			});

			
			if (selectedRecord[0].charge === 0) {
				oThisController.showMessage("Please enter a valid cost for the selected order", "E", function () {

				});
				return;
			}
			var sUrl = "/lch_services/premiumOrders/setCharge",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				},
				oPayload = selectedRecord;
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR) {

						oThisController.byId("premfreightstable").clearSelection();
						oThisController.fnPremfreightstable(pageNo);
						oThisController.showMessage("Successfuly forwarded to Planner", "I", function () {
							//success
						});
					}
					oMdlCommon.refresh();

				} catch (e) {
					// console.log(e);
				}
			}, oPayload);

		},

		onApprove: function (action) {
			debugger;
			var oThisController = this;
			var oMdlCommon = oThisController.getModel("mCommon"),
				pageNo = oMdlCommon.getProperty("/pageNo");
			var index = oThisController.byId("premfreightstable").getSelectedIndices();
			var apremfreights = oMdlCommon.getProperty("/aPremfreightorders");
			var aTaskDetails = oMdlCommon.getProperty("/aTaskDetails");

			if (index.length === 0) {
				oThisController.showMessage("Please select the records to perform this action", "E", function () {

				});

			} else {
				oThisController.confirmUserAction("Do you want to perform the selected action ?", "I", function (sAction) {
					if (sAction === "YES") {

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
								"orderId": apremfreights[index].orderId,
								"status": action,
								"taskIdDetails": aTaskDetails[index].taskId

							};
						oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
							try {
								if (oXHR && oXHR.responseJSON) {

									oThisController.byId("premfreightstable").clearSelection();

									if (action === "Approved") {
										oThisController.showMessage("Order approved successfully!", "I", function () {
											//console.log("success");
										});

									} else if (action === "Rejected") {
										oThisController.showMessage("Order rejected successfully!", "I", function () {
											//console.log("success");
										});

									}
									oThisController.fnPremfreightstable(pageNo);
									oMdlCommon.refresh();

								}

							} catch (e) {
								// console.log(e);
							}
						}, oPayload);

					} else {
						// console.log(e);
					}

				});
			}
		}
	});
});