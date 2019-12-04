<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>交通指南</title>
</head>
<body>
<div id="body">
    <div id="Hospital-list">
        <div class="space15"></div>
        <#if hospitals?exists>
        	<#if hospitals?size &gt; 0>
        		<ul class="hospital-list">
	        		<#list hospitals as hospital>
	        			<li class="arrow" onclick="go('${hospital.trafficURL}')">
			                <div class="pic">
			                    <img src="${basePath}${hospital.logo}"  width="60"  height="60" />
			                </div>
			                <div class="info">
			                    <div class="title skinColor fontSize120">${hospital.name}</div>
			                    <div class="des">交通指南</div>
			                </div>
			            </li>
	        		</#list>
        		</ul>
        	</#if>
        </#if>
        <!--<ul class="hospital-list">
           <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=262')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_03.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市第二人民医院</div>
                    <div class="des">交通指南</div>
                </div>
            </li>
            <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=703')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_27.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市第三人民医院</div>
                    <div class="des">交通指南</div>
                </div>
            </li>
            <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=732')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_05.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市人民医院</div>
                    <div class="des">交通指南</div>
                </div>
            </li>
            
           <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=729')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_07.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">北京大学深圳医院</div>
                    <div class="des">交通指南</div>
                </div>
            </li>-->
            <!--  <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=736')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_09.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">香港大学深圳医院</div>
                    <div class="des">交通指南</div>
                </div>
            </li>
             
             <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=831')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_23.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市中医院</div>
                    <div class="des">交通指南</div>
                </div>
            </li> -->
            <!--<li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=739')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_24.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市妇幼保健院</div>
                    <div class="des">交通指南</div>
                </div>
            </li>-->
            <!--  <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=741')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_26.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">交通指南</div>
                    <div class="des">深圳市孙逸仙心血管医院</div>
                </div>
            </li>

            <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=707')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_35.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市眼科医院</div>
                    <div class="des">交通指南</div>
                </div>
            </li>
            <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=714')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_32.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市儿童医院</div>
                    <div class="des">交通指南</div>
                </div>
            </li>
            <!-- <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=674')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_34.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">交通指南</div>
                    <div class="des">深圳市康宁医院</div>
                </div>
            </li> -->
    </div>
</div>
<#include "/easyhealth/common/footer.ftl">
</body>
<script>
	function doRefresh()
	{
		window.location.href="${basePath}easyhealth/toHospitalInfo?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}";
	}
	
	function doGoBack()
	{
		 windowClose();
	}
</script>
</html>
