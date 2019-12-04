<html>
<head>
<#include "/sys/common/common.ftl">
<title>推送规则</title>
<script type="text/javascript" src="${basePath}js/sys/rule/sys.rule.js"></script>
<style>
.tabs li a {
	min-width: 140px;
}
</style>
</head>
<body>
	<!--header str-->
	<#include "./sys/common/hospital_setting.ftl">
	<div id="content-header">
		<div class="widget-title">
			<h3 class="title">推送规则</h3>
			<#include "/sys/rule/rule_select.ftl">
		</div>
	</div>
	<div class="container-fluid">
		<div class="space10"></div>
		<div class="row-fluid">
			<div class="space30"></div>
			<div class="row-fluid">
				<div class="pull-left">
					<ul class="tabs">
						<#list platforms?keys as platformCode>
						<li>
							<a href="#tab${platforms["${platformCode}"].targetId}"<#if (platformCode_index = 0)>class="select"</#if>>${platforms["${platformCode}"].platformName}</a>
						</li>
						</#list>
					</ul>
				</div>
			</div>
			<div class="space10"></div>
			<#list platforms?keys as platformCode>
			<div class="widget-box bangKa tab_content" id="tab${platforms["${platformCode}"].targetId}" style="display: none;">
				<div class="space10"></div>
				<form class="form-horizontal evenBg rulePush" id="editRuleForm${platforms["${platformCode}"].platformCode}">
					<input type="hidden" name="hospitalId" value="${rules["${platformCode}"].hospitalId}" />
					<input type="hidden" id="id" name="id" value="${rules["${platformCode}"].id}" />
					<input type="hidden" id="platformType" name="platformType" value="${platforms["${platformCode}"].platformCode}" />
					<div class="widget-content guaHao">
						<div class="row-fluid">
							<div class="control-box show">
								<div class="control-group w235">
									<label class="control-label">消息推送模式</label>
									<div class="controls ">
										<#list platforms['${platformCode}'].msgModes as mode>
											<label class="checkboxTwo inline <#if rules["${platformCode}"].modeArray?seq_contains("${mode.targetId}")>check</#if>">
												<input name="modeArray" type="checkbox" value="${mode.targetId}" <#if rules["${platformCode}"].modeArray?seq_contains("${mode.targetId}")>checked="checked"</#if>>${mode.name}
											</label>
										</#list>
									</div>
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].bindCardSuc == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">绑卡成功是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].bindCardSuc == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> 
											<input type="radio" name="bindCardSuc" value="1"<#if rules["${platformCode}"].bindCardSuc == 1>checked="checked"<#else></#if>>是
										</label> 
										<label class="radio inline <#if rules["${platformCode}"].bindCardSuc == 0>check<#else></#if>"> 
											<input type="radio" name="bindCardSuc" value="0"<#if rules["${platformCode}"].bindCardSuc == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="bindCardSucCode" value="${rules["${platformCode}"].bindCardSucCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].createCardSuc == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">建档成功是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].createCardSuc == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="createCardSuc" value="1"<#if rules["${platformCode}"].createCardSuc == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].createCardSuc == 0>check<#else></#if>"> <input type="radio" name="createCardSuc" value="0"<#if
											rules["${platformCode}"].createCardSuc == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="createCardSucCode" value="${rules["${platformCode}"].createCardSucCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].predayVisit == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">就诊前一天是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].predayVisit == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="predayVisit" value="1"<#if rules["${platformCode}"].predayVisit == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].predayVisit == 0>check<#else></#if>"> <input type="radio" name="predayVisit" value="0"<#if
											rules["${platformCode}"].predayVisit == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="predayVisitCode" value="${rules["${platformCode}"].predayVisitCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].curdayVisit == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">就诊当天是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].curdayVisit == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="curdayVisit" value="1"<#if rules["${platformCode}"].curdayVisit == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].curdayVisit == 0>check<#else></#if>"> <input type="radio" name="curdayVisit" value="0"<#if
											rules["${platformCode}"].curdayVisit == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="curdayVisitCode" value="${rules["${platformCode}"].curdayVisitCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].lockResSuc == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">锁号成功是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].lockResSuc == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="lockResSuc" value="1"<#if rules["${platformCode}"].lockResSuc == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].lockResSuc == 0>check<#else></#if>"> <input type="radio" name="lockResSuc" value="0"<#if
											rules["${platformCode}"].lockResSuc == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="lockResSucCode" value="${rules["${platformCode}"].lockResSucCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].onDutyPaySuc == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">当班挂号成功是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].onDutyPaySuc == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="onDutyPaySuc" value="1"<#if rules["${platformCode}"].onDutyPaySuc == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].onDutyPaySuc == 0>check<#else></#if>"> <input type="radio" name="onDutyPaySuc" value="0"<#if
											rules["${platformCode}"].onDutyPaySuc == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="onDutyPaySucCode" value="${rules["${platformCode}"].onDutyPaySucCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].appointPaySuc == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">预约挂号成功是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].appointPaySuc == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="appointPaySuc" value="1"<#if rules["${platformCode}"].appointPaySuc == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].appointPaySuc == 0>check<#else></#if>"> <input type="radio" name="appointPaySuc" value="0"<#if
											rules["${platformCode}"].appointPaySuc == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="appointPaySucCode" value="${rules["${platformCode}"].appointPaySucCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].appointPayFail == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">挂号失败是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].appointPayFail == 1>check<#else></#if> " style="margin-right: 50px; display: inline;"> <input type="radio"
												name="appointPayFail" value="1"<#if rules["${platformCode}"].appointPayFail == 1>checked="checked"<#else></#if> >是
										</label> <label class="radio inline <#if rules["${platformCode}"].appointPayFail == 0>check<#else></#if> "> <input type="radio" name="appointPayFail" value="0"<#if
											rules["${platformCode}"].appointPayFail == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="appointPayFailCode" value="${rules["${platformCode}"].appointPayFailCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].appointPayExp == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">挂号异常是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].appointPayExp == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="appointPayExp" value="1"<#if rules["${platformCode}"].appointPayExp == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].appointPayExp == 0>check<#else></#if>"> <input type="radio" name="appointPayExp" value="0"<#if
											rules["${platformCode}"].appointPayExp == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="appointPayExpCode" value="${rules["${platformCode}"].appointPayExpCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].cancelOnDuty == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">取消当班挂号是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes  <#if rules["${platformCode}"].cancelOnDuty == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="cancelOnDuty" value="1"<#if rules["${platformCode}"].cancelOnDuty == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline  <#if rules["${platformCode}"].cancelOnDuty == 0>check<#else></#if>"> <input type="radio" name="cancelOnDuty" value="0"<#if
											rules["${platformCode}"].cancelOnDuty == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="cancelOnDutyCode" value="${rules["${platformCode}"].cancelOnDutyCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].cancelAppointment == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">取消预约挂号是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].cancelAppointment == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="cancelAppointment" value="1"<#if rules["${platformCode}"].cancelAppointment == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].cancelAppointment == 0>check<#else></#if>"> <input type="radio" name="cancelAppointment" value="0"<#if
											rules["${platformCode}"].cancelAppointment == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="cancelAppointmentCode"
										value="${rules["${platformCode}"].cancelAppointmentCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].refundSuccess == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">当班挂号退费成功是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].refundSuccess == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="refundSuccess" value="1"<#if rules["${platformCode}"].refundSuccess == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].refundSuccess == 0>check<#else></#if>"> <input type="radio" name="refundSuccess" value="0"<#if
											rules["${platformCode}"].refundSuccess == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="refundSuccessCode" value="${rules["${platformCode}"].refundSuccessCode}" />
								</div>
							</div>
							
							<div class="control-box <#if rules["${platformCode}"].refundSuccessAppoint == 1>show<#else></#if>">
                                <div class="control-group w235">
                                    <label class="control-label">预约挂号退费成功是否推送消息</label>
                                    <div class="controls ">
                                        <label class="radio inline yes <#if rules["${platformCode}"].refundSuccessAppoint == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> 
                                        <input type="radio" name="refundSuccessAppoint" value="1" <#if rules["${platformCode}"].refundSuccessAppoint == 1>checked="checked"<#else></#if>>是</label>
                                        <label class="radio inline <#if rules["${platformCode}"].refundSuccessAppoint == 0>check<#else></#if>"> 
                                        <input type="radio" name="refundSuccessAppoint" value="0" <#if rules["${platformCode}"].refundSuccessAppoint == 0>checked="checked"<#else></#if>>否</label>
                                    </div>
                                </div>
                                <div class="control-input">
                                    模版编码<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="refundSuccessAppointCode" value="${rules["${platformCode}"].refundSuccessAppointCode}"/>
                                </div>
                            </div>
							
							<div class="control-box <#if rules["${platformCode}"].refundFail == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">挂号退费失败是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].refundFail == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="refundFail" value="1"<#if rules["${platformCode}"].refundFail == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].refundFail == 0>check<#else></#if>"> <input type="radio" name="refundFail" value="0"<#if
											rules["${platformCode}"].refundFail == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="refundFailCode" value="${rules["${platformCode}"].refundFailCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].refundException == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">挂号退费异常是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].refundException == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="refundException" value="1"<#if rules["${platformCode}"].refundException == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].refundException == 0>check<#else></#if>"> <input type="radio" name="refundException" value="0"<#if
											rules["${platformCode}"].refundException == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="refundExceptionCode"
										value="${rules["${platformCode}"].refundExceptionCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].replaceRegVisit == 1>show<#else></#if>">
                                <div class="control-group w235">
                                    <label class="control-label">医生替诊是否推送消息</label>
                                    <div class="controls ">
                                        <label class="radio inline yes <#if rules["${platformCode}"].replaceRegVisit == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> 
                                        <input type="radio" name="replaceRegVisit" value="1" <#if rules["${platformCode}"].replaceRegVisit == 1>checked="checked"<#else></#if>>是</label>
                                        <label class="radio inline <#if rules["${platformCode}"].replaceRegVisit == 0>check<#else></#if>"> 
                                        <input type="radio" name="replaceRegVisit" value="0" <#if rules["${platformCode}"].replaceRegVisit == 0>checked="checked"<#else></#if>>否</label>
                                    </div>
                                </div>
                                <div class="control-input">
                                    模版编码<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="replaceRegVisitCode" value="${rules["${platformCode}"].replaceRegVisitCode}"/>
                                </div>
                            </div>
							<div class="control-box <#if rules["${platformCode}"].stopVisit == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">医生停诊是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].stopVisit == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="stopVisit" value="1"<#if rules["${platformCode}"].stopVisit == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].stopVisit == 0>check<#else></#if>"> <input type="radio" name="stopVisit" value="0"<#if
											rules["${platformCode}"].stopVisit == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="stopVisitCode" value="${rules["${platformCode}"].stopVisitCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].waitVisit == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">候诊排队是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].waitVisit == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="waitVisit" value="1"<#if rules["${platformCode}"].waitVisit == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].waitVisit == 0>check<#else></#if>"> <input type="radio" name="waitVisit" value="0"<#if
											rules["${platformCode}"].waitVisit == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="waitVisitCode" value="${rules["${platformCode}"].waitVisitCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].clinicPaySuc == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">门诊缴费成功是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].clinicPaySuc == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="clinicPaySuc" value="1"<#if rules["${platformCode}"].clinicPaySuc == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].clinicPaySuc == 0>check<#else></#if>"> <input type="radio" name="clinicPaySuc" value="0"<#if
											rules["${platformCode}"].clinicPaySuc == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="clinicPaySucCode" value="${rules["${platformCode}"].clinicPaySucCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].clinicPayFail == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">门诊缴费失败是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].clinicPayFail == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="clinicPayFail" value="1"<#if rules["${platformCode}"].clinicPayFail == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].clinicPayFail == 0>check<#else></#if>"> <input type="radio" name="clinicPayFail" value="0"<#if
											rules["${platformCode}"].clinicPayFail == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="clinicPayFailCode" value="${rules["${platformCode}"].clinicPayFailCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].clinicPayExp == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">门诊缴费异常是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].clinicPayExp == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="clinicPayExp" value="1"<#if rules["${platformCode}"].clinicPayExp == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].clinicPayExp == 0>check<#else></#if>"> <input type="radio" name="clinicPayExp" value="0"<#if
											rules["${platformCode}"].clinicPayExp == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="clinicPayExpCode" value="${rules["${platformCode}"].clinicPayExpCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].payDepositSuc == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">押金补缴成功是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].payDepositSuc == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="payDepositSuc" value="1"<#if rules["${platformCode}"].payDepositSuc == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].payDepositSuc == 0>check<#else></#if>"> <input type="radio" name="payDepositSuc" value="0"<#if
											rules["${platformCode}"].payDepositSuc == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="payDepositSucCode" value="${rules["${platformCode}"].payDepositSucCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].payDepositFail == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">押金补缴失败是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].payDepositFail == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="payDepositFail" value="1"<#if rules["${platformCode}"].payDepositFail == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].payDepositFail == 0>check<#else></#if>"> <input type="radio" name="payDepositFail" value="0"<#if
											rules["${platformCode}"].payDepositFail == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="payDepositFailCode" value="${rules["${platformCode}"].payDepositFailCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].payDepositExp == 1>show<#else></#if>">
								<div class="control-group w235">
									<label class="control-label">押金补缴异常是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes <#if rules["${platformCode}"].payDepositExp == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="payDepositExp" value="1"<#if rules["${platformCode}"].payDepositExp == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline <#if rules["${platformCode}"].payDepositExp == 0>check<#else></#if>"> <input type="radio" name="payDepositExp" value="0"<#if
											rules["${platformCode}"].payDepositExp == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="payDepositExpCode" value="${rules["${platformCode}"].payDepositExpCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].generateReport == 1>show<#else></#if> ">
								<div class="control-group w235">
									<label class="control-label">报告出结果是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes  <#if rules["${platformCode}"].generateReport == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="generateReport" value="1"<#if rules["${platformCode}"].generateReport == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline  <#if rules["${platformCode}"].generateReport == 0>check<#else></#if>"> <input type="radio" name="generateReport" value="0"<#if
											rules["${platformCode}"].generateReport == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="generateReportCode" value="${rules["${platformCode}"].generateReportCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].clinicRefundSuc == 1>show<#else></#if> ">
								<div class="control-group w235">
									<label class="control-label">门诊退费成功是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes  <#if rules["${platformCode}"].clinicRefundSuc == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="clinicRefundSuc" value="1"<#if rules["${platformCode}"].clinicRefundSuc == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline  <#if rules["${platformCode}"].clinicRefundSuc == 0>check<#else></#if>"> <input type="radio" name="clinicRefundSuc" value="0"<#if
											rules["${platformCode}"].clinicRefundSuc == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="clinicRefundSucCode"
										value="${rules["${platformCode}"].clinicRefundSucCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].clinicPartRefundSuc == 1>show<#else></#if> ">
								<div class="control-group w235">
									<label class="control-label">门诊部分退费成功是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes  <#if rules["${platformCode}"].clinicPartRefundSuc == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="clinicPartRefundSuc" value="1"<#if rules["${platformCode}"].clinicPartRefundSuc == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline  <#if rules["${platformCode}"].clinicPartRefundSuc == 0>check<#else></#if>"> <input type="radio" name="clinicPartRefundSuc" value="0"<#if
											rules["${platformCode}"].clinicPartRefundSuc == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="clinicPartRefundSucCode"
										value="${rules["${platformCode}"].clinicPartRefundSucCode}" />
								</div>
							</div>
							<div class="control-box <#if rules["${platformCode}"].clinicPaySuccessComment == 1>show<#else></#if> ">
                                <div class="control-group w235">
                                    <label class="control-label">门诊缴费成功是否推送就诊评价消息</label>
                                    <div class="controls ">
                                        <label class="radio inline yes  <#if rules["${platformCode}"].clinicPaySuccessComment == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> 
                                        <input type="radio" name="clinicPaySuccessComment" value="1" <#if rules["${platformCode}"].clinicPaySuccessComment == 1>checked="checked"<#else></#if>>是</label>
                                        <label class="radio inline  <#if rules["${platformCode}"].clinicPaySuccessComment == 0>check<#else></#if>"> 
                                        <input type="radio" name="clinicPaySuccessComment" value="0" <#if rules["${platformCode}"].clinicPaySuccessComment == 0>checked="checked"<#else></#if>>否</label>
                                    </div>
                                </div>
                                <div class="control-input">
                                    模版编码<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="clinicPaySucCommentCode" value="${rules["${platformCode}"].clinicPaySucCommentCode}"/>
                                </div>
                            </div>
                            <div class="control-box <#if rules["${platformCode}"].clinicPaySuccessFlower == 1>show<#else></#if> ">
                                <div class="control-group w235">
                                    <label class="control-label">门诊缴费成功是否推送送花评价</label>
                                    <div class="controls ">
                                        <label class="radio inline yes  <#if rules["${platformCode}"].clinicPaySuccessFlower == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> 
                                        <input type="radio" name="clinicPaySuccessFlower" value="1" <#if rules["${platformCode}"].clinicPaySuccessFlower == 1>checked="checked"<#else></#if>>是</label>
                                        <label class="radio inline  <#if rules["${platformCode}"].clinicPaySuccessFlower == 0>check<#else></#if>"> 
                                        <input type="radio" name="clinicPaySuccessFlower" value="0" <#if rules["${platformCode}"].clinicPaySuccessFlower == 0>checked="checked"<#else></#if>>否</label>
                                    </div>
                                </div>
                                <div class="control-input">
                                    模版编码<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="clinicPaySuccessFlowerCode" value="${rules["${platformCode}"].clinicPaySuccessFlowerCode}"/>
                                </div>
                            </div>
                            <div class="control-box">
                                <div class="control-group w235">
                                    <label class="control-label">送花推送的门诊支付时间范围</label>
                                    <div class="controls ">
                                    	从
                                    	<div class="my_select">
                                            <select name="presentFlowerDayFlag" id="presentFlowerDayFlag"  style="width:80px">
                                                <option value="1" <#if rules["${platformCode}"].presentFlowerDayFlag == 1>selected="selected"</#if>>昨天</option>
                                                <option value="2" <#if rules["${platformCode}"].presentFlowerDayFlag == 2>selected="selected"</#if>>当天</option>
                                            </select>
                                        </div>　
                                        <input type="text" class="span2 input33" name="presentFlowerBeginTime" id="presentFlowerBeginTime" 
                                            value="${rules["${platformCode}"].presentFlowerBeginTime?string('HH:mm')}"/> 到当天
                                        <input type="text" class="span2 input33" name="presentFlowerEndTime" id="presentFlowerEndTime" 
                                            value="${rules["${platformCode}"].presentFlowerEndTime?string('HH:mm')}"/>　(例如00:00 - 16:00)
                                    </div>
                                </div>
                            </div>
                            <div class="control-box <#if rules["${platformCode}"].depositRefundAllSuccess == 1>show<#else></#if> ">
                                <div class="control-group w235">
                                    <label class="control-label">住院全额退费成功后是否推送</label>
                                    <div class="controls ">
                                        <label class="radio inline yes  <#if rules["${platformCode}"].depositRefundAllSuccess == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> 
                                        <input type="radio" name="depositRefundAllSuccess" value="1" <#if rules["${platformCode}"].depositRefundAllSuccess == 1>checked="checked"<#else></#if>>是</label>
                                        <label class="radio inline  <#if rules["${platformCode}"].depositRefundAllSuccess == 0>check<#else></#if>"> 
                                        <input type="radio" name="depositRefundAllSuccess" value="0" <#if rules["${platformCode}"].depositRefundAllSuccess == 0>checked="checked"<#else></#if>>否</label>
                                    </div>
                                </div>
                                <div class="control-input">
                                    模版编码<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="depositRefundAllSuccessCode" value="${rules["${platformCode}"].depositRefundAllSuccessCode}"/>
                                </div>
                            </div>
                            <div class="control-box <#if rules["${platformCode}"].depositRefundPartSuccess == 1>show<#else></#if> ">
                                <div class="control-group w235">
                                    <label class="control-label">住院部分退费成功后是否推送</label>
                                    <div class="controls ">
                                        <label class="radio inline yes  <#if rules["${platformCode}"].depositRefundPartSuccess == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> 
                                        <input type="radio" name="depositRefundPartSuccess" value="1" <#if rules["${platformCode}"].depositRefundPartSuccess == 1>checked="checked"<#else></#if>>是</label>
                                        <label class="radio inline  <#if rules["${platformCode}"].depositRefundPartSuccess == 0>check<#else></#if>"> 
                                        <input type="radio" name="depositRefundPartSuccess" value="0" <#if rules["${platformCode}"].depositRefundPartSuccess == 0>checked="checked"<#else></#if>>否</label>
                                    </div>
                                </div>
                                <div class="control-input">
                                    模版编码<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="depositRefundPartSuccessCode" value="${rules["${platformCode}"].depositRefundPartSuccessCode}"/>
                                </div>
                            </div>
                            <div class="control-box <#if rules["${platformCode}"].takeNoSuccess == 1>show<#else></#if> ">
                                <div class="control-group w235">
                                    <label class="control-label">取号成功是否推送</label>
                                    <div class="controls ">
                                        <label class="radio inline yes  <#if rules["${platformCode}"].takeNoSuccess == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> 
                                        <input type="radio" name="takeNoSuccess" value="1" <#if rules["${platformCode}"].takeNoSuccess == 1>checked="checked"<#else></#if>>是</label>
                                        <label class="radio inline  <#if rules["${platformCode}"].takeNoSuccess == 0>check<#else></#if>"> 
                                        <input type="radio" name="takeNoSuccess" value="0" <#if rules["${platformCode}"].takeNoSuccess == 0>checked="checked"<#else></#if>>否</label>
                                    </div>
                                </div>
                                <div class="control-input">
                                    模版编码<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="takeNoSuccessCode" value="${rules["${platformCode}"].takeNoSuccessCode}"/>
                                </div>
                            </div>
							<#-- <div class="control-box <#if rules["${platformCode}"].sendComment == 1>show<#else></#if> ">
								<div class="control-group w235">
									<label class="control-label">发表评价后是否推送消息</label>
									<div class="controls ">
										<label class="radio inline yes  <#if rules["${platformCode}"].sendComment == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="sendComment" value="1"<#if rules["${platformCode}"].sendComment == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline  <#if rules["${platformCode}"].sendComment == 0>check<#else></#if>"> <input type="radio" name="sendComment" value="0"<#if
											rules["${platformCode}"].sendComment == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="sendCommentCode" value="${rules["${platformCode}"].sendCommentCode}" />
								</div>
							</div> -->

							<div class="control-box <#if rules["${platformCode}"].finishUserInfo == 1>show<#else></#if> ">
								<div class="control-group w235">
									<label class="control-label">完成注册后是否推送完善资料提示</label>
									<div class="controls ">
										<label class="radio inline yes  <#if rules["${platformCode}"].finishUserInfo == 1>check<#else></#if>" style="margin-right: 50px; display: inline;"> <input type="radio"
												name="finishUserInfo" value="1"<#if rules["${platformCode}"].finishUserInfo == 1>checked="checked"<#else></#if>>是
										</label> <label class="radio inline  <#if rules["${platformCode}"].finishUserInfo == 0>check<#else></#if>"> <input type="radio" name="finishUserInfo" value="0"<#if
											rules["${platformCode}"].finishUserInfo == 0>checked="checked"<#else></#if>>否
										</label>
									</div>
								</div>
								<div class="control-input">
									模版编码
									<input type="text" placeholder="" class="span5 input33" style="margin-left: 15px; display: inline;" name="finishUserInfoCode" value="${rules["${platformCode}"].finishUserInfoCode}" />
								</div>
							</div>

						</div>
					</div>
					<div class="space20"></div>
					<button class="btn btn-save" type="button" onclick="ruleJS.saveRule('editRuleForm${platforms["${platformCode}"].platformCode}' , 'RulePush')">保存</button>
				</form>
			</div>
			</#list>
		</div>
	</div>
	</div>
	<!--content end-->
