/**
 * APP定位js
 */
var $app = {};

$app.location = {};

$app.location.save = function() {
	var platformLocations = [];

	var forms = $('form');
	for (var i = 0; i < forms.length; i++) {
		var platformLocation = {};
		var platformCode = $(forms).eq(i).attr('data-code');
		platformLocation.platformCode = platformCode;
		platformLocation.checkedDatas = [];
		var checkeds = $(forms).eq(i).find(".form-check .city-content .check");
		for (var j = 0; j < checkeds.length; j++) {
			var checkedItem = $(checkeds[j]).find("input:checkbox");

			var item = {};
			item.cityId = checkedItem.val();
			item.cityCode = checkedItem.attr("data-city-code");
			item.name = checkedItem.attr("data-name");
			item.level = checkedItem.attr("data-level");
			item.pinyin = checkedItem.attr("data-pinyin");
			item.platformCode = platformCode;
			item.status = 1;
			item.showSort = 0;

			platformLocation.checkedDatas.push(item);
		}

		platformLocations.push(platformLocation);
	}

	console.log(platformLocations);
	$.ajax({
		url: 'saveLocation',
		data: {
			jsonAppLocations: $.toJSON(platformLocations)
		},
		dataType: 'json',
		type: 'POST',
		error: function(XMLHQ, errorMsg) {
			console.log(errorMsg);
			alert(errorMsg);
		},
		success: function(data) {
			console.dir(data);

			if (data.status == 'OK') {
				alert("保存成功");
			} else {
				alert(data.message);
			}
		}
	});
}