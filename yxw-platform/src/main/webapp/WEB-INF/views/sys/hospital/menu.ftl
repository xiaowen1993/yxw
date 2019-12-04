<html>
<head>
		<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/json_utils.js"></script>
    <script type="text/javascript" src="${basePath}js/sys/hospital/sys.hospital.js"></script>
    <title>菜单管理</title>
</head>
<body>
	<#include "./sys/common/hospital_setting.ftl">
	<div id="content-header">
    	<div class="widget-title"><h3 class="title">菜单配置</h3></div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="row-fluid">
                <div class="space10"></div>
                <div class="myStep s6">
                    <div class="myStepClick">
                        <a class="aStepClick a-s1" href="${basePath}sys/hospital/toEdit?id=${hospitalId}"></a>
                        <a class="aStepClick a-s2" href="${basePath}sys/branchHospital/toView?hospitalId=${hospitalId}"></a>
                        <a class="aStepClick a-s3" href="${basePath}sys/platformSettings/toView?hospitalId=${hospitalId}"></a>
                        <a class="aStepClick a-s4" href="${basePath}sys/paySettings/toView?hospitalId=${hospitalId}"></a>
                        <a class="aStepClick a-s5" href="${basePath}sys/optional/toView?hospitalId=${hospitalId}"></a>
                        <a class="aStepClick a-s6" href="${basePath}sys/customerMenu/toView?hospitalId=${hospitalId}"></a>
                    </div>
                </div>

            </div>
            <div class="widget-box">
                <div class="h22"></div>
                <div class="widget-content">
                    <div class="row-fluid">
                        <!--内容-->
                        <div class="my_select_w page_select">
                            <form class="form-inline">
                                <div class="my_select_title">应用平台</div>
                                <div class="my_select">
                                	<input type="hidden" id="hospitalId" value="${hospitalId}" />
                                    <select id="platforms" onchange="getMeun(this)">
                            			<option value="${platformsMap['wechat'].id}" code="${platformsMap['wechat'].code}" <#if platformVo.id == platformsMap['wechat'].id>selected="selected"</#if>>${platformsMap['wechat'].name}</option>
                        				<option value="${platformsMap['alipay'].id}" code="${platformsMap['alipay'].code}" <#if platformVo.id == platformsMap['alipay'].id>selected="selected"</#if>>${platformsMap['alipay'].name}</option>
                        				<option value="${platformsMap['app'].id}" code="${platformsMap['app'].code}" <#if platformVo.id == platformsMap['app'].id>selected="selected"</#if>>${platformsMap['app'].name}</option>
                            			<!--
                            			<option value="${platformsMap['appWechat'].id}" code="${platformsMap['appWechat'].code}" <#if platformVo.id == platformsMap['appWechat'].id>selected="selected"</#if>>${platformsMap['appWechat'].name}</option>
                            			<option value="${platformsMap['appAlipay'].id}" code="${platformsMap['appAlipay'].code}" <#if platformVo.id == platformsMap['appAlipay'].id>selected="selected"</#if>>${platformsMap['appAlipay'].name}</option>
                                   		-->
                                    </select>
                                </div>
                            </form>
                        </div>

                        <div id="menu_box" class="totalList">
                            <div class="action-row bottom clearfix">
                                <div class="row-li" style="width:10%;">排序号</div>
                                <div class="row-li" style="width:25%;">菜单名称</div>
                                <div class="row-li" style="width:25%;">菜单级别</div>
                                <div class="row-li" style="width:25%;">功能设置</div>
                                <div class="row-li addfirstMenu" style="width:15%;"><a href="javascript:void(0);" onclick="addfirstMenu();"><span class="green"><i class="icons-add"></i>添加一级菜单</span></a></div>
                            </div>
                            <!--包含 一 二级菜单 str-->
                       		<#if resultMenu?size>
                       			<#list resultMenu as menu>
                           			<div class="action_box clearfix availability">
                           				<div class="action-row clearfix">
                           					<input type="hidden" name="id" value="${menu.id}" />
                           					<div class="row-li" style="width:10%;"><div class="sidepadding"><span class="label_id">${menu_index + 1}</span></div></div>
                           					<div class="row-li" style="width:25%;"><div class="sidepadding"><input type="text" class="span12" name="name" value="${menu.name}"></div></div>
                           					<#if platformVo.code == 'wechat' || platformVo.code == 'alipay'>
								           	 	<div class="row-li" style="width:25%;"><div class="sidepadding"><div class="text-default">一级菜单</div></div></div>
								           	<#else>
								           		<div class="row-li" style="width:25%;"><div class="sidepadding"><div class="text-default">普通功能</div></div></div>
								           	</#if>
                           					
                           					<div class="row-li" style="width:25%;">
                           						<div class="sidepadding">
	                           						<div class="my_select">
	                           							<select name="menuOptionalSelect" class="span12" onchange="changeSet(this)" flagTime="${menu.id}" >
	                           								<option value="">请选择</option>
	                           								<#if platformVo.code == 'wechat' || platformVo.code == 'alipay'>
	                   											<#if menu.meunType == 1>
	                   												<option value="" menutype="1" selected="selected">外部链接</option>
	                   											<#else>
	                   												<option value="" menutype="1">外部链接</option>
	                   											</#if>
	                   											<#if menu.meunType == 2>
	                   												<option value="" menutype="2" selected="selected">图文素材</option>
	                   											<#else>
	                   												<option value="" menutype="2">图文素材</option>
	                   											</#if>
	                   										</#if>
	                           								<#if selHospitalOptionalsList?size>
	                           									<#list selHospitalOptionalsList as selHospitalOptionals>
	                           										<#if platformVo.code == 'wechat' || platformVo.code == 'alipay'>
		                           										<#if menu.optionalId == selHospitalOptionals.optional.id>
		                           											<option value="${selHospitalOptionals.optional.id}" bizCode="${selHospitalOptionals.optional.bizCode}" selected="selected">${selHospitalOptionals.optional.name}</option>
		                           										<#else>
		                           											<option value="${selHospitalOptionals.optional.id}" bizCode="${selHospitalOptionals.optional.bizCode}">${selHospitalOptionals.optional.name}</option>
		                           										</#if>
		                           									<#else>
		                           										<#if selHospitalOptionals.optional.isApp == 1>
			                           										<#if menu.optionalId == selHospitalOptionals.optional.id>
			                           											<option value="${selHospitalOptionals.optional.id}" bizCode="${selHospitalOptionals.optional.bizCode}" selected="selected">${selHospitalOptionals.optional.name}</option>
			                           										<#else>
			                           											<option value="${selHospitalOptionals.optional.id}" bizCode="${selHospitalOptionals.optional.bizCode}">${selHospitalOptionals.optional.name}</option>
			                           										</#if>
		                           										</#if>
	                           										</#if>
            													</#list>
            												</#if>
	                           							</select>
	                           							<input type="hidden" name="level" value="level1" />
	                           							<input type="hidden" name="oldOptionalId" value="${menu.optionalId}" />
	                           							<input type="hidden" name="oldMenutype" value="${menu.meunType}" />
	                           						</div>
	                           					</div>
	                           				</div>
                           					<div class="row-li addfirstMenu textleft" style="width:15%;">
                           						<div class="leftpadding">
                           							<#if platformVo.id == platformsMap['wechat'].id || platformVo.id == platformsMap['alipay'].id>
	                           							<#if menu.meunType == 1 || menu.meunType == 2 >
														 	<button type="button" class="btn btn-tool" name="levelAdd" disabled="disabled" onclick="addSecondMenu(this);"><i class="icons-plus"></i></button>
														<#else>
															<button type="button" class="btn btn-tool" name="levelAdd" onclick="addSecondMenu(this);"><i class="icons-plus"></i></button>
														</#if>
													</#if>
                           							<button type="button" class="btn btn-tool" onclick="delFirstMenu(this);"><i class="icons-trash"></i></button>
                           						</div>
                           					</div>
                           				</div>
                           				<!-- 一级菜单块外部链接及图文素材块 start -->
                           				<div class="action-second-content-parent clearfix">
                           					<#if menu.meunType == 1>
                           						<div class="action-second clearfix">
                   									<div class="row-li" style="width:10%;"></div>
                   									<div class="row-li textright" style="width: 75%">
                   										<div class="sidepadding">
                   											<span class="tag-line"></span>
                   											<input type="text" class="span11 link" readonly="readonly" name="content" value="${menu.content}">
                   										</div>
                   									</div>    
                   									<div class="row-li addfirstMenu textleft" style="width:15%;">
                   										<div class="leftpadding">
                   											<button type="button" class="btn btn-tool" onclick="editLinkMenu(this);"><i class="icons-edit"></i></button>
                   											<button type="button" class="btn btn-tool" onclick="delLinkMenu(this);"><i class="icons-trash"></i></button>
                   										</div>
                   									</div>
                   								</div>
                           					<#elseif menu.meunType == 2>
                           						<#if (menu.mixedMeterial.subMixedMeterialList?size > 0)>
                       								<div class="action-second clearfix">
				                                    	<div class="row-li" style="width:10%;"></div>
					                                    <div class="row-li textright" style="width: 75%">
					                                    	<div class="sidepadding">
					                                    	<span class="tag-line"></span>
					                                        <div class="t-msg">
					                                            <div class="inner">
					                                                <div class="multi-thumb">
					                                                    <h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">${menu.mixedMeterial.title}</a></h4>
					                                                    <input type="hidden" name="grapicsId" value="${menu.mixedMeterial.id}">
					                                                    <div class="msg-thumb-wrap">
					                                                        <img class="msg-thumb" style="display:block" src="${menu.mixedMeterial.coverPicPath}">
					                                                        <i class="msg-thumb-size default" style="display:none"></i>
					                                                    </div>
					                                                    <div class="msg-edit-mask"><a class="icons_edit edit-white" href="javascript:void(0)"></a></div>
					                                                </div>
					                                            </div>
                   												<#list menu.mixedMeterial.subMixedMeterialList as subMixedMeterial>
						                                            <div class="inner msg-item">
						                                                <img class="msg-thumb" style="display:block" src="${subMixedMeterial.coverPicPath}">
						                                                <i class="msg-thumb-size default" style="display:none"></i>
						                                                <h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">${subMixedMeterial.title}</a></h4>
						                                                <div class="msg-edit-mask">
						                                                    <a class="icons_edit edit-white" href="javascript:void(0)"></a>
						                                                    <div class="spaceW9"></div>
						                                                    <a class="icons_edit del-white" href="javascript:void(0)"></a>
						                                                </div>
						                                            </div>
						                                		</#list>
					                                        </div>
					                                    </div>
					                                    </div>
					                                    <div class="row-li addfirstMenu textleft" style="width:15%;">
					                                        <div class="leftpadding">
					                                            <button type="button" class="btn btn-tool" onclick="editMultiTextMenu(this,'${menu.mixedMeterial.id}','${menu.id}');"><i class="icons-edit"></i></button>
					                                            <button type="button" class="btn btn-tool" onclick="delMultiTextMenu(this);"><i class="icons-trash"></i></button>
					                                        </div>
					                                    </div>
					                                </div>
	                   							<#else>
	                   								<div class="action-second clearfix">
														<div class="row-li" style="width:10%;"></div>
														<div class="row-li textright" style="width: 75%">
															<div class="sidepadding">
																<span class="tag-line"></span>
																<div class="t-msg">
																	<div class="inner">
																		<div class="as-thumb">
																			<h4 class="msg-title js_msgTitle">${menu.mixedMeterial.title}</h4>
																			<input type="hidden" name="grapicsId" value="${menu.mixedMeterial.id}" />
																			<div class="msg-info">2015-5-20 17:08:29</div>
																			<div class="msg-thumb-wrap">
																				<img class="msg-thumb" style="display: block;" src="${menu.mixedMeterial.coverPicPath}" />
																				<i class="msg-thumb-size default" style="display:none"></i>
																			</div>
																			<div class="msg-desc js_msgDesc">${menu.mixedMeterial.description}</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
														<div class="row-li addfirstMenu textleft" style="width:15%;">
															<div class="leftpadding">
																<button type="button" class="btn btn-tool" onclick="editSingleTextMenu(this,'${menu.mixedMeterial.id}','${menu.id}');">
																	<i class="icons-edit"></i>
																</button>
																<button type="button" class="btn btn-tool" onclick="delSingleTextMenu(this);">
																	<i class="icons-trash"></i>
																</button>
															</div>
														</div>
													</div>
	                   							</#if>
                           					</#if>
                           				</div>
                           				<!-- 一级菜单块外部链接及图文素材块 end -->
                           				<!-- 二级菜单块 start -->
                           				<div class="action-second-box clearfix">
	                           				<#if (menu.childMenus?size > 0)>
		                           				<#list menu.childMenus as childMenus>
			                           				<div class="action-second-outer clearfix">
		                           						<div class="action-second clearfix">
		                           							<input type="hidden" name="id" value="${childMenus.id}" />
		                           							<div class="row-li" style="width:10%;"></div>
		                           							<div class="row-li" style="width:25%;"><div class="sidepadding"><input type="text" class="span12" name="name" value="${childMenus.name}"></div></div>
		                           							<div class="row-li" style="width:25%;"><div class="sidepadding"><div class="text-default">二级菜单</div></div></div>
		                           							<div class="row-li" style="width:25%;">
		                           								<div class="sidepadding">
		                           									<div class="my_select">
		                           										<select name="menuOptionalSelect" class="span12" onchange="changeSet(this)" flagTime="${childMenus.id}">
		                           											<option value="">请选择</option>
		                           											<#if childMenus.meunType == 1>
		                           												<option value="" menutype="1" selected="selected">外部链接</option>
		                           											<#else>
		                           												<option value="" menutype="1">外部链接</option>
		                           											</#if>
		                           											<#if childMenus.meunType == 2>
		                           												<option value="" menutype="2" selected="selected">图文素材</option>
		                           											<#else>
		                           												<option value="" menutype="2">图文素材</option>
		                           											</#if>
		                           											<#if selHospitalOptionalsList?size>
					                           									<#list selHospitalOptionalsList as selHospitalOptionals>
					                           										<#if childMenus.optionalId == selHospitalOptionals.optional.id>
					                           											<option value="${selHospitalOptionals.optional.id}" bizCode="${selHospitalOptionals.optional.bizCode}" selected="selected">${selHospitalOptionals.optional.name}</option>
					                           										<#else>
					                           											<option value="${selHospitalOptionals.optional.id}" bizCode="${selHospitalOptionals.optional.bizCode}">${selHospitalOptionals.optional.name}</option>
					                           										</#if>
			                													</#list>
			                												</#if>
		                           										</select>
		                           										<input type="hidden" name="level" value="level2" />
		                           										<input type="hidden" name="oldOptionalId" value="${childMenus.optionalId}" />
		                           										<input type="hidden" name="oldMenutype" value="${childMenus.meunType}" />
		                           									</div>
		                           								</div>
		                           							</div>
		                           							<div class="row-li addfirstMenu textleft" style="width:15%;">
		                           								<div class="leftpadding"><button type="button" class="btn btn-tool" onclick="delSecondMenu(this);;"><i class="icons-trash"></i></button></div>
		                           							</div>
		                           						</div>
		                           						<div class="action-second-content clearfix">
		                           							<#if childMenus.meunType == 1>
		                           								<div class="action-second clearfix">
		                           									<div class="row-li" style="width:10%;"></div>
		                           									<div class="row-li textright" style="width: 75%">
		                           										<div class="sidepadding">
		                           											<span class="tag-line"></span>
		                           											<input type="text" class="span11 link" readonly="readonly" name="content" value="${childMenus.content}">
		                           										</div>
		                           									</div>    
		                           									<div class="row-li addfirstMenu textleft" style="width:15%;">
		                           										<div class="leftpadding">
		                           											<button type="button" class="btn btn-tool" onclick="editLinkMenu(this);"><i class="icons-edit"></i></button>
		                           											<button type="button" class="btn btn-tool" onclick="delLinkMenu(this);"><i class="icons-trash"></i></button>
		                           										</div>
		                           									</div>
		                           								</div>
		                           							<#elseif childMenus.meunType == 2>
		                           								<#if (childMenus.mixedMeterial.subMixedMeterialList??)>
			                           								<div class="action-second clearfix">
								                                    	<div class="row-li" style="width:10%;"></div>
									                                    <div class="row-li textright" style="width: 75%">
									                                    	<div class="sidepadding">
									                                    	<span class="tag-line"></span>
									                                        <div class="t-msg">
									                                            <div class="inner">
									                                                <div class="multi-thumb">
									                                                    <h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">${childMenus.mixedMeterial.title}</a></h4>
									                                                    <input type="hidden" name="grapicsId" value="${childMenus.mixedMeterial.id}">
									                                                    <div class="msg-thumb-wrap">
									                                                        <img class="msg-thumb" style="display:block" src="${childMenus.mixedMeterial.coverPicPath}">
									                                                        <i class="msg-thumb-size default" style="display:none"></i>
									                                                    </div>
									                                                    <div class="msg-edit-mask"><a class="icons_edit edit-white" href="javascript:void(0)"></a></div>
									                                                </div>
									                                            </div>
		                           												<#list childMenus.mixedMeterial.subMixedMeterialList as subMixedMeterial>
										                                            <div class="inner msg-item">
										                                                <img class="msg-thumb" style="display:block" src="${subMixedMeterial.coverPicPath}">
										                                                <i class="msg-thumb-size default" style="display:none"></i>
										                                                <h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">${subMixedMeterial.title}</a></h4>
										                                                <div class="msg-edit-mask">
										                                                    <a class="icons_edit edit-white" href="javascript:void(0)"></a>
										                                                    <div class="spaceW9"></div>
										                                                    <a class="icons_edit del-white" href="javascript:void(0)"></a>
										                                                </div>
										                                            </div>
										                                		</#list>
									                                        </div>
									                                    </div>
									                                    </div>
									                                    <div class="row-li addfirstMenu textleft" style="width:15%;">
									                                        <div class="leftpadding">
									                                            <button type="button" class="btn btn-tool" onclick="editMultiTextMenu(this,'${childMenus.mixedMeterial.id}','${childMenus.id}');"><i class="icons-edit"></i></button>
									                                            <button type="button" class="btn btn-tool" onclick="delMultiTextMenu(this);"><i class="icons-trash"></i></button>
									                                        </div>
									                                    </div>
									                                </div>
			                           							<#else>
			                           								<div class="action-second clearfix">
																		<div class="row-li" style="width:10%;"></div>
																		<div class="row-li textright" style="width: 75%">
																			<div class="sidepadding">
																				<span class="tag-line"></span>
																				<div class="t-msg">
																					<div class="inner">
																						<div class="as-thumb">
																							<h4 class="msg-title js_msgTitle">${childMenus.mixedMeterial.title}</h4>
																							<input type="hidden" name="grapicsId" value="${childMenus.mixedMeterial.id}" />
																							<div class="msg-info">2015-5-20 17:08:29</div>
																							<div class="msg-thumb-wrap">
																								<img class="msg-thumb" style="display: block;" src="${childMenus.mixedMeterial.coverPicPath}" />
																								<i class="msg-thumb-size default" style="display:none"></i>
																							</div>
																							<div class="msg-desc js_msgDesc">${childMenus.mixedMeterial.description}</div>
																						</div>
																					</div>
																				</div>
																			</div>
																		</div>
																		<div class="row-li addfirstMenu textleft" style="width:15%;">
																			<div class="leftpadding">
																				<button type="button" class="btn btn-tool" onclick="editSingleTextMenu(this,'${childMenus.mixedMeterial.id}','${childMenus.id}');">
																					<i class="icons-edit"></i>
																				</button>
																				<button type="button" class="btn btn-tool" onclick="delSingleTextMenu(this);">
																					<i class="icons-trash"></i>
																				</button>
																			</div>
																		</div>
																	</div>
			                           							</#if>
	                           								</#if>
		                           						</div>
		                           					</div>
		                           				</#list>
		                           			</#if>
		                           		</div>
		                           	</div>
		                           	<!-- 二级菜单块 start -->
                       			</#list>
                       		<#else>
                       			<div class="action_box clearfix availability"></div>
                       		</#if>
                            <!--包含 一 二级菜单 end-->
                        </div>

                        <!--内容 end-->
                    </div>
                </div>
            </div>
            <div class="footer-tool">
                <div class="row-fluid">
                    <button class="btn btn-remove" onclick="$hospital.back('${basePath }sys/optional/toView?hospitalId=${hospitalId }');">上一步</button>
                    <button class="btn btn-save" onclick="$hospital.menu.save();" >保存</button>
                    <button class="btn btn-post" onclick="$hospital.optional.publish();">发布菜单</button>
                </div>
            </div>

        </div>
    </div>
