function cancelRegister(domObj){
	$(domObj).attr("disabled",true);
    $Y.loading.show();
    $Y.mask.show();
    var datas = $("#paramsForm").serializeArray(); 
	var url = base.appPath + "easyhealth/register/infos/checkCanCancelReg";
	$.ajax({  
		type : 'POST',  
	    url : url,  
	    data : datas,  
	    dataType : 'json',  
	    timeout:120000,
	    success : function(data) { 
	    	$Y.loading.hide();
	    	if(data.message.isSuccess){
	    		$("#paramsForm").attr("action" , base.appPath + "easyhealth/register/infos/cancelRegister");
	    	    $("#paramsForm").submit();
	    	}else{
	    		$Y.mask.remove();
	    		var refundWarnMsg = new $Y.confirm({
	                content:'<div>' + data.message.msgInfo + '</div>',
	                ok:{title: '确定'}
	            }); 
	    		$(domObj).attr("disabled",false);
	    	}
	    },  
	    error : function(data) {  
	    	$Y.loading.hide();
	    	$Y.mask.remove();
	    	var refundErrorMsg = new $Y.confirm({
                content:'<div>网络异常,请保持您的网络通畅,稍后再试.</div>',
                ok:{title: '确定'}
            })
	    	$(domObj).attr("disabled",false);
	    }  
	 }); 
}

function goRegInfoList(){
	
	var referrer = document.referrer;
	if (referrer.indexOf("usercenter/regRecords/list") > 0) {
		window.location.href = referrer;
	}else {
		skipPages.forward('userCenterIndex');
	}
}

function payMentReg(domObj){
	$(domObj).attr("disabled",true);
	$Y.loading.show("请求提交中");
	if(isCanPayMent == 'false' || $('#leftSecondSpan').text() == "00:00"){
		$Y.loading.hide();
		var tipMsg = new $Y.confirm({
            content:'<div>支付时间已过,该订单已过期.</div>',
            ok:{title: '确定'}
        })
		$(domObj).attr("disabled",false);
		return;
	}
	var url = base.appPath + "easyhealth/register/infos/checkCanPayMent";
	var datas = $("#paramsForm").serializeArray();
	$.ajax({  
		type : 'POST',  
	    url : url,  
	    data : datas,  
	    dataType : 'json',  
	    timeout:120000,
	    success : function(data) { 
	    	$Y.loading.hide();
	    	if(data.message.isSuccess){
	    		$("#payForm").formEdit(data.message.pay);
    			$("#payForm").attr("action" , data.message.tradeUrl);
                $("#payForm").submit();
	    	}else{
	    		var payMentWarnMsg = new $Y.confirm({
	                content:'<div>' + data.message.msgInfo + '</div>',
	                ok:{title: '确定'}
	            }); 
	    		$(domObj).attr("disabled",false);
	    	}
	    },  
	    error : function(data) {  
	    	$Y.loading.hide();
	    	var refundErrorMsg = new $Y.confirm({
                content:'<div>网络异常,请保持您的网络通畅,稍后再试.</div>',
                ok:{title: '确定'}
            })
	    	$(domObj).attr("disabled",false);
	    }  
	}); 
}

function refundConfirm(domObj){
     var myBox = new $Y.confirm({
    	content:'<div>您是否真的要取消预约?</div>',
		ok:{
			title:"确定",
			click:function(){ //参数可为空，没有默认方法,不会自动关闭窗体，可用  myBox.close()来关闭  
				myBox.close();
				refundRegConfirm(domObj);
			}
		},
		cancel:{                   
			title:"取消",
			click:function(){       //参数可为空, 当为空时默认方法关闭窗体
				myBox.close();
			}
		},
		callback:function(){ 
		//窗体显示后的回调
		}
     })
}
     
