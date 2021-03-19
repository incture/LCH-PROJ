sap.ui.define([
	"sap/m/DateTimePicker"
], function (DateTimePicker) {
	"use strict";
	var ExtDateTimePicker = DateTimePicker.extend("com.incture.lch.premfreightOrders.control.ExtDateTimePicker", {
		renderer: function (oRm, oControl) {
			try {
				sap.m.DateTimePickerRenderer.render(oRm, oControl);
			} catch (e) {
				//exception caught
			}
		}
	});

	ExtDateTimePicker.prototype.onkeypress = function (oEvent) {
		try {
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
		} catch (e) {
			//exception caught
		}
	};
	return ExtDateTimePicker;
});