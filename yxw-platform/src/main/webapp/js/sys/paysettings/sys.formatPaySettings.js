var formatPaySettings = {};

formatPaySettings.format1 = function(params, data){
		var paySettingsHtml = [
								'<input type="hidden" field="id" value="'+((data!=null && data.id!='')?data.id:"")+'" />',
								'<input type="hidden" field="flag" value="'+((data!=null && data.id!='')?"0":"1")+'" />',
								'<input type="hidden" field="payModeId" value="'+params.payModeId+'" />',
								'<input type="hidden" field="platformSettingsId" value="'+params.platformSettingsId+'" />',
								'<div class="control-group"><label class="control-label">支付宝AppId</label><div class="controls"><input type="text" field="appId" value="'+((data!=null && data.appId!='')?data.appId:"")+'" class="span11"></div></div>',
								'<div class="control-group"><label class="control-label">支付宝合作者ID</label><div class="controls"><input type="text" field="mchId" value="'+((data!=null && data.mchId!='')?data.mchId:"")+'" class="span11"></div></div>',
								'<div class="control-group"><label class="control-label">支付宝商家帐号</label><div class="controls"><input type="text" field="mchAccount" value="'+((data!=null && data.mchAccount!='')?data.mchAccount:"")+'" class="span11"></div></div>',
								'<div class="control-group"><label class="control-label">支付宝支付密匙</label><div class="controls"><input type="text" field="payKey" value="'+((data!=null && data.payKey!='')?data.payKey:"")+'" class="span11"></div></div>',
								'<div class="control-group"><label class="control-label">支付宝支付私钥</label><div class="controls"><input type="text" field="payPrivateKey" value="'+((data!=null && data.payPrivateKey!='')?data.payPrivateKey:"")+'" class="span11"></div></div>',
								'<div class="control-group"><label class="control-label">支付宝支付公钥</label><div class="controls"><input type="text" field="payPublicKey" value="'+((data!=null && data.payPublicKey!='')?data.payPublicKey:"")+'" class="span11"></div></div>'
		                       ];
		
		$('#paySettings div[id="'+params.platformCode+'_'+params.payModeCode+'_input"]').append(paySettingsHtml.join(""));
};

