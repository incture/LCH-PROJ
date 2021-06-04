sap.ui.define([], function () {
	"use strict";

	var oFormatter = {

		isNonEmpty: function (param) {
			var bNonEmpty = false;
			switch (typeof param) {
			case "number":
				bNonEmpty = (param > 0) ? true : false;
				break;
			case "string":
				bNonEmpty = (param.length) ? true : false;
				break;
			case "boolean":
				bNonEmpty = (param) ? true : false;
				break;
			case "object":
				bNonEmpty = (param) ? true : false;
				break;
			case "undefined":
			default:
				bNonEmpty = false;
			}

			return bNonEmpty;
		},

		isBothNonEmpty: function (param1, param2) {
			var bOne = oFormatter.isNonEmpty(param1);
			var bTwo = oFormatter.isNonEmpty(param2);
			return (bOne && bTwo) ? true : false;
		},

		getNumber: function (param) {
			var oRegExpNumber = new RegExp("^[0-9]+$");
			return oRegExpNumber.test(param) ? (Number(param) + "") : "";
		},
		
		removeDecimal: function(param){
			let nNum = oFormatter.getQuantity(param);
			return (param && nNum) ? (nNum + "") : "";
		},

		getQuantity: function (sQuantity) {
			var nQuantity = Number(sQuantity);
			if (!nQuantity) {
				return "";
			}
			return (isNaN(nQuantity) ? "" : nQuantity);

		},

		getWeight: function (sWeight) {
			var iWeight = Number(sWeight);
			if (!iWeight) {
				return "";
			}
			return (isNaN(iWeight) ? "" : iWeight);
		},

		//Function to get first 10 characters of carrier description
		formatText: function (carrierDesc) {
			if (carrierDesc) {
				var oDescription = carrierDesc.substr(0, 10);
				return oDescription;
			} else {
				return "";
			}
		},

		getLength: function (param) {
			if (param instanceof Array) {
				return param.length;
			} else {
				return 0;
			}
		},

		getBy3: function (value) {
			if (!value) {
				return 0;
			}
			return Math.floor(value / 3);
		},

		getBy3PlusRem: function (value) {
			if (!value) {
				return 0;
			}
			return (Math.floor(value / 3) + (value % 3));
		},

		setBoolean: function (oParams) {
			var Value;
			if (oParams === true) {
				Value = "YES";
			} else if (oParams === false) {
				Value = "NO";
			} else {
				Value = "";
			}
			return Value;
		},

		getBoolean: function (oParams) {
			var Value;
			if (oParams) {
				if (oParams.toLocaleLowerCase() === "true") {
					Value = true;
				} else if (oParams.toLocaleLowerCase() === "false") {
					Value = false;
				} else {
					Value = null;
				}
			}
			return Value;
		},

		fnSetEditByActn: function (bEdit, sDisplayOnly) {
			var bOne = oFormatter.isNonEmpty(bEdit);
			var bTwo = oFormatter.isNonEmpty(sDisplayOnly);
			return (bOne && bTwo) ? true : false;
		},

		getStatusEnabled: function (sStatus) {
			if (sStatus === "NOTARRIVED") {
				return false;
			} else {
				return true;
			}
		},

		getEditableField: function (sRole) {
			switch (sRole) {
			case "LCH_MATERIAL_HANDLER":
				return false;
			case "KWR_ADMIN":
				return true;
			default:
				return true;
			}
		}

	};

	return oFormatter;

});