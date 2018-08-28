//var menuTree;
var permissionIds;
$(function () {
    // getMenuTreeData();
    validateRule();
});
$.validator.setDefaults({
    submitHandler: function () {
        save();
    }
});


function save() {
    //  $('#permissionIds').val(permissionIds);
    var dept = $('#signupForm').serialize();
    $.ajax({
        cache: true,
        type: "POST",
        url: "/sys/dept/save",
        data: dept,// 你的formid
        async: false,
        error: function (request) {
            alert("Connection error");
        },
        success: function (data) {
            var result = JSON.parse(data);
            if (result.resultCode == 1) {
                parent.layer.msg("操作成功");
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);

            } else {
                parent.layer.msg(result.errorMsg);
            }
        }
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            levelNo: {
                required: true
            }
        },
        messages: {
            roleName: {
                required: icon + "请输入级别代码"
            }
        }
    });
}