</body>
</html>
<script type="text/javascript">
	$(document).on('click', '.js_yes', function() {
		if ($(this).hasClass('check')) {
			$(this).parents('.control-box ').addClass('show')
		}
	});
	$('.tabs li a').click(function() {
		$(this).addClass('select');
		$(this).parent().siblings().children().removeClass('select');
	});
	/*选中 是。 显示 模版编码*/
	$(document).on('click', 'input[type=radio]', function() {
		var p = $(this).parents('label');
		var pc = $(this).parents('.yes');
		var ps = $(this).parents('.control-box');
		var name = $(this).attr('name');
		$('form input[name=' + name + ']').parents('label').removeClass('check');
		p.addClass('check');
		if (pc.hasClass('check')) {
			ps.addClass('show');
		} else {
			ps.removeClass('show');
		}
	});
	$(document).ready(function() {
		//Default Action
		$(".tab_content").hide(); //Hide all content
		$("ul.tabs li:first").addClass("active").show(); //Activate first tab
		$(".tab_content:first").show(); //Show first tab content
		//On Click Event
		$("ul.tabs li").click(function() {
			$("ul.tabs li").removeClass("active"); //Remove any "active" class
			$(this).addClass("active"); //Add "active" class to selected tab
			$(".tab_content").hide(); //Hide all tab content
			var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content
			$(activeTab).fadeIn(); //Fade in the active content
			return false;
		});
	});
</script>