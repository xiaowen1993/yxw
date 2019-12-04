<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>选择城市</title>
	<script src="${basePath}js/json_utils.js"></script>
</head>
<body>
<div id="body">
    <section class="gpsBox">

   		<div class="g-title">当前就医通服务已经开通的城市列表</div>
        <div class="g-header"><i class="iconfont icon-gps"></i>当前位置：<span id="gpsCity">&nbsp;</span></div>
        <div class="tab-content" >
            <div class="tab-nav J_tabNav">
                <ul>
                    <#--
                    <li class="active" go="">广东省</li>
                    <li go="">广西省</li>
                    <li go="">湖北省</li>
                    <li go="">湖北省</li>
                    <li go="">湖北省</li>
                    <li go="">湖北省</li>
                    <li go="">湖北省</li>
                    <li go="">湖北省</li>
                    <li go="">湖北省</li>
                    -->

					<#if appLocations?exists>
                        <#list appLocations as appLocation>
                        	<#if appLocation.childAppLocations?size gt 0>
                        		<li>${appLocation.name}</li>
                    		</#if>
                        </#list>
					</#if>
                </ul>
            </div>
            <div class="tab-menu J_tabMenu">
                <ul>
                	<#--
                    <li class="active">
                        <div class="tab-item">广州1</div>
                        <div class="tab-item">深圳1</div>
                        <div class="tab-item">汕头1</div>
                    </li>
                    <li>
                        <div class="tab-item">广州2</div>
                        <div class="tab-item">深圳2</div>
                        <div class="tab-item">汕头2</div>
                    </li>
                    <li>
                        <div class="tab-item">广州3</div>
                        <div class="tab-item">深圳3</div>
                        <div class="tab-item">汕头3</div>
                    </li>
                    <li>
                        <div class="tab-item">广州4</div>
                        <div class="tab-item">深圳4</div>
                        <div class="tab-item">汕头4</div>
                    </li>
                    <li>
                        <div class="tab-item">广州5</div>
                        <div class="tab-item">深圳5</div>
                        <div class="tab-item">汕头5</div>
                    </li>
                    -->

					<#if appLocations?exists>
                        <#list appLocations as appLocation>
                        	<#if appLocation.childAppLocations?size gt 0>
                        		<li>
                        		<#list appLocation.childAppLocations as childAppLocation>
                        			<div class="tab-item" 
                        				 onclick="cityClick('${childAppLocation.id}', '${childAppLocation.cityCode}', '${childAppLocation.name}');"
                        				 data-id-path="${childAppLocation.id}" 
                        				 data-city-code="${childAppLocation.cityCode}">${childAppLocation.name}</div>
                        		</#list>
                        		</li>
                    		</#if>
                        </#list>
					</#if>
                </ul>
            </div>
        </div>
    </section>

</div>
		
<#include "/easyhealth/common/footer.ftl">

