```js
   var url = window.location.port == 82?'../static/':'/static/index/';
            var vm = this;
            if($('#mobcss').length <1){
                $('head').append('<link id="mobcss" rel="stylesheet" href="'+url+'js/lib/mobiscroll/css/mobiscroll.custom-3.0.0-beta2.min.css" />')
            }
            $.get(url+'js/lib/mobiscroll/mobiscroll.custom-3.0.0-beta2.min.js',function () {
                var currYear = (new Date()).getFullYear();
                //初始化配置参数
                $('#js-date').mobiscroll().calendar({
                    theme: 'mobiscroll',
                    lang: 'zh',
                    mode: 'Basic usage'   ,
                    dateFormat: 'yyyy-mm-dd',
                    display: 'center',     //显示方式
                    defaultValue: new Date(1990, 10, 1), //初始日期
                    endYear: currYear,  //结束年份,
                    onSet:function (event, inst) {
                        vm.data.patient_birthday = event.valueText
                    }
                });
            })
```