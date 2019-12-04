<html>
<#include "/mobileApp/common/common.ftl">
<head>
    <title>搜索</title>
    <style type="text/css">
    	
/** 水平伸缩容器**/
.row-flex{
  -moz-box-orient: horizontal;
  -webkit-box-orient: horizontal;
  -moz-box-direction: normal;
  -webkit-box-direction: normal;
  -moz-box-lines: multiple;
  -webkit-box-lines: multiple;
  -ms-flex-flow: row wrap;
  -webkit-flex-flow: row wrap;
  flex-flow: row wrap;
}


/** 伸缩容器**/
.flex-wrap{
  /** 各种版本兼容**/
  display: -webkit-box;
  display: -moz-box;
  display: -ms-flexbox;
  display: -webkit-flex;
  display: flex;
}
.flexWidth1{
  -webkit-box-flex: 1;
  -moz-box-flex: 1;
  -ms-flex: 1;
  -webkit-flex:1;
  flex:1;
}
#ks-search #search_btn{
  
    position: static;
    background: none;
    color: #37b494;
    border: none;
    font-size: 16px;
    text-align:right;
    display:block;
    margin:0;
    padding:10px;
    line-height:1
 
}
#ks-search {
    padding: 0;
    padding-top: 5px;
    border-bottom: solid 1px #efefef;
    position: relative;
}
.iconBox{ 
	width: 25px;
	padding-left: 10px;
	padding-top: 12px;
}
#ks-search .icon-search{ position: static;display:inline-block;line-height:40px;}
#ks-search .search_input{
  padding:0;
  margin:0
}
#ks-search .yx-input{
 color:#666;
 font-size:14px;
 padding: 10px 0;
 height:auto;
}
    </style>
</head>

<body style="background-color: #fff">

<div id="body">
<div style="height:15px;background-color: #eee;"></div>
    <div id="ks-search" class="flex-wrap row-flex">
        <div class="iconBox"><i class="icon-search"></i></div>
        <div class="search_input flexWidth1">
            <input type="text" class="yx-input" id="searchKeyword" oninput="doSearch(this.value)" placeholder="请输入关键字" autocomplete="off"/>
        </div>
        <div id="search_btn" onclick="search()" >搜索</div>
    </div>
    <div class="section" style="overflow: visible">
        <ul class="search-list" id="searchList">
        </ul>
    </div>
</div>
<form id="paramsForm" method="post" action="${basePath}easyhealth/search/doSearch">
    <input type="hidden" id="organizationName"  name="organizationName"  value="${commonParams.organizationName}"/>
    <input type="hidden" id="appCode"    name="appCode"    value="${appCode}>
<form id="administrativeRegionFrom" method="post" action="${basePath}easyhealth/communitycenter/communityHealth/getCommunityHealthOnlyOneById" accept-charset="utf-8">
 <input type="hidden" id="communityId" name="communityId"/>
</form>
<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script type="text/javascript">
$(function(){
    $("#searchKeyword").focus();
})

function search(){
    var organizationName = $("#searchKeyword").val();
    doSearch(organizationName);
}

function doSearch(organizationName){
    if(organizationName == ""){
        return;
    }else{
        var reg = /^[0-9]+|[\u4E00-\u9FA5]+$/; 

        var url = "${basePath}easyhealth/communitycenter/communityHealth/getCommunityHealthByParams";
     //   var datas = $("#paramsForm").serializeArray(); 
        $Y.loading.show(); 
        jQuery.ajax({  
           type : 'POST',  
           url : url,  
           data : {
           		organizationName:organizationName,
           },  
           dataType : 'json',  
           timeout : 120000,
           success : function(data) { 
                $Y.loading.hide();
                var listDom = $("#searchList");
                var htmlStr;

                htmlStr = showHospitals(data);
            
                $(listDom).html(htmlStr);
           },  
           error : function(data) {  
                $Y.loading.hide();
           }  
        });  
    }
}

function showHospitals(data){
    var htmlStr = "";
    var entityList = data.message.communityHealthCenterList;
    if(entityList && entityList.length > 0){
        for(var i = 0 ; i < entityList.length; i++){
            htmlStr += "<li onclick='goToCommunityHealth(\"" + entityList[i].id +"\")'>";
            htmlStr += "<span class='title'>" + entityList[i].organizationNameSub + "</span>&nbsp"
            htmlStr += "</li>";
        }
    }else{
         htmlStr += "没有搜到该医院,请重新输入搜索值";
    }
    return htmlStr;
}

function goToCommunityHealth(id) {
	$("#communityId").val(id);
	$('#administrativeRegionFrom').submit();
   
};

$.fn.formEdit = function(data){
    return this.each(function(){
        var elementDom;
        var elementDomName;
        if(data == null){
            this.reset(); 
            return; 
        }
        for(var i = 0; i < this.length; i++){  
            elementDom = this.elements[i];
            elementDomName = elementDom.name;
            if(data[elementDomName] == undefined){ 
                continue;
            }
            elementDom.value = data[elementDomName];
        }
    });
};
</script>