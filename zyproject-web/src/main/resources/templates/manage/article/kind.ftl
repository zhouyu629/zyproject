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

<!--修改模态框-->
<div id="modify" class="modal fade" aria-hidden='true'>
    <div class="modal-dialog" style="width:800px;">
        <div class="modal-content">
            <div class="modal-header">
                <a class="close" data-dismiss="modal">×</a>
                <h4>文章分类信息</h4>
            </div>
            <div class="modal-body">
                <form article_kind="form-inline" id="form1" action="submitAreaManage" method="post">
                    <table class="table table-bordered table-hover">
                        <tr>
                            <th>上级分类</th>
                            <td><span id="sup_name"></span><input type="hidden" id="sup_article_kind_id" name="sup_article_kind_id"></td>
                        </tr>
                        <tr>
                            <th>文章分类名称</th>
                            <td>
                                <input type="text" class="form-control" name="article_kind_name" id="article_kind_name" placeholder="请输入文章分类名称" />
                                <input type="hidden" name="article_kind_id" id="article_kind_id" />
                            </td>

                        </tr>
                        <tr>
                            <th>文章分类排序</th>
                            <td>
                                <input type="number" class="form-control" name="taxis" id="taxis" placeholder="请输入文章分类排序" />
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
                    <li class="active">${tree.tree_name}/${tree_id}/${tree.tree_id}</li>
                </ol>
            </div><!--/.row-->
            <div class="panel panel-default">
                <div class="panel-heading">${tree.tree_name}
                    <span class="float-right">
                        <@perm tree_id="${tree_id}" ptype="A">
                            <button name="btnAdd" onclick="edit('',0,'顶级分类')" type="button" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                新增文章分类</button>
                        </@perm>
                    </span>
                </div>
                <div class="panel-body">
                    <form article_kind="form-inline" id="f" action="submitAreaManage" method="post">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>文章分类名称</th>
                                <th>文章分类排序</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list kindlist.data as article_kinds>
                                <#if article_kinds.sup_article_kind_id = 0>
                                <tr>
                                    <td>${article_kinds.article_kind_name}</td>
                                    <td>${article_kinds.taxis}</td>
                                    <td>
                                        <@perm tree_id="${tree_id}" ptype="M">
                                            <button name="btnModify" onclick="edit(${article_kinds.article_kind_id},0,'')" type="button" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</button>
                                        </@perm>
                                        <@perm tree_id="${tree_id}" ptype="D">
                                            <button name="btnDel" type="button" onclick="del(${article_kinds.article_kind_id})" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</button>
                                        </@perm>
                                        <@perm tree_id="${tree_id}" ptype="C">
                                            <button name="btnChild" type="button" onclick="edit(0,${article_kinds.article_kind_id},'${article_kinds.article_kind_name}')" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>添加二级分类</button>
                                        </@perm>
                                    </td>
                                </tr>
                                </#if>
                            <#--二级分类-->
                                <#assign num = 1>
                                <#list kindlist.data as skind>
                                    <#if skind.sup_article_kind_id==article_kinds.article_kind_id>
                                    <#--如果是第一条，画表头-->
                                        <#if num == 1 >
                                    <tr><td colspan="3">
                                            <table class="table table-bordered table-hover">
                                                <tr><th>分类名称</th><th>排序</th><th>操作</th></tr>
                                        </#if>
                                            <#--二级分类表格行-->
                                                <tr>
                                                    <td>${skind.article_kind_name}</td>
                                                    <td>${skind.taxis}</td>
                                                    <td>
                                                        <@perm tree_id="${tree_id}" ptype="M">
                                                            <button name="btnModify" onclick="edit(${skind.article_kind_id},${skind.sup_article_kind_id},'${article_kinds.article_kind_name}')" type="button" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</button>
                                                        </@perm>
                                                        <@perm tree_id="${tree_id}" ptype="D">
                                                            <button name="btnDel" type="button" onclick="del(${skind.article_kind_id})" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</button>
                                                        </@perm>
                                                    </td>
                                                </tr>
                                        <#assign num++>
                                    </#if>
                                </#list>
                                    </table>
                                </td></tr>
                            </#list>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/manage/js/jquery.min.js"></script>
<script src="/manage/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(function(){

    });

    function edit(id,supid,sup_name){
        if (id!='') {
            //编辑
            $.ajax({
                url:"/manage/article/getarticlebyid",
                type:"POST",
                dataType:"json",
                data:{article_kind_id:id},
                success:function(response){
                    if(response.code != 1000){
                        alert(response.msg);
                    }else{
                        $("#sup_article_kind_id").val(supid);
                        $("#sup_name").html(sup_name==''?'顶级分类':sup_name);
                        $("#article_kind_id").val(response.data.article_kind_id);
                        $("#article_kind_name").val(response.data.article_kind_name);
                        $("#taxis").val(response.data.taxis);
                    }
                }
            });
        }else{
            $("#form1")[0].reset();
            $("#sup_article_kind_id").val(supid);
            $("#sup_name").html(sup_name);
        }

        $('#modify').modal({
            show:true,
            backdrop:'static'
        });

    }

    //新增或修改提交
    function submit(){
        var article_kind_name = $("#article_kind_name").val();
        var taxis = $("#taxis").val();
        if(article_kind_name == ''){
            alert('文章分类名称不能为空');
            $("#article_kind_name").focus();
            return;
        }if(taxis == ''){
            alert('文章分类排序不能为空');
            $("#taxis").focus();
            return;
        }else{
            //开始提交
            $.ajax({
                url:"/manage/article/article_kind_submit",
                type:"POST",
                dataType:"json",
                data:$("#form1").serialize(),
                success:function(response){
                    if(response.code != 1000){
                        alert(response.data);
                    }else{
                        alert('操作成功');
                        window.location.reload();
                    }
                }
            });
        }
    }
    function del(id){
        if(confirm("删除文章分类删除下级分类及所有分类下发布的文章且无法恢复，确实要删除此文章分类吗？")){
            $.ajax({
                url:"/manage/article/del_article_kind",
                type:"POST",
                data:{article_kind_id:id},
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
        window.location.href="/manage/article_kind/article_kindrightset?article_kind_id="+id;
    }

</script>

</body>
</html>