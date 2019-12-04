function gotoPHPModule(openId, appCode, toUrl) {
	if (openId && appCode && toUrl) {
		$Y.loading.show('正在为您加载数据...');
		var now = new Date();
		var timeTemp = now.getTime();
		var datas = $('#voForm').serializeArray();
		console.log(datas);
		$.ajax({
			url: base.appPath + toUrl + '?timeTemp=' + timeTemp,
			data: {
				openId: openId,
				appCode: appCode
			},
			type: "post",
			dataType: "json", 
			global: false,
			cache: false,
			timeout: 5000,
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				$Y.loading.hide();
				// 提示不能跳转
				myBox = new $Y.confirm({
	              title:"",
	              content:"<div style='text-align: center;'>网络异常,请保持您的网络通畅,稍后再试.</div>",
	              ok:{title:"确定",
	                  click:function(){
	                      myBox.close();
	                  }
	              }
	          });
			},
			success: function(data) {
				console.log(data);
				if (data.status == 'OK' && data.message.healthMonitorUrl) {
					$Y.loading.hide();
					// 跳转
					var item = data.message;
					var url = item.healthMonitorUrl + 
						'?controller=' + item.controller + 
						'&action=' + item.action + 
						'&goUrl=' + item.goUrl + 
						'&secret=' + item.secret +
						'&time=' + item.time + 
						'&module=' + item.module + 
						'&mtime=' + encodeURIComponent(item.mtime) + 
						'&name=' + encodeURIComponent(item.name) + 
						'&mobile=' + encodeURIComponent(item.mobile) + 
						'&idNo=' + encodeURIComponent(item.idNo);
					console.log('跳转PHP的地址：' + url);
					go(url, true);
				} else {
					$Y.loading.hide();
					// 提示不能跳转
					console.log('跳转失败！');
					myBox = new $Y.confirm({
		              title:"",
		              content:"<div style='text-align: center;'>跳转健康记录失败,请稍后再试.</div>",
		              ok:{title:"确定",
		                  click:function(){
		                      myBox.close();
		                  }
		              }
		          });
				}
			}
		});
	}
}