$(function() {
	$('form input[name="pages"]').val("");
	$('form input[name="pageNum"]').val("");
	$('form input[name="pageSize"]').val("");
	$Y.loading.show('正在为您加载数据...');
	//scrollRefresh();
	search();
});

function scrollRefresh() {
	var touchArea = $('.scrollTouch'), tip = $('.scroll-tip'), list = $('#refreshList'), devaationStartOffset, touchMoveOffset, scrollBarOffset
	body = $(document.body),
	// 下拉偏移值 ，触发回调
	devaation = 50;

	/**
	 * flag有3种状态
	 * 0 = 初始化状态
	 * 1 = 滚动中
	 * 2 = 执行相应的回调函数
	 */
	var flag = 0, timer1, timer2;

	touchArea.on('touchstart', scrollTouchStart);
	touchArea.on('touchmove', scrollTouchMove);
	touchArea.on('touchend', scrollTouchEnd);

	// 设置偏移量
	function translate(num) {
		touchArea.css({
			'-webkit-transform' : 'translateY(' + num + 'px)',
			'transform' : 'translateY(' + num + 'px)',
		})
	}

	// 触摸开始，初始化偏移值

	function scrollTouchStart(e) {

		if (flag != 2) {
			if (touchArea.hasClass('onload2')) {
				touchArea.removeClass('onload2');
			}
			if (touchArea.hasClass('onload')) {
				touchArea.removeClass('onload');
			}
		}

		var target = e.originalEvent.targetTouches[0];
		// $(this).off('webkitTransitionEnd');
		scrollBarOffset = body.scrollTop();
		touchStartOffset = target.clientY;
	}

	// 移动
	function scrollTouchMove(e) {
		var target = e.originalEvent.targetTouches[0];
		touchMoveOffset = target.clientY;
		// 判断滚动条到顶部 并 处于下拉状态
		console.log(body.scrollTop(),touchMoveOffset > touchStartOffset)
		if (body.scrollTop() < 1 && (touchMoveOffset > touchStartOffset)) {
			e.preventDefault();
			if (flag != 2) {
				var num = (touchMoveOffset - touchStartOffset - scrollBarOffset) * 0.6;

				num = num > 100 ? 100 : num;
			

				if (num < 80) {
					tip.html('下拉刷新...');
					flag = 0;
				} else {
					tip.html('释放刷新...');
					flag = 1;
				}
				translate(num);
			}

			// alert(2)

		}
	}

	// 结束
	function scrollTouchEnd(e) {
		if (flag == 1) {
			flag = 2;
			tip.html('正在加载...');

			// touchArea.on('webkitTransitionEnd.end1', function() {
			// 	touchArea.removeClass('onload');
			// 	addList();
			// 	$(this).off('webkitTransitionEnd.end1');

			// });
			touchArea.addClass('onload');
			translate(80);

			if (timer1) {
				clearTimeout(timer1)
			}
			timer1 = setTimeout(function() {
				touchArea.removeClass('onload');
				addList();
			}, 500)

		} else if (flag == 2) {
			return false;
		} else {
			// touchArea.addClass('onload');
			translate(0);
			flag = 0;
		}
	}

	// 更新列表操作
	function addList() {
		tip.off('touchstart');
		$('form input[name="pages"]').val("");
		$('form input[name="pageNum"]').val("");
		$('form input[name="pageSize"]').val("");
		//$(".yx-list").children().remove();
		$.ajax({
			type : "POST",
			url : base.appPath + "app/msgcenter/list",
			data : $("form").serializeArray(),
			dataType : "json",
			error : function(data) {
				$Y.loading.hide();
				// 提示不能跳转
				myBox = new $Y.confirm({
					title : "",
					content : "<div style='text-align: center;'>网络异常,请保持您的网络通畅,稍后再试.</div>",
					ok : {
						title : "确定",
						click : function() {
							myBox.close();
						}
					}
				});
			},
			success : function(data) {
				//$Y.loading.hide();
				var listTag = $(".notice-list");
				//清空
				listTag.empty();
				$(".btn-w").remove();
				if (data.list.length > 0) {
					var html = "";
					$(data.list).each(function(i, n) {
						var url = appPath + "app/msgcenter/detailView?userId=" + $('form input[name="userId"]').val() + "&id=" + n.id;
						var temp = new Date(n.ct);
						var month = temp.getMonth() + 1;
						month = month < 10 ? "0" + month : month;
						var day = temp.getDate() < 10 ? "0" + temp.getDate() : temp.getDate();
						var hours = temp.getHours() < 10 ? "0" + temp.getHours() : temp.getHours();
						var minutes = temp.getMinutes() < 10 ? "0" + temp.getMinutes() : temp.getMinutes();
						var time = temp.getFullYear() + "/" + month + "/" + day + " " + hours + ":" + minutes;
						var path = window.location.protocol + "//" + window.location.host;
						var first = "";
						var json = eval('(' + n.msgContent + ')');
						if (json.msgTemplateContents.length > 0) {
							for (var i = 0, j = json.msgTemplateContents.length; i < j; i++) {
								var m = json.msgTemplateContents[i];
								if (m.sort == 1) {
									first = m.value;
									break;
								}
							};
						}
						if (first.length > 0 && first.length > 30) {
							first = first.substring(0, 30) + "...";
						}

						html = "<li href='" + url + "'>";
						// if (n.iconPath != null && n.iconPath != '') {
						// html += "<img src='" + path + n.iconPath + "' width='48' height='48' alt=''>";
						// } else {
						// html += "<img src='" + path + '/easyhealth/images/grey.gif' + "' width='48' height='48' alt=''>";
						// }
						if (n.hospitalName.length > 0 && n.hospitalName.length > 10) {
							n.hospitalName = n.hospitalName.substring(0, 10);
						}
						if (n.isRead == 1) {
							html += "<h6 class='unread'><span>" + n.msgTitle + "</span><span>" + n.hospitalName + "</span></h6>";
						} else {
							html += "<h6><span>" + n.msgTitle + "</span><span>" + n.hospitalName + "</span></h6>";
						}

						html += "<div class='noticeListContent'>";
						html += "<p class='noticeHospital'>" + first + "</p>";
						html += "<p class='noticeTime'>" + time + "</p>";
						html += "</div>";
						html += "</li>";
						var li = listTag.append(html);

					});

					$('form input[name="pages"]').val(data.pages);
					$('form input[name="pageNum"]').val(data.pageNum);
					$('form input[name="pageSize"]').val(data.pageSize);

					tip.html('加载完成');
					//$("#refreshList").prepend(html);
					if (data.pages == data.pageNum) {
						$(".btn-w").remove();
					} else {
						var but = $(".scrollTouch");
						var html = "<div class='btn-w'>";
						html += "<div class='btn btn-ok btn-block' onClick='changePage(" + data.nextPage + ")'>点击加载更多</div>";
						html += "</div>";
						but.append(html);
					}
					// touchArea.on('webkitTransitionEnd.end2', function() {
					// 	flag = 0;
					// 	$(this).removeClass('onload2');
					// 	$(this).off('webkitTransitionEnd.end2');

					// })
					touchArea.addClass('onload2');
					translate(0);

					if (timer2) {
						clearTimeout(timer2)
					}
					timer2 = setTimeout(function() {
						flag = 0;
						touchArea.removeClass('onload2');
					}, 1300)

				} else {
					$(".mod-notice").remove();
					var body = $("#body");
					var html = body.append("<div id='success'><div class='noData noticeEmpty'></div><div class='p color666' >还没有收到任何消息通知</div></div>");
					body.append(html);
				}
			}
		});

	}

}

