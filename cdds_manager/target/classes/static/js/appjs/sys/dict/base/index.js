var prefix = "/sys/dictbase/open/";
var prefixDetail = "/sys/basedetail/open/";
$(function () {
    getTreeData();
});

var zTreeObj;

function load(baseId) {
    $('#dictBaseTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefixDetail + "/selectBySysDictBaseId", // 服务器数据的加载地址
                iconSize: 'outline',
                toolbar: '#dictBaseToolbar',
                uniqueId: "id",

                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect: false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 5, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                // search : true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者"server"
                onEditableSave: onEditableSave,
                responseHandler: function (res) {
                    if (res.resultCode == 1) {
                        return {
                            //server：对应rows;client：对应data
                            total: res.result.pageInfo.total,
                            rows: res.result.pageInfo.list
                        }
                    } else {
                        layer.msg(res.errorMsg);
                        return false;
                    }

                },
                queryParams: function (params) {
                    //参数
                    var json = {
                        baseId: $("#baseId").val()
                    };
                    var param = {
                        pageNumber: params.offset / params.limit + 1,
                        pageSize: params.limit,
                        params: JSON.stringify(json)
                    };
                    return param;
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
                        field: 'groupName',
                        title: '代码组名称',
                        cellStyle: cellStyle
                    },
                    {
                        align: "left",
                        field: 'groupCode',
                        title: '代码组代码',
                        cellStyle: cellStyle
                    },
                    {
                        align: "left",
                        field: 'paramKey',
                        title: '代码键',
                        cellStyle: cellStyle
                    },
                    {
                        align: "left",
                        field: 'paramValue',
                        title: '代码值',
                        undefinedText: '-',
                        editable: {
                            type: 'text',
                            validate: function (v) {
                                //校验
                            }
                        },
                        formatter: function (value, row, index) {
                            if (undefined == value) {
                                return '';
                            } else {
                                return value;
                            }
                        },
                        cellStyle: cellStyle
                    },
                    {
                        field: 'status',
                        title: '状态',
                        align: 'left',
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
                            var ids = new Array();
                            ids[0] = row.id;
                            var e = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit "></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + ids.toString()
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            return d;
                        }
                    }]
            });
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
    var url = prefixDetail + 'updateParamValueById/' + row.id;
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

/**
 * 查询前初始化
 * @param params
 * @returns {{pageNumber: number, pageSize: (*|number), params}}
 */
function queryParams(params) {
    //参数
    var json = {
        baseId: baseId,
        startDate: $("#startDate").val(),
        endDate: $("#endDate").val()
    };
    var param = {
        pageNumber: params.offset / params.limit + 1,
        pageSize: params.limit,
        params: JSON.stringify(json)
    }
    return param;
}

function reLoad() {
    var opt = {
        silent: true,
        query: {
            baseId: $('#baseId').val(),
            nickName: $('#searchName').val()
        }
    };
    $('#dictBaseTable').bootstrapTable('refresh', opt);
}