function refundRegConfirm(domObj){

	$(domObj).attr("disabled",true);
	$Y.loading.show("取消预约中,请等待..");
	$Y.mask.show();
	var datas = $("#paramsForm").serializeArray(); 
	var url = base.appPath + "easyhealth/register/infos/refundRegConfirm"
	$.ajax({  
		type : 'POST',  
	    url : url,  
	    data : datas,  
	    dataType : 'json',  
	    timeout:120000,
	    success : function(data) { 
	    	$Y.loading.hide();
	    	//退费确认是否成功
	    	if(data.message.isSuccess){
	    		if(data.message.refundIsSuccess){
	    			
	    			$Y.mask.remove();
	    			if(data.message.isTip){
	    				var isTipMsg = new $Y.confirm({
			                content:'<div>'+ data.message.tipMsg +'</div>',
			                ok:{
			                	title: '确定',
			                	click:function(){ //参数可为空，没有默认方法,不会自动关闭窗体，可用  myBox.close()来关闭  
			                		isTipMsg.close();
			                		var refundSuccessMsg = new $Y.confirm({
						                content:'<div>您的请求已受理,请稍后查看结果.</div>',
						                ok:{
						                	title: '确定',
						                	click:function(){ //参数可为空，没有默认方法,不会自动关闭窗体，可用  myBox.close()来关闭  
						                		refundSuccessMsg.close();
						        				url = base.appPath + "easyhealth/register/infos/showOrderInfo";
						    	    			$("#paramsForm").attr("action" , url);
						    	    			$("#paramsForm").submit();
						        			}
						                }
						            }); 
			        			}
			                }
			            }); 
	    			}else{
	    				//第3方退费成功
			    		var refundSuccessMsg = new $Y.confirm({
			                content:'<div>您的请求已受理,请稍后查看结果.</div>',
			                ok:{
			                	title: '确定',
			                	click:function(){ //参数可为空，没有默认方法,不会自动关闭窗体，可用  myBox.close()来关闭  
			                		refundSuccessMsg.close();
			        				url = base.appPath + "easyhealth/register/infos/showOrderInfo";
			    	    			$("#paramsForm").attr("action" , url);
			    	    			$("#paramsForm").submit();
			        			}
			                }
			            }); 
	    			}
	    		}else{
	    			//第3方退费失败
	    			$Y.mask.remove();
		    		var refundWarnMsg = new $Y.confirm({
		                content:'<div>' + data.message.failMsg + '</div>',
		                ok:{title: '确定'}
		            }); 
		    		$(domObj).attr("disabled",false);
	    		}
	    	}else{
	    		$Y.mask.remove();
	    		var refundWarnMsg = new $Y.confirm({
	                content:'<div>' + data.message.msgInfo + '</div>',
	                ok:{title: '确定'}
	            }); 
	    		$(domObj).attr("disabled",false);
	    	}
	    },  
	    error : function(data) {  
	    	$Y.loading.hide();
	    	$Y.mask.remove();
	    	var refundErrorMsg = new $Y.confirm({
                content:'<div>网络异常,请保持您的网络通畅,稍后再试.</div>',
                ok:{title: '确定'}
            })
	    	$(domObj).attr("disabled",false);
	    }  
	 }); 

	/*
	$(domObj).attr("disabled",true);
	$Y.loading.show("取消预约中,请等待..");
	$Y.mask.show();
	var datas = $("#paramsForm").serializeArray(); 
	var url = appPath + "easyhealth/register/infos/refundRegConfirm"
	$.ajax({  
		type : 'POST',  
	    url : url,  
	    data : datas,  
	    dataType : 'json',  
	    timeout:120000,
	    success : function(data) { 
	    	$Y.loading.hide();
	    	//退费确认是否成功
	    	if(data.message.isSuccess){
	    		if(data.message.refundIsSuccess){
	    			//第3方退费成功
	    			url = appPath + "easyhealth/register/infos/refundSuccess";
	    			$("#paramsForm").attr("action" , url);
	    			$("#paramsForm").submit();
	    		}else{
	    			//第3方退费失败
	    			url = appPath + "easyhealth/register/infos/refundException";
	    			$("#paramsForm").attr("action" , url);
	    			$("#failMsg").val(data.message.failMsg);
	    			$("#paramsForm").submit();
	    		}
	    	}else{
	    		$Y.mask.remove();
	    		var refundWarnMsg = new $Y.confirm({
	                content:'<div>' + data.message.msgInfo + '</div>',
	                ok:{title: '确定'}
	            }); 
	    		$(domObj).attr("disabled",false);
	    	}
	    },  
	    error : function(data) {  
	    	$Y.loading.hide();
	    	$Y.mask.remove();
	    	var refundErrorMsg = new $Y.confirm({
                content:'<div>网络异常,请保持您的网络通畅,稍后再试.</div>',
                ok:{title: '确定'}
            })
	    	$(domObj).attr("disabled",false);
	    }  
	 }); 
*/}

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

jQuery(function(){
	var leftSecondDom = $('#leftSecond');
    if(leftSecondDom){
    	var leftSecond = leftSecondDom.val();
        if(leftSecond <= 0){
            $('#leftSecond').val(0);
            $('#leftSecondSpan').text("00:00");
            $('#payBtn').unbind("click");//禁用
        }else{
            $('#leftSecondSpan').text(secondToMin(leftSecond));
            setTimeout(djs, 1000);
        }
    }
});

//倒计时
function djs() {
    var leftSecond = $('#leftSecond').val() - 1;
    if (leftSecond > 0) {
        $('#leftSecond').val(leftSecond);
        $('#leftSecondSpan').text(secondToMin(leftSecond));
        setTimeout(djs, 1000);
    } else {
        $('#leftSecond').val(0);
        $('#leftSecondSpan').text("00:00");
        $('#payBtn').unbind("click");//禁用
    }
}

function secondToMin(leftSecond){
    var min = 0;
    var second = 0;
    if(leftSecond > 60){
        var min = parseInt(leftSecond/60);
        var second = parseInt(leftSecond%60);
        if(min < 10){
            min = "0" + min;
        }
        if(second < 10){
            second = "0" + second;
        }
    }else{
        var second = leftSecond%60;
        if(second < 10){
            second = "0" + second;
        }
        min = "00";
    }
    return min + ":" + second;
}

function goDidipasnger(){
	if(IS.isMacOS){ 
		appCallOtherApp("didipasnger");
	}else if(IS.isAndroid){
		window.yx129.appCallOtherApp("com.sdu.didi.psnger");
	}
}

$(function() {
//	pushHistory();
	window.addEventListener("popstate", function(e) {
		
		var referrer = document.referrer;
		if (referrer.indexOf("usercenter/regRecords/list") > 0) {
			window.location.href = referrer;
		}else {
			skipPages.forward('userCenterIndex');
		}
		
	}, false);
	function pushHistory() {
		var state = {
			title : "title",
			url : "#"
		};
		window.history.pushState(state, "title", "#");
	}
});