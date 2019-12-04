var noRecord = {
	/**
	 * 添加无记录样式 			-- 无按钮
	 * @param obj			-- 在哪里追加
	 * @param msg			-- 显示消息
	 * @param imgSrc		-- 图片相对路径，内部自添加域名
	 */
	addStyle: function(obj, msg, imgSrc) {
		var sHtml = '';

		sHtml += '<div class="noRecord">';
		sHtml += '	<div id="success">';
		sHtml += '		<div class="noData">';
		sHtml += '			<img src="' + base.appPath + imgSrc + '" width="220" />';
		sHtml += '		</div>';
		sHtml += '		<div class="p color666">' + msg + '</div>';
		sHtml += '	</div>';
		sHtml += '</div>';

		$(obj).append(sHtml);
	},
	/**
	 * 添加无记录样式 			-- 有按钮
	 * @param obj			-- 在哪里追加
	 * @param msg			-- 显示消息
	 * @param imgSrc		-- 图片相对路径，内部自添加域名
	 * @param btnText		-- 按钮文字
	 * @param forwardUrl	-- 跳转路径,get
	 */
	addStyleWithBtn: function(obj, msg, imgSrc, btnText, forwardUrl) {
		var sHtml = '';

		sHtml += '<div class="noFamily">';
		sHtml += '	<div id="success">';
		sHtml += '		<div class="noData">';
		sHtml += '			<img src="' + base.appPath + imgSrc + '" width="220" />';
		sHtml += '		</div>';
		sHtml += '		<div class="p color666">' + msg + '</div>';
		sHtml += '	</div>';
		sHtml += '	<div class="btn-w">';
		sHtml += '		<button type="button" class="btn btn-ok btn-block">' + btnText + '</button>';
		sHtml += '	</div>';
		sHtml += '</div>';

		$(obj).append(sHtml);
		
		$('div.noFamily>div.btn-w').off('click').on('click', function() {
			window.location.href = forwardUrl;
		});
	}
}