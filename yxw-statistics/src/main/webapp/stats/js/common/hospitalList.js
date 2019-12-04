var hospitalList = {
	showDetail: function(hospitalName, hospitalCode, bizType) {
		var url = "";
		switch(bizType) {
		case 1:
			url = "stats/main/index";
			break;
		case 2:
			url = "stats/card/index";
			break;
		case 3:
			url = "stats/order/index";
			break;
		case 11:
			url = "manager/order/index";
			break;
		case 12:
			url = "manager/card/index";
			break;
		case 13:
			url = "manager/register/index";
			break;
		default:
			console.log("i don't know what is bizType=" + bizType);
		}
		
		url = appPath + url + '?hospitalCode=' + hospitalCode + '&hospitalName=' + encodeURIComponent(hospitalName) + '&bizType=' + bizType;
		window.location.href = url;
	},
	findHospital: function(hospitalName) {
		
	}
}