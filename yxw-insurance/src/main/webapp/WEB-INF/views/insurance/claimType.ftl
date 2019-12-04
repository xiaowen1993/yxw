<!DOCTYPE html>
<html>
<head>
  	<#include "/common/common.ftl">
    <title>理赔申请</title>
    
    <style type="text/css">
       .search-select:before {
       		border-bottom: none;
       }

		.search-select .search-select-input {
			margin-left: 15px;
		}
		
		.search-select .search-select-btn {
			margin-right: 10px;
		}
	</style>
	
</head>
<body>
<div id="body">
	<div class="yxw-data">
	
	<form id="voForm" method="post">
		<input type="hidden" id="openId" name="openId" value="${payDetail.openId}" />
		<input type="hidden" id="appCode" name="appCode" value="${payDetail.appCode}" />
		<input type="hidden" id="appId" name="appId" value="${payDetail.appId}" />
		<input type="hidden" id="hospitalCode" name="hospitalCode" value="${payDetail.hospitalCode}" />
		<input type="hidden" id="orderNo" name="orderNo" value="${payDetail.orderNo}" />
		<input type="hidden" id="patName" name="patName" value="${payDetail.patName}" />
		<input type="hidden" id="birthDay" name="birthDay" value="${payDetail.birthDay}" />
		<input type="hidden" id="patSex" name="patSex" value="${payDetail.patSex}" />
		<input type="hidden" id="patIdType" name="patIdType" value="${payDetail.patIdType}" />
		<input type="hidden" id="patIdNo" name="patIdNo" value="${payDetail.patIdNo}" />
		<input type="hidden" id="mzFeeId" name="mzFeeId" value="${payDetail.mzFeeId}" />
		<input type="hidden" id="time" name="time" value="${payDetail.time}" />
		<input type="hidden" id="patMobile" name="patMobile" value="${payDetail.patMobile}" />
		<input type="hidden" id="patAddress" name="patAddress" value="${payDetail.patAddress}" />
		<input type="hidden" id="deptName" name="deptName" value="${payDetail.deptName}" />
		<input type="hidden" id="doctorName" name="doctorName" value="${payDetail.doctorName}" />
		
		<input type="hidden" id="mainDiagnosisCode" name="mainDiagnosisCode" value="" />
		<input type="hidden" id="mainDiagnosisName" name="mainDiagnosisName" value="" />
	
		<div id="payRecord">
			<div style="color: #888;padding: .8em 1em">请您完善理赔申请信息：</div>
         	<ul class="ui-radio-list">
                <li><label><input type="radio" name="claimType" id="claimType" value="1" > 疾病</label></li>
                <li><label><input type="radio" name="claimType" id="claimType" value="2"> 意外</label></li>
            </ul>
            <section class="mod-search">
		        <section class="search-select">
		            <input type="text" placeholder="请输入主诊断疾病名称" class="search-select-input" id="searchKeyword" oninput="mainDiagnosis.matchMainDiagnosisName(this.value)" autocomplete="off">
		            <button id="search_btn" row-data="doctor" class="search-select-btn">搜索</button>
		        </section>
		        
		        <div class="space5"></div>
		        <div class="box-tips icontips"><i class="iconfont"></i>温馨提示：请如实按照门诊病历【诊断】项内容填写。如有多个诊断，请填写第一个；如果同时有中医、西医诊断，请填写西医诊断。</div>
		        
		        <section class="search-main" >
		            <#-- 搜索记录为空的样式 
		            <article class="seach-result recent-search">
		                <div class="recent-search-empty">
		                    没有最近搜索记录
		                </div>
		            </article>-->
		            <#-- 搜索结果页 -->
		            <article class="seach-result recent-search" id="searchList"></article>
		        </section>
		        <div></div>
		    </section>
		</div>
		
		<div class="btn-w fixed">
            <div class="btn btn-ok btn-block" id="toClinList">提交</div>
        </div>
		</form>
    </div>
    <div id="commonTips" style="display: none;">
    </div>
</div>


	


<#include "/common/footer.ftl">
</body>
<script src="${basePath}yxw.app/js/biz/insurance/app.serachMainDiagnosis.js?v=1.0" type="text/javascript"></script>
</html>

<script type="text/javascript">
	$(function(){
	
		//提交
		 $('#toClinList').one("click", function(event) {
        	event.stopPropagation();
    		event.preventDefault();
        	var action="${basePath}api/addClaim"; 
			goToCommunityHealth(action);
        });
        
		//验证是否选择理赔原因
		function goToCommunityHealth(action) {
			var claimType=$('input:radio[name="claimType"]:checked').val();
			var mainDiagnosisCode = $('#mainDiagnosisCode').val();
			if(claimType==null||claimType==""){
				var tip ="请选择申请理赔的类型";
				$Y.tips(tip);
	            return false;
	        }
	        
	        if(mainDiagnosisCode==""){
	        	var tip ="请填写主诊断疾病名称";
				$Y.tips(tip);
	            return false;
	        }
			$('#voForm').attr("action", action);
		 	$('#voForm').submit();
		}
		
	})
</script>