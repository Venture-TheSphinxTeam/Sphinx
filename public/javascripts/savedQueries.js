function setSQModalBody(){ 
	
	$("#modal-label").html("Save As Saved Query");
	//alert("Beep Boop");
	//$("#modal-save").click()

	

	/*		<form>
		<input type="" name = "queryName" value="New Query"></br>
	</form>*/

	$("#cancel").removeClass();
	$("#cancel").addClass("btn btn-primary");

	$("#save").removeClass();
	$("#save").addClass("btn btn-default");
	$("#save").click(saveQuery);
}

function saveQuery(name){
	var facets="";
	$(".selectpicker").each(
		function(){
			facets = facets + "{ name: " + $(this).attr('id') + ", value :" + $(this).(":selected").text() +"}, ";
		}

		)
	
}