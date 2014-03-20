Ext.require([
    'Ext.selection.CellModel',
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*',
    'Ext.form.*'
]);

function login(){
	
	Ext.Ajax.request({
		url:'login',
		params:{
			'userinfo.username':document.getElementById("username").value,
			'userinfo.password':document.getElementById("password").value
		},
		success:function(response){
			var result = response.responseText;
			if(result=="succeed"){
				window.location.href="stock_main.html";
				document.getElementById("error_msg").style.display="none";
			}else{
				document.getElementById("error_msg").style.display="block";
			}
		}
	});
}
