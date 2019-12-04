<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>OPENID MANAGER</title>

<style type="text/css">
/* #main { display: none; } */
#main input {
	width: 90%;
	height: 30px;
	margin: 3px;
}

#main p {
	text-align: left;
}

#main textarea {
	width: 90%;
	height: 60px;
	border: 0px;
}
</style>
</head>
<body>
	<center>
		<div id="main">
			<form action="${pageContext.request.contextPath}/openidmanager/setting?appId=${appId}" method="post">
				<p>AppId : ${appId}</p>
				<br />
				<p>OpenId :</p>
				<textarea name="openId">${openId}</textarea>
				<br />
				<br />
				<input type="submit" value="设置OPENID" />
			</form>
		</div>

	</center>
</body>
</html>