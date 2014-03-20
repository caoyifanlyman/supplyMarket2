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
	Ext.define('Product', {
        extend: 'Ext.data.Model',
        fields: [
            // the 'name' below matches the tag name to read
            {name: 'ISBN', type: 'string'},
            {name: 'edition', type: 'int'},
            {name: 'impression', type: 'int'},
            {name: 'bookname', type: 'string'},
            {name: 'author', type: 'String'},
            {name: 'publishDate', type: 'Date'},
            {name: 'impressionDate', type: 'Date'},
            {name: 'press', type: 'string'}
        ],
        idProperty: 'ISBN'
    });
    
    // create the Data Store
    var pstore = Ext.create('Ext.data.Store', {
        // destroy the store if the grid is destroyed
        autoDestroy: true,
        model: 'Product',
        proxy: {
            type: 'ajax',
            // load remote data using HTTP
            url: 'getAllProduct',
            // specify a JSON reader
            reader: {
                type: 'json',
                // the record tree has a root named 'list'
                root: 'root'
            }
        }
    });
    
    Ext.define('OutOrder', {
        extend: 'Ext.data.Model',
        fields: [
            // the 'name' below matches the tag name to read
            {name: 'outOrderId', type: 'int'},
            {name: 'customerId', type: 'int'},
            {name: 'outTotalPrice', type: 'float'},
            {name: 'outDiscountRate', type: 'float'},
            {name: 'outDiscountedPrice', type: 'float'},
            {name: 'outOrderDate', type: 'string'}
        ],
        idProperty: 'outOrderId'
    });


    // create the Data Store
    var store = Ext.create('Ext.data.Store', {
        // destroy the store if the grid is destroyed
        autoDestroy: true,
        model: 'OutOrder',
        proxy: {
            type: 'ajax',
            // load remote data using HTTP
            url: 'getAllOutOrders',
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
            id: 'dname',
            header: 'Customer Id',
            dataIndex: 'customerId',
            flex:1,
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Total Price',
            dataIndex: 'outTotalPrice',
            flex:1
        }, {
            header: 'Discount Rate',
            dataIndex: 'outDiscountRate',
            flex:1
        }, {
            header: 'Discounted Price',
            dataIndex: 'outDiscountedPrice',
            flex:1
        },  {
            header: 'Order Date',
            dataIndex: 'outOrderDate',
            flex:3
        }],
        renderTo: 'ext-grid',
        width: 800,
        height: 300,
        title: 'Edit Order',
        frame: true,
        tbar: [{
	            text: 'Add Order',
	            handler : function(){
	            	if(rowEditing.editing&&e.record.data.outOrderId==0){
						store.remove(e.record);
					}
	                // Create a model instance
	                var r = Ext.create('OutOrder', {
	                	outOrderId:0,
	                    customerId:1,
	                    outTotalPrice:0,
	                    outDiscountRate:1,
	                    outDiscountedPrice:0
	                });
	                store.insert(0,r);
	                rowEditing.startEdit(0,0);
	            }
            },
           	{
            text: 'Remove Order',
            //this should be able to remove multiple records
            //now our selection can only select one record
            handler : function(){
            	rowEditing.cancelEdit();
                // Create a model instance
                var data = grid.getSelectionModel().getSelection();
                var id =data[0].get("outOrderId");
				//alert(id);
				if(id==0){
                    	store.removeAt(data[0]);
            	}else{
            		Ext.Ajax.request({
            			url:'removeOutOrder',
            			params:{
			    			'outorder.outOrderId':id
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
           }, {
           	text: 'Details',
           	handler : function(){
                // Create a model instance
                //alert("clicked");
            }
           }],
        plugins: [rowEditing]
    });
	
    pstore.load({
    	callback: function(){
            store.load();
        }
    });

    
    grid.on('edit', function(editor, e) {
    // commit the changes right after editing finished
    	//alert(e.record.data.employeeId);
    	if(e.record.data.outOrderId==0){
    		//alert("insert");    		

    		Ext.Ajax.request({
	    		url:'addOutOrder',
	    		params:{
	    			'outorder.customerId':e.record.data.customerId,
	    			'outorder.outTotalPrice':e.record.data.outTotalPrice,
	    			'outorder.outDiscountRate':e.record.data.outDiscountRate,
	    			'outorder.outDiscountedPrice':e.record.data.outDiscountedPrice
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
	    		url:'updateOutOrder',
	    		params:{
	    			'outorder.outOrderId':e.record.data.outOrderId,
	    			'outorder.customerId':e.record.data.customerId,
	    			'outorder.outTotalPrice':e.record.data.outTotalPrice,
	    			'outorder.outDiscountRate':e.record.data.outDiscountRate,
	    			'outorder.outDiscountedPrice':e.record.data.outDiscountedPrice
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
		if(e.record.data.outOrderId==0){
			store.remove(e.record);
		}
	});
	
	function bookconvert(value,record){
		var index = pstore.findBy(find);
		function find(r,id){
			if(r.get("ISBN")==record.get("ISBN")&&r.get("edition")==record.get("edition")&&r.get("impression")==record.get("impression")){
				return true;
			}
		}
		if(index==-1)
			return "bookname";
		return pstore.getAt(index).get("bookname");
	}
	
	//the edit grid
	Ext.define('OutDetails', {
        extend: 'Ext.data.Model',
        fields: [
            // the 'name' below matches the tag name to read
            {name: 'outDetailsId', type: 'int'},
            {name: 'ISBN', type: 'string'},
            {name: 'edition', type: 'int'},
            {name: 'impression', type: 'int'},
            {name: 'bookname', convert:bookconvert},
            {name: 'outOrderId', type: 'int'},
            {name: 'warehouseId', type: 'int'},
            {name: 'outCount', type: 'int'},
            {name: 'outItemPrice', type: 'float'}
        ],
        idProperty: 'outDetailsId'
    });
    //store for details
     var dstore = Ext.create('Ext.data.Store', {
        // destroy the store if the grid is destroyed
        autoDestroy: true,
        model: 'OutDetails',
        proxy:{
    		type: 'ajax',
    		url: 'getAllOutDetails',
            // specify a JSON reader
            reader: {
                type: 'json',
                // the record tree has a root named 'list'
                root: 'root'
            }
    	}
    });
    
    var drowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
        clicksToEdit: 2
    });
    
    var bookcombo = new Ext.form.field.ComboBox({
                typeAhead: true,
                triggerAction: 'all',
                selectOnTab: true,
                store: pstore,
                queryMode: 'local',
                valueField: 'bookname',
                displayField: 'bookname'
            });
            
    bookcombo.on('select',function(combo,records){
    	dgrid.getSelectionModel().getSelection()[0].set("ISBN",records[0].data.ISBN);
    	dgrid.getSelectionModel().getSelection()[0].set("edition",records[0].data.edition);
    	dgrid.getSelectionModel().getSelection()[0].set("impression",records[0].data.impression);
    	
    });
    
    var dgrid = Ext.create('Ext.grid.Panel', {
        store: dstore,
        columns: [{
            id: 'name',
            header: 'ISBN',
            dataIndex: 'ISBN',
            flex:2
        }, {
            header: 'Edition',
            dataIndex: 'edition',
            flex:1
        }, {
            header: 'Impression',
            dataIndex: 'impression',
            flex:1
        }, {
            header: 'Book Name',
            dataIndex: 'bookname',
            flex:1,
            editor:bookcombo
        },{
            header: 'Warehouse ID',
            dataIndex: 'warehouseId',
            flex:1,
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Count',
            dataIndex: 'outCount',
            flex:2,
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Item Price',
            dataIndex: 'outItemPrice',
            flex:2,
            editor: {
                allowBlank: false
            }
        }],
        renderTo: 'ext-edit-grid',
        width: 800,
        height: 300,
        title: 'Edit Detail',
        frame: true,
        tbar: [{
	            text: 'Add Detail',
	            handler : function(){
	            	if(drowEditing.editing&&e.record.data.outDetailsId==0){
						store.remove(e.record);
					}
	                // Create a model instance
	                var r = Ext.create('OutDetails', {
	                	outDetailsId:0,
	                    ISBN:'new ISBN',
	                    edition:1,
	                    impression:1,
	                    outOrderId:grid.getSelectionModel().getSelection()[0].get("outOrderId"),
	                    warehouseId:1,
	                    outCount:100,
	                    outItemPrice:10
	                });
	                dstore.insert(0,r);
	                drowEditing.startEdit(0,0);
	            }
            },
           	{
            text: 'Remove Detail',
            //this should be able to remove multiple records
            //now our selection can only select one record
            handler : function(){
            	drowEditing.cancelEdit();
                // Create a model instance
                var data = dgrid.getSelectionModel().getSelection();
                var id =data[0].get("outDetailsId");
				//alert(id);
				if(id==0){
                    	dstore.removeAt(data[0]);
            	}else{
            		Ext.Ajax.request({
            			url:'removeOutDetails',
            			params:{
			    			'outdetails.outDetailsId':id
            			},
            			success:function(response){
			    			//alert(response.responseText);
			    			if(response.responseText=="succeed"){
			    				dstore.remove(data[0]);
			    				store.reload();
			    			}else
			    				alert("remove failed");
			    		}
            		});
            	}
            }
           }],
        plugins: [drowEditing]
    });
    
    dgrid.on('edit', function(editor, e) {
    // commit the changes right after editing finished
    	//alert(e.record.data.employeeId);
    	if(e.record.data.outDetailsId==0){
    		//alert("insert");    		
    		Ext.Ajax.request({
	    		url:'addOutDetails',
	    		params:{
	    			'outdetails.ISBN':e.record.data.ISBN,
	    			'outdetails.edition':e.record.data.edition,
	    			'outdetails.impression':e.record.data.impression,
	    			'outdetails.outOrderId':e.record.data.outOrderId,
	    			'outdetails.warehouseId':e.record.data.warehouseId,
	    			'outdetails.outCount':e.record.data.outCount,
	    			'outdetails.outItemPrice':e.record.data.outItemPrice
	    		},
	    		success:function(response){
	    			//alert(response.responseText);
	    			if(response.responseText=="succeed"){
	    				e.record.commit();
	    				dstore.reload();
	    				store.reload();
	    			}else
	    				alert("insert failed");
	    		}
	    	});
    		
    	}else{
	    	Ext.Ajax.request({
	    		url:'updateOutDetails',
	    		params:{
	    			'outdetails.outDetailsId':e.record.data.outDetailsId,
	    			'outdetails.ISBN':e.record.data.ISBN,
	    			'outdetails.edition':e.record.data.edition,
	    			'outdetails.impression':e.record.data.impression,
	    			'outdetails.outOrderId':e.record.data.outOrderId,
	    			'outdetails.warehouseId':e.record.data.warehouseId,
	    			'outdetails.outCount':e.record.data.outCount,
	    			'outdetails.outItemPrice':e.record.data.outItemPrice
	    		},
	    		success:function(response){
	    			//alert(response.responseText);
	    			if(response.responseText=="succeed"){
	    				e.record.commit();
	    				store.reload();
	    			}else
	    				alert("update failed");
	    		}
	    	});
    	}
	});
	
    dgrid.on('cancelEdit',function(editor,e){
		//alert(e.record.data.employeeId);
		//this is a newly created record
		if(e.record.data.outDetailsId==0){
			dstore.remove(e.record);
		}
	});
    
    grid.on('select',function(item,record,index){
    	dstore.load({
    		params:{
    			'outorder.outOrderId':record.get("outOrderId")
    		}
    	});
    });
		
});