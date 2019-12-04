var stringUtils = {};

stringUtils.getValue = function(data) {
	if (!data || data == 'null') {
		data = '';
	}
	return data;
}

stringUtils.isNull = function(str){
	var flag = false;
	if (str === window.undefined || str == null || str == "" || str.length == 0 || str == "null") {
		flag = true;
	}
	return flag;
}