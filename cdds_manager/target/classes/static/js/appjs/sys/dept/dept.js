var prefix = "/sys/dept"
$(document).ready(function () {
    load();
});
var load = function () {
    $('#deptTable')
        .bootstrapTreeTable(
            {
                id: 'id',
                code: 'id',
                parentColumn: 'parentId',
                type: "GET", // 请求数据的ajax类型
                url: prefix + '/list', // 请求数据的ajax的url
                ajaxParams: {}, // 请求数据的ajax的data属性
                search: true, // 是否显示搜索框
                 expandColumn: '1',// 在哪一列上面显示展开按钮
                striped: true, // 是否各行渐变色
                bordered: true, // 是否显示边框
                expandAll: true, // 是否全部展开
                search: true, // 是否显示搜索框
                showColumns: true, // 是否显示内容下拉框（选择显示的列）
                // toolbar : '#exampleToolbar',
                columns: [
                    {
                        title: '编号',
                        field: 'id',
                        visible: true,
                        align: 'center',
                        valign: 'middle',
                        width: '50px'
                    },
                    {
                        title: '名称',
                        field: 'deptName'
                    },
                    {
                        title: '部门代码',
                        field: 'deptCode'
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (item, index) {
                            var e = '<a class="btn btn-primary btn-sm '
                                + s_edit_h
                                + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + item.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var p = '<a class="btn btn-primary btn-sm '
                                + s_add_h
                                + '" href="#" mce_href="#" title="添加下级" onclick="add(\''
                                + item.id
                                + '\')"><i class="fa fa-plus"></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm '
                                + s_remove_h
                                + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + item.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            return e + d + p;
                        }
                    }]
            });
}

function reLoad() {
    load();
}

function add(pId) {
    layer.open({
        type: 2,
        title: '增加菜单',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add/' + pId // iframe的url
    });
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (data) {
                var result = JSON.parse(data);
                if (result.resultCode == 1) {
                    layer.msg("删除成功");
                    reLoad();
                } else {
                    layer.msg(result.errorMsg);
                }
            }
        });
    })
}

function edit(id) {
    layer.open({
        type: 2,
        title: '菜单修改',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}

function batchRemove() {
    // var rows = $('#exampleTable').bootstrapTable('getSelections');

}