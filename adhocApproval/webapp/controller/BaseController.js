sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/core/routing/History",
	"sap/m/MessageBox",
	"com/incture/lch/adhocApproval/utility/Formatter",
	"com/incture/lch/adhocApproval/utility/DateHelper",
	"sap/ui/model/json/JSONModel",
	"sap/ui/model/Filter",
	"sap/ui/model/FilterOperator",
	"sap/ui/model/odata/v2/ODataModel"
], function (Controller, History, MessageBox, Formatter, DateHelper, JSONModel, Filter, FilterOperator, ODataModel) {
	"use strict";

	return Controller.extend("com.incture.lch.adhocApproval.controller.BaseController", {

		formatter: Formatter,

		dateHelper: DateHelper,

		_oCommon: {},

		/**
		 * @author Mohammed Saleem Bani
		 * Changed by Noopur
		 * @purpose Initialize the App 
		 */
		fnInitializeApp: function () {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var sRootPath = jQuery.sap.getModulePath("com.incture.lch.adhocApproval");
			oMdlCommon.attachRequestCompleted(function (oEvent) {
				oThisController.fnSetCurrentUser();
				oMdlCommon.setProperty("/today", new Date());
				var mcontextModel = oThisController.getOwnerComponent().getModel("context");
				var contextData = mcontextModel.getData();
				oMdlCommon.setProperty("/context",contextData);
				oMdlCommon.refresh();
				oThisController.getRouter().navTo("Order");
			
		
			});

			oMdlCommon.loadData(sRootPath + "/model/Property.json", null, false);
			Array.prototype.uniqueByStrProp = function (prop1, prop2) {
				var aAll = this;
				var aUnique = [];
				for (var i = 0; i < aAll.length; i++) {
					var oCurRow = aAll[i];
					if (aUnique.filter(function (fRow) {
							if (prop1 && prop2) {
								return fRow[prop1] === oCurRow[prop1] && fRow[prop2] === oCurRow[prop2];
							} else if (prop1 && !prop2) {
								return fRow[prop1] === oCurRow[prop1];
							} else if (!prop1 && prop2) {
								return fRow[prop2] === oCurRow[prop2];
							} else {
								return false;
							}
						}).length === 0) {
						aUnique.push(oCurRow);
					}
				}
				return aUnique;
			};

			if (!String.splice) {
				String.prototype.splice = function (start, delCount, newSubStr) {
					return this.slice(0, start) + newSubStr + this.slice(start + Math.abs(delCount));
				};
			}
		},

		/**
		 * Convenience method for accessing the router.
		 * @public
		 * @returns {sap.ui.core.routing.Router} the router for this component
		 */
		getRouter: function () {
			return sap.ui.core.UIComponent.getRouterFor(this);
		},

		/**
		 * Convenience method for getting the view model by name.
		 * @public
		 * @param {string} [sName] the model name
		 * @returns {sap.ui.model.Model} the model instance
		 */
		getParentModel: function (sName) {
			var oMdl = this.getOwnerComponent().getModel(sName);
			if (!oMdl) {
				oMdl = new JSONModel({});
				this.setParentModel(oMdl, sName);
			}
			return oMdl;
		},

		/**
		 * Convenience method for setting the view model.
		 * @public
		 * @param {sap.ui.model.Model} oModel the model instance
		 * @param {string} sName the model name
		 * @returns {sap.ui.mvc.View} the view instance
		 */
		setParentModel: function (oModel, sName) {
			oModel.setSizeLimit(9999);
			return this.getOwnerComponent().setModel(oModel, sName);
		},

		/**
		 * Convenience method for getting the view model by name.
		 * @public
		 * @param {string} [sName] the model name
		 * @returns {sap.ui.model.Model} the model instance
		 */
		getModel: function (sName) {
			var oMdl = this.getView().getModel(sName);
			if (!oMdl) {
				oMdl = new JSONModel({});
				this.setModel(oMdl, sName);
			}
			return oMdl;
		},

		/**
		 * Convenience method for setting the view model.
		 * @public
		 * @param {sap.ui.model.Model} oModel the model instance
		 * @param {string} sName the model name
		 * @returns {sap.ui.mvc.View} the view instance
		 */
		setModel: function (oModel, sName) {
			return this.getView().setModel(oModel, sName);
		},

		/**
		 * Getter for the resource bundle.
		 * @public
		 * @returns {sap.ui.model.resource.ResourceModel} the resourceModel of the component
		 */
		getResourceBundle: function () {
			return this.getOwnerComponent().getModel("i18n").getResourceBundle();
		},

		_BusyDialog: new sap.m.BusyDialog({
			busyIndicatorDelay: 0 //,
				// customIcon:"./media/lodr-nw.gif",
				// customIconRotationSpeed:0,
				// customIconWidth:"150px",
				// customIconHeight:"150px"
		}),

		openBusyDialog: function () {
			if (this._BusyDialog) {
				this._BusyDialog.open();
			} else {
				this._BusyDialog = new sap.m.BusyDialog({
					busyIndicatorDelay: 0
				});
				this._BusyDialog.open();
			}
		},

		closeBusyDialog: function () {
			if (this._BusyDialog) {
				this._BusyDialog.close();
			}
		},

		/**
		 * @author Mohammed Saleem Bani
		 * @purpose Message from Resource Bundle 
		 * @param1 pMessage -- String-Property of Resource Bundle
		 * @param2 aParametrs -- Array-Parameters
		 */
		getMessage: function (pMessage, aParametrs) {
			// read msg from i18n model
			var sMsg = "";
			var oMdlI18n = this.getOwnerComponent().getModel("i18n");
			if (oMdlI18n) {
				this._oCommon._oBundle = oMdlI18n.getResourceBundle();
			} else {
				this._oCommon._oBundle = null;
				return sMsg;
			}

			if (aParametrs && aParametrs.length) {
				sMsg = this._oCommon._oBundle.getText(pMessage, aParametrs);
			} else {
				sMsg = this._oCommon._oBundle.getText(pMessage);
			}

			return sMsg;
		},

		/**
		 * @author Mohammed Saleem Bani
		 * @purpose Message 
		 * @param1 pMessage -- String-Message to be displayed
		 * @param2 pMsgTyp -- String-Message type
		 * @param2 pHandler -- function-callback function
		 */
		showMessage: function (pMessage, pMsgTyp, pHandler) {

			if (pMessage.trim().length === 0) {
				return;
			}

			if (["A", "E", "I", "W"].indexOf(pMsgTyp) === -1) {
				sap.m.MessageToast.show(pMessage);
			} else {
				var sIcon = "";

				switch (pMsgTyp) {
				case 'W':
					sIcon = "WARNING";
					break;
				case 'E':
					sIcon = "ERROR";
					break;
				case 'I':
					sIcon = "INFORMATION";
					break;
				case 'A':
					sIcon = "NONE";
					break;
				default:
				}
				MessageBox.show(pMessage, {
					icon: sIcon,
					title: sIcon,
					onClose: pHandler
				});
			}
		},

		/**
		 * @author Mohammed Saleem Bani
		 * @purpose Message 
		 * @param1 pMessage -- String-Message to be displayed
		 * @param2 pMsgTyp -- String-Message type
		 * @param2 pHandler -- function-callback function
		 */
		confirmUserAction: function (pMessage, pMsgTyp, pHandler) {
			var sIcon = "";

			switch (pMsgTyp) {
			case 'W':
				sIcon = "WARNING";
				break;
			case 'E':
				sIcon = "ERROR";
				break;
			case 'I':
				sIcon = "INFORMATION";
				break;
			case 'A':
				sIcon = "NONE";
				break;
			default:

			}
			MessageBox.confirm(pMessage, {
				icon: sIcon,
				title: "Confirm",
				actions: [MessageBox.Action.YES, MessageBox.Action.NO],
				onClose: pHandler
			});
		},

		/**
		 * @author Mohammed Saleem Bani
		 * @purpose Model filter for given control 
		 * @param1 aPropertyFilterSettings -- {propertyName:"",filterOperator:"",propertyValue:""}
		 * @param2 oBinding -- Binding Object
		 * @param3 bLogicalOperator -- boolean value true for AND operation
		 */
		fnApplyCustomerFilter: function (aPropertyFilterSettings, oBinding, bLogicalOperator) {
			var aFilters = [];
			if (aPropertyFilterSettings && aPropertyFilterSettings instanceof Array && aPropertyFilterSettings.length > 0) {
				$.each(aPropertyFilterSettings, function (index, oRow) {
					if (oRow.propertyName && oRow.filterOperator &&
						(oRow.propertyValue != undefined || oRow.propertyValue != null || oRow.propertyValue != "")) {
						aFilters.push(new Filter(oRow.propertyName, oRow.filterOperator, oRow.propertyValue));
					}
				});

				// update list binding
				var bOpAND = (bLogicalOperator) ? true : false;
				oBinding.filter(new Filter(aFilters, bOpAND), "Application");
			} else {
				oBinding.filter([], "Application");
			}
		},

		/*
		 * @author Mohammed Saleem Bani
		 * @purpose Delayed execution
		 * @param1 callback -- callback function to be executed
		 * @param2 delayMicroSeconds -- delay in micro seconds
		 */
		fnDelayedCall: function (callback, delayMicroSeconds) {
			var delay = (delayMicroSeconds && delayMicroSeconds > 0) ? delayMicroSeconds : 0;
			jQuery.sap.delayedCall(delay, null, callback);
		},

		/*
		 * @author Mohammed Saleem Bani
		 * @purpose Handle Service Request 
		 * @param1 sUrl -- String
		 * @param2 sReqType -- String-(GET/POST/PUT/DELETE)
		 * @param3 oHeader -- Header JSON Object
		 * @param4 bShowBusy -- Boolean value to Show Busy Dialog or not
		 * @param5 pHandler -- function-callback function
		 * @param6 oData -- Data to be sent JSON Object
		 */
		fnProcessDataRequest: function (sUrl, sReqType, oHeader, bShowBusy, pHandler, oData) {
			debugger;
			var oThisController = this;
			var oAjaxSettings = {
				url: sUrl,
				type: sReqType,
				cache: false,
				beforeSend: function (jqXHR) {
					if (bShowBusy) {
						oThisController.openBusyDialog();
					}
				},
				complete: function (jqXHR, status) {

					if (jqXHR.getResponseHeader("com.sap.cloud.security.login")) {
						oThisController.showMessage("Session is expired, page shall be reloaded.", "I", function () {
							window.location.reload();
						});
					} else {
						if (pHandler) {
							pHandler(jqXHR, status);
						}
					}

					if (status === "error") {
						oThisController.closeBusyDialog();
					}

					if (bShowBusy) {
						oThisController.closeBusyDialog();
					}
				}
			};

			if (oHeader && oHeader instanceof Object) {
				oHeader["X-XSS-Protection"] = "1; mode=block";
				oHeader["Content-Security-Policy"] = "default-src 'self'";
				oAjaxSettings.headers = oHeader;
			}

			if (oData && oData instanceof Object) {
				oAjaxSettings.data = JSON.stringify(oData);
			}

			$.ajax(oAjaxSettings);
		},

		getSNumberPadZeroes: function (value, length) {
			var sNumber = "" + value;
			while (sNumber.length < length) {
				sNumber = "0" + sNumber;
			}
			return sNumber;
		},

		onChangeLang: function (oEvent) {
			var sSelKey = oEvent.getSource().getSelectedKey();

			if (!sSelKey) {
				sSelKey = "en";
			}

			var i18nModel = new sap.ui.model.resource.ResourceModel({
				bundleUrl: "i18n/i18n.properties",
				bundleLocale: sSelKey
			});

			this.getOwnerComponent().setModel(i18nModel, "i18n");
			this.setAttribute("language", sSelKey);
			i18nModel.refresh();
		},

		//Function to get Fiori launchpad roles
		/*	fnGetLaunchPadRoles: function (callback) {
				var oThisController = this;
				var oMdlCommon = this.getModel("mCommon");
				var sUserId = oMdlCommon.getProperty("/userDetails/name");
				var sUrl = "/lch_rest/freightunits/getRoles/" + sUserId;
				var oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};
				oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
					if (status === "success") {
						if (oXHR && oXHR.responseJSON && oXHR.responseJSON.roles) {
							oMdlCommon.setProperty("/aCockpitRoles", oXHR.responseJSON.roles);
							oMdlCommon.refresh();

							oThisController.fnGetAuthRules(callback);
						}
					} else {
						oMdlCommon.setProperty("/cockpitRoles", []);
						oMdlCommon.refresh();
						// unable to fetch roles.
					}
				});
			},*/

		fnNumberOnlyLiveChange: function (oEvent) {
			var oInput = oEvent.getSource();
			var value = oInput.getValue();
			value = value.replace(/[^0-9]/g, "");
			oInput.setValue(value);
		},

		onLiveChange: function (oEvent) {
			this.fnNumberOnlyLiveChange(oEvent);
			this.onInpChange(oEvent);
		},

		fnSetCurrentUser: function (callback) {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			oThisController.fnProcessDataRequest("../user", "GET", null, false, function (oXHR, status) {
				if (oXHR && oXHR.responseJSON) {
					oThisController._oCommon.userDetails = oXHR.responseJSON;
					if (!oXHR.responseJSON.emails) {
						oXHR.responseJSON.emails = "testing@email.com";
					}
					oMdlCommon.setProperty("/userDetails", oXHR.responseJSON);
					oMdlCommon.refresh();
				}
			}, null);

		},

		//navigation in tnt layout

		onChangeCBox: function (oEvent) {
			var oCBox = oEvent.getSource();
			if (oCBox.getSelectedKey()) {
				oCBox.setValueState("None");
			} else {
				oCBox.setValueState("Error");
			}
		},

		onChangeOptnlCBox: function (oEvent) {
			var oCBox = oEvent.getSource();
			if (oCBox.getSelectedKey() || !oCBox.getValue()) {
				oCBox.setValueState("None");
			} else {
				oCBox.setValueState("Error");
			}
		},

		onChngCBoxShip: function (oEvent) {
			var oMdlCommon = this.getModel("mCommon");
			var oCBox = oEvent.getSource();
			if (oCBox.getSelectedKey() === "-") {
				oMdlCommon.setProperty("/visible/bOriginFreeText", true);
			} else {
				oMdlCommon.setProperty("/visible/bOriginFreeText", false);
				this.onChangeCBox(oEvent);
			}
			oMdlCommon.refresh();
		},

		onChngCBoxDest: function (oEvent) {
			var oMdlCommon = this.getModel("mCommon");
			var oCBox = oEvent.getSource();
			if (oCBox.getSelectedKey() === "-") {
				oMdlCommon.setProperty("/visible/bDestFreeText", true);
			} else {
				oMdlCommon.setProperty("/visible/bDestFreeText", false);
				this.onChangeCBox(oEvent);
			}
			oMdlCommon.refresh();
		},

		onChangeDate: function (oEvent) {
			if (oEvent.getParameter("valid") && oEvent.getParameter("value")) {
				oEvent.getSource().setValueState("None");
			} else {
				oEvent.getSource().setValueState("Error");
			}
		},

		onInpChange: function (oEvent) {
			if (oEvent.getParameter("value")) {
				oEvent.getSource().setValueState("None");
			} else {
				oEvent.getSource().setValueState("Error");
			}
		},

		//Function to Get Country Url and Setting it to mPartModel
		fnSetLocations: function (sQuery, sPurpose) {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			/*	var sUrl = "/lch_services/ZTM_LOOKUPS_SRV/locationlookupSet",*/
			var sUrl = "/lch_services/adhocorders/getAllShipperDetails",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};

			sUrl += "?$filter=substringof('" + sQuery + "',LocDes) or substringof('" + sQuery + "',LocNo)";

			oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
				debugger;
				try {
					if (oXHR && oXHR.responseJSON && oXHR.responseText) {

						if (sPurpose === "Ship") {
							oMdlCommon.setProperty("/aLocShippers", oXHR.responseJSON);

						} else if (sPurpose === "Dest") {
							oMdlCommon.setProperty("/aLocReceivers", oXHR.responseJSON);
						}
					}
					oMdlCommon.refresh();
				} catch (e) {
					// console.log(e);
				}
			});
		},

		//Function to Get Destination Url and Setting it to mCommon
		fnGetLookUpDestination: function () {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var sUrl = "/lch_services/adhocorders/getAllDivisions",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};

			oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
				try {
					console.log(oXHR);
					if (oXHR && oXHR.responseJSON && oXHR.responseText) {
						var aLoc = $.extend(true, [], oXHR.responseJSON);
						console.log(aLoc);
						oMdlCommon.setProperty("/aDestination", aLoc);
					}
					oMdlCommon.refresh();
				} catch (e) {
					// console.log(e);
				}
			});

		},

		//Function to Get ReasonCodes and Setting it to mCommon
		fnGetReasonCodes: function () {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var sUrl = "/lch_services/adhocorders/getReasonCode",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};

			oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON && oXHR.responseJSON.aReasonCodes instanceof Array) {
						if (oXHR.responseJSON.aReasonCodes.length) {
							oMdlCommon.setProperty("/aReasonCodes", oXHR.responseJSON.aReasonCodes);
						}
					}
					oMdlCommon.refresh();
				} catch (e) {
					// console.log(e);
				}
			});

		},

		onLiveChangeEmail: function (oEvent) {
			var oMdlCommon = this.getParentModel("mCommon");
			var oControl = oEvent.getSource();
			var oRegExp = new RegExp(
				/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/);
			if (oRegExp.test(oEvent.getParameter("value"))) {
				oMdlCommon.setProperty("/valState/plannerEmail", "None");
			} else {
				oMdlCommon.setProperty("/valState/plannerEmail", "Error");
			}
			oMdlCommon.refresh();
		}

	});

});