function setSQModalBody() {

	$("#modal-label").html("Save As Saved Query");

	$("#cancel").removeClass();
	$("#cancel").addClass("btn btn-primary");

	$("#save").removeClass();
	$("#save").addClass("btn btn-default");
	$("#save").click(saveQuery);
}

function executeQuery(facets) {

	var priority = facets.match(/priority: "(.*?)"/);
	var status = facets.match(/status: "(.*?)"/);
	var reporter = facets.match(/reporter: "(.*?)"/);
	var assignee = facets.match(/assignee: "(.*?)"/);
	var label = facets.match(/labels: "(.*?)"/);

	if (priority != null) {
		priority = priority[1];
	} else {
		priority = "";
	}

	if (status != null) {
		status = status[1];
	} else {
		status = "";
	}

	if (reporter != null) {
		reporter = reporter[1];
	} else {
		reporter = "";
	}

	if (assignee != null) {
		assignee = assignee[1];
	} else {
		assignee = "";
	}

	if (label != null) {
		label = label[1];
	} else {
		label = "";
	}

	setCookie("priority", priority);
	setCookie("status", status);
	setCookie("reporter", reporter);
	setCookie("assignee", assignee);
	setCookie("labels", label);
	window.location.href = "./search?arg=" + "" + "&field=" + "" + "&priority="
			+ priority + "&status=" + status + "&reporter=" + reporter
			+ "&assignee=" + assignee + "&label=" + label;
}

function setModalBody_DeleteQuery(queryName) {

	$("#modal-label").html("Delete Query Subscription");

	// set inner html
	$("#modal-body-text")
			.html(
					"<p>Are you sure you would like to unsubscribe from this query?</p>");
	$("#modal-form").html("");

	// update buttons
	$("#modal-button1").html("Cancel");
	$("#modal-button1").removeClass();
	$("#modal-button1").addClass("btn btn-default");

	$("#modal-button2").html("Yes");
	$("#modal-button2").removeClass();
	$("#modal-button2").addClass("btn btn-primary");

	// Add function to delete entity upon clicking 'Yes'
	$("#modal-button2").click(function() {
		removeQuerySubscription(queryName)
	});

}

function setModalBody_UpdateQuery(queryName, facets, types) {

	var form = document.getElementById("editQueryModal-form").innerHTML;

	$("#editQueryModal-label").html("Edit Query Subscription");
	// set inner html
	$("#editQueryModal-body-text").html(
			"<p>Please choose new facets for this query.</p>");
	$("#editQueryModal-form").empty();
	$("#editQueryModal-form").html(form);

	var priority = facets.match(/priority: "(.*?)"/);
	var status = facets.match(/status: "(.*?)"/);
	var reporter = facets.match(/reporter: "(.*?)"/);
	var assignee = facets.match(/assignee: "(.*?)"/);
	var label = facets.match(/labels: "(.*?)"/);

	var create = types.search("CREATE");
	var update = types.search("UPDATE");
	var _delete = types.search("DELETE");
	var report = types.search("REPORT");
	var time = types.search("TIMESPENT");

	if (priority != null) {
		document.getElementById("priority").value = priority[1];
	}

	if (status != null) {
		document.getElementById("status").value = status[1];
	}

	if (reporter != null) {
		document.getElementById("reporter").value = reporter[1];
	}

	if (assignee != null) {
		document.getElementById("assignee").value = assignee[1];
	}

	if (label != null) {
		document.getElementById("labels").value = label[1];
	}

	if (report != -1) {
		$("#event-report_e").prop('checked', true);
	}
	if (time != -1) {
		$("#event-timespent_e").prop('checked', true);
	}
	if (create != -1) {
		$("#event-create_e").prop('checked', true);
	}
	if (update != -1) {
		$("#event-update_e").prop('checked', true);
	}
	if (_delete != -1) {
		$("#event-delete_e").prop('checked', true);
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
	$("#editQueryModal-button2").click(function() {
		updateQuerySubscription(queryName)
	});
}

function removeQuerySubscription(queryName) {

	var json = {
		"name" : queryName,
	};
	var url = "/deleteQuerySubscription";

	myJsRoutes.controllers.SavedQueryController.deleteQuerySubscription().ajax(
			{
				data : JSON.stringify(json),
				datatype : "json",
				contentType : 'application/json; charset=utf-8',
				success : function(data) {
					location.reload();
				}
			})

}

function updateQuerySubscription(queryName) {

	var facets = "";
	var pickers = $(".selectpicker");

	// Construct a string of facet objects
	pickers.each(

	function() {
		// console.log($(":selected", this).val());
		if ($(":selected", this).val() != $("option", this).first().val()) {
			facets = facets + "{ \"name\": \"" + $(this).attr('id')
					+ "\", \"value\": \"" + $(":selected", this).text()
					+ "\"}, ";
		}
	});

	// Cut off the trailing comma
	if (facets.length > 0) {
		facets = facets.slice(0, facets.length - 2);
	}

	var eventTypes = "[";

	$("#modal-chkbox div input:checked").each(

	function() {
		eventTypes = eventTypes + "\"" + $(this).val() + "\",";
	});

	if (eventTypes.length > 1) {
		eventTypes = eventTypes.slice(0, eventTypes.length - 1);
	}

	eventTypes += "]";

	var json = {
		"facets" : "[" + facets + "]",
		"name" : queryName,
		"eventTypes" : eventTypes
	};

	myJsRoutes.controllers.SavedQueryController.updateQuerySubscription().ajax(
			{
				data : JSON.stringify(json),
				datatype : "json",
				contentType : 'application/json; charset=utf-8',
				success : function(data) {
					location.reload();
				}
			})

}

function saveQuery() {

	var facets = "";
	var pickers = $(".selectpicker");

	var keyword = $("#entitySearch").val().trim();
	var keyType;
	if (keyword.length > 0) {
		keyType = $("#radio_div :selected").val().trim();
		facets += "{\"name\" : \"" + keyType + "\", \"value\" : \"" + keyword
				+ "\" }, "
	}
	// Construct a string of facet objects
	pickers.each(

	function() {
		// console.log($(":selected", this).val());
		if ($(":selected", this).val() != $("option", this).first().val()) {
			facets = facets + "{ \"name\": \"" + $(this).attr('id')
					+ "\", \"value\": \"" + $(":selected", this).text()
					+ "\"}, ";
		}
	});

	// Cut off the trailing comma
	if (facets.length > 0) {
		facets = facets.slice(0, facets.length - 2);
	}

	var name = $("#queryName").val();

	var eventTypes = "[";

	$("#modal-chkbox div input:checked").each(

	function() {
		eventTypes = eventTypes + "\"" + $(this).val() + "\",";
	});

	if (eventTypes.length > 1) {
		eventTypes = eventTypes.slice(0, eventTypes.length - 1);
	}

	eventTypes += "]";

	var json = {
		"facets" : "[" + facets + "]",
		"name" : name,
		"eventTypes" : eventTypes
	};

	myJsRoutes.controllers.SavedQueryController.saveQuery().ajax({
		data : JSON.stringify(json),
		datatype : "json",
		contentType : 'application/json; charset=utf-8',
		success : function(data) {
			// location.reload();
		}
	})
}
