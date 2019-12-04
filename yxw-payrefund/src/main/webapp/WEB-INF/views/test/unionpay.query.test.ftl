<html>
<head>
	<#include "/common/common.ftl">
	<title>银联钱包-查询-测试</title>
	
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
                    <div class="label">银联退费订单号</div>
                    <div class="values color666"><input type="text" name="agtRefundOrderNo" value="${params.agtRefundOrderNo}"></div>
                </li>
            	<li>
                    <div class="label">退费订单号</div>
                    <div class="values color666"><input type="text" name="refundOrderNo" value="${params.refundOrderNo}"></div>
                </li>
            </ul>
        </div>
        </form>
		
		<div class="section btn-w">
	       <div id="queryBtn" class="btn btn-ok btn-block"">查询</div>
	    </div>
	    
	    
		<table align="center" width="98%" border="0"><tr><td>
		<textarea style="width: 100%; min-height: 200px; border: 0px; background:transparent; border-style:none;"></textarea>
		</td></tr></table>
	    
	
	</div>
	
	<#include "/common/copyright_footer.ftl">
	
	<script type="text/javascript" src="${basePath}payrefund/js/biz/test/unionpay.query.test.js"></script>
</body>
</html>