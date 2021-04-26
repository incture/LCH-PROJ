sap.ui.define([
	"com/incture/lch/Accountant/controller/BaseController",
	"sap/ui/core/mvc/Controller"
], function (BaseController) {
	"use strict";

	return BaseController.extend("com.incture.lch.Accountant.controller.App", {
		onInit: function () {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			this.fnInitializeApp();
			oThisController.fnTaskDetails();
		},

		fnTaskDetails: function () {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				sUrl = "/lch_services/premiumOrders/getallTaskInstancesId/";

			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json",
				"user": ""
			};
			/*	oHeader.user = oThisController._oCommon.userDetails.id;*/
			oHeader.user = "P000331";
			sUrl += oHeader.user;
			oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {

						oMdlCommon.setProperty("/aTaskList", oXHR.responseJSON);
						oMdlCommon.refresh();
						console.log(oMdlCommon);
					}

				} catch (e) {
					// console.log(e);
				}
			});
		},
		fnPremAccDetails: function (oEvent) {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				sUrl = "/lch_services/premiumOrders/getPremiumAccountingDetails/";

			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json",
				"workflowInstanceId": ""
			};

			oHeader.workflowInstanceId = oEvent.getSource().getText();
			oMdlCommon.setProperty("/currInstance", oEvent.getSource().getText());
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
		fnUpdatePremAccDetails: function () {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				sUrl = "/lch_services/premiumOrders/updatePremiumAccountingDetails";

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
						"orderId": oMdlCommon.getProperty("/oPremAccountingDetails/orderId"),
						"plannerEmail": oMdlCommon.getProperty("/oPremAccountingDetails/plannerEmail"),
						"proNumber": oMdlCommon.getProperty("/oPremAccountingDetails/proNumber"),
						"profitCenter": oMdlCommon.getProperty("/oPremAccountingDetails/profitCenter"),
						"trailerNumber": oMdlCommon.getProperty("/oPremAccountingDetails/trailerNumber"),
						"carrier": oMdlCommon.getProperty("/oPremAccountingDetails/carrier"),
						"totalCost": oMdlCommon.getProperty("/oPremAccountingDetails/totalCost"),
						"quantity": oMdlCommon.getProperty("/oPremAccountingDetails/quantity")
					},
					"orderIdDetails": oMdlCommon.getProperty("/oPremAccountingDetails/orderId"),
					"status": action,
					"taskIdDetails": oMdlCommon.getProperty("/currInstance")
				};
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {

						console.log(oXHR.responseJSON);
					}

				} catch (e) {
					// console.log(e);
				}
			}, oPayload);
		}
	});
});