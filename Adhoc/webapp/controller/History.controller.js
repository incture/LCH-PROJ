sap.ui.define([
	"com/incture/lch/Adhoc/controller/BaseController"
], function (BaseController) {
	"use strict";

	return BaseController.extend("com.incture.lch.Adhoc.controller.History", {

		onInit: function () {
			let oThisController = this;
			this._oRouter = this.getRouter();
			this._oRouter.attachRoutePatternMatched(function (oEvent) {
				if (oEvent.getParameter("name") === "History") {
					oThisController.fnGetHistory(true);
					var oMdlCommon = this.getParentModel("mCommon");
					oMdlCommon.setProperty("/selNavigationKey", "kHistory");
					oMdlCommon.setProperty("/visible/bClearAll", false);
					oMdlCommon.setProperty("/visible/bSubmit", false);
					oMdlCommon.refresh();
				}
			}, this);
		},

		onPressAllOrders: function (oEvent) {
			let oThisController = this;
			let oMdlCommon = this.getParentModel("mCommon");
			oMdlCommon.setProperty("/aHistory", oMdlCommon.getProperty("/aAllOrders"));
			oMdlCommon.setProperty("/iLenHistory", oMdlCommon.getProperty("/iLenAllOrders"));
			console.log(oMdlCommon.getProperty("/aHistory"));

		},
		onPressNext24Hours: function (oEvent) {
			let oThisController = this;
			let oMdlCommon = this.getParentModel("mCommon");
			oMdlCommon.setProperty("/aHistory", oMdlCommon.getProperty("/a24Hours"));
			oMdlCommon.setProperty("/iLenHistory", oMdlCommon.getProperty("/iLen24Hours"));
			console.log(oMdlCommon.getProperty("/aHistory"));

		},

		onPressNext72Hours: function (oEvent) {
			let oThisController = this;
			let oMdlCommon = this.getParentModel("mCommon");
			oMdlCommon.setProperty("/aHistory", oMdlCommon.getProperty("/a72Hours"));
			oMdlCommon.setProperty("/iLenHistory", oMdlCommon.getProperty("/iLen72Hours"));
			console.log(oMdlCommon.getProperty("/aHistory"));

		},

		onPressDrafts: function (oEvent) {
			let oThisController = this;
			let oMdlCommon = this.getParentModel("mCommon");
			oMdlCommon.setProperty("/aHistory", oMdlCommon.getProperty("/aDrafts"));
			oMdlCommon.setProperty("/iLenHistory", oMdlCommon.getProperty("/iLenDrafts"));
			console.log(oMdlCommon.getProperty("/aHistory"));

		},

		onSearchGroup: function (oEvent) {
			var sQuery = oEvent.getParameter("newValue");
			var oTable = oEvent.getSource().getParent().getParent();
			var oBinding = oTable.getBinding("rows");
			var aPropertyFilterSettings = [{
				propertyName: "adhocOrderId",
				filterOperator: "Contains",
				propertyValue: sQuery
			}];
			this.fnApplyCustomerFilter(aPropertyFilterSettings, oBinding, false);
		},


		onCancelFuCutomize: function (oEvent) {
			let oThisController = this;
			oThisController.oDlgCustomize.close();

		}

	});

});