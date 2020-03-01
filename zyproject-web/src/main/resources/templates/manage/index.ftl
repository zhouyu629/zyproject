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
                <form role="form-inline" id="user_basic_form" enctype="multipart/form-data" method="post">
                    <div class="tab-pane fade in active" id="basic">
                        <table class="table table-bordered table-hover">
                            <tr>
                                <th>登录名<span class="redtxt">*</span></th>
                                <td>
                                    <input type="text" class="form-control" name="user_code" id="user_code" placeholder="登录名" readonly="true"/>
                                    <input type="hidden" name="user_id" id="user_id" />
                                </td>
                                <th>姓名</th>
                                <td>
                                    <input type="text" class="form-control" name="user_name" id="user_name" placeholder="姓名" />
                                </td>
                                <th>性别</th>
                                <td>
                                    <select name="user_sex" id="user_sex" class="form-control">
                                        <option value="男">男</option>
                                        <option value="女">女</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>手机</th>
                                <td>
                                    <input type="text" class="form-control" name="user_phone" id="user_phone" placeholder="手机号码" />
                                </td>
                                <!-- 		                     <th>密码</th>
                                                             <td>
                                                                <input type="password" class="form-control" name="user_password" id="user_password" placeholder="请输入密码" />
                                                             </td> -->
                                <th>角色</th>
                                <td>
                                    <select name="user_role" id="user_role" class="form-control" disabled="disabled">
                                        <option value="">请选择</option>
                                        <volist name="role_list" id="t">
                                            <option value="{$t.role_id}">{$t.role_name}</option>
                                        </volist>
                                    </select>
                                </td>
                                <th>出生日期</th>
                                <td>
                                    <input type="text" class="form-control" name="user_birthday" id="user_birthday" placeholder="出生日期" />
                                </td>
                            </tr>
                            <tr>
                                <th>电子邮箱</th>
                                <td>
                                    <input type="text" class="form-control" name="user_email" id="user_email" placeholder="电子邮箱" />
                                </td>
                                <th>QQ</th>
                                <td>
                                    <input type="text" class="form-control" name="user_qq" id="user_qq" placeholder="请输入QQ号" />
                                </td>
                                <th>身份证号</th>
                                <td>
                                    <input type="text" class="form-control" name="user_card" id="user_card" placeholder="请输入身份证号码" />
                                </td>
                            </tr>
                            <tr>

                                <th>学历</th>
                                <td>
                                    <select name="user_degree" id="user_degree" class="form-control">
                                        <option value="博士">博士</option>
                                        <option value="硕士">硕士</option>
                                        <option value="本科">本科</option>
                                        <option value="大专">大专</option>
                                        <option value="高中">高中</option>
                                        <option value="初中">初中</option>
                                        <option value="小学">小学</option>
                                    </select>
                                </td>

                                <th>所属地区<span class="redtxt">*</span></th>
                                <td colspan=3>
                                    <select name="area_id" id="area_id" class="form-control" disabled="disabled">
                                        <volist name="select_area_tree" id="t">
                                            <option value="{$t.area_id}">{$t.area_name}</option>
                                        </volist>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>通讯地址</th>
                                <td colspan=5>
                                    <input type="text" class="form-control" name="user_address" id="user_address" placeholder="请输入通讯地址" />
                                </td>
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
                        <li><a href="__APP__/Home/Login/logout"><span class="glyphicon glyphicon-log-out"></span> 退出登录</a></li>
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
<script src="/manage/js/bootstrap-datepicker.js"></script>
<script>
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

        $.ajax({
            url:"/manage/Index/getUserData",
            type:"post",
            dataType:"json",
            success:function(res){
                if(res.code == 1){
                    initEditData(res.value);
                }else{
                    alert(res.msg);
                }
            }
        });

        $('#personalRes').modal({
            show:true,
            backdrop:'static'
        });

        $("#personalRes").modal('show').css({
            width: 'auto'
        });
    }

    //编辑资料时初始化值
    function initEditData(c){
        $("#user_id").val(c.userid);
    }

    // 新增或修改提交
    function submit(){
        var user_code = $("#user_code").val();
        if (user_code == '') {
            alert('登录名不能为空');
            $("#user_code").focus();
        } else{
            // 开始提交
            $.ajax({
                url:"/manage/Index/editSubmit",
                type:"POST",
                dataType:"json",
                data:$("#user_basic_form").serialize(),
                success:function(response){
                    if(response.code == -1){
                        alert(response.msg);
                    }else{
                        alert('操作成功');
                        $("#personalRes").modal('hide');
                    }
                }
            });
        }
    }


</script>
</body>

</html>
