/** 
 * Subscribe to an entity
 */
function updateEntitySubscriptionStatus(entityType,entityId,username){
	alert("Entity Type: " + entityType + "\nEntity ID: " + entityId + "\nUsername: " + username);

	// Get current class so can get current status of button
	//alert(entityType + "-" + entityId + "-subscribe");
	//currentClass = document.getElementById( entityType + "-" + entityId + "-subscribe" ).className;

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
