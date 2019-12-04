<html>
<head>
	<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/hospital/sys.hospital.js"></script>
    <title>缓存管理</title>
</head>
<body>
    <form id="hospitalParamForm" method="post" accept-charset="utf-8" action="${basePath}sys/cacheManage/index">
       <input type="hidden" id="hospitalId" name="hospitalId" />
       <input type="hidden" id="hospitalName" name="hospitalName" />
       <input type="hidden" id="hospitalCode" name="hospitalCode" />
    </form>
	<div id="content">
	<!--医院及系统设置-->
	 <div id="content-top">
        <div class="container-fluid">
            <div class="box">
                <!--settings str-->
                <div id="settings">
                    <div class="set-msg dropdown">
                        <a class="dropdown-toggle" href="javascript:void(0);" data-toggle="dropdown" >
                            <span class="text">当前账户：${loginedUser.userName}</span>
                            <i class="icons_settings icons-set"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a onclick="toModifyPwd();"><span class="text">修改密码</span><i class="icons_settings icons-password"></i></a></li>
                            <li><a onclick="logout();"><span class="text">退出登录</span><i class="icons_settings icons-loginout"></i></a></li>
                        </ul>
                    </div>
                </div>
                <!--settings end-->
            </div>
        </div>
    </div>
    <div id="content-header">
        <div class="widget-title"><h3 class="title">缓存管理</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid">
                <div class="pull-right" id="search">
                	<form id="pageForm" method="post" action="" accept-charset="utf-8">
                		<#if hospitals?exists>
	                		<input type="hidden" name="pageNum" value="${hospitals.pageNum}" />
	            			<input type="hidden" name="pageSize" value="${hospitals.pageSize}" />
	            			<input type="hidden" name="pages" value="${hospitals.pages}" />
	            			<input type="hidden" name="total" value="${hospitals.total}" />
            			</#if>
	                    <input type="text" name="search"  value="${search}" placeholder="请输入医院名称、医院编码、联系人"/>
                	</from>
                    <button class="tip-bottom" type="submit">
                        <i class="icon-search icon-white"></i>
                    </button>
                </div>
            </div>
            <div class="widget-box">
                <div class="space10"></div>
                <div class="widget-content form-check">
                    <div class="row-fluid">
                        <table class="table table-bordered table-textCenter table-striped table-hover">
                            <thead>
                            	<tr>
                                	<th width="50"></th>
	                                <th>医院全称</th>
	                                <th width="100">联系人</th>
	                                <th width="120" >联系人电话</th>
	                                <th>上次操作时间</th>
	                                <th>管理</th>
	                            </tr>
                            </thead>
                            <tbody id="hospital_tab">
                            	<#if hospitals?exists>
	                                <#list hospitals.list as hospital>
		                                <tr>
		                                    <td>${hospital_index + 1} </td>
		                                    <td style="display: none;">${hospital.id}</td>
		                                    <td style="display: none;">${hospital.status}</td>
		                                    <td>${hospital.name}</td>
		                                    <td>${hospital.contactName}</td>
		                                    <td >${hospital.contactTel}</td>
		                                    <td >${hospital.ct?string("yyyy-MM-dd HH:mm:ss")}</td>
		                                    <td>
		                                    	<@p.hasPermission value="cacheManageEdit">
		                                         	<a href="#" onclick="toCacheEdit('${hospital.id}' , '${hospital.name}' , '${hospital.code}')">编辑</a>
		                                        </@p.hasPermission>
		                                    </td>
		                                </tr>
									</#list>
								<#else>
									<td colspan="10">暂无医院数据</td>
								</#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="pagination pagination-centered">
            <#if hospitals?exists>
            <ul>
                <li><a href="javascript:;" onclick="$hospital.changePage(${hospitals.prePage});">上一页</a></li>
                <#if hospitals.pages != 0>
	                <#list 1..hospitals.pages as p>
	                	<#if hospitals.pageNum == p>
	                		<li class="disabled"><span>${p}</span></li>
	                	<#else>
	                    	<li><a href="javascript:;" onclick="$hospital.changePage(${p});">${p}</a></li>
	                  	</#if>
	                </#list>
                </#if>
                <li><a href="javascript:;" onclick="$hospital.changePage(${hospitals.nextPage});">下一页</a></li>
            </ul>
            <div class="pageGoto">
                <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
                <a href="javascript:;" class="goto" onclick="$hospital.changePage($('#skipPage').val());">跳转</a>
                <span>共计 ${hospitals.pages} 页</span>
            </div>
            </#if>
        </div>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
</body>
</html>
<script type="text/javascript">
    var $hospital = {};
    /**
     * 搜索医院
     */
    $hospital.search = function() {
      document.forms['pageForm'].submit();
    }
    
    $hospital.back = function(url) {
        location.href = url;
    }
    
    /**
     * 医院管理分页
     */
    $hospital.changePage = function(pageNum, pageSize) {
      if (pageNum) {
        var pages = $('form input[name="pages"]').val();
        var pageNumInput = $('form input[name="pageNum"]');
    
        // 如果输入的页数是非数字，则还是跳到当前页
        if (isNaN(pageNum)) {
          pageNum = pageNumInput.val();
        }
    
        // 如果页数大于总页数，则跳至最后一页，如页数小于最小页数，则跳至第一页
        pageNum = pageNum > pages ? pages : pageNum;
        pageNum = pageNum < 1 ? 1 : pageNum;
    
        pageNumInput.val(pageNum);
    
        // 如果修改了每页显示的数量
        if (pageSize) {
          $('form input[name="pageSize"]').val(pageSize);
        }
        $hospital.search();
      }
    }
    
    function toCacheEdit(id , name , code){
        $("#hospitalId").val(id);
        $("#hospitalName").val(name);
        $("#hospitalCode").val(code);
        $("#hospitalParamForm").submit();
    }
</script>

