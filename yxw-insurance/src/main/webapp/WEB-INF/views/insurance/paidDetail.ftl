<!DOCTYPE html>
<html>
<head>
  	<#include "/common/common.ftl">
    <title>缴费明细</title>
</head>
<body ontouchmove="$Y.hover.TouchMove(event)">
<div id="body">
    <div class="box-list">
        <ul class="yx-list">
        	<li class="flex">
                <div class="flexItem w120">医院</div>
                <div class="flexItem color666 textRight">${commonParams.hospitalName}</div>
            </li>
			<li class="flex">
                <div class="flexItem w120">就诊人</div>
                <div class="flexItem color666 textRight">${commonParams.encryptedPatientName}</div>
            </li>
            <li class="flex">
                <div class="flexItem w120">卡号</div>
                <div class="flexItem color666 textRight">${commonParams.cardNo}</div>
            </li>
            <li class="flex">
                <div class="flexItem w120">处方号</div>
                <div class="flexItem color666 textRight">${commonParams.omitMzFeeId}</div>
            </li>
            <#if commonParams.medicareType != '自费'>
            <li class="flex">
                <div class="flexItem w120">总金额</div>
                <div class="flexItem color666 textRight"><span class="yellow fontSize140">${(commonParams.totalFee / 100)?string('0.00')}</span> 元</div>
            </li>
            <li class="flex">
                <div class="flexItem w120">统筹金额</div>
                <div class="flexItem color666 textRight"><span class="yellow fontSize140">${(commonParams.medicareFee / 100)?string('0.00')}</span> 元</div>
            </li>
	        </#if>
            <li class="flex">
                <div class="flexItem w120">付款金额</div>
                <div class="flexItem color666 textRight"><span class="yellow fontSize140">${(commonParams.payFee / 100)?string('0.00')}</span> 元</div>
            </li>
            <#if commonParams.clinicStatus == 30>
            <li class="flex">
                <div class="flexItem w120">退费金额</div>
                <div class="flexItem color666 textRight"><span class="yellow fontSize140">${(refundFee / 100)?string('0.00')}</span> 元</div>
            </li>
            </#if>
        </ul>
    </div>
    <#-- 显示条形码 -->
	<#if rule.isShowBarcode == 1>
		<div class="qrcode">
			<div id="barcodeTarget" class="barcodeTarget" style="margin: 0 auto;"></div>
		</div>
	</#if>
	
	<#--
    <#if rule.isShowBarcode == 1>
    	<#if rule.barcodeData == 1 && (commonParams.barcode)?? && (commonParams.barcode != '')>
		    <div class="box-list" style="padding: .5em;">
		    	<div>条型码</div>
		        <div class="barCode">
		    		<div id="barcodeTarget" class="barcodeTarget" style="margin: 0 auto;"></div>
		    	</div>
		    </div>
	    </#if>
	    <#if rule.barcodeData == 2 && (commonParams.cardNo)?? && (commonParams.cardNo != '')>
	    	<div class="box-list" style="padding: .5em;">
		    	<div>条型码</div>
		        <div class="barCode">
		    		<div id="barcodeTarget" class="barcodeTarget" style="margin: 0 auto;"></div>
		    	</div>
		    </div>
	    </#if>
	</#if>
	-->
    
    <#-- 显示取药信息 -->
    <div class="box-list fff accordion js-accordion">
        <div class="acc-li">
            <div class="acc-header js-acc-header show">门诊温馨提示</div>
            <div class="acc-content show">
                <div class="p" id="clinicTips">
                    ${commonParams.hisMessage}
					<#if (commonParams.receiptNum?exists) && (commonParams.receiptNum?length gt 0)>
					<br />
					<span class="yellow">${commonParams.receiptNum}<span>
					</#if>
                </div>
            </div>
        </div>
    </div>
    
    <#-- 详情 -->
    
    <div id="paid-detail"></div>
		    <#if commonParams.isClaim !=1 >
		    <div class="btn-w">
		            <div class="btn btn-ok btn-block" id="toClinList">申请理赔</div>
		        </div>
		    </#if>
    	
    <div class="noRecord" style="display: none;">
        <div id="success">
            <div class="noData"><img src="${basePath}yxw.app/images/greenSkin/doll/yx-doll3.png" width="220"> </div>
            <div class="p color666">没有查询到缴费明细</div>
        </div>
    </div>
</div>
<form id="voForm" method="post">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}" />
	<input type="hidden" id="appCode" name="appCode" value="${commonParams.appCode}" />
	<input type="hidden" id="appId" name="appId" value="${commonParams.appId}" />
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}" />
	<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />
	<input type="hidden" id="cardType" name="cardType" value="${commonParams.cardType}" />
	<input type="hidden" id="orderNo" name="orderNo" value="${commonParams.orderNo}" />
	<input type="hidden" id="cardNo" name="cardNo" value="${commonParams.cardNo}" />
	<input type="hidden" id="hisOrdNum" name="hisOrdNum" value="${commonParams.hisOrdNum}" />
	<input type="hidden" id="receiptNum" name="receiptNum" value="${commonParams.receiptNum}" />
	<input type="hidden" id="barcode" name="barcode" value="${commonParams.barcode}" />
	<input type="hidden" id="mzFeeId" name="mzFeeId" value="${commonParams.mzFeeId}" />
	<input type="hidden" id="isShowBarcode" name="isShowBarcode" value="${rule.isShowBarcode}" />
	<input type="hidden" id="barcodeData" name="barcodeData" value="${rule.barcodeData}" />
	<input type="hidden" id="barcodeStyle" name="barcodeData" value="${rule.barcodeStyle}" />
	
</form>

<#include "/common/footer.ftl">
<script type="text/javascript" src="${basePath}yxw.app/js/common/nav-list.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/common/jquery-barcode.min.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/insurance/app.paidDetail.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/common/app.skipPages.js"></script>
<script>
	
	$(function(){
	//提交
	    $('#toClinList').off('click').on('click', function() {
	        var action="${basePath}api/claimType"; 
			$('#voForm').attr("action", action);
		 	$('#voForm').submit();
		});
		});
	
	function showNoData() {
		var sHtml = '';
		
		sHtml += '<ul id="clinic-list" class="yx-list">';
		sHtml += '	<li class="noData">';
	    sHtml += '  	<p class="color666" style="text-align: center">没有查到您的已缴费明细信息。</p>';
	    sHtml += '	</li>';
	    sHtml += '</ul>';
		
		$('#paid-detail').html('');
		$('#paid-detail').append(sHtml);
		$('#paid-detail').addClass('boxLi');
	}
	
</script>
</body>
</html>