formatPaySettings.format2 = function(params, data){
	console.log(((data!=null && data.id!='')?"0":"1"));
	var paySettingsHtml = [
							'<input type="hidden" field="id" value="'+((data!=null && data.id!='')?data.id:"")+'" />',
							'<input type="hidden" field="flag" value="'+((data!=null && data.id!='')?"0":"1")+'" />',
							'<input type="hidden" field="payModeId" value="'+params.payModeId+'" />',
							'<input type="hidden" field="platformSettingsId" value="'+params.platformSettingsId+'" />',
							'<div class="control-group">',
							'<label name="label_appId" class="control-label">微信AppId</label>',
							'<div class="controls">',
							'<input type="text" field="appId" value="'+((data!=null && data.appId!='')?data.appId:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_appSecret" class="control-label">微信AppSecret</label>',
							'<div class="controls">',
							'<input type="text" field="appSecret" value="'+((data!=null && data.appSecret!='')?data.appSecret:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_mchId" class="control-label">',
//							'<#if paySettingsMap['innerUnionPay'].isSubPay == 0>微信支付商户号</#if>',
//							'<#if paySettingsMap['innerUnionPay'].isSubPay == 1>微信支付父商户号</#if>',
//							(data!=null && data.isSubPay==0)?"微信支付商户号":"微信支付父商户号",
							"微信支付商户号",
							'</label>',
							'<div class="controls">',
							'<input type="text" field="mchId" value="'+((data!=null && data.mchId!='')?data.mchId:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_payKey" class="control-label">',
//							'<#if paySettingsMap['innerUnionPay'].isSubPay == 0>微信支付密匙</#if>',
//							'<#if paySettingsMap['innerUnionPay'].isSubPay == 1>微信支付父商户密匙</#if>',
//							(data!=null && data.isSubPay==0)?"微信支付密匙":"微信支付父商户密匙",
							"微信支付密匙",
							'</label>',
							'<div class="controls">',
							'<input type="text" field="payKey" value="'+((data!=null && data.payKey!='')?data.payKey:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_certificatePath" class="control-label">',
//							'<#if paySettingsMap['innerUnionPay'].isSubPay == 0>微信退费证书</#if>',
//							'<#if paySettingsMap['innerUnionPay'].isSubPay == 1>微信父商户退费证书</#if>',
//							(data!=null && data.isSubPay==0)?"微信退费证书":"微信父商户退费证书",
							"微信退费证书",
							'</label>',
							'<div class="controls">',
							'<span class="btn-save btn-upload" style="float:left;">',
							'选择证书',
							'<input class="fileupload" field="certificatePath" type="file" id="uploadFile2" name="uploadFile" onchange="$attach.uploadFile(this,innerUnionPayFileNameDiv,innerUnionPayAttachId,2);" value="'+((data!=null && data.certificatePath!='')?data.certificatePath:"")+'"></span>',
							'<div id="innerUnionPayFileNameDiv" class="span5" style="width: auto; min-width: 50%; border:#ccc 1px solid; height:40px; line-height:40px; display:inline-block; padding:0 5px;">'+((data!=null && data.certificatePath!='')?data.certificatePath:"")+'</div>',
							'</div>',
							'</div>'
	                       ];
	$('#paySettings div[id="'+params.platformCode+'_'+params.payModeCode+'_input"]').append(paySettingsHtml.join(""));
};

formatPaySettings.format3 = function(params, data){
	var paySettingsHtml = [
							'<input type="hidden" field="id" value="'+((data!=null && data.id!='')?data.id:"")+'" />',
							'<input type="hidden" field="flag" value="'+((data!=null && data.id!='')?"0":"1")+'" />',
							'<input type="hidden" field="payModeId" value="'+params.payModeId+'" />',
							'<input type="hidden" field="platformSettingsId" value="'+params.platformSettingsId+'" />',
							'<div class="control-group"><label class="control-label">银联钱包AppId</label><div class="controls"><input type="text" field="appId" value="'+((data!=null && data.appId!='')?data.appId:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">银联钱包AppSecret</label><div class="controls"><input type="text" field="appSecret" value="'+((data!=null && data.appSecret!='')?data.appSecret:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">银联商户号</label><div class="controls"><input type="text" field="mchId" value="'+((data!=null && data.mchId!='')?data.mchId:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">银联商户证书</label><div class="controls"><input type="text" field="certificatePath" value="'+((data!=null && data.certificatePath!='')?data.certificatePath:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">银联商户证书密码</label><div class="controls"><input type="text" field="certificatePwd" value="'+((data!=null && data.certificatePwd!='')?data.certificatePwd:"")+'" class="span11"></div></div>'
	                       ];
	
	$('#paySettings div[id="'+params.platformCode+'_'+params.payModeCode+'_input"]').append(paySettingsHtml.join(""));
};

formatPaySettings.format4 = function(params, data){
	var paySettingsHtml = [
							'<input type="hidden" field="id" value="'+((data!=null && data.id!='')?data.id:"")+'" />',
							'<input type="hidden" field="flag" value="'+((data!=null && data.id!='')?"0":"1")+'" />',
							'<input type="hidden" field="payModeId" value="'+params.payModeId+'" />',
							'<input type="hidden" field="platformSettingsId" value="'+params.platformSettingsId+'" />',
							'<div class="control-group"><label class="control-label">支付宝AppId</label><div class="controls"><input type="text" field="appId" value="'+((data!=null && data.appId!='')?data.appId:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">支付宝合作者ID</label><div class="controls"><input type="text" field="mchId" value="'+((data!=null && data.mchId!='')?data.mchId:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">支付宝商家帐号</label><div class="controls"><input type="text" field="mchAccount" value="'+((data!=null && data.mchAccount!='')?data.mchAccount:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">支付宝支付密匙</label><div class="controls"><input type="text" field="payKey" value="'+((data!=null && data.payKey!='')?data.payKey:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">支付宝支付私钥</label><div class="controls"><input type="text" field="payPrivateKey" value="'+((data!=null && data.payPrivateKey!='')?data.payPrivateKey:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">支付宝支付公钥</label><div class="controls"><input type="text" field="payPublicKey" value="'+((data!=null && data.payPublicKey!='')?data.payPublicKey:"")+'" class="span11"></div></div>'
	                       ];
	
	$('#paySettings div[id="'+params.platformCode+'_'+params.payModeCode+'_input"]').append(paySettingsHtml.join(""));
};

formatPaySettings.format5 = function(params, data){
	var paySettingsHtml = [
							'<input type="hidden" field="id" value="'+((data!=null && data.id!='')?data.id:"")+'" />',
							'<input type="hidden" field="flag" value="'+((data!=null && data.id!='')?"0":"1")+'" />',
							'<input type="hidden" field="payModeId" value="'+params.payModeId+'" />',
							'<input type="hidden" field="platformSettingsId" value="'+params.platformSettingsId+'" />',
							'<div class="control-group"><label class="control-label">银联钱包AppId</label><div class="controls"><input type="text" field="appId" value="'+((data!=null && data.appId!='')?data.appId:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">银联钱包AppSecret</label><div class="controls"><input type="text" field="appSecret" value="'+((data!=null && data.appSecret!='')?data.appSecret:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">银联商户号</label><div class="controls"><input type="text" field="mchId" value="'+((data!=null && data.mchId!='')?data.mchId:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">银联商户证书</label><div class="controls"><input type="text" field="certificatePath" value="'+((data!=null && data.certificatePath!='')?data.certificatePath:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">银联商户证书密码</label><div class="controls"><input type="text" field="certificatePwd" value="'+((data!=null && data.certificatePwd!='')?data.certificatePwd:"")+'" class="span11"></div></div>'
	                       ];
	
	$('#paySettings div[id="'+params.platformCode+'_'+params.payModeCode+'_input"]').append(paySettingsHtml.join(""));
};

formatPaySettings.format6 = function(params, data){
	var paySettingsHtml = [
							'<input type="hidden" field="id" value="'+((data!=null && data.id!='')?data.id:"")+'" />',
							'<input type="hidden" field="flag" value="'+((data!=null && data.id!='')?"0":"1")+'" />',
							'<input type="hidden" field="payModeId" value="'+params.payModeId+'" />',
							'<input type="hidden" field="platformSettingsId" value="'+params.platformSettingsId+'" />',
							'<div class="control-group">',
							'<label name="label_appId" class="control-label">微信AppId</label>',
							'<div class="controls">',
							'<input type="text" field="appId" value="'+((data!=null && data.appId!='')?data.appId:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_appSecret" class="control-label">微信AppSecret</label>',
							'<div class="controls">',
							'<input type="text" field="appSecret" value="'+((data!=null && data.appSecret!='')?data.appSecret:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_mchId" class="control-label">',
							//'<#if paySettingsMap['autoService'].isSubPay == 0>微信支付商户号</#if>',
							//'<#if paySettingsMap['autoService'].isSubPay == 1>微信支付父商户号</#if>',
							//(data!=null && data.isSubPay==0)?"微信支付商户号":"微信支付父商户号",
							"微信支付商户号",
							'</label>',
							'<div class="controls">',
							'<input type="text" field="mchId" value="'+((data!=null && data.mchId!='')?data.mchId:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_payKey" class="control-label">',
							//'<#if paySettingsMap['autoService'].isSubPay == 0>微信支付密匙</#if>',
							//'<#if paySettingsMap['autoService'].isSubPay == 1>微信支付父商户密匙</#if>',
							//(data!=null && data.isSubPay==0)?"微信支付密匙":"微信支付父商户密匙",
							"微信支付密匙",
							'</label>',
							'<div class="controls">',
							'<input type="text" field="payKey" value="'+((data!=null && data.payKey!='')?data.payKey:"")+'" class="span11"></div>',
							'</div>'
	                       ];
	
	$('#paySettings div[id="'+params.platformCode+'_'+params.payModeCode+'_input"]').append(paySettingsHtml.join(""));
};

formatPaySettings.format7 = function(params, data){
	var paySettingsHtml = [
							'<input type="hidden" field="id" value="'+((data!=null && data.id!='')?data.id:"")+'" />',
							'<input type="hidden" field="flag" value="'+((data!=null && data.id!='')?"0":"1")+'" />',
							'<input type="hidden" field="payModeId" value="'+params.payModeId+'" />',
							'<input type="hidden" field="platformSettingsId" value="'+params.platformSettingsId+'" />',
							'<div class="control-group"><label class="control-label">支付宝AppId</label><div class="controls"><input type="text" field="appId" value="'+((data!=null && data.appId!='')?data.appId:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">支付宝合作者ID</label><div class="controls"><input type="text" field="mchId" value="'+((data!=null && data.mchId!='')?data.mchId:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">支付宝商家帐号</label><div class="controls"><input type="text" field="mchAccount" value="'+((data!=null && data.mchAccount!='')?data.mchAccount:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">支付宝支付密匙</label><div class="controls"><input type="text" field="payKey" value="'+((data!=null && data.payKey!='')?data.payKey:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">支付宝支付私钥</label><div class="controls"><input type="text" field="payPrivateKey" value="'+((data!=null && data.payPrivateKey!='')?data.payPrivateKey:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">支付宝支付公钥</label><div class="controls"><input type="text" field="payPublicKey" value="'+((data!=null && data.payPublicKey!='')?data.payPublicKey:"")+'" class="span11"></div></div>'
	                       ];
	
	$('#paySettings div[id="'+params.platformCode+'_'+params.payModeCode+'_input"]').append(paySettingsHtml.join(""));
};

formatPaySettings.format8 = function(params, data){
	var paySettingsHtml = [
							'<input type="hidden" field="id" value="'+((data!=null && data.id!='')?data.id:"")+'" />',
							'<input type="hidden" field="flag" value="'+((data!=null && data.id!='')?"0":"1")+'" />',
							'<input type="hidden" field="payModeId" value="'+params.payModeId+'" />',
							'<input type="hidden" field="platformSettingsId" value="'+params.platformSettingsId+'" />',
							'<div class="control-group">',
							'<label name="label_appId" class="control-label">微信AppId</label>',
							'<div class="controls">',
							'<input type="text" field="appId" value="'+((data!=null && data.appId!='')?data.appId:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_appSecret" class="control-label">微信AppSecret</label>',
							'<div class="controls">',
							'<input type="text" field="appSecret" value="'+((data!=null && data.appSecret!='')?data.appSecret:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_mchId" class="control-label">',
							//'<#if paySettingsMap['autoService'].isSubPay == 0>微信支付商户号</#if>',
							//'<#if paySettingsMap['autoService'].isSubPay == 1>微信支付父商户号</#if>',
							//(data!=null && data.isSubPay==0)?"微信支付商户号":"微信支付父商户号",
							"微信支付商户号",
							'</label>',
							'<div class="controls">',
							'<input type="text" field="mchId" value="'+((data!=null && data.mchId!='')?data.mchId:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_payKey" class="control-label">',
							//'<#if paySettingsMap['autoService'].isSubPay == 0>微信支付密匙</#if>',
							//'<#if paySettingsMap['autoService'].isSubPay == 1>微信支付父商户密匙</#if>',
							//(data!=null && data.isSubPay==0)?"微信支付密匙":"微信支付父商户密匙",
							"微信支付密匙",
							'</label>',
							'<div class="controls">',
							'<input type="text" field="payKey" value="'+((data!=null && data.payKey!='')?data.payKey:"")+'" class="span11"></div>',
							'</div>'
	                       ];
	
	$('#paySettings div[id="'+params.platformCode+'_'+params.payModeCode+'_input"]').append(paySettingsHtml.join(""));
};

formatPaySettings.format9 = function(params, data){
	var paySettingsHtml = [
							'<input type="hidden" field="id" value="'+((data!=null && data.id!='')?data.id:"")+'" />',
							'<input type="hidden" field="flag" value="'+((data!=null && data.id!='')?"0":"1")+'" />',
							'<input type="hidden" field="payModeId" value="'+params.payModeId+'" />',
							'<input type="hidden" field="platformSettingsId" value="'+params.platformSettingsId+'" />',
							'<div class="control-group">',
							'<label name="label_appId" class="control-label">微信AppId</label>',
							'<div class="controls">',
							'<input type="text" field="appId" value="'+((data!=null && data.appId!='')?data.appId:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_appSecret" class="control-label">微信AppSecret</label>',
							'<div class="controls">',
							'<input type="text" field="appSecret" value="'+((data!=null && data.appSecret!='')?data.appSecret:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_mchId" class="control-label">',
							//'<#if paySettingsMap['autoService'].isSubPay == 0>微信支付商户号</#if>',
							//'<#if paySettingsMap['autoService'].isSubPay == 1>微信支付父商户号</#if>',
							//(data!=null && data.isSubPay==0)?"微信支付商户号":"微信支付父商户号",
							"微信支付商户号",
							'</label>',
							'<div class="controls">',
							'<input type="text" field="mchId" value="'+((data!=null && data.mchId!='')?data.mchId:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_payKey" class="control-label">',
							//'<#if paySettingsMap['autoService'].isSubPay == 0>微信支付密匙</#if>',
							//'<#if paySettingsMap['autoService'].isSubPay == 1>微信支付父商户密匙</#if>',
							//(data!=null && data.isSubPay==0)?"微信支付密匙":"微信支付父商户密匙",
							"微信支付密匙",
							'</label>',
							'<div class="controls">',
							'<input type="text" field="payKey" value="'+((data!=null && data.payKey!='')?data.payKey:"")+'" class="span11"></div>',
							'</div>'
	                       ];
	
	$('#paySettings div[id="'+params.platformCode+'_'+params.payModeCode+'_input"]').append(paySettingsHtml.join(""));
};

formatPaySettings.format10 = function(params, data){
	var paySettingsHtml = [
							'<input type="hidden" field="id" value="'+((data!=null && data.id!='')?data.id:"")+'" />',
							'<input type="hidden" field="flag" value="'+((data!=null && data.id!='')?"0":"1")+'" />',
							'<input type="hidden" field="payModeId" value="'+params.payModeId+'" />',
							'<input type="hidden" field="platformSettingsId" value="'+params.platformSettingsId+'" />',
							'<div class="control-group">',
							'<label name="label_appId" class="control-label">微信AppId</label>',
							'<div class="controls">',
							'<input type="text" field="appId" value="'+((data!=null && data.appId!='')?data.appId:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_appSecret" class="control-label">微信AppSecret</label>',
							'<div class="controls">',
							'<input type="text" field="appSecret" value="'+((data!=null && data.appSecret!='')?data.appSecret:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_mchId" class="control-label">',
							//'<#if paySettingsMap['innerWechat'].isSubPay == 0>微信支付商户号</#if>',
							//'<#if paySettingsMap['innerWechat'].isSubPay == 1>微信支付父商户号</#if>',
							//(data!=null && data.isSubPay==0)?"微信支付商户号":"微信支付父商户号",
							"微信支付商户号",
							'</label>',
							'<div class="controls">',
							'<input type="text" field="mchId" value="'+((data!=null && data.mchId!='')?data.mchId:"")+'" class="span11"></div>',
							'</div>',
							'<div class="control-group">',
							'<label name="label_payKey" class="control-label">',
							//'<#if paySettingsMap['innerWechat'].isSubPay == 0>微信支付密匙</#if>',
							//'<#if paySettingsMap['innerWechat'].isSubPay == 1>微信支付父商户密匙</#if>',
							//(data!=null && data.isSubPay==0)?"微信支付密匙":"微信支付父商户密匙",
							"微信支付密匙",
							'</label>',
							'<div class="controls">',
							'<input type="text" field="payKey" value="'+((data!=null && data.payKey!='')?data.payKey:"")+'" class="span11"></div>',
							'</div>'
	                       ];
	
	$('#paySettings div[id="'+params.platformCode+'_'+params.payModeCode+'_input"]').append(paySettingsHtml.join(""));
};

formatPaySettings.format11 = function(params, data){
	var paySettingsHtml = [
							'<input type="hidden" field="id" value="'+((data!=null && data.id!='')?data.id:"")+'" />',
							'<input type="hidden" field="flag" value="'+((data!=null && data.id!='')?"0":"1")+'" />',
							'<input type="hidden" field="payModeId" value="'+params.payModeId+'" />',
							'<input type="hidden" field="platformSettingsId" value="'+params.platformSettingsId+'" />',
							'<div class="control-group"><label class="control-label">支付宝AppId</label><div class="controls"><input type="text" field="appId" value="'+((data!=null && data.appId!='')?data.appId:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">支付宝合作者ID</label><div class="controls"><input type="text" field="mchId" value="'+((data!=null && data.mchId!='')?data.mchId:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">支付宝商家帐号</label><div class="controls"><input type="text" field="mchAccount" value="'+((data!=null && data.mchAccount!='')?data.mchAccount:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">支付宝支付密匙</label><div class="controls"><input type="text" field="payKey" value="'+((data!=null && data.payKey!='')?data.payKey:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">支付宝支付私钥</label><div class="controls"><input type="text" field="payPrivateKey" value="'+((data!=null && data.payPrivateKey!='')?data.payPrivateKey:"")+'" class="span11"></div></div>',
							'<div class="control-group"><label class="control-label">支付宝支付公钥</label><div class="controls"><input type="text" field="payPublicKey" value="'+((data!=null && data.payPublicKey!='')?data.payPublicKey:"")+'" class="span11"></div></div>'
	                       ];
	
	$('#paySettings div[id="'+params.platformCode+'_'+params.payModeCode+'_input"]').append(paySettingsHtml.join(""));
};

String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};