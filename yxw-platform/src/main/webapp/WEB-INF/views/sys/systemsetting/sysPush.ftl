<html>
<head>
	<#include "/sys/common/common.ftl">
    <title>系统全局规则</title>
    <script type="text/javascript" src="${basePath}js/sys/syssetting/sys.systemsetting.js"></script>
</head>
<body>
<!--header str-->
	<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title">
            <h3 class="title">系统全局规则</h3>
        </div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
        	<div class="space30"></div>
            <div class="widget-box bangKa tab_content" >
                <div class="space10"></div>
                <form class="form-horizontal evenBg" id="settingform">
                    <input type="hidden" id="id" name="id" value="${systemSetting.id}"/>
                    <div class="widget-content guaHao">
                        <div class="row-fluid">
                            <div class="control-box <#if systemSetting.finishUserInfo == 1>show<#else></#if>">
                                <div class="control-group w235">
                                    <label class="control-label">注册后是否推送完善信息的提示</label>
                                    <div class="controls ">
                                        <label class="radio inline yes <#if systemSetting.finishUserInfo == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> 
                                        <input type="radio" name="finishUserInfo" value="1" <#if systemSetting.finishUserInfo == 1>checked="checked"<#else></#if>>是</label>
                                        <label class="radio inline <#if systemSetting.finishUserInfo == 0>check<#else></#if>"> 
                                        <input type="radio" name="finishUserInfo" value="0" <#if systemSetting.finishUserInfo == 0>checked="checked"<#else></#if>>否</label>
                                    </div>
                                </div>
                                <div class="control-input">
                                    模版编码<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="finishUserInfoCode" value="${systemSetting.finishUserInfoCode}" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="space20"></div>
                    <button class="btn btn-save" type="button" onclick="syssetting.save();">保存</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!--content end-->
</body>
</html>
<script type="text/javascript">
   $(document).on('click','.js_yes',function(){
        if($(this).hasClass('check')){
            $(this).parents('.control-box ').addClass('show')
        }
    });
    $('.tabs li a').click(function(){
    	$(this).addClass('select');
    	$(this).parent().siblings().children().removeClass('select');
    });
    /*选中 是。 显示 模版编码*/
    $(document).on('click','input[type=radio]',function(){
        var p = $(this).parents('label');
        var pc = $(this).parents('.yes');
        var ps =  $(this).parents('.control-box');
        var name = $(this).attr('name');
        $('form input[name='+name+']').parents('label').removeClass('check');
        p.addClass('check');
        if(pc.hasClass('check')){
            ps.addClass('show');
        }else{
            ps.removeClass('show');
        }
    });
    $(document).ready( function () {
        //Default Action
        $( ".tab_content" ).hide();  //Hide all content
        $( "ul.tabs li:first" ).addClass( "active" ).show();  //Activate first tab
        $( ".tab_content:first" ).show();  //Show first tab content
        //On Click Event
        $( "ul.tabs li" ).click( function () {
            $( "ul.tabs li" ).removeClass( "active" );  //Remove any "active" class
            $( this ).addClass( "active" );  //Add "active" class to selected tab
            $( ".tab_content" ).hide();  //Hide all tab content
            var  activeTab = $( this ).find( "a" ).attr( "href" );  //Find the rel attribute value to identify the active tab + content
            $(activeTab).fadeIn();  //Fade in the active content
            return   false ;
        });
    });

</script>