
<!DOCTYPE html>
<html>
<head>
	<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> 
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
	<meta HTTP-EQUIV="expires" CONTENT="0"> 
    <#include "/easyhealth/common/common.ftl">
	
	<script type="text/javascript" src="${basePath}yxw.app/js/common/nav-list.js?v=2"></script>
    <title>社康中心</title>
    <style>
    	.yx-list li{
		    display: -webkit-box;
		    display: -ms-flexbox;
		    display: -webkit-flex;
		    display: flex;
    	}
    	.accordion .acc-li .acc-header:before{
    		//content:""!important;
    		background:none!important;
    	}
    </style>
 <script type="text/javascript">
    $(document).ready(function(){
    	 var week = getWeek(new Date());
         $("#week1").html("星期一　"+week.monday.format("{0}-{1}-{2}")+"　出诊医生");
         $("#week2").html("星期二　"+week.tuesday.format("{0}-{1}-{2}")+"　出诊医生");
         $("#week3").html("星期三　"+week.wednesday.format("{0}-{1}-{2}")+"　出诊医生");
         $("#week4").html("星期四　"+week.thursday.format("{0}-{1}-{2}")+"　出诊医生");
         $("#week5").html("星期五　"+week.friday.format("{0}-{1}-{2}")+"　出诊医生");
         $("#week6").html("星期六　"+week.saturday.format("{0}-{1}-{2}")+"　出诊医生");
         $("#week7").html("星期天　"+week.sunday.format("{0}-{1}-{2}")+"　出诊医生");
    });
	String.prototype.format = function() {
	    var vs = arguments;
	    return this.replace(/\{(\d+)\}/g, function() { return vs[parseInt(arguments[1])]; });
	};
    Date.prototype.format = function(formatString) {
        with (this) {
            return (formatString||"{0}-{1}-{2} {3}:{4}:{5}").format(
                  getFullYear()
                , ("0" + (getMonth()+1)).slice(-2)
                , ("0" + getDate()).slice(-2)
                , ("0" + getHours()).slice(-2)
                , ("0" + getMinutes()).slice(-2)
                , ("0" + getSeconds()).slice(-2)
            );
        }
    };
     
    function getWeek(theDay) {
        var monday = new Date(theDay.getTime());
        var tuesday = new Date(theDay.getTime());
        var wednesday = new Date(theDay.getTime());
        var thursday = new Date(theDay.getTime());
        var friday = new Date(theDay.getTime());
        var saturday = new Date(theDay.getTime());
        var sunday = new Date(theDay.getTime());
        if(monday.getDay()==0){
        	monday.setDate(monday.getDate()+1-monday.getDay()-7);
	        tuesday.setDate(tuesday.getDate()+2-tuesday.getDay()-7);
	        wednesday.setDate(wednesday.getDate()+3-wednesday.getDay()-7);
	        thursday.setDate(thursday.getDate()+4-thursday.getDay()-7);
	        friday.setDate(friday.getDate()+5-friday.getDay()-7);
	        saturday.setDate(saturday.getDate()+6-saturday.getDay()-7);
	        sunday.setDate(sunday.getDate()+7-sunday.getDay()-7);
        }else{
        	monday.setDate(monday.getDate()+1-monday.getDay());
	        tuesday.setDate(tuesday.getDate()+2-tuesday.getDay());
	        wednesday.setDate(wednesday.getDate()+3-wednesday.getDay());
	        thursday.setDate(thursday.getDate()+4-thursday.getDay());
	        friday.setDate(friday.getDate()+5-friday.getDay());
	        saturday.setDate(saturday.getDate()+6-saturday.getDay());
	        sunday.setDate(sunday.getDate()+7-sunday.getDay());
        }
        
        return {monday:monday, tuesday:tuesday,wednesday:wednesday,thursday:thursday,friday:friday,saturday:saturday,sunday:sunday};
    }
     
     
   
</script>
</head>
<body>

