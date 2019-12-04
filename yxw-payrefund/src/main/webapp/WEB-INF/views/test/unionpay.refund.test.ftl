<html>
<head>
	<#include "/common/common.ftl">
	<title>银联钱包-退费-测试</title>
	
</head>
<body>
	<div id="body">
	
		<form id="myForm" name="myForm" method="post">
		<div class="box-list">
            <ul class="yx-list flex">
            	<li>
                    <div class="label">医院代码</div>
                    <div class="values color666"><input type="text" name="hospitalCode" value="${params.hospitalCode}"></div>
                </li>
            	<li>
                    <div class="label">银联订单号</div>
                    <div class="values color666"><input type="text" name="agtOrderNo" value="${params.agtOrderNo}"></div>
                </li>
            	<li>
                    <div class="label">订单号</div>
                    <div class="values color666"><input type="text" name="orderNo" value="${params.orderNo}"></div>
                </li>
            	<li>
                    <div class="label">订单金额（分）</div>
                    <div class="values color666"><input type="text" name="totalFee" value="${params.totalFee}"></div>
                </li>
            	<li>
                    <div class="label">退费金额（分）</div>
                    <div class="values color666"><input type="text" name="refundFee" value="${params.refundFee}"></div>
                </li>
            	<li>
                    <div class="label">退费描述</div>
                    <div class="values color666"><input type="text" name="refundDesc" value="就是要退"></div>
                </li>
            </ul>
        </div>
        </form>
		
		<div class="section btn-w">
	       <div id="refundBtn" class="btn btn-ok btn-block"">退费</div>
	    </div>
	    
	    
		<table align="center" width="98%" border="0"><tr><td>
		<textarea style="width: 100%; min-height: 200px; border: 0px; background:transparent; border-style:none;"></textarea>
		</td></tr></table>
	    
	
	</div>
	
	<#include "/common/copyright_footer.ftl">
	
	<script type="text/javascript" src="${basePath}payrefund/js/biz/test/unionpay.refund.test.js"></script>
</body>
</html>