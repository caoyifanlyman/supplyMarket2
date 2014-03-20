checkLogin(1,"customer_main.html");
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

    Ext.define('Customer', {
        extend: 'Ext.data.Model',
        fields: [
            // the 'name' below matches the tag name to read
            {name: 'customerId', type: 'int'},
            {name: 'employeeId', type: 'int'},
            {name: 'customerName', type: 'string'},
            {name: 'customerGender', convert:gmap},
            {name: 'customerEmail', type: 'string'},
            {name: 'customerPhone', type: 'string'},
            {name: 'customerAddress', type: 'string'}
        ],
        idProperty: 'customerId'
    });


    // create the Data Store
    var store = Ext.create('Ext.data.Store', {
        // destroy the store if the grid is destroyed
        autoDestroy: true,
        model: 'Customer',
        proxy: {
            type: 'ajax',
            // load remote data using HTTP
            url: 'getAllCustomers',
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
            header: 'Employee Id',
            dataIndex: 'employeeId',
            flex:1,
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Customer Name',
            dataIndex: 'customerName',
            flex:1,
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Customer Gender',
            dataIndex: 'customerGender',
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
            header: 'Customer E-mail',
            dataIndex: 'customerEmail',
            felx:3,
            align: 'right', 
            editor: {
                allowBlank: false,
                inputType:'email'
            }
        }, {
            header: 'Customer Phone',
            dataIndex: 'customerPhone',
            felx:2,
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Customer Address',
            dataIndex: 'customerAddress',
            flex:3,
            editor: {
                allowBlank: false
            }
        }],
        renderTo: 'ext-grid',
        width: 800,
        height: 600,
        title: 'Edit Customer',
        frame: true,
        tbar: [{
	            text: 'Add Customer',
	            handler : function(){
	            	if(rowEditing.editing&&e.record.data.customerId==0){
						store.remove(e.record);
					}
	                // Create a model instance
	                var r = Ext.create('Customer', {
	                	customerId:0,
	                	employeeId:1,
	                    customerName: 'New Customer',
	                    customerGender: 1,
	                    customerEmail: 'XX@mm.org',
	                    customerPhone: '15679316351',
	                    customerAddress: 'PP Rd. YUI St.'
	                });
	                store.insert(0,r);
	                rowEditing.startEdit(0,0);
	            }
            },
           	{
            text: 'Remove Customer',
            //this should be able to remove multiple records
            //now our selection can only select one record
            handler : function(){
            	rowEditing.cancelEdit();
                // Create a model instance
                var data = grid.getSelectionModel().getSelection();
                var id =data[0].get("customerId");
				//alert(id);
				if(id==0){
                    	store.removeAt(data[0]);
            	}else{
            		Ext.Ajax.request({
            			url:'removeCustomer',
            			params:{
			    			'customer.customerId':id
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
    	if(e.record.data.customerId==0){
    		//alert("insert");    		
    		Ext.Ajax.request({
	    		url:'addCustomer',
	    		params:{
	    			'customer.employeeId':e.record.data.employeeId,
	    			'customer.customerId':e.record.data.customerId,
	    			'customer.customerName':e.record.data.customerName,
	    			'customer.customerGender':gen,
	    			'customer.customerEmail':e.record.data.customerEmail,
	    			'customer.customerPhone':e.record.data.customerPhone,
	    			'customer.customerAddress':e.record.data.customerAddress
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
	    		url:'updateCustomer',
	    		params:{
	    			'customer.employeeId':e.record.data.employeeId,
	    			'customer.customerId':e.record.data.customerId,
	    			'customer.customerName':e.record.data.customerName,
	    			'customer.customerGender':gen,
	    			'customer.customerEmail':e.record.data.customerEmail,
	    			'customer.customerPhone':e.record.data.customerPhone,
	    			'customer.customerAddress':e.record.data.customerAddress
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
		if(e.record.data.customerId==0){
			store.remove(e.record);
		}
	});

		
});