<div id="body">
	<div class="space15"></div>
    <ul class="yx-list">
        <li class="flex-wrap row-flex">
            <div class="title flexWidth5">社康</div>
            <div class="title flexWidth5 textRight color999">${communityHealthCenter.organizationNameSub }</div>
        </li>
        <li class="flex-wrap row-flex">
            <div class="title flexWidth5">电话</div>
            <div class="title flexWidth5 textRight color999">${communityHealthCenter.phoneNumber }</div>
        </li>
        <li class="flex-wrap row-flex">
            <div class="title flexWidth5">专家派出单位</div>
            <div class="title flexWidth5 textRight color999">${communityHealthCenter.hospitalName }</div>
        </li>
        <li class="flex-wrap row-flex">
            <div class="title flexWidth5">社区地址</div>
            <div class="title flexWidth5 textRight color999">${communityHealthCenter.organizationAddress }</div>
        </li>
    </ul>

    <div class="space15"></div>
    <div class="box-list fff accordion js-accordion">
	   

	    <div class="acc-li">
	        <div class="acc-header js-acc-header" id="week1">
	        	星期一　出诊专家
	        </div>
	        <ul class="acc-content">
	           <#if organizationSchmagsList1?exists>
			   <#list organizationSchmagsList1 as organizationSchmag> 
	                <li class="item flex-wrap row-flex">
	                    <div class="boxInline over">
	                        <div class="pic">
	                            <img class="circle" src="${basePath}yxw.app/images/touxiang.png" width="60" height="60"/>
	                        </div>
	                        <div class="color666"> ${organizationSchmag.doctorName}（${organizationSchmag.position}） <br/> ${organizationSchmag.specialty}</div>
	                    </div>
	                    <#if organizationSchmag.timeSlot == "2"><span class="btnLabel i">下午</span></#if>
	                    <#if organizationSchmag.timeSlot == "1"><span class="btnLabel">上午</span></#if>
	                </li>
	  		   </#list>
			   <#else>
				 <li class="item flex-wrap row-flex">
	                <div class="boxInline over">
	                   	<div class="color999">暂时没有获取到排班信息</div>
	                </div>
	             </li>
			   </#if>
	        </ul>
	    </div>
	    
	    <div class="acc-li">
	        <div class="acc-header js-acc-header" id="week2">
	        	星期二　出诊专家
	        </div>
	        <ul class="acc-content">
	           <#if organizationSchmagsList2?exists>
			   <#list organizationSchmagsList2 as organizationSchmag> 
	                <li class="item flex-wrap row-flex ">
	                    <div class="boxInline over">
	                        <div class="pic">
	                            <img class="circle" src="${basePath}yxw.app/images/touxiang.png" width="60" height="60"/>
	                        </div>
	                        <div class="color666"> ${organizationSchmag.doctorName}（${organizationSchmag.position}） <br/> ${organizationSchmag.specialty}</div>
	                    </div>
	                    <#if organizationSchmag.timeSlot == "2"><span class="btnLabel i">下午</span></#if>
	                    <#if organizationSchmag.timeSlot == "1"><span class="btnLabel">上午</span></#if>
	                </li>
	  		   </#list>
			   <#else>
				 <li class="item flex-wrap row-flex">
	                <div class="boxInline over">
	                   	<div class="color999">暂时没有获取到排班信息</div>
	                </div>
	             </li>
			   </#if>
	        </ul>
	    </div>
	    
	    <div class="acc-li">
	        <div class="acc-header js-acc-header" id="week3">
	        	星期三　出诊专家
	        </div>
	        <ul class="acc-content">
	           <#if organizationSchmagsList3?exists>
			   <#list organizationSchmagsList3 as organizationSchmag> 
	                <li class="item flex-wrap row-flex ">
	                    <div class="boxInline over">
	                        <div class="pic">
	                            <img class="circle" src="${basePath}yxw.app/images/touxiang.png" width="60" height="60"/>
	                        </div>
	                        <div class="color666"> ${organizationSchmag.doctorName}（${organizationSchmag.position}） <br/> ${organizationSchmag.specialty}</div>
	                    </div>
	                    <#if organizationSchmag.timeSlot == "2"><span class="btnLabel i">下午</span></#if>
	                    <#if organizationSchmag.timeSlot == "1"><span class="btnLabel">上午</span></#if>
	                </li>
	  		   </#list>
			   <#else>
				 <li class="item flex-wrap row-flex">
	                <div class="boxInline over">
	                   	<div class="color999">暂时没有获取到排班信息</div>
	                </div>
	             </li>
			   </#if>
	        </ul>
	    </div>
	    
	    <div class="acc-li">
	        <div class="acc-header js-acc-header" id="week4">
	        	星期四　出诊专家
	        </div>
	        <ul class="acc-content">
	           <#if organizationSchmagsList4?exists>
			   <#list organizationSchmagsList4 as organizationSchmag> 
	                <li class="item flex-wrap row-flex ">
	                    <div class="boxInline over">
	                        <div class="pic">
	                            <img class="circle" src="${basePath}yxw.app/images/touxiang.png" width="60" height="60"/>
	                        </div>
	                        <div class="color666"> ${organizationSchmag.doctorName}（${organizationSchmag.position}） <br/> ${organizationSchmag.specialty}</div>
	                    </div>
	                    <#if organizationSchmag.timeSlot == "2"><span class="btnLabel i">下午</span></#if>
	                    <#if organizationSchmag.timeSlot == "1"><span class="btnLabel">上午</span></#if>
	                </li>
	  		   </#list>
			   <#else>
				 <li class="item flex-wrap row-flex">
	                <div class="boxInline over">
	                   	<div class="color999">暂时没有获取到排班信息</div>
	                </div>
	             </li>
			   </#if>
	        </ul>
	    </div>
	    
	    <div class="acc-li">
	        <div class="acc-header js-acc-header" id="week5">
	        	星期五　出诊专家
	        </div>
	        <ul class="acc-content">
	           <#if organizationSchmagsList5?exists>
			   <#list organizationSchmagsList5 as organizationSchmag> 
	                <li class="item flex-wrap row-flex ">
	                    <div class="boxInline over">
	                        <div class="pic">
	                            <img class="circle" src="${basePath}yxw.app/images/touxiang.png" width="60" height="60"/>
	                        </div>
	                        <div class="color666"> ${organizationSchmag.doctorName}（${organizationSchmag.position}） <br/> ${organizationSchmag.specialty}</div>
	                    </div>
	                    <#if organizationSchmag.timeSlot == "2"><span class="btnLabel i">下午</span></#if>
	                    <#if organizationSchmag.timeSlot == "1"><span class="btnLabel">上午</span></#if>
	                </li>
	  		   </#list>
			   <#else>
				 <li class="item flex-wrap row-flex">
	                <div class="boxInline over">
	                   	<div class="color999">暂时没有获取到排班信息</div>
	                </div>
	             </li>
			   </#if>
	        </ul>
	    </div>
	    
	    <div class="acc-li">
	        <div class="acc-header js-acc-header" id="week6">
	        	星期六　出诊专家
	        </div>
	        <ul class="acc-content">
	           <#if organizationSchmagsList6?exists>
			   <#list organizationSchmagsList6 as organizationSchmag> 
	                <li class="item flex-wrap row-flex ">
	                    <div class="boxInline over">
	                        <div class="pic">
	                            <img class="circle" src="${basePath}yxw.app/images/touxiang.png" width="60" height="60"/>
	                        </div>
	                        <div class="color666"> ${organizationSchmag.doctorName}（${organizationSchmag.position}） <br/> ${organizationSchmag.specialty}</div>
	                    </div>
	                    <#if organizationSchmag.timeSlot == "2"><span class="btnLabel i">下午</span></#if>
	                    <#if organizationSchmag.timeSlot == "1"><span class="btnLabel">上午</span></#if>
	                </li>
	  		   </#list>
			   <#else>
				 <li class="item flex-wrap row-flex">
	                <div class="boxInline over">
	                   	<div class="color999">暂时没有获取到排班信息</div>
	                </div>
	             </li>
			   </#if>
	        </ul>
	    </div>
	    
	    <div class="acc-li">
	        <div class="acc-header js-acc-header" id="week7">
	        	星期天　出诊专家
	        </div>
	        <ul class="acc-content">
	           <#if organizationSchmagsList7?exists>
			   <#list organizationSchmagsList7 as organizationSchmag> 
	                <li class="item flex-wrap row-flex ">
	                    <div class="boxInline over">
	                        <div class="pic">
	                            <img class="circle" src="${basePath}yxw.app/images/touxiang.png" width="60" height="60"/>
	                        </div>
	                        <div class="color666"> ${organizationSchmag.doctorName}（${organizationSchmag.position}） <br/> ${organizationSchmag.specialty}</div>
	                    </div>
	                    <#if organizationSchmag.timeSlot == "2"><span class="btnLabel i">下午</span></#if>
	                    <#if organizationSchmag.timeSlot == "1"><span class="btnLabel">上午</span></#if>
	                </li>
	  		   </#list>
			   <#else>
				 <li class="item flex-wrap row-flex">
	                <div class="boxInline over">
	                   	<div class="color999">暂时没有获取到排班信息</div>
	                </div>
	             </li>
			   </#if>
	        </ul>
	    </div>
	    
            

	    
	</div>
</div>

<div id="copyright">  </div>
<form id="administrativeRegionFrom" method="post" action="${basePath}easyhealth/communitycenter/communityHealth/getCommunityHealthByAR" accept-charset="utf-8">
 <input type="hidden" id="communityId" name="communityId"/>
 <#--
 <button type="button" onclick="doRefresh()">doRefresh</button>
<button type="button" onclick="doGoBack()">doGoBack</button>
-->
</form>
<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script type="text/javascript">

function doRefresh()
{
	window.location.href="${basePath}/easyhealth/communitycenter/communityHealth/getCommunityHealthOnlyOneById?communityId=${communityId}";
}

function doGoBack()
{	
	var freshForm=$("<form></form>");
	freshForm.append($('<input type="hidden" name="administrativeRegion" value="${communityHealthCenter.administrativeRegion }"/>'));
	freshForm.appendTo("body");
	freshForm.css('display','none');
	freshForm.attr("method","post");
	freshForm.attr("action","${basePath}/easyhealth/communitycenter/communityHealth/getCommunityHealthByAR");
	freshForm.submit();
}
</script>