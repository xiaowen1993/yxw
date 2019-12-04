<div class="page-title">选择支付方式</div>
<#assign showTypes=(tradeTypes?split(','))>
<#-- 0:不显示; 1:显示不使用; 2:显示并使用 -->
<div class="radio-list">
	<ul class="yx-list tradeTypes">
		<#list payModes as payMode>
			<#if showTypes[payMode_index] != 0>
			<li data-id="${payMode.targetId}" class="flex <#if showTypes[payMode_index] == 1>disable</#if> <#if defaultTradeType == payMode.targetId>active</#if>">
				<div>
					<i class="icon-radio"></i>${payMode.showName}
				</div>
			</li>
			</#if>
		</#list>
	</ul>
</div>

<script src="${basePath}yxw.app/js/biz/common/app.commonTrade.js" type="text/javascript"></script>