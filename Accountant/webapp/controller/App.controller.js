sap.ui.define([
	"com/incture/lch/Accountant/controller/BaseController",
	"sap/ui/core/mvc/Controller"
], function (BaseController) {
	"use strict";

	return BaseController.extend("com.incture.lch.Accountant.controller.App", {
		onInit: function () {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
		
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("accountant");
			this.fnInitializeApp();
		}

	
	
	});
});