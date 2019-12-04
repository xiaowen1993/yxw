<div id="material-dialog">

	<div class="widget-content">
	    <div class="row-fluid">
	       <div class="controls center"><button id="singleBtn" type="button" class="btn btn-save w260 h48">添加单图文消息</button></div>
	        <div class="space15"></div>
	       <div class="controls center"><button id="multiBtn" type="button" class="btn btn-save w260 h48">添加多图文消息</button></div>
	        <div class="space15"></div>
	        <input type="hidden" name="metarialId" id="metarialId" value="" />
	       <div class="controls center"><button type="button" class="btn btn-library w260 h48" onclick="invokeMeteLib();">调用素材库</button></div>
	        <div class="space15"></div>
	    </div>
	</div>

	<!--<div class="controlsBtnBox rowBg center">
	    <button class="btn-save">保存</button>
	    <div class="spaceW15"></div>
	    <button class="btn-remove">取消</button>
	</div>-->

</div>
<script>
function invokeMeteLib()
{
	var iTop = (window.screen.availHeight-30)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-1400)/2; //获得窗口的水平位置;
	window.open($('#basePath').val()+"message/mixedMeterial?method=chooseMeteList&hospitalId=${hospitalId}&thirdType=${thirdType}&type=${type}",
			"_blank","toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no,height="+window.screen.availHeight+", width=1400, top="+iTop+", left="+iLeft);
}


function setMetarial(id)
{
	$('#metarialId').val(id);
	$("#metarialId").change();
}
</script>