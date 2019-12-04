var queueIndex = {
	init: function() {
		queueIndex.bindEvent();
	},
	bindEvent: function() {
		$('div.queues').off('click').on('click', function() {
			queueIndex.showQueue(this);
		});
	},
	bindEventAfterComplete: function() {
		$('.btn-w').off('click').on('click', function() {
			loadData();
		});
	},
	showQueue: function(obj) {
		var hospitalCode = $('#hospitalFilter').val();
		if (hospitalCode && hospitalCode != '0') {
			bizQuery.selectScreen(obj);
		}
	},
	formatMzQueueData: function(data) {
		var sHtml = '';
    	
    	$.each(data, function(i, item) {
    		if (i > 0) {
    			sHtml += '<div class="space15" style="background-color: #eee;"></div>';
    		}
    		
			sHtml += '<div class="houzhen-wrap">';
        	sHtml += '	<div class="houzhen-infoList">';
            sHtml += '		<ul class="yx-list">';
            sHtml += '			<li>';
            sHtml += '				<div class="label">您前面还有</div>';
            sHtml += '				<div class="values">';
            sHtml += '					<span>' + (stringUtils.getValue(item.frontNum)?stringUtils.getValue(item.frontNum):'---') + '</span>人';
            sHtml += '				</div>';
            sHtml += '			</li>';
            sHtml += '			<li>';
            sHtml += '				<div class="label">正在就诊</div>';
            sHtml += '				<div class="values blue">';
            sHtml += '					<span>' + (stringUtils.getValue(item.currentNum)?stringUtils.getValue(item.currentNum):'---') + '</span>号';
            sHtml += '				</div>';
            sHtml += '			</li>';
            sHtml += '			<li>';
            sHtml += '				<div class="label">排队号</div>';
            sHtml += '				<div class="values"><span>' + (stringUtils.getValue(item.serialNum)?stringUtils.getValue(item.serialNum):'---') + '</span>号</div>';
            sHtml += '			</li>';
            sHtml += '			<li>';
            sHtml += '				<div class="label">候诊时间</div>';
            sHtml += '				<div class="values">请提前30分钟到医院就诊</div>';
            sHtml += '			</li>';
            sHtml += '			<li>';
            sHtml += '				<div class="label">就诊医生</div>';
            sHtml += '				<div class="values">' + (stringUtils.getValue(item.doctorName)?stringUtils.getValue(item.doctorName):'---') + '</div>';
            sHtml += '			</li>';
            sHtml += '			<li>';
            sHtml += '				<div class="label">就诊科室</div>';
            sHtml += '				<div class="values">' + (stringUtils.getValue(item.deptName)?stringUtils.getValue(item.deptName):'---') + '</div>';
            sHtml += '			</li>';
            sHtml += '		</ul>';
        	sHtml += '	</div>';
    		sHtml += '</div>';
    	});
    	
    	$('.yxw-data').html('').append(sHtml);
    	$('.btn-w').show();
    	$('.yxw-data').show();
	},
	formatMedicineQueueData: function(data) {
		var sHtml = '';
    	
    	$.each(data, function(i, item) {
    		if (i > 0) {
    			sHtml += '<div class="space15" style="background-color: #eee;"></div>';
    		}
    		
			sHtml += '<div class="houzhen-wrap">';
        	sHtml += '	<div class="houzhen-infoList">';
            sHtml += '		<ul class="yx-list">';
            sHtml += '			<li>';
            sHtml += '				<div class="label">您前面还有</div>';
            sHtml += '				<div class="values"><span>' + (stringUtils.getValue(item.frontNum)?stringUtils.getValue(item.frontNum):'---') + '</span>人</div>';
            sHtml += '			</li>';
            sHtml += '			<li>';
            sHtml += '				<div class="label">正在就诊</div>';
            sHtml += '				<div class="values blue">' + (stringUtils.getValue(item.currentNum)?stringUtils.getValue(item.currentNum):'---') + '号</div>';
            sHtml += '			</li>';
            sHtml += '			<li>';
            sHtml += '				<div class="label">排队号</div>';
            sHtml += '				<div class="values"><span>' + (stringUtils.getValue(item.serialNum)? stringUtils.getValue(item.serialNum):'---') + '</span>号</div>';
            sHtml += '			</li>';
            sHtml += '			<li>';
            sHtml += '				<div class="label">药房名称</div>';
            sHtml += '				<div class="values">' + (stringUtils.getValue(item.pharmacy)?stringUtils.getValue(item.pharmacy):'---') + '</div>';
            sHtml += '			</li>';
            sHtml += '			<li>';
            sHtml += '				<div class="label">药房地点</div>';
            sHtml += '				<div class="values">' + (stringUtils.getValue(item.localtion)?stringUtils.getValue(item.localtion):'---') + '</div>';
            sHtml += '			</li>';
            sHtml += '		</ul>';
        	sHtml += '	</div>';
    		sHtml += '</div>';
    	});
    	
    	$('.yxw-data').html('').append(sHtml);
    	$('.btn-w').show();
    	$('.yxw-data').show();
	},
	formatCheckQueueData: function(data) {
		var sHtml = '';
    	
    	$.each(data, function(i, item) {
    		if (i > 0) {
    			sHtml += '<div class="space15" style="background-color: #eee;"></div>';
    		}
    		
			sHtml += '<div class="houzhen-wrap">';
        	sHtml += '	<div class="houzhen-infoList">';
            sHtml += '		<ul class="yx-list">';
            sHtml += '			<li>';
            sHtml += '				<div class="label">您前面还有</div>';
            sHtml += '				<div class="values"><span>' + (stringUtils.getValue(item.frontNum)?stringUtils.getValue(item.frontNum):'---') + '</span>人</div>';
            sHtml += '			</li>';
            sHtml += '			<li>';
            sHtml += '				<div class="label">正在就诊</div>';
            sHtml += '				<div class="values blue">' + (stringUtils.getValue(item.currentNum)?stringUtils.getValue(item.currentNum):'---') + '号</div>';
            sHtml += '			</li>';
            sHtml += '			<li>';
            sHtml += '				<div class="label">排队号</div>';
            sHtml += '				<div class="values"><span>' + (stringUtils.getValue(item.serialNum)?stringUtils.getValue(item.serialNum):'---') + '</span>号</div>';
            sHtml += '			</li>';
            sHtml += '			<li>';
            sHtml += '				<div class="label">名称</div>';
            sHtml += '				<div class="values">' + (stringUtils.getValue(item.checkName)?stringUtils.getValue(item.checkName):'---') + '</div>';
            sHtml += '			</li>';
            sHtml += '			<li>';
            sHtml += '				<div class="label">执行科室</div>';
            sHtml += '				<div class="values">' + (stringUtils.getValue(item.deptName)?stringUtils.getValue(item.deptName):'---') + '</div>';
            sHtml += '			</li>';
            sHtml += '			<li>';
            sHtml += '				<div class="label">科室地址</div>';
            sHtml += '				<div class="values">' + (stringUtils.getValue(item.deptLocation)?stringUtils.getValue(item.deptLocation):'---') + '</div>';
            sHtml += '			</li>';
            sHtml += '		</ul>';
        	sHtml += '	</div>';
    		sHtml += '</div>';
    	});
    	
    	$('.yxw-data').html('').append(sHtml);
    	$('.btn-w').show();
    	$('.yxw-data').show();
	}
}

