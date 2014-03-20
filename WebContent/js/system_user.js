checkLogin(0,"employee_main.html");
Ext.require([
    'Ext.selection.CellModel',
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*',
    'Ext.form.*'
]);

Ext.onReady(function(){

    Ext.define('UserInfo', {
        extend: 'Ext.data.Model',
        fields: [
            // the 'name' below matches the tag name to read
            {name: 'username', type: 'string'},
            {name: 'employeeId', type: 'int'},
            {name: 'password', type: 'string'},
            {name: 'privilege', type: 'int'},
            {name: 'exist', convert:function(v,rec){
            	if(rec.data.username=='new user') return 0;
            	else return 1;
            }}
        ],
        idProperty: 'username'
    });


    // create the Data Store
    var store = Ext.create('Ext.data.Store', {
        // destroy the store if the grid is destroyed
        autoDestroy: true,
        model: 'UserInfo',
        proxy: {
            type: 'ajax',
            // load remote data using HTTP
            url: 'getAllUsers',
            // specify a JSON reader
            reader: {
                type: 'json',
                // the record tree has a root named 'list'
                root: 'root'
            }
        }
    });

    var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
        clicksToEdit: 2
    });
    

    // create the grid and specify what field you want
    // to use for the editor at each header.
    var grid = Ext.create('Ext.grid.Panel', {
        store: store,
        columns: [{
            id: 'name',
            header: 'User Name',
            dataIndex: 'username',
            flex:2,
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Employee Id',
            dataIndex: 'employeeId',
            flex:2,
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Password',
            dataIndex: 'password',
            flex:2,
            editor: {
                allowBlank: false,
                inputType:'password'
            },
            hidden:true
        }, {
            header: 'Privilege',
            dataIndex: 'privilege',
            flex:2,
            editor: {
                allowBlank: false
            }
        }],
        renderTo: 'ext-grid',
        width: 800,
        height: 600,
        title: 'Edit User',
        frame: true,
        tbar: [{
	            text: 'Add User',
	            handler : function(){
	            	if(rowEditing.editing&&e.record.data.exist==0){
						store.remove(e.record);
					}
	                // Create a model instance
	                var r = Ext.create('UserInfo', {
	                	username:'new user',
	                    employeeId: 1,
	                    password: 'password',
	                    privilege:1
	                });
	                store.insert(0,r);
	                rowEditing.startEdit(0,0);
	            }
            },
           	{
            text: 'Remove User',
            //this should be able to remove multiple records
            //now our selection can only select one record
            handler : function(){
            	rowEditing.cancelEdit();
                // Create a model instance
                var data = grid.getSelectionModel().getSelection();
                var id =data[0].get("username");
				//alert(id);
                //this should never happen
				if(data[0].dirty){
                    	store.removeAt(data[0]);
            	}else{
            		Ext.Ajax.request({
            			url:'removeUser',
            			params:{
			    			'userinfo.username':id
            			},
            			success:function(response){
			    			//alert(response.responseText);
			    			if(response.responseText=="succeed"){
			    				store.remove(data[0]);
			    			}else
			    				alert("remove failed");
			    		}
            		});
            	}
            }
           }],
        plugins: [rowEditing]
    });

    // manually trigger the data store load
    store.load({
        // store loading is asynchronous, use a load listener or callback to handle results
    });
    
    grid.on('edit', function(editor, e) {
    // commit the changes right after editing finished
    	//alert(e.record.data.employeeId);
    	if(e.record.data.exist==0){
    		//alert("insert");    		
    		Ext.Ajax.request({
	    		url:'addUser',
	    		params:{
	    			'userinfo.username':e.record.data.username,
	    			'userinfo.employeeId':e.record.data.employeeId,
	    			'userinfo.password':e.record.data.password,
	    			'userinfo.privilege':e.record.data.privilege
	    		},
	    		success:function(response){
	    			//alert(response.responseText);
	    			if(response.responseText=="succeed"){
	    				e.record.data.exist = 1;
	    				e.record.commit();
	    				store.reload();
	    			}else
	    				alert("insert failed");
	    		}
	    	});
    		
    	}else{
	    	rowEditing.cancelEdit();
    	}
	});
	
	grid.on('cancelEdit',function(editor,e){
		//alert(e.record.data.employeeId);
		//this is a newly created record
		if(e.record.data.exist==0){
			store.remove(e.record);
		}
	});

		
});