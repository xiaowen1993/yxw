<html>
<head>
	<#include "/sys/common/common.ftl">
	<script type="text/javascript" src="${basePath}js/sys/custom/sys.custom.js?v=1.3"></script>
    <title>客服中心</title>
</head>
<body>
<!--header str-->
	<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title">
            <h3 class="title">客服反馈</h3>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
        	<div class="row-fluid">
	            <div class="pull-right" >
                	<form method="post" action="">
            			<input type="hidden" name="pageNum" value="${feedBackList.pageNum}" />
            			<input type="hidden" name="pageSize" value="${feedBackList.pageSize}" />
            			<input type="hidden" name="pages" value="${feedBackList.pages}" />
            			<input type="hidden" name="total" value="${feedBackList.total}" />
                	</form>
                	<select name="feedbackSelect" id="feedbackSelect" class="mini" onchange="$custom.switchType(this.value)">
	                    <option value="1"    <#if type == "1">selected="selected"</#if>>医院反馈</option>
	                    <option value="2" <#if type == "2">selected="selected"</#if>>app建议意见</option>
                	</select>
                </div>
       		</div>
            <div class="widget-box bangKa tab_content" id="tab1">
                <div class="space10"></div>
                <div class="widget-content">
                    <div class="row-fluid">
                        <table class="table table-bordered table-textCenter table-striped table-hover">
                            <thead>
	                            <tr>
	                                <th width="300">医院反馈</th>
	                                <th width="100">OPENID</th>
	                                <th width="60">平台类型</th>
	                                <th width="60">回复内容</th>
	                                <th width="50"></th>
	                            </tr>
                            </thead>
                            <tbody>
                            	<#if (feedBackList.list?size>0)>
		                            <tbody>
		                            	<#list feedBackList.list as f>
			                                <tr>
			                                    <td>${f.feedback}</td>
			                                    <td>${f.openId}</td>
			                                    <td>${f.platformType}</td>
			                                    <td>${f.reply}</td>
			                                    <td> <li class="tab_pic"><a href="javascript:void(0);" onclick="$custom.addReply('${f.id}')">回复</a></li></td>
			                                </tr>
		                                </#list>
		                            </tbody>
                            	</#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        
     	<div class="pagination pagination-centered">
            <ul>
                <li><a href="javascript:;" onclick="$custom.changePage(${feedBackList.prePage});">上一页</a></li>
                <#if feedBackList.pages != 0>
	                <#list 1..feedBackList.pages as p>
	                	<#if feedBackList.pageNum == p>
	                		<li class="disabled"><span>${p}</span></li>
	                		<#else>
	                    	<li><a href="javascript:;" onclick="$custom.changePage(${p});">${p}</a></li>
	                  </#if>
	                </#list>
                </#if>
                <li><a href="javascript:;" onclick="$custom.changePage(${feedBackList.nextPage});">下一页</a></li>
            </ul>
            <div class="pageGoto">
                <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
                <a href="javascript:;" class="goto" onclick="$custom.changePage($('#skipPage').val());">跳转</a>
            </div>
      </div>
      <div class="space30"></div>
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