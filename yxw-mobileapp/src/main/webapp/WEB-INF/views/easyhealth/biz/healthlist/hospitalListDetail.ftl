<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>云医院</title>
</head>
<style type="text/css"> html,body,#body,.hospital-view{ width:100%;height: 100%;}</style>
<body>
<div id="body">
    <div class="hospital-view col-flex flex-wrap">
        <div class="topHosMenu">
            <ul class="hospital-list">
                <li>
                    <div class="pic">
                        <img src="../static/images/logo/logo_03.png"  width="60"  height="60" />
                    </div>
                    <div class="info">
                        <div class="title skinColor fontSize110">${hospital.name}</div>
                        <div class="des">${hospital.address}</div>
                    </div>
                </li>
            </ul>
            </div>
        <div class="space15"></div>
        <#list menus as menu>
	    	<ul class="HMenuList flex-wrap col-flex flexWidth1">
	            <li class="flex-wrap row-flex flexWidth3">
	                <div class="flexWidth3">
	                    <div class="item " onclick="go()">
	                        <div class="icon"><img src="../static/images/greenSkin/hospital/icon-h1.png"/> </div>
	                        <div class="label">当班挂号</div>
	                    </div>
	                </div>
	                <div class="flexWidth3">
	                    <div class="item " onclick="go()">
	                        <div class="icon"><img src="../static/images/greenSkin/hospital/icon-h2.png"/> </div>
	                        <div class="label">预约挂号</div>
	                    </div>
	                </div>
	                <div class="flexWidth3">
	                    <div class="item " onclick="go()">
	                        <div class="icon"><img src="../static/images/greenSkin/hospital/icon-h3.png"/> </div>
	                        <div class="label">门诊缴费</div>
	                    </div>
	                </div>
	            </li>
	            <li class="flex-wrap row-flex flexWidth3">
	                <div class="flexWidth3">
	                    <div class="item " onclick="go()">
	                        <div class="icon"><img src="../static/images/greenSkin/hospital/icon-h4.png"/> </div>
	                        <div class="label">候诊查询</div>
	                    </div>
	                </div>
	                <div class="flexWidth3">
	                    <div class="item " onclick="go()">
	                        <div class="icon"><img src="../static/images/greenSkin/hospital/icon-h5.png"/> </div>
	                        <div class="label">报告查询</div>
	                    </div>
	                </div>
	                <div class="flexWidth3">
	                    <div class="item " onclick="go()">
	                        <div class="icon"><img src="../static/images/greenSkin/hospital/icon-h6.png"/> </div>
	                        <div class="label">问医生</div>
	                    </div>
	                </div>
	            </li>
	            <li class="flex-wrap row-flex flexWidth3">
	                <div class="flexWidth3">
	                    <div class="item " onclick="go()">
	                        <div class="icon"><img src="../static/images/greenSkin/hospital/icon-h7.png"/> </div>
	                        <div class="label">就医指南</div>
	                    </div>
	                </div>
	                <div class="flexWidth3">
	                    <div class="item " onclick="go()">
	                        <div class="icon"><img src="../static/images/greenSkin/hospital/icon-h8.png"/> </div>
	                        <div class="label">交通指引</div>
	                    </div>
	                </div>
	                <div class="flexWidth3">
	                    <div class="item " onclick="go()">
	                        <div class="icon"><img src="../static/images/greenSkin/hospital/icon-h9.png"/> </div>
	                        <div class="label">更多</div>
	                    </div>
	                </div>
	            </li>
	        </ul>
        </#list>
    </div>

</div>
<#include "/easyhealth/common/footer.ftl">
</body>
</html>
