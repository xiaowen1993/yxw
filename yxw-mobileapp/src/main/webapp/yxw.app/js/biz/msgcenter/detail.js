/**
 *关闭窗口
 */
function closeView() {
	if (IS.isMacOS) {
		try {
			appCloseView();
		} catch (e) {
			alert('关闭页面失败');
		}
	} else if (IS.isAndroid) {
		try {
			window.yx129.appCloseView();
		} catch (e) {
			alert('关闭页面失败');
		}

	} else {
		go(appPath + 'easyhealth/user/toLogin');
	}
}

/**
 *跳链
 * @param {Object} path
 */
function chain(path) {
	var url = "";
	if(path.indexOf("&openId=")>-1){
		url=decodeURIComponent(path);
	}else{
		url= decodeURIComponent(path) + "&openId=" + $('#openId').val();
	}
	
	window.location.href = url;
}