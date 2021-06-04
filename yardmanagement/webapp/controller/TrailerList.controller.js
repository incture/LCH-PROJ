sap.ui.define([
	"com/incture/lch/yardmanagement/controller/BaseController",
	"sap/ui/model/Filter",
	"sap/ui/model/FilterOperator",
], function (BaseController, Filter, FilterOperator) {
	"use strict";

	return BaseController.extend("com.incture.lch.yardmanagement.controller.TrailerList", {

		/**
		 * Called when a controller is instantiated and its View controls (if available) are already created.
		 * Can be used to modify the View before it is displayed, to bind event handlers and do other one-time initialization.
		 * @memberOf com.incture.lch.yardmanagement.view.TrailerList
		 */
		onInit: function () {
			var oThisController = this;
			this._oRouter = this.getRouter();
			this._oRouter.attachRoutePatternMatched(function (oEvent) {
				var oMdlCommon = oThisController.getParentModel("mCommon");
				var viewName = oEvent.getParameter("name");
				if (viewName === "TrailerList") {
					oMdlCommon.setProperty("/sTntSelectedKey", "yardOverview");
				}
				if (viewName === "YardHistory") {
					oThisController.fnSetYardDefault("ALL");
					oMdlCommon.setProperty("/sTntSelectedKey", "yardHistory");
				}
				if (viewName === "GeoMap") {
					oMdlCommon.setProperty("/sTntSelectedKey", "geoMap");
				}
			});
		},

		//Function to handle Filter 
		onYardFilter: function (sKPIStatus) {
			var oThisController = this;
			oThisController.openBusyDialog();
			var oMdlCommon = this.getParentModel("mCommon");
			var oFilters = $.extend(true, {}, oMdlCommon.getProperty("/oFilters"));
			var oValState = $.extend(true, {}, oMdlCommon.getProperty("/oValState"));
			var sUserId = oThisController._oCommon.userDetails.name;
			var aCockpitRoles = $.extend(true, [], oMdlCommon.getProperty("/aCockpitRoles"));
			var sRole = oMdlCommon.getProperty("/sActiveRole");

			if (!sRole) {
				oThisController.showMessage(oThisController.getMessage("NO_ROLE_INFO"), "E");
				oThisController.closeBusyDialog();
				return;
			}

			var oDataPayload = {
				"freightOrderNo": "",
				"carrier": "",
				"trailer": "",
				"fromDate": "",
				"toDate": "",
				"location": "",
				"supplier": "",
				"status": "",
				"pendingWith": aCockpitRoles
			};

			let aValueStateChk = [
				oValState.sFromDateValState,
				oValState.sToDateValState,
				oValState.sValStateFilterByKey,
				oValState.sValStateYardLocation,
				oValState.sValStateStatus
			];
			if (aValueStateChk.indexOf("Error") !== -1) {
				oThisController.showMessage(oThisController.getMessage("FILTER_ERROR_INFO"), "E");
				oThisController.closeBusyDialog();
				return;
			}

			if (oFilters.fromDateFilter && oFilters.toDateFilter) {
				var dFromDate = new Date(oFilters.fromDateFilter);
				var dToDate = new Date(oFilters.toDateFilter);
				if (dFromDate.getTime() > dToDate.getTime()) {
					oMdlCommon.setProperty("/oValState/sFromDateValState", "Error");
					oMdlCommon.setProperty("/oValState/sToDateValState", "Error");
					oThisController.showMessage(oThisController.getMessage("INVALID_DATE"), "E");
					oThisController.closeBusyDialog();
					return;
				}
			}

			if (oMdlCommon.getProperty("/oFilters/fromDateFilter")) {
				var fromDate = oMdlCommon.getProperty("/oFilters/fromDateFilter");
				var dFromDateFormat = oThisController.dateHelper.getBackEndDate(fromDate);
			}

			if (oMdlCommon.getProperty("/oFilters/toDateFilter")) {
				var toDate = oMdlCommon.getProperty("/oFilters/toDateFilter");
				var dToDateFormat = oThisController.dateHelper.getBackEndDate(toDate).replace("00:00:00", "23:59:59");
			}

			if (oFilters.aSelStatus) {
				oDataPayload.status = oFilters.aSelStatus;
			} else {
				oDataPayload.status = [];
			}

			oDataPayload.freightOrderNo = (oFilters.filterByKey === "F1" && oFilters.filterByText) ? oFilters.filterByText : "";
			oDataPayload.carrier = (oFilters.filterByKey === "F3" && oFilters.filterByText) ? oFilters.filterByText : "";
			oDataPayload.trailer = (oFilters.filterByKey === "F2" && oFilters.filterByText) ? oFilters.filterByText : "";
			oDataPayload.fromDate = (dFromDateFormat) ? dFromDateFormat : "";
			oDataPayload.toDate = (dToDateFormat) ? dToDateFormat : "";
			oDataPayload.yardLocation = (oFilters.yardLocation) ? oFilters.yardLocation : "";
			// oDataPayload.yardId = (oFilters.yardId) ? oFilters.yardId : "";
			oDataPayload.supplier = (oFilters.supplierId) ? oFilters.supplierId : "";
			// oDataPayload.status = (typeof (sKPIStatus) !== "object") ? sKPIStatus : "";
			oThisController.fnSetYardDefault("KPI");
			oThisController.fnUnselectAllYard();
			oMdlCommon.setProperty("/aShipments", []);
			oThisController.fnCallYardData(oDataPayload);
		},

		//function to Filter Trucks Exceeded Aging
		onFilterAgingExceed: function (oEvent) {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var oFilters = $.extend(true, {}, oMdlCommon.getProperty("/oFilters"));
			var aCockpitRoles = $.extend(true, [], oMdlCommon.getProperty("/aCockpitRoles"));
			var sRole = oMdlCommon.getProperty("/sActiveRole");
			var bAgingClicked = oMdlCommon.getProperty("/bAgingClicked");
			var iAgingCount = oMdlCommon.getProperty("/iAgingCount");

			if (!sRole) {
				oThisController.showMessage(oThisController.getMessage("NO_ROLE_INFO"), "E");
				oThisController.closeBusyDialog();
				return;
			}

			//TODO Indicate Aging Filter is clicked/Applied
			/*if (!bAgingClicked) {
				oEvent.getSource().removeStyleClass("sapUIMStsBlue");
				oEvent.getSource().addStyleClass("sapUIMStsBlue");
				oMdlCommon.setProperty("/bAgingClicked", true);
			} else {
				oEvent.getSource().removeStyleClass("sapUIMStsBlue");
				oMdlCommon.setProperty("/bAgingClicked", false);
			}*/

			var oDataPayload = {};
			oDataPayload.freightOrderNo = (oFilters.filterByKey === "F1" && oFilters.filterByText) ? oFilters.filterByText : "";
			oDataPayload.carrier = (oFilters.filterByKey === "F3" && oFilters.filterByText) ? oFilters.filterByText : "";
			oDataPayload.trailer = (oFilters.filterByKey === "F2" && oFilters.filterByText) ? oFilters.filterByText : "";
			oDataPayload.fromDate = (oFilters.fromDateFilter) ? oThisController.dateHelper.getBackEndDate(oFilters.fromDateFilter) : "";
			oDataPayload.toDate = (oFilters.toDateFilter) ? oThisController.dateHelper.getBackEndDate(oFilters.toDateFilter) : "";
			// oDataPayload.location = (oFilters.yardLocation) ? oFilters.yardLocation : "";
			oDataPayload.yardLocation = (oFilters.yardLocation) ? oFilters.yardLocation : "";
			oDataPayload.yardId = (oFilters.yardId) ? oFilters.yardId : "";
			oDataPayload.supplier = (oFilters.supplierId) ? oFilters.supplierId : "";
			oDataPayload.status = "";
			oDataPayload.agingCount = Number(iAgingCount);
			oDataPayload.pendingWith = aCockpitRoles;
			oThisController.fnSetYardDefault("KPI");
			oThisController.fnUnselectAllYard();
			oMdlCommon.setProperty("/aShipments", []);
			oThisController.fnCallYardData(oDataPayload);
		},

		//function for validation of change event 
		onFilterChangeCBox: function (oEvent) {

			var oValidatedComboBox = oEvent.getSource(),
				sSelectedKey = oValidatedComboBox.getSelectedKey();
			// sValue = oValidatedComboBox.getValue();
			if (sSelectedKey === "") {
				oValidatedComboBox.setValueState("Error");
			} else {
				oValidatedComboBox.setValueState("None");
			}
		},

		//Function to handle Change Todate
		onAppChngeToDateCBox: function (oEvent) {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var oFilters = $.extend(true, {}, oMdlCommon.getProperty("/oFilters"));
			if (oFilters.fromDateFilter && oFilters.toDateFilter) {
				var dFromDate = new Date(oFilters.fromDateFilter);
				var dToDate = new Date(oFilters.toDateFilter);
				if (dFromDate.getTime() < dToDate.getTime()) {
					oMdlCommon.setProperty("/oValState/sFromDateValState", "None");
					oMdlCommon.setProperty("/oValState/sToDateValState", "None");
				}
			}
			oMdlCommon.refresh();
		},

		//Function to handle Change Todate
		onAppChngeFromDateCBox: function (oEvent) {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var oFilters = $.extend(true, {}, oMdlCommon.getProperty("/oFilters"));
			if (oFilters.fromDateFilter && oFilters.toDateFilter) {
				var dFromDate = new Date(oFilters.fromDateFilter);
				var dToDate = new Date(oFilters.toDateFilter);
				if (dFromDate.getTime() < dToDate.getTime()) {
					oMdlCommon.setProperty("/oValState/sFromDateValState", "None");
					oMdlCommon.setProperty("/oValState/sToDateValState", "None");
				}
			}
			oMdlCommon.refresh();
		},

		//function to handle Filter By key
		onFilterKeyChangeCBox: function (oEvent) {
			this.onFilterChangeCBox(oEvent);
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var oFilters = $.extend(true, {}, oMdlCommon.getProperty("/oFilters"));
			var oComboBox = oEvent.getSource(),
				sSelectedKey = oComboBox.getSelectedKey();
			oMdlCommon.setProperty("/oFilters/filterByText", "");
			oMdlCommon.refresh();
		},

		//function to handle Filter by Text
		onLiveChngeFilterByTxt: function (oEvent) {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var oInput = oEvent.getSource(),
				iValueLength = oInput.getValue();
			var oFilters = $.extend(true, {}, oMdlCommon.getProperty("/oFilters"));
			if (oFilters.filterByKey === "") {
				oThisController.showMessage(oThisController.getMessage("FILTER_KEY_INFO"), "E");
				oInput.setValue("");
				return;
			}

		},
		onPressKpi: function (oEvent) {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var sSelectedKpi = oEvent.getSource().getCustomData()[0].getValue();
			var aKpiSelected = $.extend(true, [], oMdlCommon.getProperty("/aKpiSelected"));
			var aJavaKpiDetails = $.extend(true, [], oMdlCommon.getProperty("/aJavaKpiDetails"));
			oThisController.fnUnselectAllYard();
			var iActive = aKpiSelected.findIndex(function (oRow) {
				return oRow.title === sSelectedKpi;
			});

			var oKpiActive = {
				"bNotArrivved": false,
				"bAtGate": false,
				"bInYard": false,
				"bPickedByMH": false,
				"bAtDoc": false,
				"bOutGate": false
			};

			if (iActive !== -1) {
				if (!aKpiSelected[iActive].iKpiClick) {
					aKpiSelected[iActive].iKpiClick = 1;
					for (var i = 0; i < aKpiSelected.length; i++) {
						if (i !== iActive) {
							aKpiSelected[i].iKpiClick = 0;
						}
					}
					var sStatus = aKpiSelected[iActive].value;
					oMdlCommon.setProperty("/bActive", true);
					var oKPIClicked = aJavaKpiDetails.find(function (oRow) {
						let sCurStatus = oRow.groupOrKpi.toUpperCase().replace(" ", "");
						let sTempStatus = sStatus.toUpperCase().replace(" ", "");
						return sCurStatus === sTempStatus;
					});

					var aKPIClicked = $.extend(true, [], oKPIClicked.ymRecords);
					// oThisController.fnSetFormatYardData(aKPIClicked);
					var aShipments = oThisController.fnSetFormatYardData(aKPIClicked);
					oMdlCommon.setProperty("/aShipments", aShipments);
					oMdlCommon.setProperty("/aBckShipments", aShipments);
					oMdlCommon.setProperty("/iYardLoaded", aShipments.length);
					oMdlCommon.setProperty("/iTotalLength", aShipments.length);
					// this.onYardFilter(sStatus);
					switch (iActive) {
					case 0:
						oKpiActive.bNotArrivved = true;
						break;
					case 1:
						oKpiActive.bAtGate = true;
						break;
					case 2:
						oKpiActive.bInYard = true;
						break;
					case 3:
						oKpiActive.bPickedByMH = true;
						break;
					case 4:
						oKpiActive.bAtDoc = true;
						break;
					case 5:
						oKpiActive.bOutGate = true;
						break;
					}

					oMdlCommon.setProperty("/oKpiActive", oKpiActive);

				} else if (aKpiSelected[iActive].iKpiClick === 1) {
					oMdlCommon.setProperty("/bActive", false);
					aKpiSelected[iActive].iKpiClick = 0;
					var sStatus = "";
					this.onYardFilter(sStatus);
					oMdlCommon.setProperty("/oKpiActive", oKpiActive);

				}
			}
			this.fnSetYardDefault("FILTERS");
			oMdlCommon.setProperty("/aKpiSelected", aKpiSelected);
			oMdlCommon.refresh();
		},

		//Function to Handle Priority Live Change
		onChngePriority: function (oEvent) {
			this.fnNumberOnlyLiveChange(oEvent);
			var oThisController = this;
			// oThisController.openBusyDialog();
			var oMdlCommon = this.getParentModel("mCommon");
			var sPath = oEvent.getSource().getBindingContext("mCommon").sPath;
			var oCurData = $.extend(true, {}, oMdlCommon.getProperty(sPath));
			var oInput = oEvent.getSource(),
				iValueLength = oInput.getValue();

			var iTotalLength = oMdlCommon.getProperty("/iTotalLength");
			oMdlCommon.setProperty(sPath + "/priority", "");
			var aShipments = $.extend(true, [], oMdlCommon.getProperty("/aShipments"));
			if (iValueLength !== "") {
				var iIndex = aShipments.findIndex(function (oRow) {
					return oRow.priority === iValueLength;
				});
			}

			oMdlCommon.setProperty(sPath + "/priority", iValueLength);
			oMdlCommon.setProperty(sPath + "/bJavaValChange", true);
			oMdlCommon.refresh();
		},

		//Function to Handle Priority Live Change
		onChngeComment: function (oEvent) {
			var oThisController = this;
			// oThisController.openBusyDialog();
			var oMdlCommon = this.getParentModel("mCommon");
			var sPath = oEvent.getSource().getBindingContext("mCommon").sPath;
			var oCurData = $.extend(true, {}, oMdlCommon.getProperty(sPath));
			oMdlCommon.setProperty(sPath + "/bPIValChange", true);
			oMdlCommon.refresh();
		},

		onChangeYard: function (oEvent) {
			var oValidatedComboBox = oEvent.getSource(),
				sSelectedKey = oValidatedComboBox.getSelectedKey();

			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var oBindingContext = oEvent.getSource().getBindingContext("mCommon");
			var aYardLocation = $.extend(true, [], oMdlCommon.getProperty("/aYardLocation"));
			var iIndex = aYardLocation.findIndex(function (fRow) {
				return fRow.yardId === sSelectedKey;
			});

			// sValue = oValidatedComboBox.getValue();
			if (sSelectedKey === "") {
				oValidatedComboBox.setValueState("Error");
			} else {
				oValidatedComboBox.setValueState("None");
				if (oBindingContext && oBindingContext.getPath()) {
					// oThisController.fnGetLocationByYardId(iIndex, true, oBindingContext.getPath());
					oMdlCommon.setProperty(oBindingContext.getPath() + "/aRowLoc", aYardLocation[iIndex].aLoc);
				} else {
					// oThisController.fnGetLocationByYardId(iIndex);
					oMdlCommon.setProperty("/aLocation", aYardLocation[iIndex].aLoc);
				}
			}

			oMdlCommon.refresh();
		},

		//function to handle Yard status change:
		onChangeYardStatus: function (oEvent) {
			//Check valueState:
			this.onFilterChangeCBox(oEvent);
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var sPath = oEvent.getSource().getBindingContext("mCommon").sPath;
			oMdlCommon.setProperty(sPath + "/bPIValChange", true);
			oMdlCommon.refresh();
		},

		onSendEmail: function (oEvent) {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var aSelectedData = $.extend(true, [], oMdlCommon.getProperty("/aSelectedFOData"));
			var aSelectedYard = oThisController.fnGetUpdatedData(aSelectedData);
			var aDataPayload = [];

			var sUrl = "/lch_services/mail/sendMailToCarrier";
			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json"
			};

			for (let i = 0; i < aSelectedYard.length; i++) {
				debugger;
				if (aSelectedYard[i].status && aSelectedYard[i].status === "Empty - Ready For Pickup" ) {
					aDataPayload.push({
						"carrierId": (aSelectedYard[i].carrier) ? aSelectedYard[i].carrier : "",
						"location": (aSelectedYard[i].location) ? aSelectedYard[i].location : "",
						"trailerNo": (aSelectedYard[i].trailer) ? aSelectedYard[i].trailer : ""
					});
				}
			}

			if (aDataPayload.length) {
				oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, true, function (oXHR, status) {
					if (status === "success") {
						oThisController.showMessage(oThisController.getMessage("EMAIL_SENT_SUCCESSFULLY"), "I");
					} else {
						oThisController.showMessage(oThisController.getMessage("REQUEST_OPERATION_FAILED"), "E");
					}
				}, aDataPayload);
			} else {
				oThisController.showMessage(oThisController.getMessage("TRAILER_EMAIL_MESSAGE"), "I");
			}

		},

		//function to handle Update Yard Data
		onUpdateYard: function () {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var aSelectedData = $.extend(true, [], oMdlCommon.getProperty("/aSelectedFOData"));
			var aSelectedYard = oThisController.fnGetUpdatedData(aSelectedData);
			var aDataPayload = [];
			var aCockpitRoles = $.extend(true, [], oMdlCommon.getProperty("/aCockpitRoles"));
			var sRole = oMdlCommon.getProperty("/sActiveRole");
		/*	var sRole = "LCH_SECURITY_GUARD"*/
			let aSupplier = $.extend(true, [], oMdlCommon.getProperty("/aSupplier"));
			let aCarrier = $.extend(true, [], oMdlCommon.getProperty("/aCarrier"));
			let aDestination = $.extend(true, [], oMdlCommon.getProperty("/aDestination"));

			if (!sRole) {
				oThisController.showMessage(oThisController.getMessage("NO_ROLE_INFO"), "E");
				oThisController.closeBusyDialog();
				return;
			}
			// console.log(aSelectedYard);
			var bError = false;
			var iExistingPost = 0;
			var iAdhocPost = 0;
			for (var i = 0; i < aSelectedYard.length; i++) {
				let oTemp = aSelectedYard[i];
				let oSupplierFilterData, oCarrierFilterData, oDestFilterData;

				let oSelected = {
					"id": oTemp.id,
					"adhocType": oTemp.adhocType,
					"priority": oTemp.priority,
					"carrier": oTemp.carrier,
					"carrierDesc": oTemp.carrierDes,
					"carrierName": oTemp.carrierName,
					"trailer": oTemp.trailer,
					"sealNo": oTemp.sealNo,
					"licencePlateNo": oTemp.licencePlateNo,
					"supplier": oTemp.supplierBckUp,
					"supplierAddress": oTemp.supplierAddress,
					"plannedShipDate": oThisController.dateHelper.getBackEndDate(oTemp.plannedShipDate),
					"yardId": oTemp.yardId,
					"isPpKit": oTemp.isPpKit,
					"arrival": oThisController.dateHelper.getBackEndDate(oTemp.arrival), //eta
					"handlingUnit": oTemp.handlingUnit,
					"proNo": oTemp.proNo,
					"yardIn": oTemp.yardIn,
					"yardOut": oTemp.yardOut,

					"freightOrderNo": oTemp.freightOrderNo,
					"destId": oTemp.destId,
					"updatedBy": oThisController._oCommon.userDetails.name.familyName,
					"comments": oTemp.comments,
					"status": oTemp.status,
					"statusGroup": oTemp.statusGroup,
					"yardLocation": oTemp.yardLocation,
					"updatedDate": oThisController.dateHelper.getBackEndDate(new Date()),

					"FreightOrder": oTemp.freightOrderNo,
					"Destination_ID": "",
					"Updated_By": oThisController._oCommon.userDetails.name.familyName,
					"Comments": oTemp.comments,
					"Status": oTemp.status,
					"Status_Desc": oTemp.statusGroup,
					"Location": oTemp.yardLocation,
					"UpdatedDate": oThisController.dateHelper.getBackEndDate(new Date())
				};

				let aErrorValidation = [oTemp.sYrdTbleSupplierValState, oTemp.sYrdTbleDestValState, oTemp.sYrdTbleCarrierValState];

				if (aErrorValidation.indexOf("Error") !== -1) {
					oThisController.showMessage(oThisController.getMessage("FILTER_ERROR_INFO"), "E");
					oThisController.closeBusyDialog();
					return;
				}

				if (oTemp.supplierKey) {
					oSupplierFilterData = oThisController.fnGetFltrDataBasedOnKey(aSupplier, "bpNumber", oTemp.supplierKey);
					oSelected.supplier = oSupplierFilterData.bpNumber;
					oSelected.supplierAddress = oSupplierFilterData.SupplierAddress;
				}
				if (oTemp.carrierKey) {
					oCarrierFilterData = oThisController.fnGetFltrDataBasedOnKey(aCarrier, "bpNumber", oTemp.carrierKey);
					oSelected.carrier = oCarrierFilterData.bpNumber;
					oSelected.carrierDesc = oCarrierFilterData.carrierName;
				}
				if (oTemp.destKey) {
					oDestFilterData = oThisController.fnGetFltrDataBasedOnKey(aDestination, "destId", oTemp.destKey);
					oSelected.destId = oDestFilterData.destId;
				}

				oSelected.updatedBy = oThisController._oCommon.userDetails.name.familyName;
				oSelected.updatedDate = oThisController.dateHelper.getBackEndDate(new Date());

				//Set Date for java
				oSelected.plannedShipDate = (oTemp.plannedShipDate) ? oThisController.dateHelper.getBackEndDate(oTemp.plannedShipDate) : "";

				if (sRole === oThisController.constantsDef.tManager) {
					oSelected.yardIn = (oTemp.yardIn) ? oThisController.dateHelper.getBackEndDate(oTemp.yardIn) : "";
					// oSelected.yardOut = (oTemp.yardOut) ? oThisController.dateHelper.getBackEndDate(oTemp.yardOut) : "";
				}

				if (oTemp.bAdhocEntry && oTemp.freightOrderNo === "") {
					oSelected.pendingWith = aCockpitRoles.toString();
					oTemp.pendingWith = aCockpitRoles.toString();
				}

				//Validation Zone
				if (oTemp.bAdhocEntry && oTemp.trailer === "") {
					bError = true;
				}

				if (oTemp.sValStateYardStatus === "Error") {
					oThisController.showMessage(oThisController.getMessage("FILTER_ERROR_INFO"), "E");
					oThisController.closeBusyDialog();
					return;
				}

				if (oTemp.freightOrderNo && oTemp.bPIValChange) {
					iExistingPost += 1;
				}
				if (!oTemp.freightOrderNo || oTemp.bJavaValChange) {
					iAdhocPost += 1;
				}
				aDataPayload.push(oSelected);
			}
			if (bError) {
				oThisController.showMessage(oThisController.getMessage("TRAILER_NO_MANDATORY_INFO"), "E");
				oThisController.closeBusyDialog();
				return;
			}

			//Decide POST CPI or JAVA or BOTH
			var sPostMethod = "";
			switch (true) {
			case (iExistingPost > 0 && iAdhocPost > 0):
				sPostMethod = "CPIJAVA";
				break;
			case (iExistingPost === 0 && iAdhocPost > 0):
				sPostMethod = "JAVA"
				break;
			case (iExistingPost > 0 && iAdhocPost === 0):
				sPostMethod = "CPIJAVA";
				break;
			default:
				sPostMethod = "JAVA"
				break;
			}

			if (sPostMethod) {
				oThisController.confirmUserAction(oThisController.getMessage("CONFIRM_UPDATE_INFO"), "I", function (sAction) {
					if (sAction === "YES") {
						oThisController.fnDecidePostCall(sPostMethod, aDataPayload, aSelectedYard);
					} else {
						oThisController.closeBusyDialog();
					}
				});
			}

		},

		//function to get data based on filter key and data passed
		fnGetFltrDataBasedOnKey: function (aData, sFilterParam, sFilterKey) {
			let oFilterData = aData.find(function (oRow) {
				return oRow[sFilterParam] === sFilterKey;
			});
			return oFilterData;
		},

		//function to handle suggestion for supplier lookup adhoc entry
		onSuggestSupplier: function (oEvent) {
			let oMdlCommon = this.getParentModel("mCommon");
			let bNewSupplierBtn = oMdlCommon.getProperty("/bNewSupplierBtn");
			
			if(bNewSupplierBtn){
				return;
			}
			
			let sSuggestValue = oEvent.getParameter("suggestValue");
			let aFilters = [];
			if (sSuggestValue) {
				aFilters.push(new Filter("shipperName", sap.ui.model.FilterOperator.StartsWith, sSuggestValue));
			}
			oEvent.getSource().getBinding("suggestionItems").filter(aFilters);
			let iSuggestLen = oEvent.getSource().getSuggestionItems().length;
			let sPath = oEvent.getSource().getBindingContext("mCommon").getPath();
			
			if (!iSuggestLen) {
				oMdlCommon.setProperty(sPath + "/sYrdTbleSupplierValState", "Error");
			}else{
				oMdlCommon.setProperty(sPath + "/sYrdTbleSupplierValState", "None");
			}
			oMdlCommon.refresh();
		},

		//function to handle suggestion for supplier lookup adhoc entry
		onNewSupplier: function (oEvent) {
			let oSource = oEvent.getSource();
			let oMdlCommon = this.getParentModel("mCommon");
			let sPath = oEvent.getSource().getBindingContext("mCommon").getPath();
			if(oSource.getIcon() === "sap-icon://write-new"){
				oMdlCommon.setProperty(sPath + "/sYrdTbleSupplierValState", "None");
				oMdlCommon.setProperty("/bNewSupplierBtn", true);
				oSource.setIcon("sap-icon://filter-facets");
			}else{
				oMdlCommon.setProperty("/bNewSupplierBtn", false);
				oSource.setIcon("sap-icon://write-new");
			}
			oMdlCommon.refresh();
		},

		//function to handle suggestion for carrier lookup adhoc entry
		onSuggestCarrier: function (oEvent) {
			let oMdlCommon = this.getParentModel("mCommon");
			let bNewCarrier = oMdlCommon.getProperty("/bNewCarrierBtn");
			
			if(bNewCarrier){
				return;
			}
			
			let sSuggestValue = oEvent.getParameter("suggestValue");
			let aFilters = [];
			if (sSuggestValue) {
				aFilters.push(new Filter("carrierDetails", sap.ui.model.FilterOperator.StartsWith, sSuggestValue));
			}
			oEvent.getSource().getBinding("suggestionItems").filter(aFilters);
			let iSuggestLen = oEvent.getSource().getSuggestionItems().length;
			let sPath = oEvent.getSource().getBindingContext("mCommon").getPath();
			
			if (!iSuggestLen) {
				oMdlCommon.setProperty(sPath + "/sYrdTbleCarrierValState", "Error");
			}else{
				oMdlCommon.setProperty(sPath + "/sYrdTbleCarrierValState", "None");
			}
			oMdlCommon.refresh();
		},

		//function to handle suggestion for carrier lookup adhoc entry
		onNewCarrier: function (oEvent) {
			let oSource = oEvent.getSource();
			let oMdlCommon = this.getParentModel("mCommon");
			let sPath = oEvent.getSource().getBindingContext("mCommon").getPath();
			if(oSource.getIcon() === "sap-icon://write-new"){
				oMdlCommon.setProperty(sPath + "/sYrdTbleCarrierValState", "None");
				oMdlCommon.setProperty("/bNewCarrierBtn", true);
				oSource.setIcon("sap-icon://filter-facets");
			}else{
				oMdlCommon.setProperty("/bNewCarrierBtn", false);
				oSource.setIcon("sap-icon://write-new");
			}
			oMdlCommon.refresh();
		},

		//function to handle suggestion for Destination lookup adhoc entry
		onSuggestDest: function (oEvent) {
			let oMdlCommon = this.getParentModel("mCommon");
			let bNewDestIdBtn = oMdlCommon.getProperty("/bNewDestIdBtn");
			
			if(bNewDestIdBtn){
				return;
			}
			
			let sSuggestValue = oEvent.getParameter("suggestValue");
			let aFilters = [];
			if (sSuggestValue) {
				aFilters.push(new Filter("destId", sap.ui.model.FilterOperator.StartsWith, sSuggestValue));
			}
			oEvent.getSource().getBinding("suggestionItems").filter(aFilters);
			let iSuggestLen = oEvent.getSource().getSuggestionItems().length;
			let sPath = oEvent.getSource().getBindingContext("mCommon").getPath();
			
			if (!iSuggestLen) {
				oMdlCommon.setProperty(sPath + "/sYrdTbleDestValState", "Error");
			}else{
				oMdlCommon.setProperty(sPath + "/sYrdTbleDestValState", "None");
			}
			oMdlCommon.refresh();
		},

		//function to handle suggestion for Destination lookup adhoc entry
		onNewDestination: function (oEvent) {
			let oSource = oEvent.getSource();
			let oMdlCommon = this.getParentModel("mCommon");
			let sPath = oEvent.getSource().getBindingContext("mCommon").getPath();
			
			if(oSource.getIcon() === "sap-icon://write-new"){
				oMdlCommon.setProperty(sPath + "/sYrdTbleDestValState", "None");
				oMdlCommon.setProperty("/bNewDestIdBtn", true);
				oSource.setIcon("sap-icon://filter-facets");
			}else{
				oMdlCommon.setProperty("/bNewDestIdBtn", false);
				oSource.setIcon("sap-icon://write-new");
			}
			oMdlCommon.refresh();
		},

		//function to decide the service calls
		fnDecidePostCall: function (sPostMethod, aDataPayload, aSelectedYard) {
			var oThisController = this;
			var aPIFields = [
				"FreightOrder",
				"Destination_ID",
				"Updated_By",
				"Comments",
				"Status",
				"Status_Desc",
				"Location",
				"UpdatedDate"
			];
			var aPIPayload = JSON.parse(JSON.stringify(aDataPayload, aPIFields));
			switch (sPostMethod) {
			case "CPIJAVA":
				oThisController.fnPIPostYardDetails(aPIPayload, function () {
					oThisController.fnUpdatePostJava(aDataPayload);
				});
				break;
			case "JAVA":
				oThisController.fnUpdatePostJava(aDataPayload);
				break;
			default:
				oThisController.closeBusyDialog();
			}
		},

		//function to handle Create Adhoc Entry
		onCreateAdhoc: function (oEvent) {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var aSelectedData = $.extend(true, [], oMdlCommon.getProperty("/aSelectedFOData"));
			var aShipments = $.extend(true, [], oMdlCommon.getProperty("/aShipments"));
			var oNewAdhocData = $.extend(true, {}, oMdlCommon.getProperty("/oNewAdhocData"));
			aShipments.unshift(oNewAdhocData);
			oMdlCommon.setProperty("/aShipments", aShipments);
			oMdlCommon.setProperty("/iTotalLength", aShipments.length);
			oThisController.fnUnselectAllYard();
			oMdlCommon.refresh();
		},

		//function to handle Download Aging Excel
		onAgingDownload: function (oEvent) {
			let oThisController = this;
			let oMdlCommon = this.getParentModel("mCommon");
			let sURL = "/lch_services/yard/downLoadYardAgingData";
			let aCockpitRoles = $.extend(true, [], oMdlCommon.getProperty("/aCockpitRoles"));
			let sRole = oMdlCommon.getProperty("/sActiveRole");
			let iAgingCount = oMdlCommon.getProperty("/iAgingCount");
			if (!sRole) {
				oThisController.showMessage(oThisController.getMessage("NO_ROLE_INFO"), "E");
				oThisController.closeBusyDialog();
				return;
			}

			let oPayload = {
				"agingCount": Number(iAgingCount),
				"pendingWith": aCockpitRoles
			};
			oThisController.onBase64FileDownload("AGING_EXCEED_TRUCKS", sURL, oPayload);
		},

		onDownload: function (oEvent) {
			let oThisController = this;
			let oMdlCommon = this.getParentModel("mCommon");
			let sURL = "/lch_services/yard/downloadYardDetails";
			let aShipments = $.extend(true, [], oMdlCommon.getProperty("/aShipments"));

			let oPayload = [/*{
					"priority": "",
					"carrier": "",
					"trailer": "",
					"sealNo": null,
					"licencePlateNo": null,
					"status": "NOTARRIVED",
					"supplier": "1000001645",
					"destId": null,
					"plannedShipDate": "22011210102200",
					"updatedDate": "2021-03-02 07:00:00",
					"yardLocation": null,
					"comments": "",
					"isPpKit": "true",
					"arrival": "0",
					"handlingUnit": "0E-14",
					"fuCount": "2",
					"proNo": "",
					"freightOrderNo": "6100001984"
				}*/];

			for (let i = 0; i < aShipments.length; i++) {
				let oShipment = $.extend(true,{},aShipments[i]);
				oShipment.carrier = oShipment.carrierKey;
				oShipment.plannedShipDate = oThisController.dateHelper.getBackEndDate(oShipment.plannedShipDate);
				oShipment.updatedDate = oThisController.dateHelper.getBackEndDate(oShipment.updatedDate);
				oPayload.push(oShipment);
			}

			oThisController.onBase64FileDownload("TRAILERS_LIST", sURL, oPayload);
		},

		onChangeYardStatus: function (oEvent) {

		},

		onSelFinishStatus: function (oEvent) {

		}

		/**
		 * Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered
		 * (NOT before the first rendering! onInit() is used for that one!).
		 * @memberOf com.incture.lch.yardmanagement.view.TrailerList
		 */
		//	onBeforeRendering: function() {
		//
		//	},

		/**
		 * Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here.
		 * This hook is the same one that SAPUI5 controls get after being rendered.
		 * @memberOf com.incture.lch.yardmanagement.view.TrailerList
		 */
		//	onAfterRendering: function() {
		//
		//	},

		/**
		 * Called when the Controller is destroyed. Use this one to free resources and finalize activities.
		 * @memberOf com.incture.lch.yardmanagement.view.TrailerList
		 */
		//	onExit: function() {
		//
		//	},
	});

});