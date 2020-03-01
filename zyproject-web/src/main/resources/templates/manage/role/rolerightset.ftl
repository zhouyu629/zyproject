<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title></title>
    <script type="text/javascript" src="/manage/js/jquery-1.11.1.min.js"></script>
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
    <script type="text/javascript">
        /**
         * 按钮权限选择，需要选择上级树，只有两级，不用递归
        */
        function checkbtn(obj,tree_id,parentid){
            var ischecked = $(obj).prop("checked");
            var parentnode = $("#tree_"+parentid);
            if(!ischecked) {
                /* 画蛇添足了，没有必要取消上级的选中，也就是说如果只要查看权限，可以只选择菜单而不选择按钮
                **
                //只有同一级所有按钮，全部取消了选择，父级才同时取消
                parentnode.prop("checked",$("input[parentid='tree_"+parentid+"']:checked").length > 0);
                //只有父级所有按钮全部取消了选择，爷爷级才取消选择
                $("#"+parentnode.attr("parentid")).prop("checked",$("input[parentid='"+parentnode.attr("parentid")+"']:checked").length > 0);
                */
            }else{
                console.log("set papa and grandpa checked:tree_"+parentid);
                parentnode.prop("checked",true);
                console.log("grandpa="+parentnode.attr("parentid"));
                $("#"+parentnode.attr("parentid")).prop("checked",true);
            }
        }

        /**
         * 菜单选择
         * @param obj：当前操作的checkbox
         * @param tree_id：当前操作的tree_id
         * @param level：菜单级别，只支持两级菜单
         */
        function checktree(obj,tree_id,level){
            //1.选中上级tree
            var parentid = $(obj).attr("parentid");
            var ischecked = $(obj).prop("checked");
            var checkparent = true;
            //如果是取消选择，只有所有下级都取消了，才取消上级的选中状态
            if(!ischecked){
               checkparent = $("input[parentid='"+parentid+"']:checked").length == 0?false:true;
            }
            $("#" + parentid).prop("checked", checkparent);
            //2. 选中下级tree
            $("input[parentid='tree_"+tree_id+"']").each(function(){
                var chk = $(this);
                chk.prop("checked",ischecked);
                if(level == 1){
                    //还要选择下级菜单的所有按钮
                    $("input[parentid='"+chk.attr("id")+"']").prop("checked",ischecked);
                }
            });
        }
        //全选
        function checkall(obj){
            console.log("check status："+$(obj).prop("checked"));
            $("input[type='checkbox']").prop("checked",$(obj).prop("checked"));
        }
        //表单提交
        function submit(){
            $("#form1").submit();
        }
    </script>
</head>

<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-12 col-lg-12 main">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="#"><span class="glyphicon glyphicon-home"></span></a></li>
                    <li class="active">${Session["tree"].tree_name}/权限设置</li>
                </ol>
            </div><!--/.row-->
            <div class="panel panel-default">
                <div class="panel-heading">"${role.role_name}"-${Session["tree"].tree_name}
                    <span class="float-right">
	            		<button onclick="history.back();" type="button" class="btn btn-default btn-sm">
               			<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
               			返回</button>
           			</span>
                </div>
                <div class="panel-body">
                    <form role="form-inline" id="form1" action="./rolerightset-submit" method="post">
                        <input type="hidden" name="role_id" id="role_id" value="${role.role_id}"/>
                        <table class="table table-bordered table-hover">
                            <tr><td colspan="2"><input type="checkbox" onclick="checkall(this);" />全选/取消选择</td> </tr>
                            <#list tree as t>
                                <#if t.sup_tree_id == 0>
                            <tr>
                                <td colspan='2' >
                                    <input type="checkbox" id="tree_${t.tree_id}" name="chkids" value="tree_${t.tree_id}" parentid="0" curtype="menu" onclick="checktree(this,${t.tree_id},1)"
                                     <#if t.perm!=0> checked</#if> />
                                    ${t.tree_name}
                                </td>
                            </tr>
                                    </#if>
                            <#--二级菜单-->
                                <#list tree as t2>
                                    <#if t2.sup_tree_id==t.tree_id>
                            <tr>
                                <td style="padding-left:40px">
                                    <input type="checkbox" id="tree_${t2.tree_id}" name="chkids" value="tree_${t2.tree_id}" onclick="checktree(this,${t2.tree_id},2)" parentid="tree_${t.tree_id}" curtype="menu"
                                     <#if t2.perm!=0>checked</#if> />
                                    ${t2.tree_name}
                                </td>
                                <td >
                                    <#--按钮-->
                                    <#list func as f>
                                        <#if f.tree_id==t2.tree_id>
                                    <input type="checkbox" id="btn_${f.tree_id}_${f.tree_func_name}" name="chkids" value="btn_${f.tree_id}_${f.tree_func_name}" onclick="checkbtn(this,${f.tree_id},${t2.tree_id})" parentid="tree_${t2.tree_id}" curtype="btn"
                                       <#if f.perm!=0>checked</#if>/>
                                        ${f.tree_func_namedesc}
                                        </#if>
                                    </#list>
                                </td>
                            </tr>
                                        </#if>
                                    </#list>
                            </#list>
                        </table>
                        <div class="modal-footer">
                            <a href="javascript:submit();" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span> 提交
                            </a>
                            <a href="javascript:history.back();" class="btn btn-warning">
                                <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span> 返回
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>