var prefix = "/api/sys/account";
var $table = $('#accountTable'),
    $button = $('#batchRemove');
$(document).ready(function () {
    load();
    $button.click(function () {
        var array = $table.bootstrapTable('getSelections');
        if (array.length > 0) {
            var ids = new Array();
            for (var i = 0; i < array.length; i++) (
                ids[i] = array[i].id
            )
            remove(ids.toString());
        } else {
            layer.msg("请选择要删除的数据!");
        }
    });
});
var load = function () {
    $table.bootstrapTable(
        {
            method: 'get', // 服务器数据的请求方式 get or post
            url: prefix + "/list", // 服务器数据的加载地址
            iconSize: 'outline',
            toolbar: '#accountToolbar',
            striped: true, // 设置为true会有隔行变色效果
            dataType: "json", // 服务器返回的数据类型
            pagination: true, // 设置为true会在底部显示分页条
            // queryParamsType : "limit",
            // //设置为limit则会发送符合RESTFull格式的参数
            singleSelect: false, // 设置为true将禁止多选
            contentType: "application/x-www-form-urlencoded",
            // //发送到服务器的数据编码类型
            pageSize: 10, // 如果设置了分页，每页数据条数
            pageNumber: 1, // 如果设置了分布，首页页码
            pageList: [10, 25, 50, 100],
            showRefresh: false,//是否显示刷新按钮
            sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者"server"
            responseHandler: responseHandler,
            queryParams: queryParams,
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
                    field: 'sysAccount',
                    title: '账套号'
                },
                {
                    align: "left",
                    field: 'company',
                    title: '公司名称'
                },
                {
                    align: "left",
                    field: 'webAddress',
                    title: '网址'
                },
                {
                    align: "left",
                    field: 'address',
                    title: '地址'
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
                        return e + d;
                    }
                }]
        });
}

function reLoad() {
    $('#accountTable').bootstrapTable('refresh');
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

function add() {
    layer.open({
        type: 2,
        title: '新增账套号',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add' // iframe的url
    });
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/delete",
            type: "post",
            data: {
                'ids': id
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
        title: '账套号修改',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}

var searchIndex = null;

function search() {
    searchIndex = layer.open({
        type: 1,
        title: '账套号查询',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['500px', '390px'],
        content: $("#accountSearch"),
        success: function (layero, index) {
            $(layero).find(".form_datetime").datetimepicker({
                autoclose: true,
                todayBtn: true,
                minView: "month",
                language: 'zh-CN',
                pickerPosition: "bottom-right",
                format: 'yyyy-mm-dd'
            });
        }
    });
}

function resetSearch() {
    $(".searchForm .form-group").each(function () {
        $(this).find("input").val("");
    });
    $("#resetSearch").hide();
    $(".searchContent").hide().find("span").text("");
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
    $(".searchForm .form-group").each(function () {
        var val = $(this).find("input").val();
        if (val != null && val != undefined && val != "") {
            str += $(this).find(".control-label").text() + val + ";";
        }
    });

    if (str.length > 0) {
        str = "当前查询条件：" + str;
        $("#resetSearch").show();
    } else {
        $("#resetSearch").hide();
    }
    $(".searchContent").show().find("span").text(str);
    //关闭最新弹窗
    if (searchIndex != null) {
        layer.close(searchIndex);
    }
    reLoad();
}

function checkSearchInput(object, idTag, remark) {
    //正则表达式
    var reg = new RegExp("^[A-Za-z]+$");
    if (!reg.test(object)) {
        layer.msg(remark + "只能输入字母！");
        $("#" + idTag).val("");
        return false;
    }
    return true;
}
