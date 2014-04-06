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
			$("#entity-comments").load("/ #comment-container div");
		}
	})

	//$.ajax({
	//			type: "GET",
	//			url: $(this).attr('href'), //current url
				//data: JSON.stringify(json),
				//datatype: "json",
	//			contentType: 'application/json; charset=utf-8'
	//		})
}

function editComment(commentHeader,comment){
	var json = { 'entityType': entityType, 
	'entityId': 	 entityId,
	'createdBy':   createdBy,
	'commentHeader': commentHeader,
	'comment': comment,  };

	var url = "";

	$.ajax({
		type: "POST",
		url: url,
		data: JSON.stringify(json),
		datatype: "json",
		contentType: 'application/json; charset=utf-8',
		
	})
}