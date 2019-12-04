<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
<head>
<base href="<%=basePath%>">
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>

<title>Plupload - jQuery UI Widget</title>

<link rel="stylesheet" href="js/plupload-2.1.2/js/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="js/plupload-2.1.2/js/jquery.ui.plupload/css/jquery.ui.plupload.css" type="text/css" />

<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/plupload-2.1.2/js/jquery-ui.min.js"></script>

<!-- production -->
<script type="text/javascript" src="js/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="js/plupload-2.1.2/js/jquery.ui.plupload/jquery.ui.plupload.js"></script>
<script type="text/javascript" src="js/plupload-2.1.2/js/i18n/zh_CN.js"></script>
<script type="text/javascript" src="js/attach/sys.attach.js"></script>

</head>
<body style="font: 13px Verdana; background: #eee; color: #333">

<h1>文件上传</h1>

<p>
        <button id="browse">选择文件</button>
        <button id="start_upload">开始上传</button>
    </p>

<script type="text/javascript">
	var basePath = '<%=basePath%>';
	$attach.basePath = basePath;
	//$attach.start_upload = 'browse';
	//$attach.params = "123";
	
	$attach.create();
	$attach.uploader.start();
	if($attach.uploader != null){
		$attach.uploader.bind('UploadComplete',function(uploader,file){
			alert($attach.data.status + "--" + $attach.data.attachId);
	    });
	}
</script>
</body>
</html>

