sap.ui.define([
	"sap/m/DatePicker"
], function (DatePicker) {
	"use strict";
	 var ExtDatePicker = DatePicker.extend("com.incture.lch.Adhoc.control.ExtDatePicker", {
		renderer: function (oRm, oControl) {
			sap.m.DatePickerRenderer.render(oRm, oControl);
		}
	});
	
	ExtDatePicker.prototype.onkeypress = function (oEvent) {
	    // the keypress event should be fired only when a character key is pressed,
	    // unfortunately some browsers fire the keypress event for control keys as well.
	    if (!oEvent.charCode || oEvent.metaKey || oEvent.ctrlKey) {
            return;
	    }
	
	    var oFormatter = this._getFormatter(true);
	    var sChar = String.fromCharCode(oEvent.charCode);
	
	    if (oFormatter.sAllowedCharacters.indexOf(sChar) < 0) {
            oEvent.preventDefault();
	    }
	};
	
	return ExtDatePicker;
});