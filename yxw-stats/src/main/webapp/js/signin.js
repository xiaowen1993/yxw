var login = {
		confirm : function(){
			var params = {userName: $('input[name="userName"]').val(), password: $('input[name="password"]').val()};
			$.ajax({
				type : "post",
				url : "/submit",
				data: params,
				cache:false, 
				dataType:"json", 
				error : function(XMLHttpRequest, textStatus, errorThrown) {
	            //alert("系统繁忙，请稍候再试");
				},
				success : function(data, textStatus) {
					console.log(data);
					if (data.status == 'OK') {
							if (data.message.login_states == 'true') {
								window.location.href = "/portal";
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
				url : "/signout",
				data: {},
				cache:false, 
				dataType:"json", 
				error : function(XMLHttpRequest, textStatus, errorThrown) {
	            //alert("系统繁忙，请稍候再试");
				},
				success : function(data, textStatus) {
					console.log(data);
					if (data.status == 'OK') {
							if (data.message.login_states == 'true') {
								window.location.href = "/signin";
							}else {
								window.location.href = "/signin";//暂时先跳到登录
							}
					}else {
						window.location.href = "/signin";//暂时先跳到登录
					}
				}
	 		});
		},
		
		
	    
		init : function(){
			getCookie();
			
			if ($('#confirm').length > 0) {
				$('#confirm').click(function(event){
					event.stopPropagation();
					event.preventDefault();
					
					setCookie();  
					 
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


function sCookie(c_name, value, expiredays) {
	  expiredays = expiredays ? expiredays : 30;// 默认30天
	  var exdate = new Date();
	  exdate.setDate(exdate.getDate() + expiredays);
	  document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())+";path=/";;
	}

function gCookie(c_name) {
	  if (document.cookie.length > 0) {
	    c_start = document.cookie.indexOf(c_name + "=");
	    if (c_start != -1) {
	      c_start = c_start + c_name.length + 1;
	      c_end = document.cookie.indexOf(";", c_start);
	      if (c_end == -1)
	        c_end = document.cookie.length;
	      return unescape(document.cookie.substring(c_start, c_end));
	    }
	  }
	  return "";
	}

function setCookie(){ 
    var userName = $("#userName").val(); 
    var password = $("#password").val(); 
    var checked = $("[name='checkbox']:checked");

    if(checked){ 
    	sCookie("userName",userName, 365 * 24 * 60 * 60);
    	sCookie("password",$.base64.encode(password), 365 * 24 * 60 * 60);
    }else{   
    	sCookie("password", null);   
    }    
}   

function getCookie(){   
    var userName = gCookie("userName"); 
    var password = gCookie("password"); 
    
    if(password){
       $("[name='checkbox']").attr("checked","true");  
    }  
    if(userName){
       $("#userName").val(userName);  
    }  
    if(password){
       $("#password").val($.base64.decode(password)); 
    }  
}   

$(function() {

	login.init();
});