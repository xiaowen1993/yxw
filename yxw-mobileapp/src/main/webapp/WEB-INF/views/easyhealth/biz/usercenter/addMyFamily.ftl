<!DOCTYPE html>
<html>
<head>
<#include "/easyhealth/common/common.ftl">
<title>添加家人</title>
</head>
<body>
<div id="body">
	<div class="familyInfo">
        <div class="box-tips icontips"><i class="iconfont"></i>温馨提示：身份证号码将作为您在平台上所有医院的统一就诊识别码。手机号码用于接收医院就诊通知，请准确填写。</div>
        <div class="space15"></div>
        <ul class="yx-list">
            <li class="flex line">
                <div class="">关系</div>
                <div class="flexItem color666 textRight">
                    <label><span class="text">父母</span>
                        <select name="ownership" class="select-screen-box select-relationship">
                            <option value="4">父母</option>
                            <option value="6">伴侣</option>
                            <option value="3">儿童</option>
                            <option value="5">兄弟姐妹</option>
                            <option value="2">其他</option>
                        </select>
                        <i class="iconfont">&#xe600;</i>
                    </label>
                </div>
            </li>
        </ul>
        <!--父母 str-->
        <div class="familyBox" style="display: block;">
            <ul class="yx-list">
                <li class="flex">
                    <div class="">姓名</div>
                    <div class="flexItem color666 textRight"><input type="text" class="yx-input patName" placeholder="请输入" id="parentName"></div>
                </li>
                <li class="flex">
                    <div class="">证件类型</div>
                    <div class="flexItem color666 textRight">
                        <label><span class="text">二代身份证</span>
                            <select name="" class="select-screen-box select-idType" id="parentIdType">
                                <option value="1">二代身份证</option>
                                <option value="2">港澳通行证</option>
                            </select>
                            <i class="iconfont">&#xe600;</i>
                        </label>
                    </div>
                </li>
                <li class="flex">
                    <div class="">证件号</div>
                    <div class="flexItem color666 textRight"><input type="text" class="yx-input patIdNo" placeholder="请输入" id="parentIdNo"></div>
                </li>
                <li class="flex birth" style="display: none;">
                    <div class="">出生日期</div>
                    <div class="flexItem color666 textRight"><input  type="text" class="yx-input js-date" placeholder="选择日期" id="parentBirth"></div>
                </li>
                <li class="flex sex_w" style="display: none;">
                    <div class="">性别</div>
                    <div class="flexItem color666 textRight">
                        <label><span class="text">男</span>
                            <select name="" class="select-screen-box select-sex" id="parentSex">
                                <option value="1">男</option>
                                <option value="2">女</option>
                            </select>
                            <i class="iconfont">&#xe600;</i>
                        </label>
                    </div>
                </li>
                <li class="flex">
                    <div class="">手机号</div>
                    <div class="flexItem color666 textRight"><input type="text" class="yx-input patMobile" placeholder="请输入" id="parentMobile" maxLength="11"></div>
                </li>
                <li class="flex" style="display: none;">
                    <div class="">验证码</div>
                    <div class="flexItem color666 textRight"><input id="parentCode" type="tel" class="yx-input inputCode" placeholder="请输入" value="1233123"/></div>
                    <div class="codeBtn" id="parentCodeBtn" onclick="sendVerifyCode(this);">点击获取验证码</div>
                </li>
                <li class="flex">
                    <div class="">地址</div>
                    <div class="flexItem color666 textRight"><input type="text" class="yx-input" placeholder="请输入" id="parentAddress"></div>
                </li>
            </ul>
            <div class="btn-w">
	            <div class="btn btn-ok btn-block" id="addParent">添加</div>
	        </div>
        </div>
        <!--父母 end-->
        <!--伴侣 str-->
        <div class="familyBox" style="display: none;">
            <ul class="yx-list">
	            <li class="flex">
	                <div class="">姓名</div>
	                <div class="flexItem color666 textRight"><input type="text" class="yx-input patName" placeholder="请输入" id="partnerName"></div>
	            </li>
	            <li class="flex">
	                <div class="">证件类型</div>
	                <div class="flexItem color666 textRight">
	                    <label><span class="text">二代身份证</span>
	                        <select name="" class="select-screen-box select-idType" id="partnerIdType">
	                            <option value="1">二代身份证</option>
	                            <option value="2">港澳通行证</option>
	                        </select>
	                        <i class="iconfont">&#xe600;</i>
	                    </label>
	                </div>
	            </li>
	            <li class="flex">
	                <div class="">证件号</div>
	                <div class="flexItem color666 textRight"><input type="text" class="yx-input patIdNo" placeholder="请输入" id="partnerIdNo"></div>
	            </li>
	            <li class="flex birth" style="display: none;">
	                <div class="">出生日期</div>
	                <div class="flexItem color666 textRight"><input  type="text" class="yx-input js-date" placeholder="选择日期" id="partnerBirth"></div>
	            </li>
	            <li class="flex sex_w" style="display: none;">
	                <div class="">性别</div>
	                <div class="flexItem color666 textRight">
	                    <label>
	                    	<span class="text">男</span>
	                        <select name="" class="select-screen-box select-sex" id="partnerSex">
	                            <option value="1">男</option>
	                            <option value="2">女</option>
	                        </select>
	                        <i class="iconfont">&#xe600;</i>
	                    </label>
	                </div>
	            </li>
	            <li class="flex">
	                <div class="">手机号</div>
	                <div class="flexItem color666 textRight"><input type="text" class="yx-input patMobile" placeholder="请输入" id="partnerMobile" maxLength="11"></div>
	            </li>
                <li class="flex" style="display: none;">
                    <div class="">验证码</div>
                    <div class="flexItem color666 textRight"><input type="tel" id="partnerCode" class="yx-input inputCode" placeholder="请输入" value="1233123"/></div>
                    <div class="codeBtn" id="partnerCodeBtn" onclick="sendVerifyCode(this);">点击获取验证码</div>
                </li>
	            <li class="flex">
	                <div class="">地址</div>
	                <div class="flexItem color666 textRight"><input type="text" class="yx-input" placeholder="请输入" id="partnerAddress"></div>
	            </li>
	        </ul>
        	<div class="btn-w">
	            <div class="btn btn-ok btn-block" id="addPartner">添加</div>
	        </div>
        </div>
        <!--伴侣 end-->
        <!--子女 str-->
        <div class="familyBox" style="display: none;">
            <ul class="yx-list">
                <li class="flex">
                    <div class="">姓名</div>
                    <div class="flexItem color666 textRight"><input type="text" class="yx-input patName" placeholder="请输入" id="childName"></div>
                </li>
                <li class="flex">
                    <div class="">出生日期</div>
                    <div class="flexItem color666 textRight"><input  type="text" class="yx-input js-date" placeholder="选择日期" id="childBirth"></div>
                </li>
                <li class="flex">
                    <div class="">性别</div>
                    <div class="flexItem color666 textRight">
                        <label><span class="text">男</span>
                            <select name="" class="select-screen-box select-sex" id="childSex">
                                <option value="1">男</option>
                                <option value="2">女</option>
                            </select>
                            <i class="iconfont">&#xe600;</i>
                        </label>
                    </div>
                </li>
                <li class="flex">
                    <div class="">地址</div>
                    <div class="flexItem color666 textRight"><input type="text" class="yx-input" placeholder="请输入" id="childAddress"></div>
                </li>
            </ul>
            <div class="space15"></div>
            <ul class="yx-list">
                <li class="flex">
                    <div class="">监护人姓名</div>
                    <div class="flexItem color666 textRight"><input type="text" class="yx-input patName" placeholder="请输入" id="guardName"></div>
                </li>
                <li class="flex">
                    <div class="">证件类型</div>
                    <div class="flexItem color666 textRight">
                        <label><span class="text">二代身份证</span>
                            <select name="" class="select-screen-box select-idType" id="guardIdType">
                                <option value="1">二代身份证</option>
                                <option value="2">港澳通行证</option>
                            </select>
                            <i class="iconfont">&#xe600;</i>
                        </label>
                    </div>
                </li>
                <li class="flex">
                    <div class="">监护人证件号</div>
                    <div class="flexItem color666 textRight"><input type="text" class="yx-input patIdNo" placeholder="请输入" id="guardIdNo"></div>
                </li>
                <li class="flex">
                    <div class="">监护人手机</div>
                    <div class="flexItem color666 textRight"><input type="text" class="yx-input patMobile" placeholder="请输入" id="guardMobile"  maxLength="11"></div>
                </li>
                <li class="flex" style="display: none;">
                    <div class="">验证码</div>
                    <div class="flexItem color666 textRight"><input type="tel" id="childCode" class="yx-input inputCode" placeholder="请输入" value="1233123"/></div>
                    <div class="codeBtn" id="childCodeBtn" onclick="sendVerifyCode(this);">点击获取验证码</div>
                </li>
            </ul>
            
            <div class="btn-w">
	            <div class="btn btn-ok btn-block" id="addChild">添加</div>
	        </div>
        </div>
        <!--子女 end-->
        <!--兄弟姐妹 str-->
        <div class="familyBox" style="display: none;">
            <ul class="yx-list">
	            <li class="flex">
	                <div class="">姓名</div>
	                <div class="flexItem color666 textRight"><input type="text" class="yx-input patName" placeholder="请输入" id="siblingName"></div>
	            </li>
	            <li class="flex">
	                <div class="">证件类型</div>
	                <div class="flexItem color666 textRight">
	                    <label><span class="text">二代身份证</span>
	                        <select name="" class="select-screen-box select-idType" id="siblingIdType">
	                            <option value="1">二代身份证</option>
	                            <option value="2">港澳通行证</option>
	                        </select>
	                        <i class="iconfont">&#xe600;</i>
	                    </label>
	                </div>
	            </li>
	            <li class="flex">
	                <div class="">证件号</div>
	                <div class="flexItem color666 textRight"><input type="text" class="yx-input patIdNo" placeholder="请输入" id="siblingIdNo"></div>
	            </li>
	            <li class="flex birth" style="display: none;">
	                <div class="">出生日期</div>
	                <div class="flexItem color666 textRight"><input  type="text" class="yx-input js-date" placeholder="选择日期" id="siblingBirth"></div>
	            </li>
	            <li class="flex sex_w" style="display: none;">
	                <div class="">性别</div>
	                <div class="flexItem color666 textRight">
	                    <label><span class="text">男</span>
	                        <select name="" class="select-screen-box select-sex" id="siblingSex">
	                            <option value="1">男</option>
	                            <option value="2">女</option>
	                        </select>
	                        <i class="iconfont">&#xe600;</i>
	                    </label>
	                </div>
	            </li>
	            <li class="flex">
	                <div class="">手机号</div>
	                <div class="flexItem color666 textRight"><input type="text" class="yx-input patMobile" placeholder="请输入" id="siblingMobile"  maxLength="11"></div>
	            </li>
                <li class="flex" style="display: none;">
                    <div class="">验证码</div>
                    <div class="flexItem color666 textRight"><input type="tel" id="siblingCode" class="yx-input inputCode" placeholder="请输入" value="1233123"/></div>
                    <div class="codeBtn" id="siblingCodeBtn" onclick="sendVerifyCode(this);">点击获取验证码</div>
                </li>
	            <li class="flex">
	                <div class="">地址</div>
	                <div class="flexItem color666 textRight"><input type="text" class="yx-input" placeholder="请输入" id="siblingAddress"></div>
	            </li>
	        </ul>
        	<div class="btn-w">
	            <div class="btn btn-ok btn-block" id="addSibling">添加</div>
	        </div>
        </div>
        <!--兄弟姐妹 end-->
        <!--他人 str-->
        <div class="familyBox" style="display: none;">
            <ul class="yx-list">
	            <li class="flex">
	                <div class="">姓名</div>
	                <div class="flexItem color666 textRight"><input type="text" class="yx-input patName" placeholder="请输入" id="otherName"></div>
	            </li>
	            <li class="flex">
	                <div class="">证件类型</div>
	                <div class="flexItem color666 textRight">
	                    <label><span class="text">二代身份证</span>
	                        <select name="" class="select-screen-box select-idType" id="otherIdType">
	                            <option value="1">二代身份证</option>
	                            <option value="2">港澳通行证</option>
	                        </select>
	                        <i class="iconfont">&#xe600;</i>
	                    </label>
	                </div>
	            </li>
	            <li class="flex">
	                <div class="">证件号</div>
	                <div class="flexItem color666 textRight"><input type="text" class="yx-input patIdNo" placeholder="请输入" id="otherIdNo"></div>
	            </li>
	            <li class="flex birth" style="display: none;">
	                <div class="">出生日期</div>
	                <div class="flexItem color666 textRight"><input type="text" class="yx-input js-date" placeholder="选择日期" id="otherBirth"></div>
	            </li>
	            <li class="flex sex_w" style="display: none;">
	                <div class="">性别</div>
	                <div class="flexItem color666 textRight">
	                    <label><span class="text">男</span>
	                        <select name="" class="select-screen-box select-sex" id="otherSex">
	                            <option value="1">男</option>
	                            <option value="2">女</option>
	                        </select>
	                        <i class="iconfont">&#xe600;</i>
	                    </label>
	                </div>
	            </li>
	            <li class="flex">
	                <div class="">手机号</div>
	                <div class="flexItem color666 textRight"><input type="text" class="yx-input patMobile" placeholder="请输入" id="otherMobile"  maxLength="11"></div>
	            </li>
                <li class="flex" style="display: none;">
                    <div class="">验证码</div>
                    <div class="flexItem color666 textRight"><input type="tel" id="otherCode" class="yx-input inputCode" placeholder="请输入" value="1233123"/></div>
                    <div class="codeBtn" id="otherCodeBtn" onclick="sendVerifyCode(this);">点击获取验证码</div>
                </li>
	            <li class="flex">
	                <div class="">地址</div>
	                <div class="flexItem color666 textRight"><input type="text" class="yx-input" placeholder="请输入" id="otherAddress"></div>
	            </li>
	        </ul>
        
	        <div class="btn-w">
	            <div class="btn btn-ok btn-block" id="addOther">添加</div>
	        </div>
        </div>
        <!--他人 end-->
    </div>
    
