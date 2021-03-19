sap.ui.define([
	"com/incture/lch/Adhoc/controller/BaseController",
	"sap/ui/model/Filter",
	"sap/ui/model/FilterOperator",

], function (BaseController, Filter, FilterOperator) {
	"use strict";

	return BaseController.extend("com.incture.lch.Adhoc.controller.Order", {

		/**
		 * Called when a controller is instantiated and its View controls (if available) are already created.
		 * Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
		 * @memberOf com.incture.lch.Adhoc.view.Order
		 */
		onInit: function () {
			this._oRouter = this.getRouter();
			this._oRouter.attachRoutePatternMatched(function (oEvent) {
				if (oEvent.getParameter("name") === "Order") {
					var oMdlCommon = this.getParentModel("mCommon");
					if (oEvent.getParameter("arguments") && oEvent.getParameter("arguments").accessMode === "view") {
						oMdlCommon.setProperty("/selNavigationKey", "kHistory");
						oMdlCommon.setProperty("/bIsEditable", false);
					} else if (oEvent.getParameter("arguments") && oEvent.getParameter("arguments").accessMode === "edit") {
						oMdlCommon.setProperty("/selNavigationKey", "kHistory");
						oMdlCommon.setProperty("/bIsEditable", true);
						oMdlCommon.setProperty("/visible/bClearAll", true);
						oMdlCommon.setProperty("/visible/bSubmit", false);
						oMdlCommon.setProperty("/visible/bNext", true);
					} else {
						this.onClearAll();
						oMdlCommon.setProperty("/selNavigationKey", "kOrder");
						oMdlCommon.setProperty("/bIsEditable", true);
						oMdlCommon.setProperty("/visible/bClearAll", true);
						oMdlCommon.setProperty("/visible/bSubmit", false);
						oMdlCommon.setProperty("/visible/bNext", true);
					}
					oMdlCommon.refresh();
				}
			}, this);
		},

		handleButtonsVisibility: function () {
			let oMdlCommon = this.getParentModel("mCommon");
			this._oWizard = this.byId("idOrder");
			console.log(this._oWizard.getProgress());
			switch (this._oWizard.getProgress()) {
			case 1:
				oMdlCommon.setProperty("/visible/bNext", true);
				oMdlCommon.setProperty("/visible/bBack", false);
				oMdlCommon.setProperty("/visible/bSubmit", false);
				break;
			case 2:
				oMdlCommon.setProperty("/visible/bBack", true);
				oMdlCommon.setProperty("/visible/bSubmit", false);
				oMdlCommon.setProperty("/visible/bNext", true);
				break;
			case 3:
				oMdlCommon.setProperty("/visible/bNext", false);
				oMdlCommon.setProperty("/visible/bSubmit", true);
				break;
			default:
				break;
			}
			oMdlCommon.refresh();
		},

		onDialogNextButton: function () {
			this._oWizard = this.byId("idOrder");
			if (this._oWizard.getProgressStep().getValidated()) {
				this._oWizard.nextStep();
			}
			this.handleButtonsVisibility();
		},

		onDialogBackButton: function () {
			this._oWizard = this.byId("idOrder");
			this._oWizard.previousStep();
			this.handleButtonsVisibility();

		},


		onVinChange: function (oEvent) {
			var bState = oEvent.getSource().getState();
			var oMdlCommon = this.getParentModel("mCommon");
			oMdlCommon.setProperty("/newFU/vinNumber", "");
			oMdlCommon.setProperty("/enable/bVINNumber", (bState) ? true : false);
			oMdlCommon.refresh();
		},

		onPartChange: function (oEvent) {
			debugger;
			let oMdlCommon = this.getParentModel("mCommon");
			let oInpPart = oEvent.getSource();
			let sKey = oInpPart.getSelectedKey();

			if (sKey) {
				console.log(sKey);
				let oItem = oInpPart.getSuggestionItemByKey(sKey);
				let sPath = oItem.getBindingContext("mCommon").getPath();
				let oCurData = oMdlCommon.getProperty(sPath);
				console.log(oCurData);
				oMdlCommon.setProperty("/enable/bPartDesc", false);
				if (oCurData.Maktx) {
					oMdlCommon.setProperty("/newFU/partDescription", oCurData.Maktx);
					oMdlCommon.setProperty("/valState/partDescription", "None");
				}
				if (oCurData.Hazmat) {
					oMdlCommon.setProperty("/newFU/isHazmat", true);
					oMdlCommon.setProperty("/newFU/hazmatNumber", oCurData.Hazmat);
				} else {
					oMdlCommon.setProperty("/newFU/isHazmat", false);
					oMdlCommon.setProperty("/newFU/hazmatNumber", "");
				}
			} else {
				oMdlCommon.setProperty("/newFU/partDescription", "");
				oMdlCommon.setProperty("/enable/bPartDesc", true);
				oMdlCommon.setProperty("/newFU/isHazmat", false);
				oMdlCommon.setProperty("/newFU/hazmatNumber", "");
			}
			oMdlCommon.refresh();
		},

		onSuggestShipName: function (oEvent) {
			var oMdlCommon = this.getParentModel("mCommon");

			if (oMdlCommon.getProperty("/bNewShip")) {
				return;
			}

			var sShipperName = oEvent.getParameter("suggestValue");
			this.fnSetLocations(sShipperName, "Ship");

		},

		onSuggestDestName: function (oEvent) {
			var oMdlCommon = this.getParentModel("mCommon");

			if (oMdlCommon.getProperty("/bNewDest")) {
				return;
			}

			var sShipperName = oEvent.getParameter("suggestValue");
			this.fnSetLocations(sShipperName, "Dest");
		},

		onNewShipper: function (oEvent) {
			var oMdlCommon = this.getParentModel("mCommon");
			var bRequired = oMdlCommon.getProperty("/newFU/bRequiredOrigni");
			oMdlCommon.setProperty("/newFU/shipperName", "");
			oMdlCommon.setProperty("/newFU/shipperNameDesc", "");
			oMdlCommon.setProperty("/newFU/shipperNameFreeText", "");
			oMdlCommon.setProperty("/newFU/originAddress", "");
			oMdlCommon.setProperty("/newFU/originCity", "");
			oMdlCommon.setProperty("/newFU/originState", "");
			oMdlCommon.setProperty("/newFU/originZip", "");
			oMdlCommon.setProperty("/newFU/originCountry", "");
			oMdlCommon.setProperty("/valState/sOriginAddress", "None");
			oMdlCommon.setProperty("/valState/sOriginCity", "None");
			oMdlCommon.setProperty("/valState/sOriginState", "None");
			oMdlCommon.setProperty("/valState/sOriginZip", "None");
			oMdlCommon.setProperty("/valState/sOriginCountry", "None");
			if (!bRequired) {
				oMdlCommon.setProperty("/newFU/bRequiredOrigni", true);
			} else {
				oMdlCommon.setProperty("/newFU/bRequiredOrigni", false);
			}
			oMdlCommon.setProperty("/valState/shipperName", "None");

			if (oMdlCommon.getProperty("/bNewShip")) {
				oMdlCommon.setProperty("/bNewShip", false);
				oMdlCommon.setProperty("/sIconShip", "sap-icon://write-new");
			} else {
				oMdlCommon.setProperty("/bNewShip", true);
				oMdlCommon.setProperty("/sIconShip", "sap-icon://filter-facets");
				oMdlCommon.setProperty("/aLocShippers", []);
			}
			oMdlCommon.refresh();
		},

		onNewDestination: function (oEvent) {
			var oMdlCommon = this.getParentModel("mCommon");
			var bRequired = oMdlCommon.getProperty("/newFU/bRequiredDest");
			oMdlCommon.setProperty("/newFU/destinationName", "");
			oMdlCommon.setProperty("/newFU/destinationNameDesc", "");
			oMdlCommon.setProperty("/newFU/destinationNameFreeText", "");
			oMdlCommon.setProperty("/newFU/destinationAddress", "");
			oMdlCommon.setProperty("/newFU/destinationCity", "");
			oMdlCommon.setProperty("/newFU/destinationState", "");
			oMdlCommon.setProperty("/newFU/destinationZip", "");
			oMdlCommon.setProperty("/newFU/destinationCountry", "");
			oMdlCommon.setProperty("/valState/sDestinationAddress", "None");
			oMdlCommon.setProperty("/valState/sDestinationCity", "None");
			oMdlCommon.setProperty("/valState/sDestinationState", "None");
			oMdlCommon.setProperty("/valState/sDestinationZip", "None");
			oMdlCommon.setProperty("/valState/sDestinationCountry", "None");
			if (!bRequired) {
				oMdlCommon.setProperty("/newFU/bRequiredDest", true);
			} else {
				oMdlCommon.setProperty("/newFU/bRequiredDest", false);
			}
			oMdlCommon.setProperty("/valState/shipperName", "None");

			oMdlCommon.setProperty("/valState/destinationName", "None");

			if (oMdlCommon.getProperty("/bNewDest")) {
				oMdlCommon.setProperty("/bNewDest", false);
				oMdlCommon.setProperty("/sIconDest", "sap-icon://write-new");
			} else {
				oMdlCommon.setProperty("/bNewDest", true);
				oMdlCommon.setProperty("/sIconDest", "sap-icon://filter-facets");
				oMdlCommon.setProperty("/aLocReceivers", []);
			}
			oMdlCommon.refresh();
		},

		onChngShipLoc: function (oEvent) {
			debugger;
			var oMdlCommon = this.getParentModel("mCommon");
			if (oMdlCommon.getProperty("/bNewShip")) {
				console.log(oEvent.getParameter("newValue"));
				oMdlCommon.setProperty("/newFU/shipperName", oEvent.getParameter("newValue"));
				oMdlCommon.setProperty("/newFU/shipperNameFreeText", oEvent.getParameter("newValue"));
				oMdlCommon.setProperty("/newFU/shipperNameDesc", oEvent.getParameter("newValue"));
				return;
			}

			var oIBox = oEvent.getSource();
			console.log(oIBox);
			var sKey = oIBox.getSelectedItem();
			console.log(sKey);
			if (sKey && sKey !== "") {
				sKey = sKey.split("-")[2];
				var oSelData = oMdlCommon.getProperty("/aLocShippers/" + sKey);
				if (oMdlCommon.getProperty("/valState/shipperName") === "Error") {
					oMdlCommon.setProperty("/valState/shipperName", "None");
				}
				oMdlCommon.setProperty("/newFU/originAddress", oSelData.shipperAddress);
				oMdlCommon.setProperty("/newFU/originCity", oSelData.shipperCity);
				oMdlCommon.setProperty("/newFU/originState", oSelData.shipperState);
				oMdlCommon.setProperty("/newFU/originZip", oSelData.shipperZip);
				oMdlCommon.setProperty("/newFU/originCountry", oSelData.shipperCountry);
			} else {
				oMdlCommon.setProperty("/newFU/originAddress", "");
				oMdlCommon.setProperty("/newFU/originCity", "");
				oMdlCommon.setProperty("/newFU/originState", "");
				oMdlCommon.setProperty("/newFU/originZip", "");
				oMdlCommon.setProperty("/newFU/originCountry", "");
			}
			oMdlCommon.refresh();
		},

		onChngDestLoc: function (oEvent) {
			var oMdlCommon = this.getParentModel("mCommon");

			if (oMdlCommon.getProperty("/bNewDest")) {
				oMdlCommon.setProperty("/newFU/destinationName", oEvent.getParameter("newValue"));
				oMdlCommon.setProperty("/newFU/destinationNameFreeText", oEvent.getParameter("newValue"));
				oMdlCommon.setProperty("/newFU/destinationNameDesc", oEvent.getParameter("newValue"));
				return;
			}

			var oIBox = oEvent.getSource();
			var sKey = oIBox.getSelectedItem();

			if (sKey && sKey !== "") {
				sKey = sKey.split("-")[2];
				if (oMdlCommon.getProperty("/valState/destinationName") === "Error") {
					oMdlCommon.setProperty("/valState/destinationName", "None");
				}
				var oSelData = oMdlCommon.getProperty("/aLocReceivers/" + sKey);
				console.log(oSelData);
				oMdlCommon.setProperty("/newFU/destinationAddress", oSelData.shipperAddress);
				oMdlCommon.setProperty("/newFU/destinationCity", oSelData.shipperCity);
				oMdlCommon.setProperty("/newFU/destinationState", oSelData.shipperState);
				oMdlCommon.setProperty("/newFU/destinationZip", oSelData.shipperZip);
				oMdlCommon.setProperty("/newFU/destinationCountry", oSelData.shipperCountry);
			} else {
				oMdlCommon.setProperty("/newFU/destinationAddress", "");
				oMdlCommon.setProperty("/newFU/destinationCity", "");
				oMdlCommon.setProperty("/newFU/destinationState", "");
				oMdlCommon.setProperty("/newFU/destinationZip", "");
				oMdlCommon.setProperty("/newFU/destinationCountry", "");
			}
			oMdlCommon.refresh();
		},

		onToggleInternational: function (oEvent) {
			let oThisController = this;

			if (!oThisController.oDlgInternational) {
				oThisController.oDlgInternational = sap.ui.xmlfragment("com.incture.lch.Adhoc.fragment.International", oThisController);
				oThisController.getView().addDependent(oThisController.oDlgInternational);
			}

			if (oEvent.getParameter("state")) {
				oThisController.oDlgInternational.open();
			}
		},

		onAfterCloseDlgInt: function (oEvent) {
			// this.oDlgInternational.close();
		},

		onCloseDlgInt: function (oEvent) {
			let oMdlCommon = this.getParentModel("mCommon");
			let oData = $.extend(true, {}, oMdlCommon.getProperty("/newFU"));
			if (!oData.countryOrigin) {
				oMdlCommon.setProperty("/valState/countryOrigin", "Error");
			} else {
				oMdlCommon.setProperty("/valState/countryOrigin", "None");
				this.oDlgInternational.close();
			}
		},

		onEscDlgInt: function (oPromise) {
			let oThisController = this;
			let oMdlCommon = this.getParentModel("mCommon");
			oThisController.confirmUserAction("Do you want to discard the changes?", "W", function (sAction) {
				if (sAction === "YES") {
					oPromise.resolve();
					oMdlCommon.setProperty("/newFU/isInternational", false);
					oMdlCommon.setProperty("/newFU/value", "");
					oMdlCommon.setProperty("/newFU/currency", "");
					oMdlCommon.setProperty("/newFU/countryOrigin", "");
				} else {
					oPromise.reject();
				}
				oMdlCommon.refresh();
			});
		},

		onToggleHazmat: function (oEvent) {
			let oThisController = this;

			/*if (!oThisController.oDlgHazmat) {
				oThisController.oDlgHazmat = sap.ui.xmlfragment("com.incture.lch.Adhoc.fragment.Hazmat", oThisController);
				oThisController.getView().addDependent(oThisController.oDlgHazmat);
			}

			if (oEvent.getParameter("state")) {
				oThisController.oDlgHazmat.open();
			}*/
		},

		onCloseDlgHazmat: function (oEvent) {
			this.oDlgHazmat.close();
		},

		onEscDlgHazmat: function (oPromise) {

		},

		onToggleTruck: function (oEvent) {
			let oThisController = this;
			let oMdlCommon = this.getParentModel("mCommon");
			if (!oThisController.oDlgTruck) {
				oThisController.oDlgTruck = sap.ui.xmlfragment("com.incture.lch.Adhoc.fragment.Truck", oThisController);
				oThisController.getView().addDependent(oThisController.oDlgTruck);
			}

			oMdlCommon.setProperty("/enable/bVINNumber", oEvent.getParameter("state"));

			if (oEvent.getParameter("state")) {
				oThisController.oDlgTruck.open();
			} else {
				oMdlCommon.setProperty("/newFU/vinNumber", "");
			}
			oMdlCommon.refresh();
		},

		onCloseDlgTruck: function (oEvent) {
			this.oDlgTruck.close();
		},

		onEscDlgTruck: function (oPromise) {

		},

		onToggleChargeBack: function (oEvent) {
			let oThisController = this;

			if (!oThisController.oDlgChargeBack) {
				oThisController.oDlgChargeBack = sap.ui.xmlfragment("com.incture.lch.Adhoc.fragment.ChargeBack", oThisController);
				oThisController.getView().addDependent(oThisController.oDlgChargeBack);
			}

			if (oEvent.getParameter("state")) {
				oThisController.oDlgChargeBack.open();
			}
		},

		onCloseDlgChargeBack: function (oEvent) {
			this.oDlgChargeBack.close();
		},

		onEscDlgChargeBack: function (oPromise) {

		},

		onViewInt: function (oEvent) {
			let oThisController = this;

			if (!oThisController.oDlgInternational) {
				oThisController.oDlgInternational = sap.ui.xmlfragment("com.incture.lch.Adhoc.fragment.International", oThisController);
				oThisController.getView().addDependent(oThisController.oDlgInternational);
			}

			oThisController.oDlgInternational.open();
		},

		onViewHazmat: function (oEvent) {
			let oThisController = this;

			if (!oThisController.oDlgHazmat) {
				oThisController.oDlgHazmat = sap.ui.xmlfragment("com.incture.lch.Adhoc.fragment.Hazmat", oThisController);
				oThisController.getView().addDependent(oThisController.oDlgHazmat);
			}

			oThisController.oDlgHazmat.open();
		},

		onViewTruck: function (oEvent) {
			let oThisController = this;

			if (!oThisController.oDlgTruck) {
				oThisController.oDlgTruck = sap.ui.xmlfragment("com.incture.lch.Adhoc.fragment.Truck", oThisController);
				oThisController.getView().addDependent(oThisController.oDlgTruck);
			}

			oThisController.oDlgTruck.open();
		},

		onViewChargeBack: function (oEvent) {
			let oThisController = this;

			if (!oThisController.oDlgChargeBack) {
				oThisController.oDlgChargeBack = sap.ui.xmlfragment("com.incture.lch.Adhoc.fragment.ChargeBack", oThisController);
				oThisController.getView().addDependent(oThisController.oDlgChargeBack);
			}

			oThisController.oDlgChargeBack.open();
		},

		onTogglePremiumFreight: function (oEvent) {
			var oMdlCommon = this.getParentModel("mCommon");
			var aRoles = oMdlCommon.getProperty("/aCockpitRoles");
			if (oEvent.getParameter("state")) {
				if (aRoles instanceof Array) {
					if (aRoles.some(function (sRole) {
							return (sRole === "LOGISTICS_MANAGER" || sRole === "MATERIAL_PLANNER");
						})) {
						oMdlCommon.setProperty("/newFU/plannerEmail", oMdlCommon.getProperty("/userDetails/userEmail"));
						oMdlCommon.setProperty("/enable/plannerEmail", false);
					}
				}
			} else {
				oMdlCommon.setProperty("/newFU/premiumReasonCode", "");
				oMdlCommon.setProperty("/valState/premiumReasonCode", "None");
				oMdlCommon.setProperty("/newFU/plannerEmail", "");
				oMdlCommon.setProperty("/valState/plannerEmail", "None");
				oMdlCommon.refresh();
			}
		}

	});

});