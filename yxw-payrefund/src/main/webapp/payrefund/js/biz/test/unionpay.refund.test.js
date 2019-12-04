$("#refundBtn").click(function() {
	$("textarea").text("");
	
	var datas = $("#myForm").serializeArray();
	
	$.ajax({
		url : "unionpayRefund",
		data : datas,
		async : true,
		dataType : 'json',
		timeout : 15000,
		type : 'POST',
		error : function(XMLHQ, errorMsg) {
			console.log(errorMsg);
		},
		success : function(data) {
			console.dir(data);
			$("textarea").text(data.fmtData);
		}
	});
});