function onSignIn(googleUser) {
	var profile = googleUser.getBasicProfile();
	/* console.log('ID: ' + profile.getId());
	console.log('Name: ' + profile.getName());
	console.log('Image URL: ' + profile.getImageUrl());
	console.log('Email: ' + profile.getEmail()); */
	var id_token = googleUser.getAuthResponse().id_token;
	var user = {
		"name" : profile.getName(),
		"id" : profile.getId(),
		"image_url" : profile.getImageUrl(),
		"email" : profile.getEmail(),
		"token" : id_token
	};
	console.log('profile :' + JSON.stringify(user));
	login(user);
	document.getElementById("user").value = JSON.stringify(user);
	//set image url and remove s96-c in the url to allow resizing
	var imgurl = profile.getImageUrl();
	if (imgurl) {
		var start = imgurl.substring(0, imgurl.indexOf("s96-c"));

		if (start) {
			imgurl = start + imgurl.substring(imgurl.indexOf("s96-c") + 6);
		}
		document.getElementById("sign-up").innerHTML = "<div class=\"avatar-frame\" style=\"background-image:url("
				+ imgurl
				+ "?sz=40);\" data-reactid=\".0.$/.0.2.2.$=10:0\"></div>";
	}
};

function login(token){
	$.ajax({
		url : 'rest/login',
		type : 'POST',
		data: JSON.stringify(token),
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
		async : false,
		cache : false,
		processData : false,
		success : function() {
			alert('Form Submitted!');
		},
		error : function() {
			alert("error in ajax form submission");
		}
	});
};