(function(){

    // subscribe
    (function(){
    	commMethod("subscribe");
    })();

    // card
    (function(){
    	commMethod("card");
    })();


    // order
    (function(){
    	
    	commOrderMethod({statsType:'1',statsField:'amount'});//累计交易金额
    	
    	commOrderMethod({statsType:'1',statsField:'count'});//累计交易金额
    	
    	commOrderMethod({statsType:'2',statsField:'amount'});//每月交易金额
    	
    	commOrderMethod({statsType:'2',statsField:'count'});//每月订单量
    	
    })();

    
     // sexProportion
    (function(){

    	 var canvas = document.getElementById('sexProportionDoughnut');
    	 var dataInfo = {
    			    type: "pie",
    			    data: {
    			        labels: [],
    			        datasets: [
    			            {
    			                label: "sexProportion",
    			                data: [],
    			                backgroundColor: [
    			                    "rgb(54, 162, 235)",
    			                    "rgb(255, 99, 132)"
    			                ]
    			            }
    			        ]
    			    },
	    			options: {
	    				legend: {
	    		            display: true,
	    		            position: "left"
	    				},
	    				animation: {
	    		            duration: 0
	    		        },
	    				hover: {
	    					animationDuration: 0  
	    				},
	    				tooltips: {
	    		            callbacks: {
	    		                label: function(tooltipItem, data) {
	    		                	console.log(data);
	    		                    var label = data.labels[tooltipItem.index] || '';

	    		                    if (label) {
	    		                        label += '：';
	    		                    }
	    		                    label += yxw.number_format(data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index], 0, '.', ',');
	    		                    return label;
	    		                }
	    		            }
	    		        }
	    			}
    			};
    	 
    	 
    	 
    	 $.post('/portal/getSexProportionOrderDatas', function(data) {
    		 console.log(data);
    		 
    		 if (data.status == 'OK') {
     			//累计
    			 console.log(data.message.genderScale);
    			 
    			 dataInfo.data.datasets[0].data = data.message.genderScale.values;
    			 dataInfo.data.labels = data.message.genderScale.legend;
    			 
     			 var sexProportionPieChart = new Chart(canvas.getContext('2d'), dataInfo);
     			 var meta = sexProportionPieChart.controller.getDatasetMeta(0);
    			 triggerMouseEvent(sexProportionPieChart, "click", meta.data[0]);
    		 }
    		 
    	 });
    	 
    })();

    function commMethod(params){
    	
    	var url = "";
    	var subscribeCumulateLineContexts = {}, subscribeMonthLineContexts = {};
    	if (params == 'subscribe') {
    		
    		subscribeCumulateLineContexts.line = document.getElementById('subscribe-cumulate-line').getContext('2d');
    		subscribeMonthLineContexts.line = document.getElementById('subscribe-month-line').getContext('2d');
    		url = "/portal/getSubscribeDatas";
		}
       
    	 var cardCumulateLineContexts = {}, cardMonthLineContexts = {};
	     if (params == 'card') {
	    	 cardCumulateLineContexts.line = document.getElementById('card-cumulate-line').getContext('2d');
	    	 cardMonthLineContexts.line = document.getElementById('card-month-line').getContext('2d');
	    	 url = "/portal/getCardDatas";
	     }
	     
	     var dataInfo = {
	    		 	type: "line",
	    			data: {
	    				labels: "",
	    				datasets: [
										{
										    label: "",
										    data: [],
										    fill: true,
										    borderColor: "rgb(75, 192, 192)",
										    lineTension: 0.1
										}
	    				             ]
	    			},
	    			options: {
	    				legend: {
	    		            display: false,
	    				},
	    				animation: {
	    		            duration: 0/*, 
	    		            onComplete: function () {
		    	                    var chartInstance = this.chart,
		    	 
		    	                    ctx = chartInstance.ctx;
		    	                    ctx.font = Chart.helpers.fontString(Chart.defaults.global.defaultFontSize, Chart.defaults.global.defaultFontStyle, Chart.defaults.global.defaultFontFamily);
		    	                    ctx.fillStyle = "black";
		    	                    ctx.textAlign = 'center';
		    	                    ctx.textBaseline = 'bottom';
		    	 
		    	                    this.data.datasets.forEach(function (dataset, i) {
		    	                        var meta = chartInstance.controller.getDatasetMeta(i);
		    	                        meta.data.forEach(function (bar, index) {
		    	                            var data = dataset.data[index];
		    	                            ctx.fillText(data, bar._model.x, bar._model.y - 5);
		    	                        });
		    	                    });
		    	            }*/
	    		        },
	    				hover: {
	    					animationDuration: 0  
	    				},
	    				scales: {
	    		            yAxes: [{
	    		                ticks: {
	    		                    // Include a dollar sign in the ticks
	    		                    callback: function(value, index, values) {
	    		                        return yxw.number_format(value, 0, '.', ',');
	    		                    }
	    		                }
	    		            }]
	    		        },
	    		        tooltips: {
	    		            callbacks: {
	    		                label: function(tooltipItem, data) {
	    		                	console.log(data);
	    		                    var label = data.datasets[tooltipItem.datasetIndex].label || '';

	    		                    if (label) {
	    		                        label += '：';
	    		                    }
	    		                    label += yxw.number_format(tooltipItem.yLabel, 0, '.', ',');
	    		                    return label;
	    		                }
	    		            }
	    		        }
	    			}
	     };
        
        $.post(url, function(data) {
        	console.log(data);
        	
    		if (data.status == 'OK') {
    			
    			dataInfo.data.labels = data.message.monthList;
    			
    			//累计
    			console.log(data.message.datas);
    			
    			var cumulateData = []; 
    			var monthData = [];
    			
    			$.each(data.message.datas, function(areaKey, areaValue) {
    				var cumulateDataTemp = [];
    				var monthDataTemp = [];
    				
    				$.each(areaValue, function(month, value) {
//    					console.log(month + "----" +value);
    					cumulateDataTemp.push(value.wxCumulateCountTillThisMonth + value.aliCumulateCountTillThisMonth);
    					monthDataTemp.push(value.thisMonthWxIncCount + value.thisMonthAliIncCount);
    				});
//    				console.log(cumulateDataTemp);
    				cumulateData.push(cumulateDataTemp);
    				monthData.push(monthDataTemp);
    			});
    			
    			var cumulateDataTarget = [];
    			var monthDataTarget = [];
    			
    			$.each(cumulateData, function(k, v){
    				var cumulateTemp = [];
    				for (var i in v){
    				    if (cumulateDataTarget.length > 0) {
    				    	cumulateTemp.push(cumulateDataTarget[i] + v[i]);
    					}else {
    						cumulateTemp.push(v[i] + 600000);
    					}
    				}
    				cumulateDataTarget = cumulateTemp;
    			});
    			
    			console.log("累计" + cumulateDataTarget);//累计
    			
    			$.each(monthData, function(k, v){
    				var monthTemp = [];
    				for (var i in v){
    				    if (monthDataTarget.length > 0) {
    				    	monthTemp.push(monthDataTarget[i] + v[i]);
    					}else {
    						monthTemp.push(v[i]);
    					}
    				}
    				monthDataTarget = monthTemp;
    			});
    			
    			console.log("每月"+monthDataTarget);//每月
    			//还要再加上线下的数据（php接口汇总数据）
    			var n = 0;
    			$.each(jsonDataInfo, function(i, v){
    				console.log(i);
    				console.log(v);
    				
    				cumulateDataTarget[n] = cumulateDataTarget[n] + v[1];
    				monthDataTarget[n] = monthDataTarget[n] + v[1];
    				
    				n++;
    				
    			});

    			console.log("最终累计" + cumulateDataTarget);//累计
    			console.log("最终每月"+monthDataTarget);//每月
    			
    			if (params == 'subscribe') {
    				
//    				dataInfo.data.datasets[0].label = "cumulateSubscribe";
    				dataInfo.data.datasets[0].label = "累计";
    				dataInfo.data.datasets[0].data = cumulateDataTarget;
        			var subscribeCumulateLineChart = new Chart(subscribeCumulateLineContexts.line, dataInfo);
        			var meta = subscribeCumulateLineChart.controller.getDatasetMeta(0);
        			triggerMouseEvent(subscribeCumulateLineChart, "click", meta.data[meta.data.length-1]);
        			
        			
        			var tempDataInfo = {};
        			$.extend(true,tempDataInfo, dataInfo);

//        			tempDataInfo.data.datasets[0].label = "monthSubscribe";
        			tempDataInfo.data.datasets[0].label = "新增";
        			tempDataInfo.data.datasets[0].data = monthDataTarget;
        			var subscribeMonthLineChart = new Chart(subscribeMonthLineContexts.line, tempDataInfo);
        			var meta = subscribeMonthLineChart.controller.getDatasetMeta(0);
        			triggerMouseEvent(subscribeMonthLineChart, "click", meta.data[meta.data.length-1]);
    				
        			var info = dataInfo.data.labels[dataInfo.data.labels.length - 1] + "单月增长量：" + monthDataTarget[monthDataTarget.length - 1];
        			$('#subscribe-month-line-text').text(info);
    			}
    	       
    		     if (params == 'card') {
//    		    	dataInfo.data.datasets[0].label = "cumulateCard";
    		    	dataInfo.data.datasets[0].label = "累计";
    		    	dataInfo.data.datasets[0].data = cumulateDataTarget;
					var cardCumulateLineChart = new Chart(cardCumulateLineContexts.line, dataInfo);
					var meta = cardCumulateLineChart.controller.getDatasetMeta(0);
        			triggerMouseEvent(cardCumulateLineChart, "click", meta.data[meta.data.length-1]);
					
					var tempDataInfo = {};
        			$.extend(true,tempDataInfo, dataInfo);
        			
//					tempDataInfo.data.datasets[0].label = "monthCard";
        			tempDataInfo.data.datasets[0].label = "新增";
					tempDataInfo.data.datasets[0].data = monthDataTarget;
					var cardMonthLineChart = new Chart(cardMonthLineContexts.line, tempDataInfo);
					var meta = cardMonthLineChart.controller.getDatasetMeta(0);
        			triggerMouseEvent(cardMonthLineChart, "click", meta.data[meta.data.length-1]);
					
					var info = dataInfo.data.labels[dataInfo.data.labels.length - 1] + "单月增长量：" + monthDataTarget[monthDataTarget.length - 1];
        			$('#card-month-line-text').text(info);
    		     }
    			
    		}
    	}, 'json');

    }
    
    function triggerMouseEvent(chart, type, el) {
    	var node = chart.canvas;
    	var rect = node.getBoundingClientRect();
    	var event = new MouseEvent(type, {
    		clientX: rect.left + el._model.x,
    		clientY: rect.top + el._model.y,
    		cancelable: true,
    		bubbles: true,
    		view: window
    	});

    	node.dispatchEvent(event);
    }
    
    function commOrderMethod(params){
    	
//    	commOrderMethod({statsType:'1',statsField:'amount'});//累计交易金额
//    	
//    	commOrderMethod({statsType:'1',statsField:'count'});//累计交易金额
//    	
//    	commOrderMethod({statsType:'2',statsField:'amount'});//每月交易金额
//    	
//    	commOrderMethod({statsType:'2',statsField:'count'});//每月订单量
    	var amountCumulateLineContexts = {}, amountMonthLineContexts = {}, countCumulateLineContexts = {}, countMonthLineContexts = {};
    	var label = '';
    	
    	if (params.statsType == '1' && params.statsField == 'amount') {
    		amountCumulateLineContexts.line = document.getElementById('amount-cumulate-line').getContext('2d');
//    		label = 'amount-cumulate-line';
    		label = '累计';
		}else if (params.statsType == '1' && params.statsField == 'count') {
			countCumulateLineContexts.line = document.getElementById('count-cumulate-line').getContext('2d');
//			label = 'count-cumulate-line';
			label = '累计';
		}else if (params.statsType == '2' && params.statsField == 'amount') {
			amountMonthLineContexts.line = document.getElementById('amount-month-line').getContext('2d');
//			label = 'amount-month-line';
			label = '新增';
		}else if (params.statsType == '2' && params.statsField == 'count') {
//			label = 'count-month-line';
			label = '新增';
			countMonthLineContexts.line = document.getElementById('count-month-line').getContext('2d');
		}
    	
    	 var dataInfo = {
	    		 	"type": "line",
	    			"data": {
	    				"labels": "",
	    				"datasets": [
										{
										    "label": label,
										    "data": [],
										    "fill": true,
										    "borderColor": "rgb(75, 192, 192)",
										    "lineTension": 0.1
										}
	    				             ]
	    			},
	    			"options": {
	    				legend: {
	    		            display: false,
	    				},
	    				animation: {
	    		            duration: 0/*, 
	    		            onComplete: function () {
		    	                    var chartInstance = this.chart,
		    	 
		    	                    ctx = chartInstance.ctx;
		    	                    ctx.font = Chart.helpers.fontString(Chart.defaults.global.defaultFontSize, Chart.defaults.global.defaultFontStyle, Chart.defaults.global.defaultFontFamily);
		    	                    ctx.fillStyle = "black";
		    	                    ctx.textAlign = 'center';
		    	                    ctx.textBaseline = 'bottom';
		    	 
		    	                    this.data.datasets.forEach(function (dataset, i) {
		    	                        var meta = chartInstance.controller.getDatasetMeta(i);
		    	                        meta.data.forEach(function (bar, index) {
		    	                            var data = dataset.data[index];
		    	                            ctx.fillText(data, bar._model.x, bar._model.y - 5);
		    	                        });
		    	                    });
		    	            }*/
	    		        },
	    				hover: {
	    					animationDuration: 0  
	    				},
	    				scales: {
	    		            yAxes: [{
	    		                ticks: {
	    		                    // Include a dollar sign in the ticks
	    		                    callback: function(value, index, values) {
	    		                        return yxw.number_format(value, 0, '.', ',');
	    		                    }
	    		                }
	    		            }]
	    		        },
	    		        tooltips: {
	    		            callbacks: {
	    		                label: function(tooltipItem, data) {
	    		                	console.log(data);
	    		                    var label = data.datasets[tooltipItem.datasetIndex].label || '';

	    		                    if (label) {
	    		                        label += '：';
	    		                    }
	    		                    if (params.statsField == 'amount') {
	    		                    	 label += yxw.number_format(tooltipItem.yLabel, 2, '.', ',') + "元";
	    		                    }else{
	    		                    	label += yxw.number_format(tooltipItem.yLabel, 0, '.', ',');
	    		                    }
	    		                    
	    		                    return label;
	    		                }
	    		            }
	    		        }
	    			}
	     };
    	 
        
    	 $.post('/portal/getOrderDatas', params, function(data) {
    		 console.log(data);
    		 
     		if (data.status == 'OK') {
     			
     			console.log(data.message.order);
     			
    			dataInfo.data.labels = data.message.order.xData;
    			
    			var dataTarget = []; 
    			
    			$.each(data.message.order.yData, function(key, value) {
    				console.log(value);
    				dataTarget = value;
    				return false;
    			});
    			
    			dataInfo.data.datasets[0].data = dataTarget;
    			
    			if (params.statsType == '1' && params.statsField == 'amount') {
    				
    				dataInfo.data.datasets[0].data = yxw.fromFenToYuan(dataTarget);
    				    				
    				var amountCumulateLineChart = new Chart(amountCumulateLineContexts.line, dataInfo);
    				var meta = amountCumulateLineChart.controller.getDatasetMeta(0);
        			triggerMouseEvent(amountCumulateLineChart, "click", meta.data[meta.data.length-1]);
    			}else if (params.statsType == '1' && params.statsField == 'count') {
    				var countCumulateLineChart = new Chart(countCumulateLineContexts.line, dataInfo);
    				var meta = countCumulateLineChart.controller.getDatasetMeta(0);
        			triggerMouseEvent(countCumulateLineChart, "click", meta.data[meta.data.length-1]);
    			}else if (params.statsType == '2' && params.statsField == 'amount') {
    				
    				dataInfo.data.datasets[0].data = yxw.fromFenToYuan(dataTarget);
    				
    				var amountMonthLineChart = new Chart(amountMonthLineContexts.line, dataInfo);
    				
    				var info = dataInfo.data.labels[dataInfo.data.labels.length - 1] + "单月增长量：" + dataTarget[dataTarget.length - 1];
        			$('#amount-month-line-text').text(info);
        			
    				var meta = amountMonthLineChart.controller.getDatasetMeta(0);
        			triggerMouseEvent(amountMonthLineChart, "click", meta.data[meta.data.length-1]);
    			}else if (params.statsType == '2' && params.statsField == 'count') {
    				var countMonthLineChart = new Chart(countMonthLineContexts.line, dataInfo);
    				
    				var info = dataInfo.data.labels[dataInfo.data.labels.length - 1] + "单月增长量：" + dataTarget[dataTarget.length - 1];
        			$('#count-month-line-text').text(info);
    				
        			var meta = countMonthLineChart.controller.getDatasetMeta(0);
        			triggerMouseEvent(countMonthLineChart, "click", meta.data[meta.data.length-1]);
    			}
    			
     		}
    	 });
    }
    
})();


