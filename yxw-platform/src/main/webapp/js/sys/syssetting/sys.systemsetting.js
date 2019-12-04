var syssetting = {
	//全局配置
	toSysConfig:function(){
		window.location.href=$('#basePath').val()+"sys/systemSetting/index";
	},
	save:function()
	{
		var datas = $('#settingform').serializeArray();  
        jQuery.ajax( {  
        	type : 'POST',  
        	url : $('#basePath').val()+'/sys/systemSetting/save',  
        	data : datas,  
        	dataType : 'json',  
        	timeout:120000,
        	success : function(data) {  
        		alert("保存成功!");
        		$("#id").val(data.message.entityId);
        	},  
        	error : function(data) {  
        		alert("保存失败!");  
        	}  
        });  
	}
};