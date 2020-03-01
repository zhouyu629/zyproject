
<!doctype html>
<html lang="en-US">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>500 - 对不起，服务器内部错误！</title>
    <link rel="stylesheet" type="text/css" href="css/error.css">
</head>

<body>
<div id="wrapper">
    <a class="logo" href="/"></a>
    <div id="main">
        <div id="header">
            <h1><span class="icon">!</span>500<span class="sub">出错啦</span></h1>
        </div>
        <div id="content">
            <h2>出错啦！</h2>
            <p>当您看到这个页面,表示遇到了错误,无法执行您的请求,具体错误原因为：
                <p class="erro-desc">
                    ${errorDesc}
                </p>
            </p>
            <div class="utilities">
                <a class="button right" href="#" onClick="history.go(-1);return true;">返回...</a>
                <a class="button right" href="/manage/login">重新登录</a>
                <div class="clear"></div>
            </div>
        </div>

    </div>
</div>
</html>