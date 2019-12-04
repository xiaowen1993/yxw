<#--
 *
 * 1、通用的记录查询头 - 门诊缴费、挂号记录
 * 2、显示包括用户信息、医院以
 * 3、同步获取以上信息，并初始化（功能下医院信息+用户关联的家人信息，不再获取就诊卡），即跳转时候拿到
 * 4、家人，默认选择第一个。（和以前不同，以前有记录最后的一个，现在先不做了）
 * 流程如下： a.切换医院或家人时，异步获取其就诊卡信息 - 缓存拿好了。
 * 		   b.只有在获取到有就诊卡信息时，才去调用页面提供的loadData方法
 * 		   c.如果没有就诊卡信息，则提示关联卡等等等等...
 *	
 * 要求如下： 1、页面按照样式 
 *		   3、提供moduleName - 页面显示使用
 * 		   4、提供loadData方法，调用查询（异步结束后，都需要更改commonTips的显示）
-->
<div style="height: 50px;"></div>
<div class="screeningBox">
    <ul class="yx-list">
    	<li class="flex">
            <div class="flexItem">
            	<label>
            		<span class="text" id="hospital" data-id="0">请选择医院</span>
            		<select id="hospitalFilter" class="select-screen-box">
            			<option value="0">请选择医院</option>
            			<#list hospitals as hospital>
        				<option value="${hospital.hospitalCode}">${hospital.hospitalName}</option>
            			</#list>
            		</select>
            		<i class="iconfont">&#xe66d;</i>
            	</label>
            </div>
            <div class="flexItem">
            	<label>
            		<span class="text" id="date" data-id="0">全部</span>
            		<select id="dateFilter" class="select-screen-box">
            			<option value="0">全部日期</option>
                        <option value="1">今天</option>
                        <option value="2">近3天</option>
                        <option value="3">近7天</option>
                        <option value="4">近30天</option>
            		</select>
            		<i class="iconfont">&#xe66d;</i>
            	</label>
            </div>
        </li>
	</ul>
</div>

<script src="${basePath}yxw.app/js/biz/common/app.commonRecordQuery.js" type="text/javascript"></script>