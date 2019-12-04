<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="../js/iscroll/scrollbar.css">
<script type="text/javascript" src="../js/iscroll/iscroll.js"></script>
<script>
	var myScroll, pullDownEl, pullDownOffset, pullUpEl, pullUpOffset, generatedCount = 0;

	/**
	 * 下拉刷新 （自定义实现此方法）
	 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
	 */
	function pullDownAction() {
		 myScroll.refresh();
	}

	/**
	 * 滚动翻页 （自定义实现此方法）
	 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
	 */
	function pullUpAction() {
		 if(isRefresh == false){
			 isRefresh = true;//确保每次只有一个更新
			 if(currentPage < TotalPage){
				 setTimeout(function() { // 
						//延迟一秒后执行
						getInfo(currentPage+1, size);
					}, 1000); //
			 }else{
				 isRefresh = false;
				 myScroll.refresh();
			 }
		 }else{
		 }
	}

	/**
	 * 初始化iScroll控件
	 */
	function loaded() {
		pullDownEl = document.getElementById('pullDown');
		pullDownOffset = pullDownEl.offsetHeight;
		pullUpEl = document.getElementById('pullUp');
		pullUpOffset = pullUpEl.offsetHeight;

		myScroll = new iScroll(
				'wrapper',
				{
					scrollbarClass : 'myScrollbar', /* 重要样式 */
					useTransition : false, /* 此属性不知用意，本人从true改为false */
					topOffset : pullDownOffset,
					onRefresh : function() {
						if (pullDownEl.className.match('loading')) {
							pullDownEl.className = '';
							pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
						} else if (pullUpEl.className.match('loading')) {
							pullUpEl.className = '';
							pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
						}
					},
					onScrollMove : function() {
						if (this.y > 5 && !pullDownEl.className.match('flip')) {
							pullDownEl.className = 'flip';
							pullDownEl.querySelector('.pullDownLabel').innerHTML = '松手开始更新...';
							this.minScrollY = 0;
						} else if (this.y < 5
								&& pullDownEl.className.match('flip')) {
							pullDownEl.className = '';
							pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
							this.minScrollY = -pullDownOffset;
						} else if (this.y < (this.maxScrollY - 5)
								&& !pullUpEl.className.match('flip')) {
							pullUpEl.className = 'flip';
							pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手开始更新...';
							this.maxScrollY = this.maxScrollY;
						} else if (this.y > (this.maxScrollY + 5)
								&& pullUpEl.className.match('flip')) {
							pullUpEl.className = '';
							pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
							this.maxScrollY = pullUpOffset;
						}
					},
					onScrollEnd : function() {
						if (pullDownEl.className.match('flip')) {
							pullDownEl.className = 'loading';
							pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';
							pullDownAction(); // Execute custom function (ajax call?)
						} else if (pullUpEl.className.match('flip')) {
							pullUpEl.className = 'loading';
							pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
							pullUpAction(); // Execute custom function (ajax call?)
						}
					}
				});

		setTimeout(function() {
			document.getElementById('wrapper').style.left = '0';
		}, 800);
	}

	//初始化绑定iScroll控件 
	document.addEventListener('touchmove', function(e) {
		e.preventDefault();
	}, false);
	document.addEventListener('DOMContentLoaded', loaded, false);
	
	var currentPage = 1;
	var size = -1;
	var TotalPage = 1;
	var isRefresh = false;
	
	//获取绑卡历史数据
	function getInfo(pageNumber, pageSize) {
		$.post("/yxw_framework/attach/query", {
			pageNumber : pageNumber,
			pageSize : pageSize
		}, function(response) {
			var el, li, i;
			el = document.getElementById('thelist');

			for (i = 0; i < response.rows.length; i++) {
				li = document.createElement('li');
				li.innerHTML =pageNumber-1+''+i+'--'+response.rows[i].archiveName+'--'+response.rows[i].uploadDate+'--'
				+ Math.floor((response.rows[i].attachSize)/1000)+'kb'+'<button onclick="javascript:down(\''+response.rows[i].archiveName+'\')">下载</button>';
				el.appendChild(li, el.childNodes[0]);
				myScroll.refresh();
			}
			//跟新页面参数
			TotalPage = response.totalPage;
			size = response.pageSize;
			currentPage= response.currentPage;
			isRefresh = false;
			
			return false;
		});

	}
	
	function down(archiveName){
		$.post("/yxw_framework/attach/downFile", {
			archiveName : archiveName
		}, function(response){});
	}
	getInfo(currentPage, size);
