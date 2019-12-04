var commonTabs = {
	addTabs: function(options) {
		id = "tab_" + options.id;
		$('#mainContent').find(".active").removeClass("active");
		if (!$("#" + id)[0]) {
			// 固定TAB中IFRAME高度
			mainHeight = $(document.body).height() - 90;
			// 创建新TAB的title
			title = '<li role="presentation" id="tab_' + id + '">';

			// 默认标题
			if (!options.title) {
				if (options.defaultTitle) {
					options.title = commonTabs.getTabIndex(options.defaultTitle, 0);
				} else {
					options.title = commonTabs.getTabIndex('未定义', 0);
				}
			}

			title += '	<a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab">' + options.title;
			// 是否允许关闭
			if (options.close) {
				title += ' <i class="glyphicon glyphicon-remove" data-close="' + id + '"></i>';
			}
			title += '	</a>';
			title += '</li>';

			content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + options.content + '</div>';
			// 加入TABS
			$(".nav-tabs").append(title);
			$(".tab-content").append(content);
		}
		// 激活TAB
		$("#tab_" + id).addClass('active');
		$("#" + id).addClass("active");

		return id;
	},
	getTabIndex: function(defaultTitle, index) {
		var isDuplicate = false;
		if (!index || isNaN(index)) {
			index = 0;
		}
		var title = defaultTitle + index;

		$.each($('ul.nav-tabs').find('li>a'), function(i, item) {
			if ($(item).text().trim() == title) {
				isDuplicate = true;
				return false;
			}
		});

		if (isDuplicate) {
			return commonTabs.getTabIndex(defaultTitle, index + 1);
		} else {
			return title;
		}
	},
	closeTab: function(id) {
		// 如果关闭的是当前激活的TAB，激活他的前一个TAB
		if ($('#mainContent').find("li.active").attr('id') == "tab_" + id) {
			if ($("#tab_" + id).prev().length > 0) {
				$("#tab_" + id).prev().addClass('active');
				$("#" + id).prev().addClass('active');
			} else {
				if ($("#tab_" + id).next().length > 0) {
					$("#tab_" + id).next().addClass('active');
					$("#" + id).next().addClass('active');
				}
			}
		}
		// 关闭TAB
		$("#tab_" + id).remove();
		$("#" + id).remove();
	},
	/**
	 * @param objType
	 *            类型数组, 暂定1为图, 2为表
	 * @param titles
	 *            标题数组
	 * @param clazzs
	 *            每个图/表的class数组
	 * @param hints 
	 *  		  提示
	 * @returns {String}
	 */
	addContent: function(types, titles, clazzs, hints) {
		var sHtml = '';

		sHtml += '<div class="row placeholders">';

		for (var i = 0; i < types.length; i++) {
			sHtml += '<hr />';
			var type = types[i];

			if (type == 1) {
				sHtml += '<div class="col-md-12 placeholder">';
				sHtml += '	<div>' + titles[i] + '</div>';
				if(hints && hints.length - 1 >= i) {
					sHtml += '	<div class="hints">' + hints[i] + '</div>'
				}
				sHtml += '	<div class="' + clazzs[i] + '" style="width: 100%; height: 600px;"></div>';
				sHtml += '</div>';
			} else if (type == 2) {
				sHtml += '<div class="col-md-12 placeholder">';
				sHtml += '	<div>';
				sHtml += '		<span style="padding-left: 90px;">' + titles[i] + '</span>';
				sHtml += '		<div class="btn btn-default btn-sm pull-right btnDownload">';
				sHtml += '			<span class="glyphicon glyphicon-circle-arrow-down"></span>&nbsp;&nbsp;下载';
				sHtml += '		</div>';
				sHtml += '	</div>';
				if(hints && hints.length - 1 >= i) {
					sHtml += '	<div class="hints">' + hints[i] + '</div>'
				}
				sHtml += '	<div class="' + clazzs[i] + ' table-responsive" style="width: 100%; min-height: 150px;">';
				sHtml += '		<table class="table table-striped"><thead></thead><tbody></tbody></table>';
				sHtml += '	</div>';
				sHtml += '</div>';
			} else {
				console.log('[很大的一个错误]无效的类型。');
			}

		}

		sHtml += '</div>';

		return sHtml;
	}
}

$(function() {
	mainHeight = $(document.body).height() - 45;
	$('.main-left,.main-right').height(mainHeight);
	$("[addtabs]").click(function() {
		addTabs({
			id: $(this).attr("id"),
			title: $(this).attr('title'),
			close: true
		});
	});

	$(".nav-tabs").on("click", "[data-close]", function(e) {
		id = $(this).attr("data-close");
		commonTabs.closeTab(id);
	});
});