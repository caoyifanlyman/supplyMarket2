checkLogin(1,"system.html");
function changePwd(){
	if(document.getElementById("newpwd").value!=document.getElementById("newpwd2").value){
		alert("the new password is different from the confirm password");
		return;
	}
	//check pwd
	Ext.Ajax.request({
		url:'changePwd',
		params:{
			'userinfo.password':document.getElementById("oldpwd").value,
			'password':document.getElementById("newpwd").value
		},
		success:function(response){
			var result = response.responseText;
			if(result=="succeed"){
				alert("change succeed")
			}else{
				alert("change failed");
			}
		}
	});
}