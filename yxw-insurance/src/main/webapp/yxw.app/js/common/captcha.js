var captcha = function() {
    var _appCode;
    var _openId;
    var _module;
    var callbackFunc;
    var parentElement;
    return {
        init: function (appCode, openId, module, parentEle, callback) {
            _appCode = appCode;
            _openId = openId;
            _module = module;
            callbackFunc = callback;
            parentElement = parentEle;
        },

        create: function(parentElement) {
            var html = '<div id="captcha" align="center" style="margin: 10% 0;">'
                     + '    <div style="width: 180px; ">'
                     + '        <input type="text" placeholder="请输入验证码" id="code" style="padding: 0 5px; width: 100%;  height: 35px; line-height: 35px; border: 1px solid #c7c7c7; border-radius: 2px; font-size: 115%;">'
                     + '        <div id="captchaTip" style="text-align: left; color: #999; "></div>'
                     + '        <div><img id="captchaImg"  width="100%" height="40" alt="点击刷新验证码" style="margin-top: 10%; cursor:pointer; vertical-align: bottom;" /></div>'
                     + '        <div id="validateBtn" style="margin-top: 10%; width: 100%; height: 35px; background: #37b494;  color: #fff; border: 0;  border-radius:2px; font-size: 115%; box-shadow:none;">确认</div>'
                     + '    </div>'
                     + '</div>';
            $(parentElement).append(html);
        },

        bind: function () {
            $('#captchaImg').click(captcha.change);
            $('#validateBtn').click(captcha.validate);
        },

        show: function () {
            var captchaDiv = $('#captcha');
            if (captchaDiv.size() == 0) {
                captcha.create(parentElement);
                captcha.bind();
                $('#captchaImg').click();
            } else {
                $('#captcha').show();
            }
            $(parentElement).show();
        },

        hide: function () {
            $('#captcha').hide();
        },

        change: function () {
        	// 给予延迟属性
        	setTimeout(function() {
        		var timeNow = new Date().getTime();
                var url = base.appPath + 'app/captcha/get?appCode=' + _appCode + '&openId=' + _openId + '&module=' + _module + '&time=' + timeNow;
                $('#captchaImg').attr('src', url);
        	}, 200);
        },

        validate: function () {
            var code = $('#code').val();
            $.ajax({
                url: base.appPath + 'app/captcha/validate',
                data: {appCode: _appCode, openId: _openId, module: _module, code: code},
                dataType: "json",
                type: 'POST',
                timeout: '10000',
                error: function (XMLHQ, errorMsg) {
                    captcha.showTip("网络异常，请稍后重试");
                },
                success: function(data) {
                    if (data.status == 'OK') {
                        captcha.hide();
                        callbackFunc();
                    } else {
                        captcha.change();
                        captcha.showTip(data.message);
                    }
                }
            })
        },

        showTip: function (tip) {
            $('#captchaTip').text(tip);
        }
    }
}();