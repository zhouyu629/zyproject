<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${tree.tree_name}</title>
    <script type="text/javascript"></script>
    <link href="/manage/css/bootstrap.min.css" rel="stylesheet">
    <link href="/manage/css/styles.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="/manage/js/html5shiv.js"></script>
    <script src="/manage/js/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        .table {font-size:12px;}
        th{white-space:nowrap;}
        .modal-body{padding-bottom: 0px;}
        .tab-content{padding-bottom: 0px;}
    </style>

</head>

<body>

<#--角色设置模态框-->
<div id="userrole" class="modal fade" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header"><a class="close" data-dismiss="modal">x</a><h4>用户角色</h4></div>
            <div class="modal-body">
                <fieldset>
                    <input type="hidden" name="user_id2" id="user_id2"/>
                    <table class="table table-bordered dchannel-table">
                        <tbody>
                        <tr class="item-default">
                            <td align="right" style="width: 50%;">
                                    <select id="sel_unselected_roles" multiple="multiple" size="10" style="width:100%;">
                                </select>
                            </td>
                            <td style="width: 50px;" valign="middle">
                                <button type="button" class="btn btn-default btn-small" id="btn_alltoright"><span class="glyphicon glyphicon-forward"></span></button>
                                <button type="button" class="btn btn-default btn-small" id="btn_onetoright"><span class="glyphicon glyphicon-chevron-right"></span></button>
                                <button type="button" class="btn btn-default btn-small" id="btn_onetoleft"><span class="glyphicon glyphicon-chevron-left"></span></button>
                                <button type="button" class="btn btn-default btn-small" id="btn_alltoleft"><span class="glyphicon glyphicon-backward"></span></button>
                            </td>
                            <td style="width: 50%;">
                                <select id="sel_selected_roles"  multiple="multiple" size="10" style="width:100%;">
                                </select>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </fieldset>
            </div>
            <div class="modal-footer">
                <a href="javascript:roleuserSubmit();" class="btn btn-primary"><span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span> 提交</a>
                <a href="#" class="btn btn-warning" data-dismiss="modal"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span> 关闭</a>
            </div>
        </div>
    </div>
</div>
<#--角色设置模态框END-->

<!--新增或修改模态框-->
<div id="modify" class="modal fade" aria-hidden='true'>
    <div class="modal-dialog" style="width:800px;">
        <div class="modal-content">
            <div class="modal-header">
                <a class="close" data-dismiss="modal">×</a>
                <h4>用户管理</h4>
            </div>
            <div class="modal-body">
                <form role="form-inline" class="form-horizontal" id="form1" action="" method="post">
                    <div class="from-group row top-m-5">
                        <label class="control-label col-sm-3" for="user_id">登录名</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="login_code" id="login_code" placeholder="请输入登录名" />
                            <input type="hidden" name="user_id" id="user_id" />
                        </div>
                    </div>

                    <div class="from-group row top-m-5">
                        <label class="control-label col-sm-3" for="user_name">姓名</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="user_name" id="user_name" placeholder="请输入显示姓名" />
                        </div>
                    </div>
                    <div class="form-group row top-m-5" id="password-tips" style="display: none;">
                        <div class="col-sm-12 color-red text-center">密码不修改可以为空</div>
                    </div>
                    <div class="from-group row top-m-5">
                        <label class="control-label col-sm-3" for="login_password">密码</label>
                        <div class="col-sm-9">
                            <input type="password" class="form-control" name="login_password" id="login_password" placeholder="请输入密码" />
                        </div>
                    </div>

                    <div class="from-group row top-m-5">
                        <label class="control-label col-sm-3" for="login_password2">确认密码</label>
                        <div class="col-sm-9">
                            <input type="password" class="form-control" name="login_password2" id="login_password2" placeholder="请再次输入密码" />
                        </div>
                    </div>

                    <div class="from-group row top-m-5">
                        <label class="control-label col-sm-3" for="mobile">手机号码</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="mobile" id="mobile" placeholder="请输入手机号码" />
                        </div>
                    </div>

                    <div class="row">&nbsp;</div>
                </form>
            </div>
            <div class="modal-footer">
                <a href="javascript:submit();" class="btn btn-primary">
                    <span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span> 提交
                </a>
                <a href="#" class="btn btn-warning" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span> 关闭
                </a>
            </div>
        </div>
    </div>
</div>
<!--修改模态框End-->

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-12 col-lg-12 main">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="#"><span class="glyphicon glyphicon-home"></span></a></li>
                    <li class="active">${tree.tree_name}</li>
                </ol>
            </div><!--/.row-->
            <div class="panel panel-default">
                <div class="panel-heading">${tree.tree_name}
                    <span class="float-right">
                        <@perm tree_id="${tree_id}" ptype="A">
                            <button name="btnAdd" onclick="addOrEditOnClick('')" type="button" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                新增用户</button>
                        </@perm>
                    </span>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" user="form-inline" id="f" action="" method="post">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>登录名</th>
                                <th>姓名</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list users.list as user>
                                <tr>
                                    <td>${user.login_code}</td>
                                    <td>${user.user_name}</td>
                                    <td>${(user.del_flag==0)?string("正常","禁用")}</td>
                                    <td>
                                        <@perm tree_id="${tree_id}" ptype="M">
                                            <button name="btnModify" onclick="addOrEditOnClick(${user.user_id})" type="button" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</button>
                                        </@perm>
                                        <#if user.del_flag==0>
                                        <@perm tree_id="${tree_id}" ptype="D">
                                            <button name="btnDel" type="button" onclick="del(${user.user_id})" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</button>
                                        </@perm>
                                        <#elseif user.del_flag==1>
                                        <#--恢复按钮（暂时只取所有del_flag=0的用户，不实现）-->
                                        </#if>
                                        <@perm tree_id="${tree_id}" ptype="O">
                                            <button name="btnRole" type="button" onclick="setRole(${user.user_id})" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-user"></span>用户角色 </button>
                                        </@perm>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                        <ul class="row pagination">
                            <#import "../public/page.ftl" as fpage />
                            <@fpage.fpage page=users.page pagesize=users.pagesize totalpages=users.totalpages totalrecords=users.totalrecords url="/manage/user/index?tree_id=${tree.tree_id}" />
                        </ul>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/manage/js/jquery.min.js"></script>
<script src="/manage/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/manage/js/jquery.validate/core.js"></script>
<script type="text/javascript" src="/manage/js/user/index.js?v=17"></script>
<script type="text/javascript" src="/manage/js/user/select.js?v=3"></script>
</body>
</html>