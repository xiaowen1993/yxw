<html>
<head>
	
	<#include "/sys/statistics/statisticsCommon.ftl">
	<script type="text/javascript" src="${basePath}js/sys/statistics/sys.statistics.js"></script>
	<script type="text/javascript" src="${basePath}js/dialog.js"></script>
    <title>社康中心-排班管理</title>
</head>

<script type="text/javascript">
	/*选中 变色*/
	function clickHit(obj){
		var bl = jQuery(obj).attr("class");
		jQuery(obj).parent().find("tr").removeClass("hit-class");
		if(bl){
			jQuery(obj).removeClass('hit-class');
		}else{
			jQuery(obj).addClass('hit-class');
		}
	};
	
	function deleteOrganization(id) {
		$Y.confirm ('确定删除？',function(Confirm){
            Confirm.close();
            deleteOrganizationAJKX(id);
        })
		
	};
	
	function deleteOrganizationAJKX(id){
	    $.ajax({
			type: "POST",
			url: "${basePath}sysSchmag/communitycenter/organizationSchmag/deleteOrganization",
			data: {
				id: id,
			},
			cache: false, 
			dataType: "json", 
			timeout: 600000,
			type: 'POST',
			error: function(data) {
				showMessageBox('删除失败，请稍后重试。');
			},
			success: function(data) {
				console.log(data);
				if (data.status == 'OK') {
					if (data.message.isSuccess == 'success') {
						showMessageBox('删除成功。');
						resultTable();
					} else {
						showMessageBox('删除失败，请稍后重试。');
					}
				} else {
					showMessageBox('删除失败，请稍后重试。');
				}
			}
		})
	}

	function suppyOrganziation() {
		$("#organizationName").val('');
		$("#organizationName").val($("#organizationNameQuert").val());
		$("#administrativeRegion").val($("#administrativeRegionQuert").val());

		var url="${basePath}sysSchmag/communitycenter/organizationSchmag/queryCcommunityHealth";

	    $("#schedulingRegionFrom").attr("action",url);
        $("#schedulingRegionFrom").submit();
	   
	};
	
	function showMessageBox(data) {
		
	  $Y.tips(data,2000);
	};
	

	
	function addOrganization() {
		var week=$("#week").val();
		if(!isNotNull(week)){
		   howMessageBox('星期 不能为空。');
		   return;
		}
    	var timeSlot=$("#timeSlot").val();
    	if(!isNotNull(timeSlot)){
		   showMessageBox('午段 不能为空。');
		   return;
		}
    	var doctorName=$("#doctorName").val();
    	if(!isNotNull(doctorName)){
		   showMessageBox('专家 不能为空。');
		   return;
		}
    	var position=$("#position").val();
    	if(!isNotNull(position)){
		   showMessageBox('职称 不能为空。');
		   return;
		}
    	var specialty=$("#specialty").val();
    	if(!isNotNull(specialty)){
		   showMessageBox('专科 不能为空。');
		   return;
		}
    	var hospitalName=$("#hospitalName").val();
    	if(!isNotNull(hospitalName)){
		   showMessageBox('指派医院 不能为空。');
		   return;
		}
		var id=$("#id").val();
		var organizationId=$("#organizationId").val();
		var organizationName=$("#organizationName").val();
		$.ajax({
			url: "${basePath}sysSchmag/communitycenter/organizationSchmag/addOrganization",
			data: {
				id: id,
				week: week,
				timeSlot: timeSlot,
				doctorName: doctorName,
				position: position,
				specialty: specialty,
				hospitalName: hospitalName,
				organizationId:organizationId,
				organizationName:organizationName,
			},
			cache: false, 
			dataType: "json", 
			timeout: 600000,
			type: 'POST',
			error: function(data) {
				showMessageBox('操作失败，请稍后重试。');
			},
			success: function(data) {
				if (data.status == 'OK') {
					if (data.message.isSuccess == 'success') {
						showMessageBox('操作成功。');
						resultTable();
					} else {
						showMessageBox('操作失败，请稍后重试。');
					}
				} else {
					showMessageBox('操作失败，请稍后重试。');
				}
			}
		})
		
    	
	
		resultTable();
	};
	function resultTable(){
		setTimeout(function() {			
		    var url="${basePath}sysSchmag/communitycenter/organizationSchmag/getOrganizationSchmagByCommunitId";
		    $("#schedulingRegionFrom").attr("action",url);
			$('#schedulingRegionFrom').submit();
		}, 1000);
	}
	
	function isNotNull(data){
	  if(data!=null && data!="" && data!="null" && week!="undefined"){
	  	return true;
	  }else{
	    return false;
	  }
	}
	//添加值班信息
    function addDuty(){
            new $Y.dialog({
                title:'修改排班',
                width:'1000px',
                height:'350px',
                content:'',
                callback:function(myBox){
                    $.ajax({
                        url:'${basePath}sysSchmag/communitycenter/organizationSchmag/getDialogAddDuty',
                        dataType:'html',
                        cache:false,
                        success:function(html){
                        	myBox.content(html);

                        	$(myBox.id).on('click','.btn-save',function(){
                           	  addOrganization();
                            });
                        	$(myBox.id).on('click','.btn-remove',function(){
                           	 myBox.close();
                            });
                        }
                    })
                }
            });
    }
    
    //修改值班信息
    function updateDuty(id,week,timeSlot,doctorName,position,specialty,hospitalName){
            new $Y.dialog({
                title:'添加排班',
                width:'1000px',
                height:'150px',
                content:'',
                callback:function(myBox){
                    $.ajax({
                        url:'${basePath}sysSchmag/communitycenter/organizationSchmag/getDialogAddDuty',
                        dataType:'html',
                        cache:false,
                        success:function(html){
                        	myBox.content(html);
                        	$("#id").val(id);
                        	$("#week").val(week);
                        	$("#timeSlot").val(timeSlot);
                        	$("#doctorName").val(doctorName);
                        	$("#position").val(position);
                        	$("#specialty").val(specialty);
                        	$("#hospitalName").val(hospitalName);
                        	$(myBox.id).on('click','.btn-save',function(){
                           	  addOrganization();
                            });
                        	$(myBox.id).on('click','.btn-remove',function(){
                           	 myBox.close();
                            });
                        }
                    })
                }
            });
    }
    
    function showMessageBox(data) {
		
	  $Y.tips(data,2000);
	};
