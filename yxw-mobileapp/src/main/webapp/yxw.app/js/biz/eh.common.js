/**
 * zhoujb
 * 2015-10-06 
 */
function windowClose(){
	if(IS.isMacOS){
        try
        {
            window.appCloseView();

        } catch (e) {
          //  alert('IOS的方法出错');
        }

    }else if(IS.isAndroid){
        try
        {
            window.yx129.appCloseView();

        } catch (e) {
         //   alert('Android的方法出错');
        }

    }else{
    	 go(appPath + 'easyhealth/index');
    }
}

