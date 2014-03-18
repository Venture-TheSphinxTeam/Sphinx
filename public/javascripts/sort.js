/**
 * 
 */


function sortAlpha() {
	$('#entities').html($("#entities li").sort(asc_sort));
	
	$(document).ready(function() {
		$('#page_container').pajinate({
			items_per_page : 15,
			abort_on_small_lists : true
		});
	});
}

function asc_sort(a, b) {
	return ($(b).find("#summary").text()) < ($(a).find("#summary").text()) ? 1
			: -1;
}
