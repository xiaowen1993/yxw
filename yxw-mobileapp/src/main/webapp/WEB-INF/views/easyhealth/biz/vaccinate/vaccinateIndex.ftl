<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>打疫苗</title>
</head>
<body>

		<div id="body">
		    <div id="myCenter">
		        <ul class="yx-list vaccinateNav">
		            <li class="arrow" onclick="go('region')">
		                <div class="vaccinateNav-icon ">&#xe692;</div>
		                <div class="b">
		                    <div class="title">去哪里打疫苗</div>
		                    <div class="mate color999">深圳市预防接种门诊查询</div>
		                </div>
		            </li>
		            <li class="arrow" onclick="go('schedule')">
		                <div class="vaccinateNav-icon ">&#xe696;</div>
		                <div class="b">
		                    <div class="title">打疫苗时间表</div>
		                    <div class="mate color999">宝宝6岁前的疫苗清单</div>
		                </div>
		            </li>
		            <li class="arrow" onclick="go('http://wx.yx129.net/show.php?pid=856')">
		                <div class="vaccinateNav-icon ">&#xe697;</div>
		                <div class="b">
		                    <div class="title">疫苗小知识，爸妈必看</div>
		                    <div class="mate color999">关爱宝宝健康</div>
		                </div>
		            </li>
		            <!-- <li>
		                <div class="vaccinateNav-icon">&#xe693;</div>
		                <div class="b">
		                    <div class="title">儿童疫苗接种查询</div>
		                    <div class="mate color999">查询儿童疫苗接种情况</div>
		                </div>
		            </li> -->
		        </ul>
		    </div>
		
		</div>

		<#include "/easyhealth/common/footer.ftl">
		
		<script type="text/javascript">
			function doGoBack() {
				if(IS.isMacOS){
          try
          {
            window.appCloseView(false);
          } catch (e) {
            //  alert('IOS的方法出错');
          }
          
        }else if(IS.isAndroid){
          try
          {
            window.yx129.appCloseView(false);
          } catch (e) {
            //   alert('Android的方法出错');
          }
          
        }else{
          go(appPath + 'easyhealth/index');
        }
			}
		</script>
</body>
</html>