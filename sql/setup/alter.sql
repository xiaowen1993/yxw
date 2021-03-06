---------添加银联钱包openId 关联字段---------
ALTER TABLE `EH_USER_1` ADD COLUMN `UNIONPAY_ID` varchar(100) AFTER `WECHAT_ID`;
ALTER TABLE `EH_USER_2` ADD COLUMN `UNIONPAY_ID` varchar(100) AFTER `WECHAT_ID`;
ALTER TABLE `EH_USER_3` ADD COLUMN `UNIONPAY_ID` varchar(100) AFTER `WECHAT_ID`;
ALTER TABLE `EH_USER_4` ADD COLUMN `UNIONPAY_ID` varchar(100) AFTER `WECHAT_ID`;
ALTER TABLE `EH_USER_5` ADD COLUMN `UNIONPAY_ID` varchar(100) AFTER `WECHAT_ID`;
ALTER TABLE `EH_USER_6` ADD COLUMN `UNIONPAY_ID` varchar(100) AFTER `WECHAT_ID`;
ALTER TABLE `EH_USER_7` ADD COLUMN `UNIONPAY_ID` varchar(100) AFTER `WECHAT_ID`;
ALTER TABLE `EH_USER_8` ADD COLUMN `UNIONPAY_ID` varchar(100) AFTER `WECHAT_ID`;
ALTER TABLE `EH_USER_9` ADD COLUMN `UNIONPAY_ID` varchar(100) AFTER `WECHAT_ID`;
ALTER TABLE `EH_USER_10` ADD COLUMN `UNIONPAY_ID` varchar(100) AFTER `WECHAT_ID`;


------规则配置-全局配置-修改------
ALTER TABLE `SYS_RULE_EDIT` 
ADD COLUMN `CUR_DAY_VISIT_NOTICE_PAY_TYPE` int(1) DEFAULT '0' COMMENT '就诊通知-当天就诊推送的支付类型订单 0.已支付 1.全部',
ADD COLUMN `PRE_DAY_VISIT_NOTICE_PAY_TYPE` int(1) DEFAULT '0' COMMENT '就诊通知-提前一天的就诊通知推送的支付类型订单 0.已支付 1.全部',
ADD COLUMN `STOPREG_NEED_INVOKE_ACKREFUND` int(1) DEFAULT '0' COMMENT '停诊是否调用退费确认接口  0-不调用    1-调用',
ADD COLUMN `OVER_BEGINTIME_BANSTOPREG` int(1) DEFAULT '1' COMMENT '停诊是否限制超过就诊开始时间无法停诊 0-不限制 1-限制',
ADD COLUMN `OPEN_TEMPLATE_MSG_PUSH` int(1) DEFAULT '1' COMMENT '是否开放模版消息推送接口【对外】',
ADD COLUMN `HIGH_FREQUENCY_STOPREG` int(1) DEFAULT '0' COMMENT '是否开启高频停诊 0 否 1 是',
ADD COLUMN `HIGH_FREQUENCY_STOPREG_DAYNUM` int(2) DEFAULT '0' COMMENT '高频停诊查询天数',
ADD COLUMN `REFUND_WAITING_SECONDS` int(10) DEFAULT '180' COMMENT 'his支付失败退费等待时间',

ADD COLUMN `ACCEPT_REPLACE_REG_INFO_TYPE` int(11) DEFAULT '0' COMMENT '替诊信息方式:0：医院his主动推送1：标准平台轮询查询',
ADD COLUMN `REPLACE_REG_PUSH_INFO_TIME` time DEFAULT NULL COMMENT '推送/查询替诊信息时间',
ADD COLUMN `REPLACE_REG_PUSH_INFO_DAY` int(11) DEFAULT NULL COMMENT '替诊查询天数',

CHANGE COLUMN `PUSH_INFO_TIME` `PUSH_INFO_TIME` varchar(255) DEFAULT NULL COMMENT '推送/查询停诊信息时间';

