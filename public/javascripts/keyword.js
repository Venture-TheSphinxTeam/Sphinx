/**
 * 
 */

function keywordSearch(){
var patt = document.getElementById("entitySearch").value;
var radio_field = $("input:radio[name='keyword']:checked").val();
window.location.href = "./search?arg=" + patt + "&field=" + radio_field;
}


$('.btn').button();
