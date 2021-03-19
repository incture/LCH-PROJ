sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/core/routing/History",
	"sap/m/MessageBox",
	"com/incture/lch/Adhoc/utility/Formatter",
	"com/incture/lch/Adhoc/utility/DateHelper",
	"sap/ui/model/json/JSONModel",
	"sap/ui/model/Filter",
	"sap/ui/model/FilterOperator",
	"sap/ui/model/odata/v2/ODataModel"
], function (Controller, History, MessageBox, Formatter, DateHelper, JSONModel, Filter, FilterOperator, ODataModel) {
	"use strict";

	return Controller.extend("com.incture.lch.Adhoc.controller.BaseController", {

		formatter: Formatter,

		dateHelper: DateHelper,

		_oCommon: {},

		fnInitializeApp: function () {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var sRootPath = jQuery.sap.getModulePath("com.incture.lch.Adhoc");
			oMdlCommon.attachRequestCompleted(function (oEvent) {
				oThisController.fnSetCurrentUser();
				oMdlCommon.setProperty("/today", new Date());
				oMdlCommon.refresh();
				oThisController.onISelMenu(undefined, "kOrder");
				oThisController.fnGetLookUpDestination();
				oThisController.fnGetReasonCodes();

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
			busyIndicatorDelay: 0
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
		 * @purpose Delayed execution
		 * @param1 callback -- callback function to be executed
		 * @param2 delayMicroSeconds -- delay in micro seconds
		 */
		fnDelayedCall: function (callback, delayMicroSeconds) {
			var delay = (delayMicroSeconds && delayMicroSeconds > 0) ? delayMicroSeconds : 0;
			jQuery.sap.delayedCall(delay, null, callback);
		},

		/*
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

		//All Generic Functions that are not specific to the App will be above this line
		/*
				fnSetCurrentUser: function (callback) {
					var oThisController = this;
					var oMdlCommon = this.getModel("mCommon");
					oThisController.fnProcessDataRequest("UserDetails/currentuser", "GET", null, false, function (oXHR, status) {
						if (oXHR && oXHR.responseJSON) {
							oThisController._oCommon.userDetails = oXHR.responseJSON;

							if (!oXHR.responseJSON.userEmail) {
								oXHR.responseJSON.userEmail = "testing@email.com";
							}

							oMdlCommon.setProperty("/userDetails", oXHR.responseJSON);
							oMdlCommon.refresh();

							oThisController.fnGetLaunchPadRoles(callback);
						}
					}, null);
				},
		*/
		/*	fnGetAuthRules: function (callback) {
				var oThisController = this;
				var oMdlCommon = this.getModel("mCommon");
				var aCockpitRoles = $.extend(true, [], oMdlCommon.getProperty("/aCockpitRoles"));
				var oPayLoad = {
					"RuleServiceId": "82304f62fc784b239cc8af849d82f3dd",
					"Vocabulary": []
				};

				var oHeaders = {
					"Content-Type": "application/json",
					"X-CSRF-Token": ""
				};

				for (var i = 0; i < aCockpitRoles.length; i++) {
					oPayLoad.Vocabulary.push({
						"TransportRuleInputForAdhoc": {
							"Application": "adhoc",
							"SupplierGroup": aCockpitRoles[i],
							"Division": "PROD"
						}
					});
				}

				oThisController.fnProcessDataRequest("/bpmrulesruntime_auth/rules-service/rest/v2/xsrf-token", "GET", {
						"X-CSRF-Token": "Fetch"
					}, false,
					function (oXHR1, status1) {
						oHeaders["X-CSRF-Token"] = oXHR1.getResponseHeader("X-CSRF-Token");
						if (oHeaders["X-CSRF-Token"]) {
							oThisController.fnProcessDataRequest("/bpmrulesruntime_auth/rules-service/rest/v2/workingset-rule-services", "POST", oHeaders,
								false,
								function (oXHR, status) {
									if (oXHR && oXHR.responseJSON) {
										var aRules = [];
										for (var j = 0; j < oXHR.responseJSON.Result.length; j++) {
											aRules.push(oXHR.responseJSON.Result[j].TransportRuleOutputForAdhoc);
										}

										if (aRules.some(function (row) {
												return row.bEditPremium;
											})) {
											oMdlCommon.setProperty("/bEditPremium", true);
										}

										oMdlCommon.refresh();
										if (callback) {
											callback(oXHR, status);
										}
									}
								}, oPayLoad);
						}
					}, null);
			},*/

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
		onISelMenu: function (oEvent, key) {
			var oMdlCommon = this.getModel("mCommon");
			this._oRouter = this.getRouter();
			if (!key) {
				key = oEvent.getParameter("item").getKey();
			}

			oMdlCommon.setProperty("/selNavigationKey", key);
			oMdlCommon.setProperty("/selNavKeyBckUp", key);

			if (key === "kHistory" && location.hash.indexOf("/history") === -1) {
				this._oRouter.navTo("History");
			}
			if (key === "kOrder" && location.hash.indexOf("/create") === -1) {
				this._oRouter.navTo("Order", {
					"accessMode": "create"
				}, true);
			}
			if (key === "kApprove" && location.hash.indexOf("/approval") === -1) {
				this._oRouter.navTo("Approval");
			}
		},

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

		fnGetHistory: function (bBusy) {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");

			if (bBusy) {
				oThisController.openBusyDialog();
			}
			var oPayLoad = {
				"adhocOrderId": "",
				"createdBy": oThisController._oCommon.userDetails.id,
				"partNo": "",
				"fromDate": "",
				"toDate": "",
				"createdDate": ""
			};
			var oHeaders = {
				"Content-Type": "application/json",
				"Accept": "application/json"
			};

			oThisController.fnProcessDataRequest("/lch_services/adhocorders/getAdhocOrders", "POST", oHeaders, true,
				function (oXHR, status) {
					if (!oXHR.responseJSON) {
						oXHR.responseJSON = [];
					}

					$.each(oXHR.responseJSON, function (index, oRow) {
						oRow.createdDate = oThisController.dateHelper.getDateObj(oRow.createdDate, "JgwDate");
						oRow.expectedDeliveryDate = oThisController.dateHelper.getDateObj(oRow.expectedDeliveryDate, "JgwDate");
						oRow.shipDate = oThisController.dateHelper.getDateObj(oRow.shipDate, "JgwDate");
						oRow.updatedDate = oThisController.dateHelper.getDateObj(oRow.updatedDate, "JgwDate");
					});
					oMdlCommon.setProperty("/aHistory", oXHR.responseJSON);
					oMdlCommon.setProperty("/iLenHistory", oXHR.responseJSON.length);
					oMdlCommon.setProperty("/aAllOrders", oXHR.responseJSON);
					oMdlCommon.setProperty("/iLenAllOrders", oXHR.responseJSON.length);
					oMdlCommon.refresh();

					if (bBusy) {
						oThisController.closeBusyDialog();
					}

				}, oPayLoad);

			oThisController.fnProcessDataRequest("/lch_services/adhocorders/getKpiBasedResult/" + 1, "POST", oHeaders, true,
				function (oXHR, status) {
					if (!oXHR.responseJSON) {
						oXHR.responseJSON = [];
					}

					$.each(oXHR.responseJSON, function (index, oRow) {
						oRow.createdDate = oThisController.dateHelper.getDateObj(oRow.createdDate, "JgwDate");
						oRow.expectedDeliveryDate = oThisController.dateHelper.getDateObj(oRow.expectedDeliveryDate, "JgwDate");
						oRow.shipDate = oThisController.dateHelper.getDateObj(oRow.shipDate, "JgwDate");
						oRow.updatedDate = oThisController.dateHelper.getDateObj(oRow.updatedDate, "JgwDate");
					});
					oMdlCommon.setProperty("/a24Hours", oXHR.responseJSON);
					oMdlCommon.setProperty("/iLen24Hours", oXHR.responseJSON.length);
					oMdlCommon.refresh();

					if (bBusy) {
						oThisController.closeBusyDialog();
					}

				}, oPayLoad);

			oThisController.fnProcessDataRequest("/lch_services/adhocorders/getKpiBasedResult/" + 3, "POST", oHeaders, true,
				function (oXHR, status) {
					if (!oXHR.responseJSON) {
						oXHR.responseJSON = [];
					}

					$.each(oXHR.responseJSON, function (index, oRow) {
						oRow.createdDate = oThisController.dateHelper.getDateObj(oRow.createdDate, "JgwDate");
						oRow.expectedDeliveryDate = oThisController.dateHelper.getDateObj(oRow.expectedDeliveryDate, "JgwDate");
						oRow.shipDate = oThisController.dateHelper.getDateObj(oRow.shipDate, "JgwDate");
						oRow.updatedDate = oThisController.dateHelper.getDateObj(oRow.updatedDate, "JgwDate");
					});
					oMdlCommon.setProperty("/a72Hours", oXHR.responseJSON);
					oMdlCommon.setProperty("/iLen72Hours", oXHR.responseJSON.length);
					oMdlCommon.refresh();

					if (bBusy) {
						oThisController.closeBusyDialog();
					}

				}, oPayLoad);

			oThisController.fnProcessDataRequest("/lch_services/adhocorders/getDrafts", "POST", oHeaders, true,
				function (oXHR, status) {
					if (!oXHR.responseJSON) {
						oXHR.responseJSON = [];
					}

					$.each(oXHR.responseJSON, function (index, oRow) {
						oRow.createdDate = oThisController.dateHelper.getDateObj(oRow.createdDate, "JgwDate");
						oRow.expectedDeliveryDate = oThisController.dateHelper.getDateObj(oRow.expectedDeliveryDate, "JgwDate");
						oRow.shipDate = oThisController.dateHelper.getDateObj(oRow.shipDate, "JgwDate");
						oRow.updatedDate = oThisController.dateHelper.getDateObj(oRow.updatedDate, "JgwDate");
					});
					oMdlCommon.setProperty("/aDrafts", oXHR.responseJSON);
					oMdlCommon.setProperty("/iLenDrafts", oXHR.responseJSON.length);
					oMdlCommon.refresh();

					if (bBusy) {
						oThisController.closeBusyDialog();
					}

				}, oPayLoad);
			oThisController.onGetCustomization(false, false);
		},

		onChangeDate: function (oEvent) {
			if (oEvent.getParameter("valid") && oEvent.getParameter("value")) {
				oEvent.getSource().setValueState("None");
			} else {
				oEvent.getSource().setValueState("Error");
			}
		},

		onClearAll: function (oEvent) {
			var oMdlCommon = this.getParentModel("mCommon");
			oMdlCommon.setProperty("/bNewShip", false);
			oMdlCommon.setProperty("/sIconShip", "sap-icon://write-new");
			oMdlCommon.setProperty("/bNewDest", false);
			oMdlCommon.setProperty("/sIconDest", "sap-icon://write-new");
			oMdlCommon.setProperty("/newFU/adhocType", "");
			oMdlCommon.setProperty("/newFU/adhocOrderId", "");
			oMdlCommon.setProperty("/newFU/businessDivision", "");
			oMdlCommon.setProperty("/newFU/charge", false);
			oMdlCommon.setProperty("/newFU/countryOrigin", "");
			oMdlCommon.setProperty("/newFU/createdBy", "");
			oMdlCommon.setProperty("/newFU/createdDate", null);
			oMdlCommon.setProperty("/newFU/currency", "USD"); // Default Value
			oMdlCommon.setProperty("/newFU/customerOrderNo", "");
			oMdlCommon.setProperty("/newFU/destinationAddress", "");
			oMdlCommon.setProperty("/newFU/destinationCity", "");
			oMdlCommon.setProperty("/newFU/destinationName", "");
			oMdlCommon.setProperty("/newFU/destinationNameFreeText", "");
			oMdlCommon.setProperty("/newFU/destinationState", "");
			oMdlCommon.setProperty("/newFU/destinationZip", "");
			oMdlCommon.setProperty("/newFU/destinationCountry", "");
			oMdlCommon.setProperty("/newFU/dimensionL", "");
			oMdlCommon.setProperty("/newFU/dimensionH", "");
			oMdlCommon.setProperty("/newFU/dimensionB", "");
			oMdlCommon.setProperty("/newFU/dimensionsUom", "IN"); // Default Value
			oMdlCommon.setProperty("/newFU/expectedDeliveryDate", null);
			oMdlCommon.setProperty("/newFU/glcode", "");
			oMdlCommon.setProperty("/newFU/hazmatNumber", "");
			oMdlCommon.setProperty("/newFU/hazmatUn", "");
			oMdlCommon.setProperty("/newFU/originAddress", "");
			oMdlCommon.setProperty("/newFU/originCity", "");
			oMdlCommon.setProperty("/newFU/originState", "");
			oMdlCommon.setProperty("/newFU/originZip", "");
			oMdlCommon.setProperty("/newFU/originCountry", "");
			oMdlCommon.setProperty("/newFU/isHazmat", false);
			oMdlCommon.setProperty("/newFU/isInternational", false);
			oMdlCommon.setProperty("/newFU/isTruck", false);
			oMdlCommon.setProperty("/newFU/packageType", "");
			oMdlCommon.setProperty("/newFU/partNum", "");
			oMdlCommon.setProperty("/newFU/partNumText", "");
			oMdlCommon.setProperty("/newFU/partDescription", "");
			oMdlCommon.setProperty("/newFU/podataNumber", "");
			oMdlCommon.setProperty("/newFU/premiumFreight", false);
			oMdlCommon.setProperty("/newFU/premiumReasonCode", ""); //New
			oMdlCommon.setProperty("/newFU/plannerEmail", ""); //New
			oMdlCommon.setProperty("/newFU/projectNumber", "");
			oMdlCommon.setProperty("/newFU/quantity", "");
			oMdlCommon.setProperty("/newFU/reasonCode", "");
			oMdlCommon.setProperty("/newFU/receivingContact", "");
			oMdlCommon.setProperty("/newFU/referenceNumber", "");
			oMdlCommon.setProperty("/newFU/shipDate", null);
			oMdlCommon.setProperty("/newFU/shipperName", "");
			oMdlCommon.setProperty("/newFU/shipperNameFreeText", "");
			oMdlCommon.setProperty("/newFU/shippingInstruction", "");
			oMdlCommon.setProperty("/newFU/shippingContact", "");
			oMdlCommon.setProperty("/newFU/terms", "");
			oMdlCommon.setProperty("/newFU/updatedBy", "");
			oMdlCommon.setProperty("/newFU/updatedDate", null);
			oMdlCommon.setProperty("/newFU/userId", "");
			oMdlCommon.setProperty("/newFU/userName", ""); //New
			oMdlCommon.setProperty("/newFU/userEmail", ""); //New
			oMdlCommon.setProperty("/newFU/uom", "EA"); // Default Value
			oMdlCommon.setProperty("/newFU/value", "");
			oMdlCommon.setProperty("/newFU/vinNumber", "");
			oMdlCommon.setProperty("/newFU/weight", "");
			oMdlCommon.setProperty("/newFU/weightUom", "LBS"); // Default Value
			oMdlCommon.setProperty("/newFU/bRequiredOrigni", false);
			oMdlCommon.setProperty("/newFU/bRequiredDest", false);
			oMdlCommon.setProperty("/enable/bHazmatNumber", false);
			oMdlCommon.setProperty("/enable/bVINNumber", false);
			oMdlCommon.setProperty("/valState/adhocType", "None");
			oMdlCommon.setProperty("/valState/businessDivision", "None");
			oMdlCommon.setProperty("/valState/countryOrigin", "None");
			oMdlCommon.setProperty("/valState/shipperName", "None");
			oMdlCommon.setProperty("/valState/destinationName", "None");
			oMdlCommon.setProperty("/valState/partDescription", "None");
			oMdlCommon.setProperty("/valState/partNum", "None");
			oMdlCommon.setProperty("/valState/shipDate", "None");
			oMdlCommon.setProperty("/valState/quantity", "None");
			oMdlCommon.setProperty("/valState/qtyUom", "None");
			oMdlCommon.setProperty("/valState/weightUom", "None");
			oMdlCommon.setProperty("/valState/expectedDeliveryDate", "None");
			oMdlCommon.setProperty("/valState/sOriginAddress", "None");
			oMdlCommon.setProperty("/valState/sOriginCity", "None");
			oMdlCommon.setProperty("/valState/sOriginState", "None");
			oMdlCommon.setProperty("/valState/sOriginZip", "None");
			oMdlCommon.setProperty("/valState/sOriginCountry", "None");
			oMdlCommon.setProperty("/valState/sDestinationAddress", "None");
			oMdlCommon.setProperty("/valState/sDestinationCity", "None");
			oMdlCommon.setProperty("/valState/sDestinationState", "None");
			oMdlCommon.setProperty("/valState/sDestinationZip", "None");
			oMdlCommon.setProperty("/valState/sDestinationCountry", "None");
			oMdlCommon.setProperty("/valState/premiumReasonCode", "None");
			oMdlCommon.setProperty("/valState/plannerEmail", "None");

			oMdlCommon.refresh();
		},

		onSubmit: function (oEvent) {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var oDataPayload = $.extend(true, {}, oMdlCommon.getProperty("/newFU"));
			var bRequiredOrigni = oDataPayload.bRequiredOrigni;
			var bRequiredDest = oDataPayload.bRequiredDest;
			var bValidData = true,
				bMandatoryFields = true,
				sUrl = "/lch_services/adhocorders/addAdhocOrders",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};

			oThisController.openBusyDialog();
			//Validation for Free text entries
			if (bRequiredOrigni) {
				if (!oDataPayload.originAddress) {
					oMdlCommon.setProperty("/valState/sOriginAddress", "Error");
					bMandatoryFields = false;
				}
				if (!oDataPayload.originCity) {
					oMdlCommon.setProperty("/valState/sOriginCity", "Error");
					bMandatoryFields = false;
				}
				if (!oDataPayload.originState) {
					oMdlCommon.setProperty("/valState/sOriginState", "Error");
					bMandatoryFields = false;
				}
				if (!oDataPayload.originZip) {
					oMdlCommon.setProperty("/valState/sOriginZip", "Error");
					bMandatoryFields = false;
				}
				if (!oDataPayload.originCountry) {
					oMdlCommon.setProperty("/valState/sOriginCountry", "Error");
					bMandatoryFields = false;
				}
			}

			if (bRequiredDest) {
				if (!oDataPayload.destinationAddress) {
					oMdlCommon.setProperty("/valState/sDestinationAddress", "Error");
					bMandatoryFields = false;
				}
				if (!oDataPayload.destinationCity) {
					oMdlCommon.setProperty("/valState/sDestinationCity", "Error");
					bMandatoryFields = false;
				}
				if (!oDataPayload.destinationState) {
					oMdlCommon.setProperty("/valState/sDestinationState", "Error");
					bMandatoryFields = false;
				}
				if (!oDataPayload.destinationZip) {
					oMdlCommon.setProperty("/valState/sDestinationZip", "Error");
					bMandatoryFields = false;
				}
				if (!oDataPayload.destinationCountry) {
					oMdlCommon.setProperty("/valState/sDestinationCountry", "Error");
					bMandatoryFields = false;
				}
			}

			//Validations start here
			if (!oDataPayload.adhocType) {
				oMdlCommon.setProperty("/valState/adhocType", "Error");
				bMandatoryFields = false;
			}
			if (!oDataPayload.shipperName) {
				oMdlCommon.setProperty("/valState/shipperName", "Error");
				bMandatoryFields = false;
			} else {
				oDataPayload.shipperNameDesc = oDataPayload.shipperNameFreeText;
				oDataPayload.shipperNameFreeText = "";
			}

			if (!oDataPayload.destinationName) {
				oMdlCommon.setProperty("/valState/destinationName", "Error");
				bMandatoryFields = false;
			} else {
				oDataPayload.destinationNameDesc = oDataPayload.destinationNameFreeText;
				oDataPayload.destinationNameFreeText = "";
			}

			if (!oDataPayload.businessDivision) {
				oMdlCommon.setProperty("/valState/businessDivision", "Error");
				bMandatoryFields = false;
			}

			if (oDataPayload.isInternational && !oDataPayload.countryOrigin) {
				oMdlCommon.setProperty("/valState/countryOrigin", "Error");
				bMandatoryFields = false;
			}

			if (!oDataPayload.partDescription) {
				oMdlCommon.setProperty("/valState/partDescription", "Error");
				bMandatoryFields = false;
			}

			if (!oDataPayload.quantity) {
				oMdlCommon.setProperty("/valState/quantity", "Error");
				if (!oDataPayload.uom) {
					oMdlCommon.setProperty("/valState/qtyUom", "Error");
				}
				bMandatoryFields = false;
			}

			if (oDataPayload.shipDate && oDataPayload.expectedDeliveryDate) {
				if (oDataPayload.shipDate.getTime() > oDataPayload.expectedDeliveryDate.getTime()) {
					oMdlCommon.setProperty("/valState/shipDate", "Error");
					oMdlCommon.setProperty("/valState/expectedDeliveryDate", "Error");
					oThisController.showMessage(oThisController.getMessage("INVALID_DATE_SHIP_DELIVERY"), "E");
					oThisController.closeBusyDialog();
					return;
				}
			} else {
				if (!oDataPayload.shipDate) {
					oMdlCommon.setProperty("/valState/shipDate", "Error");
				}

				if (!oDataPayload.expectedDeliveryDate) {
					oMdlCommon.setProperty("/valState/expectedDeliveryDate", "Error");
				}

				bMandatoryFields = false;
			}

			//Added for validating Shipping Details start here
			if (!oDataPayload.weight) {
				oMdlCommon.setProperty("/valState/weight", "Error");
				bMandatoryFields = false;
			} else {
				oMdlCommon.setProperty("/valState/weight", "None");
			}

			if (!oDataPayload.weightUom) {
				oMdlCommon.setProperty("/valState/weightUom", "Error");
				bMandatoryFields = false;
			} else {
				oMdlCommon.setProperty("/valState/weightUom", "None");
			}

			if (!oDataPayload.packageType) {
				oMdlCommon.setProperty("/valState/packageType", "Error");
				bMandatoryFields = false;
			} else {
				oMdlCommon.setProperty("/valState/packageType", "None");
			}

			if (!oDataPayload.terms) {
				oMdlCommon.setProperty("/valState/terms", "Error");
				bMandatoryFields = false;
			} else {
				oMdlCommon.setProperty("/valState/terms", "None");
			}

			if (!oDataPayload.dimensionsUom) {
				oMdlCommon.setProperty("/valState/dimensionsUom", "Error");
				bMandatoryFields = false;
			} else {
				oMdlCommon.setProperty("/valState/dimensionsUom", "None");
			}

			if (!oDataPayload.dimensionL) {
				oMdlCommon.setProperty("/valState/dimensionL", "Error");
				bMandatoryFields = false;
			} else {
				oMdlCommon.setProperty("/valState/dimensionL", "None");
			}

			if (!oDataPayload.dimensionB) {
				oMdlCommon.setProperty("/valState/dimensionB", "Error");
				bMandatoryFields = false;
			} else {
				oMdlCommon.setProperty("/valState/dimensionB", "None");
			}

			if (!oDataPayload.dimensionH) {
				oMdlCommon.setProperty("/valState/dimensionH", "Error");
				bMandatoryFields = false;
			} else {
				oMdlCommon.setProperty("/valState/dimensionH", "None");
			}

			if (oDataPayload.premiumFreight) {
				if (!oDataPayload.premiumReasonCode) {
					oMdlCommon.setProperty("/valState/premiumReasonCode", "Error");
					bMandatoryFields = false;
				} else {
					oMdlCommon.setProperty("/valState/premiumReasonCode", "None");
				}

				if (!oDataPayload.plannerEmail) {
					oMdlCommon.setProperty("/valState/plannerEmail", "Error");
					bMandatoryFields = false;
				} else {
					oMdlCommon.setProperty("/valState/plannerEmail", "None");
				}
			}
			//Added for validating Shipping Details end here

			if (!bMandatoryFields) {
				oThisController.showMessage(oThisController.getMessage("MANDATORY_DATA"), "E");
				oThisController.closeBusyDialog();
				return;
			} else {
				oMdlCommon.setProperty("/valState/adhocType", "None");
				oMdlCommon.setProperty("/valState/businessDivision", "None");
				oMdlCommon.setProperty("/valState/countryOrigin", "None");
				oMdlCommon.setProperty("/valState/shipperName", "None");
				oMdlCommon.setProperty("/valState/destinationName", "None");
				oMdlCommon.setProperty("/valState/partDescription", "None");
				oMdlCommon.setProperty("/valState/partNum", "None");
				oMdlCommon.setProperty("/valState/shipDate", "None");
				oMdlCommon.setProperty("/valState/expectedDeliveryDate", "None");
				oMdlCommon.setProperty("/valState/sOriginAddress", "None");
				oMdlCommon.setProperty("/valState/sOriginCity", "None");
				oMdlCommon.setProperty("/valState/sOriginState", "None");
				oMdlCommon.setProperty("/valState/sOriginZip", "None");
				oMdlCommon.setProperty("/valState/sOriginCountry", "None");
				oMdlCommon.setProperty("/valState/sDestinationAddress", "None");
				oMdlCommon.setProperty("/valState/sDestinationCity", "None");
				oMdlCommon.setProperty("/valState/sDestinationState", "None");
				oMdlCommon.setProperty("/valState/sDestinationZip", "None");
				oMdlCommon.setProperty("/valState/sDestinationCountry", "None");
				oMdlCommon.setProperty("/valState/premiumReasonCode", "None");
				oMdlCommon.setProperty("/valState/plannerEmail", "None");
			}

			var oValState = $.extend(true, {}, oMdlCommon.getProperty("/valState"));
			for (var prop in oValState) {
				if (oValState[prop] === "Error") {
					bValidData = false;
					break;
				}
			}

			if (!bValidData) {
				oThisController.showMessage(oThisController.getMessage("INVALID_DATA"), "E");
				oThisController.closeBusyDialog();
				return;
			}

			//Validations end here

			oDataPayload.createdDate = oThisController.dateHelper.getBackEndDate(new Date());
			oDataPayload.updatedDate = oThisController.dateHelper.getBackEndDate(new Date());

			if (oDataPayload.expectedDeliveryDate) {
				oDataPayload.expectedDeliveryDate = oThisController.dateHelper.getBackEndDate(oDataPayload.expectedDeliveryDate);
			}

			if (oDataPayload.shipDate) {
				oDataPayload.shipDate = oThisController.dateHelper.getBackEndDate(oDataPayload.shipDate);
			}

			oDataPayload.createdBy = oThisController._oCommon.userDetails.name.familyName;
			oDataPayload.updatedBy = oThisController._oCommon.userDetails.name.familyName;
			oDataPayload.userId = oThisController._oCommon.userDetails.id;

			oDataPayload.userName = oThisController._oCommon.userDetails.name.givenName;
			oDataPayload.userEmail = oThisController._oCommon.userDetails.emails[0].value;

			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, true, function (oXHR, status) {
				if (oXHR.responseJSON) {
					if (oXHR.responseJSON.status === "SUCCESS") {
						oThisController.showMessage(oThisController.getMessage("ADHOC_CREATED"), "I", function () {
							oThisController.onClearAll(oEvent);
							oThisController.fnGetHistory();
						});
					} else {
						oThisController.showMessage(oThisController.getMessage("REQUEST_OPERATION_FAILED"), "E");
					}
				} else {
					oThisController.showMessage(oThisController.getMessage("REQUEST_OPERATION_FAILED"), "E");
				}
			}, oDataPayload);

		},

		onSuggestParts: function (oEvent) {
			debugger;
			var oThisController = this;
			let oInpPart = oEvent.getSource();
			let sQuery = oInpPart.getValue();
			var oMdlCommon = this.getParentModel("mCommon"),
				oPayLoad = {
					"partNum": sQuery

				},
				sUrl = "/lch_services/adhocorders/getByPartNumber",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};
			oMdlCommon.setProperty("/bPartNumBusy", true);
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				if (status === "success") {
					if (oXHR.responseJSON) {
						oMdlCommon.setProperty("/aParts", oXHR.responseJSON);
						oMdlCommon.setProperty("/newFU/partDescription", oXHR.responseJSON.partDesc);
					}
					oMdlCommon.setProperty("/bPartNumBusy", false);
					oMdlCommon.refresh();
				}
			}, oPayLoad);
			console.log(oMdlCommon);

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
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var sUrl = "/lch_services/adhocorders/getAllShipperDetails",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};

			sUrl += "?$filter=substringof('" + sQuery + "',LocDes) or substringof('" + sQuery + "',LocNo)";
			oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
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

		onSaveDraft: function () {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			console.log($.extend(true, {}, oMdlCommon.getProperty("/newFU")));
			console.log(oMdlCommon.getProperty("/newFU"));
			var oDataPayload = oMdlCommon.getProperty("/newFU");
			oDataPayload.createdBy = oThisController._oCommon.userDetails.name.familyName;
			oDataPayload.updatedBy = oThisController._oCommon.userDetails.name.familyName;
			oDataPayload.userId = oThisController._oCommon.userDetails.id;
			oDataPayload.userName = oThisController._oCommon.userDetails.name.givenName;
			oDataPayload.userEmail = oThisController._oCommon.userDetails.emails[0].value;
			var sUrl = "/lch_services/adhocorders/saveAdhocOrders",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};

			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, true, function (oXHR, status) {
				if (status === "success") {
					debugger;
					if (oXHR.status === 200 && oXHR.statusText === "OK") {
						oThisController.showMessage(oThisController.getMessage("Draft Saved Successfuly!"), "I", function () {
						});
					} else {
						oThisController.showMessage(oThisController.getMessage("REQUEST_OPERATION_FAILED"), "E");
					}
				} else {
					oThisController.showMessage(oThisController.getMessage("REQUEST_OPERATION_FAILED"), "E");
				}
			}, oDataPayload);

		},

		onCustomization: function (oEvent) {
			let oThisController = this;

			if (!oThisController.oDlgCustomize) {
				oThisController.oDlgCustomize = sap.ui.xmlfragment("com.incture.lch.Adhoc.fragment.Customization", oThisController);
				oThisController.getView().addDependent(oThisController.oDlgCustomize);
			}

			oThisController.oDlgCustomize.open();

		},

		fnGetColumnLabel: function (sColumn) {
			var oColumnLabel = {
				"header": "",
				"desc": ""
			};
			switch (sColumn) {
			case "adhocOrderId":
				oColumnLabel.header = this.getMessage("ADHOC_ID");
				oColumnLabel.desc = this.getMessage("ADHOC_ORDER_ID");
				break;
			case "businessDivision":
				oColumnLabel.header = this.getMessage("BUSINESS_DIVISION");
				oColumnLabel.desc = this.getMessage("BUSINESS_DIVISION");
				break;
			case "countryOrigin":
				oColumnLabel.header = this.getMessage("COUNTRY_ORIGIN");
				oColumnLabel.desc = this.getMessage("COUNTRY_ORIGIN");
				break;
			case "createdBy":
				oColumnLabel.header = this.getMessage("CREATED_BY");
				oColumnLabel.desc = this.getMessage("CREATED_BY");
				break;
			case "createdDate":
				oColumnLabel.header = this.getMessage("CREATED_DATE");
				oColumnLabel.desc = this.getMessage("CREATED_DATE");
				break;
			case "currency":
				oColumnLabel.header = this.getMessage("CURRENCY");
				oColumnLabel.desc = this.getMessage("CURRENCY");
				break;
			case "destinationAddress":
				oColumnLabel.header = this.getMessage("DESTINATION_ADDRESS");
				oColumnLabel.desc = this.getMessage("DESTINATION_ADDRESS");
				break;
			case "destinationCity":
				oColumnLabel.header = this.getMessage("DESTINATION_CITY");
				oColumnLabel.desc = this.getMessage("DESTINATION_CITY");
				break;
			case "destinationName":
				oColumnLabel.header = this.getMessage("DESTINATION_NAME");
				oColumnLabel.desc = this.getMessage("DESTINATION_NAME");
				break;
			case "destinationState":
				oColumnLabel.header = this.getMessage("DESTINATION_STATE");
				oColumnLabel.desc = this.getMessage("DESTINATION_STATE");
				break;
			case "destinationZip":
				oColumnLabel.header = this.getMessage("DESTINATION_ZIP");
				oColumnLabel.desc = this.getMessage("DESTINATION_ZIP");
				break;
			case "expectedDeliveryDate":
				oColumnLabel.header = this.getMessage("EXP_DEL_DATE");
				oColumnLabel.desc = this.getMessage("EXPECTED_DELIVERY_DATE");
				break;
			case "originAddress":
				oColumnLabel.header = this.getMessage("ORIGIN_ADDRESS");
				oColumnLabel.desc = this.getMessage("ORIGIN_ADDRESS");
				break;
			case "originCity":
				oColumnLabel.header = this.getMessage("ORIGIN_CITY");
				oColumnLabel.desc = this.getMessage("ORIGIN_CITY");
				break;
			case "originState":
				oColumnLabel.header = this.getMessage("ORIGIN_STATE");
				oColumnLabel.desc = this.getMessage("ORIGIN_STATE");
				break;
			case "originZip":
				oColumnLabel.header = this.getMessage("ORIGIN_ZIP");
				oColumnLabel.desc = this.getMessage("ORIGIN_ZIP");
				break;
			case "packageType":
				oColumnLabel.header = this.getMessage("PACKAGE_TYPE");
				oColumnLabel.desc = this.getMessage("PACKAGE_TYPE");
				break;
			case "partNum":
				oColumnLabel.header = this.getMessage("PART_NUMBER");
				oColumnLabel.desc = this.getMessage("PART_NUMBER");
				break;
			case "partDescription":
				oColumnLabel.header = this.getMessage("PART_DESCRIPTION");
				oColumnLabel.desc = this.getMessage("PART_DESCRIPTION");
				break;

			case "quantity":
				oColumnLabel.header = this.getMessage("QUANTITY");
				oColumnLabel.desc = this.getMessage("QUANTITY");
				break;
			case "shipDate":
				oColumnLabel.header = this.getMessage("SHIP_DATE");
				oColumnLabel.desc = this.getMessage("SHIP_DATE");
				break;
			case "weight":
				oColumnLabel.header = this.getMessage("WEIGHT");
				oColumnLabel.desc = this.getMessage("WEIGHT");
				break;
			case "copy":
				oColumnLabel.header = this.getMessage("COPY");
				oColumnLabel.desc = this.getMessage("COPY");
				break;

			}
			return oColumnLabel;
		},

		fnGetColDesc: function (sColumn) {
			var oCol = this.fnGetColumnLabel(sColumn);
			return oCol.desc;
		},

		fnTableFactFU: function (sId, oContext) {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var oDate = new Date();
			var aColumn = $.extend(true, {}, oMdlCommon.getProperty("/aColumn"));
			var oColumn = null;
			var oRow = aColumn[Number(oContext.sPath.replace("/aColumn/", ""))];

			var oColumnLabel = this.fnGetColumnLabel(oRow.columnName);
			switch (oRow.columnName) {
			case "adhocOrderId":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Link({
			
						text: "{mCommon>adhocOrderId}",
						press: function (oEvent) {
								oThisController.onFuDetail(oEvent);
							}
						
					})
				});
				break;
			case "businessDivision":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>businessDivision}",
						maxLines: 1
					})
				});
				break;
			case "countryOrigin":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>countryOrigin}",
						maxLines: 1
					})
				});
				break;
			case "createdBy":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>createdBy}",
						maxLines: 1
					})
				});
				break;
			case "createdDate":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>createdDate}",
						maxLines: 1
					})
				});
				break;
			case "currency":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
				
						text: "{mCommon>currency}",
						maxLines: 1
					})
				});
				break;
			case "destinationAddress":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>destinationAddress}",
						maxLines: 1
					})
				});
				break;
			case "destinationCity":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>destinationCity}",
						maxLines: 1
					})
				});
				break;
			case "destinationName":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
				
						text: "{mCommon>destinationName}",
						maxLines: 1
					})
				});
				break;
			case "destinationState":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>destinationState}",
						maxLines: 1
					})
				});
				break;
			case "destinationZip":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>destinationZip}",
						maxLines: 1
					})
				});
				break;

			case "expectedDeliveryDate":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>expectedDeliveryDate}",
						maxLines: 1
					})
				});
				break;
			case "originAddress":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
						
						text: "{mCommon>originAddress}",
						maxLines: 1
					})
				});
				break;
			case "originCity":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>originCity}",
						maxLines: 1
					})
				});
				break;
			case "originState":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>originState}",
						maxLines: 1
					})
				});
				break;

			case "originZip":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>originZip}",
						maxLines: 1
					})
				});
				break;
			case "packageType":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>packageType}",
						maxLines: 1
					})
				});
				break;

			case "partNum":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>partNum}",
						maxLines: 1
					})
				});
				break;
			case "partDescription":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>partDescription}",
						maxLines: 1
					})
				});
				break;
			case "quantity":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>quantity}",
						maxLines: 1
					})
				});
				break;
			case "shipDate":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
						
						text: "{mCommon>shipDate}",
						maxLines: 1
					})
				});
				break;
			case "weight":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.m.Text({
					
						text: "{mCommon>weight}",
						maxLines: 1
					})
				});
				break;
			case "copy":
				oColumn = new sap.ui.table.Column({
					width: "7rem",
					label: new sap.m.Label({
						text: oColumnLabel.header,
						tooltip: oColumnLabel.desc,
						wrapping: true,
						design: "Bold"
					}),
					template: new sap.ui.core.Icon({
					
						src: "sap-icon://copy",
						press: function (oEvent) {
							oThisController.onCopyAdhocOrder(oEvent);
						}
					})
				});
				break;
			default:

			}
			if (oColumn) {
				oColumn.setBindingContext(new sap.ui.model.Context(oMdlCommon,
					"/aColumn"));
			}
			return oColumn;
		},

		onGetCustomization: function (bBusyOpen, bBusyClose) {
			debugger;
			var oThisController = this;
			var oMdlCommon = oThisController.getModel("mCommon");
			var aFUColumnBackUp = oMdlCommon.getProperty("/aFUColumnBackup");
			console.log(aFUColumnBackUp);
			try {
				var oMyPref = {
					"userId": oThisController._oCommon.userDetails.id
				};

				var sUrl = "/lch_services/preference/getPreference",
					oHeader = {
						"Content-Type": "application/json",
						"Accept": "application/json",
					};

				if (bBusyOpen) {
					oThisController.openBusyDialog();
				}

				oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false,
					function (oXHR, status) {
						try {
							debugger;
							var oPreference = oXHR.responseJSON
							console.log(oPreference);
							if (oPreference) {
								oMdlCommon.setProperty("/tabCust", oPreference);
								if (oPreference.columns instanceof Array && oPreference.columns
									.length === aFUColumnBackUp.length) {
									oPreference.columns = oPreference.columns.sort(function (a, b) {
										return a.sequence - b.sequence;
									});
									oMdlCommon.setProperty("/tabCust/columns", oPreference.columns);
									oMdlCommon.setProperty("/aColumn", $.grep(oPreference.columns,
										function (gRow) {
											return gRow.isVisible;
										}));
								} else {
									oMdlCommon.setProperty("/tabCust/columns", aFUColumnBackUp);
									oMdlCommon.setProperty("/aColumn", $.grep(aFUColumnBackUp,
										function (gRow) {
											return gRow.isVisible;
										}));
								}
							} else {
								oMdlCommon.setProperty("/tabCust", {});
								oMdlCommon.setProperty("/tabCust/columns", aFUColumnBackUp);
								oMdlCommon.setProperty("/aColumn", $.grep(aFUColumnBackUp,
									function (gRow) {
										return gRow.isVisible;
									}));
							}
							oMdlCommon.refresh();

						} catch (e) {
							// console.log(e);
							if (bBusyClose) {
								oThisController.closeBusyDialog();
							}
						}
					}, oMyPref);
			} catch (e) {
				oThisController.showMessage(oThisController.getMessage("EXCEPTION"),
					"E");
				oThisController.closeBusyDialog();
			}
		},

		onSaveCustomization: function (oEvent) {
			debugger;
			var oThisController = this;
			var oMdlCommon = oThisController.getModel("mCommon");
			var oTabCust = $.extend(true, {}, oMdlCommon.getProperty("/tabCust"));
			var oCol = null;
			var oMyPref = {
				"userId": "",
				"preferenceDTOs": []

			};
			oMyPref.userId = oThisController._oCommon.userDetails.id;
			var oDlg = oEvent.getSource().getParent();
			try {

				if (oTabCust && oTabCust.columns) {
					oTabCust.columns = oTabCust.columns.sort(function (a, b) {
						return a - b;
					});
				}

				for (var i = 0; i < oTabCust.columns.length; i++) {
					oCol = oTabCust.columns[i];
					oCol.sequence = (oCol.sequence) ? Number(oCol.sequence) : 10;
					oMyPref.preferenceDTOs.push(oCol);
				}

				var sUrl = "/lch_services/preference/setPreference",
					oHeader = {
						"Content-Type": "application/json",
						"Accept": "application/json",
						/*"role": "",
						"user": ""*/
					};

				oThisController.openBusyDialog();
				var aSequence = oMdlCommon.getProperty("/TableCustSeqArray");
				if (aSequence) {
					for (var j = 0; j < aSequence.length; j++) {
						for (var k = j + 1; k < aSequence.length; k++) {
							if ((aSequence[j] === "" || aSequence[j] === null ||
									aSequence[j] === 0) || aSequence[j] === aSequence[k]) {
								oThisController.showMessage(oThisController.getMessage(
									"INVALID_ORDER"), "E", function () {
									oThisController.closeBusyDialog();
								});
								return;
							}
						}
					}
				}

				oThisController.fnProcessDataRequest(sUrl, "POST", oHeader,
					false,
					function (oXHR, status) {
						debugger;
						try {
							if (oXHR.responseText === "success") {
								oThisController.showMessage(oThisController.getMessage(
									"PREFERENCE_SAVED"), "I", function () {
									oMdlCommon.refresh();
									oThisController.onGetCustomization(false, false);
									oThisController.oDlgCustomize.close();
								});
							} else {
								oThisController.showMessage(oThisController.getMessage(
									"REQUEST_OPERATION_FAILED"), "E");
							}
						} catch (e) {
							// console.log(e);
							oThisController.closeBusyDialog();
						}
						oThisController.closeBusyDialog();

					}, oMyPref);
			} catch (e) {
				oThisController.showMessage(oThisController.getMessage("EXCEPTION"),
					"E");
				oThisController.closeBusyDialog();
			}
		},

		fnNumberOnlyLiveChange: function (oEvent) {
			var oMdlCommon = this.getModel("mCommon");
			var oInput = oEvent.getSource();
			var value = oInput.getValue();
			oMdlCommon.refresh();
			value = value.replace(/[^0-9]/g, "");
			var aSequence = [];
			var aColumns = oMdlCommon.getProperty("/tabCust/columns");
			for (var i = 0; i < aColumns.length; i++) {
				var iSequence = Number(aColumns[i].sequence);
				aSequence.push(iSequence);
			}
			oMdlCommon.setProperty("/TableCustSeqArray", aSequence);
			var sPath = oInput.getBindingContext("mCommon").getPath();
			oInput.setValue(value);
		},

		onFuDetail: function (oEvent) {
			let oThisController = this;
			let sPath = oEvent.getSource().getBindingContext("mCommon").getPath();
			let oMdlCommon = this.getParentModel("mCommon");
			let oCurRow = $.extend(true, {}, oMdlCommon.getProperty(sPath))

			oMdlCommon.setProperty("/newFU", $.extend(true, {}, oCurRow));

			oMdlCommon.setProperty("/bIsEditable", false);
			oMdlCommon.setProperty("/enable/bHazmatNumber", false);
			oMdlCommon.setProperty("/enable/bVINNumber", false);
			oMdlCommon.setProperty("/selNavKeyBckUp", "kOrder");
			oMdlCommon.refresh(true);

			oThisController.fnDelayedCall(function () {
				if (oCurRow.shipperNameDesc) {
					oMdlCommon.setProperty("/newFU/shipperNameFreeText", oCurRow.shipperNameDesc);
				}

				if (oCurRow.destinationNameDesc) {
					oMdlCommon.setProperty("/newFU/destinationNameFreeText", oCurRow.destinationNameDesc);
				}

				if (oCurRow.partNum) {
					oMdlCommon.setProperty("/newFU/partNumText", oCurRow.partNum);
				}
			}, 500);

			this.getRouter().navTo("Order", {
				"accessMode": "view"
			}, true);
		},

		onCopyAdhocOrder: function (oEvent) {
			let oThisController = this;
			let sPath = oEvent.getSource().getBindingContext("mCommon").getPath();
			let oMdlCommon = this.getParentModel("mCommon");
			let oCurRow = $.extend(true, {}, oMdlCommon.getProperty(sPath))
			oCurRow.partNum = "";
			oCurRow.partNumText = "";
			oCurRow.partDescription = "";

			oMdlCommon.setProperty("/bCopyOrder", true);
			oMdlCommon.setProperty("/newFU", $.extend(true, {}, oCurRow));

			oMdlCommon.setProperty("/bIsEditable", false);
			oMdlCommon.setProperty("/enable/bHazmatNumber", false);
			oMdlCommon.setProperty("/enable/bVINNumber", false);
			oMdlCommon.refresh(true);

			oThisController.fnDelayedCall(function () {
				if (oCurRow.shipperNameDesc) {
					oMdlCommon.setProperty("/newFU/shipperNameFreeText", oCurRow.shipperNameDesc);
				}

				if (oCurRow.destinationNameDesc) {
					oMdlCommon.setProperty("/newFU/destinationNameFreeText", oCurRow.destinationNameDesc);
				}
			}, 500);

			this.getRouter().navTo("Order", {
				"accessMode": "edit"
			}, true);
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