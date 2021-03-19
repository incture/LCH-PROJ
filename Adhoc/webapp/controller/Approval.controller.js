sap.ui.define([
	"com/incture/lch/Adhoc/controller/BaseController"
], function (BaseController) {
	"use strict";

	return BaseController.extend("com.incture.lch.Adhoc.controller.Approval", {

		onInit: function () {
			this._oRouter = this.getRouter();
			this.fnGetApproval();

		},

		fnGetApproval: function () {
			var oThisController = this;
			var oMdlCommon = oThisController.getParentModel("mCommon");
			var sUrl = "/lch_services/adhocorders/getAllApproveRule",
				oHeader = {
					"Content-Type": "application/json",
					"Accept": "application/json"
				};
			oThisController.fnProcessDataRequest(sUrl, "GET", oHeader, false, function (oXHR, status) {
				try {
					if (oXHR && oXHR.responseJSON) {
						console.log(oXHR.responseJSON);
						oMdlCommon.setProperty("/aApproval", oXHR.responseJSON);
					}
					oMdlCommon.refresh();
					console.log(oMdlCommon);
				} catch (e) {
					// console.log(e);
				}
			});

		},

		onUpload: function (oEvent) {
			var oFileUploader = this.byId("fileUploader");
			console.log(oFileUploader);
			var domRef = oFileUploader.getFocusDomRef();
			var file = domRef.files[0];
			var oFormData = new FormData();
			var oNewFile = new File([file], file.name, {
				type: file.type
			});
			oFormData.append("file", oNewFile);
			console.log(oNewFile);
			try {
				if (oNewFile) {
					debugger;
					/*	var oFormData = new FormData();
						jQuery.sap.domById(oFileUploader.getId() + "-fu").setAttribute("type", "file");
						oFormData.append("file", jQuery.sap.domById(oFileUploader.getId() + "-fu").files[0]);
						console.log(oFormData);*/
					var sUrl = "/lch_services/adhocorders/excelUpload";
					$.ajax({
						type: 'POST',
						url: sUrl,
						headers: {

						},
						cache: false,
						contentType: false,
						processData: false,
						data: oNewFile,
						success: function (response) {
							console.log("success");

						},
						error: function (err) {
							console.log("failed");
						}
					});
				}
			} catch (oException) {
				jQuery.sap.log.error("File upload failed:\n" + oException.message);
			}

		}

		/*		onExtract: function () {
					var oThisController = this;
					var oMdlCommon = this.getParentModel("mCommon");
					var sUrl = "/lch_services/adhocorders/downloadApprovalRule",
						oHeader = {
							"Content-Type": "application/json",
							"Accept": "application/json"
						};

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

				},

				onDownloadTemplate: function () {
					var oThisController = this;
					var oMdlCommon = this.getParentModel("mCommon");
					var sUrl = "/lch_services/adhocorders/downloadSampleTemplate ",
						oHeader = {
							"Content-Type": "application/json",
							"Accept": "application/json"
						};

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

				}*/

	});

});