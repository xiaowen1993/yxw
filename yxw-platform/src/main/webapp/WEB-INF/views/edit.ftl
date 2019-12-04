<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width,  initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0,  user-scalable=no" />
<#include "/common/common.ftl">
<link href="${basePath}css/yxw_wechat.css" rel="stylesheet" type="text/css" />
<link href="${basePath}css/style_green.css" rel="stylesheet" type="text/css" />
<link href="${basePath}css/form.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath}js/grayscale.js"></script>
<title>添加就诊卡</title>
<script type="text/javascript">
	function smt() {
		//$("#smt").removeAttr("onclick");//禁用
		grayscale($("#smt"));
		var testName = /^[a-zA-Z0-9\u4e00-\u9fa5]{1,30}$/;
		var name = $("input[name='name']");
		var testIdNo = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		var idNo = $("input[name='idNo']");
		var testMobileNo = /^1\d{10}$/;
		var mobileNo = $("input[name='mobileNo']");
		var testCardNo = /^[a-zA-Z0-9]{1,30}/;
		var cardNo = $("input[name='cardNo']");
		if (!$.trim(name.val())) {
			showInfo("请输入姓名!");
			grayscale.reset($("#smt"));
			$("#smt").attr("onclick", "smt()");//启用
			return false;
		} else if (!testName.exec(name.val())) {
			showInfo("姓名填写不正确!");
			grayscale.reset($("#smt"));
			$("#smt").attr("onclick", "smt()");//启用
			return false;
		}
		if (!$.trim(idNo.val())) {
			showInfo("请输入身份证号码!");
			grayscale.reset($("#smt"));
			$("#smt").attr("onclick", "smt()");//启用
			return false;
		} else if (!identityCodeValid(idNo.val())) {
			showInfo("身份证号码填写不正确!");
			grayscale.reset($("#smt"));
			$("#smt").attr("onclick", "smt()");//启用
			return false;
		}

		if (!$.trim(mobileNo.val())) {
			showInfo("请输入手机号码!");
			grayscale.reset($("#smt"));
			$("#smt").attr("onclick", "smt()");//启用
			return false;
		} else if (!testMobileNo.exec(mobileNo.val())) {
			showInfo("手机号码填写不正确!");
			grayscale.reset($("#smt"));
			$("#smt").attr("onclick", "smt()");//启用
			return false;
		}

		if (!$.trim(cardNo.val())) {
			showInfo("请输入就诊卡号!");
			grayscale.reset($("#smt"));
			$("#smt").attr("onclick", "smt()");//启用
			return false;
		} else if (!testCardNo.exec(cardNo.val())) {
			showInfo("就诊卡号填写不正确!");
			grayscale.reset($("#smt"));
			$("#smt").attr("onclick", "smt()");//启用
			return false;
		}
		$("select[name='ownership']").removeAttr("disabled");
		$.ajax({
				url: '${basePath}ftl/save',
				data: $("form[name='medicalcardForm']").serializeArray(),
				dataType: 'json',
				timeout: 30000,
				type: 'POST',
				error: function(XMLHQ, errorMsg) {
				}, 
				success: function(data) {
					if(data.id!="null"){
						alert("绑定成功!");
					}else{
						alert("绑定失败!");
					}
				}
			});
	}
	function showInfo(content) {
		$(".toast_top div").text(content);
		$(".toast_top").fadeIn("slow");
		//自动关闭提示层  
		setTimeout(function() {
			$(".toast_top").fadeOut("slow");
		}, 1000);
	};

	function identityCodeValid(code) {
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
		var reg15 = /^[1-9]\d{7}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}$/i;
		var reg18 = /^[1-9]\d{5}((1[89]|20)\d{2})(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[\d|X]$/i;
		var isValidate = true;

		if (!code || !(reg18.test(code) || reg15.test(code))) {
			tip = "身份证号格式错误";
			isValidate = false;
		}

		else if (!city[code.substr(0, 2)]) {
			tip = "身份证地址编码错误";
			isValidate = false;
		} else {
			//18位身份证需要验证最后一位校验位
			if (code.length == 18) {
				code = code.split('');
				//∑(ai×Wi)(mod 11)
				//加权因子
				var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8,
						4, 2 ];
				//校验位
				var parity1 = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
				//var parity2 = [ 1, 0, 'x', 9, 8, 7, 6, 5, 4, 3, 2 ];
				var sum = 0;
				var ai = 0;
				var wi = 0;
				for ( var i = 0; i < 17; i++) {
					ai = code[i];
					wi = factor[i];
					sum += ai * wi;
				}
				//if ((parity1[sum % 11] != code[17]) && (parity2[sum % 11] != code[17])) {
				if (parity1[sum % 11] != code[17]) {
					tip = "校验位错误";
					isValidate = false;
				}
			}
		}
		// alert(tip);
		return isValidate;
	};
</script>
</head>

<body>
	<div id="main">
		<div class="toast_top">
			<div></div>
		</div>
		<form id="medicalcardForm" name="medicalcardForm" method="post">
			<div class="list_box">
				<ul class="user_list">
					<li class="list_line2">
						<div>患者姓名</div>
						<span class="user_list_input">
							<input name="name" maxlength="30" placeholder="请输入姓名" />
						</span>
					</li>
					<li class="list_line2">
						<div>身份证号</div>
						<span class="user_list_input">
							<input name="idNo" maxlength="18" placeholder="请输入身份证号码" />
						</span>
						</li>
					<li class="list_line2">
						<div>手机号</div>
						<span class="user_list_input">
							<input type="tel" name="mobileNo" maxlength="11" placeholder="请输入手机号码" />
						</span>
					</li>
					<li class="list_line2">
						<div>与本人关系</div> 
							<div class="dropdown">
								<select name="ownership" class="dropdown-select">
									<option value="1">本人</option>
									<option value="2">他人</option>
								</select>
							</div>
						</li>
					<li>
						<div>就诊卡</div>
						<span class="user_list_input">
							<input name="cardNo" maxlength="255" placeholder="请输入卡号" />
					</span></li>
				</ul>
			</div>
			<div class="box_info">以上信息请务必和您在医院登记的信息保持一致，否则将无法完成绑定。</div>

			<div class="bnt bnt_1">
				<a id="smt" href="javascript:void(0);" onclick="smt()">确认绑定</a>
			</div>
			<s:if test="msg!=null">
				<div class="box_info">
					<span style="color:red;font-size: 16px;">
					
					</span>
				</div>
			</s:if>
		</form>
	</div>
	<#include "/common/copyright_footer.ftl">
</body>
</html>
