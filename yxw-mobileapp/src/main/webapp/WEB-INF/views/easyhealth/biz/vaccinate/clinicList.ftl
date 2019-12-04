<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>${regionName}</title>
</head>
<body>

<div id="body">
    <div class="box-list fff ">
        <ul class="yx-list">
            <li class="flex-wrap row-flex">
                <#--<div class="title flexWidth5">搜索</div> -->
                <div class="title">搜索</div>
                <div class="title flexWidth5 textRight color999"><input type="text" class="v-input" placeholder="请输关键字" /></div>
            </li>
        </ul>

    </div>

    <ul class="yx-list vaccinateClinicList">
    		
    		<#if vaccinateClinics?exists>
	      	<#list vaccinateClinics as vaccinateClinic>
		        <li class="vaccinateClinicItem">
		        		<!-- ${vaccinateClinic_index + 1} -->
		            <div class="title">${vaccinateClinic.vaccinateClinicName}</div>
		            <div class="mate color999">${vaccinateClinic.vaccinateClinicAddr}</div>
		            <div class="mate color999">咨询电话：${vaccinateClinic.vaccinateClinicTel}</div>
		        </li>
        	</#list>
					<#else>
					<!-- 无数据HTML -->
				</#if>
    </ul>
</div>

<#include "/easyhealth/common/footer.ftl">

<form id="regionForm" method="post" action="clinic" accept-charset="utf-8">
	<input type="hidden" id="regionName" name="regionName" value="${regionName}">
</form>
<script type="text/javascript">
	$(function() {
	  $.expr[':'].Contains = function(a,i,m){
	      return (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase())>=0;
	  };
	  

		var list = $(".vaccinateClinicList");

		$(".v-input").change( function() {
        var filter = $(this).val();
        if(filter) {
					  $matches = $(list).find('div:Contains(' + filter + ')').parent();
					  $('.vaccinateClinicItem', list).not($matches).slideUp();
					  $matches.slideDown();
        } else {
          $(list).find(".vaccinateClinicItem").slideDown();
        }
	  } ).keyup( function() {
	  		$(this).change();
	  } ).on("input", function() {
	  		$(this).change();
	  } );

	});

	function doRefresh() {
			$("#regionForm").submit();
	}
	
	function doGoBack() {
		go('region');
	}
</script>
</body>
</html>