------规则配置-绑卡配置-修改------
ALTER TABLE `SYS_RULE_TIED_CARD`
ADD COLUMN `INPUT_CARD_NO_TIP`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卡号输入框的提示内容' ,
ADD COLUMN `INPUT_CARD_TYPE_REMARK`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卡类型的备注，对应的卡类型备注不为空的时候，显示备注内容' ,
ADD COLUMN `IS_UNBIND_RESTRICTED`  int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '是否限制解绑 0不限制 1限制' ,
ADD COLUMN `UNBIND_RESTRICTED_DAY_NUM`  int(3) NULL DEFAULT 30 COMMENT '绑定后第N天才能解绑',
ADD COLUMN `UNBIND_RESTRICTED_TIP`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '解绑限制提示语';

------规则配置-门诊缴费规则配置-修改------
ALTER TABLE `SYS_RULE_CLINIC`
ADD COLUMN `IS_BASEDON_MEDICAL_INSURANCE`  int(1) NULL DEFAULT 0 ,
ADD COLUMN `IS_SUPPORT_FORBIDDEN_PAYMENT`  int(1) NULL DEFAULT 0 COMMENT '是否支持不允许支付订单' ,
ADD COLUMN `HOSPITAL_DEPOSIT_MAX_MONEY`  double NULL DEFAULT NULL COMMENT '住院押金补缴最高缴费金额' ,
ADD COLUMN `HOSPITAL_DEPOSIT_PAYMENTS_TIP`  varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住院押金补缴提示语' ,
ADD COLUMN `SUPPORT_FORBIDDEN_PAYMENT_TIPS`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊支持不允许支付的提示' ,
ADD COLUMN `OUTPATIENT_SERVICE_PAY_TIPS`  varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门诊代缴费提示语' ,
ADD COLUMN `CLEAR_ACCOUNT_TIP`  varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '清账提示语' ,
ADD COLUMN `CLEAR_ACCOUNT_PRE_ORDER_TIP`  varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '清账下单前提示语' ,
ADD COLUMN `SHOW_CLINIC_PAY_DETAIL_STYLE`  int(1) NULL DEFAULT 0 COMMENT '0按明细，1按费别' ,
ADD COLUMN `PRESETTLE_STYLE`  int(1) NULL DEFAULT 0 COMMENT '门诊预结算样式' ,
ADD COLUMN `CONFIRM_TIP_MEDICARE_WECHAT`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '确认缴费弹框提示语' ,
ADD COLUMN `IS_CONFIRM_TIP_MEDICARE_WECHAT`  int(1) NULL DEFAULT 0 COMMENT '确认缴费是否弹框 0-否 1-是' ,
ADD COLUMN `CONFIRM_TIP_MEDICARE_ALIPAY`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
ADD COLUMN `IS_CONFIRM_TIP_MEDICARE_ALIPAY`  int(1) NULL DEFAULT 0 ,
ADD COLUMN `CONFIRM_TIP_SELFPAY_WECHAT`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
ADD COLUMN `IS_CONFIRM_TIP_SELFPAY_WECHAT`  int(1) NULL DEFAULT 0 ,
ADD COLUMN `CONFIRM_TIP_SELFPAY_ALIPAY`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
ADD COLUMN `IS_CONFIRM_TIP_SELFPAY_ALIPAY`  int(1) NULL DEFAULT 0 ,
ADD COLUMN `REFUND_DELAY_AFTER_EXCEPTION`  int(11) NULL DEFAULT NULL COMMENT 'his支付异常发生后的退费延时时间(分钟）' ;

