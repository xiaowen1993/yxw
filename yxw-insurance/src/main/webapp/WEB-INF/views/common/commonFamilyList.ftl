<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
	<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> 
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
	<meta HTTP-EQUIV="expires" CONTENT="0"> 
    <title>${title}</title>
</head>
<body>
<div id="body">
	<div class="section">
        <div class="box-tips icontips"><i class="iconfont"></i>温馨提示：扫一扫医院的“报到二维码”即可自动报到，无需排队。本功能目前只在深圳市第三人民医院试运行，后续将支持更多医院。</div>
    </div>
	<div class="scanList">
        <div class="page-title">请选择要报到的就诊人</div>
        <div class="scanChoose">
            <ul class="yx-list">
                <#list entityList as family>
                <li class="scan_item <#if family.ownership == 1>show</#if>" familyId="${family.id}">
                	<i class="iconfont">&#xe69a;</i>${family.encryptedName}（${family.ownershipLabel}）
                </li>
	        	</#list>
            </ul>
        </div>
        <div class="btn-w">
            <div class="btn btn-ok btn-block" onclick="scanCode();">开始扫码</div>
        </div>
    </div>
</div>

<form id="voForm" method="post">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}" />
	<input type="hidden" id="appCode" name="appCode" value="${appCode}" />
	<input type="hidden" id="appId" name="appId" value="${commonParams.appId}" />
	<input type="hidden" id="areaCode" name="areaCode" value="${areaCode}" />
</form>

<!--<button type="button" onclick="doRefresh()">doRefresh</button>
<button type="button" onclick="doGoBack()">doGoBack</button>-->
<#include "/easyhealth/common/footer.ftl">
<script src="${basePath}yxw.app/js/common/nav-list.js" type="text/javascript"></script>
</body>
</html>
<script type="text/javascript">
	var appVersion = '';
	$(function(){
        $('.scan_item').click(function(){

            if($(this).hasClass('show')){
                $(this).removeClass('show');
            }else{
            	$('.scan_item').removeClass('show');
                $(this).addClass('show');
            }
        });
        
        appVersion = '';
        setTimeout(function(){
			appVersion = getVersion();
		}, 500);
        
    })
    
    function getVersion() {
		//android函数原型	window.yx129.appGetVersion();
		//ios 函数原型	appGetVersion();
		var appVersion = "未知";
		if(IS.isMacOS) {
	    	appVersion = appGetVersion();
	    } else if(IS.isAndroid) {
	      appVersion = window.yx129.appGetVersion();
	    }
		
		return appVersion;
	}
    
    function scanCode() {
    	// 验证
    	var obj = $('li.show');
    	if (obj.length == 0) {
    		myBox = new $Y.confirm({
		    	title:"",
		        content:"<div style='text-align: center;'>请先选择需要报告的就诊人</div>",
		        ok:{title:"我知道了",
		        	click:function(){ 
		            	myBox.close();
		            }
		        }
		    });
    	} else {
    		// 验证版本号 低于1.1.6版本(测试版本可能带T的)
    		var newVersion = '1.1.6_t';
    		if (appVersion.localeCompare(newVersion) == -1) {
    			myBox = new $Y.confirm({
			    	title:"",
			        content:"<div style='text-align: center;'>请先更新程序版本再使用此功能！</div>",
			        ok:{title:"我知道了",
			        	click:function(){ 
			            	myBox.close();
			            }
			        }
			    });
    		} else {
	    		var urlParams = '&familyId=' + $(obj).attr('familyId') + 
	    						'&openId=' + '${commonParams.openId}' + 
	    						'&appCode=' + '${appCode}' + 
	    						'&areaCode=' + '${areaCode}';
	    		if(IS.isMacOS){
					try {
		              window.appScan(urlParams);
		  			} catch (e) {
		            //  alert('IOS的方法出错');
		          	}
				}else if(IS.isAndroid){
		          	try {
		              window.yx129.appScan(urlParams);
		          	} catch (e) {
		           	//   alert('Android的方法出错');
		          	}
		       	}else{
		       		go(appPath + 'easyhealth/userCenterIndex');
		        }
		    }
    	}
    }
    
	function doRefresh() {
		$('#voForm').attr('action', '${basePath}mobileApp/common/familyList');
		$('#voForm').submit();
	}

	function doGoBack() {
		if(IS.isMacOS){
			try {
              window.appCloseView(true);
  			} catch (e) {
            //  alert('IOS的方法出错');
          	}
		}else if(IS.isAndroid){
          	try {
              window.yx129.appCloseView(true);
          	} catch (e) {
           	//   alert('Android的方法出错');
          	}
       	}else{
       		go(appPath + 'easyhealth/userCenterIndex');
        }
	}
</script>