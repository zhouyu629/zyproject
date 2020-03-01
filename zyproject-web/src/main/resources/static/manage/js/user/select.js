$(function(){
    var j_all_area = $("#sel_selected_roles"), j_selected_areas = $("#sel_unselected_roles");
    /*1.选中单击去右边*/
    $("#btn_onetoright").click(function(){
        $("#sel_unselected_roles option:selected").appendTo($("#sel_selected_roles"));
    });

    /*2.单击全部去右边*/
    $("#btn_alltoright").click(function(){
        $("#sel_unselected_roles option").appendTo($("#sel_selected_roles"));
    });

    /*3.选中双击去右边*/
    $("#sel_selected_roles").dblclick(function(){
        $("#sel_unselected_roles option:selected").appendTo($("#sel_selected_roles"));
    });


    /*4.选中单击去左边*/
    $("#btn_onetoleft").click(function(){
        $("#sel_selected_roles option:selected").appendTo($("#sel_unselected_roles"));
    });

    /*2.单击全部去右边*/
    $("#btn_alltoleft").click(function(){
        $("#sel_selected_roles option").appendTo($("#sel_unselected_roles"));
    });

    /*3.选中双击去右边*/
    $("#sel_unselected_roles").dblclick(function(){
        $("#sel_selected_roles option:selected").appendTo($("#sel_unselected_roles"));
    });

})