/*
Ajax 三级省市联动
http://code.ciaoca.cn/
日期：2012-7-18

settings 参数说明
-----
url:省市数据josn文件路径
prov:默认省份
city:默认城市
nodata:无数据状态
required:必选项
------------------------------ */
(function($) {
	$.fn.citySelect = function(settings) {
		if (this.length < 1) {
			return;
		}

		// 默认值
		settings = $.extend({
			url : appPath + "stats/js/common/area.js",
			prov : null,
			city : null,
			nodata : null,
			required : true
		}, settings);

		var box_obj = this;
		var prov_obj = box_obj.find(".prov");
		var city_obj = box_obj.find(".city");
		var prov_val = settings.prov;
		var city_val = settings.city;
		var select_prehtml = (settings.required) ? "" : "<option value='-1'>请选择</option>";
		var city_json;

		// 赋值市级函数
		var cityStart = function() {
			var prov_id = prov_obj.get(0).value;
			city_obj.empty().attr("disabled", true);

			// 遍历赋值市级下拉列表
			temp_html = select_prehtml;
			$.each(city_json, function(i, province) {
				if (province.id == prov_id) {
					$.each(province.cityMap, function(key, value) {
						temp_html += "<option value='" + value.id + "'>" + value.name + "</option>";
					});
				}
			});

			city_obj.html(temp_html).attr("disabled", false).css({
				"display" : "",
				"visibility" : ""
			});
		};

		var init = function() {
			// 遍历赋值省份下拉列表
			temp_html = select_prehtml;

			$.each(city_json, function(i, province) {
				temp_html += "<option value='" + province.id + "'>" + province.name + "</option>";
			});

			prov_obj.html(temp_html);

			// 若有传入省份与市级的值，则选中。（setTimeout为兼容IE6而设置）
			setTimeout(function() {
				if (settings.prov != null) {
					prov_obj.val(settings.prov);
					cityStart();
					setTimeout(function() {
						if (settings.city != null) {
							city_obj.val(settings.city);
						}
					}, 1);
				} 
					// 设置默认
					if (!prov_obj.val()) {
						prov_obj.find('option[value="-1"]').attr('selected', 'selected');
					}
			}, 1);

			// 选择省份时发生事件
			prov_obj.bind("change", function() {
				cityStart();
			});
			
		};

		// 设置省市json数据
		if (typeof (settings.url) == "string") {
			$.getJSON(settings.url, function(json) {
				city_json = json;
				init();
			});
		} else {
			city_json = settings.url;
			init();
		}
	};
})(jQuery);