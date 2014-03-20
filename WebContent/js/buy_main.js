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
    
    Ext.define('InOrder', {
        extend: 'Ext.data.Model',
        fields: [
            // the 'name' below matches the tag name to read
            {name: 'inOrderId', type: 'int'},
            {name: 'customerId', type: 'int'},
            {name: 'inTotalPrice', type: 'float'},
            {name: 'inDiscountRate', type: 'float'},
            {name: 'inDiscountedPrice', type: 'float'},
            {name: 'inOrderDate', type: 'string'}
        ],
        idProperty: 'inOrderId'
    });


    // create the Data Store
    var store = Ext.create('Ext.data.Store', {
        // destroy the store if the grid is destroyed
        autoDestroy: true,
        model: 'InOrder',
        proxy: {
            type: 'ajax',
            // load remote data using HTTP
            url: 'getAllInOrders',
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
            dataIndex: 'inTotalPrice',
            flex:1
        }, {
            header: 'Discount Rate',
            dataIndex: 'inDiscountRate',
            flex:1
        }, {
            header: 'Discounted Price',
            dataIndex: 'inDiscountedPrice',
            flex:1
        },  {
            header: 'Order Date',
            dataIndex: 'inOrderDate',
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
	            	if(rowEditing.editing&&e.record.data.inOrderId==0){
						store.remove(e.record);
					}
	                // Create a model instance
	                var r = Ext.create('InOrder', {
	                	inOrderId:0,
	                    customerId:1,
	                    inTotalPrice:0,
	                    inDiscountRate:1,
	                    inDiscountedPrice:0
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
                var id =data[0].get("inOrderId");
				//alert(id);
				if(id==0){
                    	store.removeAt(data[0]);
            	}else{
            		Ext.Ajax.request({
            			url:'removeInOrder',
            			params:{
			    			'inorder.inOrderId':id
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
    	if(e.record.data.inOrderId==0){
    		//alert("insert");    		

    		Ext.Ajax.request({
	    		url:'addInOrder',
	    		params:{
	    			'inorder.customerId':e.record.data.customerId,
	    			'inorder.inTotalPrice':e.record.data.inTotalPrice,
	    			'inorder.inDiscountRate':e.record.data.inDiscountRate,
	    			'inorder.inDiscountedPrice':e.record.data.inDiscountedPrice
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
	    		url:'updateInOrder',
	    		params:{
	    			'inorder.inOrderId':e.record.data.inOrderId,
	    			'inorder.customerId':e.record.data.customerId,
	    			'inorder.inTotalPrice':e.record.data.inTotalPrice,
	    			'inorder.inDiscountRate':e.record.data.inDiscountRate,
	    			'inorder.inDiscountedPrice':e.record.data.inDiscountedPrice
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
		if(e.record.data.inOrderId==0){
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
	Ext.define('InDetails', {
        extend: 'Ext.data.Model',
        fields: [
            // the 'name' below matches the tag name to read
            {name: 'inDetailsId', type: 'int'},
            {name: 'ISBN', type: 'string'},
            {name: 'edition', type: 'int'},
            {name: 'impression', type: 'int'},
            {name: 'bookname', convert:bookconvert},
            {name: 'inOrderId', type: 'int'},
            {name: 'warehouseId', type: 'int'},
            {name: 'inCount', type: 'int'},
            {name: 'inItemPrice', type: 'float'}
        ],
        idProperty: 'inDetailsId'
    });
    //store for details
     var dstore = Ext.create('Ext.data.Store', {
        // destroy the store if the grid is destroyed
        autoDestroy: true,
        model: 'InDetails',
        proxy:{
    		type: 'ajax',
    		url: 'getAllInDetails',
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
            dataIndex: 'inCount',
            flex:2,
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Item Price',
            dataIndex: 'inItemPrice',
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
	            	if(drowEditing.editing&&e.record.data.inDetailsId==0){
						store.remove(e.record);
					}
	                // Create a model instance
	                var r = Ext.create('InDetails', {
	                	inDetailsId:0,
	                    ISBN:'new ISBN',
	                    edition:1,
	                    impression:1,
	                    inOrderId:grid.getSelectionModel().getSelection()[0].get("inOrderId"),
	                    warehouseId:1,
	                    inCount:100,
	                    inItemPrice:10
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
                var id =data[0].get("inDetailsId");
				//alert(id);
				if(id==0){
                    	dstore.removeAt(data[0]);
            	}else{
            		Ext.Ajax.request({
            			url:'removeInDetails',
            			params:{
			    			'indetails.inDetailsId':id
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
    	if(e.record.data.inDetailsId==0){
    		//alert("insert");    		
    		Ext.Ajax.request({
	    		url:'addInDetails',
	    		params:{
	    			'indetails.ISBN':e.record.data.ISBN,
	    			'indetails.edition':e.record.data.edition,
	    			'indetails.impression':e.record.data.impression,
	    			'indetails.inOrderId':e.record.data.inOrderId,
	    			'indetails.warehouseId':e.record.data.warehouseId,
	    			'indetails.inCount':e.record.data.inCount,
	    			'indetails.inItemPrice':e.record.data.inItemPrice
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
	    		url:'updateInDetails',
	    		params:{
	    			'indetails.inDetailsId':e.record.data.inDetailsId,
	    			'indetails.ISBN':e.record.data.ISBN,
	    			'indetails.edition':e.record.data.edition,
	    			'indetails.impression':e.record.data.impression,
	    			'indetails.inOrderId':e.record.data.inOrderId,
	    			'indetails.warehouseId':e.record.data.warehouseId,
	    			'indetails.inCount':e.record.data.inCount,
	    			'indetails.inItemPrice':e.record.data.inItemPrice
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
		if(e.record.data.inDetailsId==0){
			dstore.remove(e.record);
		}
	});
    
    grid.on('select',function(item,record,index){
    	dstore.load({
    		params:{
    			'inorder.inOrderId':record.get("inOrderId")
    		}
    	});
    });
		
});