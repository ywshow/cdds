var permissionIds;
$(function () {
    validateRule();
});
$.validator.setDefaults({
    submitHandler: function () {
        update();
    }
});


function update() {
    debugger;
    var dept = $('#signupForm').serialize();
    $.ajax({
        cache: true,
        type: "POST",
        url: "/sys/dept/save",
        data: dept, // 你的formid
        async: false,
        error: function (request) {
            alert("Connection error");
        },
        success: function (r) {
            var result = JSON.parse(r);
            if (result.resultCode == 1) {
                parent.layer.msg("保存成功");
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
            deptName: {
                required: true
            },
            deptCode: {
                required: true
            },
            sysAccount: {
                required: true
            }
        },
        messages: {
            deptName: {
                required: icon + "请输入部门名称"
            }, deptCode: {
                required: icon + "请输入部门代码"
            }, sysAccount: {
                required: icon + "请输入套账号"
            }
        }
    });
}