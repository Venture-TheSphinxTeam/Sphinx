/**
 * 
 */

function setCookie(cname,cvalue)
{
document.cookie = cname + "=" + cvalue;
}

function getCookie(cname)
{
var name = cname + "=";
var ca = document.cookie.split(';');
for(var i=0; i<ca.length; i++) 
  {
  var c = ca[i].trim();
  if (c.indexOf(name)==0) return c.substring(name.length,c.length);
  }
return "";
}

function deleteAllCookies() {
    var cookies = document.cookie.split(";");

    for (var i = 0; i < cookies.length; i++) {
    	var cookie = cookies[i];
    	var eqPos = cookie.indexOf("=");
    	var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
    	document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }
}


function keywordSearch() {
	var patt = document.getElementById("entitySearch").value;
	var radio_field = $('.btn.btn-primary.active').attr('id');
	window.location.href = "./search?arg=" + patt;
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
	setCookie("priority", priority);
	setCookie("status", status);
	setCookie("reporter", reporter);
	setCookie("assignee", assignee);
	setCookie("labels", label);
	window.location.href = "./search?arg=" + "" + "&field=" + "" + "&priority="
			+ priority + "&status=" + status + "&reporter=" + reporter
			+ "&assignee=" + assignee + "&label=" + label;
}

$('.btn').button();
