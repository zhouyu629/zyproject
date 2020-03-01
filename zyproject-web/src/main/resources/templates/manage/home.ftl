<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>{$Think.session.site.site_name}</title>

    <link href="/manage/css/bootstrap.min.css" rel="stylesheet">
    <link href="/manage/css/datepicker3.css" rel="stylesheet">
    <link href="/manage/css/styles.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <script src="/manage/js/html5shiv.js"></script>
    <script src="/manage/js/respond.min.js"></script>
    <![endif]-->

</head>

<body>


<div class="col-sm-12 col-lg-12 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#"><span class="glyphicon glyphicon-home"></span></a></li>
            <li class="active">首页</li>
        </ol>
    </div><!--/.row-->

    <div class="alert bg-success" role="alert">
        <span class="glyphicon glyphicon-check"></span> 欢迎你：${user.user_name} </a>
    </div>

    <div class="row">
        <div class="col-xs-12 col-md-4 col-lg-3">
            <div class="panel panel-blue panel-widget ">
                <div class="row no-padding">
                    <div class="col-sm-3 col-lg-5 widget-left">
                        <em class="glyphicon glyphicon-shopping-cart glyphicon-l"></em>
                    </div>
                    <div class="col-sm-9 col-lg-7 widget-right">
                        <div class="large">10000</div>
                        <div class="text-muted">当日销售额</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-12 col-md-4 col-lg-3">
            <div class="panel panel-orange panel-widget">
                <div class="row no-padding">
                    <div class="col-sm-3 col-lg-5 widget-left">
                        <em class="glyphicon glyphicon-comment glyphicon-l"></em>
                    </div>
                    <div class="col-sm-9 col-lg-7 widget-right">
                        <div class="large">50万</div>
                        <div class="text-muted">会员数</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-12 col-md-4 col-lg-3">
            <div class="panel panel-red panel-widget">
                <div class="row no-padding">
                    <div class="col-sm-3 col-lg-5 widget-left">
                        <em class="glyphicon glyphicon-stats glyphicon-l"></em>
                    </div>
                    <div class="col-sm-9 col-lg-7 widget-right">
                        <div class="large">15亿</div>
                        <div class="text-muted">日元PV</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">管他什么趋势图</div>
                <div class="panel-body">
                    <div id="charts" class="chart"></div>
                </div>
            </div>
        </div>
    </div><!--/.row-->

</div>	<!--/.main-->

<script src="/manage/js/jquery-1.11.1.min.js"></script>
<script src="/manage/js/bootstrap.min.js"></script>
<script src="/manage/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="/manage/js/echarts/echarts.min.js"></script>
<script>
    !function ($) {
        var myChart = echarts.init(document.getElementById('charts'));
        myChart.showLoading("正在加载数据");
        $.ajax({
            url:"/manage/getecharts-data",
            dataType:"json",
            type:"post",
            success:function(res){
                myChart.hideLoading();
                myChart.setOption(res.data);
            }
        });
    }(window.jQuery);

    //打开新窗口，查看统计明细
    function openWin(type){

    }

    //初始化图表
    function initCharts(type){

    }
</script>
</body>

</html>
