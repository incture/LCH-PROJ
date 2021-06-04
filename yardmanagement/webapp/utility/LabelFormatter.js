jQuery.sap.declare("com.incture.lch.yardmanagement.utility.LabelFormatter");
com.incture.lch.yardmanagement.utility.LabelFormatter = {
	//Function to display change color of button based on status of task 
	setTaskStatusColor: function (status, foNum) {
		// status = (status) ? status.split(" ").join("").toUpperCase() : "";
		// var oButton = this.getView().byId("StatusBtn");
		this.removeStyleClass("sapUIMStsRed sapUIMStsOrg sapUIMStsGreen sapUIMStsBlue sapUIMStsGrey sapUIMStsPur");
		status = (status) ? status.toUpperCase().replace(" ", "") : "";
		switch (status) {
		case "NOTARRIVED":
			this.addStyleClass("sapUIMStsRed");
			break;
		case "ATDOCK":
			this.addStyleClass("sapUIMStsGrey");
			break;
		case "INYARD":
			this.addStyleClass("sapUIMStsGreen");
			break;
		case "ATGATE":
			this.addStyleClass("sapUIMStsOrg");
			break;
		case "OUTGATE":
			this.addStyleClass("sapUIMStsPur");
			break;
		default:
			this.addStyleClass("sapUIMStsRed");
		}

		return foNum;
	},

	setAgingColor: function (iAgingCount) {
		// var oButton = this.getView().byId("StatusBtn");
		this.removeStyleClass("sapUIMStsRed sapUIMStsGreen");
		let iTempAge = Number(iAgingCount);
		switch (true) {
		case (iTempAge >= 5):
			this.addStyleClass("sapUIMStsRed");
			break;
		case (iTempAge < 5):
			this.addStyleClass("sapUIMStsGreen");
			break;
		default:
			this.addStyleClass("sapUIMStsGreen");
		}
		return iAgingCount;
	}

};