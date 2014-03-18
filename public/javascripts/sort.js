/**
 * 
 */

function sortAlpha(desc) {
	if (desc) {
		$('#entities').html($("#entities li").sort(desc_alpha_sort));
	}

	else {
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
		$('#entities').html($("#entities li").sort(desc_proposed_sort));
	} else {
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
		$('#entities').html($("#entities li").sort(desc_recent_sort));
	} else {
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
