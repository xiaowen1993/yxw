<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>就医宝典beta</title>
</head>
<body>
<div id="body">
	<section class="guide">
		<article class="guideBanner">
			<h6>
				深圳市属医院就医指南
				<span>2015-2016</span>
			</h6>
		</article>
		<!-- <div class="space15"></div> -->
		<article class="guideTableWrap guideTable1" data-arr='arr1'>
			<h6 class="guideTit">就医流程</h6>
			<ul class="guideTable">
				<!-- <li><span>前言</span></li>
				<li><span class="active">市民就医指导原则</span></li>
				<li><span>门诊就医</span></li>
				<li><span>住院就医</span></li>
				<li><span>检验检查注意事项</span></li>
				<li class="more"><span >更多</span></li> -->
			</ul>
		</article>
		<article class="guideTableWrap guideTable2" data-arr='arr2'>
			<h6 class="guideTit">按医院指引</h6>
			<ul class="guideTable">
			</ul>
		</article>
		<article class="guideTableWrap guideTable3" data-arr='arr3'>
			<h6 class="guideTit">按疾病分类诊疗科目指引</h6>
			<ul class="guideTable">
			</ul>
		</article>
		<article class="guideTableWrap">
			<h6 class="guideTit">学科情况</h6>
			<ul class="guideTable">
				<li onclick="go('http://wx.yx129.net/show.php?pid=935')"><span>重点学科</span></li>
				<li onclick="go('http://wx.yx129.net/show.php?pid=934')"><span>领先学科</span></li>
			</ul>
		</article>
		<article class="guideTableWrap">
			<h6 class="guideTit">就医指南英文版</h6>
			<p class="guideTip" onclick="go('http://wx.yx129.net/show.php?pid=948')">
				Guide to Choosing a Public Hospital in Shenzhen
			</p>
		</article>
	</section>
</div>
</body>

