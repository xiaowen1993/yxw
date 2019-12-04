function scrollRefresh(){
	var touchArea = $('.scrollTouch'),
	    tip = $('.scroll-tip'),
	    list = $('#refreshList'),
	    devaationStartOffset,
	    touchMoveOffset,
	    scrollBarOffset
	    body = $(document.body),
	    // 下拉偏移值 ，触发回调
	    devaation = 50;


	/**
	 * flag有3种状态
	 * 0 = 初始化状态
	 * 1 = 滚动中
	 * 2 = 执行相应的回调函数
	 */
	var flag = 0,
		timer1,timer2;

	

	touchArea.on('touchstart',scrollTouchStart);
	touchArea.on('touchmove',scrollTouchMove);
	touchArea.on('touchend',scrollTouchEnd);

	// 设置偏移量
	function translate(num){
		touchArea.css({
			'-webkit-transform':'translateY('+num+'px)',
			'transform':'translateY('+num+'px)',
		})
	}

	// 触摸开始，初始化偏移值
	
	function scrollTouchStart(e){

		var target = e.originalEvent.targetTouches[0];
		// $(this).off('webkitTransitionEnd');
		scrollBarOffset = body.scrollTop();
		touchStartOffset = target.clientY;
	}

	// 移动
	function scrollTouchMove(e){
		var target = e.originalEvent.targetTouches[0];
		touchMoveOffset = target.clientY;
		// 判断滚动条到顶部 并 处于下拉状态
		if( body.scrollTop() < 1 && (touchMoveOffset > touchStartOffset) ){
			e.preventDefault();
			if(flag != 2){
				var num  = (touchMoveOffset - touchStartOffset - scrollBarOffset)*0.6;

				num = num > 100? 100 : num;

				if(num < 80){
					tip.html('下拉刷新...');
					flag = 0;
				}else{
					tip.html('释放加载...');
					flag = 1;
				}
				translate(num);
			}
		}
	}

	// 结束
	function scrollTouchEnd(e){
		// e.preventDefault();
		if(flag == 1){
			flag = 2;
			tip.html('正在加载...');
			
			// touchArea.on('webkitTransitionEnd.end1',function(){
			// 	touchArea.removeClass('onload');
			// 	addList();
			// 	$(this).off('webkitTransitionEnd.end1');
				
			// });
			touchArea.addClass('onload');
			translate(80);
			if(timer1){
				clearTimeout(timer1)
			}
			timer1 = setTimeout(function(){
				touchArea.removeClass('onload');
				addList();
			},500)
		}else if(flag == 2){
			return false;
		}else{
			// touchArea.addClass('onload');
			translate(0);
			flag = 0;
		}
	}

	// 更新列表操作
	function addList(){
		tip.off('touchstart');
		if(timer2){
			clearTimeout(timer2)
		}
		$.ajax({
			url: '/path/to/file',
			type: 'default GET (Other values: POST)',
			dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
			data: {param1: 'value1'},
		})
		.done(function() {


			tip.html('加载完成');
			var html = $('#a1').html();
			$("#refreshList").prepend(html);
			// touchArea.one('webkitTransitionEnd.end2',function(){
			// 	flag = 0;
			// 	$(this).removeClass('onload2');
			// 	// $(this).off('webkitTransitionEnd.end2');
				
			// })
			touchArea.addClass('onload2');
			translate(0);
			if(timer2){
				clearTimeout(timer2)
			}
			timer2 = setTimeout(function(){
				flag = 0;
				touchArea.removeClass('onload2');
			},1300)

		})
		.fail(function() {

			tip.html('加载失败，点击请重新加载');
			flag = 0;;
			tip.on('touchstart',function(e){
				location.reload();
			})
				
		})
		.always(function() {

			/**
			 * 可以删除下面注释查看看效果
			 */
			tip.html('加载完成');
			var html = $('#a1').html();
			$("#refreshList").prepend(html);
			// touchArea.one('webkitTransitionEnd.end2',function(){
			// 	flag = 0;
			// 	$(this).removeClass('onload2');
			// 	// $(this).off('webkitTransitionEnd.end2');
				
			// })
			touchArea.addClass('onload2');
			translate(0);
			if(timer2){
				clearTimeout(timer2)
			}
			timer2 = setTimeout(function(){
				flag = 0;
				touchArea.removeClass('onload2');
			},1300)
		});
		
	}
}
scrollRefresh();
