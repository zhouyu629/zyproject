<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${tree.tree_name}</title>
    <script type="text/javascript"></script>
    <link href="/manage/css/bootstrap.min.css" rel="stylesheet">
    <link href="/manage/css/styles.css" rel="stylesheet">
    <link href="static/manage/css/bootstrap-select/bootstrap-select.css?v=2">

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
                            <button name="btnBack" onclick="history.back()" type="button" class="btn btn-default btn-sm">
                                <span class="glyphicon glyphicon-backward" aria-hidden="true"></span>
                                返回</button>
                    </span>
                </div>
                <div class="panel-body">
                    <form role="form-inline" class="form-horizontal" id="form1" action="" method="post">
                        <div class="from-group row top-m-5">
                            <label class="control-label col-sm-3" for="user_id">文章标题</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="article_title" id="article_title" placeholder="请输入文章标题" value="${article.article_title}" />
                                <input type="hidden" name="article_id" id="article_id" value="${article.article_id}" />
                            </div>
                        </div>
                        <div class="from-group row top-m-5">
                            <label class="control-label col-sm-3" for="user_id">文章分类</label>
                            <div class="col-sm-9">
                                <select name="article_kind_id" id="article_kind_id" class="selectpicker form-control" title="请选择分类">
                                    <#list kinds as ks>
                                        <#if ks.sup_article_kind_id == 0>
                                            <optgroup label="${ks.article_kind_name}">
                                                <#list kinds as ks2>
                                                    <#if ks2.sup_article_kind_id=ks.article_kind_id>
                                                        <option value="${ks2.article_kind_id}">${ks2.article_kind_name}</option>
                                                    </#if>
                                                </#list>
                                            </optgroup>
                                        </#if>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="from-group row top-m-5">
                            <label class="control-label col-sm-3" for="user_id">文章内容</label>
                        </div>
                        <div class="from-group row top-m-5">
                            <div class="col-sm-12">
                                <!--style给定宽度可以影响编辑器的最终宽度-->
                                <div type="text/plain" id="article_content" style="width:100%;height:200px;">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer center-align">
                    <a href="javascript:submit();" class="btn btn-primary">
                        <span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span> 提交
                    </a>
                    <a href="javascript:history.back();" class="btn btn-warning">
                        <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span> 取消
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="/manage/js/jquery.min.js"></script>
<script src="/manage/js/bootstrap.min.js"></script>
<script src="/manage/js/bootstrap-select/bootstrap-select.min.js?v=2"></script>
<script src="/manage/js/bootstrap-select/i18n/defaults-zh_CN.js"></script>
<script type="text/javascript" src="/manage/js/jquery.validate/core.js"></script>
<script type="text/javascript" charset="utf-8" src="/ueditor1_4_3_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/ueditor1_4_3_3/ueditor.all.min.js"></script>
<script type="text/javascript" src="/ueditor1_4_3_3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
    $(function () {
        //如果是编辑，默认选中select
        if('${article.article_id}'!=''){
            $("#article_kind_id").val([${article.article_kind_id}]);
            $("#article_kind_id").selectpicker('refresh');
        }
        //ueditor初始化
        var um = UE.getEditor('article_content',{
            textarea:"article_content"
        });
        um.ready(function(){
            um.setContent('${article.article_content}');
        });

        myValidate =  $("#form1").validate({
            submitHandler: function(form){
                //向服务器提交表单
                $.ajax({
                    url:"/manage/article/add-submit",
                    type:"POST",
                    dataType:"json",
                    data:$("#form1").serialize(),
                    success:function(res){
                        if(res.code == 1000){
                            alert("操作成功");
                            window.location.href = "/manage/article/index?tree_id=${tree_id}";
                        }else{
                            alert(res.msg+"/"+res.data);
                        }
                    }
                });
            },
            focusInvalid : true,
            rules:{
                article_title: {required:true},
                article_kind_id: {required:true},
                article_content: {required:true}
            },
            messages:{
                article_title: {required: "文章标题不能为空！"},
                article_kind_id: {required:"文章分类不能为空"},
                article_content: {required: "文章内容不能为空"}
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
    //提交
    function submit(){
        $("#form1").submit();
    }
</script>
</html>