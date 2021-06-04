sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/core/routing/History",
	"sap/ui/model/json/JSONModel",
	"sap/ui/model/Filter",
	"sap/ui/model/FilterOperator",
	"sap/m/MessageBox",
	"com/incture/lch/yardmanagement/utility/Formatter",
	"com/incture/lch/yardmanagement/utility/LabelFormatter",
	"com/incture/lch/yardmanagement/utility/DateHelper",
	"com/incture/lch/yardmanagement/control/ExtDateTimePicker",
	"sap/base/Log",
	"sap/ui/core/IconPool"
], function (Controller, History, JSONModel, Filter, FilterOperator, MessageBox, Formatter, LabelFormatter, DateHelper, ExtDateTimePicker,
	Log, IconPool) {
	"use strict";

	return Controller.extend("com.incture.lch.yardmanagement.controller.BaseController", {

		formatter: Formatter,

		labelFormater: LabelFormatter,

		dateHelper: DateHelper,

		constantsDef: {
			"matHandler": "LCH_MATERIAL_HANDLER",
			"yardAdmin": "LCH_YARD_ADMIN",
			"security": "LCH_SECURITY_GUARD",
			"tManager": "TRANSPORT_MANAGER",
			"internal": "INTERNAL"
		},

		_oCommon: {},

		_BusyDialog: new sap.m.BusyDialog({
			busyIndicatorDelay: 0 //,
				// customIcon:"./media/lodr-nw.gif",
				// customIconRotationSpeed:0,
				// customIconWidth:"150px",
				// customIconHeight:"150px"
		}),

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
			oModel.setSizeLimit(999999);
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
		 * Convenience method for opening App Busy Dialog.
		 * @public
		 */
		openBusyDialog: function () {
			this._BusyDialog.open();
		},

		/**
		 * Convenience method for closeing App Busy Dialog.
		 * @public
		 */
		closeBusyDialog: function () {
			this._BusyDialog.close();
		},

		/**
		 * Getter for the resource bundle.
		 * @public
		 * @returns {sap.ui.model.resource.ResourceModel} the resourceModel of the component
		 */
		getResourceBundle: function () {
			return this.getOwnerComponent().getModel("i18n").getResourceBundle();
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
		 * @purpose Initialize the App 
		 * @param {string} [sAction] Edit/Display/Create
		 */
		_loadIcons: function () {
			const aFonts = [{
				fontFamily: "SAP-icons-TNT",
				fontURI: sap.ui.require.toUrl("sap/tnt/themes/base/fonts/")
			}];
			aFonts.forEach(oFont => {
				IconPool.registerFont(oFont);
			});
		},

		fnInitializeApp: function (sAction) {
			var oThisController = this;
			oThisController._loadIcons();

			Object.defineProperty(oThisController.constantsDef, "matHandler", {
				writable: false
			});
			Object.defineProperty(oThisController.constantsDef, "yardAdmin", {
				writable: false
			});
			Object.defineProperty(oThisController.constantsDef, "security", {
				writable: false
			});
			Object.defineProperty(oThisController.constantsDef, "tManager", {
				writable: false
			});
			Object.defineProperty(oThisController.constantsDef, "internal", {
				writable: false
			});

			var oMdlCommon = oThisController.getParentModel("mCommon");
			var sRootPath = jQuery.sap.getModulePath("com.incture.lch.yardmanagement");
			oMdlCommon.attachRequestCompleted(function (oEvent) {
				/*	oMdlCommon.setProperty("/bEditable", true);*/
				/*	var oURLParsing = new sap.ushell.services.URLParsing();
					var url = oURLParsing.getHash(location.href);
					var oShellHash = oURLParsing.parseShellHash(url);
					oThisController.fnSetUserInterFace(oShellHash.action);*/
				oMdlCommon.setProperty("/today", new Date());
				//Loading LookUps and Other background services
				oThisController.fnGetAllYardStatus();
				oThisController.fnManageSrvCall();
				oThisController.fnGetLookUpOriginId();
				oThisController.fnGetLookUpDestination();
				oThisController.fnGetLookUpCarrier();
			});

			oMdlCommon.loadData(sRootPath + "/model/Property.json", null, false);
			this.dateFormat = sap.ui.core.format.DateFormat.getDateInstance({
				pattern: "yyyy-MM-dd"
			});
			var dToday = this.dateFormat.format(new Date());
			oMdlCommon.setProperty("/dToday", dToday);

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

		/**
		 * @author Mohammed Saleem Bani
		 * @purpose Message from Resource Bundle 
		 * @param1 pMessage -- String-Property of Resource Bundle
		 * @param2 aParametrs -- Array-Parameters
		 */
		getSNumberPadZeroes: function (value, length) {
			var sNumber = "" + value;
			while (sNumber.length < length) {
				sNumber = "0" + sNumber;
			}
			return sNumber;
		},

		//Function to get Fiori launchpad roles
		fnGetLaunchPadRoles: function (callback) {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var sUserId = oMdlCommon.getProperty("/userDetails/id");
			var sUrl = "/lch_services/lchRole/getYardRole/" + sUserId;
			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json"
			};
			oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
				if (status === "success") {
					if (oXHR && oXHR.responseJSON) {
						oMdlCommon.setProperty("/aCockpitRoles", oXHR.responseJSON);
						let sRole = oThisController.fnSetPendingWith();
						oThisController.fnSetActionsForRoles(sRole);
						oMdlCommon.refresh();
						if (callback) {
							callback(oXHR, status);
						}
					}
				} else {
					oMdlCommon.setProperty("/aCockpitRoles", []);
					oMdlCommon.refresh();
				}
			});
		},

		//function to get the current user
		fnSetCurrentUser: function (callback) {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			oThisController.fnProcessDataRequest("../user", "GET", null, false, function (oXHR, status) {
				if (oXHR && oXHR.responseJSON) {
					oThisController._oCommon.userDetails = oXHR.responseJSON;
					oMdlCommon.setProperty("/userDetails", oXHR.responseJSON);
					oMdlCommon.refresh();

					oThisController.fnGetLaunchPadRoles(function () {
						if (callback) {
							callback(oXHR, status);
						}
					});

				}
			}, null);
		},

		fnGetPerformanceTime: function (fnName, dStartTime, dEndTime, oData) {
			var sLogType;
			var dTimeTaken = dEndTime - dStartTime;
			var dTotalTime = (dTimeTaken / 1000).toFixed(5);
			if (dTotalTime > "5.00000") {
				sLogType = "W";
			} else {
				sLogType = "I";
			}
			this.fnCreateLogMessage(fnName, dTotalTime, sLogType, oData);
		},

		fnCreateLogMessage: function (fnName, dTimeTaken, sLogType, oData) {
			var sMessage = "";
			switch (sLogType) {
			case "W":
				sMessage = (fnName + " took " + dTimeTaken + " seconds");
				break;
			default:
				sMessage = (fnName + " took " + dTimeTaken + " seconds");
			}
			this.fnCreateLog(sMessage, sLogType, oData);
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

		//Function to check the current browser
		checkBrowser: function () {
			var browser = sap.ui.Device.browser;
			if (browser.hasOwnProperty("chrome")) {
				return "chrome";
			} else if (browser.hasOwnProperty("firefox")) {
				return "firefox";
			} else if (browser.hasOwnProperty("internet_explorer")) {
				return "internet_explorer";
			} else if (browser.hasOwnProperty("edge")) {
				return "edge";
			} else {
				return "";
			}
		},

		//Function to download excel
		downloadAttachment: function (fileType, base64, finalDate, sFileName) {
			//Check if browser is IE
			var browser = this.checkBrowser();
			if (browser === "internet_explorer" || browser === "edge") {
				var contentType = "data:application/vnd.ms-excel;base64";
				var blob = this.convertBase64ToBlob(base64, contentType);
				window.navigator.msSaveOrOpenBlob(blob, +sFileName + "_" + finalDate + "." + fileType);
			} else {
				var type = "data:application/vnd.ms-excel;base64";
				var uri = type + "," + escape(base64);
				var link = document.createElement('a');
				$(document).context.body.appendChild(link);
				link.href = uri;
				link.download = sFileName + "_" + finalDate + "." + fileType;
				link.click();
			}
		},

		//Function to convert base64 content to Blob object.
		convertBase64ToBlob: function (b64Data, contentType, sliceSize) {
			var byteArrays = [];
			sliceSize = sliceSize || 512;
			contentType = contentType || '';
			var byteCharacters = atob(b64Data);
			for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
				var slice = byteCharacters.slice(offset, offset + sliceSize);
				var byteNumbers = new Array(slice.length);
				for (var i = 0; i < slice.length; i++) {
					byteNumbers[i] = slice.charCodeAt(i);
				}
				var byteArray = new Uint8Array(byteNumbers);
				byteArrays.push(byteArray);
			}
			var oBlob = new Blob(byteArrays, {
				type: contentType
			});
			return oBlob;
		},

		//function to show work in progress
		fnWorkinProgress: function () {
			var oThisController = this;
			oThisController.showMessage(oThisController.getMessage("DEVELOPMENT_IN_PROGRESS"), "I", function () {});
		},

		//function to check the input is only number (number validation) 
		fnNumberOnlyLiveChange: function (oEvent) {
			var oInput = oEvent.getSource();
			var value = oInput.getValue();
			value = value.replace(/[^0-9]/g, "");
			oInput.setValue(value);
		},

		//function to check the input is decimal (decimal validation) 
		fnDecimalNumberLiveChange: function (oEvent) {
			var iResult;
			var oInput = oEvent.getSource();
			var value = oInput.getValue();
			var regEx = /^[+-]?(([1-9][0-9]*)?[0-9](\.\d{0,3})?|\.[0-9]+)$/;
			if (value.match(regEx)) {
				oInput.setValue(value);
			} else {
				// value = value.replace(/[^0-9]/g, "");
				var ilength = value.length - 1;
				iResult = value.substring(0, ilength);
				oInput.setValue(iResult);
			}

		},

		//function for validation of change event 
		onChangeCBox: function (oEvent) {
			var oValidatedComboBox = oEvent.getSource(),
				sSelectedKey = oValidatedComboBox.getSelectedKey(),
				sValue = oValidatedComboBox.getValue();
			if (sSelectedKey === "" && sValue === "") {
				oValidatedComboBox.setValueState("Error");
			} else {
				oValidatedComboBox.setValueState("None");
			}
		},

		//function for calling get service centralized
		fnManageSrvCall: function () {
			var oThisController = this;
			oThisController.fnSetCurrentUser(function (oXHR, status) {
				oThisController.fnGetAllYardLoc();
				oThisController.fnGetYardData();
			});
		},

		//***********************
		//***********************
		//***********************
		//Generic functions end here
		//***********************
		//***********************
		//***********************

		//function to set PendingWith Based on ROle
		fnSetPendingWith: function () {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var aCockpitRoles = $.extend(true, [], oMdlCommon.getProperty("/aCockpitRoles"));
			var sRole = "";
			for (var i = 0; i < aCockpitRoles.length; i++) {
				debugger;
				var sTemp = aCockpitRoles[i];
				if (sTemp.includes(oThisController.constantsDef.matHandler)) {
					sRole = oThisController.constantsDef.matHandler;
				} else if (sTemp.includes(oThisController.constantsDef.yardAdmin)) {
					sRole = oThisController.constantsDef.yardAdmin;
				} else if (sTemp.includes(oThisController.constantsDef.security)) {
					sRole = oThisController.constantsDef.security;
				} else if (sTemp.includes(oThisController.constantsDef.tManager)) {
					sRole = oThisController.constantsDef.tManager;
				} else if (sTemp.includes(oThisController.constantsDef.internal)) {
					sRole = oThisController.constantsDef.internal
				}
			}
			oMdlCommon.setProperty("/sActiveRole", sRole);
			oMdlCommon.refresh();
			return sRole;
		},

		//function to handle Actions Based On ROLE
		fnSetActionsForRoles: function (sRole) {
			debugger;
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			switch (sRole) {
			case oThisController.constantsDef.matHandler:
				oMdlCommon.setProperty("/bVisibleEmail", true);
				oMdlCommon.setProperty("/bVisibleAging", false);
				oMdlCommon.setProperty("/bEditPriority", false);
				oMdlCommon.setProperty("/bVisibleCreateAdhoc", true);
				oMdlCommon.setProperty("/bVisibleYardIn", false);
				oMdlCommon.setProperty("/bEditable", true);
				break;
			case oThisController.constantsDef.yardAdmin:
				oMdlCommon.setProperty("/bVisibleEmail", true);
				oMdlCommon.setProperty("/bVisibleAging", false);
				oMdlCommon.setProperty("/bEditPriority", true);
				oMdlCommon.setProperty("/bVisibleCreateAdhoc", true);
				oMdlCommon.setProperty("/bVisibleYardIn", false);
				oMdlCommon.setProperty("/bEditable", true);
				break;
			case oThisController.constantsDef.tManager:
				oMdlCommon.setProperty("/bVisibleEmail", false);
				oMdlCommon.setProperty("/bVisibleAging", true);
				oMdlCommon.setProperty("/bEditPriority", true);
				oMdlCommon.setProperty("/bVisibleCreateAdhoc", false);
				oMdlCommon.setProperty("/bVisibleYardIn", true);
				break;
			case oThisController.constantsDef.security:
				oMdlCommon.setProperty("/bVisibleEmail", true);
				oMdlCommon.setProperty("/bVisibleAging", false);
				oMdlCommon.setProperty("/bEditPriority", false);
				oMdlCommon.setProperty("/bVisibleCreateAdhoc", true);
				oMdlCommon.setProperty("/bVisibleYardIn", false);
				oMdlCommon.setProperty("/bEditable", true);
				break;
			default:
				oMdlCommon.setProperty("/bVisibleEmail", false);
				oMdlCommon.setProperty("/bVisibleAging", false);
				oMdlCommon.setProperty("/bEditPriority", false);
				oMdlCommon.setProperty("/bVisibleCreateAdhoc", false);
				oMdlCommon.setProperty("/bVisibleYardIn", false);
			}
			oMdlCommon.refresh();
		},

		//Function to get GetPremiumData
		fnGetYardData: function (oDataPayload) {
			var oThisController = this;
			oThisController.openBusyDialog();
			var oMdlCommon = this.getParentModel("mCommon");
			// var sUserId = oMdlCommon.getProperty("/userDetails/name");
			var sUserId = oThisController._oCommon.userDetails.name;
			var aCockpitRoles = $.extend(true, [], oMdlCommon.getProperty("/aCockpitRoles"));
			var sRole = oMdlCommon.getProperty("/sActiveRole");

			oMdlCommon.setProperty("/bUpdateYard", false);

			this.dateFormat = sap.ui.core.format.DateFormat.getDateInstance({
				pattern: "yyyy-MM-dd"
			});

			if (!oThisController._oCommon.iPageSizeRecords) {
				oThisController._oCommon.iPageSizeRecords = 10;
			}
			if (!oThisController._oCommon.iOverviewRecordsLoaded) {
				oThisController._oCommon.iOverviewRecordsLoaded = 0;
				oMdlCommon.setProperty("/aPremiumForders", []);
			}

			if (!sRole) {
				oThisController.showMessage(oThisController.getMessage("NO_ROLE_INFO"), "E");
				oThisController.closeBusyDialog();
				return;
			}

			oDataPayload = {
				"pendingWith": aCockpitRoles
			};

			oThisController.fnCallYardData(oDataPayload);
			oThisController.fnCallKpiData(oDataPayload);
			oMdlCommon.refresh();
		},

		//Funciton call to get YardData
		fnCallYardData: function (oDataPayload) {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var sShipmentsViewType = oMdlCommon.getProperty("/shipmentsViewType");
			var sUrl = "/lch_services/yard/getYardManagementDetail";
			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json"
			};
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {

				if (status === "success" && oXHR.status === 200) {
					if (oXHR.responseJSON) {
						var aShipments = oThisController.fnSetFormatYardData(oXHR.responseJSON);
						oMdlCommon.setProperty("/aShipments", aShipments);
						oMdlCommon.setProperty("/aBckShipments", aShipments);
						oMdlCommon.setProperty("/iYardLoaded", aShipments.length);
						oMdlCommon.setProperty("/iTotalLength", aShipments.length);
						if (sShipmentsViewType === "PRIORITY") {
							oMdlCommon.setProperty("/shipmentsViewType", "PRIORITY");
							oThisController.onCngViewType();
						}
					} else {
						oMdlCommon.setProperty("/aShipments", []);
					}
				} else {
					oThisController.showMessage(oThisController.getMessage("REQUEST_OPERATION_FAILED"), "E");
				}
				oMdlCommon.refresh();
				oThisController.closeBusyDialog();
			}, oDataPayload);
		},

		//function format yard data to UI from java
		fnSetFormatYardData: function (aResponse) {

			var oThisController = this;
			oThisController.openBusyDialog();
			var oMdlCommon = this.getParentModel("mCommon");
			var aCockpitRoles = $.extend(true, [], oMdlCommon.getProperty("/aCockpitRoles"));
			var sRole = oMdlCommon.getProperty("/sActiveRole");
			var aYardStatus = $.extend(true, [], oMdlCommon.getProperty("/aJavaYardStatus"));
			var aShipments = [];

			var aTempYrdData = [];
			for (var i = 0; i < aResponse.length; i++) {
				var oTemp = aResponse[i];
				var oShipments = {
					"id": (oTemp.id) ? oTemp.id : "",
					"arrival": oThisController.dateHelper.getDateObj(oTemp.arrival, "YYYYMMDDHHmmss"),
					"agingCount": (oTemp.agingCount) ? oTemp.agingCount : "0",
					"bol": (oTemp.bol) ? oTemp.bol : "",
					"carrierDesc": (oTemp.carrierDesc) ? oTemp.carrierDesc : "",
					"carrierName": (oTemp.carrierName) ? oTemp.carrierName : "",
					"comments": (oTemp.comments) ? oTemp.comments : "",
					"createdDate": oThisController.dateHelper.getDateObj(oTemp.createdDate),
					"destDesc": (oTemp.destDesc) ? oTemp.destDesc : "",
					"destId": (oTemp.destId) ? oTemp.destId : "",
					"freightOrderNo": oThisController.formatter.removeDecimal(oTemp.freightOrderNo),
					"fuCount": (oTemp.fuCount) ? oTemp.fuCount : "0",
					"lineSeq": (oTemp.lineSeq) ? oTemp.lineSeq : "",
					"location": (oTemp.location) ? oTemp.location : "",
					"latitude": (oTemp.latitude) ? oTemp.latitude : "",
					"longitude": (oTemp.longitude) ? oTemp.longitude : "",
					"pendingWith": (oTemp.pendingWith) ? oTemp.pendingWith : "",
					"pickNo": (oTemp.pickNo) ? oTemp.pickNo : "",
					"plannedShipDate": oThisController.dateHelper.getDateObj(oTemp.plannedShipDate, "YYYYMMDDHHmmss"),
					"proNo": (oTemp.proNo) ? oTemp.proNo : "",
					"qty": (oTemp.qty) ? oTemp.qty : "",
					"referenceNo": (oTemp.referenceNo) ? oTemp.referenceNo : "",
					// "supplier": (oTemp.supplier) ? `${oTemp.supplierAddress}(${oTemp.supplier})` : "",
					"supplier": (oTemp.supplier) ? oTemp.supplier : "",
					"supplierBckUp": (oTemp.supplier) ? oTemp.supplier : "",
					"supplierAddress": (oTemp.supplierAddress) ? oTemp.supplierAddress : "",
					"trailer": (oTemp.trailer) ? oTemp.trailer : "",
					"toDate": (oTemp.toDate) ? oTemp.toDate : "",
					"status": (oTemp.status) ? oTemp.status : "",
					"updatedBy": (oTemp.updatedBy) ? oTemp.updatedBy : "",
					"updatedDate": oThisController.dateHelper.getDateObj(oTemp.updatedDate),
					"yard": (oTemp.yardId) ? oTemp.yardId : "",
					// "yardId": (oTemp.yardId) ? oTemp.yardId : "",
					"yardLocation": (oTemp.yardLocation) ? oTemp.yardLocation : "",
					// "aRowLoc": (oTemp.yardId) ? oThisController.fnGetYardLocation(oTemp.yardId) : "",
					"carrier": (oTemp.carrier) ? oTemp.carrier : "",
					"createdBy": (oTemp.createdBy) ? oTemp.createdBy : "",
					"priority": (oTemp.priority) ? oTemp.priority : "",
					"handlingUnit": oThisController.formatter.removeDecimal(oTemp.handlingUnit),
					"licencePlateNo": (oTemp.licencePlateNo) ? oTemp.licencePlateNo : "",
					"sealNo": (oTemp.sealNo) ? oTemp.sealNo : "",
					"planningShipdate": (oTemp.planningShipdate) ? oTemp.planningShipdate : "",
					"yardIn": (oTemp.yardIn && oTemp.yardIn !== "0") ? new Date(oTemp.yardIn) : null,
					"yardOut": (oTemp.yardOut && oTemp.yardOut !== "0") ? new Date(oTemp.yardOut) : null,
					"isPpKit": (oTemp.isPpKit) ? oTemp.isPpKit : false,
					"sValStateYardStatus": "None",
					"bPostToPI": true,
					"bAdhocEntry": false
				};

				if (oShipments.supplierAddress) {
					if (oShipments.supplier) {
						oShipments.supplier = oShipments.supplierAddress + "(" + oShipments.supplier + ")";
					} else {
						oShipments.supplier = oShipments.supplierAddress;
					}
				}

				let oTempStatus = oThisController.fnCheckStatus(oShipments.status, aYardStatus);
				let dPlannedShipDate = oShipments.plannedShipDate;
				if (dPlannedShipDate && dPlannedShipDate !== "0") {
					oShipments.plannedShipDate = new Date(dPlannedShipDate);
				} else {
					oShipments.plannedShipDate = null;
				}
				oShipments.status = oTempStatus.statusId;
				oShipments.statusGroup = oTempStatus.groupOrKpi;

				switch (sRole) {
				case oThisController.constantsDef.matHandler:
				case oThisController.constantsDef.tManager:
				case oThisController.constantsDef.yardAdmin:
				case oThisController.constantsDef.security:
					oShipments.bCarier = false;
					oShipments.bTrailerNo = true;
					oShipments.bSealNo = true;
					oShipments.bLicense = true;
					oShipments.bSupplier = false;
					oShipments.bDestId = false;
					oShipments.bPlanDueDate = false;
					oShipments.bYardId = false;
					oShipments.bYardLoc = false;
					oShipments.bArrival = false;
					oShipments.bHU = false;
					oShipments.bProNo = false;
					break;

					// oShipments.bCarier = true;
					// oShipments.bTrailerNo = true;
					// oShipments.bSealNo = true;
					// oShipments.bLicense = true;
					// oShipments.bSupplier = true;
					// oShipments.bDestId = true;
					// oShipments.bPlanDueDate = true;
					// oShipments.bYardId = true;
					// oShipments.bYardLoc = true;
					// oShipments.bArrival = true;
					// oShipments.bHU = true;
					// oShipments.bProNo = true;
					// break;
				default:
					oShipments.bCarier = false;
					oShipments.bTrailerNo = false;
					oShipments.bSealNo = false;
					oShipments.bLicense = false;
					oShipments.bSupplier = false;
					oShipments.bDestId = false;
					oShipments.bPlanDueDate = false;
					oShipments.bYardId = false;
					oShipments.bYardLoc = false;
					oShipments.bArrival = false;
					oShipments.bHU = false;
					oShipments.bProNo = false;
				}
				aTempYrdData.push(oShipments);
			}
			aShipments = aShipments.concat(aTempYrdData);
			oThisController.closeBusyDialog();
			return aShipments;
		},

		//function to check status and set status
		fnCheckStatus: function (sStatus, aStatus) {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			sStatus = (sStatus === "LEFTGATE") ? "OUTGATE" : sStatus;
			var oTempStatus = {
				"groupOrKpi": "Not Arrived",
				"statusDesc": "Not Arrived",
				"statusId": "NOTARRIVED"
			};
			var iIndex = aStatus.findIndex(function (oRow) {
				return oRow.statusId === sStatus;
			});

			return (iIndex !== -1) ? aStatus[iIndex] : oTempStatus;
		},

		//Function to handle Segmented Button
		onCngViewType: function (oEvent) {
			var sKey = "";
			var oMdlCommon = this.getParentModel("mCommon");
			if (oEvent) {
				let oSegBtn = oEvent.getSource();
				sKey = oSegBtn.getSelectedKey();
			} else {
				sKey = oMdlCommon.getProperty("/shipmentsViewType");
			}

			var aShipments = $.extend(true, [], oMdlCommon.getProperty("/aShipments"));
			var aBckShipments = $.extend(true, [], oMdlCommon.getProperty("/aBckShipments"));

			switch (sKey) {
			case "PRIORITY":
				if (aShipments.length) {

					//Added to remove non priority shipments
					let aPriorityShipments = $.grep(aShipments, function (gRow) {
						return Number(gRow.priority);
					});

					var aSortedShipments = aPriorityShipments.sort(function (cur, next) {
						if (cur.priority === next.priority) {
							return 0;
						}
						// nulls sort after anything else
						else if (cur.priority === "") {
							return 1;
						} else if (next.priority === "") {
							return -1;
						}
						// otherwise, if we're ascending, lowest sorts first
						else {
							return cur.priority < next.priority ? -1 : 1;
						}
						// return Number(cur.priority) - Number(next.priority);
					});

					oMdlCommon.setProperty("/aShipments", aSortedShipments);
					oMdlCommon.setProperty("/iTotalLength", aSortedShipments.length);
				}
				break;
			case "ALL":
				oMdlCommon.setProperty("/aShipments", aBckShipments);
				oMdlCommon.setProperty("/iTotalLength", aBckShipments.length);
				break;
			};
			this.fnUnselectAllYard();
			oMdlCommon.refresh();
		},

		//function to get yard location based on yardID
		// fnGetYardLocation: function (yardId) {
		// 	var aYardLoc = "";
		// 	var oMdlCommon = this.getParentModel("mCommon");
		// 	var aYardLocation = $.extend(true, [], oMdlCommon.getProperty("/aYardLocation"));
		// 	var oYardDetails = aYardLocation.find(function (fRow) {
		// 		return fRow.yardId === yardId;
		// 	});
		// 	if (oYardDetails) {
		// 		aYardLoc = oYardDetails.aLoc;
		// 	}
		// 	return aYardLoc;
		// },

		onCommentsLiveChange: function (oEvent) {
			var oMdlCommon = this.getParentModel("mCommon");
			var oTextArea = oEvent.getSource(),
				iValueLength = oTextArea.getValue().length,
				iMaxLength = oTextArea.getMaxLength(),
				sState = iValueLength > iMaxLength ? "Warning" : "None";
			oTextArea.setValueState(sState);
			oMdlCommon.setProperty("/sCommentValueState", sState);
		},

		//function to call service  for kpi count
		fnCallKpiData: function (oDataPayload) {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var sUrl = "/lch_services/yard/getYardManagementKpi";
			var sUserId = oThisController._oCommon.userDetails.name;
			var aCockpitRoles = $.extend(true, [], oMdlCommon.getProperty("/aCockpitRoles"));
			var sRole = oMdlCommon.getProperty("/sActiveRole");
			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json"
			};

			if (!sRole) {
				oThisController.showMessage(oThisController.getMessage("NO_ROLE_INFO"), "E");
				oThisController.closeBusyDialog();
				return;
			}

			oDataPayload = {
				"pendingWith": aCockpitRoles
			};

			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {

				if (status === "success" && oXHR.status === 200) {
					if (oXHR.responseJSON) {
						oThisController.fnSetKpiData(oXHR.responseJSON);
					} else {
						var oTempdetails = oMdlCommon.getProperty("/oKpiDetailsbackup");
						oMdlCommon.setProperty("/oKpiDetails", oTempdetails);
					}
				} else {
					oThisController.showMessage(oThisController.getMessage("REQUEST_OPERATION_FAILED"), "E");
				}
				oMdlCommon.refresh();
				oThisController.closeBusyDialog();
			}, oDataPayload);

		},

		//fn to set the data to kpi 
		fnSetKpiData: function (aResponse) {
			var oThisController = this;
			oThisController.openBusyDialog();
			var oMdlCommon = this.getParentModel("mCommon");
			var oTempKpiDetails = oMdlCommon.getProperty("/oKpiDetailsbackup");
			for (var i = 0; i < aResponse.length; i++) {
				var oTemp = aResponse[i];
				var sStatus = oTemp.groupOrKpi;
				sStatus = sStatus.toUpperCase().replace(" ", "");
				switch (sStatus) {
				case "NOTARRIVED":
					oTempKpiDetails.iNotArrivved = oTemp.count;
					break;
				case "ATGATE":
					oTempKpiDetails.iAtGate = oTemp.count;
					break;
				case "PARKEDATLOCATION":
					oTempKpiDetails.iParkedAtLoc = oTemp.count;
					break;
				case "PICKEDBYMH":
					oTempKpiDetails.iPickedByMH = oTemp.count;
					break;
				case "OUTGATE":
					oTempKpiDetails.iOutGate = oTemp.count;
					break;
				case "INYARD":
					oTempKpiDetails.iInYard = oTemp.count;
					break;
				case "ATDOCK":
					oTempKpiDetails.iAtDoc = oTemp.count;
					break;
				}
			}
			oMdlCommon.setProperty("/oKpiDetails", oTempKpiDetails);
			oMdlCommon.setProperty("/aJavaKpiDetails", aResponse);
			oMdlCommon.refresh();

		},

		//function to handle POST PRIORITY/COMMENTS
		fnUpdatePostJava: function (aDataPayload) {
			var oThisController = this;
			oThisController.openBusyDialog();
			var oMdlCommon = this.getParentModel("mCommon");
			var oKpiActive = {
				"bNotArrivved": false,
				"bAtGate": false,
				"bInYard": false,
				"bPickedByMH": false,
				"bAtDoc": false,
				"bOutGate": false
			};

			var sUrl = "/lch_services/yard/addYardManagement";
			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json"
			};

			var aPayload = {
				"yardManagementDto": aDataPayload
			}
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				if (status === "success") {
					if (oXHR.status === 200 && oXHR.statusText === "success" && oXHR.responseJSON && oXHR.responseJSON.code === "00") {
						oThisController.fnSendNotification(aDataPayload);
						oThisController.fnSetYardDefault("ALL");
						oThisController.fnGetYardData();
						// oThisController.fnCallKpiData();
					} else if (oXHR && oXHR.responseJSON && oXHR.responseJSON.code === "01") {
						oThisController.showMessage(oXHR.responseJSON.message, "E");
					}
					oThisController.closeBusyDialog();
				} else {
					oThisController.showMessage(oThisController.getMessage("REQUEST_OPERATION_FAILED"), "E");
				}
				oMdlCommon.refresh();
				oThisController.closeBusyDialog();
			}, aPayload);

		},

		//function to get yard-id and yard-location
		// fnGetAllYardIdLoc: function () {
		// 	var oThisController = this;
		// 	var oMdlCommon = this.getParentModel("mCommon");

		// 	var sUrl = "/lch_services/yardrules/getYardLocationsAndYardId";
		// 	var oHeader = {
		// 		"Content-Type": "application/json",
		// 		"Accept": "application/json"
		// 	};

		// 	oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
		// 		if (status === "success") {
		// 			if (oXHR.status === 200 && oXHR.statusText === "success" && oXHR.responseJSON) {
		// 				oMdlCommon.setProperty("/aYardLocation", oXHR.responseJSON);
		// 				oMdlCommon.setProperty("/aYard", $.extend(true, [], oXHR.responseJSON));
		// 			}
		// 		}
		// 		oMdlCommon.refresh();
		// 	});

		// },

		fnGetAllYardLoc: function () {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var aCockpitRoles = $.extend(true, [], oMdlCommon.getProperty("/aCockpitRoles"));
			
			var sUrl = "/lch_services/yardrules/getYardLocationByPendingWith";
			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json"
			};

			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				debugger;
				if (status === "success") {
					debugger;
					if (oXHR.status === 200 && oXHR.statusText === "OK" && oXHR.responseJSON) {
						oMdlCommon.setProperty("/aYardLocation", oXHR.responseJSON);
					}
				}
				oMdlCommon.refresh();
			}, aCockpitRoles);

		},

		//function to get Yard Status
		fnGetAllYardStatus: function () {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");

			var sUrl = "/lch_services/yardStatus/getAllYardStatus";
			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json"
			};

			oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
				debugger;
				if (status === "success") {
					if (oXHR.status === 200 && oXHR.statusText === "OK" && oXHR.responseJSON) {
						oMdlCommon.setProperty("/aJavaYardStatus", oXHR.responseJSON);
					}
				}
				oMdlCommon.refresh();
			});

		},

		//Function for Columns Header CheckBox in Table
		onChangeSelectAll: function (oEvent) {
			var oMdlCommon = this.getParentModel("mCommon");
			var bSelected = oEvent.getParameter("selected");
			var oTable = oEvent.getSource().getParent().getParent();
			var oBinding = oTable.getBinding("rows");
			if (oBinding.oList.length === oBinding.aIndices.length) {
				var aShipments = $.extend(true, [], oMdlCommon.getProperty("/aShipments"));
				$.each(aShipments, function (index, oRow) {
					oRow.isSelected = bSelected;
				});
				oMdlCommon.setProperty("/aShipments", aShipments);
				oMdlCommon.refresh();
			} else {
				for (var i = 0; i < oBinding.aIndices.length; i++) {
					oBinding.oList[oBinding.aIndices[i]].isSelected = bSelected;
				}
				oBinding.update();
			}
			this.fnUpdateSelectAllYardData(oTable);
		},

		//Function for Columns CheckBox in PartsPackaging Table
		onChangeIsSelected: function (oEvent) {
			var oTable = oEvent.getSource().getParent().getParent();
			this.fnUpdateSelectAllYardData(oTable);
		},

		//function to update the selected item from parts packaging table
		fnUpdateSelectAllYardData: function (oTable) {
			var oMdlCommon = this.getParentModel("mCommon");
			var aShipments = [];
			if (oTable) {
				var oBinding = oTable.getBinding("rows");
				if (oBinding.aIndices.length) {
					for (var i = 0; i < oBinding.aIndices.length; i++) {
						aShipments.push(oBinding.oList[oBinding.aIndices[i]]);
					}
				} else {
					aShipments = oBinding.oList;
				}
			} else {
				aShipments = $.extend(true, [], oMdlCommon.getProperty("/aShipments"));
			}

			if (aShipments.length && $.grep(aShipments, function (gRow) {
					return gRow.isSelected;
				}).length === aShipments.length) {
				oMdlCommon.setProperty("/isSelectedAllFO", true);
			} else {
				oMdlCommon.setProperty("/isSelectedAllFO", false);
			}
			oMdlCommon.refresh();
			this.fnGetSelectedYardData();
			this.fnUpdateFuActions();
		},

		//function to get the selected item in parts packaging
		fnGetSelectedYardData: function () {
			var oMdlCommon = this.getParentModel("mCommon");
			var aShipments = $.extend(true, [], oMdlCommon.getProperty("/aShipments"));
			var aSelectedFU = [];

			$.each(aShipments, function (index, oRow) {
				if (oRow.isSelected) {
					oRow.sPath = ("/aShipments/" + index);
					aSelectedFU.push(oRow);
				}
			});

			oMdlCommon.setProperty("/aSelectedFOData", aSelectedFU);
			oMdlCommon.refresh();
		},

		//function to enable the buttons after selection in table
		fnUpdateFuActions: function () {
			var oMdlCommon = this.getParentModel("mCommon");
			var aSelectedFOData = $.extend(true, [], oMdlCommon.getProperty("/aSelectedFOData"));

			var iSelectedData = aSelectedFOData.length;
			if (iSelectedData <= 0) {
				oMdlCommon.setProperty("/bUpdateYard", false);
			} else {
				oMdlCommon.setProperty("/bUpdateYard", true);
			}
			oMdlCommon.refresh();
		},

		//Function to have Updated data in aSelectedData
		fnGetUpdatedData: function (aSelectedData) {
			var aUpdatedData = [];
			var oMdlCommon = this.getParentModel("mCommon");
			if (aSelectedData.length) {
				for (var i = 0; i < aSelectedData.length; i++) {
					var oParts = $.extend(true, {}, oMdlCommon.getProperty(aSelectedData[i].sPath));
					oParts.sPath = aSelectedData[i].sPath;
					aUpdatedData.push(oParts);
				}
			}
			return aUpdatedData;
		},

		//Function for Columns Header CheckBox unselection in Table
		fnUnselectAllYard: function () {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			var aShipments = $.extend(true, [], oMdlCommon.getProperty("/aShipments"));
			$.each(aShipments, function (index, oRow) {
				oRow.isSelected = false;
			});
			oMdlCommon.setProperty("/aShipments", aShipments);
			oMdlCommon.setProperty("/isSelectedAllFO", false);
			oMdlCommon.setProperty("/aSelectedFOData", []);
			oMdlCommon.refresh();
			this.fnUpdateSelectAllYardData();
		},

		//navigation in tnt layout
		onItemSelect: function (oEvent) {
			var oThisController = this;
			this._oRouter = this.getRouter();
			var oMdlCommon = this.getParentModel("mCommon");
			var key = oEvent.getParameter("item").getKey();

			switch (key) {
			case "yardOverview":
				this._oRouter.navTo("TrailerList");
				break;
			case "yardHistory":
				this._oRouter.navTo("YardHistory");
				break;
			case "geoMap":
				this._oRouter.navTo("GeoMap");
				break;
			}
		},

		fnPIPostYardDetails: function (aDataPayload, callback) {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			oThisController.openBusyDialog();
			var sUrl = "/paccar_pi/http/yard/management/process_event_notification/from_portal";
			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json"
			};
			var oKpiActive = {
				"bNotArrivved": false,
				"bAtGate": false,
				"bInYard": false,
				"bPickedByMH": false,
				"bAtDoc": false,
				"bOutGate": false
			};
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				if (status === "success") {
					oThisController.closeBusyDialog();
					if (callback) {
						callback();
					}
				} else {
					oThisController.showMessage(oThisController.getMessage("REQUEST_OPERATION_FAILED"), "E");
					oThisController.closeBusyDialog();
				}
				oMdlCommon.refresh();
			}, aDataPayload);
		},

		//function to set Default values:
		fnSetYardDefault: function (sDefaultType) {
			var oThisController = this;
			var oMdlCommon = oThisController.getParentModel("mCommon");
			var oFilters = {
				"filterByKey": "",
				"status": "",
				"yardLocation": "",
				"filterByText": "",
				"supplierId": "",
				"fromDateFilter": null,
				"toDateFilter": null,
				"yardId": ""
			};
			var oValState = {
				"sFromDateValState": "None",
				"sToDateValState": "None",
				"sValStateFilterByKey": "None",
				"sValStateYardLocation": "None",
				"sValStateStatus": "None"
			};
			var oKpiActive = {
				"bNotArrivved": false,
				"bAtGate": false,
				"bInYard": false,
				"bPickedByMH": false,
				"bAtDoc": false,
				"bOutGate": false
			};

			var aKpiSelected = [{
				"title": "notArrived",
				"iKpiClick": 0,
				"value": "Not Arrived"
			}, {
				"title": "AtGate",
				"iKpiClick": 0,
				"value": "At Gate"
			}, {
				"title": "InYard",
				"iKpiClick": 0,
				"value": "In Yard"
			}, {
				"title": "PickedByMH",
				"iKpiClick": 0,
				"value": "Picked By MH"
			}, {
				"title": "AtDock",
				"iKpiClick": 0,
				"value": "At Dock"
			}, {
				"title": "OutGate",
				"iKpiClick": 0,
				"value": "Out Gate"
			}];

			switch (sDefaultType) {
			case 'KPI':
				oMdlCommon.setProperty("/oKpiActive", oKpiActive);
				oMdlCommon.setProperty("/aKpiSelected", aKpiSelected);
				break;
			case 'FILTERS':
				oMdlCommon.setProperty("/oFilters", oFilters);
				oMdlCommon.setProperty("/oValState", oValState);
				break;
			case 'ALL':
				oMdlCommon.setProperty("/oFilters", oFilters);
				oMdlCommon.setProperty("/oValState", oValState);
				oMdlCommon.setProperty("/oKpiActive", oKpiActive);
				oMdlCommon.setProperty("/aKpiSelected", aKpiSelected);
				oMdlCommon.setProperty("/iYardLoaded", 0);
				oMdlCommon.setProperty("/iTotalLength", 0);
				oMdlCommon.setProperty("/aShipments", [])
				oMdlCommon.setProperty("/aSelectedFOData", []);
				oMdlCommon.setProperty("/isSelectedAllFO", false);
				break;
			}
			oMdlCommon.refresh();
		},

		//Function to handle clear Filter
		onClearYrdFilter: function () {
			this.fnSetYardDefault("ALL");
			this.fnManageSrvCall();
		},

		//function to handle reload
		onReloadYard: function () {
			var oThisController = this;
			var oMdlCommon = this.getParentModel("mCommon");
			this.fnSetYardDefault("ALL");
			oThisController.fnManageSrvCall();
		},

		//Get call for downloading document
		onBase64FileDownload: function (fileName, sURL, oPayload) {
			var oThisController = this;
			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json"
			};

			try {
				oThisController.openBusyDialog();

			} catch (e) {
				oThisController.showMessage(oThisController.getMessage("EXCEPTION"), "E");
				oThisController.closeBusyDialog();
			}

			oThisController.fnProcessDataRequest(sURL, "POST", oHeader, false, function (oXHR, status) {
				if (status === "success") {
					var base64 = oXHR.responseJSON.message;
					if (base64) {
						var finalDate = oThisController.dateHelper.getBackEndDate(new Date());
						oThisController.downloadAttachment("xls", base64, finalDate.replace(/:/g, " "), fileName);
						oThisController.closeBusyDialog();
					} else {
						oThisController.showMessage(oThisController.getMessage("REQUEST_OPERATION_FAILED"), "E");
						oThisController.closeBusyDialog();
					}
				} else {
					oThisController.showMessage(oThisController.getMessage("REQUEST_OPERATION_FAILED"), "W", function () {});
					oThisController.closeBusyDialog();
				}
			}, oPayload);
		},

		fnSendNotification: function (aSelectedYard) {
			let oThisController = this;
			let oMdlCommon = oThisController.getParentModel("mCommon");
			let oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json"
			};
			let sURL = "/lch_services/notification/sendNotification";
			let sRole = oMdlCommon.getProperty("/sActiveRole");
			let aPushNotificationPayload = [];

			for (let i = 0; i < aSelectedYard.length; i++) {
				let oTemp = aSelectedYard[i];
				let oNotification = {
					"foNumber": "",
					"destId": "",
					"Message": "",
					"receipentRole": []
				};

				switch (oTemp.status) {
				case "INGATE":
					if (sRole === oThisController.constantsDef.security) {
						oNotification.foNumber = oTemp.freightOrderNo;
						oNotification.destId = oTemp.destId;
						oNotification.Message = ("FO Num " + oTemp.freightOrderNo + " has been ingated and been alloted Yard Location " + oTemp.yardLocation +
							".");
						oNotification.receipentRole = [oThisController.constantsDef.matHandler, oThisController.constantsDef.tManager];
					}
					break;
				case "DOCK":
					if (sRole === oThisController.constantsDef.matHandler) {
						oNotification.foNumber = oTemp.freightOrderNo;
						oNotification.destId = oTemp.destId;
						oNotification.Message = ("FO Num " + oTemp.freightOrderNo + " has been docked at Dock.");
						oNotification.receipentRole = [oThisController.constantsDef.tManager];
					}
					break;
				case "OUTGATE":
					if (sRole === oThisController.constantsDef.security) {
						oNotification.foNumber = oTemp.freightOrderNo;
						oNotification.destId = oTemp.destId;
						oNotification.Message = ("FO Num " + oTemp.freightOrderNo + " has been Out Gated/Executed.");
						oNotification.receipentRole = [oThisController.constantsDef.tManager];
					}
					break;

				}
				if (oNotification.foNumber) {
					aPushNotificationPayload.push(oNotification);
				}
			}

			if (aPushNotificationPayload.length) {
				oThisController.fnProcessDataRequest(sURL, "POST", oHeader, false, function (oXHR, status) {
					if (status === "success") {
						oThisController.fnCreateLog("Notification Sent Successfully", "I", aPushNotificationPayload);
					} else {
						oThisController.fnCreateLog("Notification Failed", "E", aPushNotificationPayload);
					}
				}, aPushNotificationPayload);
			}

		},

		fnSetUserInterFace: function (sAction) {
			var oMdlCommon = this.getParentModel("mCommon");
			if (sAction === "Edit") {
				oMdlCommon.setProperty("/bEditable", true);
			} else {
				oMdlCommon.setProperty("/bEditable", false);
			}
		},

		fnGetLookUpOriginId: function () {
			let oThisController = this;
			let oMdlCommon = this.getParentModel("mCommon");
			let sUrl = "lch_services/adhocorders/getAllShipperDetails",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};

			try {
				oMdlCommon.setProperty("/aSupplier", []);
				oMdlCommon.refresh();
			} catch (e) {
				//catch
			}

			oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {
						oMdlCommon.setProperty("/aSupplier", oXHR.responseJSON);
						/*let aSupplier = [];
						for (let i = 0; i < oXHR.responseJSON.d.results.length; i++) {
							let oRow = oXHR.responseJSON.d.results[i];
							oRow.SupplierAddress = oRow.SupplierAddress.replace(oRow.SupplierName + "/", "");
							aSupplier.push(oRow);*/

					}
					oMdlCommon.refresh();
				} catch (e) {
					// console.log(e);
				}
			});

		},

		fnGetLookUpDestination: function () {
			let oThisController = this;
			let oMdlCommon = this.getParentModel("mCommon");
			let sUrl = "/lch_services/yard/getYardDestinationDetails",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};

			try {
				oMdlCommon.setProperty("/aDestination", []);
				oMdlCommon.refresh();
			} catch (e) {
				//catch
			}

			oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
				try {
					debugger;
					if (oXHR && oXHR.responseJSON) {
						debugger;
						/*	let aLoc = JSON.parse(JSON.stringify(oXHR.responseJSON, ["DestName", "DestinationId"]));*/
						oMdlCommon.setProperty("/aDestination", oXHR.responseJSON);
					}
					oMdlCommon.refresh();
				} catch (e) {
					// console.log(e);
				}
			});

		},

		fnGetLookUpCarrier: function () {
			var oThisController = this;
			var oMdlCommon = oThisController.getParentModel("mCommon");
			var sUrl = "/lch_services/premiumOrders/getAllCarrierDetails",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};
			try {
				oMdlCommon.setProperty("/aCarrier", []);
				oMdlCommon.refresh();
			} catch (e) {
				//catch
				//console.log(e);
				oThisController.closeBusyDialog();
			}
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {
						oMdlCommon.setProperty("/aCarrier", oXHR.responseJSON);
					}
					oMdlCommon.refresh();
				} catch (e) {
					oMdlCommon.setProperty("/aCarrier", []);
					oMdlCommon.refresh();
					// console.log(e);
				}
			});
			// console.log(oMdlCommon);
		},

	});

});