</script>
<style type="text/css" media="all">
body, ul, li {
	padding: 0;
	margin: 0;
	border: 0;
}

body {
	font-size: 12px;
	-webkit-user-select: none;
	-webkit-text-size-adjust: none;
	font-family: helvetica;
}

#header {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 45px;
	line-height: 45px;
	background-image: -webkit-gradient(linear, 0 0, 0 100%, color-stop(0, #fe96c9),
		color-stop(0.05, #d51875), color-stop(1, #7b0a2e));
	background-image: -moz-linear-gradient(top, #fe96c9, #d51875 5%, #7b0a2e);
	background-image: -o-linear-gradient(top, #fe96c9, #d51875 5%, #7b0a2e);
	padding: 0;
	color: #eee;
	font-size: 20px;
	text-align: center;
}

#header a {
	color: #f3f3f3;
	text-decoration: none;
	font-weight: bold;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.5);
}

#footer {
	position: absolute;
	bottom: 0;
	left: 0;
	width: 100%;
	height: 48px;
	background-image: -webkit-gradient(linear, 0 0, 0 100%, color-stop(0, #999),
		color-stop(0.02, #666), color-stop(1, #222));
	background-image: -moz-linear-gradient(top, #999, #666 2%, #222);
	background-image: -o-linear-gradient(top, #999, #666 2%, #222);
	padding: 0;
	border-top: 1px solid #444;
}

#wrapper {
	position: absolute;
	z-index: 1;
	top: 45px;
	bottom: 48px;
	left: 0;
	width: 100%;
	background: #555;
	overflow: auto;
}

#scroller {
	position: relative;
	/*	-webkit-touch-callout:none;*/
	-webkit-tap-highlight-color: rgba(0, 0, 0, 0);
	float: left;
	width: 100%;
	padding: 0;
}

#scroller ul {
	position: relative;
	list-style: none;
	padding: 0;
	margin: 0;
	width: 100%;
	text-align: left;
}

#scroller li {
	padding: 0 10px;
	height: 40px;
	line-height: 40px;
	border-bottom: 1px solid #ccc;
	border-top: 1px solid #fff;
	background-color: #fafafa;
	font-size: 14px;
}

#scroller li>a {
	display: block;
}

/**
 *
 * 下拉样式 Pull down styles
 *
 */
#pullDown, #pullUp {
	background: #fff;
	height: 40px;
	line-height: 40px;
	padding: 5px 10px;
	border-bottom: 1px solid #ccc;
	font-weight: bold;
	font-size: 14px;
	color: #888;
}

#pullDown .pullDownIcon, #pullUp .pullUpIcon {
	display: block;
	float: left;
	width: 40px;
	height: 40px;
	background: url(pull-icon@2x.png) 0 0 no-repeat;
	-webkit-background-size: 40px 80px;
	background-size: 40px 80px;
	-webkit-transition-property: -webkit-transform;
	-webkit-transition-duration: 250ms;
}

#pullDown .pullDownIcon {
	-webkit-transform: rotate(0deg) translateZ(0);
}

#pullUp .pullUpIcon {
	-webkit-transform: rotate(-180deg) translateZ(0);
}

#pullDown.flip .pullDownIcon {
	-webkit-transform: rotate(-180deg) translateZ(0);
}

#pullUp.flip .pullUpIcon {
	-webkit-transform: rotate(0deg) translateZ(0);
}

#pullDown.loading .pullDownIcon, #pullUp.loading .pullUpIcon {
	background-position: 0 100%;
	-webkit-transform: rotate(0deg) translateZ(0);
	-webkit-transition-duration: 0ms;
	-webkit-animation-name: loading;
	-webkit-animation-duration: 2s;
	-webkit-animation-iteration-count: infinite;
	-webkit-animation-timing-function: linear;
}

@
-webkit-keyframes loading {from { -webkit-transform:rotate(0deg)translateZ(0);
	
}

to {
	-webkit-transform: rotate(360deg) translateZ(0);
}
}
</style>
</head>
<body>
	<h2>下载列表</h2>
	<div id="wrapper">
		<div id="scroller">
			<div id="pullDown">
				<span class="pullDownIcon"></span><span class="pullDownLabel">下拉刷新...</span>
			</div>
			<ul id="thelist">
			</ul>
			<div id="pullUp">
				<span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span>
			</div>

		</div>
	</div>
	
</body>
</html>