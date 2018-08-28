var prefix = "/sys/user/";
var prefixDept = "/sys/dept/"
var zTreeObj;

$(function () {
    var deptId = '';
    getTreeData();
    load(deptId);
});

function load(deptId) {
    // console.log("数据加载");
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/list", // 服务器数据的加载地址
                // showRefresh : true,
                // showToggle : true,
                // showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect: false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                // search : true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server ", // 设置在哪里进行分页，可选值为"client" 或者
                // "server"
                responseHandler: function (res) {
                    return res.result;
                },
                queryParams: function (params) {
                    return {
                        // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset: params.offset,
                        nickName: $('#searchName').val(),
                        deptId: deptId
                    };
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns: [
                    {
                        checkbox: true
                    },
                    {
                        align: "left",
                        title: '序号', // 列标题
                        formatter: function (value, row, index) {
                            return index + 1;
                        }
                    },
                    {
                        align: "left",
                        field: 'nickName',
                        title: '姓名'
                    },
                    {
                        align: "left",
                        field: 'username',
                        title: '用户名'
                    },
                    {
                        align: "left",
                        field: 'email',
                        title: '邮箱'
                    },
                    {
                        field: 'status',
                        title: '状态',
                        align: 'center',
                        formatter: function (value, row, index) {
                            if (value == '0') {
                                return '<span class="label label-danger">禁用</span>';
                            } else if (value == '1') {
                                return '<span class="label label-primary">正常</span>';
                            }
                        }
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var e = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit "></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            var f = '<a class="btn btn-success btn-sm ' + s_resetPwd_h + '" href="#" title="重置密码"  mce_href="#" onclick="editPwd(\''
                                + row.id
                                + '\')"><i class="fa fa-key"></i></a> ';
                            return e + d + f;
                        }
                    }]
            });
}

function reLoad() {
    var opt = {
        silent: true,
        query: {
            deptId: $('#deptId').val(),
            nickName: $('#searchName').val()
        }
    };
    $('#exampleTable').bootstrapTable('refresh', opt);
}

function add() {
    var deptId = $('#deptId').val();
    if (deptId == null || deptId.length == 0) {
        layer.msg("请选择部门");

        return;
    }
    // iframe层
    layer.open({
        type: 2,
        title: '增加用户',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add/' + deptId
    });
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "batchRemove",
            type: "post",
            data: {
                'ids': id
            },
            success: function (data) {
                var result = JSON.parse(data);
                if (result.resultCode == 1) {
                    layer.msg("操作成功");
                    reLoad();
                } else {
                    layer.msg(data.errorMsg);
                }
            }
        });
    })
}

function edit(id) {
    layer.open({
        type: 2,
        title: '用户修改',
        maxmin: true,
        shadeClose: true, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}

function editPwd(id) {
    layer.open({
        type: 2,
        title: '重置密码',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['450px', '360px'],
        content: prefix + '/editPwd/' + id // iframe的url
    });
}

function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['id'];

        });

        $.ajax({
            type: 'POST',
            dataType: "json",
            data: {'ids': ids + ''},
            url: prefix + '/batchRemove',
            success: function (data) {

                if (data.resultCode >= 1) {
                    layer.msg("删除成功");
                    reLoad();
                } else {
                    layer.msg(data.errorMsg);
                }
            }
        });
    }, function () {
    });
}

function getTreeData() {
    $.ajax({
        type: "GET",
        url: prefixDept + "tree",
        dataType: 'json',
        success: function (tree) {
            if (tree.resultCode == 1) {
                loadTree(tree.result);
            } else {
                layer.msg(tree.errorMsg);
            }
        }
    });
}

function loadTree(zTreeNodes) {
    var setting = {
        callback: {
            beforeExpand: zTreeBeforeExpand,
            beforeClick: zTreeBeforeClick
        }
    };
    zTreeObj = $.fn.zTree.init($("#ztree"), setting, zTreeNodes);
}

function zTreeBeforeClick(treeId, treeNode, clickFlag) {
    console.log(treeNode.id);
    $('#deptId').val(treeNode.id);
    reLoad(treeNode.id);
    return true;
};


function reLoad(deptId) {
    var opt = {
        silent: true,
        query: {
            nickName: $('#searchName').val(),
            deptId: deptId
        }
    };
    $('#exampleTable').bootstrapTable('refresh', opt);
}

function zTreeBeforeExpand(treeId, treeNode) {
    $.ajax({
        type: "GET",
        url: prefixDept + "selectChildrenTree/" + treeNode.id,
        dataType: 'json',
        success: function (tree) {
            if (tree.resultCode == 1) {
                zTreeObj.removeChildNodes(treeNode);
                zTreeObj.addNodes(treeNode,tree.result);
            } else {
                layer.msg(tree.errorMsg);
            }
        }
    });
    return true;
};