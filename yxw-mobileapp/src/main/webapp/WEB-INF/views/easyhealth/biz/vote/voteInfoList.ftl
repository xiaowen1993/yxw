<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>就诊评价</title>
</head>
<body>
<div id="body">
    <div >
        <div class="orderH" >
        <div class="box-list fff">
            <div class="js-tag flex">
                <div class="tag flexItem active">待评价</div>
                <div class="tag flexItem">已评价</div>
            </div>
            <!--tag1-->
            <div class="js-tagContent tagContent show">
                <#if (noVoteList?size > 0)>
                <ul class="yx-list userList">
                     <#list noVoteList as entity>
                     <li class="pic arrow" onclick="goVote('${entity.id}' ,'${entity.openId}' , '${entity.orderNo}' , '${entity.hospitalId}')">
                        <div class="item-right">
                            <span class="tag">去评价</span>
                        </div>
                        <div class="info">
                            <div class="title">${entity.voteTitle}:${entity.patientName}(${entity.cardNo})</div>
                            <div class="mate"><font size="1">${entity.createTimeStr}</font></div>
                            <div class="mate">${entity.hospitalName}</div>
                        </div>
                     </li>
                     </#list>
                </ul>
                <#else>
                <div class="jiuzhen-empty">
                                                    没有待评价的就诊信息
                </div>
                </#if>
            </div>
            <!--tag1-->

            <!--tag2-->
            <div class="js-tagContent tagContent">
                <#if (hadVoteList?size > 0)>
                <ul class="yx-list userList">
                    <#list hadVoteList as entity>
                    <li class="pic arrow" onclick="toView('${entity.id}' ,'${entity.openId}' , '${entity.orderNo}', '${entity.hospitalId}')">
                        <div class="item-right">
                            <span class="tag ">查看</span>
                        </div>
                        <div class="info">
                            <div class="title">${entity.voteTitle}:${entity.patientName}(${entity.cardNo})</div>
                            <div class="mate"><font size="1">${entity.createTimeStr}</font></div>
                            <div class="mate">${entity.hospitalName}</div>
                        </div>
                    </li>
                    </#list>
                </ul>
                <#else>
                <div class="jiuzhen-empty">
                                            没有进行过评价
                </div>
                </#if>
            </div>
        </div>
    </div>
    </div>
</div>
</div>
<#include "/easyhealth/common/yxw_footer.ftl">
</body>
</html>
<script src="${basePath}yxw.app/js/common/nav-list.js" type="text/javascript"></script>
<script type="text/javascript">
function goVote(id ,openId ,orderNo ,hospitalId ){
	var url = appPath + "easyhealth/vote/toEdit?openId=" + openId + "&id=" + id + "&orderNo=" + orderNo + "&hospitalId=" + hospitalId;
	<#-- var appCode = '${appCode}';
	if (appCode == 'easyHealth') {
		go(url, true);
	} else {
    	window.location = url;
    } -->
    window.location = url;
}

function toView(id ,openId ,orderNo ,hospitalId){
	var url = appPath + "easyhealth/vote/toView?openId=" + openId + "&id=" + id + "&orderNo=" + orderNo + "&hospitalId=" + hospitalId;
	<#-- var appCode = '${appCode}';
    if (appCode == 'easyHealth') {
		go(url, true);
	} else {
    	window.location = url;
    } -->
    window.location = url;
}
</script>