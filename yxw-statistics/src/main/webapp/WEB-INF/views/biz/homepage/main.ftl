<#include "./common/common.ftl">
<head>
    <title>掌上医院全流程管理后台</title>
</head>

<frameset cols="230px,*" frameborder="no" border="0" framespacing="0">
	  <frameset rows="120px,*"  frameborder="no" border="0" framespacing="0">
	       <frame src="${basePath}sys/main/head" scrolling="No" noresize="noresize" id="leftTopFrame">
	       <frame src="${basePath}sys/main/menu" scrolling="Yes" name="leftBottomFrame" id="leftBottomFrame">
	  </frameset>
	  <frameset>
	  	<frame src="${basePath}sys/hospital/list?bizType=1" name="rightBottomFrame" id="rightBottomFrame">
	  </frameset>
</frameset>

<script>

</script>
</html>