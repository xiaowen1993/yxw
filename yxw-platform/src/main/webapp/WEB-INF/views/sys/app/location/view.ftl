<html>
<head>
    <#include "/sys/common/common.ftl">
    <title>定位管理</title>
    <style>
    	label.checkboxTwoAll.twoStyle.radioCheck {
		    background: url(${basePath}images/icon/checkBox.png) 0 -241px no-repeat;
		}
    </style>
</head>
<body>
<div id="content">
	<#include "./sys/common/hospital_setting.ftl">
	
    <div id="content-header">
        <div class="widget-title"><h3 class="title">定位管理</h3></div>
    </div>

    <div class="container-fluid">
    	<div class="space20"></div>
    	<div class="row-fluid">
        	<div class="pull-left">
                <ul class="tabs">
                    <#list platforms as platform>
                    	<#if platform.state == 1>
                    		<li><a href="#tab${platform_index + 1}" data-code="${platform.code}" style="width: 150px;">${platform.name}</a></li>
                    	</#if>
                    </#list>
                </ul>
            </div>
        </div>
        <div class="space10"></div>
        <#list platforms as platform>
        	<#if platform.state == 1>
            <div class="widget-box bangKa tab_content" id="tab${platform_index + 1}">
                <div class="space10"></div>
                <div class="widget-box">
					<div class="widget-content">
						<div class="row-fluid">
							<!--内容-->
							<div class="row-layout">
								<form class="form-horizontal" id="form_${platform_index + 1}" data-code="${platform.code}">
									<#if areas?exists>
										<#list areas as area1>
											<#if area1.childAreas?size &gt; 0>
												<div class="city-list <#if area1_index &gt;= 0>show</#if> form-check">
													<div class="city-title">
														<span class="dropdown js-dropdown-city"><i class="caret"></i></span>
														<label class="checkboxTwoAll twoStyle inline <#if appLocationMap[area1.idPath]?exists>check</#if>"> 
															<input type="checkbox" class="js-checkboxAll" name="all" value="${area1.idPath}" data-city-code="${area1.cityCode}" data-name="${area1.shortName}" data-level="${area1.level}" data-pinyin="${area1.pinyin}">${area1.shortName}
														</label>
													</div>
													<div class="city-content">
													<#list area1.childAreas as area2>
														<span class="city-li">
															<label class="checkboxTwo inline <#if appLocationMap[area2.idPath]?exists>check</#if>"> 
																<input type="checkbox" value="${area2.idPath}" data-city-code="${area2.cityCode}" data-name="${area2.shortName}" data-level="${area2.level}" data-pinyin="${area2.pinyin}">
																${area2.shortName}
															</label>
														</span>
													</#list>
													</div>
												</div>
												<div class="space20"></div>
											</#if>
										</#list>
									</#if>
								</form>
							</div>
							<!--内容 end-->
						</div>
					</div>
				</div>
            </div>
            </#if>
		</#list>
        <div class="row-fluid">
            <div class="footer-tool">
                <div class="row-fluid">
                    <button class="btn btn-save" onclick="$app.location.save();">保存</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
<script>
    $(function(){
        $('.js-dropdown-city').click(function(){
            var parents = $(this).parents('.city-list');

            if(parents.hasClass('show')){
                parents.removeClass('show');
            }else{
                parents.addClass('show');
            }
        });
	    
	    $('.tabs li a').removeClass('select').eq(0).addClass('select');
	    $(".tab_content" ).hide();
    	$(".tab_content:first" ).show();
    	
    	$("ul.tabs li" ).click( function () {
            $("ul.tabs li").removeClass("active");  //Remove any "active" class
            $(this).addClass("active");  //Add "active" class to selected tab
            $(".tab_content").hide();  //Hide all tab content
            var  activeTab = $( this ).find( "a" ).attr( "href" );  //Find the rel attribute value to identify the active tab + content
            $(activeTab).fadeIn();  //Fade in the active content
            return   false ;
        });
    })
    
    $('.tabs li a').click(function(){
    	$(this).addClass('select');
    	$(this).parent().siblings().children().removeClass('select');
    });
</script>

<script src="${basePath}js/sys/app/location/sys.app.location.js"></script>

</body>
</html>