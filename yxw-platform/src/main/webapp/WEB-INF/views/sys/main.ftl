<#include "./sys/common/common.ftl">
<head>
    <title>掌上医院全流程管理后台</title>
</head>
<frameset cols="230px,*"  frameborder="no" border="0" framespacing="0">
	  <frameset rows="120px,*"  frameborder="no" border="0" framespacing="0">
	       <frame src="${basePath}menu/head" scrolling="No" noresize="noresize" id="leftTopFrame">
	       <frame src="${basePath}menu/showMenu" scrolling="Yes" name="leftBottomFrame" id="leftBottomFrame">
	  </frameset>
	  <frameset>
	    <!--<frame src="${basePath}menu/setting" name="rightTopFrame" id="rightTopFrame">-->
	  	<frame src="${basePath}sys/hospital/list" name="rightBottomFrame" id="rightBottomFrame">
	  </frameset>
</frameset>
</body>

<script>
$("body").bind("keydown", 
   function(event) {
     if (event.keyCode == 116) {
          $('#rightBottomFrame').window.location.reload();      
          return false;
      } 
    }
}); 
</script>
</html>