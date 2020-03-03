<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>后台首页</title>

    <link href="/manage/css/bootstrap.min.css" rel="stylesheet">
    <link href="/manage/css/datepicker3.css" rel="stylesheet">
    <link href="/manage/css/styles.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <script src="/manage/js/html5shiv.js"></script>
    <script src="/manage/js/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        html,body{height:99%;}
        body{padding-top: 50px;}
        .main{height:100%;padding-right: 0px;padding-left: 0px;}
    </style>

</head>

<body>


<!--修改密码模态框-->
<div id="password" class="modal fade" aria-hidden='true'>
    <div class="modal-dialog" style="width:800px;">
        <div class="modal-content">
            <div class="modal-header">
                <a class="close" data-dismiss="modal">×</a>
                <h4>修改密码</h4>
            </div>
            <div class="modal-body">
                <form role="form-inline" id="form1" action="submitAreaManage" method="post">
                    <table class="table table-bordered table-hover">
                        <tr >
                            <th>初始密码</th>
                            <td >
                                <input type="password" class="form-control" name="old_password" id="old_password" placeholder="请输入初始密码" />
                                <input type="hidden" name="user_id" id="user_id" />
                            </td>
                        </tr>

                        <tr >
                            <th>新密码</th>
                            <td>
                                <input type="password" class="form-control" name="new_password" id="new_password" placeholder="请输入新密码" />
                            </td>
                        </tr>

                        <tr>
                            <th>确认密码</th>
                            <td >
                                <input type="password" class="form-control" name="check_password" id="check_password" placeholder="请再次输入密码"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <a href="javascript:reset();" class="btn btn-primary">
                    <span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span> 提交
                </a>
                <a href="#" class="btn btn-warning" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span> 关闭
                </a>
            </div>
        </div>
    </div>
</div>
<!--修改密码模态框End-->


<!--个人资料模态框-->
<div id="personalRes" class="modal fade" aria-hidden='true'>
    <div class="modal-dialog" style="width:800px;">
        <div class="modal-content">
            <div class="modal-header">
                <a class="close" data-dismiss="modal">×</a>
                <h4>个人资料</h4>
            </div>
            <div class="modal-body">
                <form role="form-inline" id="user_basic_form" method="post">
                    <div class="tab-pane fade in active" id="basic">
                        <table class="table table-bordered table-hover">
                            <tr>
                                <th>登录名<span class="redtxt">*</span></th>
                                <td>
                                    <input type="text" value="${user.login_code}" class="form-control" name="login_code" id="login_code" placeholder="登录名" readonly="true"/>
                                    <input type="hidden" name="user_id" id="user_id" value="${user.user_id}" />
                                </td>
                            </tr>
                            <tr>
                                <th>姓名<span class="redtxt">*</span> </th>
                                <td>
                                    <input type="text" value="${user.user_name}" class="form-control" name="user_name" id="user_name" placeholder="姓名" />
                                </td>
                            </tr>
                            <tr>
                                <th>手机号码</th>
                                <td><input type="text" name="mobile" id="mobile" class="form-control" value="${user.mobile!''}" placeholder="请输入11位手机号码"></td>
                            </tr>
                        </table>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a href="javascript:submit();" class="btn btn-primary">
                    <span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span> 修改
                </a>
                <a href="#" class="btn btn-warning" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span> 关闭
                </a>
            </div>
        </div>
    </div>
</div>
<!--个人资料模态框End-->

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><span>微服务</span>Admin</a>
            <ul class="user-menu">
                <li class="dropdown pull-right">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> ${username} <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="javascript:userResource()"><span class="glyphicon glyphicon-user"></span> 个人资料</a></li>
                        <li><a href="javascript:resetPassword()"><span class="glyphicon glyphicon-cog"></span> 密码修改</a></li>
                        <li><a href="/manage/logout"><span class="glyphicon glyphicon-log-out"></span> 退出登录</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div><!-- /.container-fluid -->
</nav>

<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
    <ul class="nav menu">
        <li class="active"><a href="javascript:goto('0000','/home');"><span class="glyphicon glyphicon-home"></span> 首页</a></li>
            <#list treelist as tree>
                <#if tree.sup_tree_id == 0>
                <li class="parent ">
                    <a data-toggle="collapse" href="#sub-item-${tree.tree_id?c}">
                        <span class="glyphicon ${tree.tree_icon}"></span> ${tree.tree_name}
                    </a>
                    <ul class="children collapse" id="sub-item-${tree.tree_id?c}">
                <#list treelist as tree_c>
                    <#if tree_c.sup_tree_id==tree.tree_id>
                        <li><a href="javascript:goto(${tree_c.tree_id?c},'${tree_c.link_addr!}')"><span class="glyphicon ${tree_c.tree_icon}"></span> ${tree_c.tree_name}</a></li>
                    </#if>
                </#list>
                    </ul>
                </li>
                </#if>
            </#list>
        <li role="presentation" class="divider"></li>
        <li><a href="/manage/Login/logout"><span class="glyphicon glyphicon-user"></span> 注销</a></li>
    </ul>
</div><!--/.sidebar-->

<div class="col-sm-12 col-lg-10 col-lg-offset-2 main">
    <iframe src="/manage/home" height="100%" width="100%" id="main" frameborder="0"
            scrolling="yes"></iframe>
</div>	<!--/.main-->

<script src="/manage/js/jquery-1.11.1.min.js"></script>
<script src="/manage/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/manage/js/jquery.validate/core.js"></script>
<script type="text/javascript">
    $("#user_basic_form").validate({
        submitHandler: function(form){
            //向服务器提交表单
            $.ajax({
                url:"/manage/user/user-submit",
                type:"POST",
                dataType:"json",
                data:$("#user_basic_form").serialize(),
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
            user_name: {required:true},
        },
        messages:{
            user_name: {required: "文章标题不能为空！"}
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

    !function ($) {
        $(document).on("click","ul.nav li.parent > a > span.icon", function(){
            $(this).find('em:first').toggleClass("glyphicon-minus");
        });
        $(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");

    }(window.jQuery);

    $(window).on('resize', function () {
        if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
    })
    $(window).on('resize', function () {
        if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
    })

    function goto(tree_id,link){
        $("#main").attr('src','/manage' + link+'?tree_id='+tree_id);
    }

    function resetPassword(){
        $('#password').modal({
            show:true,
            backdrop:'static'
        });

        $("#password").modal('show').css({
            width: 'auto'
        });

    }

    function reset(){
        var oldPassword = $("#old_password").val();
        if(oldPassword == ''){
            alert('请输入初始密码');
            $("#old_password").focus();
            return;
        }


        var newPassword = $("#new_password").val();
        if(newPassword == ''){
            alert('请输入新密码');
            $("#new_password").focus();
            return;
        }

        var checkPassword = $("#check_password").val();
        if(checkPassword == ''){
            alert('请再次输入新密码');
            $("#check_password").focus();
            return;
        }


        $.ajax({
            url:"/manage/Index/resetPassword",
            type:"POST",
            dataType:"json",
            data:$("#form1").serialize(),
            success:function(response){
                if(response.code == -1){
                    alert(response.msg);
                }else{
                    alert('修改成功，下次请使用新密码登陆！');
                    //window.location.reload();
                    $("#password").modal('hide');
                    $("#old_password").val("");
                    $("#new_password").val("");
                    $("#check_password").val("");

                }
            }
        });
    }

    function userResource(){
        $('#personalRes').modal({
            show:true,
            backdrop:'static'
        });
    }

    // 新增或修改提交
    function submit(){
        $("#user_basic_form").submit();
    }


</script>
</body>

</html>