<script>
    var isIOS = /Mac\s+OS/.test(navigator.userAgent); //是否苹果
    var isAndroid = /Android/.test(navigator.userAgent); //是否安卓
    var overscroll = function(el) {
        var y=0, y2=0;
        el.addEventListener('touchstart', function(event) {

            var top = el.scrollTop
                    , totalScroll = el.scrollHeight
                    , currentScroll = top + el.offsetHeight;
            if(top === 0) {
                el.scrollTop = 2
            } else if(currentScroll === totalScroll) {
                //el.scrollTop = top - 1
            }
            y =event.touches[0].pageY;
        })
        el.addEventListener('touchmove', function(evt) {
            y2 = evt.touches[0].pageY;
            if(y2-y>0 && el.scrollTop<=2){
                evt._isScroller = false;
                evt.preventDefault();
                evt.stopPropagation();
                return false
            }
            if(el.offsetHeight < el.scrollHeight & el.scrollTop>1){
                evt._isScroller = true;
            } else if (el.scrollTop<=1){
                evt.preventDefault();
                evt.stopPropagation();
                return false
            }
        })
    }
    overscroll(document.querySelector('.J_tabNav'));
    overscroll(document.querySelector('.J_tabMenu'));
    document.body.addEventListener('touchmove', function(evt) {
        //In this case, the default behavior is scrolling the body, which
        //would result in an overflow.  Since we don't want that, we preventDefault.
        if(!evt._isScroller) {
            evt.preventDefault();
            evt.stopPropagation();
            return false
        }
    })
    
    
    function cityClick(areaCode, cityCode, cityName) {
    	//$(".tab-item").css("background-color", "red");
    	
    	console.log(areaCode + " - " + cityCode + " - " + cityName);

    	setCookie("cookieCityCode", cityCode, 30);
    	setCookie("cookieCityName", cityName, 30);
    	
    	setAreaCode(areaCode, cityName);
    }
    
    function setAreaCode(areaCode, cityName) {
		$.ajax({
			type: 'POST',
			dataType: 'json',
			cache: false,
			timeout: 4000,
			url: appPath + 'easyhealth/index/changeAreaCode',
			data: {
				openId: '${sessionUser.id}',
				areaCode: areaCode
			},
			error: function(data) {
				$Y.loading.hide();
			},
			success: function(data) {
				console.log(areaCode + '设置成功！');
				
				//location.href = "../index?areaCode=" + areaCode;
		    	//alert("${basePath}easyhealth/index?areaCode=" + areaCode);
		    	//go("${basePath}easyhealth/index?areaCode=" + areaCode, true);

                setCity(cityName);

		    	
				 if(IS.isMacOS){
					try {
		              window.appCloseView(false);
		  			} catch (e) {
		              //  alert('IOS的方法出错');
		          	}
				 }else if(IS.isAndroid){
		          	try {
		              window.yx129.appCloseView(false);
		          	} catch (e) {
		           	  //   alert('Android的方法出错');
		          	}
		       	 }else{
		       		go("${basePath}easyhealth/index?areaCode=" + areaCode, true);
		         }
				 
			}
		});
	}
    
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
    
    $(function(){
        //切换
        $(document).on('click','.J_tabNav li',function(){
            var index = $(this).index();
            $('.J_tabNav li').removeClass('active');
            $(this).addClass('active');
            $('.J_tabMenu li').removeClass('active').eq(index).addClass('active');
        })
        
        $(".J_tabNav li:first").addClass("active");
        $(".J_tabMenu li:first").addClass("active");
        
        
        //匹配城市
        //var cityCode = "020";
        var cityCode = ""; //getCookie("cookieCityCode");

        if (!cityCode) {
        	var getCityResult = getCity();
			//$("#debug").text($json.toJSONString(getCityResult));
            //<textarea style="width: 100%; height: 50px;" id="debug"></textarea>

			cityCode = $json.toJSONObject(getCityResult).city_code;

        }
        if (cityCode) {

	        var city = $(".J_tabMenu").find("div[class='tab-item'][data-city-code='"+cityCode+"']");
	        if (city.length == 1) {
	        	console.log(cityCode + " match true");
	        	var cityName = city.text();
	        	var areaCode = city.attr("data-id-path");
	        	//$("#gpsCity").text(cityCode + " " + cityName);
	        	$("#gpsCity").text(cityName);

				if (!getCookie("cookieCityCode")) {

					var  myBox  =  new $Y.confirm({
						content: "自动切换到"+cityName+"?",
						ok: {
							title: "切换",
							click: function () {        //参数可为空，没有默认方法,不会自动关闭窗体，可用  myBox.close()来关闭
                                setCookie("cookieCityCode", cityCode, 30);
                                setCookie("cookieCityName", cityName, 30);

                                setAreaCode(areaCode, cityName);

								//setCity(cityName);
							}
						},
						cancel: {
							title: "取消",
							click: function () {       //参数可为空, 当为空时默认方法关闭窗体
								myBox.close();
							}
						}
					})

				}


	        	//setCity(cityName);
				//cityClick(areaCode, cityCode, cityName);


	        } else {
	        	console.log(cityCode + " match false");
	        	$("#gpsCity").text("当前城市不在服务区，请重选");
	        }
        } else {
        	console.log("getCity() return null");
        	$("#gpsCity").text("GPS定位失败，请重选");
        }
    })


</script>
</body>
</html>