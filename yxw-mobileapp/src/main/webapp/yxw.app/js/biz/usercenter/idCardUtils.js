/*
 * 脱敏单元
 * 姓名		首字变*			长度必须大于1，否则不进行脱敏操作
 * 身份证		显示前面3位，后面4位	长度必须为18位，否则不进行脱敏操作
 * 手机号码	显示前面3位，后面4位	长度必须为11位，否则不进行脱敏操作
 */
var idCardUtils = {
	/**----------自动获取性别----------**/
	getGender: function(idCardNo) {
		return idCardNo.substring(16,17) % 2 == 0 ? "2" : "1";
	}, 
	/**----------自动获取出生日期----------**/
	getBirth: function(idCardNo) {
		return birth = idCardNo.substring(6,10)+"-"+idCardNo.substring(10,12)+"-"+idCardNo.substring(12,14);;
	},
	getAge: function(birth) {
		var date = new Date(birth);
		var now = new Date();
		return Math.floor((now.getTime() - date.getTime()) / 365 / 24 / 60 / 60 / 1000);
	},
	/**----------身份证验证----------**/
	validateIdNo: function(idCardNo) {
		var city = {
			11 : "北京",
			12 : "天津",
			13 : "河北",
			14 : "山西",
			15 : "内蒙古",
			21 : "辽宁",
			22 : "吉林",
			23 : "黑龙江 ",
			31 : "上海",
			32 : "江苏",
			33 : "浙江",
			34 : "安徽",
			35 : "福建",
			36 : "江西",
			37 : "山东",
			41 : "河南",
			42 : "湖北 ",
			43 : "湖南",
			44 : "广东",
			45 : "广西",
			46 : "海南",
			50 : "重庆",
			51 : "四川",
			52 : "贵州",
			53 : "云南",
			54 : "西藏 ",
			61 : "陕西",
			62 : "甘肃",
			63 : "青海",
			64 : "宁夏",
			65 : "新疆",
			71 : "台湾",
			81 : "香港",
			82 : "澳门",
			91 : "国外 "
		};
		var tip = "";
		var reg = /^[1-9]\d{5}((1[89]|20)\d{2})(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[\d|X]$/i;
		var isValidate = true;

		if (!idCardNo || !reg.test(idCardNo)) {
			tip = "身份证号格式错误";
			isValidate = false;
		}

		else if (!city[idCardNo.substr(0, 2)]) {
			tip = "身份证地址编码错误";
			isValidate = false;
		} else {
			//18位身份证需要验证最后一位校验位
			if (idCardNo.length == 18) {
				idCardNo = idCardNo.split('');
				//∑(ai×Wi)(mod 11)
				//加权因子
				var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
						8, 4, 2 ];
				//校验位
				var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
				var sum = 0;
				var ai = 0;
				var wi = 0;
				for ( var i = 0; i < 17; i++) {
					ai = idCardNo[i];
					wi = factor[i];
					sum += ai * wi;
				}
				var last1 = parity[sum % 11];
				if (parity[sum % 11] != idCardNo[17]) {
					tip = "校验位错误";
					isValidate = false;
				}
			}
		}
		// alert(tip);
		//isValidate = true;		// 免验证
		return isValidate;
	}
};
