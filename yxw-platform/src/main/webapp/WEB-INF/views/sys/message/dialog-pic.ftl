<style type="text/css">
    .btn-upload{ margin: 0 auto;}
</style>
<!--弹窗内容-->
<div id="pic-dialog">
<div class="widget-content">
    <div class="row-fluid">
        <div class="controls">
            <span class="btn-save btn-upload w260 h48">
            	上传图片<input class="fileupload  w260 h48" type="file" id="uploadFile" name="uploadFile">
            </span>
        </div>
        <div class="space15"></div>
        <input type="hidden" name="metarialPath" id="metarialPath" value="" />
        <div class="controls center"><button type="button" class="btn btn-library w260 h48" onclick="invokePicLib();">调用素材库</button></div>
        <div class="space15"></div>
    </div>
</div>
</div>
<!--弹窗内容-->
<script>
function invokePicLib()
{
	var iTop = (window.screen.availHeight-30)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-1400)/2; //获得窗口的水平位置;
	window.open($('#basePath').val()+"message/meterial?method=choosePicList&hospitalId=${hospitalId}",
			"_blank","toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no,height="+window.screen.availHeight+", width=1400, top="+iTop+", left="+iLeft);
}
function setMetarial(path,holder)
{
	$('#metarialPath').val(path);
	$("#metarialPath").change();
}
</script>
</body>
</html>