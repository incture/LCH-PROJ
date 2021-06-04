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

		fnInitializeApp: function () {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			var sRootPath = jQuery.sap.getModulePath("com.incture.lch.premfreightOrders");
			oMdlCommon.attachRequestCompleted(function (oEvent) {
				oMdlCommon.setProperty("/pageNo", 1);
				oThisController.fnSetCurrentUser();
				oMdlCommon.setProperty("/today", new Date());
				oMdlCommon.refresh();
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
			var oMdlCommon = this.getModel("mCommon");
			var sUrl = "/lch_services/lchRole/getPremiumRole/";
			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json",
				"user": ""
			};
			oHeader.user = oMdlCommon.getProperty("/userDetails/id");
			sUrl += oHeader.user;
			oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {

				if (oXHR && oXHR.responseJSON) {
					oMdlCommon.setProperty("/aCockpitRoles", oXHR.responseJSON);
					oMdlCommon.refresh();
					var role = oMdlCommon.getProperty("/aCockpitRoles");
					if (role[0] === "LCH_Planner") {
						oMdlCommon.setProperty("/visible/bApprove", false);
						oMdlCommon.setProperty("/visible/bReject", true);
						oMdlCommon.setProperty("/visible/bGetcost", true);
						oMdlCommon.setProperty("/visible/bSetcost", false);
						oMdlCommon.setProperty("/visible/createdDate", true);
						oMdlCommon.setProperty("/enable/costInput", false);
						oMdlCommon.setProperty("/visible/carrierText", false);
						oMdlCommon.setProperty("/visible/carrierCombo", true);
						oMdlCommon.setProperty("/visible/carrierScac", false);
						oMdlCommon.setProperty("/visible/carrierDetails", false);
						oMdlCommon.refresh();
					} else if (role[0] === "LCH_Carrier_Admin") {
						oMdlCommon.setProperty("/visible/bApprove", false);
						oMdlCommon.setProperty("/visible/bReject", false);
						oMdlCommon.setProperty("/visible/bGetcost", false);
						oMdlCommon.setProperty("/visible/createdDate", false);
						oMdlCommon.setProperty("/visible/bSetcost", true);
						oMdlCommon.setProperty("/visible/carrierText", true);
						oMdlCommon.setProperty("/visible/carrierCombo", false);
						oMdlCommon.setProperty("/enable/costInput", true);
						oMdlCommon.setProperty("/visible/carrierScac", true);
						oMdlCommon.setProperty("/visible/carrierDetails", true);
						oMdlCommon.refresh();
					} else if (role[0] === "LCH_Manager") {
						oMdlCommon.setProperty("/visible/bApprove", true);
						oMdlCommon.setProperty("/visible/bReject", false);
						oMdlCommon.setProperty("/visible/bGetcost", false);
						oMdlCommon.setProperty("/visible/bSetcost", false);
						oMdlCommon.setProperty("/enable/bApprove", true);
						oMdlCommon.setProperty("/enable/bReject", false);
						oMdlCommon.setProperty("/enable/bGetcost", false);
						oMdlCommon.setProperty("/enable/bSetcost", false);
						oMdlCommon.setProperty("/enable/costInput", false);
						oMdlCommon.setProperty("/visible/carrierText", true);
						oMdlCommon.setProperty("/visible/carrierCombo", false);
						oMdlCommon.setProperty("/visible/carrierScac", true);
						oMdlCommon.setProperty("/visible/carrierDetails", true);
						oMdlCommon.refresh();
					}
					oThisController.fnFilterTest();

				} else {
					oMdlCommon.setProperty("/cockpitRoles", []);
					oMdlCommon.refresh();

				}

			});
		},

		fnSetCurrentUser: function () {

			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				bShowBusy = oMdlCommon.getProperty("/bShowBusy");
			if (bShowBusy) {
				oThisController.openBusyDialog();
			}
			oThisController.fnProcessDataRequest("../user", "GET", null, false, function (oXHR, status) {
				if (oXHR && oXHR.responseJSON) {
					oThisController._oCommon.userDetails = oXHR.responseJSON;
					if (!oXHR.responseJSON.emails) {
						oXHR.responseJSON.emails = "testing@email.com";
					}
					oMdlCommon.setProperty("/pageNo", 1);
					oMdlCommon.setProperty("/userDetails", oXHR.responseJSON);
					oMdlCommon.refresh();
					oThisController.fnGetLaunchPadRoles();
				}
			}, null);

		},

		fnPremfreightstable: function () {

			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon"),
				sUrl = "/lch_services/premiumOrders/getAllPremiumOrders",
				bShowBusy = oMdlCommon.getProperty("/bShowBusy");
			var originFilter = oMdlCommon.getProperty("/originFilter"),
				destinationFilter = oMdlCommon.getProperty("/destinationFilter"),
				statusFilter = oMdlCommon.getProperty("/statusFilter"),
				fromDateFilter = oMdlCommon.getProperty("/fromDateFilter"),
				toDateFilter = oMdlCommon.getProperty("/toDateFilter"),
				reasonCodeFilter = oMdlCommon.getProperty("/reasonCodeFilter"),
				pageNo = oMdlCommon.getProperty("/pageNo");
			if (fromDateFilter > toDateFilter) {
				oThisController.showMessage("Invalid date range", "E", function () {
					//error
				});
				return;
			}
			var oHeader = {
				"Content-Type": "application/json",
				"Accept": "application/json"
			};
			var oPayload = {
				"orderId": "",
				"fromDate": fromDateFilter,
				"toDate": toDateFilter,
				"status": statusFilter,
				"originName": originFilter,
				"destinationName": destinationFilter,
				"reasonCode": reasonCodeFilter,
				"pageNumber": pageNo,
				"noOfEntry": "",
				"userId": oMdlCommon.getProperty("/userDetails/id")

			};
			oThisController.fnProcessDataRequest(sUrl, "POST", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR.responseJSON.premiumFreightOrderDetailsList) {
						oMdlCommon.setProperty("/countOfRecords", oXHR.responseJSON.count);
						oMdlCommon.setProperty("/aPremfreightorders", []);
						oMdlCommon.setProperty("/aTaskDetails", []);
						var aPremfreightorders = oMdlCommon.getProperty("/aPremfreightorders");
						var aTaskDetails = oMdlCommon.getProperty("/aTaskDetails");
						var count = oMdlCommon.getProperty("/countOfRecords");
						for (var i = 0; i < oXHR.responseJSON.premiumFreightOrderDetailsList.length; i++) {
							aPremfreightorders[i] = oXHR.responseJSON.premiumFreightOrderDetailsList[i].premiumFreightDto1;
							aTaskDetails[i] = oXHR.responseJSON.premiumFreightOrderDetailsList[i].taskDetailsDto;
							if (oMdlCommon.getProperty("/aCockpitRoles")[0] === "LCH_Planner") {
								aPremfreightorders[i].enablecarriermode = false;
								if (aPremfreightorders[i].bpNumber && aPremfreightorders[i].carrierMode) {
									aPremfreightorders[i].selectedCarriermode = aPremfreightorders[i].carrierMode;
									aPremfreightorders[i].selectedCarrier = aPremfreightorders[i].bpNumber;
									aPremfreightorders[i].enablecarrier = false;

								} else {
									aPremfreightorders[i].selectedCarrier = "";
									aPremfreightorders[i].selectedCarriermode = "";
								}
							}
						}
						oMdlCommon.refresh();
						oThisController.fnButtonVisibility(count);

					}

				} catch (e) {
					// console.log(e);
				}
			}, oPayload);
			if (bShowBusy) {
				oThisController.closeBusyDialog();
			}

		},

		fnFilterTest: function () {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			oMdlCommon.setProperty("/originFilter", "");
			oMdlCommon.setProperty("/destinationFilter", "");
			oMdlCommon.setProperty("/statusFilter", "");
			oMdlCommon.setProperty("/fromDateFilter", null);
			oMdlCommon.setProperty("/toDateFilter", null);
			oMdlCommon.setProperty("/reasonCodeFilter", "");
			oThisController.fnPremfreightstable();
		},

		fnButtonVisibility: function (count) {
			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			var pageNo = oMdlCommon.getProperty("/pageNo");
			pageNo = Number(pageNo);
			var lastPageNo = Number(Math.ceil(count / 10));
			oMdlCommon.setProperty("/lastPageNo", lastPageNo);
			if (pageNo <= lastPageNo) {
				if (pageNo === 1 && pageNo !== lastPageNo) {
					oMdlCommon.setProperty("/enable/firstPage", false);
					oMdlCommon.setProperty("/enable/previousPage", false);
					oMdlCommon.setProperty("/enable/nextPage", true);
					oMdlCommon.setProperty("/enable/lastPage", true);

				} else if (pageNo !== 1 && pageNo === lastPageNo) {
					oMdlCommon.setProperty("/enable/firstPage", true);
					oMdlCommon.setProperty("/enable/previousPage", true);
					oMdlCommon.setProperty("/enable/nextPage", false);
					oMdlCommon.setProperty("/enable/lastPage", false);

				} else if (pageNo === 1 && pageNo === lastPageNo) {
					oMdlCommon.setProperty("/enable/firstPage", false);
					oMdlCommon.setProperty("/enable/previousPage", false);
					oMdlCommon.setProperty("/enable/nextPage", false);
					oMdlCommon.setProperty("/enable/lastPage", false);

				} else {
					oMdlCommon.setProperty("/enable/firstPage", true);
					oMdlCommon.setProperty("/enable/previousPage", true);
					oMdlCommon.setProperty("/enable/nextPage", true);
					oMdlCommon.setProperty("/enable/lastPage", true);

				}
			}

		},

		fnPagination: function (select) {

			var oThisController = this;
			var oMdlCommon = this.getModel("mCommon");
			var count = oMdlCommon.getProperty("/countOfRecords");
			var pageNo = oMdlCommon.getProperty("/pageNo"),
				lastPageNo = Number(Math.ceil(count / 10));
			oMdlCommon.setProperty("/lastPageNo", lastPageNo);
			pageNo = Number(pageNo);
			if (pageNo <= lastPageNo) {
				switch (select) {
				case 1:
					//first
					pageNo = 1;
					break;
				case 2:
					//prev
					if (pageNo !== 1) {
						pageNo = pageNo - 1;
					}
					break;
				case 3:
					//next
					if (pageNo !== lastPageNo) {
						pageNo = pageNo + 1;
					}
					break;
				case 4:
					//last
					pageNo = lastPageNo;
					break;
				case 5:
					//jump
					pageNo = pageNo;

					break;
				}
				oMdlCommon.setProperty("/pageNo", pageNo);
				oMdlCommon.refresh();

				oThisController.fnPremfreightstable();

			} else {
				pageNo = lastPageNo;
				oMdlCommon.setProperty("/pageNo", pageNo);
				oMdlCommon.refresh();

				oThisController.fnPremfreightstable();

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