/*侧边添加滚动条*/
//function scrollSideBar(){
//    var heightS =   document.body.clientHeight;
//    var sidebarHeight= heightS -125 -40;
//    var sidebarUl= document.getElementById('ul-sideNav');
//    var liTag = sidebarUl.getElementsByTagName('li');
//    var liNuw =liTag.length;
//    var sidebarUlListHeight = 54*liNuw;
//    sidebarUl.style.maxHeight =sidebarHeight;
//    if(sidebarUlListHeight - sidebarHeight > 0){
//        sidebarUl.style.overflowY= 'scroll' ;
//    }else{
//        sidebarUl.style.overflowY= 'auto' ;
//    }
//}
//dom加载完成后执行的js
$(function(){
    //全选
    $(document).on('click','input[type=checkbox][name=all]',function(){
        var ps = $(this).parents('.form-check');
        var p = $(this).parents('label');
        if(p.hasClass('check')){
            p.removeClass('check');
            ps.find('.checkboxTwo').removeClass('check');
        }else{
            p.addClass('check')
            ps.find('.checkboxTwo').addClass('check');
        }
    });

    //单选
    $(document).on('click','input[type=radio]',function(){
        var p = $(this).parents('label');
        var name = $(this).attr('name');
            $('form input[name='+name+']').parents('label').removeClass('check');
            p.addClass('check');
    });
    
    // 展开显示，隐藏
    $('li .subNavItem').on('click', function() {
		if (!$(this).is('.subNavItem-active')) {
			$('li .subNavItem').removeClass('subNavItem-active');
			$(this).addClass('subNavItem-active');
		}
	});
	
    // +-显示
	$('li .parentNavItem').on('click', function() {
		var obj = $(this).parent();
		if (obj.is('.active')) {
			obj.removeClass('active');
		} else {
			obj.addClass('active');
		}
	});
    
    //多选
    $(document).on('click','input[type=checkbox][name!=all][name!=one]',function(){
        var ps = $(this).parents('.form-check');
        var p = $(this).parents('label');
        if(p.hasClass('check')){
            p.removeClass('check');
        }else{
            p.addClass('check');
        }
        
        var size = ps.find('input[type=checkbox][name!=all][name!=one]').size();
        var iCount  = ps.find('.checkboxTwo.check').size();
        //  console.log(iCount);
        if(iCount ==size ){
            ps.find('.checkboxTwoAll').addClass('check');
        }else{
            ps.find('.checkboxTwoAll').removeClass('check');
        }
    });
    
    //checkbox单选
    $(document).on('click','input[type=checkbox][name=one]',function(){
        var ps = $(this).parents('.form-check');
        var p = $(this).parents('label');
        if(p.hasClass('check')){
            p.removeClass('check');
            $(this).parent().parent().parent().removeClass('warning');
        }else{
        	ps.find('.checkboxTwo').removeClass('check');
        	ps.find('input[type=checkbox]').removeClass('check');
        	// 去除行高亮
        	ps.find('tr').removeClass('warning');
        	
            p.addClass('check');
            
            // 添加行高亮
            $(this).parent().parent().parent().addClass('warning');
        }
        
    });
    
    $('.ui-select-warp li').click(function(){
        var li = $(this);
        var val = li.html();
        var input = li.parent().parent().find('input');
        input.val(val);
        if (li.attr('data-value')) {
        	input.attr('data-value', li.attr('data-value'));
        }
        li.parent().hide();
        
        // 处理回调
        if (typeof liClickCallback === 'function') {
        	liClickCallback($(this));
        }
    })

    $('.ui-select-warp .ui-select-input').click(function(){
        var input = $(this);
        var obj = input.next();
        if (obj.is(':visible')) {
        	obj.hide();
        } else {
        	obj.show();
        }
    })
    
    $('.ui-select-warp').on('mouseleave',function(){
    var o = $(this);
    setTimeout(function(){
        o.find('.ui-select-list').hide();
    }, 100)

})
    
    //侧边滚动条
    //scrollSideBar()
});

