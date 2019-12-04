var download = {
	bindEvent: function() {
		$('#btnDownload').off('click').on('click', function() {
			download.doDownload();
		});
	},
	doDownload: function() {
		alert('下载不了，请联系帮主解决!~~~');
	}
}

$(function() {
	download.bindEvent();
});