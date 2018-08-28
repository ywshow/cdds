var prefix = "/api/sys/account/";
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
    var params = $('#accountEdit').serialize();
    $.ajax({
        cache: true,
        type: "POST",
        url: prefix + "update",
        data: params, // 你的formid
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
    $("#accountEdit").validate({
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