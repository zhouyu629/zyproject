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
            data:{article_id:id},
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