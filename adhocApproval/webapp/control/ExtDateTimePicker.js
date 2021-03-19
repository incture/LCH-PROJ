sap.ui.define([
	"sap/m/DateTimePicker"
], function (DateTimePicker) {
	"use strict";
	 var ExtDateTimePicker = DateTimePicker.extend("com.incture.lch.adhocApproval.control.ExtDateTimePicker", {
		renderer: function (oRm, oControl) {
			sap.m.DateTimePickerRenderer.render(oRm, oControl);
		}
	});
	
	ExtDateTimePicker.prototype.onkeypress = function (oEvent) {
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

	
	return ExtDateTimePicker;
});