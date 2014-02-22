
/** 
 * Subscribe to an entity
 */
function updateEntitySubscriptionStatus(entityType,entityId,username){

	// Update entity subscription in the backend aka subscribe to or unsubscribe from
	var json = { 'entityType': entityType, 
				 'entityId': 	 entityId,
				 'username':   username };

	$.ajax({
		type: "POST",
		url: "/updateSubscription",
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data){
			updateSubscriptionButtonsOnPage(entityType,entityId,data['newState'] );
		}
	})

}

/**
 * Get the subscription status of entityType-entityId.
 *  Typically used for initially setting buttons.
 */
function setStatusOfSubscriptionButtons(entityType,entityId,username){

	var json = { 'entityType': entityType, 
			 	 'entityId': 	 entityId,
			 	 'username':   username };
	//Need to get a hole of the button element itself.//
	$.ajax({
		type: "POST",
		url: "/getSubscriptionStatus",
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data){
			updateSubscriptionButtonsOnPage(entityType,entityId,data['status']);
		}
	})
}

//-------------------SUBFUNCTIONS-----------------------//

/**
 *  Update all subscription buttons related to that entityType-entityid
 */
function updateSubscriptionButtonsOnPage(entityType,entityId,status){

	var buttons = document.getElementsByName("subscribe_b-"+entityType+"-"+entityId);
	if( status == false ){
		for( var i = 0; i < buttons.length; i++ ) {
			buttons[i].className = "btn btn-primary";
		}
	} else {
		for( var i = 0; i < buttons.length; i++ ) {
			buttons[i].className = "btn btn-primary active";
		}
	}

}