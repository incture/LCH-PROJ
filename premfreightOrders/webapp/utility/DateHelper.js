sap.ui.define(["sap/ui/core/format/DateFormat"], function (DateFormat) {
	"use strict";
	var oDateHelper = {
		dateFormat: DateFormat.getDateInstance({
			pattern: "yyyy-MM-dd HH:MM a"
		}),
		timeZoneOffSet: new Date(0).getTimezoneOffset() * 60 * 1000,
		getDateObj: function (pDate, pFormat) {

			if (!pDate) {
				return null;
			}

			if (pDate instanceof Date) {
				return pDate;
			}

			var oDate = new Date();

			switch (pFormat) {
			case "YYYYMMDD":
				oDate.setFullYear(Number(pDate.substr(0, 4)).toString());
				oDate.setDate(Number(pDate.substr(6, 2)).toString());
				oDate.setMonth((Number(pDate.substr(4, 2)) - 1).toString());
				oDate = new Date(oDate.setHours(12, 0, 0, 0));
				break;
			case "YYYYMMDDHHmmss":
				var oRegExpDate = new RegExp('^[0-9]+$');
				if (pDate.length < 14 || !oRegExpDate.test(pDate)) {
					return null;
				} else {
					var sDate = pDate;
					sDate = sDate.splice(4, 0, "-");
					sDate = sDate.splice(7, 0, "-");
					sDate = sDate.splice(10, 0, "T");
					sDate = sDate.splice(13, 0, ":");
					sDate = sDate.splice(16, 0, ":");
					oDate = new Date(sDate);
				}
				break;
			case "GwDate":
				oDate = new Date(parseInt(pDate.substr(6)));
				break;
			case "JgwDate":
				oDate = new Date(pDate);
				break;
			}
			return oDate;
		},

		getBackEndDate: function (pDate) {
			if (pDate instanceof Date) {
				var mm = pDate.getMonth() + 1,
					dd = pDate.getDate(),
					hr = pDate.getHours(),
					min = pDate.getMinutes(),
					sec = pDate.getSeconds();
				mm = (mm < 10) ? ("0" + mm) : mm;
				dd = (dd < 10) ? ("0" + dd) : dd;
				hr = (hr < 10) ? ("0" + hr) : hr;
				min = (min < 10) ? ("0" + min) : min;
				sec = (sec < 10) ? ("0" + sec) : sec;

				return (pDate.getFullYear() + "-" +
					mm + "-" +
					dd + " " +
					hr + ":" +
					min + ":" +
					sec);
			} else {
				pDate = null;
			}
			return pDate;
		},

		getSAPGwDateTime: function (pDate) {
			var sDate = oDateHelper.getBackEndDate(pDate);
			return ((sDate) ? sDate.replace(" ", "T") : null);
		},

		getSDateFrmYYYYMMDD: function (sYYYYMMDD) {
			var sDate = "";
			if (sYYYYMMDD) {
				var oDate = oDateHelper.getDateObj(sYYYYMMDD, "YYYYMMDD");
				sDate = oDate.toDateString();
				sDate = sDate.substr(4, 14);
				sDate = sDate.splice(6, 0, ",");
			}
			return sDate;
		},

		getDisplayDateTime: function (pDate) {
			if (pDate instanceof Date) {
				return pDate;
			} else if (typeof pDate === "string") {
				var sDate = pDate.replace(" ", "T");
				return new Date(sDate);
			}
			return null;
		},

		strDate: function (value) {
			if (value instanceof Date) {
				return oDateHelper.dateFormat.format(new Date(value.getTime() + oDateHelper.timeZoneOffSet));
			} else {
				return "";
			}
		},

		//to Support IE date Conversion
		getFullDate: function (value) {
			var arr = (value) ? value.split(" ") : [];
			var dDate = (arr.length >= 2) ? arr[0].split("-") : [];
			var time = (arr.length >= 2) ? arr[1].split(":") : [];
			if (dDate.length) {
				var date = new Date(dDate[0], (dDate[1] - 1), dDate[2], time[0], time[1], time[2]);
				return date;
			}else{
				return null;
			}
		}
	};

	return oDateHelper;

});