------规则配置-挂号配置-修改------
ALTER TABLE `SYS_RULE_REGISTER` 
ADD COLUMN `IS_BASEDON_MEDICAL_INSURANCE_TODAY` int(11) DEFAULT '0' COMMENT '挂号是否走医保流程  1：是  0：否',
ADD COLUMN `IS_BASEDON_MEDICAL_INSURANCE_APPOINT` int(11) DEFAULT NULL,
ADD COLUMN `CUSTOM_CALENDAR_DAYS` int(11) DEFAULT NULL COMMENT '自定义的预约挂号日历显示天数\r\n当且仅当 CALENDAR_DAYS_TYPE 等于 -1 时有效',
ADD COLUMN `APPOINTMENT_PAYMENT_CONTROL` int(11) DEFAULT NULL COMMENT '预约支付控制  :1-必须支付    2-不支付     3-暂不支付',
ADD COLUMN `IS_VIEW_POPULATION_TYPE` int(1) DEFAULT NULL COMMENT '是否需要显示人群类型  0不显示   1显示',
ADD COLUMN `POPULATION_TYPE` varchar(50) DEFAULT NULL COMMENT '人群类型  本地-1  外地 -2',
ADD COLUMN `IS_VIEW_APPOINTMENT_TYPE` int(1) DEFAULT NULL COMMENT '是否需要显示预约类型  0不显示  1显示',
ADD COLUMN `APPOINTMENT_TYPE` varchar(50) DEFAULT NULL COMMENT '预约类型:\r\n一般1\r\n出院后复查 2\r\n社区转诊 3\r\n术后复查 4\r\n产前检查 5',
ADD COLUMN `IS_VIEW_REGFEE` int(1) DEFAULT NULL COMMENT '是否显示挂号费',
ADD COLUMN `IS_SUPPORT_REFUND_APPOINTMENT` int(1) DEFAULT NULL COMMENT '预约是否支持退费',
ADD COLUMN `IS_VIEW_SOURCE_NUM` int(1) DEFAULT NULL COMMENT '是否显示剩余号源数',
ADD COLUMN `IS_QUERY_PATIENT_TYPE` int(1) DEFAULT NULL COMMENT '是否查询患者类型',
ADD COLUMN `UN_PAID_REG_DAYS` int(3) DEFAULT NULL COMMENT '跨平台天数,-1为无限制',
ADD COLUMN `IS_EXCEPTION_REFUNDE_ORDER` int(1) DEFAULT '0' COMMENT '是否允许异常订单退费',
ADD COLUMN `CHOOSE_DOCTOR_STYLE` int(1) DEFAULT '1' COMMENT '挂号：选择医生页面样式',
ADD COLUMN `SERIAL_NUM_TIP` varchar(250) DEFAULT NULL COMMENT '排队号提示',
ADD COLUMN `REG_CANCEL_MAXNUM_IN_MONTH` int(11) DEFAULT NULL COMMENT '超过每月允许取消挂号次数上限，则无法挂号(挂号时)',
ADD COLUMN `OVER_CANCEL_MAXNUM_IN_MONTH_TIP` varchar(250) DEFAULT NULL COMMENT '超过每月允许取消挂号次数上限，则无法挂号提示语(挂号时)',
ADD COLUMN `WILL_REACH_CANCEL_MAXNUM_IN_DAY_TIP` varchar(250) DEFAULT NULL COMMENT '当达到每天取消次数上限时提醒信息(取消时)',
ADD COLUMN `WILL_REACH_CANCEL_MAXNUM_IN_MONTH_TIP` varchar(250) DEFAULT NULL COMMENT '当达到每月取消次数上限时提醒信息(取消时)',
ADD COLUMN `HAD_REACH_CANCEL_MAXNUM_IN_DAY_CANCEL_TIP` varchar(250) DEFAULT NULL COMMENT '当超过每天取消次数上限时提醒信息(取消时)',
ADD COLUMN `HAD_REACH_CANCEL_MAXNUM_IN_MONTH_CANCEL_TIP` varchar(250) DEFAULT NULL COMMENT '当超过每月取消次数上限时提醒信息(取消时)',
ADD COLUMN `PATIENT_TYPE_TIP` varchar(250) DEFAULT NULL COMMENT '患者类型查询提示语',
ADD COLUMN `IS_VIEW_SOURCE_NUM_REG` int(1) DEFAULT '1' COMMENT '预约号源页面是否显示剩余号源数',
ADD COLUMN `SHOW_REG_PERIOD` int(1) DEFAULT '1' COMMENT '是否显示就诊时间段',
ADD COLUMN `DOCTOR_TITLE_ORDER` varchar(250) DEFAULT NULL COMMENT '医生职称排序',
ADD COLUMN `IS_ORDERBY_DOCTOR_TITLE` int(1) unsigned zerofill DEFAULT '0' COMMENT '是否按照医生职称排序(1是0否)',
ADD COLUMN `TAKENO_NEEDPAY_TIP` varchar(250) DEFAULT NULL,
ADD COLUMN `TAKENO_UNTIMELY_TIP` varchar(250) DEFAULT NULL,
ADD COLUMN `TAKENO_DETAIL_TIP` varchar(250) DEFAULT NULL,
ADD COLUMN `CONFIRM_TIP_MEDICARE_WECHAT` varchar(1000) DEFAULT NULL COMMENT '确认挂号弹框提示语',
ADD COLUMN `IS_CONFIRM_TIP_MEDICARE_WECHAT` int(1) DEFAULT '0' COMMENT '确认挂号是否弹框 0-否 1-是',
ADD COLUMN `CONFIRM_TIP_MEDICARE_ALIPAY` varchar(1000) DEFAULT NULL,
ADD COLUMN `IS_CONFIRM_TIP_MEDICARE_ALIPAY` int(1) DEFAULT '0',
ADD COLUMN `CONFIRM_TIP_SELFPAY_WECHAT` varchar(1000) DEFAULT NULL,
ADD COLUMN `IS_CONFIRM_TIP_SELFPAY_WECHAT` int(1) DEFAULT '0',
ADD COLUMN `CONFIRM_TIP_SELFPAY_ALIPAY` varchar(1000) DEFAULT NULL,
ADD COLUMN `IS_CONFIRM_TIP_SELFPAY_ALIPAY` int(1) DEFAULT '0',
ADD COLUMN `REFUND_DELAY_AFTER_EXCEPTION` int(11) DEFAULT NULL COMMENT 'his支付异常发生后的退费延时时间(分钟）';

