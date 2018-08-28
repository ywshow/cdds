var prefix = "/api/sys/account/";
$(function () {
    validateRule();
});
$.validator.setDefaults({
    submitHandler: function () {
        save();
    }
});


function save() {
    var param = $('#accountAdd').serialize();
    $.ajax({
        cache: true,
        type: "POST",
        url: prefix+"insert",
        data: param,// 你的formid
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
    $("#accountAdd").validate({
        rules: {
            sysAccount: {
                required: true
            }
        },
        messages: {
            sysAccount: {
                required: icon + "账套号不能为空"
            }
        }
    });
}