</script>
<body>
	<#include "/easyhealth/biz/community/bsmag/communityStatisticsMenu.ftl">
	<div id="content" style="margin-top: -125px;">
	    <div id="content-header">
	    	<div class="widget-title">
	    		
	    	</div>
	        <div class="widget-title"><h3 class="title">${organizationName}</h3></div>
	    </div>
	    <div class="container-fluid">
	        <div class="space10"></div>
	        <div class="row-fluid">
	            <div class="space10"></div>
	            <div class="widget-box orderBox">
	                <div class="space10"></div>
	                <div class="widget-content">
	            	<form id="medicalCardListForm" class="form-horizontal" method="post" action="${basePath}sysSchmag/communitycenter/communityHealth/queryCcommunityHealth" accept-charset="utf-8">
	            		<#if pager?exists>
	                		<input type="hidden" name="pageNum" value="${pager.pageNum}" />
	            			<input type="hidden" name="pageSize" value="${pager.pageSize}" />
	            			<input type="hidden" name="pages" value="${pager.pages}" />
	            			<input type="hidden" name="total" value="${pager.total}" />
	            			<input type="hidden" name="type" value="${type}" />
	        			</#if>
	        			<input type="hidden" name="hospitalId" value="${hospital.id}" />
	            	
	            		 <div class="row-fluid">
		                    <a class="btn-add pull-right" href="javascript:void(0);" onclick="addDuty();"><i class="caret"></i><i class="icons-plus"></i>添加</a>
		                </div>

	                    <div class="space20"></div>
	                    
					</form>
	             	<div class="space10"></div>
	      
	                <div class="row-fluid">
	                	<div class="orderTable" style="overflow-x: auto;">
	    					<table class="table table-bordered table-textCenter table-striped table-hover" style="width: 100%;">
	                    		<thead>
	                        		<tr>
	                        		    <th>序号</th>
	                        		    <th>星期</th>
	                                	<th>午段</th>
	                                	<th>专家</th>
	                                	<th>职称</th>
	                                	<th>专科</th>
	                                	<th>派出医院</th>
										<th>操作</th>
	                            	</tr>
	                    		</thead>
        					    <tbody>
	        						<#if organizationSchmagsList?exists>
                                		<#list organizationSchmagsList as organizationSchmags>
                                		  <tr >
                                		    <td> 
                                		     	${organizationSchmags_index + 1}
                                		    </td>
                                			
			                                <td>
												<#if organizationSchmags.week == 1>星期一</#if>
	                    						<#if organizationSchmags.week == 2>星期二</#if>
	                    						<#if organizationSchmags.week == 3>星期三</#if>
	                    						<#if organizationSchmags.week == 4>星期四</#if>
	                    						<#if organizationSchmags.week == 5>星期五</#if>
	                    						<#if organizationSchmags.week == 6>星期六</#if>
	                    						<#if organizationSchmags.week == 7>星期天</#if>
											</td>
											<td>
												<#if organizationSchmags.timeSlot == 1>上午</#if>
	                    						<#if organizationSchmags.timeSlot == 2>下午</#if>
	 
											</td>
			                                <td>${organizationSchmags.doctorName}</td>
			                                <td>${organizationSchmags.position}</td>
			                                <td>${organizationSchmags.specialty}</td>
			                                <td>${organizationSchmags.hospitalName}</td>
			             
			                                <td>
	                                         	<a href="javascript:void(0);" onclick="updateDuty('${organizationSchmags.id}','${organizationSchmags.week}','${organizationSchmags.timeSlot}','${organizationSchmags.doctorName}','${organizationSchmags.position}','${organizationSchmags.specialty}','${organizationSchmags.hospitalName}');">编辑</a>
	                                         	
	                                         	<a href="javascript:void(0);" onclick="deleteOrganization('${organizationSchmags.id}');">删除</a>

			                                </td>
			                               </tr>
			                             </#list>
									<#else>
										<tr><td colspan="10">暂无该社康中心医生排班信息</td></tr>
									</#if>
	        					</tbody>
        				</table>
						</div>
	                </div>
	            </div>
	        </div>
		</div>
	    <div class="pagination pagination-centered">
	    	
		</div>
		<div class="controls">
                <button class="btn btn-save" onclick="suppyOrganziation()" style="width:180px" >返回社康中心列表</button>
            </div>
	</div>
	
<form id="schedulingRegionFrom" method="post"  accept-charset="utf-8">
  <input type="hidden" id="organizationId" name="organizationId" value="${organizationId}" />
  <input type="hidden" id="organizationName" name="organizationName"  value="${organizationName}" />
  <input type="hidden" id="administrativeRegionQuert" name="administrativeRegionQuert" value="${administrativeRegionQuert}" />
  <input type="hidden" id="organizationNameQuert" name="organizationNameQuert" value="${organizationNameQuert}" />
  <input type="hidden" id="administrativeRegion" name="administrativeRegion"  value="${administrativeRegionQuert}" />
</form>

<#include "./sys/common/footer.ftl">
</body>
</html>

