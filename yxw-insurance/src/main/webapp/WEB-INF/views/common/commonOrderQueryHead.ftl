<#--
 *
 * 通用就诊人和医院查询就诊记录
 *
<#-- <div style="height: 50px;"></div> -->
<div class="screeningBox" style="position:static;">
    <ul class="yx-list">
    	<li class="flex">
    	
    	
    	<div class="flexItem">
            	<label>
            		<span class="text" id="userName" data-id="0">请选择就诊人</span>
            		<select id="userNameFilter" class="select-screen-box">
            		  
            		  <#if cards?? && (cards?size > 0) >
 				      <#list cards as c>
 				      	  <option value="${c.cardNo}">${c.name}</option>
 				      </#list>
 				      <#else> <option value="">请选择就诊人</option>
 				      </#if>
            		</select>
            		<i class="iconfont">&#xe66d;</i>
            	</label>
            </div>
    	
            <div class="flexItem">
            	<label>
            		<span class="text" id="hospital" data-id="0">请选择医院</span>
            		<select id="hospitalFilter" class="select-screen-box">
            		    
            		    <#if hospitals?? && (hospitals?size > 0) >
	 				      <#list hospitals as h>
	 				      	  <option value="${h.hospitalCode}">${h.hospitalName}</option>
	 				      </#list>
	 				      <#else><option value="">请选择医院</option>
	 				      </#if>
            		</select>
            		<i class="iconfont">&#xe66d;</i>
            	</label>
            </div>
            
        </li>
	</ul>
</div>

<script src="${basePath}yxw.app/js/biz/common/app.commonOrderQuery.js" type="text/javascript"></script>