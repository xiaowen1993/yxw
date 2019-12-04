var confirm = {};
confirm.STATE_NORMAL_HAVING = 0;//预约中
confirm.STATE_NORMAL_HAD = 1;//已预约

confirm.REG_TYPE_APPOINTMENT = 1;
confirm.REG_TYPE_CUR = 2;

confirm.confirmRegister = function(){
	$('#confirmRegister').click(function(event){
		event.stopPropagation();
		event.preventDefault();
		
		confirm.register(this);
	});
};

confirm.register = function(domObj) {

	$(domObj).attr("disabled",true);
	$Y.loading.show('号源确认中，请等待..')
	$Y.mask.show();
    var appCode = $("#appCode").val();
    
    var currCardLi = $('#selectCard ul.ui-list-radio li.active');
    if($(currCardLi).length == 1){
      var familyid = $(currCardLi).attr("familyid");
      var cardInfo = commonCard.cardInfos[familyid].card;

      var regPersonType = "";
      if (cardInfo.ownership=="1") {
    	  regPersonType = "1"
      }else if(cardInfo.ownership=="3") {
    	  regPersonType = "2"
      }else {
    	  regPersonType = "3"
      }
      $("#regPersonType").val(regPersonType);
      $("#cardType").val(cardInfo.cardType);
      $("#cardNo").val(cardInfo.cardNo);
      $("#patientName").val(cardInfo.name);
      $("#patientSex").val(cardInfo.sex);
      $("#patientMobile").val(cardInfo.mobile);
      $("#idType").val(cardInfo.idType);
      $("#idNo").val(cardInfo.idNo);
      
	}else{
    	$Y.loading.hide();
    	$Y.mask.remove();
        var noaddpatient = new $Y.confirm({
             content:'<div>请添加就诊人</div>',
             ok:{title: '确定'}
        }) 
        $(domObj).attr("disabled",false);
        return;
    }
    
    if (!$('ul.yx-list.tradeTypes li').hasClass("active")) {
    	$Y.loading.hide();
    	$Y.mask.remove();
        var noaddpatient = new $Y.confirm({
             content:'<div>请选择支付方式</div>',
             ok:{title: '确定'}
        }) 
        $(domObj).attr("disabled",false);
        return;
	}
    
    var isPayDom = $("#isPayFlag");
    if(isPayDom){
    	if(isPayDom.hasClass('icon-switch-right')){
    		$("#isPay").val(1);
    	}else{
    		$("#isPay").val(0);
    	}
    }
   
    $("#diseaseDesc").val($("#diseaseDesc_input").val());
    var reqUrl = base.appPath + "easyhealth/register/confirm/generateOrder";
    commonTrade.autoSetTradeMode();
   
    var datas = $("#paramsForm").serializeArray(); 
    console.log(JSON.stringify(datas));
    
    $.ajax({  
        type : 'POST',  
        url  : reqUrl,  
        data : datas,  
        dataType : 'json',  
        timeout  : 120000,
        success  : function(data) { 
            $Y.loading.hide()
            console.log(data);
            if(data.message.isSuccess){
            	if(data.message.currentRegStatus == confirm.STATE_NORMAL_HAD){
            		if(data.message.tradeAmout == 0){
            			var currentRegOrderNo = data.message.currentRegOrderNo;
                		var currentOpenId = data.message.currentOpenId;
                		window.location = base.appPath + "easyhealth/register/infos/showOrderInfo?reqSource=confirmReg&orderNo=" +currentRegOrderNo + "&openId=" + currentOpenId;
            		}else if(data.message.currentIsPay == 0 || data.message.currentIsPay === undefined ){
            			//暂不支付模式-未选择支付   以及 不支付模式
            			var currentRegOrderNo = data.message.currentRegOrderNo;
                		var currentOpenId = data.message.currentOpenId;
                		window.location = base.appPath + "easyhealth/register/infos/showOrderInfo?reqSource=confirmReg&orderNo=" +currentRegOrderNo + "&openId=" + currentOpenId;
            		}else{
            			// 选择支付 
            			$("#payForm").formEdit(data.message.pay);
            			$("#payForm").attr("action" , data.message.tradeUrl);
                        $("#payForm").submit();
            		}
            	}else{
            		//实际支付金额为零时,不进入支付页面
            		if(data.message.tradeAmout == 0){
            			var currentRegOrderNo = data.message.currentRegOrderNo;
                		var currentOpenId = data.message.currentOpenId;
                		window.location = base.appPath + "easyhealth/register/infos/showOrderInfo?reqSource=confirmReg&orderNo=" +currentRegOrderNo + "&openId=" + currentOpenId;
            		}else{
            			$("#payForm").formEdit(data.message.pay);
            			$("#payForm").attr("action" , data.message.tradeUrl);
                        $("#payForm").submit();
            		}
            	}
            	/*
            	if(data.message.currentRegStatus == confirm.STATE_NORMAL_HAD){
            		if(data.message.currentIsPay == 0 || data.message.currentIsPay === undefined ){
            			var currentRegOrderNo = data.message.currentRegOrderNo;
                		var currentOpenId = data.message.currentOpenId;
                		window.location = base.appPath + "easyhealth/register/infos/showOrderInfo?reqSource=confirmReg&orderNo=" +currentRegOrderNo + "&openId=" + currentOpenId;
            		}else{
            			$("#payForm").formEdit(data.message.pay);
            			$("#payForm").attr("action" , data.message.tradeUrl);
                        $("#payForm").submit();
            		}
            	}else{
            		$("#payForm").formEdit(data.message.pay);
            		$("#payForm").attr("action" , data.message.tradeUrl);
                    $("#payForm").submit();
            	}
            */}else{
            	$Y.mask.remove();
                msgBox = new $Y.confirm({
                    content:'<div>' + data.message.msgInfo + '</div>',
                    ok:{title: '确定'}
                })
                $(domObj).attr("disabled",false);
            }
         },  
         error : function(data) {  
            $Y.loading.hide()
            $Y.mask.remove();
            errorBox =  new $Y.confirm({
                content:'<div>网络异常,请保持您的网络通畅,稍后再试.</div>',
                ok:{title: '确定'}
            }) 
            $(domObj).attr("disabled",false);
         }  
    }); 
 
};

$(function(){
	confirm.confirmRegister();
});


$.fn.formEdit = function(data){
    return this.each(function(){
        var elementDom;
        var elementDomName;
        if(data == null){
        	this.reset(); 
        	return; 
        }
        for(var i = 0; i < this.length; i++){  
        	elementDom = this.elements[i];
        	elementDomName = elementDom.name;
            if(data[elementDomName] == undefined){ 
            	continue;
            }
            elementDom.value = data[elementDomName];
        }
    });
};

function doRefresh()
{
	$Y.loading.show('订单重载中...');
	setTimeout("$Y.loading.hide()",500);
}