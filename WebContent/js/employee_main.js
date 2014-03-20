checkLogin(1,"employee_main.html");
Ext.require([
    'Ext.selection.CellModel',
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*',
    'Ext.form.*'
]);

Ext.onReady(function(){

    //function for gender mapping
    function gmap(v,rec){
    	if(v==0)
    		return "Male";
    	else return "Female";
    }
    function revert(gen){
    	if(gen=="Male")
    		return 0;
    	else return 1;
    }

    Ext.define('Employee', {
        extend: 'Ext.data.Model',
        fields: [
            // the 'name' below matches the tag name to read
            {name: 'employeeId', type: 'int'},
            {name: 'name', type: 'string'},
            {name: 'gender', convert:gmap},
            {name: 'email', type: 'string'}
        ],
        idProperty: 'employeeId'
    });


    // create the Data Store
    var store = Ext.create('Ext.data.Store', {
        // destroy the store if the grid is destroyed
        autoDestroy: true,
        model: 'Employee',
        proxy: {
            type: 'ajax',
            // load remote data using HTTP
            url: 'getAllEmployees',
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
    
    var gmap = Ext.create('Ext.data.Store',{
	    fields: ['abbr', 'name'],
	    data : [
	        {"abbr":0, "name":"Male"},
	        {"abbr":1, "name":"Female"}
	    ]
    });

    // create the grid and specify what field you want
    // to use for the editor at each header.
    var grid = Ext.create('Ext.grid.Panel', {
        store: store,
        columns: [{
            id: 'name',
            header: 'Employee Name',
            dataIndex: 'name',
            flex:2,
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Gender',
            dataIndex: 'gender',
            flex:1,
            editor: new Ext.form.field.ComboBox({
                typeAhead: true,
                triggerAction: 'all',
                selectOnTab: true,
                store: gmap,
                queryMode: 'local',
                valueField: 'abbr',
                lazyRender: true,
                listClass: 'x-combo-list-small',
                displayField: 'name'
            })
        }, {
            header: 'E-mail',
            dataIndex: 'email',
            flex:4,
            editor: {
                allowBlank: false,
                inputType:'email'
            }
        }],
        renderTo: 'ext-grid',
        width: 800,
        height: 600,
        title: 'Edit Employee',
        frame: true,
        tbar: [{
	            text: 'Add Employee',
	            handler : function(){
	            	if(rowEditing.editing&&e.record.data.customerId==0){
						store.remove(e.record);
					}
	                // Create a model instance
	                var r = Ext.create('Employee', {
	                	employeeId:0,
	                    name: 'New Employee',
	                    gender: 1,
	                    email: 'XX@mm.org'
	                });
	                store.insert(0,r);
	                rowEditing.startEdit(0,0);
	            }
            },
           	{
            text: 'Remove Employee',
            //this should be able to remove multiple records
            //now our selection can only select one record
            handler : function(){
            	rowEditing.cancelEdit();
                // Create a model instance
                var data = grid.getSelectionModel().getSelection();
                var id =data[0].get("employeeId");
				//alert(id);
				if(id==0){
                    	store.removeAt(data[0]);
            	}else{
            		Ext.Ajax.request({
            			url:'removeEmployee',
            			params:{
			    			'employee.employeeId':id
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
    	/*
        callback: function(){
            Ext.Msg.show({
                title: 'Store Load Callback',
                msg: 'store was loaded, data available for processing',
                modal: false,
                icon: Ext.Msg.INFO,
                buttons: Ext.Msg.OK
            });
        }*/
    });
    
    grid.on('edit', function(editor, e) {
    // commit the changes right after editing finished
    	//alert(e.record.data.employeeId);
    	var gen = revert(e.record.data.gender);
    	if(e.record.data.employeeId==0){
    		//alert("insert");    		
    		Ext.Ajax.request({
	    		url:'addEmployee',
	    		params:{
	    			'employee.name':e.record.data.name,
	    			'employee.employeeId':e.record.data.employeeId,
	    			'employee.gender':gen,
	    			'employee.email':e.record.data.email
	    		},
	    		success:function(response){
	    			//alert(response.responseText);
	    			if(response.responseText=="succeed"){
	    				e.record.commit();
	    				store.reload();
	    			}else
	    				alert("insert failed");
	    		}
	    	});
    		
    	}else{
	    	Ext.Ajax.request({
	    		url:'updateEmployee',
	    		params:{
	    			'employee.name':e.record.data.name,
	    			'employee.employeeId':e.record.data.employeeId,
	    			'employee.gender':gen,
	    			'employee.email':e.record.data.email
	    		},
	    		success:function(response){
	    			//alert(response.responseText);
	    			if(response.responseText=="succeed")
	    				e.record.commit();
	    			else
	    				alert("update failed");
	    		}
	    	});
    	}
	});
	
	grid.on('cancelEdit',function(editor,e){
		//alert(e.record.data.employeeId);
		//this is a newly created record
		if(e.record.data.employeeId==0){
			store.remove(e.record);
		}
	});

		
});