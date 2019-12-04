//dom加载完成后执行的js
$(function() {
	//全选
	$(document).on('click', 'input[type=checkbox][name=all]', function() {
		var ps = $(this).parents('.form-check');
		var p = $(this).parents('label');
		if (p.hasClass('check')) {
			p.removeClass('check');
			ps.find('.checkboxTwo').removeClass('check');
		} else {
			p.addClass('check')
			ps.find('.checkboxTwo').addClass('check');
		}
	});

	//单选
	$(document).on('click', 'input[type=radio]', function() {
		var p = $(this).parents('label');
		var name = $(this).attr('name');
		$('form input[name=' + name + ']').parents('label').removeClass('check');
		p.addClass('check');
	});
	//多选
	$(document).on('click', 'input[type=checkbox][name!=all]', function() {
		var ps = $(this).parents('.form-check');
		var p = $(this).parents('label');
		if (p.hasClass('check')) {
			p.removeClass('check')
		} else {
			p.addClass('check')
		}
		var size = ps.find('input[type=checkbox][name!=all]').size();
		var iCount = ps.find('.checkboxTwo.check').size();
		//  console.log(iCount);
		if (iCount == size) {
			$('.checkboxTwoAll').addClass('check');
		} else {
			$('.checkboxTwoAll').removeClass('check');
		}
	});
	
	//将表单转成object 对象
	$.fn.serializeObject = function()
	{
	    var o = {};
	    var a = this.serializeArray();
	    $.each(a, function() {
	        if (o[this.name] !== undefined) {
	            if (!o[this.name].push) {
	                o[this.name] = [o[this.name]];
	            }
	            o[this.name].push(this.value || '');
	        } else {
	            o[this.name] = this.value || '';
	        }
	    });
	    return o;
	};
})