$(function() {
	queueIndex.init();
});

function doRefresh() {
	$('#voForm').attr('action', base.appPath + 'app/queue/index');
	$('#voForm').submit();
}

function loadData() {
	$('.btn-w').hide();
	$('.yxw-data').hide();
	$('#queueType').val($('div.queues.active').attr('queueType'));
	var datas = $('#voForm').serializeArray();
	$Y.loading.show('正在加载数据...');
	$.ajax({
		type: "POST",
		url: base.appPath + "app/queue/getQueueList",
		data: datas,
		cache: false,
		dataType: "json",
		timeout: 600000,
		error: function(data) {
			$Y.loading.hide();
			console.log('加载排队数据异常');
			bizQuery.showNoRecord();
		},
		success: function(data) {
			$Y.loading.hide();
			console.log(data);
			if (data.status == 'OK' && data.message && data.message.entityList && data.message.entityList.length > 0) {
				var queueType = $('#queueType').val();
				if (queueType == '1') {
					queueIndex.formatMzQueueData(data.message.entityList);
				} else if (queueType == '2') {
					queueIndex.formatCheckQueueData(data.message.entityList);
				} else if (queueType == '3') {
					queueIndex.formatMedicineQueueData(data.message.entityList);
				}
				
				queueIndex.bindEventAfterComplete();
			} else {
				bizQuery.showNoRecord();
			}
		}
	})
}