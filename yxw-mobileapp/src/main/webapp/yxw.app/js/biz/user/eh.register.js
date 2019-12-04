/**
 * zhoujb
 * 2015-10-06 
 */

$(function() {
	
});

var myBox = null;
var verifyCodeSended = false;

function sendVerifyCodeByForgetpwd(obj, codeType) {
  var account = jQuery.trim(jQuery("#account").val());
  
  var re = /^1\d{10}$/;
  if(!idCardUtils.validateIdNo(account) && !re.test(account)){
      myBox = new $Y.confirm({
          title:"",
          content:"<div style='text-align: center;'>请输入正确的手机号</div>",
          ok:{title:"确定",
              click:function(){ 
                  myBox.close();
              }
          }
      });
      return;
  }
  
  sendVerifyCode(obj, codeType, account);
}

/**
 * 发送验证吗
 */
function sendVerifyCode(obj, codeType, account){
    if (verifyCodeSended) {
      return;
    }
	var reqUrl = appPath + "easyhealth/user/sendcode";
	if (!account) {
	  account = jQuery("#account").val();
	}
	var re = /^1\d{10}$/;
	  if(!idCardUtils.validateIdNo(account) && !re.test(account)){
	      myBox = new $Y.confirm({
	          title:"",
	          content:"<div style='text-align: center;'>请输入正确的手机号</div>",
	          ok:{title:"确定",
	              click:function(){ 
	                  myBox.close();
	              }
	          }
	      });
	      return;
	  }
	
	//$Y.loading.show();
	verifyCodeSended = true;
    var btn = $(obj), time = 60, timer = null;
	
	clearInterval(timer);
    btn.html('发送中...');
    btn.addClass('doing');
    btn.unbind('click');

    _toggleBtn = function() {
        timer = setInterval(function () {
            if (time >= 1) {
                btn.html(time + '秒后重试');
                time--;
            } else {
                clearInterval(timer);
                btn.html('发送验证码');
                btn.removeClass('doing');
                time = 60;
                btn.bind('click');
                verifyCodeSended = false;
            }
        }, 1000)
    }
	
	var datas = {account:account,codeType:codeType};
	_toggleBtn();
	$.ajax({  
        type: 'POST',  
        url: reqUrl,  
        data: datas,  
        dataType: 'json',  
        timeout: 120000,
        error: function(XMLHQ, errorMsg) {
            
        },
        success: function(result) {
            //$Y.loading.hide();
        	if(result.status == 'OK'){
//        		myBox = new $Y.confirm({
//        			title:"",
//        			content:"<div style='text-align: center;'>" + result.message + "</div>",
//        			ok:{title:"确定",
//        				click:function(){ 
//        					myBox.close();
//        				}
//        			}
//        		});
        	}else{
        	    clearInterval(timer);
        	    btn.html('发送验证码');
        	    btn.removeClass('doing');
        	    verifyCodeSended = false;
        		myBox = new $Y.confirm({
        			title:"",
        			content:"<div style='text-align: center;'>" + result.message + "</div>",
        			ok:{title:"确定",
        				click:function(){
        					myBox.close();
        				}
        			}
        		});
        	}
        }
	 });
}

/**
 * 注册
 * @returns
 */
