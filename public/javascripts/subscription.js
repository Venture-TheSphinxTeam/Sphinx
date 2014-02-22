
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

			//update the status of each subscribe button related to type TYPE-ID
			var buttonsToUpdate = document.getElementsByName("subscribe_b-"+entityType+"-"+entityId);

			alert("Updating the all related buttons--TODO: TEST ME WITH MORE DATA");
			if( data['newState'] == true ){
				buttonsToUpdate.className = "btn btn-primary active"
			} 
			else {
				buttonsToUpdate.className = "btn btn-primary"
			}

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

			// Update all subscription buttons related to that entityType-entityid
			var buttons = document.getElementsByName("subscribe_b-"+entityType+"-"+entityId);
			if( data['status'] == false ){
				for( var i = 0; i < buttons.length; i++ ) {
					buttons[i].className = "btn btn-primary";
				}
			} else {
				for( var i = 0; i < buttons.length; i++ ) {
					buttons[i].className = "btn btn-primary active";
				}
			}
		}
	})
}