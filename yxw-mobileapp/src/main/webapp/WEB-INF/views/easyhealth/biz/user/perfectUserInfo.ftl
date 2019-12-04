<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>完善资料</title>
</head>
<body  style="background-color: #eeeeee;">
<div id="body">
	<form id="paramsForm" method="post" action="" accept-charset="utf-8">
		<input type="hidden" id="forward" name="forward" value="${forward}">
		<input type="hidden" id="openId" name="openId" value="${sessionUser.id}">
		
		<div class="familyInfo">
        <div class="box-tips icontips"><i class="iconfont">&#xe60d;</i>为了方便使用医院服务，请先完善资料。姓名、证件号一旦填写无法修改，请确认您的信息真实无误。</div>
        <div class="space15"></div>
        <ul class="yx-list">
            <li class="flex">
                <div class="flexItem"><input type="text" name="edtName" id="edtName"  class="yx-input textLeft" placeholder="姓名"/></div>
                <input type="hidden" name="name" id="name" />
            </li>
            <li class="flex">
                <div class="flexItem"><input type="text" name="edtCardNo" id="edtCardNo" class="yx-input textLeft" placeholder="证件"/></div>
                <input type="hidden" name="cardNo" id="cardNo" />
                <div id="yx-select-card-id" >
                    <span class="view"><i class="iconfont">&#xe600;</i></span>
                    <select name="cardType" id="yx-select-card-id-val">
                        <option value="1" selected>中国大陆身份证</option>
                        <option value="2">港澳居民身份证</option>
                        <option value="3">台湾居民身份证</option>
                    </select>
                </div>

            </li>
            
            <#if !sessionUser.mobile?exists && sessionUser.mobile == "">
            <li class="flex">
                <div class="flexItem"><input type="text" name="edtMobile" id="edtMobile"  class="yx-input textLeft" placeholder="手机号码"/></div>
                <input type="hidden" name="mobile" id="mobile" />
            </li>
            </#if>
            
          	<li class="flex" needHideIndex="0" style="display: none;">
                <div class="">出生日期</div>
                <div class="flexItem color666 textRight">
                    <input class="yx-input nb js-date" name="birthDay" id="birthDay" type="text" placeholder="请选择" />
                </div>
            </li>

            <li class="flex" needHideIndex="0" style="display: none;">
                <div class="flexItem">性别</div>
                <div class="sex-bar textRight">
                    <label><span>男</span><input type="radio" value="1" name="sex"/></label>
                    <label><span>女</span><input type="radio" value="2" name="sex"/></label>
                </div>
            </li>
            
            <#-- <li class="flex">
                <div class="">地址</div>
                <input type="text" name="address" id="address" class="yx-input textRight flexItem" placeholder="请输入"/>
            </li> -->
            
            <li class="flex">
                <div class="">省份</div>
                <div class="flexItem color666 textRight">
                    <label><span class="text">广东省</span>
                        <select id="province" name="province" class="select-screen-box">
                        </select>
                        <i class="iconfont">&#xe600;</i>
                    </label>
                </div>
            </li>
            <li class="flex">
                <div class="">城市</div>
                <div class="flexItem color666 textRight">
                    <label><span class="text"></span>
                        <select id="city" name="city" class="select-screen-box">
                        </select>
                        <i class="iconfont">&#xe600;</i>
                    </label>
                </div>
            </li>
            <li class="flex">
                <div class="">区域</div>
                <div class="flexItem color666 textRight">
                    <label><span class="text"></span>
                        <select id="area" name="area" class="select-screen-box">
                        </select>
                        <i class="iconfont">&#xe600;</i>
                    </label>
                </div>
            </li>
            
        </ul>
        <div class="btn-w">
            <div class="btn btn-ok btn-block" id="btnPerfect">完善资料</div>
        </div>
        <!-- <div class="textCenter"><a href="#" class="skinColor unline">跳过</a></div> -->
    </div>
	</form>
</div>

<script type="text/javascript" src="${basePath}yxw.app/js/biz/usercenter/idCardUtils.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/common/yxCalendar.js?v=7"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/common/city.min.js"></script>
<script src="${basePath}yxw.app/js/biz/user/app.perfect.js?v=2.2 type="text/javascript"></script>
</body>
</html>
