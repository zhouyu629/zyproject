<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${tree.tree_name}</title>
    <script type="text/javascript"></script>
    <link href="/manage/css/bootstrap.min.css" rel="stylesheet">
    <link href="/manage/css/datepicker3.css" rel="stylesheet">
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

<!--修改模态框-->
<div id="modify" class="modal fade" aria-hidden='true'>
    <div class="modal-dialog" style="width:800px;">
        <div class="modal-content">
            <div class="modal-header">
                <a class="close" data-dismiss="modal">×</a>
                <h4>角色信息</h4>
            </div>
            <div class="modal-body">
                <form role="form-inline" id="form1" action="submitAreaManage" method="post">
                    <table class="table table-bordered table-hover">
                        <tr>
                            <th>角色名称</th>
                            <td>
                                <input type="text" class="form-control" name="role_name" id="role_name" placeholder="请输入角色名称" />
                                <input type="hidden" name="role_id" id="role_id" />
                            </td>

                        </tr>
                        <tr>
                            <th>角色排序</th>
                            <td>
                                <input type="text" class="form-control" name="taxis" id="taxis" placeholder="请输入角色排序" />
                            </td>
                        </tr>
                    </table>
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
                            <button name="btnAdd" onclick="edit('')" type="button" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                新增角色</button>
                        </@perm>
                    </span>
                </div>
                <div class="panel-body">
                    <form role="form-inline" id="f" action="submitAreaManage" method="post">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>角色名称</th>
                                <th>角色排序</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list pager.list as roles>
                                <tr>
                                    <td>${roles.role_name}</td>
                                    <td>${roles.taxis}</td>
                                    <td>
                                    <@perm tree_id="${tree_id}" ptype="M">
                                        <button name="btnModify" onclick="edit(${roles.role_id})" type="button" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-th" aria-hidden="true"></span>修改</button>
                                    </@perm>
                                    <@perm tree_id="${tree_id}" ptype="D">
                                        <button name="btnDel" type="button" onclick="del(${roles.role_id})" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</button>
                                    </@perm>
                                    <@perm tree_id="${tree_id}" ptype="R">
                                        <button name="btnRight" type="button" onclick="setRight(${roles.role_id})" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-tags" aria-hidden="true"></span>权限</button>
                                    </@perm>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                        <ul class="row pagination">
                            <#import "../public/page.ftl" as fpage />
                            <@fpage.fpage page=pager.page pagesize=pager.pagesize totalpages=pager.totalpages totalrecords=pager.totalrecords url="/manage/role/index?tree_id=${tree.tree_id}" />
                        </ul>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/manage/js/jquery.min.js"></script>
<script src="/manage/js/bootstrap.min.js"></script>
<script src="/manage/js/bootstrap-datepicker.js"></script>
<script src="/manage/js/dateConvert.js"></script>
<script type="text/javascript">
    $(function(){

    });

    function edit(id){
        if (id!='') {
            //编辑
            $.ajax({
                url:"/manage/role/getrole",
                type:"POST",
                dataType:"json",
                data:{id:id},
                success:function(response){
                    if(response.code != 1000){
                        alert(response.msg);
                    }else{
                        $("#role_id").val(response.data.role_id);
                        $("#role_name").val(response.data.role_name);
                        $("#taxis").val(response.data.taxis);                  }
                }
            });
        }else{
            $("#form1")[0].reset();
        }

        $('#modify').modal({
            show:true,
            backdrop:'static'
        });

        $("#modify").modal('show').css({
            width: 'auto'
        });

    }

    //新增或修改提交
    function submit(){
        var role_name = $("#role_name").val();
        var taxis = $("#taxis").val();
        if(role_name == ''){
            alert('角色名称不能为空');
            $("#role_name").focus();
            return;
        }if(taxis == ''){
            alert('角色排序不能为空');
            $("#taxis").focus();
            return;
        }else{
            //开始提交
            $.ajax({
                url:"/manage/role/roleedit-submit",
                type:"POST",
                dataType:"json",
                data:$("#form1").serialize(),
                success:function(response){
                    if(response.code != 1000){
                        alert(response.msg);
                    }else{
                        alert('操作成功');
                        window.location.reload();
                    }
                }
            });
        }
    }
    function del(id){
        if(confirm("删除角色会同时删除已分配用户的角色，可能会影响到用户的权限，确实要删除此角色吗？")){
            $.ajax({
                url:"/manage/role/del",
                type:"POST",
                data:{id:id},
                dataType:"json",
                success:function(res){
                    if(res.code == 1000){
                        alert('删除成功');
                        window.location.reload();
                    }else{
                        alert(res.msg);
                    }
                }
            });
        }
    }

    function setRight(id){
        window.location.href="/manage/role/rolerightset?role_id="+id;
    }

</script>

</body>
</html>