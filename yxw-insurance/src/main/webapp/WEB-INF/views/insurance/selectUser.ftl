<!DOCTYPE html>
<html>
<head>
  	<#include "/common/common.ftl">
    <title>查询就诊记录</title>
</head>

<body>
<div id="body">
  	<#include "/common/commonOrderQueryHead.ftl">
  	 <input type="hidden" id="openId"   name="openId" value="${openId}" />
		<!-- <div class="btn-w">
            <div class="btn btn-ok btn-block" id="toClinList">查看就诊订单</div>
        </div>-->

	<div class="nodata" style="display: none">
	        <img src="${basePath}yxw.app/images/lipei/icon-nodata.png"  width="100">
	        <div>没有缴费记录</div>
	</div>

<section id="list">
        <ul class="lipei-list2" id="dataList">
                <!-- <li>
                     
                        <a href="#">
                                <div class="main">
                                        <div class="info">
                                                <div>就诊人：晨曦</div>
                                                <div>项目：骨科门诊费用</div>
                                                <div>金额：<span class="cost">199.00</span></div>
                                        </div>
                                        <div class="r"> <i class="arrow"></i></div>
                                </div>
                                <div class="mate">
                                        <span class="des">2017-05-05 14:02</span>
                                        <span class="cost">已缴费</span>
                                </div>
                        </a>
                </li>
                <li>
                      
                        <a href="#">

                                <div class="main">
                                        <div class="info">
                                                <div>就诊人：晨曦</div>
                                                <div>项目：骨科门诊费用</div>
                                                <div>金额：<span class="cost">199.00</span></div>
                                        </div>
                                        <div class="r"> <i class="arrow"></i></div>
                                </div>
                                <div class="mate">
                                        <span class="des">2017-05-05 14:02</span>
                                        <span class="cost">挂号成功（已缴费）</span>
                                </div>


                        </a>
                </li> -->

        </ul>
      
</section>



</div>
<#include "/common/footer.ftl">
</body>
</html>
<script type="text/javascript">
		
		//$('#toClinList').off('click').on('click', function() {
 			
       // });


		function getDataList(){
		
 				var cardNo=$('#userNameFilter').val();
 				var hospital=$('#hospitalFilter').val();
 				var openId=$('#openId').val();
 				$.ajax({
				type: "POST",
				url: "${basePath}api/getMzPayFeeList?openId="+encodeURIComponent("${openId}")+"&hospitalCode="+hospital+"&cardNo="+cardNo,
				cache: false, 
				dataType: "json", 
				timeout: 600000,
				error: function(data) {
				  $('#list').hide();
                  $('.nodata').show();
				},
				success: function(data) {
						if(data!=null){
                          	var sHtml="";
							$.each(data, function(i, item) {
								sHtml+="<li>"+
			                        "<a href='${basePath}api/toPaidDetail?openId="+encodeURIComponent(item.openId)+"&orderNo="+item.orderNo+"&hospitalCode="+item.hospitalCode+"&clinicStatus="+item.clinicStatus+"'>"+
			                                "<div class='main'>"+
			                                        "<div class='info'>"+
			                                                "<div>就诊人："+item.patientName+"</div>"+
			                                                "<div>项目："+item.recordTitle+"</div>"+
			                                                "<div>金额：<span class='cost'>"+returnFloat(item.payFee/100)+"</span></div>"+
			                                        "</div>"+
			                                        "<div class='r'> <i class='arrow'></i></div>"+
			                                "</div>"+
			                                "<div class='mate'>"+
			                                        "<span class='des'>"+timestampToTime(item.payTime)+"</span>"+
			                                        "<span class='cost'>已缴费</span>"+
			                                "</div>"+
			                        "</a>"+
		                		"</li>";
							});
							$('#dataList').html('').append(sHtml);
							$('#list').show();
	                        $('.nodata').hide();
						}else{
						  $('#list').hide();
                          $('.nodata').show();
						}
					}
				})
		}

		//格式化金额
		function returnFloat(value){
				 var value=Math.round(parseFloat(value)*100)/100;
				 var xsd=value.toString().split(".");
				 if(xsd.length==1){
				 value=value.toString()+".00";
					 return value;
				 }
				 if(xsd.length>1){
					 if(xsd[1].length<2){
					 value=value.toString()+"0";
				 }
					 return value;
				 }
			} 
	
		function timestampToTime(timestamp) {
	        var date = new Date(timestamp);
	        Y = date.getFullYear() + '-';
	        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
	        D = date.getDate()<10 ? '0' + date.getDate()+' ':date.getDate()+' ';
	        h = (date.getHours()<10?'0'+date.getHours():date.getHours()) + ':';
	        m =(date.getMinutes()<10?'0'+date.getMinutes():date.getMinutes()) + ':';
	        s = date.getSeconds()<10?'0'+date.getSeconds():date.getSeconds();
	        return Y+M+D+h+m+s;
   		 }

</script>