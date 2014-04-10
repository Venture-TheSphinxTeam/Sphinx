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

function setModalBody_DeleteQuery(queryName, username){
	
	$("#modal-label").html("Delete Query Subscription");

	// set inner html
	$("#modal-body-text").html("<p>Are you sure you would like to unsubscribe from this query?</p>");
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
			removeQuerySubscription(queryName, username)
		})
	;

}


function setModalBody_UpdateQuery(queryName, username, facets, types){
	
	 var form = document.getElementById("editQueryModal-form").innerHTML;
	
	
	$("#editQueryModal-label").html("Edit Query Subscription");
	// set inner html
	$("#editQueryModal-body-text").html("<p>Please choose new facets for this query.</p>");
	$("#editQueryModal-form").empty();
	$("#editQueryModal-form").html(form);
	
	
	var priority = facets.match(/priority: "(.*?)"/);
	var status = facets.match(/status: "(.*?)"/);
	var reporter = facets.match(/reporter: "(.*?)"/);
	var assignee = facets.match(/assignee: "(.*?)"/);
	var label = facets.match(/labels: "(.*?)"/);
	
	if(priority != null){
	document.getElementById("priority").value = priority[1];
	}
	
	if(status != null){
		document.getElementById("status").value = status[1];
		}
	
	if(reporter != null){
		document.getElementById("reporter").value = reporter[1];
		}
	
	if(assignee != null){
		document.getElementById("assignee").value = assignee[1];
		}
	
	if(label != null){
		document.getElementById("labels").value = label[1];
		}
	
	
	$('.selectpicker').selectpicker();
	
	// update buttons
	$("#editQueryModal-button1").html("Cancel");
	$("#editQueryModal-button1").removeClass();
	$("#editQueryModal-button1").addClass("btn btn-default");

	$("#editQueryModal-button2").html("Save");
	$("#editQueryModal-button2").removeClass();
	$("#editQueryModal-button2").addClass("btn btn-primary");

	// Add function to update entity upon clicking 'Yes'
	$("#editQueryModal-button2").click(
		function(){
			updateQuerySubscription(queryName, username)
		})
	;
}




function removeQuerySubscription(queryName, username){

	var json = { 'name': queryName, 
				 'username':   username };

	var url = "/deleteQuerySubscription";

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

function updateQuerySubscription(queryName, username){
	
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
    
    var json = {
            "facets": "[" + facets + "]",
                "name": queryName,
                "username": username
        };

        $.ajax({
    		type: "POST",
    		url: "/updateQuerySubscription",
    		data: JSON.stringify(json),
    		datatype: "json",
    		contentType: 'application/json; charset=utf-8',
    		success: function (data){
    			location.reload();
    		}
    	})

	
	
}


function saveQuery(username) {

    var facets = "";
    var pickers = $(".selectpicker");

    var keyword = $("#entitySearch").val().trim();
    var keyType;
    if(keyword.length >0){
        keyType = $("#radio_div :selected").val().trim();
        facets += "{\"name\" : \"" +keyType + "\", \"value\" : \"" + keyword + "\" }, "
    }
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
            "username": username,
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




