var prefix = "/sys/dict/open/";
$(function () {
    load();
});

function load() {
    $('#dictTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/selectSysDictList", // 服务器数据的加载地址
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                singleSelect: false, // 设置为true将禁止多选
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者"server"
                onEditableSave: onEditableSave,
                responseHandler: responseHandler,
                queryParams: queryParams,
                columns: [
                    { // 列配置项
                        // 数据类型，详细参数配置参见文档http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/
                        checkbox: true
                        // 列表中显示复选框
                    },
                    {
                        field: 'id', // 列字段名
                        title: '序号' , // 列标题
                        width:50,
                        align:'center',
                        formatter: function (value, row, index) {
                            return index + 1;
                        }
                    },
                    {
                        field: 'sysAccount',
                        title: '账套号'
                    },
                    {
                        field: 'groupName',
                        title: '组名'
                    },
                    {
                        field: 'groupCode',
                        title: '组代码',
                        cellStyle: cellStyle
                    },
                    {
                        field: 'paramKey',
                        title: '参数键',
                        cellStyle: cellStyle
                    },
                    {
                        field: 'paramValue',
                        title: '参数值',
                        cellStyle: cellStyle,
                        undefinedText: '-',
                        editable: {
                            type: 'text',
                            validate: function (v) {
                                //校验
                            }
                        },
                        cellStyle: cellStyle
                    },
                    {
                        field: 'remark',
                        title: '备注'
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var ids = new Array();
                            ids[0] = row.id;
                            var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + ids.toString()
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            return d;
                        }
                    }]
            });
}

function reLoad() {
    $('#dictTable').bootstrapTable('refresh');
}

/**
 * 数据返回处理
 * @param res
 * @returns {{total, rows}}
 */
function responseHandler(res) {
    if (res.resultCode != 1) {
        layer.msg(res.errorMsg);
    }
    return {
        //server：对应rows;client：对应data
        total: res.result.pageInfo.total,
        rows: res.result.pageInfo.list
    }
}

/**
 * 查询前初始化
 * @param params
 * @returns {{pageNumber: number, pageSize: (*|number), params}}
 */
function queryParams(params) {
    //参数
    var json = {
        sysAccount: $("#sysAccount").val(),
        paramKey: $("#paramKey").val(),
        groupCode: $("#groupCode").val()
    };
    var param = {
        pageNumber: params.offset / params.limit + 1,
        pageSize: params.limit,
        params: JSON.stringify(json)
    }
    return param;
}

/**
 * 行内编辑提交
 * 注：只有行内数据有更改才会触发
 * @param field
 * @param row
 * @param oldValue
 * @param $el
 */
function onEditableSave(field, row, oldValue, $el) {
    var url = prefix + '/update';
    $.post(url,
        {
            id: row.id,
            paramValue: row.paramValue
        },
        function (data) {
            if (data.resultCode != 1) {
                layer.msg(data.errorMsg);
            } else {
                layer.msg("修改成功");
            }
        }, "json");
}

/**
 * 字段截取
 * @param value
 * @param row
 * @param index
 * @returns {{css: {overflow: string, white-space: string, text-overflow: string}}}
 */
function cellStyle(value, row, index) {
    var style = {
        css: {
            'overflow': 'hidden',
            'white-space': 'nowrap',
            'text-overflow': 'ellipsis'
        }
    }
    return style;
}

function batchRemove() {
    var rows = $('#dictTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    var ids = new Array();
    // 遍历所有选择的行数据，取每条数据对应的ID
    $.each(rows, function (i, row) {
        ids[i] = row['id'];

    });
    remove(ids.toString());
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "delete",
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

function add() {
    // iframe层
    layer.open({
        type: 2,
        title: '添加账套字典',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add' // iframe的url
    });
}

function edit(id) {
    layer.open({
        type: 2,
        title: '账套字典修改',
        maxmin: true,
        shadeClose: true, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}



var searchIndex = null;

function search() {
    searchIndex = layer.open({
        type: 1,
        title: '账套字典查询',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['500px', '310px'],
        content: $("#dictSearch")
    });
}

function resetSearch() {
    dealWithRestSearch();
    reLoad();
}

function searchButton() {
    var str = "";
    var account = $("#sysAccount").val();
    var id = $("#sysAccount").attr("id");
    if ((account != null && account != undefined && account != '')) {
        if (!checkSearchInput(account, id, "账套号")) {
            return false;
        }
    }
    dealWithSearch(str);
    reLoad();
}

function syncInfo() {
    layer.confirm('是否同步数据字典？', {
        btn: ['是', '否']
    }, function () {
        var indexLoad = layer.load(2);
        $.ajax({
            url: prefix + "batchInsertFromDictBase",
            type: "post",
            success: function (data) {
                layer.close(indexLoad);
                var result = JSON.parse(data);
                if (result.resultCode == 1) {
                    layer.msg("操作成功");
                    reLoad();
                } else {
                    layer.msg(result.errorMsg);
                }
            }
        });
    });

}
