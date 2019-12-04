var chooseNoSubDept = {
		init : function(){
			 /**初始化高度 必须要 start*/
			 var b_height =  $('.keyword-bar').height() || 0;
		     var ksHeight = window.innerHeight -73-53-b_height+10;
		     $('#ks-list').height(ksHeight);
		     /**初始化高度 必须要 end*/
		     
			/**初始化绑定事件*/
			$('#hadRegDoctors a').on('click', function(event){
				event.stopPropagation();
				event.preventDefault();
				
				chooseNoSubDept.goDoctorRegSource(this);
			});
			
			$('#right-list-main ul li').on('click', function(){
				var o = $(this);
				o.addClass('hover');
				setTimeout($.proxy(function(){
					o.removeClass('hover');
					chooseNoSubDept.toRegSourceInfo(this);
				}, this), 250);
			});
			
			$('#search').on('click', function(){
				chooseNoSubDept.search();
			});
		},
		goDoctorRegSource : function(domObj){
			var domId = $(domObj).attr("id");
	        if(domId){
	            var deptCode = domId.split(":")[0];
	            var doctorCode = domId.split(":")[1];
	            var deptName =  domId.split(":")[2];
	           
	            $("#deptCode").val(deptCode);
	            $("#doctorCode").val(doctorCode);
	            $("#deptName").val(deptName);
	            $("#paramsForm").attr("action" , base.appPath + "easyhealth/register/doctor/index?isSearchDoctor=1");
	            $("#paramsForm").submit();
	        }
		},
		
		toRegSourceInfo : function(domObj){
	        $("#deptCode").val($(domObj).attr("id"));
	        $("#deptName").val($(domObj).attr("name"));
	        $("#paramsForm").attr("action" , base.appPath + "easyhealth/register/doctor/index");
	        $("#paramsForm").submit();
		},
		
		search : function(){
			var searchCode = $("#searchCode").val();
	        if(searchCode && searchCode == "1"){
	        	chooseNoSubDept.goSearchDoctor();
	        }else if(searchCode && searchCode == "2"){
	        	chooseNoSubDept.goSearchDept();
	        }
		},
		
		goSearchDoctor : function(){
			$("#searchType").val("doctor");
			$("#paramsForm").attr("action" , base.appPath + "easyhealth/register/doctor/searchIndex");
			$("#paramsForm").submit();
		},
		
		goSearchDept : function(){
			$("#searchType").val("dept");
			$("#paramsForm").attr("action" , base.appPath + "easyhealth/register/doctor/searchIndex");
			$("#paramsForm").submit();
		}
};

$(function() {
     chooseNoSubDept.init();
});

//禁用滑动
document.addEventListener('touchmove', function (e) {
    if(e.target.nodeName == "BODY"){
        e.preventDefault();
        return false
    }
}, false);