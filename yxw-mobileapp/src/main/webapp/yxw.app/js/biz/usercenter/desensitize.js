/*
 * 脱敏单元
 * 姓名		首字变*			长度必须大于1，否则不进行脱敏操作
 * 身份证		显示前面3位，后面4位	长度必须为15或18位，否则不进行脱敏操作
 * 手机号码	显示前面3位，后面4位	长度必须为11位，否则不进行脱敏操作
 */
var desensitize = {
	desName: function(data) {
		var result = data;
		if (data.length > 1) {
			result = '*' + data.substring(1, data.length);
		}
		
		return result;
	}, 
	desIdNo: function(data) {
		return result = data.substring(0, 1) + '********************************'.substring(0, data.length-2) + data.substring(data.length - 1, data.length);;
	},
	desMobile: function(data) {
		var result = data;
		if (data.length == 11) {
			result = data.substring(0,3) + '****' + data.substring(data.length - 4, data.length);
		}
		
		return result;
	}
};

$(function() {
	
 });