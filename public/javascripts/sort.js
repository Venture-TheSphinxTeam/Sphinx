/**
 * 
 */

function sortAlpha(desc) {
	if (desc) {
		document.getElementById("sort_alpha").innerText = "Sort Alphabetically ▼"; 
		$('#entities').html($("#entities li").sort(desc_alpha_sort));
	}

	else {
		document.getElementById("sort_alpha").innerText = "Sort Alphabetically ▲"; 
		$('#entities').html($("#entities li").sort(alpha_sort));

	}

	$(document).ready(function() {
		$('#page_container').pajinate({
			items_per_page : 15,
			abort_on_small_lists : true
		});
	});
}

function sortProposed(desc) {

	if (desc) {
		document.getElementById("sort_date").innerText = "Sort By Date Proposed ▼"; 
		$('#entities').html($("#entities li").sort(desc_proposed_sort));
	} else {
		document.getElementById("sort_date").innerText = "Sort By Date Proposed ▲"; 
		$('#entities').html($("#entities li").sort(proposed_sort));
	}
	$(document).ready(function() {
		$('#page_container').pajinate({
			items_per_page : 15,
			abort_on_small_lists : true
		});
	});
}

function sortUpdated(desc) {

	if (desc) {
		document.getElementById("sort_updated").innerText = "Sort By Most Recent ▼"; 
		$('#entities').html($("#entities li").sort(desc_recent_sort));
	} else {
		document.getElementById("sort_updated").innerText = "Sort By Most Recent ▲";
		$('#entities').html($("#entities li").sort(recent_sort));
	}
	$(document).ready(function() {
		$('#page_container').pajinate({
			items_per_page : 15,
			abort_on_small_lists : true
		});
	});
}

function alpha_sort(a, b) {
	return ($(b).find("#summary").text()) < ($(a).find("#summary").text()) ? 1
			: -1;
}

function desc_alpha_sort(a, b) {
	return ($(b).find("#summary").text()) > ($(a).find("#summary").text()) ? 1
			: -1;
}

function proposed_sort(a, b) {
	return ($(b).find("#proposed").text()) > ($(a).find("#proposed").text()) ? 1
			: -1;
}

function desc_proposed_sort(a, b) {
	return ($(b).find("#proposed").text()) < ($(a).find("#proposed").text()) ? 1
			: -1;
}

function recent_sort(a, b) {
	return ($(b).find("#updated").text()) > ($(a).find("#updated").text()) ? 1
			: -1;
}

function desc_recent_sort(a, b) {
	return ($(b).find("#updated").text()) < ($(a).find("#updated").text()) ? 1
			: -1;
}
