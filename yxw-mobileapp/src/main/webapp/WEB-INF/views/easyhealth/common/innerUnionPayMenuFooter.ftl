<!DOCTYPE html>
<div class="nav-footer">
	<div class="m">
       <a href="javascript: void(0);" menuCode="0" <#if menuCode==0>class="active"</#if>><i class="iconfont icon-shouye1"></i>就医服务</a>
       <a href="javascript: void(0);" menuCode="1"  <#if menuCode==1>class="active"</#if>><i class="iconfont icon-sousuo"></i>搜索</a>
       <a href="javascript: void(0);" menuCode="2"  <#if menuCode==2>class="active"</#if>><i class="iconfont icon-xx"></i>消息</a>
       <a href="javascript: void(0);" menuCode="3"  <#if menuCode==3>class="active"</#if>><i class="iconfont icon-wode"></i>我的</a>
   </div>
</div>

<input type="hidden" id="enableWebSocket" value="${enableWebSocket}" />
<input type="hidden" id="commonWebSocketPath" value="${commonWebSocketPath}" />
<input type="hidden" id="sockJSWebSocketPath" value="${sockJSWebSocketPath}" />

<script type="text/javascript" src="${basePath}yxw.app/js/common/sockjs-1.1.1.min.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/common/app.menuFoot.unionpay.js"></script>