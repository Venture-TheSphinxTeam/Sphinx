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
			alert("This was a success!!!\nSubscribe Status is now :" + data['newState']);
		}
	})

	//update the status of each subscribe button related to type TYPE-ID


	// Update all subscription buttons of events for this type-id
}

/**
 * Get the subscription status of entityType-entityId.
 *  Typically used for initially setting buttons.
 */
function getEntitySubscriptionStatus(entityType,entityId,username){

	System.out.println("I am getting the subscription state to set initial button.");

	var json = { 'entityType': entityType, 
			 	 'entityId': 	 entityId,
			 	 'username':   username };

	$.ajax({
		type: "POST",
		url: "/getSubscriptionStatus",
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data){
			if( data['status'] == false ){
				return "btn btn-primary";
			} else {
				return "btn btn-primary active";
			}
		}
	})
}
