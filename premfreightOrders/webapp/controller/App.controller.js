sap.ui.define([
	"com/incture/lch/premfreightOrders/controller/BaseController",
	"sap/ui/model/Filter",
	"sap/ui/model/FilterOperator"
], function (BaseController, Filter, FilterOperator) {
	"use strict";

	return BaseController.extend("com.incture.lch.premfreightOrders.controller.App", {
		onInit: function () {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
            oRouter.navTo("Orderdetails");
			// global model for handling local data
			var oMdlCommon = this.getModel("mCommon");
			var oDate = new Date();
			oMdlCommon.setProperty("/today", oDate);
			oMdlCommon.setProperty("/todayYear", oDate.getFullYear());
			oMdlCommon.setProperty("/todayMonth", oDate.getMonth());
			oMdlCommon.setProperty("/todayDate", oDate.getDate());
			oMdlCommon.refresh();
			//Initialize App
			this.fnInitializeApp();
			this.pageCount = 0;

		}

	});
});