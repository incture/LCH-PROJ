sap.ui.define([
	"com/incture/lch/adhocApproval/controller/BaseController"
], function (BaseController) {
	"use strict";

	return BaseController.extend("com.incture.lch.adhocApproval.controller.App", {
		onInit: function () {
			this.fnInitializeApp();
		}
		
	});
});