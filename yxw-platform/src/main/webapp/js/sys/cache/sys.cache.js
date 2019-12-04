var cacheJS = {
	//重新获取最新数据
	reLoadDataToCache:function(regType){
		if(confirm("确定要重新获取最新数据吗?")){  
			$("#regType").val(regType);
			var url = $("#basePath").val() + "sys/cacheManage/reLoadDataToCache";
			var datas = $("#hospitalParamForm").serializeArray();
			jQuery.ajax( {  
	        	type : 'POST',  
	        	url : url,  
	        	data : datas,  
	        	dataType : 'json',  
	        	timeout:120000,
	        	success : function(data) {  
	        		if(data.message.isSuccess == 0){
	        			alert(data.message.msgInfo);  
	        			
	        			if($("#branchHospitalSelect")){
	        				 $("#branchHospitalSelect option[value='" + data.message.branchCode  + "']").attr("selected","selected");
	        			}
	        			if($("#cacheTypeSelect")){
	        				 $("#cacheTypeSelect option[value='" +  data.message.cacheType + "']").attr("selected","selected");
	        			}
	        			$("#hospitalId").val(data.message.commonParams.hospitalId);
	        			$("#hospitalCode").val(data.message.commonParams.hospitalCode);
	        			$("#hospitalName").val(data.message.commonParams.hospitalName);
	        			$("#branchCode").val(data.message.commonParams.branchCode);
	        			$("#cacheType").val(data.message.commonParams.cacheType);
	        			
	        			$("#hospitalParamForm").attr("action" ,$("#basePath").val()  + "sys/cacheManage/index");
	        			$("#hospitalParamForm").submit();
	        		}else{
	        			alert(data.message.msgInfo); 
	        		}
	        	},  
	        	error : function(data) {  
	        		alert("获取医院最新数据异常!");  
	        	}  
	        });  
		}
	},
	/*
	 * 缓存编辑
	 */
	cacheEdit:function(hospitalId){
		var cacheType = $("#cacheTypeSelect");
		if(!cacheType){
			cacheType = "hospital.app.interface";
		}else{
			cacheType = $("#cacheTypeSelect").val();
		}
		
		var branchCode = $("#branchHospitalSelect").val();
		
		$("#cacheType").val(cacheType);
		$("#branchCode").val(branchCode);
		$("#hospitalParamForm").attr("action" ,$("#basePath").val()  + "sys/cacheManage/index");
		$("#hospitalParamForm").submit();
	},

	//保存规则
	saveCache:function(formId , ruleType){
		
	}
};