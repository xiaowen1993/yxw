<html>
<head>
<#include "/sys/common/common.ftl">
<title>白名单设置</title>
</head>
<style type="text/css">
    .table th, .table td {
        padding: 5px 2px;
        font-size:14px;
    }
</style>
<body>
<!--content str-->
<div id="content" >
   <div class="container-fluid" style="padding-left:0;padding-right:0;">
        <div class="row-fluid">
        <div class="space10"></div>
        <div class="widget-box bangKa tab_content">
                <div class="row-fluid">
                    <div class="control-box">
                    <form class="form-horizontal evenBg" id="whiteListForm">
                    <input type="hidden" id="id" name="id" value="${entity.id}" />
                    <input type="hidden" id="hospitalId" name="hospitalId" value="${entity.hospitalId}" />
                    <input type="hidden" id="hospitalName" name="hospitalName" value="${entity.hospitalName}" />
                    <input type="hidden" id="hospitalCode" name="hospitalCode" value="${entity.hospitalCode}" />
                    <input type="hidden" id="appId" name="appId" value="${entity.appId}" />
                    <input type="hidden" id="appCode" name="appCode" value="${entity.appCode}" />
                    <input type="hidden" id="oldIsOpen" name="oldIsOpen" value="${entity.isOpen}" />
                        <div class="control-group w162" style="padding:0 0 5 5">
                            <div class="spaceW25 fr"></div>
                            <button class="btn btn-save fr" type="button" onclick="saveWhiteList()">保存</button>
                            <label class="control-label" style="width: 140px;padding: 0 0 0 20">是否开启白名单</label>
                            <div class="controls" style="margin-left: 140px;"">
                                <label
                                    <#if entity.isOpen == 1>
                                        class="radio inline check">
                                    <#else>
                                        class="radio inline">
                                    </#if> 
                                    <input type="radio" name="isOpen" id="isOpen" value="1"
                                    <#if entity.isOpen == 1>
                                        checked="checked"
                                    </#if>
                                    />是
                                </label>
                                <label
                                    <#if entity.isOpen == 0>
                                        class="radio inline check">
                                    <#else>
                                        class="radio inline">
                                    </#if> 
                                    <input type="radio" name="isOpen" id="isOpen" value="0"
                                    <#if entity.isOpen == 0>
                                        checked="checked"
                                    </#if>
                                    />否 
                                </label>
                                <span class="white-des">(缓存服务器 cacheKey:white.list.hash  field:${entity.appId}:${entity.appCode})</span>
                            </div>
                       </div>
                       </form>
                   </div>
                   <form class="form-horizontal evenBg" id="whiteDetailForm">
                   <div class="control-box">
                        <div class="control-group w162" style="padding: 2px 0">
                            <div class="spaceW25 fr"></div>
                            <button class="btn btn-save fr" type="button" onclick="addDetail()">添加用户</button>
                            <div class="line" style="margin-right: 200px;height:3em;line-height:3em;overflow: hidden;">
                                <span style="text-align:left;padding: 0 25 0 25;color:#40505f;font-weight:bold;width:160px;
                                    line-height:30px;font-size:16px;">
                                                                                    设置白名单用户
                                </span>
                                <input type="hidden" id="whiteListId" name="whiteListId" value="${entity.id}" />
                                                                                      姓名　    <input type="text" id="name"  name="name"    class="span5 input33" style="width:100px;padding: 0 5;"/>
                                                                                     手机号　<input type="text" id="phone" name="phone"   class="span5 input33" style="width:150px/>
                            </div>
                         </div>
                   </div>
                   </form>  
                <div class="space20"></div>
                <div class="row-fluid">
                    <table class="table table-bordered table-textCenter table-striped table-hover" style="padding:0;width:98%;" id="detailInfos" >
                        <thead>
                        <tr>
                            <th width="20%" >姓名</th>
                            <th width="20%">手机号</th>
                            <th width="40%">openid</th>
                            <th width="20%">管理</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if entityList?exists && entityList?size != 0 >
                            <#list entityList as detail>
                                <tr id="tr_${detail.id}">
                                    <td width="20%" id="name_${detail.id}">${detail.name}</td>
                                    <td width="20%" id="phone_${detail.id}">${detail.phone}</td>
                                    <td width="40%" id="openId_${detail.id}">${detail.openId}</td>
                                    <td width="20%"><a href="#" onclick="delDetail('${detail.id}')">删除</a></td>
                                </tr>
                            </#list>
                        <#else>
                            <tr id="noData">
                                <td colspan="4">未设置白名单用户</td>
                            </tr>
                        </#if>
                        </tbody>
                    </table>
              </div>
