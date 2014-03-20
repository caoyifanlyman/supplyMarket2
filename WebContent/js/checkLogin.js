function logout(){
	Ext.Ajax.request({
		url:'logout.action',
		success:function(response){
			var result = response.responseText;
			if(result=="failed"){
				alert("already logout");
			}else{
				window.location.href="login.html";
			}
		}
	});

}

function checkLogin2(privilege,target){
	var xmlHttpReq;
	sendRequest();
	xmlHttpReq.onreadystatechange=processRequest;
	xmlHttpReq.open("GET","checkLogin",false);
	function sendRequest(){
		xmlHttpReq=init();
		function init(){
			if (window.XMLHttpRequest) {
				return new XMLHttpRequest();
			} 
		else if (window.ActiveXObject) {
				return new ActiveXObject("Microsoft.XMLHTTP");
			}
		}
	};
	function processRequest(){
		if(xmlHttpReq.readyState==4){
			if(xmlHttpReq.status==200){
				alert(xmlHttpReq.responseText);
			}
		}
	};
}
function checkLogin(privilege,target){
	Ext.Ajax.request({
		url:'checkLogin',
		success:function(response){
			var result = response.responseText;
			if(result=="failed"){
				alert("not login");
				window.location.href="login.html";
			}else{
				if(result>privilege){
					alert("no privilege");
					window.location.href=target;
				}
			}
		}
	});
}