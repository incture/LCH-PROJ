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
		},

		getMinMaxDate: function (today) {
			var oDate = new Date(today);
			if (oDate) {
				var mm = oDate.getMonth(),
					dd = oDate.getDate(),
					yyyy = oDate.getFullYear();
				return new Date(yyyy, mm, dd);
			} else {
				return null;
			}
		},

		getDateObject: function (sDate) {
			var oDate = new Date(sDate);
			return oDate;
		}
	};

	return oFormatter;

});