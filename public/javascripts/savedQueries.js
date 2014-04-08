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

function saveQuery(username) {

    var facets = "";
    var pickers = $(".selectpicker");

    //Construct a string of facet objects
    pickers.each(

    function () {
        //console.log($(":selected", this).val());
        if ($(":selected", this).val() != $("option", this).first().val()) {
            facets = facets + "{ \"name\": \"" + $(this).attr('id') + "\", \"value\": \"" + $(":selected", this).text() + "\"}, ";
        }
    });

    //Cut off the trailing comma
    if (facets.length > 0) {
        facets = facets.slice(0, facets.length - 2);
    }

    var name = $("#queryName").val();

    var eventTypes = "[";

    $("#modal-chkbox div input:checked").each(

    function () {
                eventTypes = eventTypes +"\"" + $(this).val() + "\",";                
    });

    if(eventTypes.length >1){
      eventTypes = eventTypes.slice(0, eventTypes.length-1);
    }
    
    eventTypes += "]";
    

    var json = {
        "facets": "[" + facets + "]",
            "name": name,
            "username": "jay-z",
            "eventTypes": eventTypes
    };

    $.ajax({
		type: "POST",
		url: "/saveQuery",
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data){
			location.reload();
		}
	})
}




