
function setCommentBox_New(entityType, entityId, createdBy){
	var allFields = $([]).add(commentHeader).add(comment);

	$("#modal-label").html("New Comment");

	$("#modal-body-text").html("Enter your new comment and hit Submit. ");

	$("#modal-form").html("<textarea name='comment' id='comment' class='form-control' row='10'>")

	$("#modal-button1").html("Cancel");
	$("#modal-button1").removeClass();
	$("#modal-button1").addClass("btn btn-primary");

	$("#modal-button2").html("Submit");
	$("#modal-button2").removeClass();
	$("#modal-button2").addClass("btn btn-default");

	$("#modal-button2").off('click');

	$("#modal-button2").click(
		function(){
			var comment = $("#comment");
			//will need to prevent submitting empty comments
			if(comment.val() == ""){
				comment.val("I have nothing to say at this particular point in time.")
			}
			createComment(entityType, entityId, createdBy, "", comment.val())
		}
	);

}

function setCommentBox_Edit(entityType, entityId, createdBy, comment, objId){
	var commentBody = comment;
	$("#modal-label").html("Edit Comment");

	$("#modal-body-text").html("Edit your comment and hit Submit. ");

	//grab comment object set text box with current body text
	$("#modal-form").html("<textarea name='comment' id='comment' class='form-control' row='10'>");
	document.getElementById('comment').value = commentBody;

	$("#modal-button1").html("Cancel");
	$("#modal-button1").removeClass();
	$("#modal-button1").addClass("btn btn-primary");

	$("#modal-button2").html("Edit");
	$("#modal-button2").removeClass();
	$("#modal-button2").addClass("btn btn-default");

	$("#modal-button2").off('click');

	$("#modal-button2").click(
		function(){
			//pass in _id
			var comment = $("#comment");
			//will need to prevent submitting empty comments
			if(comment.val() == ""){
				comment.val("[COMMENT REDACTED]")
			}
			editComment(entityType, entityId, createdBy, '', comment.val(), objId)
		}
	);

}

function setCommentBox_Delete(objectId){
	$("#modal-label").html("Delete Comment");

	$("#modal-body-text").html("Are you sure you want to delete your comment? ");
	$("#modal-form").html("")

	$("#modal-button1").html("Cancel");
	$("#modal-button1").removeClass();
	$("#modal-button1").addClass("btn btn-primary");

	$("#modal-button2").html("Delete");
	$("#modal-button2").removeClass();
	$("#modal-button2").addClass("btn btn-default");

	$("#modal-button2").off('click');

	$("#modal-button2").click(
		function(){
			//pass in _id
			deleteComment('','','','','',objectId)
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

	myJsRoutes.controllers.CommentsController.newComment().ajax({
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function(){
			window.location.reload();
		}
	})

}

function editComment(entityType, entityId, createdBy, commentHeader, comment, objectId){

	var json = { '_id': objectId,
	'entityType': entityType, 
	'entityId': 	 entityId,
	'createdBy':   createdBy,
	'commentHeader': commentHeader,
	'comment': comment  };

	var url = "/editComment";

	myJsRoutes.controllers.CommentsController.changeComment().ajax({
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function(){
			window.location.reload();
		}
	})

}

function deleteComment(entityType, entityId, createdBy, commentHeader, comment,objectId){
	var json = { '_id': objectId,
	'entityType': entityType, 
	'entityId': entityId,
	'createdBy':  createdBy,
	'commentHeader': commentHeader,
	'comment': comment };

	var url = "/deleteComment";

	myJsRoutes.controllers.CommentsController.removeComment().ajax({
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		success: function(){
			window.location.reload();
		}
	})

}