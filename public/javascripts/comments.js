
function setCommentBox_New(entityType, entityId, createdBy){
	var allFields = $([]).add(commentHeader).add(comment);

	$("#modal-label").html("New Comment");

	$("#modal-body-text").html("Enter your new comment and hit Submit. ");

	$("#modal-form").html("<input type='text' name='comment' id='comment'>")

	$("#modal-button1").html("Cancel");
	$("#modal-button1").removeClass();
	$("#modal-button1").addClass("btn btn-primary");

	$("#modal-button2").html("Sumbit");
	$("#modal-button2").removeClass();
	$("#modal-button2").addClass("btn btn-default");

	$("#modal-button2").click(
		function(){
			var comment = $("#comment");
			//will need to prevent submitting empty comments
			createComment(entityType, entityId, createdBy, "", comment.val())
		}
	);

}

function setCommentBox_Edit(){
	var allFields = $([]).add(commentHeader).add(comment);

	$("#modal-label").html("Edit Comment");

	$("#modal-body-text").html("Edit your comment and hit Submit. ");

	$("#modal-button1").html("Cancel");
	$("#modal-button1").removeClass();
	$("#modal-button1").addClass("btn btn-primary");

	$("#modal-button2").html("Sumbit");
	$("#modal-button2").removeClass();
	$("#modal-button2").addClass("btn btn-default");

	$("#modal-button2").click(
		function(){
			editComment(entityType, entityId, createdBy, "", comment)
		}
	);

}

function setCommentBox_Delete(){
	var allFields = $([]).add(commentHeader).add(comment);

	$("#modal-label").html("Delete Comment");

	$("#modal-body-text").html("Are you sure you want to delete your comment? ");

	$("#modal-button1").html("Cancel");
	$("#modal-button1").removeClass();
	$("#modal-button1").addClass("btn btn-primary");

	$("#modal-button2").html("Delete");
	$("#modal-button2").removeClass();
	$("#modal-button2").addClass("btn btn-default");

	$("#modal-button2").click(
		function(){
			deleteComment(entityType, entityId, createdBy, "", comment)
		}
	);
}

function createComment(entityType, entityId,createdBy,commentHeader,comment){

	var json = { 'entityType': entityType, 
	'entityId': 	 entityId,
	'createdBy':   createdBy,
	'commentHeader': commentHeader,
	'comment': comment  };

	var url = "/newComment";

	$.ajax({
		type: "POST",
		url: url,
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function(){
			window.location.reload();
		}
	})

}

function editComment(entityType, entityId, createdBy, commentHeader,comment){
	//NOT COMPLETED
	var json = { 'entityType': entityType, 
	'entityId': 	 entityId,
	'createdBy':   createdBy,
	'commentHeader': commentHeader,
	'comment': comment  };

	var url = "/newComment";

	$.ajax({
		type: "POST",
		url: url,
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function(){
			window.location.reload();
		}
	})
}

function deleteComment(entityType, entityId, createdBy, commentHeader,comment){
	//NOT COMPLETED
	var json = { 'entityType': entityType, 
	'entityId': 	 entityId,
	'createdBy':   createdBy,
	'commentHeader': commentHeader,
	'comment': comment  };

	var url = "/newComment";

	$.ajax({
		type: "POST",
		url: url,
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function(){
			window.location.reload();
		}
	})
}