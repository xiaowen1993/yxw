var skipPages = {
		urls: {index: 'easyhealth/index', userCenterIndex: 'easyhealth/userCenterIndex' },
		menuCode: {index: 0, userCenterIndex: 3},
		forward : function(params){
			if (skipPages.urls[params]) {
				console.log(skipPages.urls[params]);
				var url = base.appPath + skipPages.urls[params];
				var code = skipPages.menuCode[params];
				if (code) {
					url = url +  "?openId=" + base.openId + "&appCode=" + base.appCode + "&menuCode=" + code;
				}else {
					url = url +  "?openId=" + base.openId + "&appCode=" + base.appCode;
				}
				
				go(url, true);
			}
		}
};

