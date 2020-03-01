var myValidate;
$(function(){
    myValidate =  $("#form1").validate({
        submitHandler: function(form){
            //向服务器提交表单
            $.ajax({
                url:"/manage/user/user-submit",
                type:"POST",
                dataType:"json",
                data:$("#form1").serialize(),
                success:function(res){
                    if(res.code == 1000){
                        alert("操作成功");
                        window.location.reload();
                    }else{
                        alert(res.msg+"/"+res.data);
                    }
                }
            });
        },
        focusInvalid : true,
        rules:{
            login_code: {
                required: true,
                /*
                * 编辑时，不知道该怎么忽略这个验证，放到后台Service里做吧
                remote: {
                    url:"/manage/user/checklogincode",
                    type:"POST",
                    dataType:"json",
                    data: {login_code:this.value},
                    dataFilter:function(res){
                        res = $.parseJSON(res);
                        console.log("验证结果："+(res.code!="1000")+"("+res.code+")");
                        return res.code != "1000";
                    }
                }*/
            },
            user_name: { required: true },
            login_password:{required: true},
            login_password2:{
                required: true,
                equalTo: "#login_password"
            }
        },
        messages:{
            login_code: {
                required: "登录名不能为空",
                remote: "登录名已存在"
            },
            user_name: {
                required: "用户姓名不能为空"
            },
            login_password: {required: "密码不能为空"},
            login_password2:{
                required: "请再次确认密码",
                equalTo: "两次输入的密码不一致"
            }
        },
        errorElement: "em",
        errorPlacement: function ( error, element ) {
            // Add the `help-block` class to the error element
            error.addClass( "help-block" );

            if ( element.prop( "type" ) === "checkbox" ) {
                error.insertAfter( element.parent( "label" ) );
            } else {
                error.insertAfter( element );
            }
        },
        highlight: function ( element, errorClass, validClass ) {
            $( element ).parents( ".col-sm-9" ).addClass( "has-error" ).removeClass( "has-success" );
        },
        unhighlight: function (element, errorClass, validClass) {
            $( element ).parents( ".col-sm-9" ).addClass( "has-success" ).removeClass( "has-error" );
        }
    });
});

//修改或新增提交
function submit(){
    $("#form1").submit();
}

//点击新增或修改按钮
function addOrEditOnClick(id){
    myValidate.resetForm();
    //如果是修改，取消密码和确认密码的必填属性
    var rule1 = {required: true};
    var rule2 = {required: true};
    if (id!='') {
        //这是编辑，默认赋值
        $("#login_password").rules("remove","required");
        $("#login_password2").rules("remove","required");
        $("#password-tips").show();
        initData(id);
        //禁用login_code
        $("#login_code").attr("readonly",true);
    }else{
        $("#login_password").rules("add",rule1);
        $("#login_password2").rules("add",rule2);
        $("#password-tips").hide();
        $("#form1")[0].reset();
        //启用login_code
        $("#login_code").attr("readonly",false);
    }

    //显示模态框
    $('#modify').modal({
        show:true,
        backdrop:'static'
    });
}
//编辑时，初始化form表单的默认值
function initData(id){
    $.ajax({
        url:"/manage/user/getuser",
        data:{id:id},
        dataType:"json",
        success:function(res){
            if(res.code == 1000){
                var data = res.data;
                $("#user_id").val(data.user_id);
                $("#login_code").val(data.login_code);
                $("#user_name").val(data.user_name);
                $("#mobile").val(data.mobile);
            }else{
                alert(res.msg);
            }
        }
    });
}

//删除用户
function del(user_id){
    if(confirm("确实要删除此用户吗？")) {
        $.ajax({
            url: "/manage/user/deluser",
            dataType: "json",
            type: "POST",
            data: {user_id: user_id},
            success: function (res) {
                if (res.code == 1000) {
                    alert("删除成功");
                    window.location.reload();
                } else {
                    alert(res.msg);
                }
            }
        });
    }
}

//用户角色按钮
function setRole(user_id){
    //取出所未选中的角色及选中的角色
    $("#user_id2").val(user_id);
    $.ajax({
        url:"/manage/user/getuserroleinfo",
        type:"POST",
        dataType:"json",
        data:{user_id:user_id},
        success:function(res){
            if(res.code == 1000){
                var ulist = res.data.ulist; //已分配
                var list = res.data.list; //未分配
                var sel_unselected_roles = $("#sel_unselected_roles");
                var sel_selected_roles = $("#sel_selected_roles");
                sel_unselected_roles.empty();
                sel_selected_roles.empty();
                for(var i = 0;i<ulist.length;i++){
                    var v = ulist[i];
                    //给右边绑定sel_selected_roles
                    sel_selected_roles.append($("<option value="+v.role_id+">"+v.role_name+"</option>"));
                };
                for(var i=0;i<list.length;i++){
                    var v=list[i];
                    //给左边绑定默认值sel_unselected_roles
                    sel_unselected_roles.append($("<option value="+v.role_id+">"+v.role_name+"</option>"));
                };
            }else{
                alert(res.msg);
            }
        }
    });
    //显示模态框
    $('#userrole').modal({
        show:true,
        backdrop:'static'
    });
}

//用户角色设置提交
function roleuserSubmit(){
    var user_id = $("#user_id2").val();
    var roleids = $("#sel_selected_roles option");
    var ids = new Array();
    roleids.each(function(i,v){
        ids.push(v.value);
    });
    if(ids.length>0){
        ids = ids.join(",");
    }
    $.post("/manage/user/userrole-submit",{user_id:user_id,roleids:ids},function(result){
        if (result.code != 1000) {
            alert("设置成功");
            window.location.reload();
        }else{
            alert(result.msg);
        }
    });
}