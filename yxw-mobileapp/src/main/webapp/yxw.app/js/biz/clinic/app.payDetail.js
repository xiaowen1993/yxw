var payDetail = {
	loadData: function(){
		var now = new Date();
		var timeTemp = now.getTime();
		var url = base.appPath + "app/clinic/detail/getDatas?timeTemp=" + timeTemp;
		var datas = $("#voForm").serializeArray();
		console.log(datas);
		$Y.loading.show('正在为您加载数据');
		$.ajax({
	       	type : 'POST',  
	       	url : url,  
	       	data : datas,  
	       	dataType : 'json',  
	       	timeout:120000,
	       	error: function(data) {
	       		$Y.loading.hide();
	       		//payDetail.showNoData();
	       		$('#voForm').attr('action', base.appPath + 'app/clinic/payDetail?timeTemp=' + timeTemp);
				$('#voForm').submit();
	       	},
	       	success: function(data) {
	       		console.log(data);
	       		$Y.loading.hide();
	       		if (data.status == 'OK' && data.message) {
	       			
	       			var isSupportForbiddenPayment = $('#isSupportForbiddenPayment').val();
	       			var isAccessClinic = $('#isAccessClinic').val();
	       			
	       			if (isSupportForbiddenPayment == '0' && isAccessClinic == '0') {
	       				// 不代煎，不限制
	       				payDetail.formatData(data.message);
	       			} else if (isAccessClinic == '0' && isSupportForbiddenPayment == '1' ) {
	       				// 只限制配送
//	       				payDetail.formatDataPayment(data.message);
	       			} else if (isSupportForbiddenPayment == '0' && isAccessClinic == '1') {
	       				// 只代煎
//	       				payDetail.formatDataFried(data.message);
	       			} 
	       			
	       		} else {
	       			payDetail.showNoData();
	       		}
	       	}
		});
	},
	formatData: function(data) {
		$('#pay-detail').html('');
		$('#pay-medicare').html('');
		$('#pay-infos').html('');
		
		if (data.list.length > 0) {
			// 详情
			payDetail.addDetailDatas(data.list );
			// 医保结算 -- 暂时不加入
//			 payDetail.addMedicareSupport();
			// 支付按钮
			payDetail.addPayButton();
		} else {
			payDetail.showNoData();
		}
	},
	addDetailDatas: function(data) {
		var sHtml = '';
		
		sHtml += '<div class="box-list accordion js-accordion">';
		// 添加明细项
		$.each(data, function(i, item) {
			sHtml += '<div class="acc-li" data-id="' + item.itemId + '">';
			sHtml += '	<div class="acc-header js-acc-header">' + item.itemName;
			
			if (item.itemTotalFee) {
				sHtml += '		(<span class="yellow">' + Number(item.itemTotalFee / 100).toFixed(2) + '</span>元)';
			}
			
			sHtml += '	</div>';
			sHtml += '	<ul class="acc-content">';
			sHtml += '		<li class="item">';
            sHtml += '		    <div class="label">规格</div>';
            sHtml += '  		<div class="values">' + (item.itemSpec ? item.itemSpec : '---') + '</div>';
            sHtml += '		</li>';
            sHtml += '		<li class="item">';
            sHtml += '		    <div class="label">单位</div>';
            sHtml += '			<div class="values">' + (item.itemUnit ? item.itemUnit : '---') + '</div>';
            sHtml += '		</li>';
            sHtml += '		<li class="item">';
            sHtml += '		    <div class="label">单价</div>';
            sHtml += '			<div class="values">' + (item.itemPrice ? (Number(item.itemPrice / 100).toFixed(2) + '元') : '---') + '</div>';
            sHtml += '		</li>';
            sHtml += '		<li class="item">';
            sHtml += '			<div class="label">数量</div>';
            sHtml += '			<div class="values">' + (item.itemNumber ? item.itemNumber : '---') + '</div>';
            sHtml += '		</li>';
			sHtml += '	</ul>';
			sHtml += '</div>';
		});
		sHtml += '</div>';
		
		$('#pay-detail').html(sHtml);
		
		$('.js-accordion .js-acc-header ').click(function(){
			if($(this).hasClass('show')){
				$(this).removeClass('show')
			}else{
				$(this).addClass('show')
			}

		});
	},
	addMedicareSupport: function() {
		var supportMedicare = $('#supportMedicare').val();
		if (supportMedicare && supportMedicare == '1') {
			var sHtml = '';
			sHtml += '<div class="box-list switch">';
		    sHtml += '	<ul class="yx-list">';
		    sHtml += '	  <li>';
		    sHtml += '	    <div class="label">是否进行医保结算</div>';
		    sHtml += '      <div class="values"><i class="icon-switch" onclick="payDetail.calcMedicareFee(this);"></i></div>';
		    sHtml += '    </li>';
		    sHtml += '	</ul>';
		    sHtml += '</div>';
		    
		    $('#pay-medicare').append(sHtml);
		    $('#pay-medicare').show();
		} else {
			$('#pay-medicare').hide();
		}
	},
	addPayButton: function() {
		var sHtml = '';
		sHtml += '<div class="section btn-w">';
	    sHtml += '	<div class="btn btn-ok btn-block" ontouchstart="$Y.hover.TouchOn(this)" ontouchend="$Y.hover.TouchOut(this)" onclick="payDetail.pay();">确认缴费</div>';
	    sHtml += '</div>';
	    $('#pay-infos').append(sHtml);
	},
	calcMedicareFee: function(obj) {
		if ($(obj).hasClass('icon-switch-right')) {
			$(obj).removeClass('icon-switch-right');
			console.log('关闭医保结算...');
		} else {
			$(obj).addClass('icon-switch-right');
			console.log('进行医保结算...');
		}
	},
	
	pay: function() {
		commonTrade.autoSetTradeMode();
		var appCode = $('#appCode').val();
		console.log($("#voForm").find("#tradeMode").val());
		$Y.loading.show('正在生成订单，请稍后...');
		var url = base.appPath + 'app/clinic/detail/generateOrder';
		var datas = $('#voForm').serializeArray();
		$.ajax({
	       	type : 'POST',  
	       	url : url,  
	       	data : datas,  
	       	dataType : 'json',  
	       	timeout:120000,
	       	error: function(data) {
	       		$Y.loading.hide();
	       		payDetail.showNoData();
	       	},
	       	success: function(data) {
	       		console.log(data);
	       		$Y.loading.hide();
	       		if (data.status == 'OK' && data.message) {
	       			// 下面是本机测试使用
	       			if (data.message.tradeUrl) {
	       				$("#payForm").attr('action', data.message.tradeUrl);
	       				$("#payForm").initFormData(data.message.pay);
		       			// 测试异常使用
		       			// $("#notifyMethodName").val("pangziInvoke");
		       			$("#payForm").submit();
	       			} else {
	       				new $Y.confirm({
				            ok:{title:'确定'},
				            content:'网络异常，订单生成失败，请稍后重试。'
				        });
	       			}
	       		} else {
	       			new $Y.confirm({
			            ok:{title:'确定'},
			            content:'网络异常，订单生成失败，请稍后重试。'
			        });
	       		}
	       	}
		});
	},
	
	showNoData: function() {
		var sHtml = '';
		
		sHtml += '<div id="success">';
		sHtml += '	<div class="noData">';
	    sHtml += '  	<img src="' + base.appPath + 'yxw.app/images/greenSkin/doll/yx-doll3.png" width=220/>';
	    sHtml += '	</div>';
	    sHtml += '	<div class="p color666">没有查到您的缴费明细信息</div>';
	    sHtml += '</ul>';
		
		$('#pay-detail').html('');
		$('#pay-detail').append(sHtml);
	}
	
};

$.fn.initFormData = function(data) {
	return this.each(function() {
		var elementDom;
        var elementDomName;
        if(!data || data== 'null') {
        	// 将值重置为默认值
        	this.reset(); 
        	return; 
        }
        
        $.each($(this).children(), function(i, item) {
        	console.log(item.name + "=" + data[item.name]);
        	if (data[item.name] != 'null') {
        		$(item).val(data[item.name]);
        	}
        	console.log(item.name + "=" + data[item.name] + ":" + $(item).val());
        });
	});
};

$(function() {
	payDetail.loadData();
	
	$('.js-accordion .js-acc-header ').click(function(){
		if($(this).hasClass('show')){
			$(this).removeClass('show')
		}else{
			$(this).addClass('show')
		}

	});
	
});
