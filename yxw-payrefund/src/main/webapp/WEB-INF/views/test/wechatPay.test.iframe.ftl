<html>
<head>
	<#include "/common/common.ftl">
	<title>微信支付-测试</title>
	<script type="text/javascript" src="${basePath}payrefund/js/common/vue.min.js"></script>
</head>
<body>
	<div id="body">
	
		<form id="myForm" name="myForm" action="${basePath}pay/wechatPay" method="post">
			<input type="hidden" name="hospitalCode" value="zsdxdsfsyy"/>
			<input type="hidden" name="tradeMode" value="21"/>
			<input type="hidden" name="orderNo" v-model="orderNo"/>
			<#--商品价格（分） ≥0 -->
			<input type="hidden" name="totalFee" value="${totalFee}"/>
			<#--商品描述-->
			<input type="hidden" name="body" v-model="body"/>
			<#--支付成功跳转页面-->
			<input type="hidden" name="paySuccessPageUrl" value="${basePath}pay/test/success"/>
			<#--支付显示页面-->
			<input type="hidden" name="infoUrl" value="${basePath}pay/test/info"/>
			<#--支付机构系统订单超时时间（秒），传空为永不超时或者支付机构所支持的最大超时时间-->
			<input type="hidden" name="agtTimeout" value="900"/>
			<#--支付超时剩余时间（秒）-->
			<input type="hidden" name="timeout" value="900"/>
			<input type="hidden" name="viewType" value="iframe"/>
			
			<input type="hidden" name="openId" v-model="openId"/>
  		</form>
	
		<div class="box-list">
            <ul class="yx-list flex">
            	<li>
                    <div class="label">openId</div>
                    <div class="values color666">{{ openId }}</div>
                </li>
            	<li>
                    <div class="label">订单号</div>
                    <div class="values color666">{{ orderNo }}</div>
                </li>
            	<li>
                    <div class="label">名称</div>
                    <div class="values color666">{{ body }}</div>
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
		var app = new Vue({
		  el: '#body',
		  data: {
		    body: '测试',
		    openId: '${openId}',
		    orderNo: '${orderNo}'
		  }
		})
	
		function testPay() {
			$('#myForm').submit()
		}
	</script>
	
</body>
</html>