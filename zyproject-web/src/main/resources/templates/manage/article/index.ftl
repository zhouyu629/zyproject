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

</head>

<body>

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
                                新增文章</button>
                        </@perm>
                    </span>
                </div>
                <div class="panel-body">
                    <form article="form-inline" id="f" action="submitAreaManage" method="post">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>文章标题</th>
                                <th>发布时间</th>
                                <th>阅读次数</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list articles.list as article>
                                <tr>
                                    <td>${article.article_title}</td>
                                    <td>${article.add_date?date('yyyy-MM-dd')}</td>
                                    <td>
                                    <@perm tree_id="${tree_id}" ptype="M">
                                        <button name="btnModify" onclick="edit(${article.article_id})" type="button" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-th" aria-hidden="true"></span>修改</button>
                                    </@perm>
                                    <@perm tree_id="${tree_id}" ptype="D">
                                        <button name="btnDel" type="button" onclick="del(${article.article_id})" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</button>
                                    </@perm>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                        <ul class="row pagination">
                            <#import "../public/page.ftl" as fpage />
                            <@fpage.fpage page=articles.page pagesize=articles.pagesize totalpages=articles.totalpages totalrecords=articles.totalrecords url="/manage/article/index?tree_id=${tree.tree_id}" />
                        </ul>
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

    function edit(id){
       window.location.href = "/manage/article/add?article_id="+id;
    }

    //新增或修改提交
    function submit(){
        
    }
    function del(id){
        if(confirm("确实要删除此文章吗？")){
            $.ajax({
                url:"/manage/article/del_article",
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

</script>

</body>
</html>