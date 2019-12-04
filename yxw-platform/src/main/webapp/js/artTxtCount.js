(function($){
	// tipWrap: 	提示消息的容器
	// maxNumber: 	最大输入字符
	$.fn.artTxtCount = function(tipWrap, maxNumber){
		var countClass = 'js_txtCount',		// 定义内部容器的CSS类名
			fullClass = 'js_txtFull';		// 定义超出字符的CSS类名
		
		// 统计字数
		var count = function(){
			textContentNum =$('#textContent').text().trim().length;

			if(textContentNum <= maxNumber){

				tipWrap.html('<span class="' + countClass + '">\u8FD8\u80FD\u8F93\u5165 <strong>' + (maxNumber - textContentNum) + '</strong> \u4E2A\u5B57</span>');
			}else{
	
				tipWrap.html('<span class="' + countClass + ' ' + fullClass + '">\u5DF2\u7ECF\u8D85\u51FA <strong>' + (textContentNum - maxNumber) + '</strong> \u4E2A\u5B57</span>');
			};
		};
		$(this).bind('keyup change', count);
		return this;
	};
})(jQuery);