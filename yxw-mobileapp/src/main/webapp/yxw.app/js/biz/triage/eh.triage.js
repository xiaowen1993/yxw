/**
 * 疾病列表
 */
function goTrigeList(){
	var symptoms = "";
	jQuery("#symptomsUl :checkbox[class=checkBoxMask]:checked").each(function(idx,item){
		symptoms += jQuery(item).attr("value") + ",";
	});
	if(symptoms == ''){
		 var myBox = new $Y.confirm({
             title:"",
             content:"<div style='text-align: center;'>请选择症状.</div>",
             ok:{title:"确定",
                 click:function(){
                     myBox.close();
                 }
             }
         });
	}else{
		symptoms = symptoms.substring(0, symptoms.length - 1);
		jQuery("#symptoms").val(symptoms);
		jQuery("#triageIndexForm").attr("action",appPath + "smartTriage/trigeList");
		jQuery("#triageIndexForm").submit();
		//location.href = appPath + "smartTriage/trigeList?symptoms=" + symptoms;
	}
}

/**
 * 疾病详细
 * @param id
 */
function diseaseDetail(id,symptoms){
	jQuery("#id").val(id);
	jQuery("#triageListForm").attr("action",appPath + "smartTriage/triageDetail?symptoms="+symptoms);
	jQuery("#triageListForm").submit();
}