<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>城市宠儿数据中心登录</title>

    <!-- Bootstrap core CSS -->
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/signin.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <div class="container">

      <form class="form-signin" role="form">
        <h2 class="form-signin-heading">城市宠儿数据中心</h2>
        <input id="userName" name="userName" type="text" class="form-control" placeholder="用户名" required autofocus>
        <input id="password" name="password" type="password" class="form-control" placeholder="密码" required>
        <div class="checkbox">
          <label>
            <input name="checkbox" type="checkbox" value="remember-me"> 记住我
          </label>
        </div>
        <div id="message" class="alert alert-warning" role="alert" style="display:none;"></div>
        <button id="confirm" class="btn btn-lg btn-primary btn-block" type="button">登&nbsp;&nbsp;录</button>
      </form>

    </div> <!-- /container -->


	<script src="/js/jQuery/jquery.min.js"></script>
	<script src="/js/jQuery/jquery.cookie.js"></script>
	<script src="/js/jQuery/jquery.base64.js"></script>
	<script src="js/signin.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
   	<script src="assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
