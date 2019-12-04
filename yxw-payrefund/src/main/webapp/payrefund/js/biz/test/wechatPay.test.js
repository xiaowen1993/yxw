function scanQRCode() {
	$("textarea").text("");

	wx.scanQRCode({
	    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
	    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
	    success: function (res) {
		    var authCode = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
		    //alert(result);
		    
		    if (authCode) {
		    	$.showLoading("支付中处理...");
			    $.ajax({
					url : "wechatMicroPay",
					data : {authCode: authCode},
					async : true,
					dataType : 'json',
					timeout : 15000,
					type : 'POST',
					error : function(XMLHQ, errorMsg) {
						console.log(errorMsg);
					},
					success : function(data) {
						console.dir(data);
						$("textarea").text(data.fmtData);
						//alert(data.resultCode);
						
						if ("success" == data.result.resultCode) {
							$.hideLoading();
							
							//alert("微信订单号：" + data.result.agtOrderNo);
							$.toast("支付成功");
						} else {
							orderQuery(data.result.orderNo);
						}
					}
				});
		    }
		}, 
		cancel: function () {
			$.toast("已取消", "cancel");
		}
	});
}

var qureyTimes = 0;
function orderQuery(orderNo) {
	qureyTimes++;
	$.ajax({
		url : "../../../query/test/wechatPayQuery",
		data : {orderNo: orderNo},
		async : true,
		dataType : 'json',
		timeout : 15000,
		type : 'POST',
		error : function(XMLHQ, errorMsg) {
			console.log(errorMsg);
		},
		success : function(data) {
			console.dir(data);
			$("textarea").text(data.fmtData);
			//alert(data.resultCode);
			
			if ("success" == data.result.tradeState) {
				$.hideLoading();
				
				//alert("微信订单号：" + data.result.agtOrderNo);
				$.toast("支付成功");
			} else {
				if (qureyTimes < 15) {
					setTimeout(function(){
						orderQuery(orderNo);
					}, 2000);
				} else {
					$.hideLoading();
					$.toast("支付超时，请重试。", "text");
					
					//TODO 撤销订单
				}
			}
		}
	});
}
