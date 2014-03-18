/*
 *   Set HTML parameters in body of modal 
 * for a specific entityType-entityId 
 */
function setModalBody_Edit(entityType,entityId,username){ 
	
	$("#modal-label").html("Edit Entity Subscription");

	//$("#modal-save").click(removeSubscription)

	/*		<form action="">
			<input type="checkbox" name="Test1" value="test1"> I am a test<br>
			<input type="checkbox" name="Test2" value="test2"> I am a test 2<br>
		</form>*/

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
	$("#modal-body").html("<p>Are you sure you would like to unsubscribe from this entity?</p>");

	// update buttons
	$("#modal-button1").html("Cancel");
	$("#modal-button1").removeClass();
	$("#modal-button1").addClass("btn btn-default");

	$("#modal-button2").html("Yes");
	$("#modal-button2").removeClass();
	$("#modal-button2").addClass("btn btn-primary");
	//$("#modal-button2").click(function(){ alert(entityType) });
	$("#modal-button2").click(function(){removeSubscription(entityType,entityId,username)});

}