var yxw = {};

yxw.fromFenToYuan = function(fenArr) {
	var yuanArr = [];
	$.each(fenArr, function(k, val) {
		yuanArr.push(((val) / 100).toFixed(2));
	});
	return yuanArr;
};

yxw.number_format = function(number, decimals, dec_point, thousands_sep) {
	/*
	 * 参数说明： number：要格式化的数字 decimals：保留几位小数 dec_point：小数点符号 thousands_sep：千分位符号
	 */
	number = (number + '').replace(/[^0-9+-Ee.]/g, '');
	var n = !isFinite(+number) ? 0 : +number, prec = !isFinite(+decimals) ? 0 : Math.abs(decimals), sep = (typeof thousands_sep === 'undefined') ? ','
			: thousands_sep, dec = (typeof dec_point === 'undefined') ? '.' : dec_point, s = '', toFixedFix = function(
			n, prec) {
		var k = Math.pow(10, prec);
		return '' + Math.ceil(n * k) / k;
	};

	s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
	var re = /(-?\d+)(\d{3})/;
	while (re.test(s[0])) {
		s[0] = s[0].replace(re, "$1" + sep + "$2");
	}

	if ((s[1] || '').length < prec) {
		s[1] = s[1] || '';
		s[1] += new Array(prec - s[1].length + 1).join('0');
	}
	return s.join(dec);
};

yxw.percentage = function(number, total) {
	if (total > 0) {
		return (Math.round(number1 / total * 10000) / 100.00 + "%");
	} else {
		return 0;
	}
}

yxw.percentageEx = function(number, total) {
	if (total > 0) {
		return '<span style="color: red">(' + (Math.round(number / total * 10000) / 100.00 + "%") + ')</span>';
	} else {
		return '<span style="color: red">(0)</span>';
	}
}

Date.prototype.format = function(format) {
	var o = {
		"M+": this.getMonth() + 1, // month
		"d+": this.getDate(), // day
		"h+": this.getHours(), // hour
		"m+": this.getMinutes(), // minute
		"s+": this.getSeconds(), // second
		"q+": Math.floor((this.getMonth() + 3) / 3), // quarter
		"S": this.getMilliseconds()
	// millisecond
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};