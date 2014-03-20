Ext.require([
    'Ext.selection.CellModel',
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*',
    'Ext.form.*'
]);

Ext.onReady(function(){

    function formatDate(value){
        return value ? Ext.Date.dateFormat(value, 'M d, Y') : '';
    }

    Ext.define('Employee', {
        extend: 'Ext.data.Model',
        fields: [
            // the 'name' below matches the tag name to read
            {name: 'employeeId', type: 'string'},
            {name: 'name', type: 'string'},
            {name: 'gender', type: 'int'},
            {name: 'email', type: 'string'}
        ]
    });


    // create the Data Store
 /*   var store = Ext.create('Ext.data.Store', {
        // destroy the store if the grid is destroyed
        autoDestroy: true,
        model: 'Employee',
        proxy: {
            type: 'ajax',
            // load remote data using HTTP
            url: 'sampleFetch',
            // specify a JSON reader
            reader: {
                type: 'json',
                // the record tree has a root named 'list'
                root: 'root'
            }
        }
    });
    */

    var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
        clicksToEdit: 1
    });

    // create the grid and specify what field you want
    // to use for the editor at each header.
    var grid = Ext.create('Ext.grid.Panel', {
        store: store,
        columns: [{
            id: 'name',
            header: 'Employee Name',
            dataIndex: 'name',
            editor: {
                allowBlank: false
            }
        }, {
            header: 'Gender',
            dataIndex: 'gender',
            editor: new Ext.form.field.ComboBox({
                typeAhead: true,
                triggerAction: 'all',
                selectOnTab: true,
                store: [
                    [1,'Female'],
                    [2,'Unknown'],
                    [3,'Male']
                ],
                lazyRender: true,
                listClass: 'x-combo-list-small'
            })
        }, {
            header: 'E-mail',
            dataIndex: 'email',
            align: 'right', 
            editor: {
                allowBlank: false
            }
        }, {
        	header: 'Action',
            xtype: 'actioncolumn',
            width:30,
            sortable: false,
            items: [{
                icon: 'img/delete.gif',
                tooltip: 'Delete Employee',
                handler: function(grid, rowIndex, colIndex) {
                    store.removeAt(rowIndex); 
                }
            }]
        }],
        selModel: {
            selType: 'cellmodel'
        },
        renderTo: 'editor-grid',
        width: 600,
        height: 300,
        title: 'Edit Employee',
        frame: true,
        tbar: [{
            text: 'Add Employee',
            handler : function(){
                // Create a model instance
                var r = Ext.create('Employee', {
                    name: 'New Employee',
                    gender: 1,
                    email: 'XX@mm.org'
                });
                store.insert(0, r);
                cellEditing.startEditByPosition({row: 0, column: 0});
            }
        }],
        plugins: [cellEditing]
    });

    // manually trigger the data store load
    store.load({
        // store loading is asynchronous, use a load listener or callback to handle results
        callback: function(){
            Ext.Msg.show({
                title: 'Store Load Callback',
                msg: 'store was loaded, data available for processing',
                modal: false,
                icon: Ext.Msg.INFO,
                buttons: Ext.Msg.OK
            });
        }
    });
});