<!--content end-->

</body>
</html>
<script type="text/javascript">
	$hospital.id = '${hospitalId}';
	var basePath = '${basePath}';
	//切换接入平台 
	function getMeun(obj){
		var platformsId = jQuery("#platforms").find("option:selected").attr("value");
		var hospitalId = jQuery("#hospitalId").attr("value");
		location.href = '../customerMenu/toView?hospitalId=' + hospitalId + '&platformsId=' + platformsId;
	}


    //添加一级菜单
  	function addfirstMenu(obj){
 		var flagTime = new Date().getTime();
       	var html='';
           html+='<div class="action_box clearfix availability">';
           html+=' <div class="action-row clearfix"><input type="hidden" name="id" value="" />';
           html+=  '<div class="row-li" style="width:10%;"><div class="sidepadding"><span class="label_id">1</span></div></div>';
           html+=  '<div class="row-li" style="width:25%;"><div class="sidepadding"><input type="text" class="span12" name="name"/></div></div>';
           <#if platformVo.code == 'wechat' || platformVo.code == 'alipay'>
           	 	html+=  '<div class="row-li" style="width:25%;"><div class="sidepadding"><div class="text-default">一级菜单</div></div></div>';
           <#else>
           		html+=  '<div class="row-li" style="width:25%;"><div class="sidepadding"><div class="text-default">普通功能</div></div></div>';
           </#if>
           html+=  '<div class="row-li" style="width:25%;"><div class="sidepadding"><div class="my_select">';
           html+=  '<select name="menuOptionalSelect" class="span12" onchange="changeSet(this)" flagTime="' + flagTime + '"><option value="">请选择</option>';
	           	<#if platformVo.code == 'wechat' || platformVo.code == 'alipay'>
	           		html+=  '<option value="" menuType="1">外部链接</option><option  value="" menuType="2">图文素材</option>';
	           	</#if>
            	<#list selHospitalOptionalsList as selHospitalOptionals>
            		<#if platformVo.code == 'wechat' || platformVo.code == 'alipay'>
                		html+=  '<option value="${selHospitalOptionals.optional.id}" bizCode="${selHospitalOptionals.optional.bizCode}">${selHospitalOptionals.optional.name}</option>';
                	<#else>
                		<#if selHospitalOptionals.optional.isApp == 1>
                			html+=  '<option value="${selHospitalOptionals.optional.id}" bizCode="${selHospitalOptionals.optional.bizCode}">${selHospitalOptionals.optional.name}</option>';
                		</#if>
                	</#if>
                </#list>
		   html+=  '</select>';
           html+=  '<input type="hidden" name="level" value="level1" /><input type="hidden" name="oldOptionalId" value="" /><input type="hidden" name="oldMenutype" value="" /></div></div></div>';
           html+=  '<div class="row-li addfirstMenu textleft" style="width:15%;"><div class="leftpadding">';
           html+=  '<#if platformVo.id == platformsMap['wechat'].id || platformVo.id == platformsMap['alipay'].id><button type="button" class="btn btn-tool" name="levelAdd" onclick="addSecondMenu(this);"><i class="icons-plus"></i></button></#if>';
           html+=  '<button type="button" class="btn btn-tool" onclick="delFirstMenu(this);"><i class="icons-trash"></i></button>';
           html+=  '</div></div></div>';
           html+=  '<div class="action-second-content-parent clearfix"></div><div class="action-second-box clearfix"></div></div>';
      $('.totalList').append(html);
      $('.label_id').each(function(index){
          $(this).html(index+1);
      });
  }

   //删除一级菜单
    function delFirstMenu(obj){
    	var platformsCode = jQuery("#platforms").find("option:selected").attr("code");
    	var msg = "删除一级菜单会级联删除二级菜单，确定删除?";
	    if(platformsCode == "easyHealth"){
	    	msg = "确定删除当前功能?";
	    }
    	if(confirm(msg)){
	        $(obj).parents('.action_box').remove();
	        $('.label_id').each(function(index){
	            $(this).html(index+1);
	        });
        }
    }
 //添加二级级菜单
 function addSecondMenu(obj){
     var p = $(obj).parents('.action_box');
     var secondBox = p.find('.action-second-box');
     var flagTime = new Date().getTime();
     var html='';
     html+='<div class="action-second-outer clearfix"><div class="action-second clearfix"><input type="hidden" name="id" value="" /><div class="row-li" style="width:10%;"></div>';
     html+=' <div class="row-li" style="width:25%;"><div class="sidepadding"><input type="text" class="span12" name="name"/></div></div>';
     html+=' <div class="row-li" style="width:25%;"><div class="sidepadding"><div class="text-default">二级菜单</div></div></div>';
     html+='<div class="row-li" style="width:25%;"><div class="sidepadding"><div class="my_select">';
     html+=  '<select name="menuOptionalSelect" class="span12" onchange="changeSet(this)" flagTime="' + flagTime + '"><option value="">请选择</option><option value="" menuType="1">外部链接</option><option  value="" menuType="2">图文素材</option>';
     	<#list selHospitalOptionalsList as selHospitalOptionals>
        	html+=  '<option value="${selHospitalOptionals.optional.id}" bizCode="${selHospitalOptionals.optional.bizCode}">${selHospitalOptionals.optional.name}</option>';
        </#list>
	 html+=  '</select>';
     html+=' <input type="hidden" name="level" value="level2" /><input type="hidden" name="oldOptionalId" value="" /><input type="hidden" name="oldMenutype" value="" /></div></div> </div>';
     html+=' <div class="row-li addfirstMenu textleft" style="width:15%;"><div class="leftpadding">';
     html+=' <button type="button" class="btn btn-tool" onclick="delSecondMenu(this);;"><i class="icons-trash"></i></button>';
     html+=' </div></div></div>';
     html+=' <div class="action-second-content clearfix"></div></div></div>';
     $(secondBox).append(html);
 }
  	//删除二级菜单
    function delSecondMenu(obj){
    	if(confirm("确定删除?")){
        	$(obj).parents('.action-second-outer').remove();
        }
    }

  	// select
	function changeSet(obj){
    	var count = $(obj).find("option:selected").attr("menuType");
    	console.log($(obj).find("option:selected").html());
    	console.log(count);
    	var level = $(obj).parent().find(':hidden[name=level]').val();
    	console.log($(obj).parents('.availability').find('.action-second-box').find('.action-second-outer').length);
    	//如果是一级菜单时，判断是否有二级菜单，如果存在二级菜单则不能设置为外部链接和图文素材
    	if(level == 'level1' && (count == 1 || count == 2)){
    		if($(obj).parents('.availability').find('.action-second-box').find('.action-second-outer').length > 0){
    			var oldOptionalId = jQuery(obj).parent().find(':hidden[name=oldOptionalId]').val();
    			var oldMenutype = jQuery(obj).parent().find(':hidden[name=oldMenutype]').val();
    			console.log('oldOptionalId:' + oldOptionalId);
    			console.log('oldMenutype:' + oldMenutype);
    			//console.log(jQuery(obj).parent().html());
    			if(oldOptionalId != ''){
					jQuery(obj).parent().find('option[value=' + oldOptionalId + ']').attr('selected','selected');
				}else if(oldMenutype != ''){
					jQuery(obj).parent().find('option[menutype=' + oldMenutype + ']').attr('selected','selected');
				}else{
					jQuery(obj).parent().find('option').eq(0).attr('selected','selected');
				}
    			alert('当前菜单存在子菜单!');
    			return false;
    		}
    	}
    	
    	//删除当前菜单下的外部链接或者图文素材内容
    	var secondContent = null;
    	if(level == 'level1'){//一级菜单功能下拉框改变
    		secondContent = $(obj).parents('.action-row').parent().find('.action-second-content-parent');
    	}else{
    		secondContent = $(obj).parents('.action-second-outer ').find('.action-second-content');
    	}
    	jQuery(secondContent).find(".action-second").remove();
    	//console.log(jQuery(obj).parent().parent().parent().parent().html());
    	//控制一级菜单添加按钮
    	if(level == 'level1' && (count == 1 || count == 2)){
    		//外部链接及图文素材类型时不可用
    		jQuery(obj).parent().parent().parent().parent().find('button[name=levelAdd]').attr('disabled','disabled');
    	}else{
    		//非外部链接及图文素材类型时可用
    		jQuery(obj).parent().parent().parent().parent().find('button[name=levelAdd]').removeAttr('disabled');
    	}
    	if(count == 1){
        	addOutLink(obj);
    	}else if(count == 2){
    		var flagtime = $(obj).attr("flagTime");
        	addmaterial(obj);
        	//singleText(obj);
    	}
	}
    //外部链接弹窗
    function addOutLink(obj){
        new $Y.dialog({
            title:'用户点击该子菜单会跳到一下链接',
            width:'480px',
            height:'150px',
            content:'',
            callback:function(box){
                $.ajax({
                    url:basePath + 'sys/customerMenu/dialogLink',
                    dataType:'html',
                    cache:false,
                    success:function(html){
                        box.content(html);
                        $Y.ScrollBar();
                        $(box.id).on('click','.btn-save',function(){
                        	var link = jQuery("#dialogContent").val();
                        	if($.trim(link) != ''){
                        		box.close();
                            	outLink(obj,link);
                        	}else{
                        		alert("请输入外部链接地址");
                        	}
                        });
                        $(box.id).on('click','.btn-remove',function(){
                            box.close();
                        });
                    }
                })
            }
        });
    }
    
    function editOutLink(obj){
    	new $Y.dialog({
            title:'用户点击该子菜单会跳到一下链接',
            width:'480px',
            height:'150px',
            content:'',
            callback:function(box){
                $.ajax({
                    url:basePath + 'sys/customerMenu/dialogLink',
                    dataType:'html',
                    cache:false,
                    success:function(html){
                        box.content(html);
                        $Y.ScrollBar();
                        var content = jQuery(obj).parent().parent().parent().find(":text[name=content]");
                        //alert("content:" + jQuery(content).val())
                        jQuery("#dialogContent").val(jQuery(content).val());
                        $(box.id).on('click','.btn-save',function(){
                        	var link = jQuery("#dialogContent").val();
                        	if($.trim(link) != ''){
                        		jQuery(content).val(link)
                           	 	box.close();
                        	}else{
                        		alert("请输入外部链接地址");
                        	}
                        	
                        });
                        $(box.id).on('click','.btn-remove',function(){
                            box.close();
                        });
                    }
                })
            }
        });
    }
    
    
    //外部链接内容
    function outLink(obj,link){
   	 	console.log($(obj).parent().find(':hidden[name=level]').val());
    	var secondContent = null;
    	if($(obj).parent().find(':hidden[name=level]').val() == 'level1'){
    		secondContent = $(obj).parents('.action-row').parent().find('.action-second-content-parent');
    	}else{
    		secondContent = $(obj).parents('.action-second-outer').find('.action-second-content');
    	}
    	var html='';
	        html+='<div class="action-second clearfix">  <div class="row-li" style="width:10%;"></div>';
	        html+='<div class="row-li textright" style="width: 75%"><div class="sidepadding"><span class="tag-line"></span><input type="text" class="span11 link" readonly="readonly" name="content" value="' + link + '"/></div></div>';
	        html+='    <div class="row-li addfirstMenu textleft" style="width:15%;"> <div class="leftpadding">';
	        html+=' <button type="button" class="btn btn-tool" onclick="editLinkMenu(this);"><i class="icons-edit"></i></button>';
	        html+='<button type="button" class="btn btn-tool" onclick="delLinkMenu(this);;"><i class="icons-trash"></i></button>';
	        html+='</div></div></div>';
	        $(secondContent).append(html);
       
    }
    //编辑外部链接
    function editLinkMenu(obj){
        editOutLink(obj);
    }
    //删除外部链接
    function delLinkMenu(obj){
        $(obj).parents('.action-second').remove();
    }
    var box;
    //图文素材弹窗
    function addmaterial(obj){
    	var flagTime = jQuery(obj).attr("flagTime");
    	console.log("addmaterial.flagTime:" + flagTime);
    	if(flagTime == ''){
    		jQuery(obj).attr("flagTime",new Date().getTime());
    	}
       	box = new $Y.dialog({
            title:'添加图文消息',
            width:'400px',
            height:'325px',
            content:'',
            callback:function(){
                $.ajax({
                    url:basePath + 'sys/customerMenu/dialogMaterial?hospitalId=${hospitalId}',
                    dataType:'html',
                    cache:false,
                    success:function(html){
                        box.content(html);
                        $Y.ScrollBar();
                        var index = null;
                        var iTop = (window.screen.availHeight-30)/2; //获得窗口的垂直位置;
						var iLeft = (window.screen.availWidth-10-1400)/2; //获得窗口的水平位置;
                        $(box.id).find('#singleBtn').bind('click',function(){
							window.open(basePath + "message/reply?method=toSingleText&hospitalId="+$hospital.id+"&index="+index+"&type=2&flagTime=" + flagTime,
							"_blank","toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no,height="+window.screen.availHeight+", width=1400, top="+iTop+", left="+iLeft);
                    	});
                    	
                    	$(box.id).find('#multiBtn').bind('click',function(){
                    		window.open(basePath + "message/reply?method=toMulti&hospitalId="+$hospital.id+"&index="+index+"&type=2&flagTime=" + flagTime,
							"_blank","toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no,height="+window.screen.availHeight+", width=1400, top="+iTop+", left="+iLeft);
                    	});
                    	$(box.id).find('#metarialId').bind('change',function()
	                    {
	                    	$.ajax({
						        url: "${request.contextPath}/message/mixedMeterial?method=getMeterialById",
						        type:"POST",
						        dataType:"JSON",
						        data:{id:$(this).val()},
						        success:function(resp){
						            if(resp.status == 'OK')
						            {
						            	if(resp.message.type==1)
						            	{
						            		var editTime = new Date(parseInt(resp.message.et)).toLocaleString();
						            		singleText(1,resp.message.id,resp.message.title,resp.message.description,resp.message.coverPicPath,flagTime,editTime);
						            	}
						            	else
						            	{
						            		MultiTextObj(resp.message,flagTime);
						            	}
						            	box.close();
						            }
						            else
						            {
						            	window.wxc.xcConfirm("添加失败", window.wxc.xcConfirm.typeEnum.error);
						                box.close();
						            }
						        }
						    });
	                    });
                        
                        /*$(box.id).on('click','.btn-save',function(){
                            box.close();

                        });
                        $(box.id).on('click','.btn-remove',function(){
                            box.close();
                        });*/
                    }
                })
            }
        });
    }
    
    
    //图文素材内容--单图文
    function singleText(index,id,title,content,imgsrc,flagTime,editTime){
    	console.log($("select[flagTime='" + flagTime + "']").parent().find(':hidden[name=level]').val());
    	var secondContent = null;
    	if($("select[flagTime='" + flagTime + "']").parent().find(':hidden[name=level]').val() == 'level1' ){
    		secondContent = $("select[flagTime='" + flagTime + "']").parents('.action-row').parent().find('.action-second-content-parent');
    	}else{
    		secondContent = $("select[flagTime='" + flagTime + "']").parents('.action-second-outer').find('.action-second-content');
    	}
        var html='<div class="action-second clearfix"><div class="row-li" style="width:10%;"></div>';
        html+='<div class="row-li textright" style="width: 75%"><div class="sidepadding"><span class="tag-line"></span>';
        html+=' <div class="t-msg"><div class="inner"> <div class="as-thumb">';
        html+='<h4 class="msg-title js_msgTitle">' + title + '<input type="hidden" name="grapicsId" value="' + id + '" /></h4>';
        html+='<div class="msg-info">' + editTime + '</div><div class="msg-thumb-wrap">';
        html+='<img class="msg-thumb" style="display: block;" src="' + imgsrc + '"/><i class="msg-thumb-size default" style="display:none"></i>';
        html+=' </div>';
        html+=' <div class="msg-desc js_msgDesc">' + content + '</div>';
        html+='</div></div></div></div></div>';
        html+='   <div class="row-li addfirstMenu textleft" style="width:15%;"> <div class="leftpadding">';
        html+=' <button type="button" class="btn btn-tool" onclick="editSingleTextMenu(this,\'' + id + '\',\'' + flagTime + '\');"><i class="icons-edit"></i></button>';
        html+=' <button type="button" class="btn btn-tool" onclick="delSingleTextMenu(this);"><i class="icons-trash"></i></button>';
        html+=' </div></div></div>';
        $(secondContent).find("div").remove();
        $(secondContent).append(html);
        box.close();
    }
    
    //编辑单图文
    function editSingleTextMenu(obj, id, flagTime){
        var iTop = (window.screen.availHeight-30)/2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-1400)/2; //获得窗口的水平位置;
    	box = window.open(basePath + "message/mixedMeterial?method=toEditMeterial&meterialType=1&flagTime=" + flagTime + "&id=" + id+"&hospitalId=${hospitalId}",
		"_blank","toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no,height="+window.screen.availHeight+", width=1400, top="+iTop+", left="+iLeft);
    }
    
    //编辑多图文
    function editMultiTextMenu(obj, id, flagTime){
        var iTop = (window.screen.availHeight-30)/2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-1400)/2; //获得窗口的水平位置;
    	box = window.open(basePath + "message/mixedMeterial?method=toEditMeterial&meterialType=2&flagTime=" + flagTime + "&id=" + id+"&hospitalId=${hospitalId}",
		"_blank","toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no,height="+window.screen.availHeight+", width=1400, top="+iTop+", left="+iLeft);
    }
    
    
    //删除单图文
    function delSingleTextMenu(obj){
        $(obj).parents('.action-second').remove();
    }

    //图文素材内容--多图文
    function MultiText(index,ids,objArray,flagTime){
    	console.log($("select[flagTime='" + flagTime + "']").html());
    	console.log($("select[flagTime='" + flagTime + "']").parent().find(':hidden[name=level]').val());
    	console.log(objArray);
    	var secondContent = null;
    	if($("select[flagTime='" + flagTime + "']").parent().find(':hidden[name=level]').val() == 'level1' ){
    		secondContent = $("select[flagTime='" + flagTime + "']").parents('.action-row').parent().find('.action-second-content-parent');
    	}else{
    		secondContent = $("select[flagTime='" + flagTime + "']").parents('.action-second-outer').find('.action-second-content');
    	}
  
        if(objArray.length>0){
        	var html='';
	        html+='<div class="action-second clearfix"> <div class="row-li" style="width:10%;"></div>';
	        html+='<div class="row-li textright" style="width: 75%"><div class="sidepadding">   <span class="tag-line"></span>';
	        html+='<div class="t-msg"><div class="inner"> <div class="multi-thumb">';
	        html+='<h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">' + objArray[0].title + '</a><input type="hidden" name="grapicsId" value="' + ids + '" /></h4>';
	        html+='<div class="msg-thumb-wrap"><img class="msg-thumb"  style="display: block;" src="'+ objArray[0].coverPicPath + '"/><i class="msg-thumb-size default" style="display:none"></i></div>';
	        html+='<div class="msg-edit-mask"><a class="icons_edit edit-white" href="javascript:void(0)"></a></div>';
	        html+='</div> </div>';
	        for(var i=1;i<objArray.length;i++){
		        html+='<div class="msg-out clearfix">  <div class="inner msg-item">';
		        html+='<img class="msg-thumb" style="display:block"  src="'+objArray[i].coverPicPath+'"/>';
		        html+='<i class="msg-thumb-size default" style="display:none" ></i>';
		        html+='<h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">'+objArray[i].title+'</a></h4>';
		        html+='<div class="msg-edit-mask">';
		        html+='<a class="icons_edit edit-white" href="javascript:void(0)"></a>';
		        html+='<div class="spaceW9"></div>';
		        html+='<a class="icons_edit del-white" href="javascript:void(0)" onclick="delThisWhite(this);"></a>';
		        html+='</div></div></div>';
	        }
	        html+='</div></div></div>';
	       	html+='<div class="row-li addfirstMenu textleft" style="width:15%;"><div class="leftpadding">';
			html+='<button type="button" class="btn btn-tool" onclick="editMultiTextMenu(this,\'' + ids + '\',\'' + flagTime  + '\');"><i class="icons-edit"></i></button>';
			html+='<button type="button" class="btn btn-tool" onclick="delMultiTextMenu(this);"><i class="icons-trash"></i></button>';
			html+='</div></div>';
			
			console.dir($(secondContent).html());
	        $(secondContent).find("div").remove();
	        $(secondContent).append(html);
	        box.close();
        }
    }
    
    //图文素材内容--多图文
    function MultiTextObj(obj,flagTime){
    	var secondContent = null;
    	if($("select[flagTime='" + flagTime + "']").parent().find(':hidden[name=level]').val() == 'level1' ){
    		secondContent = $("select[flagTime='" + flagTime + "']").parents('.action-row').parent().find('.action-second-content-parent');
    	}else{
    		secondContent = $("select[flagTime='" + flagTime + "']").parents('.action-second-outer').find('.action-second-content');
    	}
  
        if(obj){
        	var html='';
	        html+='<div class="action-second clearfix"> <div class="row-li" style="width:10%;"></div>';
	        html+='<div class="row-li textright" style="width: 75%"><div class="sidepadding">   <span class="tag-line"></span>';
	        html+='<div class="t-msg"><div class="inner"> <div class="multi-thumb">';
	        html+='<h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">' + obj.title + '</a><input type="hidden" name="grapicsId" value="' + obj.id + '" /></h4>';
	        html+='<div class="msg-thumb-wrap"><img class="msg-thumb"  style="display: block;" src="'+ obj.coverPicPath + '"/><i class="msg-thumb-size default" style="display:none"></i></div>';
	        html+='<div class="msg-edit-mask"><a class="icons_edit edit-white" href="javascript:void(0)"></a></div>';
	        html+='</div> </div>';
	        var subList = obj.subMixedMeterialList;
	        for(var i=0;i<subList.length;i++){
		        html+='<div class="msg-out clearfix">  <div class="inner msg-item">';
		        html+='<img class="msg-thumb" style="display:block"  src="'+subList[i].coverPicPath+'"/>';
		        html+='<i class="msg-thumb-size default" style="display:none" ></i>';
		        html+='<h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">'+subList[i].title+'</a></h4>';
		        html+='<div class="msg-edit-mask">';
		        html+='<a class="icons_edit edit-white" href="javascript:void(0)"></a>';
		        html+='<div class="spaceW9"></div>';
		        html+='<a class="icons_edit del-white" href="javascript:void(0)" onclick="delThisWhite(this);"></a>';
		        html+='</div></div></div>';
	        }
	        html+='</div></div></div>';
	       	html+='<div class="row-li addfirstMenu textleft" style="width:15%;"><div class="leftpadding">';
			html+='<button type="button" class="btn btn-tool" onclick="editMultiTextMenu(this,\'' + obj.id + '\',\'' + flagTime  + '\');"><i class="icons-edit"></i></button>';
			html+='<button type="button" class="btn btn-tool" onclick="delMultiTextMenu(this);"><i class="icons-trash"></i></button>';
			html+='</div></div>';
			
	        $(secondContent).find("div").remove();
	        $(secondContent).append(html);
	        box.close();
        }
    }
    //删除多图文
    function delMultiTextMenu(obj){
        $(obj).parents('.action-second').remove();
    }
</script>