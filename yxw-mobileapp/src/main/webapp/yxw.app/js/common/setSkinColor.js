/**
 * Created by nick on 2017/3/13.
 */

function setSkinColor(value) {
    var skinColor = value||'#38c2a0';
    var head = document.querySelector('head');
    var style = document.createElement('STYLE')
    style.innerHTML = '' +
        '.dateView{color:'+skinColor+'!important}' +
        '.skinColor,.addPeople{color:'+skinColor+'} ' +
        '.skinColor,.addPeople:active{color:'+skinColor+'} ' +
        '.skinBgColor{background-color:'+skinColor+'} ' +
        '.ui-menu-footer .flex>a:active, .ui-menu-footer .flex>a.active,.ui-menu-footer .flex>a:active i, .ui-menu-footer .flex>a.active i{color:'+skinColor+'} ' +
        '.btn-w .btn,.btn-ok,.btn-bingka{background-color:'+skinColor+';border-color:'+skinColor+'}'+
        '.btn-w .btn:hover,.btn-ok:hover,.btn-bingka:hover{background-color:'+skinColor+';border-color:'+skinColor+';opacity: .6;}' +
        '.radio-list .yx-list li.addPeople .iconfont,.radio-list .yx-list li.active .icon-radio:before,.radio-list .yx-list li.active .icon-radio, .radio-list .yx-list li.active .flexItem, .radio-list .yx-list li.active .title{color:'+skinColor+'}' +
        '.mui-switch:checked{    border-color: '+skinColor+'; box-shadow: '+skinColor+' 0 0 0 16px inset; background-color: '+skinColor+';}' +
        '#cardManagement-discoveryList .hospital-appointment .add{background-color: '+skinColor+';}'+
        '#select-date .border-arrow{ border: 1px solid '+ skinColor +';}'+
        '#select-date .active .border-arrow .iconfont::after,#select-date .active .week, #select-date .active .date,#select-date .d-title .iconfont::after,#select-date .d-title.up .iconfont::after {color: '+ skinColor +'}'+
        '.search-view .btn-seaech ,.ks-list-left ul li.active,.ks-list-right.second #right-list-main ul li.hover{color: '+ skinColor +'}'+
        '#left-ks_arrow,#ks-left-list,.hospital-list li .info .tag .styleTag,#index2 .index2-topNav{ background-color:  '+ skinColor +';}'+
        '.register-wrap .register-list .code-btn, .mod-forgetpwd .register-list .code-btn, .register-wrap .register-list .select-box-text, .mod-forgetpwd .register-list .select-box-text{ background-color:  '+ skinColor +';}'+
        '.register-wrap .register-list .code-btn:hover, .mod-forgetpwd .register-list .code-btn:hover, .register-wrap .register-list .select-box-text:hover, .mod-forgetpwd .register-list .select-box-text:hover{ background-color:  '+ skinColor +';opacity: .6;}'+
        '.register-wrap .register-btn span, .mod-forgetpwd .register-btn span,.loginBox .btn-w .btn-ok,.loginBox .register button.btn-ok,.noticeBtn .btn1{ background-color:  '+ skinColor +';border-color:'+skinColor+';}'+
        '.register-wrap .register-btn span:hover, .mod-forgetpwd .register-btn span:hover,.loginBox .btn-w .btn-ok:hover,.loginBox .register button.btn-ok:hover,.noticeBtn .btn1:hover{ background-color:  '+ skinColor +';border-color:'+skinColor+';opacity: .6;}'+
        '.register-wrap .register-list .password-show-icon, .mod-forgetpwd .register-list .password-show-icon,.noticeTip,.loginBox .frm-group .icon-login{ color:'+ skinColor +';}'+
        '.headerSelectNav i.iconfont,.yx-list.fourList li .opt-view,.search-zoom-btn i{ color:'+ skinColor +';}'+
        '.headerSelectNav .js-item.hover,#payRecord .pay-tips,.search-appointment .yuyue{   background-color:'+ skinColor +';}'+
        '.js-tag .tag.active{ color:'+ skinColor +'; border-bottom: 3px solid '+ skinColor +';}'+
        '.search-select .search-select-btn,.search-select .search-select-option,.search-select .search-option-box i,.search-select .search-select-btn,.btn3kmRefresh i.green{ color:'+ skinColor +';}'+
        '#threemRange-discoveryList .hospital-appointment .guahao{ background-color:'+ skinColor +';}'+
        '#threemRange-discoveryList .hospital-appointment .guahao:hover{ background-color:'+ skinColor +';opacity: .6;}'+
        '.optBtn .btn2-ok,.yx-list li .green.textRight,.yx-list li .green.iconfont,.check-list .yx-list li .checkBoxMask:checked,.check-list .yx-list li .checkBoxMask:checked:before,.triage-view .view-row .btn-w.btn-box .btn-ok { color:'+ skinColor +';}'+
        '.btn-refresh{border:1px solid '+ skinColor +';color:'+ skinColor +';}'+
        '.btn-refresh:hover{border:1px solid '+ skinColor +';background-color:'+ skinColor +';opacity: .6;}'+
        '.codeBtn,#yx-select-card-id .view{background-color:'+ skinColor +';}'+
        '.codeBtn:hover,#yx-select-card-id .view:hover{background-color:'+ skinColor +';opacity: .6;}'+
        '.btn-async{border:1px solid '+ skinColor +';background-color:'+ skinColor +';}'+
        '.btn-async:hover{border:1px solid '+ skinColor +';background-color:'+ skinColor +';opacity: .6;}'+
        '.btnLabel{background-color:'+ skinColor +'; }'+
        '.houzhen-wrap .houzhen-infoList .values.green,.houzhen-wrap .houzhen-infoList .values span{color:'+ skinColor +';}'+
        '#payRecord .pay-tips i.icon-triangle-dark{border-left-color:'+ skinColor +';opacity: .4;}'+
        '.ks-list-left{background-color: '+ skinColor +'}'+
        '.mbsc-mobiscroll .mbsc-cal .mbsc-cal-sc-sel .mbsc-cal-sc-cell-i, .mbsc-mobiscroll .mbsc-cal .mbsc-cal-day-sel .mbsc-cal-day-i{background-color:'+ skinColor +'}'+
        '.mbsc-mobiscroll .mbsc-cal-btn-txt,.mbsc-mobiscroll .mbsc-cal-days,.mbsc-mobiscroll .mbsc-fr-btn{color:'+ skinColor +'}'+
        '.mbsc-mobiscroll .mbsc-fr-btn-a{background-color:'+ skinColor +';color:#fff}'+
        '.mbsc-mobiscroll .mbsc-cal-days th{border-color:'+ skinColor +'}'+
        '.ui-list-radio .active{color:'+ skinColor +'}'+
        '.ui-list-radio .active .icon-radio{border-color:'+ skinColor +'}'+
        '.ui-list-radio .active .icon-radio:after{background-color:'+ skinColor +';}'+

        head.appendChild(style)
}
