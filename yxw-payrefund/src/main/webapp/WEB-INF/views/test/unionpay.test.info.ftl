<html>
<head>
	<#include "/common/common.ftl">
	<title>银联钱包支付-测试-INFO</title>
</head>
<body>
	<div id="body">
	
		<div class="box-list">
            <ul class="yx-list flex">
            	<li>
                    <div class="label">名称</div>
                    <div class="values color666">测试</div>
                </li>
            	<li>
                    <div class="label">价格</div>
                    <div class="values color666">
                        <span class="price">0.01</span>
                    </div>
                </li>
            </ul>
        </div>
	</div>
	
	<script>
		//支付信息页面和支付页面不在统一域名，iframe 没法做到自适应
		//如果在一级域名相同，可以把 document.domain 都设置成一级域名，就可以骗过浏览器，从而达到 iframe 自适应的效果
		if ("${basePath}".indexOf("yx129.cn") != -1) {
			document.domain = "yx129.cn";
		}
	</script>
	
</body>
</html>