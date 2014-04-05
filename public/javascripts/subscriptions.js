/*
 *   Set HTML parameters in body of modal 
 * for a specific entityType-entityId 
 */
function setModalBody_Edit(entityType,entityId,username){ 
	
	$("#modal-label").html("Edit Entity Subscription");

	// set body of modal
	$("#modal-body-text").html("Event types subscribed to :");
	$("#modal-form").html("<br><input type='checkbox' name='event-subscriptions' id='event-report'>Report<br> <input type='checkbox' name='event-subscriptions' id='event-timespent'>Timespent<br> <input type='checkbox' name='event-subscriptions' id='event-create'>Create<br> <input type='checkbox' name='event-subscriptions' id='event-update'>Update<br> <input type='checkbox' name='event-subscriptions' id='event-delete'>Delete"); 

	// set checkbox statuses
	$event_subscriptions = getEventSubscriptions(entityType,entityId,username);

	//$("#modal-save").click(updateSubscription());

	// TODO	

	$("#modal-button1").html("Cancel");
	$("#modal-button1").removeClass();
	$("#modal-button1").addClass("btn btn-primary");

	$("#modal-button2").html("Save Changes");
	$("#modal-button2").removeClass();
	$("#modal-button2").addClass("btn btn-default");
}

function setModalBody_Delete(entityType,entityId,username){
	
	$("#modal-label").html("Delete Entity Subscription");

	// set inner html
	$("#modal-body-text").html("<p>Are you sure you would like to unsubscribe from this entity?</p>");
	$("#modal-form").html("");

	// update buttons
	$("#modal-button1").html("Cancel");
	$("#modal-button1").removeClass();
	$("#modal-button1").addClass("btn btn-default");

	$("#modal-button2").html("Yes");
	$("#modal-button2").removeClass();
	$("#modal-button2").addClass("btn btn-primary");

	// Add function to delete entity upon clicking 'Yes'
	$("#modal-button2").click(function(){removeSubscription(entityType,entityId,username)});

}

function removeSubscription(entityType,entityId,username){

	var json = { 'entityType': entityType, 
				 'entityId': 	 entityId,
				 'username':   username };

	var url = "/deleteEntitySubscription";

	$.ajax({
		type: "POST",
		url: url,
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data){
			location.reload();
		}
	})

}

function getEventSubscriptions(entityType,entityId,username){

	var json = { 'entityType': entityType, 
			 	 'entityId': 	 entityId,
			 	 'username':   username };

	var url = "/getEntityEventSubscriptions";

	$.ajax({
		type: "POST",
		url: url,
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data){
			return  data['eventsSubscribedTo']
		}
	})

	return null;
}