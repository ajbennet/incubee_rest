function onSignIn(googleUser) {
	var profile = googleUser.getBasicProfile();
	var id_token = googleUser.getAuthResponse().id_token;
	var token = {
		"name" : profile.getName(),
		"id" : profile.getId(),
		"image_url" : profile.getImageUrl(),
		"email" : profile.getEmail(),
		"token" : id_token
	};
	console.log('profile :' + JSON.stringify(token));
	login(token);
	document.getElementById("token").value = JSON.stringify(token);
	//set image url and remove s96-c in the url to allow resizing
	var imgurl = profile.getImageUrl();
	if (imgurl) {
		var start = imgurl.substring(0, imgurl.indexOf("s96-c"));

		if (start) {
			imgurl = start + imgurl.substring(imgurl.indexOf("s96-c") + 6);
		}
		document.getElementById("sign-up").innerHTML = "<a href=\"#signup\"><div class=\"avatar-frame\" style=\"background-image:url("
				+ imgurl
				+ "?sz=40);margin-left: auto;margin-right: auto;\"></div></a>";
	}
};

function login(token){
	$.ajax({
		url : 'rest/login',
		type : 'POST',
		data: JSON.stringify(token),
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
		async : true,
		cache : false,
		processData : false,
		success : function(data) {
			console.log("retrieved " + data);
			if(data && data.servicedata && data.servicedata.company_id){
				getCompany(data.servicedata.company_id);
			}
		},
		error : function(data) {
			console.error("error in login form submission" + data);
		}
	});
};

function signup(token){
	$.ajax({
		url : 'rest/signup',
		type : 'POST',
		data: JSON.stringify(token),
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
		async : true,
		cache : false,
		processData : false,
		success : function(data) {
			console.log("retrieved " + data);
			if(data && data.servicedata && data.servicedata.company_id){
				getCompany(data.servicedata.company_id);
			}else if(data && data.servicedata && data.servicedata.is_admin){
				console.log("logged in as admin user");
			}
		},
		error : function(data) {
			console.error("error in login form submission" + data);
		}
	});
};

function getCompany(id){
	$.ajax({
		url : 'rest/'+id,
		type : 'GET',
		async : true,
		cache : false,
		processData : false,
		success : function(data) {
			if(data){
				console.log("retrieved Company" + data.company_name);
				document.getElementById("company_name").value=data.company_name;
				document.getElementById("company_url").value=data.company_url;
				document.getElementById("field").value=data.field;
				document.getElementById("founder").value=data.founder;
				document.getElementById("logo_url").value=data.logo_url;
				document.getElementById("high_concept").value=data.high_concept;
				document.getElementById("description").value=data.description;
				if (data.funding)
					document.getElementById("funding").value="Yes";
				else
					document.getElementById("funding").value="No";
				document.getElementById("project_status").value=data.project_status;
				document.getElementById("location").value=data.location;
				document.getElementById("twitter_url").value=data.twitter_url;
				document.getElementById("video_url").value=data.video_url;
				document.getElementById("id").value=data.id;
			}
		},
		error : function(data) {
			console.error("error in ajax form submission" + data);
		}
	});
}