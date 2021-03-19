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
		
		isBothNonEmpty : function (param1,param2) {
			var bOne = oFormatter.isNonEmpty(param1);
			var bTwo = oFormatter.isNonEmpty(param2);
			return (bOne && bTwo) ? true : false;
		},
		
		isAllThreeNonEmpty : function (param1,param2,param3) {
			var bOne = oFormatter.isNonEmpty(param1);
			var bTwo = oFormatter.isNonEmpty(param2);
			var bThree = oFormatter.isNonEmpty(param3);
			return (bOne && bTwo && bThree) ? true : false;
		},

		getNumber: function (param) {
			var oRegExpNumber = new RegExp("^[0-9]+$");
			return oRegExpNumber.test(param) ? (Number(param) + "") : "";
		},

		getQuantity: function (sQuantity) {
			var nQuantity = Number(sQuantity);
			if (!nQuantity) {
				return "";
			}
			return (isNaN(nQuantity) ? "" : nQuantity);

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
		}

	};
	
	return oFormatter;
	
});