------规则配置-查询配置-修改------
ALTER TABLE `SYS_RULE_QUERY`
ADD COLUMN `ONE_DAY_RECORD_IS_ITEMS` int(2) DEFAULT NULL COMMENT '住院一日清单是否有大项  （1:有 0：无）',
ADD COLUMN `CHOICES_INSPECTION_REPORT_STYLE` int(1) DEFAULT NULL COMMENT '检验报告详情样式选择(1.样式一  0.样式二)',
ADD COLUMN `SHOW_CLINIC_PAID_DETAIL_STYLE` int(1) DEFAULT '0' COMMENT '门诊已缴费详情显示样式（0按明细1按费别）',
ADD COLUMN `TIP` varchar(1000) DEFAULT NULL COMMENT '报告查询温馨提示',
ADD COLUMN `SHARE_QUERY_TIMES_LIMIT` int(11) DEFAULT NULL COMMENT '是否所有类型的报告查询共享次数限制 1:是 2:否',
ADD COLUMN `MAX_QUERY_TIMES_PER_DAY` int(11) DEFAULT NULL COMMENT '每日最大查询次数',
ADD COLUMN `REACH_MAX_QUERY_TIMES_PER_DAY_TIP` varchar(1000) DEFAULT NULL COMMENT '达到每日最大查询次数提示语',
ADD COLUMN `MAX_QUERY_TIMES_PER_WEEK` int(11) DEFAULT NULL COMMENT '每周最大查询次数',
ADD COLUMN `REACH_MAX_QUERY_TIMES_PER_WEEK_TIP` varchar(1000) DEFAULT NULL COMMENT '达到每周最大查询次数提示语';

