<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>产科床位</title>
</head>
<body>

		<div id="body">
				<div class="box-tips icontips"><i class="iconfont">&#xe60d;</i>温馨提示：数据仅供参考，以医院实际床位数为准。</div>
		
		    <div id="bedList">
		        <ul class="yx-bedList">
		        	
		        		<#if obstetricsBeds?exists>
	      					<#list obstetricsBeds as bed>
	      						<#if (bed.kcs > 10)>
				            <li>
				            <#elseif (bed.kcs <= 10 && bed.kcs > 0)>
				            <li class="orange">
									  <#else>
									  <li class="grey">
				            </#if>
				                <div class="states">
				                    <span class="num">${bed.kcs}</span>
				                    <span class="text">剩余床位</span>
				                </div>
				                <div class="info">
				                    <div class="title fontSize110">${bed.hospitalName}</div>
				                    <div class="des">产科床位数：${bed.sjcw}个</div>
				                    <div class="des">现有产妇数：<span class="green">${bed.zyrs}</span>人</div>
				                </div>
				            </li>
				          </#list>
									<#else>
										<!-- 无数据HTML -->
								</#if>
		            <!-- <li class="arrow orange">
		            </li>
		            <li class="arrow grey">
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
			
		function doRefresh() {
				//$Y.tips("功能正在建设中...", 86400000);
				//$Y.tips("数据加载中...", 15000);
				$Y.loading.show("数据加载中...");
				$.ajax(loadBed);
		}
		
			var loadBed = {
		      type : 'POST',  
		      url  : appPath + "easyhealth/bed/loadObstetricsBed",  
		      data : {},  
		      dataType : 'json',
		      timeout  : 120000
		  };
			
			loadBed.success = function(data) {
          if(data.status == 'OK'){
							var list = $(".yx-bedList");
							list.html("");
							
							var obstetricsBeds = data.obstetricsBeds;
							
							for (var i = 0; i < obstetricsBeds.length; i++) {
								var bed = obstetricsBeds[i];
								var html = [];
										
								if (bed.kcs > 10) {
									html.push('<li>');
								} else if (bed.kcs <= 10 && bed.kcs > 0) {
									html.push('<li class="orange">');
								} else {
									html.push('<li class="grey">');
								}
								
								html.push('<div class="states">');
									html.push('<span class="num">' + bed.kcs + '</span>');
									html.push('<span class="text">剩余床位</span>');
								html.push('</div>');
								html.push('<div class="info">');
									html.push('<div class="title fontSize110">' + bed.hospitalName + '</div>');
									html.push('<div class="des">产科床位数：' + bed.sjcw + '个</div>');
                  html.push('<div class="des">现有产妇数：<span class="green">' + bed.zyrs + '</span>人</div>');
                html.push('</div>');
		            html.push('</li>');
		            
		            list.append(html.join(""));
							}
          }else{
              myBox = new $Y.confirm({
                  title:"",
                  content:"<div style='text-align: center;'>" + data.message + "</div>",
                  ok:{title:"确定",
                      click:function(){
                          myBox.close();
                      }
                  }
              });
          }
          
          $Y.loading.hide();
       }
			
			loadBed.error = function(data) {
					$Y.loading.hide();
          myBox = new $Y.confirm({
              title:"",
              content:"<div style='text-align: center;'>网络异常,请保持您的网络通畅,稍后再试.</div>",
              ok:{title:"确定",
                  click:function(){
                      myBox.close();
                  }
              }
          });
       }
		
			$(function() {
				doRefresh();
			});
		</script>
</body>
</html>