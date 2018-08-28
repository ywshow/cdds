var permissionIds;
$(function () {
    getMenuTreeData();
    validateRule();
});
$.validator.setDefaults({
    submitHandler: function () {
        getAllSelectNodes();
        update();
    }
});

function loadMenuTree(menuTree) {
    $('#menuTree').jstree({
        "plugins": ["wholerow", "checkbox"],
        'core': {
            'data': menuTree
        },
        "checkbox": {
            "three_state": false,
            //"cascade" : 'down'
        }
    });
    $('#menuTree').jstree('open_all');
}

function getAllSelectNodes() {
    var ref = $('#menuTree').jstree(true); // 获得整个树
    permissionIds = ref.get_selected(); // 获得所有选中节点，返回值为数组
}

function getMenuTreeData() {
    var roleId = $('#id').val();
    $.ajax({
        type: "GET",
        url: "/sys/permission/tree/" + roleId,
        success: function (data) {
            loadMenuTree(data);
        }
    });
}

function update() {
    $('#permissionIds').val(permissionIds);
    var role = $('#signupForm').serialize();
    $.ajax({
        cache: true,
        type: "POST",
        url: "/sys/role/update",
        data: role, // 你的formid
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
            roleName: {
                required: true
            }
        },
        messages: {
            roleName: {
                required: icon + "请输入角色名"
            }
        }
    });
}