<script>
	var data = {
		arr1 :[
			{
				item:"前言",
				url:'http://wx.yx129.net/show.php?pid=896',
				active:true
			},
			{
				item:"市民就医指导原则",
				url:'http://wx.yx129.net/show.php?pid=909'
			},
			{
				item:"门诊就医",
				url:'http://wx.yx129.net/show.php?pid=920'
			},
			{
				item:"住院就医",
				url:'http://wx.yx129.net/show.php?pid=921'
			},
			{
				item:"检验检查有关注意事项",
				url:'http://wx.yx129.net/show.php?pid=923'
			},
			{
				item:"体检咨询电话",
				url:'http://wx.yx129.net/show.php?pid=925'
			},
		],
		arr2 : [
			{
				item:'深圳市人民医院',
				url	: 'http://wx.yx129.net/show.php?pid=985'
			},
			{
				item:'深圳市第二人民医院',
				url	: 'http://wx.yx129.net/show.php?pid=993'
			},
			{
				item:'深圳市妇幼保健院',
				url	: 'http://wx.yx129.net/show.php?pid=953'
			},
			{
				item:'深圳市儿童医院',
				url	: 'http://wx.yx129.net/show.php?pid=987'
			},
			{
				item:'孙逸仙心血管医院',
				url	: 'http://wx.yx129.net/show.php?pid=954'
			},
			{
				item:'深圳市中医院',
				url	: 'http://wx.yx129.net/show.php?pid=955'
			},
			{
				item:'深圳市眼科医院',
				url	: 'http://wx.yx129.net/show.php?pid=952'
			},
			{
				item:'深圳市康宁医院',
				url	: 'http://wx.yx129.net/show.php?pid=989'
			},
			{
				item:'香港大学深圳医院',
				url	: 'http://wx.yx129.net/show.php?pid=950'
			},
			{
				item:'北京大学深圳医院',
				url	: 'http://wx.yx129.net/show.php?pid=994'
			},
		],
		arr3 : [
			{
				item:"眼部疾病",
				url:'http://wx.yx129.net/show.php?pid=915'
			},
			{
				item:"口腔疾病",
				url:'http://wx.yx129.net/show.php?pid=916'
			},
			{
				item:"耳鼻咽喉疾病",
				url:'http://wx.yx129.net/show.php?pid=917'
			},
			{
				item:"肿瘤",
				url:'http://wx.yx129.net/show.php?pid=918'
			},
			{
				item:"感染性（传染性）疾病",
				url:'http://wx.yx129.net/show.php?pid=919'
			},
			{
				item:"生殖医学科疾病",
				url:'http://wx.yx129.net/show.php?pid=941'
			},
			{
				item:"中医科疾病",
				url:'http://wx.yx129.net/show.php?pid=942'
			},
			{
				item:"中西医结合科疾病",
				url:'http://wx.yx129.net/show.php?pid=943'
			},
			{
				item:"康复治疗",
				url:'http://wx.yx129.net/show.php?pid=944'
			},
			{
				item:"疼痛科疾病",
				url:'http://wx.yx129.net/show.php?pid=945'
			},
			{
				item:"烧伤科疾病",
				url:'http://wx.yx129.net/show.php?pid=973'
			},
			{
				item:"皮肤性病科疾病",
				url:'http://wx.yx129.net/show.php?pid=974'
			},
			{
				item:"精神心理疾病",
				url:'http://wx.yx129.net/show.php?pid=975'
			},
			{
				item:"重大传染病或指定收治医院",
				url:'http://wx.yx129.net/show.php?pid=976'
			},
			{
				item:"蛇伤",
				url:'http://wx.yx129.net/show.php?pid=977'
			},
			{
				item:"内科系统疾病",
				url:'http://wx.yx129.net/show.php?pid=979'
			},
			{
				item:"外科系统疾病",
				url:'http://wx.yx129.net/show.php?pid=980'
			},
			{
				item:"妇产科疾病",
				url:'http://wx.yx129.net/show.php?pid=981'
			},
			{
				item:"儿科疾病",
				url:'http://wx.yx129.net/show.php?pid=982'
			},
			{
				item:"新生儿科疾病",
				url:'http://wx.yx129.net/show.php?pid=984'
			}

		]
	}
	$.fn.bindGuide = function(opt){
		var listData = opt.data;
		var $_ts = $(this);
		var $ul = $_ts.find('.guideTable');
		var html = "",i,len = listData.length;
		if(len > 6){
			
			var sList = [],
				html1="",
				len1;

			for( i=0 ; i<5 ; i++ ){
				sList.push(listData[i]);
			}

			for(i = 0,len1=sList.length; i<len1 ; i++){
				html1 += "<li onclick=\"go('"+sList[i].url+"')\"><span>"+sList[i].item+"</span></li>"
			}
			html1 += "<li class='more'><span>更多</span></li>"
			$ul.html(html1);
			for(i = 0; i<len ;i++){
				html += "<li onclick=\"go('"+listData[i].url+"')\"><span>"+listData[i].item+"</span></li>"
			}
			var $more = $_ts.find('.more');
			$more.on('click',function(){
				$ul.html(html);
			})
		}else{
			for(i = 0;i<len;i++){
				html += "<li onclick=\"go('"+listData[i].url+"')\"><span>"+listData[i].item+"</span></li>"
			}
			$ul.html(html);
		}
	}
	$(function(){
		 FastClick.attach(document.body);
		var body = document.body;
		var li = $('.guideTable li');
		$(body)
		.on('touchstart','.guideTable li',function(){
			// li.removeClass('hover')
			var ts = $(this);
			$(this).addClass('hover');
			setTimeout(function(){
				ts.removeClass('hover');
			},500)
		})
		$('.guideTable1').bindGuide({
			data : data.arr1
		})
		$('.guideTable2').bindGuide({
			data : data.arr2
		})
		$('.guideTable3').bindGuide({
			data : data.arr3
		})
	})
	
	
</script>
</script>
</html>