</div>

<!-- 伴侣的信息 -->
<input type="hidden" name="rPartnerName" id="rPartnerName" value="" class="user_input"/>
<input type="hidden" name="rPartnerIdNo" id="rPartnerIdNo" value="" class="user_input"/>
<input type="hidden" name="rPartnerMobile" id="rPartnerMobile" value="" class="user_input"/>
<!-- end 伴侣的信息 -->

<!-- 他人的信息 -->
<input type="hidden" name="rOtherName" id="rOtherName" value="" class="user_input"/>
<input type="hidden" name="rOtherIdNo" id="rOtherIdNo" value="" class="user_input"/>
<input type="hidden" name="rOtherMobile" id="rOtherMobile" value="" class="user_input"/>
<!-- end 他人的信息 -->

<!-- 父母的信息 -->
<input type="hidden" name="rParentName" id="rParentName" value="" class="user_input"/>
<input type="hidden" name="rParentIdNo" id="rParentIdNo" value="" class="user_input"/>
<input type="hidden" name="rParentMobile" id="rParentMobile" value="" class="user_input"/>
<!-- end 父母的信息 -->

<!-- 兄弟姐妹的信息 -->
<input type="hidden" name="rSiblingName" id="rSiblingName" value="" class="user_input"/>
<input type="hidden" name="rSiblingIdNo" id="rSiblingIdNo" value="" class="user_input"/>
<input type="hidden" name="rSiblingMobile" id="rSiblingMobile" value="" class="user_input"/>
<!-- end 兄弟姐妹的信息 -->

