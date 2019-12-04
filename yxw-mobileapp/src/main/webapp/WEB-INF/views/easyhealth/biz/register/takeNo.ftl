<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>扫码报到</title>
</head>
<body>
	<div id="body">
		<input type="hidden" id="hospitalCode" value="${hospitalCode}">
		<input type="hidden" id="branchCode" value="${branchCode}">
		<input type="hidden" id="patCardType" value="${patCardType}">
		<input type="hidden" id="patCardNo" value="${patCardNo}">
	
    <div class="scanList">
        <div class="scanTips takeBefore">
            <i class="iconfont">&#xe619;</i>
            <p>扫码成功，正在连接医院系统，为您自动报到</p>
        </div>
        <div class="scanTips takeAfter" style="display: none;">
            <i class="iconfont">&#xe619;</i>
            <p>扫码报到成功，您的前面还有<span class="green fontSize120 frontNum">-</span>个人</p>
        </div>
        <div class="space15"></div>
        <ul class="yx-list" style="display: none;">
            <li class="flex">
                <div>当前正在就诊</div>
                <div class="flexItem color666 textRight"><span class="green fontSize120 currentNum">-</span>号</div>
            </li>
            <li class="flex">
                <div>您的排队号</div>
                <div class="flexItem color666 textRight"><span class="green fontSize120 serialNum">-</span>号</div>
            </li>
            <li class="flex">
                <div class="label">科室</div>
                <div class="flexItem color666 textRight deptName">-</div>
            </li>
            <li class="flex">
                <div class="label">医生</div>
                <div class="flexItem color666 textRight doctorName">-</div>
            </li>
            <li class="flex">
                <div class="label">就诊地址</div>
                <div class="flexItem color666 textRight deptLocation">-</div>
            </li>
            <li class="flex">
                <div class="label">预计就诊时间</div>
                <div class="flexItem color666 textRight"><span class="visitTime">-</span><br/><span class="color999 fontSize100">建议提前30分钟到科室候诊</span></div>
            </li>

        </ul>
    </div>
    
    <!--扫码失败 str-->

    <div id="success" style="display: none;">
        <div class="noticeEmpty"></div>
        <div class="p color666 errorMessage" style="width: 100%; text-algin:center;">抱歉，扫码报到失败，请到窗口报到</div>
    </div>
    <!--扫码失败 end-->
    
	</div>
	
	<script type="text/javascript">
			function doGoBack() {
				if(IS.isMacOS){
          try
          {
            window.appCloseView(false);
          } catch (e) {
            //  alert('IOS的方法出错');
          }
          
        }else if(IS.isAndroid){
          try
          {
            window.yx129.appCloseView(false);
          } catch (e) {
            //   alert('Android的方法出错');
          }
          
        }else{
          go(appPath + 'easyhealth/index');
        }
			}
			
			function doRefresh() {
					
			}
		
			var loadTakeNoResult = {
		      type : 'POST',  
		      url  : appPath + "easyhealth/register/scanTake/takeNo",  
		      dataType : 'json',
		      timeout  : 120000
		  };
		  
		  loadTakeNoResult.data = {};
		  loadTakeNoResult.data.hospitalCode = $("#hospitalCode").val();
		  loadTakeNoResult.data.branchCode = $("#branchCode").val();
		  loadTakeNoResult.data.patCardType = $("#patCardType").val();
		  loadTakeNoResult.data.patCardNo = $("#patCardNo").val();
			
			loadTakeNoResult.success = function(data) {
          if(data.status == 'OK'){
          		$(".takeBefore").hide();
          		$(".takeAfter").show();
          		$(".yx-list").show();
          
							$(".frontNum").text(data.takeNo.frontNum);
							if (data.takeNo.currentNum) {
								$(".currentNum").text(data.takeNo.currentNum);
							} else {
								$(".currentNum").parent().text("--");
							}
							$(".serialNum").text(data.takeNo.serialNum);
							$(".deptName").text(data.takeNo.deptName);
							$(".doctorName").text(data.takeNo.doctorName);
							$(".deptLocation").text(data.takeNo.deptLocation);
							$(".visitTime").text(data.takeNo.visitTime);
          }else{
          		$(".takeBefore").hide();
          		
          		var message = data.message;
          		if (!message) {
	          			message =  "抱歉，扫码报到失败，请到窗口报到";
          		}
          		
              $(".errorMessage").text(message);
              $("#success").show();
          }
          
          $Y.loading.hide();
       }
			
			loadTakeNoResult.error = function(data) {
					$Y.loading.hide();
					$(".takeBefore").hide();
					$(".errorMessage").text("网络异常,请保持您的网络通畅,稍后再试.");
          $("#success").show();
       }
		
			$(function() {
				//$Y.tips("功能正在建设中...", 86400000);
				//$Y.tips("数据加载中...", 15000);
				$Y.loading.show("报到处理中,请稍后...");
				$.ajax(loadTakeNoResult);
			});
	</script>
</body>
</html>
