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

    Ext.define('Inventory', {
        extend: 'Ext.data.Model',
        fields: [
            // the 'name' below matches the tag name to read
            {name: 'inventoryId', type: 'int'},
            {name: 'ISBN', type: 'string'},
            {name: 'edition', type: 'int'},
            {name: 'impression', type: 'int'},
            {name: 'warehouseId', type: 'int'},
            {name: 'currentCount', type: 'int'},
            {name: 'countDate', type: 'Date'}
        ],
        idProperty: 'inventoryId'
    });


    // create the Data Store
    var store = Ext.create('Ext.data.Store', {
        // destroy the store if the grid is destroyed
        autoDestroy: true,
        model: 'Inventory',
        proxy: {
            type: 'ajax',
            // load remote data using HTTP
            url: 'getAllInventories',
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
            header: 'ISBN',
            dataIndex: 'ISBN',
            flex:2,
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Edition',
            dataIndex: 'edition',
            flex:1,
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Impression',
            dataIndex: 'impression',
            flex:1,
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Warehouse ID',
            dataIndex: 'warehouseId',
            flex:1,
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Current Count',
            dataIndex: 'currentCount',
            flex:2,
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Count Date',
            dataIndex: 'countDate',
            flex:3
        }],
        renderTo: 'ext-grid',
        width: 800,
        height: 300,
        title: 'Edit Inventory',
        frame: true,
        tbar: [{
	            text: 'Add Inventory',
	            handler : function(){
	            	if(rowEditing.editing&&e.record.data.inventoryId==0){
						store.remove(e.record);
					}
	                // Create a model instance
	                var r = Ext.create('Inventory', {
	                	inventoryId:0,
	                    ISBN:'new ISBN',
	                    edition:1,
	                    impression:1,
	                    warehouseId:1,
	                    currentCount:100
	                });
	                store.insert(0,r);
	                rowEditing.startEdit(0,0);
	            }
            },
           	{
            text: 'Remove Inventroy',
            //this should be able to remove multiple records
            //now our selection can only select one record
            handler : function(){
            	rowEditing.cancelEdit();
                // Create a model instance
                var data = grid.getSelectionModel().getSelection();
                var id =data[0].get("inventoryId");
				//alert(id);
				if(id==0){
                    	store.removeAt(data[0]);
            	}else{
            		Ext.Ajax.request({
            			url:'removeInventory',
            			params:{
			    			'inventory.inventoryId':id
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

	var pgrid = Ext.create('Ext.grid.Panel', {
        store: pstore,
        columns: [{
            id: 'pname',
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
            flex:1
        }, {
            header: 'Author',
            dataIndex: 'author',
            flex:1
        }, {
            header: 'Publish Date',
            dataIndex: 'publishDate',
            flex:2
        }, {
            header: 'Impression Date',
            dataIndex: 'impressionDate',
            flex:2
        }, {
            header: 'Press',
            dataIndex: 'press',
            flex:1
        }],
        renderTo: 'ext-product-grid',
        width: 800,
        height: 150,
        title: 'View Products',
        frame: true
    });
    
    Ext.define('Warehouse', {
        extend: 'Ext.data.Model',
        fields: [
            // the 'name' below matches the tag name to read
            {name: 'warehouseId', type: 'int'},
            {name: 'warehouseName', type: 'string'},
            {name: 'warehouseLocation', type: 'string'},
            {name: 'warehouseStatus', type: 'int'}
        ],
        idProperty: 'warehouseId'
    });
    
    // create the Data Store
    var wstore = Ext.create('Ext.data.Store', {
        // destroy the store if the grid is destroyed
        autoDestroy: true,
        model: 'Warehouse',
        proxy: {
            type: 'ajax',
            // load remote data using HTTP
            url: 'getAllWarehouse',
            // specify a JSON reader
            reader: {
                type: 'json',
                // the record tree has a root named 'list'
                root: 'root'
            }
        }
    });

	var wgrid = Ext.create('Ext.grid.Panel', {
        store: wstore,
        columns: [{
            id: 'wname',
            header: 'Warehouse ID',
            dataIndex: 'warehouseId',
            flex:2
        }, {
            header: 'Warehouse Name',
            dataIndex: 'warehouseName',
            flex:1
        }, {
            header: 'Warehouse Location',
            dataIndex: 'warehouseLocation',
            flex:1
        }, {
            header: 'Warehouse Status',
            dataIndex: 'warehouseStatus',
            flex:2
        }],
        renderTo: 'ext-warehouse-grid',
        width: 800,
        height: 150,
        title: 'View Warehouses',
        frame: true
    });
    
    // manually trigger the data store load
    store.load({
        // store loading is asynchronous, use a load listener or callback to handle results

        callback: function(){
            pstore.load();
            wstore.load();
        }
    });
    
    
    
    grid.on('edit', function(editor, e) {
    // commit the changes right after editing finished
    	//alert(e.record.data.employeeId);
    	if(e.record.data.inventoryId==0){
    		//alert("insert");    		
    		Ext.Ajax.request({
	    		url:'addInventory',
	    		params:{
	    			'inventory.ISBN':e.record.data.ISBN,
	    			'inventory.edition':e.record.data.edition,
	    			'inventory.impression':e.record.data.impression,
	    			'inventory.warehouseId':e.record.data.warehouseId,
	    			'inventory.currentCount':e.record.data.currentCount
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
	    		url:'updateInventory',
	    		params:{
	    			'inventory.inventoryId':e.record.data.inventoryId,
	    			'inventory.ISBN':e.record.data.ISBN,
	    			'inventory.edition':e.record.data.edition,
	    			'inventory.impression':e.record.data.impression,
	    			'inventory.warehouseId':e.record.data.warehouseId,
	    			'inventory.currentCount':e.record.data.currentCount
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
		if(e.record.data.inventoryId==0){
			store.remove(e.record);
		}
	});
	
	grid.on('select',function(item,record,index){
		pstore.filterBy(filtp);
		function filtp(r,id){
			if(r.get("ISBN")==record.get("ISBN")&&r.get("edition")==record.get("edition")&&r.get("impression")==record.get("impression")){
				return true;
			}
		}	
		wstore.filterBy(filtw);
		function filtw(r,id){
			if(r.get("warehouseId")==record.get("warehouseId")){
				return true;
			}
		}
    });

});