------规则配置-代煎配送规则配置-新增------
DROP TABLE IF EXISTS `SYS_RULE_FRIED_EXPRESS`;
CREATE TABLE `SYS_RULE_FRIED_EXPRESS` (
  `ID` varchar(32) NOT NULL,
  `HOSPITAL_ID` varchar(32) DEFAULT NULL,
  `IS_ACCESS_CLINIC` int(1) DEFAULT NULL COMMENT '门诊缴费是否接入代煎配送;1是,0否',
  `IS_ACCESS_PAYED` int(1) DEFAULT NULL COMMENT '缴费记录是否接入代煎配送;1是,0否',
  `IS_CAN_OFF_BY_USER` int(1) DEFAULT NULL COMMENT '用户是否可关闭代煎配送;1是,0否',
  `IS_ONLY_EXPRESS` int(1) DEFAULT NULL COMMENT '是否只设置配送信息;1是,0否',
  `IS_SPLIT_RECIPE` int(1) DEFAULT NULL COMMENT '是否要对不同的处方单设置代煎配送;1是,0否',
  `IS_FRIED_QUERY` int(1) DEFAULT NULL COMMENT '代煎配置是否要查询接口;1是,0否',
  `IS_EXPRESS_QUERY` int(1) DEFAULT NULL COMMENT '配送配置是否要查询接口;1是,0否',
  `FRIED_EXPRESS_RELATION` int(1) DEFAULT NULL COMMENT '代煎与配送的关系;1不限制,2代煎必须配送,3代煎可以不配送',
  `TIP_CONTENT` varchar(500) DEFAULT NULL COMMENT '温馨提示',
  `CP` varchar(32) DEFAULT NULL,
  `CT` datetime DEFAULT NULL,
  `EP` varchar(32) DEFAULT NULL,
  `ET` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_HOSPITAL_ID` (`HOSPITAL_ID`) USING BTREE,
  CONSTRAINT `FK_RULE_FRIED_EXPRESS_REF_HS` FOREIGN KEY (`HOSPITAL_ID`) REFERENCES `SYS_HOSPITAL` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

------规则配置-住院规则配置-新增------
DROP TABLE IF EXISTS `SYS_RULE_INHOSPITAL`;
CREATE TABLE `SYS_RULE_INHOSPITAL` (
  `ID` varchar(32) NOT NULL,
  `HOSPITAL_ID` varchar(32) DEFAULT NULL,
  `IF_BIND_NEED_ID_CARD` int(1) DEFAULT '1' COMMENT '住院信息绑定是否需要住院人身份证  1：是 0：否',
  `IF_BIND_NEED_ADMISSION_NO` int(1) DEFAULT '1' COMMENT '住院信息绑定是否需要住院号 0-不需要 1-需要',
  `HAS_BRANCH` int(1) DEFAULT '0' COMMENT '是否有分院 0-无 1-有',
  `ISTIP` int(1) DEFAULT '0',
  `TIPCONTENT` varchar(2000) DEFAULT NULL,
  `CP` varchar(32) DEFAULT NULL,
  `CT` datetime DEFAULT NULL,
  `EP` varchar(32) DEFAULT NULL,
  `ET` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

------规则配置-门诊缴费规则配置-删除字段------
ALTER TABLE `SYS_RULE_CLINIC`
DROP COLUMN `HOSPITAL_DEPOSIT_MIN_MONEY`,
DROP COLUMN `HOSPITAL_DEPOSIT_MAX_MONEY`;

------规则配置-住院规则配置-新增字段------
ALTER TABLE `SYS_RULE_INHOSPITAL`
ADD COLUMN `HOSPITAL_DEPOSIT_MAX_MONEY` double NULL DEFAULT NULL COMMENT '住院押金补缴最高缴费金额' ,
ADD COLUMN `HOSPITAL_DEPOSIT_MIN_MONEY` double NULL DEFAULT NULL COMMENT '住院押金补缴最低缴费金额';
