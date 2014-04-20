
function setCommentBox_New(entityType, entityId, createdBy){
	var allFields = $([]).add(commentHeader).add(comment);

	$("#modal-label").html("New Comment");

	$("#modal-body-text").html("Enter your new comment and hit Submit. ");

	$("#modal-form").html("<textarea name='comment' id='comment' class='form-control' row='10'>")

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

function setCommentBox_Edit(_id, entityType, entityId, createdBy){
	var allFields = $([]).add(commentHeader).add(comment);

	$("#modal-label").html("Edit Comment");

	$("#modal-body-text").html("Edit your comment and hit Submit. ");

	//grab comment object set text box with current body text

	$("#modal-button1").html("Cancel");
	$("#modal-button1").removeClass();
	$("#modal-button1").addClass("btn btn-primary");

	$("#modal-button2").html("Sumbit");
	$("#modal-button2").removeClass();
	$("#modal-button2").addClass("btn btn-default");

	$("#modal-button2").click(
		function(){
			//pass in _id
			editComment(_id, entityType, entityId, createdBy, "", comment)
		}
	);

}

function setCommentBox_Delete(objectId){
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
			//pass in _id
			deleteComment(objectId)
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

	var url = "/editComment";

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

function deleteComment(objectId){
	var json = { '_id': objectId.toString() };

	var url = "/deleteComment";

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