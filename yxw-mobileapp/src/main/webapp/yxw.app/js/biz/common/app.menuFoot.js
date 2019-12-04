var menuFoot = {
	urls: [ 'easyhealth/index', 'easyhealth/searchIndex', 'app/msgcenter/msgCenterListView', 'easyhealth/userCenterIndex' ],
	init: function() {
		menuFoot.bindEvent();
	},
	bindEvent: function() {
		$('.ui-menu-footer>.flex>a').off('click').on('click', function() {
			var menuCode = $(this).attr('menuCode');
			if ($(this).parent().find('.active').attr("menuCode") == menuCode) {
				console.log('已经在了，不重新刷');
				return false;
			}

			var url = base.appPath + menuFoot.urls[Number(menuCode)];
			go(url + "?openId=" + base.openId + "&appCode=" + base.appCode + "&menuCode=" + menuCode, true);
		})
	},
	initSocket: function() {
		var enableWebSocket = $('#enableWebSocket').val();
		if (enableWebSocket && enableWebSocket == '1') {
			// socket通过get请求
			var params = "?openId=" + base.openId + "&areaCode=" + base.areaCode + "&appCode=" + base.appCode;

			if ('WebSocket' in window) {
				websocket = new WebSocket("ws://" + window.location.host + "/" + $('#commonWebSocketPath').val() + params);
			} else if ('MozWebSocket' in window) {
				websocket = new MozWebSocket("ws://" + window.location.host + "/" + $('#commonWebSocketPath').val() + params);
			} else {
				websocket = new SockJS("http://" + window.location.host + "/" + $('#sockJSWebSocketPath').val() + params);
			}

			websocket.onmessage = function(event) {
				menuFoot.handleMessage(JSON.parse(event.data));
			};
			/*
			websocket.onopen = function(event) {
			};
			websocket.onerror = function(event) {
			};
			websocket.onclose = function(event) {
			};
			*/
		} else {
			console.log('配置不开启websocket');
		}
	},
	handleMessage: function(data) {
		if (data.messageType == "TEST") {
			// 测试消息，不予理会
			console.log("TEST");
		} else if (data.messageType == "MSGPUSH") {
			console.log("MSGPUSH");
			menuFoot.handleMsgPush(JSON.parse(data.content));
		}
	},
	handleMsgPush: function(data) {
		if (data.hasUnread) {
			$('div.ui-menu-footer>div.flex>a[menuCode="2"]>i').addClass('unread');
		}
	}
}

$(function() {
	menuFoot.init();
	menuFoot.initSocket();
})