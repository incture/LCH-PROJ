sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/core/routing/History",
	"sap/m/MessageBox",
	"com/incture/lch/premfreightOrders/utility/Formatter",
	"com/incture/lch/premfreightOrders/utility/DateHelper",
	"sap/ui/model/json/JSONModel",
	"sap/ui/model/Filter",
	"sap/ui/model/FilterOperator",
	"sap/base/Log",
	"com/incture/lch/premfreightOrders/control/ExtDateTimePicker"
], function (Controller, History, MessageBox, Formatter, DateHelper, JSONModel, Filter, FilterOperator, Log, ExtDateTimePicker) {
	"use strict";

	return Controller.extend("com.incture.lch.premfreightOrders.controller.BaseController", {

		formatter: Formatter,

		dateHelper: DateHelper,

		_oCommon: {},

		oLoadmore: {},

		iGroupId: 0, //to-do  move this to _oCommon

		/**
		 * @author Mohammed Saleem Bani
		 * @purpose Initialize the App 
		 */
		fnInitializeApp: function () {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			var sRootPath = jQuery.sap.getModulePath("com.incture.lch.premfreightOrders");
			oMdlCommon.attachRequestCompleted(function (oEvent) {
				oMdlCommon.setProperty("/today", new Date());
				oMdlCommon.refresh();
			/*	oThisController.fnManageSrvCall();*/

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

		fnGetPerformanceTime: function (fnName, dStartTime, dEndTime, sCatchError) {
			var sLogType;
			var dTimeTaken = dEndTime - dStartTime;
			var dTotalTime = (dTimeTaken / 1000).toFixed(5);
			if (sCatchError) {
				sLogType = "E";
			} else if (dTotalTime > "5.00000") {
				sLogType = "W";
			} else {
				sLogType = "I";
			}
			this.fnCreateLogMessage(fnName, dTotalTime, sLogType, sCatchError);
		},

		fnCreateLogMessage: function (fnName, dTimeTaken, sLogType, sCatchError) {
			var sMessage = "";
			switch (sLogType) {
			case "W":
				sMessage = (fnName + " took " + dTimeTaken + " seconds");
				break;
			case "E":
				sMessage = (fnName + " took " + dTimeTaken + " seconds" + ",\n " + sCatchError);
				break;
			case "F":
				sMessage = (fnName + " took " + dTimeTaken + " seconds");
				break;
			default:
				sMessage = (fnName + " took " + dTimeTaken + " seconds");
			}
			this.fnCreateLog(sMessage, sLogType);
		},

		fnCreateLog: function (sMessage, sType, oData) {
			switch (sType) {
			case "D":
				Log.debug("Debug: " + sMessage);
				if (oData) {
					Log.debug(oData);
				}
				break;
			case "E":
				Log.error("Error: " + sMessage);
				if (oData) {
					Log.error(oData);
				}
				break;
			case "F":
				Log.fatal("Fatal: " + sMessage);
				if (oData) {
					Log.fatal(oData);
				}
				break;
			case "W":
				Log.warning("Warning: " + sMessage);
				if (oData) {
					Log.warning(oData);
				}
				break;
			default:
				Log.info("Information: " + sMessage);
				if (oData) {
					Log.info(oData);
				}
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
		getModel: function (sName) {
			var oMdl = this.getOwnerComponent().getModel(sName);
			if (!oMdl) {
				oMdl = new JSONModel({});
				this.setModel(oMdl, sName);
			}
			oMdl.setSizeLimit(9999);
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
			return this.getOwnerComponent().setModel(oModel, sName);
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
						oThisController.showMessage(oThisController.getMessage("SESSION_EXPIRED", "I"), function () {
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
		fnGetLaunchPadRoles: function (callback) {
			var oThisController = this;
			var	oMdlCommon = this.getModel("mOrderModel");
			var sUrl = "/paccar_rest/freightunits/getRoles/";
			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json",
				"role": "",
				"user": ""
			};

			oHeader.user = oThisController._oCommon.userDetails.name;
			oHeader.role = oThisController._oCommon.userDetails.name;

			sUrl += oHeader.user;

			oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
				if (status === "success") {
					if (oXHR && oXHR.responseJSON && oXHR.responseJSON.roles) {
						oThisController._oCommon.userDetails["sRoles"] = oXHR.responseJSON.roles.toString();
						oMdlCommon.setProperty("/aCockpitRoles", oXHR.responseJSON.roles);
						oMdlCommon.refresh();

						oThisController.fnGetAuthRules(callback);
					}
				} else {
					oMdlCommon.setProperty("/cockpitRoles", []);
					oMdlCommon.refresh();
					
				}
			});
		},

		//All Generic Functions that are not specific to the App will be above this line

		fnSetCurrentUser: function (callback) {
			var oThisController = this;
			var oMdlCommon = this.getModel("mOrderModel");

			if (oThisController._oCommon && oThisController._oCommon.userDetails && oThisController._oCommon.userDetails.name) {
				if (callback) {
					// callback(oThisController._oCommon.userDetails, "success");
					oThisController.fnGetLaunchPadRoles(callback);
				}
			} else {
				oThisController.fnProcessDataRequest("../user", "GET", null, false, function (oXHR, status) {
					if (oXHR && oXHR.responseJSON) {
						oThisController._oCommon.userDetails = oXHR.responseJSON;
						oMdlCommon.setProperty("/userDetails", oXHR.responseJSON);
						oMdlCommon.refresh();

						if (callback) {
							oThisController.fnGetLaunchPadRoles(callback);
							// callback(oXHR, status);
						}
					}
				}, null);
			}
		}

		


	/*	fnSetUserInterFace: function (sAction) {
			var oOrderModel = this.getModel("mOrderModel");
			if (sAction === "Display") {
				oMdlCommon.setProperty("/oRoleConfig/bEditable", false);
			} else if (sAction === "Edit") {
				oMdlCommon.setProperty("/oRoleConfig/bEditable", true);
			} else {
				oMdlCommon.setProperty("/oRoleConfig/bEditable", true);
			}
		}*/

	});

});