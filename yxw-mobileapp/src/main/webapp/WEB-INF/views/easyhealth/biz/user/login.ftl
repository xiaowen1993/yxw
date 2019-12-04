<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>登录</title>
</head>
<style>
    html,body{height: 100%;overflow: hidden;

    background-color:#fff;
    padding: 0;
    margin: 0;
    }
    p{
        -webkit-margin-before: 0em;
        -webkit-margin-after: 0em;
    }
</style>
<body style="background-color: #fff;">
<div id="body">
	<div id="login-wrap">
	    <div class="loginBox">
	        <div class="thumbImg">
	            <div class="logo"><img src="${basePath}yxw.app/images/greenSkin/login/yx-logo.jpg" width="100" /> </div>
	        </div>
	        <div class="frm-box">
	        		<input type="hidden" id="appId" value="${appId}" />
	            <div class="frm-group">
	                <input type="text" class="frm-input" placeholder="账号" id="account" value="${user.account}" />
	                <i class="icon-login icon-phone">&#xe642;</i>
	            </div>
	            <div class="frm-group">
	                <input type="password" class="frm-input" placeholder="密码" id="password"/>
	                <i class="icon-login icon-password">&#xe60b;</i>
	            </div>
	            <div class="btn-w firstBtn-w login">
	                <button type="submit" class="btn btn-ok btn-block" onclick="loginBox()">登录</button>
	            </div>
	            <div class="btn-w register">
	                <button type="submit" class="btn btn-ok btn-block" onclick="loginGoView('${basePath}easyhealth/user/toRegister');">注册</button>
	            </div>
	            <!--<div class="btn-w register">
	                <button type="submit" class="btn btn-ok btn-block" onclick="loginGoView('${basePath}easyhealth/user/toRegister');">注册</button>
	            </div>-->
	            <div class="btn-w forget-pwd">
	                <span onclick="loginGoView('${basePath}easyhealth/user/toForgetpwdSendcode');">忘记密码？</span>
	            </div>
	        </div>
	    </div>
	</div>
	<div class="p-mask"></div>
	<form id="paramsForm" method="post">
		<input type="hidden" id="__src" name="__src" value="${__src}" />
		<input type="hidden" id="account" name="account" />
		<input type="hidden" id="passWord" name="passWord" />
		<input type="hidden" id="appCode" name="appCode" value="${appCode}" />
	</form>
</div>

<script  type="text/javascript">
	/**
	 * 设置Cookie
	 * 
	 * @param c_name
	 * @param value
	 * @param expiredays
	 *            过期天数
	 */
	function setCookie(c_name, value, expiredays) {
	  expiredays = expiredays ? expiredays : 30;// 默认30天
	  var exdate = new Date();
	  exdate.setDate(exdate.getDate() + expiredays);
	  document.cookie = c_name + "=" + escape(value) + ";expires=" + exdate.toGMTString() + ";path=/";
	}
	
	/**
	 * 获取Cookie
	 * 
	 * @param c_name
	 * @returns
	 */
	function getCookie(c_name) {
	  if (document.cookie.length > 0) {
	    c_start = document.cookie.indexOf(c_name + "=");
	    if (c_start != -1) {
	      c_start = c_start + c_name.length + 1;
	      c_end = document.cookie.indexOf(";", c_start);
	      if (c_end == -1) {
	        c_end = document.cookie.length;
	      }
	      return unescape(document.cookie.substring(c_start, c_end));
	    }
	  }
	  return "";
	}

	$(function() {
		// 退出后不在显示动画
		var firstLoginEasyHealth = getCookie("firstLoginEasyHealth");
		firstLoginEasyHealth = "false";
		if (firstLoginEasyHealth && firstLoginEasyHealth == "false") {
			$('.p-mask').hide();
		} else {
			$Y.animationIndexGif('loginBootImg');
	    setTimeout(function(){
	        $('.p-mask').fadeOut();
	    },4500);
	    
	    setCookie("firstLoginEasyHealth", "false");
		}
	})
</script>

<script src="${basePath}yxw.app/js/biz/user/eh.login.js?v=1.1.2" type="text/javascript"></script>
</body>
</html>
