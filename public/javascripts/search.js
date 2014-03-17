/**
 * 
 */

function keywordSearch(){
var patt = document.getElementById("entitySearch").value;
var radio_field = $('.btn.btn-primary.active').attr('id');
window.location.href = "./search?arg=" + patt + "&field=" + radio_field;
}


$('.btn').button();
