/*
 *   Set HTML parameters in body of modal 
 * for a specific entityType-entityId 
 */
function setModalBody_Edit(entityType,entityId){ 
	
	$("#modal-label").html("Edit Entity Subscription");

	// set body of modal
	$("#modal-body-text").html("Event types subscribed to :");
	
	// set checkbox statuses
	setModalCheckboxes(entityType,entityId);

	$("#modal-button1").html("Cancel");
	$("#modal-button1").removeClass();
	$("#modal-button1").addClass("btn btn-primary");

	$("#modal-button2").html("Save Changes");
	$("#modal-button2").removeClass();
	$("#modal-button2").addClass("btn btn-default");

	// Add function to delete entity upon clicking 'Yes'
	$("#modal-button2").click(
		function(){
			updateSubscriptionsEvents( entityType, entityId )
		}
	);
}

function setModalBody_Delete(entityType,entityId){
	
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
	$("#modal-button2").click(
		function(){
			removeSubscription(entityType,entityId)
		})
	;

}

function removeSubscription(entityType,entityId){

	var json = { 'entityType': entityType, 
				 'entityId': 	 entityId };

	var url = "/deleteEntitySubscription";

	myJsRoutes.controllers.SubscriptionController.DeleteEntitySubscription().ajax({
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data){
			location.reload();
		}
	})

}

function setModalCheckboxes(entityType,entityId){

	var json = { 'entityType': entityType, 
			 	 'entityId': 	 entityId };

	var url = "/getEntityEventSubscriptions";

	myJsRoutes.controllers.SubscriptionController.GetEntityEventSubscriptions().ajax({
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data){

			// Add iputs to form
			$("#modal-form").html("<br><input type='checkbox' name='event-subscriptions' id='event-report'>Report<br> <input type='checkbox' name='event-subscriptions' id='event-timespent'>Timespent<br> <input type='checkbox' name='event-subscriptions' id='event-create'>Create<br> <input type='checkbox' name='event-subscriptions' id='event-update'>Update<br> <input type='checkbox' name='event-subscriptions' id='event-delete'>Delete"); 

			// Set check box
			$("#event-report").attr('checked', data["REPORT"]);
			$("#event-timespent").attr('checked', data["TIMESPENT"]);
			$("#event-create").attr('checked', data["CREATE"]);
			$("#event-update").attr('checked', data["UPDATE"]);
			$("#event-delete").attr('checked', data["DELETE"]);
		}
	})

}

function updateSubscriptionsEvents(entityType,entityId){

	// create array of events subscribed to
	$eventsSubscribedTo = [];
	if( $("#event-report").is(":checked") ){
		$eventsSubscribedTo.push("REPORT");
	}
	if( $("#event-timespent").is(":checked")){
		$eventsSubscribedTo.push("TIMESPENT");
	}
	if( $("#event-create").is(":checked") ){
		$eventsSubscribedTo.push("CREATE");
	}
	if( $("#event-update").is(":checked") ){
		$eventsSubscribedTo.push("UPDATE");
	}
	if( $("#event-delete").is(":checked") ){
		$eventsSubscribedTo.push("DELETE");
	}

	var json = { 'entityType': entityType, 
			     'entityId': 	 entityId,
			 	 'eventSubscriptions': $eventsSubscribedTo.toString()	//sent through as a string because had to
			 	};

	var url = "/updateSubscriptionsEvents";

	myJsRoutes.controllers.SubscriptionController.UpdateSubscriptionsEvents().ajax({
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data){
		}
	})

}