<!DOCTYPE html>
<html>
<head>
<#include "/common/common.ftl">
<title>理赔申请</title>
</head>
<body>

		<div class="page-title">请完善以下信息</div>
		<form id="voForm" method="post" action="">
	        <input type="hidden"   name="id" value="${order.id}" />
	        <input type="hidden" id="bankName" name="bankName" value="${order.bankName}" />
	
		<ul class="lipei-form-list">
        <li>
                <span class="label">账户户名</span><input type="text"   readonly  name="patientName"  value="${order.patientName}"  placeholder="账户户名">
        </li>
        <li>
                <span class="label">领款帐号</span><input   type="text"  id="patientCardNo"   name="patientCardNo" value="${order.patientCardNo}" maxlength="19" placeholder="领款帐号">
        </li>
        <li>
                <span class="label">开记银行</span>
                <div class="ui-select-block">
                        <select  name="bankCode" id="bankCode" >
                        		<option value="">请选择开户银行</option>
                                 <#list blanks as blank>
	                            	<option 
	                            	<#if order.bankCode==blank.bankCode >selected="selected"</#if> value="${blank.bankCode}"
	                            	>${blank.bankName}</option>
	                            </#list>
                        </select>
                </div>
        </li>
        <li>
                <span class="label">通迅地址</span>
                <div>
                        <textarea  class="ui-textarea" maxlength="60" style="min-height: 30px;padding-top: 0" id="address" name="address"  value="${order.address}" placeholder="请填写通迅地址">${order.address}</textarea>
                </div>


        </li>
</ul>
<div class="padding-all-14">
<div class="lipei-tips-block" style="padding-left: 2em">
        <div style="text-indent: -1em"><i class="lipei icon-sharp" style="color: #f0a92c"></i>温馨提示：</div>
        1.银行账户用于理赔金的赔付转账，只能是患者本人，不可更改。<br>
        2.不得使用信用卡及其他一切有透支功能的贷记卡。<br>
        3. 通讯地址请务必填写真实地址，理赔专员或邮寄需要。<br>
</div>

<div style="height: 20px"></div>
        <a href="javascript:;" class="btn btn-block btn-ok"  id="addParent">确定</a>
</div>
</form>


<#include "/common/footer.ftl">
<script type="text/javascript" src="${basePath}yxw.app/js/biz/common/bankNoValidate.js"></script>
</body>
</html>
<script type="text/javascript">
	    //提交
	    $('#addParent').off('click').on('click', function() {
	    	if($("#patientCardNo").val()==""||!bankNoValidate.luhnCheck($("#patientCardNo").val())){
	    	    var tip ="银行卡号输入有误，请重新输入";
				$Y.tips(tip);
	    		return;
	    	}
	    	if($("#bankCode").val()==""){
	    	    var tip ="请选择开户银行";
				$Y.tips(tip);
	    		return;
	    	}
	    	if($("#address").val()==""){
	    	   var tip ="请完善通讯地址";
				$Y.tips(tip);
	    		return;
	    	}
	        var action="${basePath}api/addClaimOrder"; 
			$('#voForm').attr("action", action);
		 	$('#voForm').submit();
		});
		
		$('#bankCode').change(function(){ 
			 $("#bankName").val($(this).find("option:selected").text());
		})
</script>
