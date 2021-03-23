sap.ui.define([
	"com/incture/lch/Adhoc/controller/BaseController"
], function (BaseController) {
	"use strict";

	return BaseController.extend("com.incture.lch.Adhoc.controller.App", {
		onInit: function () {
			/*var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
            oRouter.navTo("Order");*/
			this.fnInitializeApp();
			this.fnGetLookUpCountry();
		},

		//side content collapse and expand
		onSideNavigation: function (oEvent) {
			var oSideNavigation = this.byId("idLch_adhoc_ToolPage");
			var bExpanded = oSideNavigation.getSideExpanded();
			oSideNavigation.setSideExpanded(!bExpanded);
		},

		//Function to Get Country Url and Setting it to mPartModel
		fnGetLookUpCountry: function () {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var sUrl = "/lch_services/adhocorders/getAllCountries",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};

			try {
				oMdlCommon.setProperty("/aCountries", []);
				oMdlCommon.refresh();
			} catch (e) {
				//catch
			}

			oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON && oXHR.responseJSON instanceof Array) {
						oMdlCommon.setProperty("/aCountries", oXHR.responseJSON);
					}
					oMdlCommon.refresh();
				} catch (e) {
					// console.log(e);
				}
			});
		}
	});
});