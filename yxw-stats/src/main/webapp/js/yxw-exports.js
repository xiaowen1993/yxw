var exports = {
	/**
	 * 获取浏览器版本
	 */
	getExplorer: function() {
		var explorer = window.navigator.userAgent;
		// ie ie需要区分IE8及以下-IE8以上两个版本
		if (explorer.indexOf("MSIE") >= 0 || !!window.ActiveXObject || "ActiveXObject" in window) {
			return 'ie';
		}
		// firefox
		else if (explorer.indexOf("Firefox") >= 0) {
			return 'Firefox';
		}
		// Chrome
		else if (explorer.indexOf("Chrome") >= 0) {
			return 'Chrome';
		}
		// Opera
		else if (explorer.indexOf("Opera") >= 0) {
			return 'Opera';
		}
		// Safari
		else if (explorer.indexOf("Safari") >= 0) {
			return 'Safari';
		}
	},
	getIEExplorer: function() {
		return document.getElementsByClassName ? 'high' : 'low';
	},
	/**
	 * 导出table并存为excel. 默认为xlsx格式，用户可以自己手动改后缀
	 * 
	 * @param obj
	 *            table对象
	 * @param fileName
	 *            保存的文件名
	 * @param hideObj
	 *            隐藏的调用(使用一个display: none的a标签完成)
	 */
	exportTableAsExcel: function(obj, fileName, hideObj) {
		// 原本的老方法，需要区分浏览器，部分IE浏览器需要更改安全限制
		/**
		var explorer = exports.getExplorer();
		if (explorer == 'ie') {
			// IE导出
			exports.exportExcelInIE(obj, fileName);
		} else {
			// 非IE导出
			exports.exportExcelNotInIE(obj, fileName, hideObj);
		}
		 */

		// 后台生成
		// 新建表单 -- 直接生成一个空页面的链接，数据缓冲好后，自动关闭，会询问是否下载
		// 如果不想新生成页面，网上有使用Iframe的方式，
		var form = document.createElement("form");
		// var iframe = document.createElement("iframe");
		// document.body.appendChild(iframe);
		// iframe.appendChild(form);
		document.body.appendChild(form);
		form.method = "post";
		form.action = "/common/springMvcDownload"; // springMvc文件下载
		// form.action = "/common/commonDownload";	
		// form.target = "_blank";		// 设定target就会新打开一个窗口

		// 数据元素
		var dataElement = document.createElement("input");
		dataElement.setAttribute("name", "data");
		dataElement.setAttribute("type", "hidden");
		dataElement.setAttribute("value", obj.outerHTML); // IE outerHTML有兼容性问题
		
		// dataElement.setAttribute("value", $(obj).html());
		form.appendChild(dataElement);
		// 文件名
		var fileNameElement = document.createElement("input");
		fileNameElement.setAttribute("name", "fileName");
		fileNameElement.setAttribute("type", "hidden");
		fileNameElement.setAttribute("value", fileName);
		form.appendChild(fileNameElement);
		
		form.submit();
		// document.body.removeChild(iframe);
		document.body.removeChild(form);
	},
	exportExcelInIE: function(obj, fileName) {
		var oXL;
		try {
			oXL = new ActiveXObject("Excel.Application");
		} catch (e) {
			console.log("Nested catch caught " + e);
		}

		if (oXL) {
			// 创建AX对象excel
			var oWB = oXL.Workbooks.Add();
			// 获取workbook对象
			var xlsheet = oWB.Worksheets(1);
			// 激活当前sheet
			var sel = document.body.createTextRange();
			// 把表格中的内容移到TextRange中
			sel.moveToElementText(obj);
			// 全选TextRange中内容
			sel.select;
			// 复制TextRange中内容
			sel.execCommand("Copy");
			// 粘贴到活动的EXCEL中
			xlsheet.Paste();
			// 设置excel可见属性
			oXL.Visible = true;
			// 设置sheet的名称
			xlsheet.name = fileName;

			var fname;
			try {
				fname = oXL.Application.GetSaveAsFilename(fileName + ".xls", "Excel Spreadsheets (*.xls), *.xls");
			} catch (e) {
				console.log("Nested catch caught " + e);
			} finally {
				oWB.SaveAs(fname);

				oWB.Close(savechanges = false);
				// xls.visible = false;
				oXL.Quit();
				oXL = null;
				// 结束excel进程，退出完成
				timer = window.setInterval("exports.cleanup();", 1);

			}
		} else {
			console.log('请激活ActiveX组件');
		}
	},
	cleanup: function() {
		window.clearInterval(timer);
		CollectGarbage();
	},
	exportExcelNotInIE: (function(obj, fileName, hideObj) {
		var uri = 'data:application/vnd.ms-excel;base64,';
		var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>';
		var base64 = function(s) {
			return window.btoa(unescape(encodeURIComponent(s)))
		};
		var format = function(s, c) {
			return s.replace(/{(\w+)}/g, function(m, p) {
				return c[p];
			})
		};
		return function(obj, fileName, hideObj) {
			var ctx = {
				worksheet: fileName || 'Worksheet',
				table: obj.innerHTML
			}
			hideObj.href = uri + base64(format(template, ctx));
			hideObj.download = fileName + '.xlsx';
			hideObj.click();
		}
	})()
}

var timer;