/** 
 * Subscribe to an entity
 */
function subscribeToEntity(entityType,entityId,username){
	alert("Entity Type: " + entityType + "\nEntity ID: " + entityId + "\nUsername: " + username);

	// Get current status of button
	alert(entityType + "-" + entityId + "-subscribe");
	currentClass = document.getElementById( entityType + "-" + entityId + "-subscribe" ).className;

	// Based on current status subscribe or unsubscribe user
	if (currentClass == "btn btn-primary active") {
		
		//unsubscribe user
		alert("unsubscribing");

		//update the status of each subscribe button related to type TYPE-ID
		//document.getElementById( entityType + "-" + entityId + "-subscribe" )
	}
	else{
		//subscribe user
		alert("subscribing")

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
				alert("this was a success!!!");
				alert(data['newState']);
			}
		})

		//update the status of each subscribe button related to type TYPE-ID

	}
}
