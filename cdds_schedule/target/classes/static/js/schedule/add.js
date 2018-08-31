var prefix = "/sys/schedule/";
$(function () {
    validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        save("POST", prefix + "save", "#scheduleAdd");
    }
});


function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#scheduleAdd").validate({
        rules: {
            beanName: {
                required: true
            },
            methodName: {
                required: true
            },
            cronExpression: {
                required: true
            }
        },
        messages: {
            beanName: {
                required: icon + "bean名称不能为空"
            },
            methodName: {
                required: icon + "方法名称不能为空"
            },
            cronExpression: {
                required: icon + "cron表达式不能为空"
            }
        }
    });
}