/**
 *搜索
 */
function search() {
	$.ajax({
		type : "POST",
		url : appPath + "app/msgcenter/list",
		data : $("form").serializeArray(),
		dataType : "json",
		error : function(data) {
			$Y.loading.hide();
			// 提示不能跳转
			myBox = new $Y.confirm({
				title : "",
				content : "<div style='text-align: center;'>网络异常,请保持您的网络通畅,稍后再试.</div>",
				ok : {
					title : "确定",
					click : function() {
						myBox.close();
					}
				}
			});
		},
		success : function(data) {
			$Y.loading.hide();
			var listTag = $(".notice-list");
			//清空
			//listTag.empty();
			if (data.list.length > 0) {
				$(data.list).each(function(i, n) {
					var url = appPath + "app/msgcenter/detailView?userId=" + $('form input[name="userId"]').val() + "&id=" + n.id;
					var temp = new Date(n.ct);
					var month = temp.getMonth() + 1;
					month = month < 10 ? "0" + month : month;
					var day = temp.getDate() < 10 ? "0" + temp.getDate() : temp.getDate();
					var hours = temp.getHours() < 10 ? "0" + temp.getHours() : temp.getHours();
					var minutes = temp.getMinutes() < 10 ? "0" + temp.getMinutes() : temp.getMinutes();
					var time = temp.getFullYear() + "/" + month + "/" + day + " " + hours + ":" + minutes;
					var path = window.location.protocol + "//" + window.location.host;
					var html = "";
					var first = "";
					var json = eval('(' + n.msgContent + ')');
					if (json.msgTemplateContents.length > 0) {
						for (var i = 0, j = json.msgTemplateContents.length; i < j; i++) {
							var m = json.msgTemplateContents[i];
							if (m.sort == 1) {
								first = m.value;
								break;
							}
						};
					}

					if (first.length > 0 && first.length > 30) {
						first = first.substring(0, 30) + "...";
					}

					html = "<li href='" + url + "'>";
					// if (n.iconPath != null && n.iconPath != '') {
					// html += "<img src='" + path + n.iconPath + "' width='48' height='48' alt=''>";
					// } else {
					// html += "<img src='" + path + '/easyhealth/images/grey.gif' + "' width='48' height='48' alt=''>";
					// }
					if (n.hospitalName.length > 0 && n.hospitalName.length > 10) {
						n.hospitalName = n.hospitalName.substring(0, 10);
					}
					if (n.isRead == 1) {
						html += "<h6 class='unread'><span>" + n.msgTitle + "</span><span>" + n.hospitalName + "</span></h6>";
					} else {
						html += "<h6><span>" + n.msgTitle + "</span><span>" + n.hospitalName + "</span></h6>";
					}
					html += "<div class='noticeListContent'>";
					html += "<p class='noticeHospital'>" + first + "</p>";
					html += "<p class='noticeTime'>" + time + "</p>";
					html += "</div>";
					html += "</li>";
					var li = listTag.append(html);

				});

				$('form input[name="pages"]').val(data.pages);
				$('form input[name="pageNum"]').val(data.pageNum);
				$('form input[name="pageSize"]').val(data.pageSize);

				if (data.pages == data.pageNum) {
					$(".btn-w").remove();
				} else {
					$(".btn-w").remove();
					var but = $(".scrollTouch");
					var html = "<div class='btn-w'>";
					html += "<div class='btn btn-ok btn-block' onClick='changePage(" + data.nextPage + ")'>点击加载更多</div>";
					html += "</div>";
					but.append(html);
				}

				scrollRefresh();
			} else {
				$(".mod-notice").remove();
				var body = $("#body");
				var html = body.append("<div id='success'><div class='noData noticeEmpty'></div><div class='p color666' >还没有收到任何消息通知</div></div>");
				body.append(html);
			}
		}
	});
}

/**
 *翻页
 */
function changePage(pageNum) {
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

		search();
	} else {

	}
}
