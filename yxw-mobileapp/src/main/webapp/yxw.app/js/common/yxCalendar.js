/**
 * 日期选择插件   /日历
 * yxCalendar
 *
 * @version 1.5  by nick  2015/12/8 15:00
 * */
var touchMove = function(e){e.preventDefault();};
(function(window,$){
    var $ =$;
    var yxCalendar =function(opt){
        var me  = this;
        this.opt = opt ;
        me.dom = opt.dom || ''; //时间控制 选择器
        me.position = opt.position || ''; //显示位置 无动画参数 (top 、bottom),有动画效果 slide_up、slide_down
        if(!opt.dom){return false;}
        me.date ={};                           //传递的日期
        me.callback = function(){};           //回调函数
        me.before = opt.before|| function(){};    //显示后回调函数
        me.getID = function(DOMid){
            return window.document.getElementById(DOMid);
        }
        if(opt){
            if(typeof  opt == 'object'){
                me.callback = opt.callback || function(){};
                me.minDate = opt.minDate || '';
                me.maxDate = opt.maxDate || '';
            }
        }

        me.minDate = me.minDate? this.format(me.minDate):'';
        me.maxDate = me.maxDate? this.format(me.maxDate):'';
        me.eventId = null;
        me.init();

    };


    //取当前时间
    yxCalendar.getTime = function(myDate){
        var D =  myDate || new Date();
        if(typeof D == "object"){
            var y = D.getFullYear();
            var m = D.getMonth()+1;
            var day = D.getDate();
            var date =  y+'-'+m+'-'+day;
        }else{
            var date =D;
        }
        return date;
    };

    //格式化格式
    yxCalendar.prototype.format = function(str){
        var anyDate = str.match(/^(\d+)-(\d+)-(\d+)/);
        var date ={};
        date.y = parseInt(anyDate[1]);
        date.m = parseInt(anyDate[2]);
        date.d = parseInt(anyDate[3]);
        return date;
    };
    //获取月份的天数
    yxCalendar.prototype.getDaysInMonth = function(year, month) {
        return 32 - new Date(year, month, 32).getDate();
    }

    //获取月份中的第一天是所在星期的第几天
    // month：(0--11) 月份从0开始
    // return (0-6) 0：星期日 1：星期一 ...
    yxCalendar.prototype.getFirstDayOfMonth = function(year, month) {
        return new Date(year, month, 1).getDay();
    };
    yxCalendar.prototype.CreateDayDOM =function(year,month){
        var me = this;
        var year = parseInt(year) || me.date.y;
        var month = parseInt(month)-1 || parseInt(me.date.m) -1;
        var maxMonth = this.getDaysInMonth(year,month);
        var prevMonth = this.getDaysInMonth(year,month-1);
        var nextMonth = this.getDaysInMonth(year,month+1);
        var getFirstDayOfMonth  = this.getFirstDayOfMonth(year,month);
        var MonthHtml = '<tr>';
        var i = 0,day = 1;
        for(var m =1;m<=42;m++){
            if(m<getFirstDayOfMonth+1){
                MonthHtml +='<td class="grey">'+(prevMonth -getFirstDayOfMonth + m)+'</td>'; //上一月日
            }else if(day>maxMonth){
                MonthHtml +='<td class="grey">'+(m-getFirstDayOfMonth-maxMonth)+'</td>';//下一月日
            } else {

                //正常时间
                var myF = function(){
                    if(day == me.date.d){
                        MonthHtml +='<td class="selects active">'+ day +'</td>';//当月选中日
                    }else{
                        MonthHtml +='<td class="selects">'+ day +'</td>';//当月日
                    }
                }

                //最小时间  逻辑
                var minFn=function(){
                    if(me.minDate){
                        if(me.date.y>=me.minDate.y){
                            if(me.date.m>=me.minDate.m || me.date.y>me.minDate.y){

                                if(day>=me.minDate.d ||  me.date.m>me.minDate.m || me.date.y>me.minDate.y){
                                    myF();
                                }else{
                                    MonthHtml +='<td class="grey">'+ day +'</td>';//当月日
                                }
                            }else{
                                MonthHtml +='<td class="grey">'+ day +'</td>';//当月日
                            }
                        }else{
                            MonthHtml +='<td class="grey">'+ day +'</td>';//当月日
                        }

                    }else{
                        myF();
                    }
                }

                //最大时间 逻辑
                if(me.maxDate|| me.minDate){
                    if(me.date.y>=me.maxDate.y){
                        if(me.date.m>=me.maxDate.m || me.date.y>me.maxDate.y){
                            if( me.date.m>me.maxDate.m || me.date.y>me.maxDate.y || day>me.maxDate.d){
                                MonthHtml +='<td class="grey">'+ day +'</td>';//当月日
                            }else{
                                minFn();
                            }
                        }else{
                            minFn()
                        }
                    }else{
                        minFn();
                    }

                }else{
                    if(day == me.date.d){
                        MonthHtml +='<td class="selects active">'+ day +'</td>';//当月选中日
                    }else{
                        MonthHtml +='<td class="selects">'+ day +'</td>';//当月日
                    }
                }
                day++;
            }
            //每6个换行 控制tr
            if(i>5){
                MonthHtml +='</tr>';
                i= 0;
            }else{
                i++;
            }
        }
        return MonthHtml;
    };
    yxCalendar.prototype.CreateDOM =function(str){
        var me = this,animate = false
        ,position = '';
        if(!str){
            if(!$('#'+me.eventId).next().val()){
                if(this.opt){
                    var opt = this.opt;
                    if(typeof  opt == 'object'){
                        if(opt.date){
                            opt.date  = opt.date.replace(/\s/g,'');
                            me.date = opt.date || yxCalendar.getTime();

                        }else{
                            me.date = yxCalendar.getTime();
                        }

                    }
                }else{
                    me.date =  yxCalendar.getTime();
                }
            }else{
                me.date = $('#'+me.eventId).next().val();
            }
        }else{
            me.date = str;
        }

        me.date = this.format(me.date);
        switch (me.position){
            case 'top':position = 'top:0';
                break
            case 'bottom':position = 'bottom:0';
                break
            case 'slide_up':position = 'top:-310px';animate =true;
                break
            case 'slide_down':position = 'bottom:-310px';animate =true;
                break
        }
        var html ='';
        if(!str) {
            html += '<div id="yxCalendar" class="yxFix">' +
                '<style type="text/css">' +
                '#yxCalendar i {font-style: normal;}.yxFix{position: fixed;top: 0;bottom: 0;z-index:999;left:0;right: 0;width: 100%;height: 100%;}' +
                '#yxCalendar_main{background-color: #fff;position: absolute;' + position + ';height: auto;left: 0;padding: .5em 1em;right: 0;z-index: 1000;}' +
                '#yxCalendar_main.slide_down{ -webkit-transform:translateY(-310px); -moz-transform:translateY(-310px); -webkit-transition:.3s ease all; -moz-transition:.3s ease all;}' +
                '#yxCalendar_main.slide_up{ -webkit-transform:translateY(310px); -moz-transform:translateY(310px); -webkit-transition:.3s ease all; -moz-transition:.3s ease all;}' +
                '#yxCalendar_main.slide_up.close{ -webkit-transform:translateY(0); -moz-transform:translateY(0); -webkit-transition:.3s ease all; -moz-transition:.3s ease all;}' +
                '#yx_month_warp{float: left;width: 50%;font-size:14px;}#yx_year_warp{float: right;width: 50%;text-align:right;font-size:14px;}.yxHeader{height: 30px;overflow: hidden;}#yx_month_warp span,#yx_year_warp span{ float: left; line-height:2;text-align: center;font-size: 14px;}' +
                '#yx_year_warp span{float: right;line-height:2;} #yxCalendar_main .yx_prev{ width: 30px;color: #11ba98;} #yxCalendar_main .yx_next{ width: 30px;color: #11ba98;}#yx_month_warp span.active,#yx_year_warp span.active{background-color: #11ba98;color: #000;}#yx_month ,#yx_year{ width: 60px}' +
                '#yxCalendar_main .yxBody td{text-align: center;height: 30px;color: #888;font-size: 12px}#yxCalendar_main .yxBody .line td{height: 5px}#yxCalendar_main .yxBody #yx_Week th{padding: 4px 0 ; font-weight: normal;font-size: 12px;color: #11ba98;text-align: center;}' +
                '#yxCalendar_main .yxBody #yx_Week {border-bottom: solid 1px #11ba98;}#yxCalendar_main .yxBody td.grey{color: #ddd}#yxCalendar_main .yxBody td.active{background-color: #11ba98;color: #fff;border-radius: 2px}' +
                '#yxCalendar_main .yxBody{border-bottom: solid 1px #11ba98;padding-bottom:5px}#yxCalendar_main .yxFooter {text-align: right;padding-top:10px}#yxCalendar_main .yxFooter  span{padding: 8px 20px;color: #11ba98;line-height:30px;}' +
                '#yxCalendar_bg{-webkit-transition:.5s ease all; -moz-transition:.5s ease all;opacity:0;background-color: #000}' +
                '</style>' +
                '<div id="yxCalendar_warp"><div id="yxCalendar_bg" class="yxFix"></div>' +
                '<div id="yxCalendar_main" class="">';
        }
            html +='<div class="yxHeader">'+
            '<div id="yx_month_warp">'+
            //'<span id="yx_month_prev" class="yx_prev">＜</span><span id="yx_month">'+
            '<select id="yx_month_select" class="select-width">' ;
                for(var i = 1;i<13;i++){
                    if(me.date.m == i){
                        html +='<option value="'+i+'" selected>'+i+'月</option>' ;
                    }else{
                        html +='<option value="'+i+'">'+i+'月</option>' ;
                    }
                }
            html +='</select>'+
            //'</span><span id="yx_month_next" class="yx_next">＞</span>'+
            '</div>'+
            '<div id="yx_year_warp">'+
            //'<span id="yx_year_next" class="yx_next">＞</span><span id="yx_year">'+
            '<select id="yx_year_select" class="select-width">' ;
            for(var i = me.date.y - 80;i<=me.date.y;i++){
                if(me.date.y == i){
                    html +='<option value="'+i+'" selected>'+i+'年</option>' ;
                }else{
                    html +='<option value="'+i+'">'+i+'年</option>' ;
                }
            }
              //  '<i>'+me.date.y+'</i>年' +

            html +='</select>'+
            //'</span><span id="yx_year_prev" class="yx_prev">＜</span>'+
            '</div>'+
            '</div>'+
            '<div class="yxBody">'+
            '<table width="100%" cellpadding="0" cellspacing="0" border="0">'+
            '<thead>'+
            '<tr id="yx_Week"><th>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th></tr>'+
            '<tr class="line"> <td colspan="7"></td></tr>'+
            '</thead>'+
            '<tbody id="yx_tbody">';
        html +=me.CreateDayDOM();
        html += '<tr class="line"> <td colspan="7"></td></tr></tbody>';
        html += '</table></div>' +
            '<div class="yxFooter"> <span id="yx_btn_cancel">取消</span> <span id="yx_btn_ok">确定</span> </div> </div></div></div>';
        if(!str) {
            $('body').append(html);
        }else{
            $('#yxCalendar_main').html(html);
        }

            setTimeout(function(){
                $('#yxCalendar_bg').css('opacity',.5);
               // me.getID('yxCalendar_main').className = me.position;
            },250)


    }
    yxCalendar.prototype.init= function(){
        var me = this;
        var dom = $(this.dom);
        $('.yx-date-init').remove();
        dom.each(function(){
            var d = parseInt(100000*Math.random()+1000000);
            var p = $(this).parent();
                p.css('position','relative');
                p.prepend('<div class="yx-date-init" id="date'+d+'" style="position: absolute; top: 0; left: 0; right: 0; width: 100%; height: 100%; z-index: 9;"></div>');
                //$(this).attr('inputid',d);
              //  me.id = '[inputid=\"'+d+'\"]';
                me.initEvent(d);
        })
    };

    yxCalendar.prototype.initEvent = function(d){
        var me = this;
        $('#date'+d).on('click',function(){
            var id = $(this).attr('id');
            me.eventId = id ;
            me.show();
        })
    }
    yxCalendar.prototype.show = function(){
        var me = this;
        me.CreateDOM();
        setTimeout(function(){
            document.addEventListener('touchmove',touchMove,false);
            me.eventBind();
            me.before();
        },400)
    }
    yxCalendar.prototype.update = function(str){
        var me = this;
        me.CreateDOM(str);
        setTimeout(function(){
            me.eventBind();
        },400)
    }

    /**
     * @method 事件绑定
     */
    yxCalendar.prototype.eventBind = function(){
        var me = this;
        //选择日期
        me.selcetDay = function(){
            $('#yxCalendar_main td.active').removeClass('active');
            $(this).addClass('active');
            me.date.d = $(this).html();
        };
        //日期插件回调函数
        me.callback_btn = function(){
            var obj = $(this);
            obj.css({'background-color':'#11ba98', 'color': '#fff'})
            setTimeout(function(){
                obj.css({'background':'none', 'color': '#11ba98'})
            },200)

            var d = me.date.d,m=me.date.m,y=me.date.y;
            d = d<10?('0'+d):d;
            m = m<10?('0'+m):m;
            var date = y+'-'+m+'-'+d;
            if(me.eventId){
                $('#'+me.eventId).siblings(me.dom).val(date)
            }
            me.close();
            if(typeof me.callback == 'function'){
                me.callback(date);
            }

        };
        $('#yxCalendar_main td.selects').on('click',me.selcetDay);
        $('#yx_btn_cancel').on('click',me.close); //绑定取消
        $('#yx_btn_ok').on('click',me.callback_btn); //绑定取消

        //上一月份切换绑定
        $('#yx_month_prev').on('click',function(){
            var obj =  $(this)
            obj.addClass('active');
            setTimeout(function(){
                obj.removeClass('active');
            },250)
            me.changeMonth(-1);
        })
        //下一月份切换绑定
        $('#yx_month_next').on('click',function(){
            var obj =  $(this)
            obj.addClass('active');
            setTimeout(function(){
                obj.removeClass('active');
            },250)
            me.changeMonth(+1)
        })

        $('#yx_month_select').on('change',function(){
            var o = $(this);
            var val = o.val();
            var index = o[0].selectedIndex;

            me.changeMonth(val);
            setTimeout(function(){
                $('#yx_month_select')[0].selectedIndex = index;

            },400)
        })

        //上一年切换绑定
        $('#yx_year_prev').on('click',function(){
            var obj =  $(this)
            obj.addClass('active');
            setTimeout(function(){
                obj.removeClass('active');
            },250)
            me.changeYear(-1)
        })
        //下一年切换绑定
        $('#yx_year_next').on('click',function(){
            var obj =  $(this)
            obj.addClass('active');
            setTimeout(function(){
                obj.removeClass('active');
            },250)
            me.changeYear(+1)
        })

        $('#yx_year_select').on('change',function(){
            var o = $(this);
            var val = o.val();
            var index = o[0].selectedIndex;
            me.changeYear(val);
            setTimeout(function(){
                $('#yx_year_select')[0].selectedIndex = index;

            },400)
        })
    };

    /**
     * @method 月份切换
     * @param Month 为数字 或 加减
     *
     * Month == -1 ,+2   /  Month = '2'
     */
    yxCalendar.prototype.changeMonth = function(Month){
        var me = this;
        var y = parseInt(me.date.y);
        var m = parseInt(me.date.m);
        if(Month){
            if(typeof Month == 'number'){
                m = m+Month ;
            }else if(typeof Month == 'string'){
                m = parseInt(Month);
            }
            if(m>12){
                m = 1;
              //  y = y+1;
            }else if(m<1){
                m = 12;
             //   y = y-1;
            }
            me.date.m = m;
            $('#yx_tbody').html(me.CreateDayDOM(y,m));
            $('#yxCalendar_main td.selects').on('click',me.selcetDay);
        }
    };

    /**
     * @method 年份切换
     * @param Year 为数字 或 加减
     *
     * Year == -1 ,+2   /  Year = '2'
     */
    yxCalendar.prototype.changeYear = function(year){
        var me = this;
        var y = parseInt(me.date.y);
        var m = parseInt(me.date.m);
        if(year){
            if(typeof year == 'number'){
                y = y+year ;
            }else if(typeof year == 'string'){
                y = year;
            }
            if(m>12){
                m = 1;
                y = y+1;
            }else if(m<1){
                m = 12;
                y = y-1;
            }
            me.date.y = y;

            $('#yx_tbody').html(me.CreateDayDOM(y,m));
            $('#yxCalendar_main td.selects').on('click',me.selcetDay);
        }
    };
    //销毁日期插件
    yxCalendar.prototype.close =function(e){
        var me = this;
        document.removeEventListener('touchmove',touchMove);
      //  $('#yxCalendar_main').addClass('close');
        $('#yxCalendar_bg').css('opacity',0);
        setTimeout(function(){
            $('#yxCalendar').remove();
            me.eventId = null;
        },200)

        return false;
    };
    window.yxCalendar = yxCalendar;
}(window,window.jQuery||window.Zepto));