function register(){
//  var name = jQuery.trim(jQuery("#name").val());
//	if(name == ''){
//		myBox = new $Y.confirm({
//			title:"",
//			content:"<div style='text-align: center;'>请输入姓名!</div>",
//			ok:{title:"确定",
//				click:function(){ 
//					myBox.close();
//				}
//			}
//		});
//		return;
//	}
//	
//	if (!/^[\u0391-\uFFE5]{2,}$/.test(name)) {
//	  myBox = new $Y.confirm({
//          title:"",
//          content:"<div style='text-align: center;'>请输入至少两个字的中文姓名</div>",
//          ok:{title:"确定",
//              click:function(){ 
//                  myBox.close();
//              }
//          }
//      });
//      return;
//	}
//	
//	var cardNo = jQuery.trim(jQuery("#cardNo").val());
//	if(!idCardUtils.validateIdNo(cardNo)){
//		myBox = new $Y.confirm({
//			title:"",
//			content:"<div style='text-align: center;'>请输入正确的证件号码!</div>",
//			ok:{title:"确定",
//				click:function(){ 
//					myBox.close();
//				}
//			}
//		});
//		return;
//	}
	
	var mobile = jQuery.trim(jQuery("#account").val());
	var re = /^1\d{10}$/;
	if (!re.test(mobile)){
		myBox = new $Y.confirm({
			title:"",
			content:"<div style='text-align: center;'>请输入正确的手机号码!</div>",
			ok:{title:"确定",
				click:function(){
					myBox.close();
				}
			}
		});
		return;
	}
	/*if(jQuery.trim(jQuery("#mobile").val()) == ''){
		myBox = new $Y.confirm({
			title:"",
			content:"<div style='text-align: center;'>请输入手机号码!</div>",
			ok:{title:"确定",
				click:function(){ 
					myBox.close();
				}
			}
		});
		return;
	}*/
	var verifyCode = jQuery.trim(jQuery("#verifyCode").val());
	if(verifyCode == ''){
		myBox = new $Y.confirm({
			title:"",
			content:"<div style='text-align: center;'>请输入验证码!</div>",
			ok:{title:"确定",
				click:function(){ 
					myBox.close();
				}
			}
		});
		return;
	}
		
	var passWord = jQuery.trim(jQuery("#passWord").val());
	if(passWord == ''){
		myBox = new $Y.confirm({
			title:"",
			content:"<div style='text-align: center;'>请输入密码!</div>",
			ok:{title:"确定",
				click:function(){ 
					myBox.close();
				}
			}
		});
		return;
	}
	
	re = /[a-zA-Z0-9]{6,16}/;
	if (!re.test(passWord)){
	  myBox = new $Y.confirm({
	    title:"",
	    content:"<div style='text-align: center;'>请输入正确格式密码,6-12位!</div>",
	    ok:{title:"确定",
	      click:function(){ 
	        myBox.close();
	      }
	    }
	  });
	  return;
	}
	
	var confirmPassWord = jQuery.trim(jQuery("#confirmPassWord").val());
	if(confirmPassWord == ''){
		myBox = new $Y.confirm({
			title:"",
			content:"<div style='text-align: center;'>请输入确认密码!</div>",
			ok:{title:"确定",
				click:function(){ 
					myBox.close();
				}
			}
		});
		return;
	}
	
	if(passWord != confirmPassWord){
		myBox = new $Y.confirm({
			title:"",
			content:"<div style='text-align: center;'>两次密码不一致!</div>",
			ok:{title:"确定",
				click:function(){ 
					myBox.close();
				}
			}
		});
		return;
	}
	
	$Y.loading.show();
	var datas = {verifyCode:verifyCode,account:mobile,codeType:"register"};
	var url = appPath + "easyhealth/user/checkRegisterInfo";
	$.ajax({  
        type : 'POST',  
        url  : url,  
        data : datas,  
        dataType : 'json',
        timeout  : 120000,
        success  : function(data) {
            console.log("checkRegisterInfo.data:");
            console.log(data);
            $Y.loading.hide();
            if(data.status == 'OK'){
            	 reqUrl = appPath + "easyhealth/user/register";
            	 datas = $("#paramsForm").serializeArray();
            	 console.log(datas);
            	 $.ajax({  
        	        type : 'POST',  
        	        url  : reqUrl,  
        	        data : datas,  
        	        dataType : 'json',  
        	        timeout  : 120000,
        	        success  : function(result) {
        	        	if(result.status == 'OK'){
//            	        	myBox = new $Y.confirm({
//                                title:"",
//                                content:"<div style='text-align: center;'>注册成功</div>",
//                                ok:{title:"确定",
//                                    click:function(){
//                                        myBox.close();
//                                        go(appPath + "easyhealth/index",true);//登录成功跳转至首页
//                                    }
//                                }
//                            });
        	        	  
        	        	    $Y.tips('注册成功，正在自动登录···',2000);
        	        	    setTimeout(function() {
        	        	      go(appPath + "easyhealth/index?appCode="+base.appCode+"&areaCode="+base.areaCode,true);//登录成功跳转至首页
        	        	    },1500);
        	        		
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
        	        }
            	 });
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
         }, error : function(data) {
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
	});
}

/**
 * 忘记密码页面，输入验证码后的修改密码页面
 */
function forgetpwdSendCodeNext(obj){
    var account = jQuery.trim(jQuery("#account").val());
    
    var re = /^1\d{10}$/;
    if(!idCardUtils.validateIdNo(account) && !re.test(account)){
        myBox = new $Y.confirm({
            title:"",
            content:"<div style='text-align: center;'>请输入正确的账号</div>",
            ok:{title:"确定",
                click:function(){ 
                    myBox.close();
                }
            }
        });
        return;
    }
    
    var verifyCode = jQuery.trim(jQuery("#verifyCode").val());
    if(verifyCode == ''){
        myBox = new $Y.confirm({
            title:"",
            content:"<div style='text-align: center;'>请输入验证码!</div>",
            ok:{title:"确定",
                click:function(){ 
                    myBox.close();
                }
            }
        });
        return;
    }
    
    var datas = {verifyCode:verifyCode,account:account,codeType:"forgetpwd"};
    var url = appPath + "easyhealth/user/checkRegisterInfo";
    $.ajax({  
        type : 'POST',  
        url  : url,  
        data : datas,  
        dataType : 'json',
        timeout  : 120000,
        success  : function(data) {
            if(data.status == 'OK'){
              $("#paramsForm").attr("action", appPath + "easyhealth/user/forgetpwd");
              $("#paramsForm").submit();
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
         }, error : function(data) {  
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
    });
	
}

/**
 * 修改密码
 * @param obj
 */
function modifyPwd(obj, needInitPwd){

    var initPwd = '';
    var verifyCode = '';
    var account = '';
    
    if (needInitPwd) {  //需要验证原密码
      initPwd = jQuery.trim(jQuery("#initPwd").val());
      if(initPwd == ''){
          myBox = new $Y.confirm({
              title:"",
              content:"<div style='text-align: center;'>请输入原密码!</div>",
              ok:{title:"确定",
                  click:function(){ 
                      myBox.close();
                  }
              }
          });
          return;
      }
    } else {
      verifyCode = $('#verifyCode').val();
      account = $('#account').val();
    }
    
    var passWord = jQuery.trim(jQuery("#passWord").val());
    if(passWord == ''){
        myBox = new $Y.confirm({
            title:"",
            content:"<div style='text-align: center;'>请输入密码!</div>",
            ok:{title:"确定",
                click:function(){ 
                    myBox.close();
                }
            }
        });
        return;
    }
    
    re = /[a-zA-Z0-9]{6,16}/;
    if (!re.test(passWord)){
      myBox = new $Y.confirm({
        title:"",
        content:"<div style='text-align: center;'>请输入正确格式密码,6-12位!</div>",
        ok:{title:"确定",
          click:function(){ 
            myBox.close();
          }
        }
      });
      return;
    }
    
    var confirmPassWord = jQuery.trim(jQuery("#confirmPassWord").val());
    if(confirmPassWord == ''){
        myBox = new $Y.confirm({
            title:"",
            content:"<div style='text-align: center;'>请输入确认密码!</div>",
            ok:{title:"确定",
                click:function(){ 
                    myBox.close();
                }
            }
        });
        return;
    }
    
    if(passWord != confirmPassWord){
        myBox = new $Y.confirm({
            title:"",
            content:"<div style='text-align: center;'>两次密码不一致!</div>",
            ok:{title:"确定",
                click:function(){ 
                    myBox.close();
                }
            }
        });
        return;
    }
    
    
    var datas = {cardNo:$('#cardNo').val(),verifyCode:verifyCode,account:account,passWord:passWord,initPwd:initPwd};
    var url = appPath + "easyhealth/user/modifyPwd";
    $.ajax({
        type : 'POST',  
        url  : url,  
        data : datas,  
        dataType : 'json',
        timeout  : 120000,
        success  : function(data) {
            if(data.status == 'OK'){
              myBox = new $Y.confirm({
                title:"",
                content:"<div style='text-align: center;'>密码修改成功！</div>",
                ok:{title:"确定",
                    click:function(){
                        myBox.close();
                        go(appPath + "easyhealth/user/toLogin",true);//登录成功跳转至首页
                    }
                }
            });
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
         }, error : function(data) {  
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
    });
    
}


function modifyMobile(obj) {
  var passWord = jQuery.trim(jQuery("#passWord").val());
  if(passWord == ''){
      myBox = new $Y.confirm({
          title:"",
          content:"<div style='text-align: center;'>请输入密码!</div>",
          ok:{title:"确定",
              click:function(){ 
                  myBox.close();
              }
          }
      });
      return;
  }
  
  var mobile = jQuery.trim(jQuery("#mobile").val());
  var re = /^1\d{10}$/;
  if (!re.test(mobile)){
      myBox = new $Y.confirm({
          title:"",
          content:"<div style='text-align: center;'>请输入正确的手机号码!</div>",
          ok:{title:"确定",
              click:function(){
                  myBox.close();
              }
          }
      });
      return;
  }
  
  var verifyCode = jQuery.trim(jQuery("#verifyCode").val());
  if(verifyCode == ''){
      myBox = new $Y.confirm({
          title:"",
          content:"<div style='text-align: center;'>请输入验证码!</div>",
          ok:{title:"确定",
              click:function(){ 
                  myBox.close();
              }
          }
      });
      return;
  }
  
  $Y.loading.show();
  var datas = {cardNo:$('#cardNo').val(),verifyCode:verifyCode,mobile:mobile,passWord:passWord};
  var url = appPath + "easyhealth/user/modifyMobile";
  $.ajax({
      type : 'POST',  
      url  : url,  
      data : datas,  
      dataType : 'json',
      timeout  : 120000,
      success  : function(data) {
          $Y.loading.hide();
          if(data.status == 'OK'){
            myBox = new $Y.confirm({
              title:"",
              content:"<div style='text-align: center;'>手机修改成功！</div>",
              ok:{title:"确定",
                  click:function(){
                      myBox.close();
                      go(appPath + "easyhealth/userCenterMyInfo",true);//登录成功跳转至首页
                  }
              }
          });
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
       }, error : function(data) {
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
  });
  
}

function perfectUserInfo() {
  var name = jQuery.trim(jQuery("#name").val());
  if(name == ''){
      myBox = new $Y.confirm({
          title:"",
          content:"<div style='text-align: center;'>请输入姓名!</div>",
          ok:{title:"确定",
              click:function(){ 
                  myBox.close();
              }
          }
      });
      return;
  }
  
  if (!/^[\u0391-\uFFE5]{2,}$/.test(name)) {
    myBox = new $Y.confirm({
          title:"",
          content:"<div style='text-align: center;'>请输入至少两个字的中文姓名</div>",
          ok:{title:"确定",
              click:function(){ 
                  myBox.close();
              }
          }
      });
      return;
  }
  
  var cardType = $("#yx-select-card-id-val").val();
  var cardNo = jQuery.trim(jQuery("#cardNo").val());
  
  if(cardNo == ''){
      myBox = new $Y.confirm({
          title:"",
          content:"<div style='text-align: center;'>请输入证件号码!</div>",
          ok:{title:"确定",
              click:function(){ 
                  myBox.close();
              }
          }
      });
      return;
  }
  
  if (cardType == 1) {
    if(!idCardUtils.validateIdNo(cardNo)){
        myBox = new $Y.confirm({
            title:"",
            content:"<div style='text-align: center;'>请输入正确的证件号码!</div>",
            ok:{title:"确定",
                click:function(){ 
                    myBox.close();
                }
            }
        });
        return;
    }
  }
  
  if (cardType != 1) {
    var birthDay = jQuery.trim(jQuery("#birthDay").val());
    if(birthDay == ''){
        myBox = new $Y.confirm({
            title:"",
            content:"<div style='text-align: center;'>请选择生日!</div>",
            ok:{title:"确定",
                click:function(){ 
                    myBox.close();
                }
            }
        });
        return;
    }
    
    var sex = $(".sex-bar input:radio:checked").val();
    
    if(!sex){
        myBox = new $Y.confirm({
            title:"",
            content:"<div style='text-align: center;'>请选择性别!</div>",
            ok:{title:"确定",
                click:function(){ 
                    myBox.close();
                }
            }
        });
        return;
    }
  }
  
  //  var address = jQuery.trim(jQuery("#address").val());
  //  if(address == ''){
  //      myBox = new $Y.confirm({
  //          title:"",
  //          content:"<div style='text-align: center;'>请输入地址!</div>",
  //          ok:{title:"确定",
  //              click:function(){ 
  //                  myBox.close();
  //              }
  //          }
  //      });
  //      return;
  //  }
  //  if (!/^[\u0391-\uFFE5]{1}.*$/.test(address)) {
  //    myBox = new $Y.confirm({
  //          title:"",
  //          content:"<div style='text-align: center;'>请输入正确的地址!</div>",
  //          ok:{title:"确定",
  //              click:function(){ 
  //                  myBox.close();
  //              }
  //          }
  //      });
  //      return;
  //  }
  
  //$("#paramsForm").attr("action", appPath + "easyhealth/user/perfectUserInfo");
  //$("#paramsForm").submit();
  
  var datas = {};
  datas.name = name;
  datas.cardType = cardType;
  datas.cardNo = cardNo;
  
  if (cardType != 1) {
    datas.birthDay = birthDay;
    datas.sex = sex;
  }
  //datas.address = address;
  var province = $('select[name="province"]').val();
  var city = $('select[name="city"]').val();
  var area = $('select[name="area"]').val();
  datas.address = dsy.get(0)[province]+dsy.get("0_"+province)[city]+dsy.get("0_"+province+"_"+city)[area];
  $Y.loading.show();
  $.ajax({
      type : 'POST',  
      url  : appPath + "easyhealth/user/perfectUserInfo",  
      data : datas,  
      dataType : 'json',
      timeout  : 120000,
      success  : function(data) {
          $Y.loading.hide();
          if(data.status == 'OK'){
            myBox = new $Y.confirm({
                title:"",
                content:"<div style='text-align: center;'>完善资料成功！</div>",
                ok:{title:"确定",
                    click:function(){
                        myBox.close();
                        
                        //如果有forward，则完善资料成功后用go(forward, false)跳转，否则用壳的appCloseView()方法
                        var forward = $("#forward").val();
                        var openId = $('#openId').val();
                        
                        if (forward) {
                        	if (forward.indexOf('?') != -1) {
                        		forward += '&openId=' + openId;
                        	} else {
                        		forward += '?openId=' + openId;
                        	}
                        	go(forward, false);
                        } else {
                          
                            if(IS.isMacOS){
                              try
                              {
                                //window.appCloseView();
                                window.appCloseView(true);
                                
                              } catch (e) {
                                //  alert('IOS的方法出错');
                              }
                              
                            }else if(IS.isAndroid){
                              try
                              {
                                //window.yx129.appCloseView();
                                window.yx129.appCloseView(true);
                                
                              } catch (e) {
                                //   alert('Android的方法出错');
                              }
                              
                            }else{
                              go(appPath + 'easyhealth/userCenterIndex');
                            }
                          
                          
                        }
                    }
                }
            });
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
       }, error : function(data) {
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
  });
}


