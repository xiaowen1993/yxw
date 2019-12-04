var chooseHasSubDept = {
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
				
				chooseHasSubDept.goDoctorRegSource(this);
			});
			
			$('#ks-left-list ul li').on('click', function() {
				chooseHasSubDept.select_ks(this);
			});
			
			chooseHasSubDept.bindSubDeptEvent();
			
			$('#search').on('click', function(){
				chooseHasSubDept.search();
			});
		},
		goDoctorRegSource : function(domObj){
			var domId = $(domObj).attr("id");
	        if(domId){
	            var deptCode = domId.split(":")[0];
	            var doctorCode = domId.split(":")[1];
	            
	            $("#deptCode").val(deptCode);
	            $("#doctorCode").val(doctorCode);
	            $("#paramsForm").attr("action" , base.appPath + "easyhealth/register/doctor/index?isSearchDoctor=1");
	            $("#paramsForm").submit();
	        }
		},
		select_ks : function(obj){
			var obj = $(obj);
	        var index = obj.index();
	        $('#ks-left-list li').removeClass('active');
	        obj.addClass('active');
	        var deptCode = $(obj).attr("id");
	        $("#deptCode").val(deptCode);
	        var url = base.appPath + "easyhealth/register/querySubDepts";
	        var datas = $("#paramsForm").serializeArray();  
	        $Y.loading.show();
	        jQuery.ajax( {  
	           type : 'POST',  
	           url : url,  
	           data : datas,  
	           dataType : 'json',  
	           timeout:120000,
	           success : function(data) { 
	               $Y.loading.hide();
	               var subDepts = data.message.subDepts; 
	               if(subDepts){
	                    var node = $("#subDeptList");
	                    var htmlStr = "";
	                    for(var i = 0 ; i < data.message.subDepts.length ; i++){
	                        htmlStr += "<li id='" + subDepts[i].deptCode + "'  name='" + subDepts[i].deptName + "'><i class='iconfont'>&#xe603;</i>" + subDepts[i].deptName + "</li>"
	                    }
	                    node.html(htmlStr);
					     $('#right-list-main .scroller')[0].scrollTop=0;
						
	               }
	               
	               chooseHasSubDept.bindSubDeptEvent();
	           },  
	           error : function(data) {  
	                $Y.loading.hide();
	                alert("网络异常,加载二级科室失败,请您重新操作!")  
	           }  
	        });  
		},
		
		bindSubDeptEvent : function(){
			$('#right-list-main ul li').on('click', function(){
				var o = $(this);
				o.addClass('hover');
				setTimeout($.proxy(function(){
					o.removeClass('hover');
					chooseHasSubDept.toRegSourceInfo(this);
				}, this), 250);
			});
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
	        	chooseHasSubDept.goSearchDoctor();
	        }else if(searchCode && searchCode == "2"){
	        	chooseHasSubDept.goSearchDept();
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
     chooseHasSubDept.init();
});

//禁用滑动
document.addEventListener('touchmove', function (e) {
    if(e.target.nodeName == "BODY"){
        e.preventDefault();
        return false
    }
}, false);