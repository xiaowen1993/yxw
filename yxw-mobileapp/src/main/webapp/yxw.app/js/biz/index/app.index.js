var appIndex = {
	/**
	 * 菜单跳转
	 * @param url
	 */
	optionalClick: function(url) {
		var basePath = base.appPath;
		var openId = base.openId;
		var appCode = base.appCode;
		var areaCode = base.areaCode;
		
		go(basePath + url + "?openId=" + openId + "&appCode=" + appCode + "&areaCode=" + areaCode, true);
	},
	/**
	 * PHP跳转
	 * @param url
	 * @param appCode
	 */
	phpOptionalClick: function(url, appCode) {
		var openId = base.openId;
		if (!appCode) {
			appCode = 'easyHealth';
		}

		go(gotoPHPModule(openId, appCode, url), true);
	},
	go: function(url, boolean) {
		go(url, boolean);
	},
	init: function() {
	
		var elem = document.getElementById('mySwipe');
		window.mySwipe = Swipe(elem);
		
        // img点击
		$('#slides img').off('click').on('click', function() {
			var sHref = $(this).attr('data-href');
			if (sHref && sHref.trim().length > 0) {
				window.location.href = sHref + "&openId=" + base.openId;
			}
		});
	}
}

$(function() {
	appIndex.init();
});