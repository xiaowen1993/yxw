<#--
 *
 * 1、通用的业务查询头 - 门诊缴费、报告查询、候诊排队。
 * 2、显示包括用户信息、医院以
 * 3、同步获取以上信息，并初始化（功能下医院信息+用户关联的家人信息，不再获取就诊卡），即跳转时候拿到
 * 4、家人，默认选择第一个。（和以前不同，以前有记录最后的一个，现在先不做了）
 * 流程如下： a.切换医院或家人时，异步获取其就诊卡信息 - 缓存拿好了。
 * 		   b.只有在获取到有就诊卡信息时，才去调用页面提供的loadData方法
 * 		   c.如果没有就诊卡信息，则提示关联卡等等等等...
 *	
 * 要求如下： 1、页面按照样式 
 *		   2、提供callbackUrl - 关联卡后跳转回来 (跳回来的时候，带上Openid,appCode，areaCode即可。)
 *		   3、提供moduleName - 页面显示使用
 * 		   4、提供loadData方法，调用查询（异步结束后，都需要更改commonTips的显示）
-->
<div style="height: 50px;"></div>
<div class="screeningBox">
<#if families??&& families?size gt 0>
    <ul class="yx-list">
    	<li class="flex">
            <div class="flexItem">
            	<label>
            		<#assign fa=families[0]>
            		<span class="text" id="family" data-id="${fa.id}">${fa.encryptedName}(<#if fa.ownership != 3>${fa.encryptedIdNo}<#else>儿童</#if>)</span>
            		<select id="familyFilter" class="select-screen-box">
            			<#list families as family>
        				<option value="${family.id}" ownership="${family.ownership}">${family.encryptedName}(<#if family.ownership != 3>${family.encryptedIdNo}<#else>儿童</#if>)</option>
            			</#list>
            		</select>
            		<i class="iconfont">&#xe66d;</i>
            	</label>
            </div>
            <div class="flexItem">
            	<label>
            		<span class="text" id="hospital" data-id="0">请选择医院</span>
            		<select id="hospitalFilter" class="select-screen-box">
            			<option value="0">请选择医院</option>
            			<#list hospitals as hospital>
        				<option data-appId="${hospital.appId}" value="${hospital.hospitalCode}">${hospital.hospitalName}</option>
            			</#list>
            		</select>
            		<i class="iconfont">&#xe66d;</i>
            	</label>
            </div>
        </li>
	</ul>
    <#else>
    <ul class="yx-list userList">
    <li class="pic arrow" onclick="go('${basePath}easyhealth/user/toPerfectUserInfo', true)">
		            	<#if sessionUser.sex == 2>
		            		<img src="${basePath}/easyhealth/images/girl-def.png" width="60" height="60"/>
		            	<#else>
		            		<img src="${basePath}/easyhealth/images/man-def.png" width="60" height="60"/>
		            	</#if>
	                <div class="info">请先完善资料</div>
	            </li>
	             </ul>
    </#if>
    
</div>

<script src="${basePath}yxw.app/js/biz/common/app.commonBizQuery.js" type="text/javascript"></script>