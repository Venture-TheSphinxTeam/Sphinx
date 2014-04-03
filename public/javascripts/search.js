/**
 * 
 */

function keywordSearch() {
	var patt = document.getElementById("entitySearch").value;
	var radio_field = $('.btn.btn-primary.active').attr('id');
	window.location.href = "./search?arg=" + patt + "&field=" + radio_field;
}

function facetSearch() {

	var priority = document.getElementById("priority").value;
	var status = document.getElementById("status").value;
	var reporter = document.getElementById("reporter").value;
	var assignee = document.getElementById("assignee").value;
	var label = document.getElementById("labels").value;

	if (priority.localeCompare("Priority: All") == 0) {
		priority = "";
	}
	if (status.localeCompare("Status: All") == 0) {
		status = "";
	}
	if (reporter.localeCompare("Reporter: All") == 0) {
		reporter = "";
	}
	if (assignee.localeCompare("Assignee: All") == 0) {
		assignee = "";
	}
	if (label.localeCompare("Labels: All") == 0) {
		label = "";
	}
	window.location.href = "./search?arg=" + "" + "&field=" + "" + "&priority="
			+ priority + "&status=" + status + "&reporter=" + reporter
			+ "&assignee=" + assignee + "&label=" + label;
}

$('.btn').button();
