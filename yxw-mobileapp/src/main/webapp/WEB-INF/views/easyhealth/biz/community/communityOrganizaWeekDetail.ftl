
<!DOCTYPE html>
<html>
<head>
	<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> 
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
	<meta HTTP-EQUIV="expires" CONTENT="0"> 
    <#include "/easyhealth/common/common.ftl">
	
	<script type="text/javascript" src="${basePath}yxw.app/js/common/nav-list.js?v=2"></script>
    <title>巡诊专家</title>
    <style>
    	.yx-list li{
		    display: -webkit-box;
		    display: -ms-flexbox;
		    display: -webkit-flex;
		    display: flex;
    	}
    	.accordion .acc-li .acc-header:before{
    		//content:""!important;
    		background:none!important;
    	}
    </style>
 <script type="text/javascript">
$(function(){
    var curDate;
    var aryDay = new Array("日","一","二","三","四","五","六");//显示星期

    //初始化日期加载
    function initDate() {
        curDate = new Date();
        var weekDay = curDate.getDay();//从Date对象返回一周中的某一天(0~6)
        var tdDT;//日期
        var html = '';
        var my_width = $(window).width()/7 -1;

        //显示一周的日期
        for(var i=0;i<7;i++) {
            tdDT = getDays()[i];
            var weekDay = tdDT.getDay();//星期几
            var w = aryDay[weekDay];
            var d =  tdDT.getDate();
            var newDay=i==6?0:i+1;
			if(newDay==curDate.getDay()+1){
				html +=' <div class="d-grid goHook active" style="width:'+my_width+'px" ><div class="d-grid-content"><input id="weekDay" type="hidden" value="'+weekDay+'"/><span class="week">'+ w +'</span><span class="date">'+ d +'</span></div><i class="iconfont"></i></div></div>';
			}else{
            	html +=' <div class="d-grid goHook" style="width:'+my_width+'px" ><div class="d-grid-content"><input id="weekDay" type="hidden" value="'+weekDay+'"/><span class="week">'+ w +'</span><span class="date">'+ d +'</span></div><i class="iconfont"></i></div></div>';
            }
        }
        $("#select-date").html(html);
    }

    //取得当前日期一周内的某一天
    function getWeek(i) {
        var now = new Date();
        var n = now.getDay()+1;
        var start = new Date();
        start.setDate(now.getDate() - n + i);//取得一周内的第一天、第二天、第三天...
        return start;
    }

    //取得当前日期一周内的七天
    function getDays() {
        var days = new Array();
        for(var i=1;i<=7;i++) {
            days[i-1] = getWeek(i);
        }
        return days;
    }

    initDate();
	var week="";
    $('#select-date div.goHook').click(function(){
        if(!$(this).hasClass('noNum')){
            $('.d-grid.goHook').removeClass('active');
            $('.d-grid.goHook .border-arrow').removeAttr('style')
            $(this).addClass('active')
			
        }
		week=$(this).find('#weekDay').val();
		getOrganizaByWeek(week,1);
        $Y.loading.show();
        $('.doctor-list').hide();
        setTimeout(function(){
            $Y.loading.hide();
            $('.doctor-list').show();
        },500)
    });

    getOrganizaByWeek("",1);
 }); 
function getOrganizaAddIndexSize(){
	var indexSize=$("#indexSize").val();
	if(indexSize==null || indexSize=="null" || indexSize==""){
		indexSize=2;
	}else{
	    indexSize=indexSize-0+1-0;
	}
	var week=$("#week").val();
	getOrganizaByWeek(week,indexSize);
}
function getOrganizaByWeek(week,indexSize){
	 $.ajax({
			type: "POST",
			url: "${basePath}easyhealth/communitycenter/communityHealth/getOrganizationSchmagByWeek",
			data: {
				week: week,
				indexSize: indexSize,
			},
			cache: false, 
			dataType: "json", 
			timeout: 600000,
			error: function(data) {
				showMessageBox('查询值班医生信息失败，请稍后重试。');
			},
			success: function(data) {
				console.log(data);
				if (data.status == 'OK') {
					addOrganizaHtml(data.message.organizationSchmagsList,data.message.indexSizeAdd);
					$("#week").val(data.message.week);
					$("#indexSize").val(data.message.indexSize);
				} else {
					showMessageBox('查询值班医生信息失败，请稍后重试。');
				}
			}
		})
	 
}
function addOrganizaHtml(organizationList,indexSizeAdd){
	$('#organizations').html("");
	var sHtml=""
	if (organizationList.length > 0) {
	 $.each(organizationList, function(i, organization) {
		sHtml+= '<li onclick="">';
		sHtml+= '<div class="li-right"><span class="tag skinBgColor label">';
		if(organization.timeSlot=="2"){
			sHtml+= "下午";
		}else{
			sHtml+= "上午";
		}
		sHtml+= '</span></div>';
        sHtml+= '<div class="pic"><img src="${basePath}yxw.app/images/touxiang.png"  width="65" height="65"/></div>';
        sHtml+= '<div class="info">';
        sHtml+= '<div class="title">'+organization.doctorName+'（'+organization.position+'）</div>';
        sHtml+= '<div class="mate"> '+organization.specialty+'</div>';
        sHtml+= '<div class="mate">'+organization.organizationName+'</div>';
        sHtml+= '</div></li>';
	 });
	 if(!indexSizeAdd){
	 	$("#addIndex").hide();
	 }else{
	 	
	 	$("#addIndex").show();
	 }
	    
	} else {	   
		sHtml+='<li class="noDataText"> 当前日期没有排班信息       </li>';
		$("#addIndex").hide();
	}
	$('#organizations').html(sHtml);
}
function showMessageBox(data) {
		
  $Y.tips(data,2000);
};
</script>
</head>
<body>

<div id="body">
	<div id="select-date" class="show dutyList"></div>
    <div class="space15" ></div>
    <!--<div class="box-tips fore"><i class="iconfont">&#xe60d;</i>温馨提示：超过16:00不可再挂当天的号。</div> -->
    <div class="doctor-list duty">
        <ul id="organizations">
        </ul>
        
    </div>
    <div class="btn-w" id="addIndex">
        <button type="button" onclick="getOrganizaAddIndexSize();" class="btn btn-ok btn-block">点击加载更多</button>
    </div>

</div>

<div id="copyright">  </div>
<form id="administrativeRegionFrom" method="post" action="${basePath}easyhealth/communitycenter/communityHealth/getCommunityHealthByAR" accept-charset="utf-8">
 <input type="hidden" id="indexSize" name="indexSize" />
 <input type="hidden" id="week" name="week" />
</form>
<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script type="text/javascript">
function doRefresh()
{
	window.location.href="${basePath}/easyhealth/communitycenter/communityHealth/getCommunityOrganizaWeekDetail";
}

function doGoBack()
{
	windowClose();
}
</script>