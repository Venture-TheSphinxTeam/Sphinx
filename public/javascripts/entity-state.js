
/** 
 * Subscribe/vote/watch status change toggled from true to false or vice versa
 */
function updateEntityStateStatus(entityType,entityId,buttonType){

	var json = { 'entityType': entityType, 
				 'entityId': 	 entityId,
				 'buttonType': buttonType };

	var url = "/updateButtonStatus";

	myJsRoutes.controllers.ButtonStateController.UpdateButtonStatus().ajax({
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data){
			updateButtonsOnPage(entityType,entityId,data['status'], buttonType);
		}
	})

}

/**
 * Gets and updates status of all buttons of entityType-entityId on the page
 */
function setStatusOfAllButtons(entityType,entityId){
	setStatusOfButtons(entityType,entityId,"subscription");
	setStatusOfButtons(entityType,entityId,"vote");
	setStatusOfButtons(entityType,entityId,"watch");
}

/**
 * Gets and updates status of all of a buttonType on the page
 */
function setStatusOfButtons(entityType,entityId,buttonType){

	var json = { 'entityType': entityType, 
				 'entityId': 	 entityId,
				 'buttonType': buttonType };

	var url = "@routes.ButtonStateController.GetButtonStatus()";


	myJsRoutes.controllers.ButtonStateController.GetButtonStatus().ajax({
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8', 
		success : function (data){
			updateButtonsOnPage(entityType,entityId,data['status'], buttonType);
		}
	})
	//Need to get a hole of the button element itself.//
}

//-------------------SUBFUNCTIONS-----------------------//

/**
 *  Update all buttons of buttonType related to that entityType-entityid
 */
function updateButtonsOnPage(entityType,entityId,status,buttonType){

	var buttons = document.getElementsByName(buttonType+"_b-"+entityType+"-"+entityId);
	if( status == false ){

		for( var i = 0; i < buttons.length; i++ ) {
			buttons[i].className = "btn btn-primary";
		}
	} else if (status == true) {
		for( var i = 0; i < buttons.length; i++ ) {
			buttons[i].className = "btn btn-primary active";
		}
	} else {
		alert("Button status was not correctly gathered")
	}
}

/** 
 * Subscribe/vote/watch status change toggled from true to false or vice versa
 */
function removeSubscription(entityType,entityId){

	var json = { 'entityType': entityType, 
				 'entityId': 	 entityId,
				 'buttonType': 'subscription' };

	var url = "/updateButtonStatus";

	$.ajax({
		type: "POST",
		url: '@routes.ButtonStateController.UpdateButtonStatus()',
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data){
			location.reload();
		}
	})

}