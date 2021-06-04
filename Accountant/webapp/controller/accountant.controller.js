sap.ui.define([
	"com/incture/lch/Accountant/controller/BaseController",
	"sap/ui/core/mvc/Controller"
], function (BaseController) {
	"use strict";

	return BaseController.extend("com.incture.lch.Accountant.controller.accountant", {

		onInit: function () {
			var oThisController = this,
				oMdlCommon = oThisController.getModel("mCommon");

		},

		fnPremAccDetails: function (oEvent) {
			debugger;
			var oThisController = this,
				oMdlCommon = oThisController.getModel("mCommon"),

				id = oEvent.getSource().getId().split("__item0-__list0-")[1],
				taskList = oMdlCommon.getProperty("/aTaskList"),
				taskId = taskList[id].taskDetailsDto.taskId,
				orderId = taskList[id].premiumFreightDto1.orderId,
				sUrl = "/lch_services/premiumOrders/getPremiumAccountingDetails/",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json",
					"workflowInstanceId": ""
				};
			oMdlCommon.setProperty("/totalCostVisible", true);
			oHeader.workflowInstanceId = taskList[id].taskDetailsDto.workflowInstanceId;
			oMdlCommon.setProperty("/currInstance", taskId);
			oMdlCommon.setProperty("/currOrder", orderId);
			sUrl += oHeader.workflowInstanceId;
			oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {

						oMdlCommon.setProperty("/oPremAccountingDetails", oXHR.responseJSON);
						oMdlCommon.refresh();
						console.log(oMdlCommon);
					}

				} catch (e) {
					// console.log(e);
				}
			});
		},
		
		onSuggest: function () {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				sUrl = "/lch_services/premiumOrders/getAllAccountingDetails",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};
				oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {
							oMdlCommon.setProperty("/aPlant",oXHR.responseJSON);
					}

				} catch (e) {
					// console.log(e);
				}
			});
		},
		fnUpdatePremAccDetails: function () {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				sUrl = "/lch_services/premiumOrders/updatePremiumAccountingDetails";
			var debitAmt = oMdlCommon.getProperty("/oPremAccountingDetails/debitAmount"),
				debitBy = oMdlCommon.getProperty("/oPremAccountingDetails/debitBy"),
				debitCurrency = oMdlCommon.getProperty("/oPremAccountingDetails/debitCurrency"),
				debitTo = oMdlCommon.getProperty("/oPremAccountingDetails/debitTo");
			if (!debitAmt || !debitBy || !debitCurrency || !debitTo) {
				oThisController.showMessage("Please enter all the mandatory fields", "E", function () {
					//console.log("success");
				});
			}
			var oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				},
				oPayload = {
					"comment": oMdlCommon.getProperty("/oPremAccountingDetails/comment"),
					"debitAmount": oMdlCommon.getProperty("/oPremAccountingDetails/debitAmount"),
					"debitBy": oMdlCommon.getProperty("/oPremAccountingDetails/debitBy"),
					"debitCurrency": oMdlCommon.getProperty("/oPremAccountingDetails/debitCurrency"),
					"debitPostStatus": oMdlCommon.getProperty("/oPremAccountingDetails/debitPostStatus"),
					"debitTo": oMdlCommon.getProperty("/oPremAccountingDetails/debitTo"),
					"glCode": oMdlCommon.getProperty("/oPremAccountingDetails/glCode"),
					"orderId": oMdlCommon.getProperty("/oPremAccountingDetails/orderId"),
					"plannerEmail": oMdlCommon.getProperty("/oPremAccountingDetails/plannerEmail"),
					"proNumber": oMdlCommon.getProperty("/oPremAccountingDetails/proNumber"),
					"profitCenter": oMdlCommon.getProperty("/oPremAccountingDetails/profitCenter"),
					"trailerNumber": oMdlCommon.getProperty("/oPremAccountingDetails/trailerNumber"),
					"carrier": oMdlCommon.getProperty("/oPremAccountingDetails/carrier"),
					"totalCost": oMdlCommon.getProperty("/oPremAccountingDetails/totalCost"),
					"quantity": oMdlCommon.getProperty("/oPremAccountingDetails/quantity")
				};
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {

						oThisController.showMessage("Order details updated successfully!", "I", function () {
							//console.log("success");
						});
					}

				} catch (e) {
					// console.log(e);
				}
			}, oPayload);
		},
		fnCompleteAccountantTask: function (action) {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				sUrl = "/lch_services/premiumOrders/completeAccountantTask";

			var oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				},
				oPayload = {
					"accountantDto": {
						"comment": oMdlCommon.getProperty("/oPremAccountingDetails/comment"),
						"debitAmount": oMdlCommon.getProperty("/oPremAccountingDetails/debitAmount"),
						"debitBy": oMdlCommon.getProperty("/oPremAccountingDetails/debitBy"),
						"debitCurrency": oMdlCommon.getProperty("/oPremAccountingDetails/debitCurrency"),
						"debitPostStatus": oMdlCommon.getProperty("/oPremAccountingDetails/debitPostStatus"),
						"debitTo": oMdlCommon.getProperty("/oPremAccountingDetails/debitTo"),
						"glCode": oMdlCommon.getProperty("/oPremAccountingDetails/glCode"),
						"orderId": oMdlCommon.getProperty("/oPremAccountingDetails/orderId"),
						"plannerEmail": oMdlCommon.getProperty("/oPremAccountingDetails/plannerEmail"),
						"proNumber": oMdlCommon.getProperty("/oPremAccountingDetails/proNumber"),
						"profitCenter": oMdlCommon.getProperty("/oPremAccountingDetails/profitCenter"),
						"trailerNumber": oMdlCommon.getProperty("/oPremAccountingDetails/trailerNumber"),
						"carrier": oMdlCommon.getProperty("/oPremAccountingDetails/carrier"),
						"totalCost": oMdlCommon.getProperty("/oPremAccountingDetails/totalCost"),
						"quantity": oMdlCommon.getProperty("/oPremAccountingDetails/quantity")
					},
					"orderId": oMdlCommon.getProperty("/oPremAccountingDetails/orderId"),
					"status": action,
					"taskIdDetails": oMdlCommon.getProperty("/currInstance")
				};
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {
						if (action === "Approved") {
							oThisController.showMessage("Order approved successfully!", "I", function () {
								//console.log("success");
							});
							oMdlCommon.setProperty("/oPremAccountingDetails", {});
							oMdlCommon.setProperty("/totalCostVisible", false);
							oMdlCommon.refresh();
							oThisController.fnTaskDetails();
						} else if (action === "Rejected") {
							oThisController.showMessage("Order rejected successfully!", "I", function () {
								//console.log("success");
							});
							oMdlCommon.setProperty("/oPremAccountingDetails", {});
							oMdlCommon.setProperty("/totalCostVisible", false);
							oMdlCommon.refresh();
							oThisController.fnTaskDetails();
						}
					}

				} catch (e) {
					// console.log(e);
				}
			}, oPayload);
		}

	});

});