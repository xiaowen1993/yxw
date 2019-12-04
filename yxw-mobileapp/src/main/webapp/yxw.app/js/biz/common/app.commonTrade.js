var commonTrade = {
	init: function() {
		
	},
	autoSetTradeMode: function() {
		// 健康易需要选择tradeMode
		var tradeMode = -1;
		var activeObjs = $('ul.tradeTypes>li.flex.active');
		if (activeObjs.length == 1) {
			tradeMode = activeObjs.attr('data-id');
		} else {
			new $Y.confirm({
	            ok:{title:'确定'},
	            content:'获取交易方式失败，请刷新后重试。'
	        });
    		return false;
		}
		
		if (tradeMode == -1) {
			new $Y.confirm({
	            ok:{title:'确定'},
	            content:'该医院暂未开通手机支付，请到医院窗口进行缴费。'
	        });
    		return false;
		} else {
			$('#tradeMode').val(tradeMode);
		}
	}
}

$(function() {
	
});