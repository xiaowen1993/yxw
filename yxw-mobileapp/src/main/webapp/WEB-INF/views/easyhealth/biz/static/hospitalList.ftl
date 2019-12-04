<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>云医院</title>
</head>
<body>
<div id="body">
    <div id="Hospital-list">
    	<div class="search-nav">
            <ul class="search-nav-ul">
                <li onclick="go('${basePath}easyhealth/search/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&searchType=hospital',1);" class="icon-hospital">搜医院</li>
                <li onclick="go('${basePath}easyhealth/search/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&searchType=dept',true);" class="icon-kesi">搜科室</li>
                <li onclick="go('${basePath}easyhealth/search/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&searchType=doctor',true);" class="icon-doctor">搜医生</li>
                <!-- <li onclick="go('${basePath}easyhealth/building',true);" class="icon-zhengzhuang">搜症状</li> -->
            </ul>
        </div>
        <div class="space15"></div>
        <#if hospitals?exists>
        	<#if hospitals?size &gt; 0>
    			<ul class="hospital-list">
        		<#list hospitals as hospital>
        				<li class="arrow" onclick="go('${hospital.cloudURL}')">
			                <div class="pic">
			                    <img src="${basePath}${hospital.logo}"  width="60"  height="60" />
			                </div>
			                <div class="info">
			                    <div class="title skinColor fontSize120">${hospital.name}</div>
			                    <div class="des">
				                 <!--   <#if hospital.branchHospitals?exists>
					                    <#if hospital.branchHospitals?size &gt; 0>
					                    	${hospital.branchHospitals[0].address}
					                    </#if>
				                    </#if>-->
			                    </div>
			                </div>
			            </li>
        		</#list>
    			</ul>
        	</#if>
        </#if>
        
        
        <!--<ul class="hospital-list">
            <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=708')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_03.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市第二人民医院</div>
                    <div class="des">深圳市福田区笋岗西路3002号</div>
                </div>
            </li>
            <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=709')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_27.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市第三人民医院</div>
                    <div class="des">龙岗区布澜路29号</div>
                </div>
            </li>
            <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=748')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_05.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市人民医院</div>
                    <div class="des">深圳市罗湖区东门北路1017号大院</div>
                </div>
            </li>
            
            <!--<li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=711')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_07.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">北京大学深圳医院</div>
                    <div class="des">深圳市福田区莲花路1120号</div>
                </div>
            </li>-->
           <!-- <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=527')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_09.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">香港大学深圳医院</div>
                    <div class="des">深圳市福田区海园一路1号</div>
                </div>
            </li>
            <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=832')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_23.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市中医院</div>
                    <div class="des">深圳市福华路1号/罗湖区解放路3015号/罗湖区迎春路15号</div>
                </div>
            </li>
           <!-- 
           <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=717')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_24.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市妇幼保健院</div>
                    <div class="des">福田区红荔路2004号、福强路3012号</div>
                </div>
            </li>-->
        <!--    <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=530')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_26.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市孙逸仙心血管医院</div>
                    <div class="des">罗湖区东门北路1021号</div>
                </div>
            </li>-->

          <!--  <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=710')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_35.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市眼科医院</div>
                    <div class="des">福田区泽田路18号</div>
                </div>
            </li>
            <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=713')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_32.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市儿童医院</div>
                    <div class="des">深圳市福田区益田路7019号</div>
                </div>
            </li>-->
          <!--  <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=534')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/logo/logo_34.png"  width="60"  height="60" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">深圳市康宁医院</div>
                    <div class="des">罗湖区翠竹路1080号</div>
                </div>
            </li>-->
    </div>
</div>
<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script src="${basePath}yxw.app/js/biz/static/eh.cloudHospital.js" type="text/javascript"></script>