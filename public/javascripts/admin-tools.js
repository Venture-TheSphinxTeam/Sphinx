
function setAdminButtonStatuses(){

	var json = { };

	myJsRoutes.controllers.AdminController.getAdminStatusOfUsers().ajax({
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data){

			var usernames = data["usernames"].split(",");
			var adminStatuses = data["adminStatuses"].split(",");

			// update admin checkboxes
			for (var i=0; i<usernames.length; i++){
				$("#adminStatus-" + usernames[i]).attr('checked', $.parseJSON(adminStatuses[i]) );
			}
		}
	});
}

function toggleAdminStatusOfUser(username){
	
	var json = { 'username': username };

	myJsRoutes.controllers.AdminController.toggleAdminStatusOfUser().ajax({
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function (data){
			alert("Admin status of '" + username + "' has been successfully changed.")
		}
	});

}