function add() {
    var baseId = $('#baseId').val();
    if (baseId == null || baseId.length == 0 || baseId == '001') {
        layer.msg("请选择非目录昵称");
        return;
    }
    // iframe层
    layer.open({
        type: 2,
        title: '增加字典',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefixDetail + '/add/' + baseId
    });
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefixDetail + "delete",
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

function batchRemove() {
    var rows = $('#dictBaseTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
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

function getTreeData() {
    $.ajax({
        type: "GET",
        url: prefix + "getDictBaseTree",
        dataType: 'json',
        success: function (data) {
            if (data.resultCode == 1) {
                loadTree(data.result);
            } else {
                layer.msg(data.errorMsg);
            }
        }
    });
}

function loadTree(zTreeNodes) {
    var setting = {
        edit: {
            enable: true,
            editNameSelectAll: true,
            showRemoveBtn: true,
            removeTitle: "删除节点",
            showRenameBtn: setNodebtn
        },
        view: {
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom
        },
        callback: {
            onRename: zTreeOnRename,
            beforeRemove: zTreeBeforeRemove,
            onClick: zTreeOnClick
        }
    };
    zTreeObj = $.fn.zTree.init($("#ztree"), setting, zTreeNodes);
    var nodes = zTreeObj.getNodes()[0].children;
    if (null != nodes && nodes.length > 0) {
        //默认获取非目录第一个节点
        var firstNode = nodes[0];
        $("#baseId").val(firstNode.id);
        load(firstNode.id);
        zTreeObj.selectNode(firstNode);
    }
}

/**
 * 是否显示编辑，删除按钮
 * @param treeId
 * @param treeNode
 * @returns {boolean}
 */
function setNodebtn(treeId, treeNode) {
    if (treeNode.id == parentTreeId) {
        return false;
    } else {
        return true;
    }
}

/**
 * 编辑节点
 * @param event
 * @param treeId
 * @param treeNode
 * @param isCancel
 */
function zTreeOnRename(event, treeId, treeNode, isCancel) {
    $.post(prefix + "update/" + treeNode.id, {
        id: treeNode.id,
        groupCode: treeNode.groupCode,
        groupName: treeNode.name
    }, function (data) {
        if (data.resultCode != 1) {
            layer.msg(data.errorMsg);
        } else {
            layer.msg("修改成功");
        }
    }, "json")
}

/**
 * 删除节点
 * @param treeId
 * @param treeNode
 */
function zTreeBeforeRemove(treeId, treeNode) {
    var isdel = false;
    layer.confirm('确定要删除该节点？', {
        btn: ['确定', '取消']
    }, function () {
        $.post(prefix + "delete/" + treeNode.id, {
            id: treeNode.id
        }, function (data) {
            if (data.resultCode != 1) {
                layer.msg(data.errorMsg);
                isdel = false;
            } else {
                layer.msg("删除成功");
                isdel = true;
                zTreeObj.removeNode(treeNode);
            }
        }, "json");
    }, function (index) {
        layer.close(index);
        isdel = false;
    });
    return isdel;
}

/**
 * 新增节点按钮
 * @param treeId
 * @param treeNode
 */
function addHoverDom(treeId, treeNode) {
    if (treeNode.id == parentTreeId) {
        var aObj = $("#" + treeNode.tId + "_a");
        if ($("#diyBtn_" + treeNode.id).length > 0) return;
        var editStr = "<span id='diyBtn_space_" + treeNode.id + "' title='新增节点' class='button add'></span>"
            + "<button type='button' class='diyBtn1' id='diyBtn_" + treeNode.id
            + "' title='新增节点' onfocus='this.blur();'></button>";
        aObj.append(editStr);
        var btn = $("#diyBtn_space_" + treeNode.id);
        if (btn) btn.bind("click", function () {
            openAddBase(treeNode);
        });
    }
}

function removeHoverDom(treeId, treeNode) {
    if (treeNode.id == parentTreeId) {
        $("#diyBtn_" + treeNode.id).unbind().remove();
        $("#diyBtn_space_" + treeNode.id).unbind().remove();
    }

}

function zTreeOnClick(event, treeId, treeNode) {
    if (treeNode.id != parentTreeId) {
        $("#baseId").val(treeNode.id);
        reLoad();
    }
}


/**
 * 新增树节点
 *
 */
var addLayerIndex;

function openAddBase(treeNode) {
    addLayerIndex = layer.open({
        type: 1,
        title: '新增节点',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['500px', '290px'],
        content: $("#dictBaseAdd")
    });
    $("#curentNodeTId").val(treeNode.tId);
}

/**
 * 新增节点
 */
function submitBase() {
    var param = $('#dictBaseAddForm').serialize();
    var groupCode = $("#groupCode").val();

    var url = prefix + 'insert/' + groupCode;
    $.ajax({
        cache: true,
        type: "POST",
        url: url,
        data: param,
        async: false,
        dataType: 'json',
        error: function (request) {
            alert("Connection error");
        },
        success: function (data) {
            if (data.resultCode == 1) {
                var newNode = data.result[0].children[0];
                $("#baseId").val(newNode.id);
                var node = zTreeObj.getNodeByTId($("#curentNodeTId").val());
                zTreeObj.addNodes(node, -1, newNode);
                layer.msg("操作成功");
                layer.close(addLayerIndex);
                reLoad();
            } else {
                layer.msg(result.errorMsg);
            }
        }
    });
}
