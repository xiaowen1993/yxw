<!DOCTYPE html>
<html>
<head>
  	<#include "/common/common.ftl">
    <title>理赔专区</title>
</head>
<body>
<div id="body">
	<div class="yxw-data">
		<div class="lipei-tips"><i class="lipei icon-sharp"></i> 请选择就诊订单进行理赔申请</div>
		<ul class="lipei-list2">
                <li>
                        <!--<div><input type="checkbox" class="ui-checkobx skinColor"></div>-->
                        <a href="${basePath}api/claimType">
                                <div class="main">
                                        <div class="info">
                                                <div>就诊人：晨曦</div>
                                                <div>项目：骨科门诊费用</div>
                                                <div>金额：<span class="cost">199.00</span></div>
                                        </div>
                                        <div class="r"> <i class="arrow"></i></div>
                                </div>
                                <div class="mate">.
                                        <span class="des">2017-05-05 14:02</span>
                                        <span class="cost">已缴费</span>
                                </div>
                        </a>
                </li>

        </ul>
		<!--
		<div class="btn-w">
            <div class="btn btn-ok btn-block" id="toClinList">申请理赔</div>
        </div>
		-->
    </div>
    <div id="commonTips" style="display: none;">
    </div>
</div>
<form id="voForm" method="post">
	<input type="hidden" id="openId" name="openId" value="" />
	<input type="hidden" id="appCode" name="appCode" value="" />
	<input type="hidden" id="appId" name="appId" value="" />
	<input type="hidden" id="areaCode" name="areaCode" value="" />
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="" />
	<input type="hidden" id="hospitalId" name="hospitalId" value="">
	<input type="hidden" id="orderNo" name="orderNo" value="" />
	<input type="hidden" id="moduleName" value="" />
</form>


<#include "/common/footer.ftl">
</body>
</html>
<script type="text/javascript">

	
	function formatData(data) {
		var sHtml = '';

		sHtml += '<ul class="yx-list">';
		$.each(data, function(i, item) {
			sHtml += '<li class="lock f_vertical ';
			if (item.statusLabel == '缴费成功' || item.statusLabel == '部分退费') {
				sHtml += 'arrow';
			}
			sHtml += ' boxTable flex" data-orderNo="' + item.orderNo + '" data-hospitalId="' + item.hospitalId + 
					 '" data-hospitalCode="' + item.hospitalCode + '" data-status="' + item.clinicStatus +  '">';
			sHtml += '	<div class="flexItem">';
			sHtml += '		<div class="name fontSize120">' + item.recordTitle + '－' + item.patientName + '</div>';
			sHtml += '		<div class="mate">' + item.hospitalName + '</div>';
			sHtml += '		<div class="mate">' + Number(item.payFee / 100).toFixed(2) + '元</div>';
			sHtml += '		<div class="time color999">' + item.payDate + '</div>';
			sHtml += '	</div>';
			sHtml += '	<div class="color999 flexItem w100 textRight vertical">';
			if (item.statusLabel == '缴费成功' || item.statusLabel == '部分退费') {
				sHtml += '	<div class="status color666">' + item.statusLabel;
			} else {
				if (item.statusLabel == '缴费失败') {
					sHtml += '	<div class="status red">' + item.statusLabel;
				} else {
					sHtml += '	<div class="status color666">' + item.statusLabel;
				}
			}
			sHtml += '	</div>';
			sHtml += '</li>'
		});
		sHtml += '</ul>';

		$('#payRecord').html('').append(sHtml);
		$('.yxw-data').show();
		paidIndex.bindEventAfterComplete();
    }
	
	
	function showNoRecord() {
		var sHtml = '';

		sHtml += '<div class="noRecord">';
		sHtml += '	<div id="success">';
		sHtml += '		<div class="noData"><img src="' + base.appPath + 'yxw.app/images/lipei/icon-nodata2.png" width="220"> </div>';
		sHtml += '		<div class="p color666">您还没有理赔申请单……</div>';
		sHtml += '	</div>';
		sHtml += '</div>';

		$('#commonTips').html('').append(sHtml);
		$('#commonTips').show();
	}
	
</script>