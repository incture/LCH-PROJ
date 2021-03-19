/* global QUnit */
QUnit.config.autostart = false;

sap.ui.getCore().attachInit(function () {
	"use strict";

	sap.ui.require([
		"com/incture/lch/Adhoc/test/unit/AllTests"
	], function () {
		QUnit.start();
	});
});