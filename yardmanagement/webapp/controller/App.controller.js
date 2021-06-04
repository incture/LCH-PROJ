sap.ui.define([
	"com/incture/lch/yardmanagement/controller/BaseController"
], function (BaseController) {
	"use strict";

	return BaseController.extend("com.incture.lch.yardmanagement.controller.App", {
		onInit: function () {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("TrailerList");
			this.fnInitializeApp();
		},

		onSideNavigation: function (oEvent) {
			var oSideNavigation = this.byId("idLch_YardMgmt_toolpage");
			var bExpanded = oSideNavigation.getSideExpanded();
			oSideNavigation.setSideExpanded(!bExpanded);
		}
	});
});