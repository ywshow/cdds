var parentTreeId = "001";
$(function () {
    $(".f-menus").click(function () {
        if ($(this).find(".ico-tag").hasClass("fa-angle-down")) {
            $(".f-menus").find('.ico-tag').removeClass("fa-angle-down");
            $(".f-menus").find('.ico-tag').addClass('arrow');
            $(".f-menus").parent("li").find(".nav-second-level").hide();
            $(this).parent("li").find(".nav-second-level").show();
            $(this).find(".ico-tag").removeClass("arrow");
            $(this).find(".ico-tag").addClass("fa-angle-down");
        } else if ($(this).find(".ico-tag").hasClass("arrow")) {
            $(".f-menus").find('.ico-tag').removeClass("fa-angle-down");
            $(".f-menus").find('.ico-tag').addClass('arrow');
            $(".f-menus").parent("li").find(".nav-second-level").hide();
            $(this).parent("li").find(".nav-second-level").show();
            $(this).find(".ico-tag").addClass("fa-angle-down");
            $(this).find(".ico-tag").removeClass("arrow");
        }
    })
});

/**
 * 出现处理
 * @param str 显示的查询条件
 */
function dealWithSearch(str) {
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
}

/**
 * 重置处理
 */
function dealWithRestSearch() {
    $(".searchForm .form-group").each(function () {
        $(this).find("input").val("");
    });
    $("#resetSearch").hide();
    $(".searchContent").hide().find("span").text("");
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

function save(method, url, formId) {
    console.log(formId);
    var param = $(formId).serialize();
    $.ajax({
        cache: true,
        type: method,
        url: url,
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