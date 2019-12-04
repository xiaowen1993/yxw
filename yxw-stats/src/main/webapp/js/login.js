var login = {
		confirm : function(){
			var params = {userName: $('input[name="userName"]').val(), password: $('input[name="password"]').val()};
			$.ajax({
				type : "post",
				url : "/confirm",
				data: params,
				cache:false, 
				dataType:"json", 
				error : function(XMLHttpRequest, textStatus, errorThrown) {
	            //alert("系统繁忙，请稍候再试");
				},
				success : function(data, textStatus) {
					console.log(data);
					if (data.status == 'OK') {
							if (data.message.login_status == 'true') {
								window.location.href = "/index";
							}else {
								$('#message').show().text("用户名或密码错误");
							}
					}else {
						$('#message').show().text("用户名或密码错误");
					}
				}
	 		});
			
		},
		logout: function(){
			$.ajax({
				type : "post",
				url : "/logout",
				data: {},
				cache:false, 
				dataType:"json", 
				error : function(XMLHttpRequest, textStatus, errorThrown) {
	            //alert("系统繁忙，请稍候再试");
				},
				success : function(data, textStatus) {
					console.log(data);
					if (data.status == 'OK') {
							if (data.message.login_status == 'true') {
								window.location.href = "/login";
							}else {
								window.location.href = "/login";//暂时先跳到登录
							}
					}else {
						window.location.href = "/login";//暂时先跳到登录
					}
				}
	 		});
		},
		init : function(){
			if ($('#confirm').length > 0) {
				$('#confirm').click(function(event){
					event.stopPropagation();
					event.preventDefault();
					
					login.confirm();
				});
			}else {
				$('#logout').click(function(event){
					event.stopPropagation();
					event.preventDefault();
					
					login.logout();
				});
			}
		}
};

$(function() {

	login.init();
});