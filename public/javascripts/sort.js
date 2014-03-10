/**
 * 
 */

$('#entities').html($("#entities li").sort(asc_sort));

function asc_sort(a, b) {
	return ($(b).find('h3:first-child').text()) < ($(a).find('h3:first-child')
			.text()) ? 1 : -1;
}
