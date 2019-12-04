<html>
<head>
	<#include "/common/common.ftl">
	<title>银联钱包支付-测试</title>
</head>
<body>
	<div id="body">
	
		<form id="myForm" name="myForm" action="${basePath}pay/unionpay" method="post">
			<input type="hidden" name="hospitalCode" value="zsdxdsfsyy"/>
			<input type="hidden" name="tradeMode" value="23"/>
			<input type="hidden" name="orderNo" value=""/>
			<#--商品价格（分） ≥0 -->
			<input type="hidden" name="totalFee" value="${totalFee}"/>
			<#--商品描述-->
			<input type="hidden" name="body" value="测试"/>
			<#--支付成功跳转页面-->
			<input type="hidden" name="paySuccessPageUrl" value="${basePath}pay/test/success"/>
			<#--支付显示页面-->
			<input type="hidden" name="infoUrl" value="${basePath}pay/test/jsonpServer"/>
			<#--支付机构系统订单超时时间（秒），传空为永不超时或者支付机构所支持的最大超时时间-->
			<input type="hidden" name="agtTimeout" value="900"/>
			<#--支付超时剩余时间（秒）-->
			<input type="hidden" name="timeout" value="900"/>
			<input type="hidden" name="viewType" value="jsonp"/>
  		</form>
	
		<div class="box-list">
            <ul class="yx-list flex">
            	<li>
                    <div class="label">订单号</div>
                    <div class="values color666"><input type="text" id="i_orderNo" value="${orderNo}"></div>
                </li>
            	<li>
                    <div class="label">名称</div>
                    <div class="values color666"><input type="text" id="i_body" value="测试"></div>
                </li>
            	<li>
                    <div class="label">价格</div>
                    <div class="values color666">
                        <span class="price">${showTotalFee}</span>
                    </div>
                </li>
            </ul>
        </div>


		<div class="section btn-w">
	       <div id="payBtn" class="btn btn-ok btn-block" onclick="testPay();">确定支付</div>
	    </div>
	
	</div>
	
	<#include "/common/copyright_footer.ftl">
	
	<script>
		function testPay() {
			var inputs = $("input[id^='i_']");
			for(var i = 0; i < inputs.length; i++) {
				var id = $(inputs[i]).attr("id");
				var value = $(inputs[i]).val();
				
				console.log(id + ": " + value);
				var replaceId = id.replace("i_", "");
				console.log(replaceId);
				$("#myForm [name='"+replaceId + "']").val(value);
			}
		
			$('#myForm').submit()
		}
	</script>
	
</body>
</html>