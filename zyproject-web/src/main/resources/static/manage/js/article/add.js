$(function () {
    //如果是编辑，默认选中select
    if("${article.article_id!''}"!=''){
        $("#article_kind_id").val([${article.article_kind_id!''}]);
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