<!-- 儿童的信息 -->
<input type="hidden" name="rChildName" id="rChildName" value="" class="user_input"/>
<input type="hidden" name="rChildIdNo" id="rChildIdNo" value="" class="user_input"/>
<input type="hidden" name="rGuardName" id="rGuardName" value="" class="user_input"/>
<input type="hidden" name="rGuardIdNo" id="rGuardIdNo" value="" class="user_input"/>
<input type="hidden" name="rGuardMobile" id="rGuardMobile" value="" class="user_input"/>

<form id="voForm" method="post" action="">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}">
	<input type="hidden" id="appCode" name="appCode" value="${appCode}">
	<input type="hidden" id="appId" name="appId" value="${commonParams.appId}">
	<input type="hidden" id="areaCode" name="areaCode" value="${areaCode}">
	<input type="hidden" id="forward" name="forward" value="${commonParams.forward}">
	<input type="hidden" id="syncType" name="syncType" value="${commonParams.syncType}">
</form>

<#include "/easyhealth/common/footer.ftl">
<script type="text/javascript" src="${basePath}yxw.app/js/biz/usercenter/idCardUtils.js"></script>
</body>
</html>

<script type="text/javascript" src="${basePath}yxw.app/js/common/yxCalendar.js?v=7"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/usercenter/app.addFamily.js?v=1.0"></script>