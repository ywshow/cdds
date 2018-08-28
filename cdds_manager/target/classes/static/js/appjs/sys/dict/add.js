var prefix = "/sys/dict/open/";
var prefixBase = "/sys/dictbase/open/";
var zTreeObj;
$(function () {
    getTreeData();
    //禁止dropdown-menu点击关闭
    $("#ztree").on("click", function (e) {
        e.stopPropagation();
    });
    validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        save("POST", prefix + "insert", "#dictForm");
    }
});


function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#dictForm").validate({
        rules: {
            groupName: {
                required: true
            },
            groupCode: {
                required: true
            },
            paramKey: {
                required: true
            },
            paramValue: {
                required: true
            }
        },
        messages: {
            groupName: {
                required: icon + "名称不能为空"
            },
            groupCode: {
                required: icon + "代码组不能为空"
            },
            paramKey: {
                required: icon + "代码键不能为空"
            },
            paramValue: {
                required: icon + "代码值不能为空"
            }
        }
    });
}

function getTreeData() {
    $.ajax({
        type: "GET",
        url: prefixBase + "selectNotIn/" + $("#sysAccount").val(),
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
        check: {
            enable: true
        },
        callback: {
            onClick: zTreeOnClick,
            beforeCheck: zTreeBeforeCheck
        }
    };
    zTreeObj = $.fn.zTree.init($("#ztree"), setting, zTreeNodes);
}

function zTreeOnClick(event, treeId, treeNode) {
    if (treeNode.id != parentTreeId) {
        zTreeBeforeCheck(treeId, treeNode);
        //当前节点选中/取消选中
        if (!treeNode.checked) {
            treeNode.checked = true;
        } else {
            treeNode.checked = false;
        }
        zTreeObj.updateNode(treeNode);
    }
}

var array = new Array();

function zTreeBeforeCheck(treeId, treeNode) {
    var value = $("#ztreeValue").val();
    if (treeNode.checked == true) {
        if (treeNode.isParent == true) {
            //为父节点，删除全部
            value = "";
            $("#ztreeValue").val("");
            array = new Array();
        } else {
            //为子节点，删除当前
            value = value.replace(treeNode.name + ";", "");
            value = value.replace(";" + treeNode.name, "");
            value = value.replace(treeNode.name, "");
            if (array.length > 0) {
                var index = array.indexOf(treeNode.id);
                if (index > -1) {
                    array.splice(index, 1);
                }
            }
        }
    } else {
        if (treeNode.isParent == true) {
            //为父节点，添加全部
            $("#ztreeValue").val("");
            value = "";
            var nodes = treeNode.children;
            for (var i = 0; i < nodes.length; i++) {
                value += nodes[i].name + ";";
                array.push(nodes[i].id);
            }
        } else {
            //为子节点，添加当前
            if (value != "" && value != "null") {
                value += ";" + treeNode.name;
            } else {
                value += treeNode.name;
            }
            array.push(treeNode.id);
        }
    }
    $("#ztreeValue").val(value);
    return true;
}
