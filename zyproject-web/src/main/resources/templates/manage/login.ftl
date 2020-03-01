
<!DOCTYPE html>
<html lang="en" class="no-js">

<head>

    <meta charset="utf-8">
    <title>用户登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- CSS -->
    <link rel="stylesheet" href="/manage/login/css/reset.css">
    <link rel="stylesheet" href="/manage/login/css/supersized.css">
    <link rel="stylesheet" href="/manage/login/css/style.css">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

</head>

<body>

<div class="page-container">
    <h1>系统登录</h1>
    <form action="./login-submit" method="post">
        <input type="text" name="username" class="username" placeholder="用户名">
        <input type="password" name="password" class="password" placeholder="密码">
        <button type="submit">登录</button>
        <div class="error"><span>+</span></div>
    </form>
    <div class="connect">
        为了更好的体验效果，推荐使用火狐、Chrome等浏览器进行操作
    </div>
</div>
<!-- Javascript -->
<script type="text/javascript">
    var resDir = "/manage/login/";
</script>
<script src="/manage/login/js/jquery-1.8.2.min.js"></script>
<script src="/manage/login/js/supersized.3.2.7.min.js"></script>
<script src="/manage/login/js/supersized-init.js"></script>
<script src="/manage/login/js/scripts.js"></script>

</body>

</html>

