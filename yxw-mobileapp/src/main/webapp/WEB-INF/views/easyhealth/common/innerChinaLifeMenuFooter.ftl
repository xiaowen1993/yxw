<!DOCTYPE html>
<div class="ui-menu-footer">
	<div class="flex">
       <a href="javascript: void(0);" menuCode="0" <#if menuCode==0>class="active"</#if>><i class="iconfont icon-jiuyifuwu"></i>首页</a>
       <a href="javascript: void(0);" menuCode="1"  <#if menuCode==1>class="active"</#if>><i class="iconfont icon-sousuo1"></i>搜索</a>
       <a href="javascript: void(0);" menuCode="2"  <#if menuCode==2>class="active"</#if>><i class="iconfont icon-zhuyuan1"></i>消息</a>
       <a href="javascript: void(0);" menuCode="3"  <#if menuCode==3>class="active"</#if>><i class="iconfont icon-wode1"></i>我的</a>
   </div>
</div>

<input type="hidden" id="enableWebSocket" value="${enableWebSocket}" />
<input type="hidden" id="commonWebSocketPath" value="${commonWebSocketPath}" />
<input type="hidden" id="sockJSWebSocketPath" value="${sockJSWebSocketPath}" />

<script type="text/javascript" src="${basePath}yxw.app/js/common/sockjs-1.1.1.min.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/common/app.menuFoot.js"></script>