</body>
</html>
<script type="text/javascript">
    function saveWhiteList(){
        var reqUrl =  "${basePath}sys/whiteListSetings/save";
        var datas = $("#whiteListForm").serializeArray(); 
        $.ajax({  
            type : 'POST',  
            url  : reqUrl,  
            data : datas,  
            dataType : 'json',  
            timeout  : 120000,
            success  : function(data) { 
                if(data.message.isSuccess){
                    alert("保存成功!")
                    var enityId = data.message.entity.id;
                    var isOpen = data.message.entity.isOpen;
                    $("#id").val(enityId);
                    $("#whiteListId").val(enityId);
                    $("#oldIsOpen").val(isOpen);
                }else{
                    alert("保存失败!")
                }
             },  
             error : function(data) {  
               alert("保存失败!");
             }  
        });        
    }
    
    function addDetail(){
        var whiteListId = $("#id").val();
        if(whiteListId == ''){
            alert("请先保存白名单设置!");
            return;
        }
        var hospitalId = $("#hospitalId").val();
        var hospitalCode = $("#hospitalCode").val();
        var appId =   $("#appId").val();
        var appCode = $("#appCode").val();
        
        var isOpen = $("input[name='isOpen']:checked").val();
        var oldIsOpen = $("#oldIsOpen").val();
        if(isOpen != oldIsOpen){
            alert("白名单是否开启设置已发生更改,请先保存开始设置!");
            return;
        }
        
        var phone =   $("#phone").val();
        if(phone == ''){
            alert("电话不能为空!");
            return;
        }else{
            var partten = /^1[3,5,8]\d{9}$/;
            if(!partten.test(phone)){
               alert("电话号码格式不正确!");
               return;
            }
        }
        
        var name =   $("#name").val();
        if(name == ''){
            alert("姓名不能为空!");
            return;
        }
       
        
        var reqUrl =  "${basePath}sys/whiteListDetail/save";
        var datas = {
            whiteListId:whiteListId,hospitalId:hospitalId,hospitalCode:hospitalCode,
            appId:appId,appCode:appCode,isOpen:isOpen,phone:phone,name:name
        };
        $.ajax({  
            type : 'POST',  
            url  : reqUrl,  
            data : datas,  
            dataType : 'json',  
            timeout  : 120000,
            success  : function(data) { 
                if(data.message.isSuccess){
                    var entity = data.message.entity;
                    var openId = entity.openId == null ? "" : entity.openId;
                    var htmlStr = "<tr id='tr_" +  entity.id + "'>";
                    htmlStr +=      "<td id='name_" +  entity.name + "'>" + entity.name  + "</td>";
                    htmlStr +=      "<td id='phone_" +  entity.phone + "'>" + entity.phone + "</td>";
                    htmlStr +=      "<td id='openId_" +  entity.openId + "'>" + openId + "</td>";
                    htmlStr +=     "<td><a href='#' onclick='delDetail(\"" + entity.id + "\")'>删除</a></td>";
                    htmlStr +=   "</tr>";
                    $(htmlStr).insertAfter($("#detailInfos tr:eq(0)"));
                }else{
                    alert(data.message.msgInfo);
                }
             },  
             error : function(data) {  
               alert("保存失败!");
             }  
        });        
    }
    
    function delDetail(id){
        var reqUrl =  "${basePath}sys/whiteListDetail/delDetail";
        var whiteListId = $("#id").val();
        var hospitalId = $("#hospitalId").val();
        var hospitalCode = $("#hospitalCode").val();
        var appId =   $("#appId").val();
        var appCode = $("#appCode").val();
        var isOpen =  $("#isOpen").val();
        var phone =   $("#phone_" + id).text();
        var name =    $("#name_" + id).text(); 
        var openId =  $("#openId_" + id).text();
        var datas =  {
            whiteListId:whiteListId,hospitalId:hospitalId,hospitalCode:hospitalCode,openId:openId,
            appId:appId,appCode:appCode,isOpen:isOpen,phone:phone,name:name ,id:id
        };
        $.ajax({  
            type : 'POST',  
            url  : reqUrl,  
            data : datas,  
            dataType : 'json',  
            timeout  : 120000,
            success  : function(data) { 
                if(data.message.isSuccess){
                    delTr(id);
                    alert("删除成功!")
                }else{
                    alert("删除失败!")
                }
             },  
             error : function(data) {  
               alert("保存失败!");
             }  
        });            
    }
    
    function delTr(id){
       var trId = "tr_" + id;
       $("#detailInfos tr").each(function(i){
           if($(this).attr("id") == trId){ 
               //不能删除行标题
               if(i!=0){
                  $("#detailInfos tr:eq("+i+")").remove();
               }
           }
       });
    }
</script>