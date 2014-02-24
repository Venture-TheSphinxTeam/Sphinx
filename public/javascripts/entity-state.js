
/** 
 * Subscribe/vote/watch status change toggled from true to false or vice versa
 */
function updateEntityStateStatus(entityType,entityId,username,buttonType){

	var json = { 'entityType': entityType, 
				 'entityId': 	 entityId,
				 'username':   username };

	// set url to post data to ie. to subscribe, vote or watch
	var url;
	if( buttonType.toLowerCase() == 'subscription'){
		url = "/updateSubscription";
	}
	else if(  buttonType.toLowerCase() == 'watch'){
		//url = "/updateWatch"
	}
	else{	// vote
		//url = "/updateVote"
	}

	$.ajax({
		type: "POST",
		url: url,
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data){
			
			// update all buttons of buttenType on the page
			updateButtonsOnPage(entityType,entityId,data['status'], buttonType);

		}
	})

}

/**
 * Gets and updates status of all buttons of entityType-entityId on the page
 */
function setStatusOfAllButtons(entityType,entityId,username){
	setStatusOfButtons(entityType,entityId,username,'subscription');
	//setStatusOfButtons(entityType,entityId,username,buttonType);
	//setStatusOfButtons(entityType,entityId,username,buttonType);
}

/**
 * Gets and updates status of all of a buttonType on the page
 */
function setStatusOfButtons(entityType,entityId,username,buttonType){

	var json = { 'entityType': entityType, 
			 	 'entityId': 	 entityId,
			 	 'username':   username };

	// set url to post data to ie. to subscribe, vote or watch
	var url;
	if( buttonType.toLowerCase() == 'subscription'){
		url = "/getSubscriptionStatus";
	}
	else if(  buttonType.toLowerCase() == 'watch'){
		//url = "/getWatchStatus"
	}
	else{	// vote
		//url = "/getVoteStatus"
	}

	//Need to get a hole of the button element itself.//
	$.ajax({
		type: "POST",
		url: url,
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data){
			// update all buttons of buttenType on the page
			updateButtonsOnPage(entityType,entityId,data['status'], "subscription");
			//updateButtonsOnPage(entityType,entityId,data['status'], "vote");
			//updateButtonsOnPage(entityType,entityId,data['status'], "watch");
		}
	})
}

//-------------------SUBFUNCTIONS-----------------------//

/**
 *
 */


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