var index = {
	// 区域医院饼图
	hospitaChart: echarts.init(document.getElementById('hospital')),

	// 绑卡数柱状图
	cardChart: echarts.init(document.getElementById('card')),

	// 订单数柱状图
	orderChart: echarts.init(document.getElementById('order')),

	// 订单总额柱状图（暂时又不要了）
	// amountChart : echarts.init(document.getElementById('amount')),

	// 关注数柱状图
	subscribeChart: echarts.init(document.getElementById('subscribe')),

	// 订单数前十的医院 关注、绑卡、订单数列举。 柱状图
	// top10Chart : echarts.init(document.getElementById('top10')),

	hostpitalOption: {
		/*
		 * title : { text : '区域医院统计', subtext : '区域所有已签约医院', x : 'left' },
		 */
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b} ({d}%)"
		},
		legend: {
			orient: 'horizontal',
			left: 40,
			right: 40,
			data: []
		},
		toolbox: {
			feature: {
				saveAsImage: {
					name: '区域医院统计',
					title: '保存'
				}
			},
		},
		series: [ {
			name: '区域',
			type: 'pie',
			radius: '55%',
			center: [ '50%', '60%' ],
			data: [],
			itemStyle: {
				emphasis: {
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)'
				}
			}
		} ]
	},
	cardOption: {
		/*
		 * title : { text : '医院绑卡统计', x : 'left' },
		 */
		backgroundColor: '#FFF',
		legend: {
			data: [ '微信绑卡数', '支付宝绑卡数' ],
			align: 'right',
			right: 40
		},
		tooltip: {},
		toolbox: {
			feature: {
				saveAsImage: {
					name: '医院绑卡统计',
					title: '保存'
				}
			},
		},
		xAxis: {
			data: [],
			name: '医院',
			axisLabel: {
				interval: 0,
				rotate: 25, // 刻度旋转45度角
				textStyle: {
					fontSize: 12
				}
			},
			silent: false,
			axisLine: {
				onZero: true
			},
			splitLine: {
				show: false
			},
			splitArea: {
				show: false
			}
		},
		yAxis: {
			name: '绑卡数',
			inverse: false,
			splitArea: {
				show: false
			}
		},
		grid: {
			left: 100,
			bottom: 100
		},
		series: [ {
			name: '微信绑卡数',
			type: 'bar',
			stack: 'one',
			itemStyle: {
				emphasis: {
					barBorderWidth: 1,
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowOffsetY: 0,
					shadowColor: 'rgba(0,0,0,5)'
				}
			},
			data: []
		}, {
			name: '支付宝绑卡数',
			type: 'bar',
			stack: 'one',
			itemStyle: {
				emphasis: {
					barBorderWidth: 1,
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowOffsetY: 0,
					shadowColor: 'rgba(0,0,0,5)'
				}
			},
			data: []
		} ]
	},
	orderOption: {
		/*
		 * title : { text : '订单数', subtext : '无关医院无关区域', x : 'left' }, tooltip : {
		 * trigger : 'item', formatter : "{a} <br/>{b} : {c}笔 ({d}%)" }, series : [ {
		 * name : '订单类型', type : 'pie', radius : '55%', center : [ '50%', '60%' ],
		 * data : [ { value : 335, name : '挂号订单', selected : true }, { value :
		 * 310, name : '门诊缴费' }, { value : 234, name : '住院押金' } ], itemStyle : {
		 * emphasis : { shadowBlur : 10, shadowOffsetX : 0, shadowColor :
		 * 'rgba(0, 0, 0, 0.5)' } } } ]
		 */
		/*
		 * title : { text : '医院订单数统计', x : 'left' },
		 */
		backgroundColor: '#FFF',
		legend: {
			data: [ '挂号订单数', '门诊订单数', '押金订单数' ],
			align: 'right',
			right: 40
		},
		tooltip: {},
		toolbox: {
			feature: {
				saveAsImage: {
					name: '订单数统计',
					title: '保存'
				}
			},
		},
		xAxis: {
			data: [],
			name: '医院',
			axisLabel: {
				interval: 0,
				rotate: 25, // 刻度旋转45度角
				textStyle: {
					fontSize: 12
				}
			},
			silent: false,
			axisLine: {
				onZero: true
			},
			splitLine: {
				show: false
			},
			splitArea: {
				show: false
			}
		},
		yAxis: {
			name: '订单数',
			inverse: false,
			splitArea: {
				show: false
			}
		},
		grid: {
			left: 100,
			bottom: 100
		},
		visualMap: {
			type: 'continuous',
			dimension: 1,
			text: [ 'High', 'Low' ],
			inverse: true,
			itemHeight: 200,
			calculable: true,
			min: 0,
			max: 60000,
			top: 60,
			left: 10,
			inRange: {
				colorLightness: [ 0.4, 0.8 ]
			},
			outOfRange: {
				color: '#bbb'
			},
			controller: {
				inRange: {
					color: '#2f4554'
				}
			}
		},
		series: [ {
			name: '挂号订单数',
			type: 'bar',
			stack: 'one',
			itemStyle: {
				emphasis: {
					barBorderWidth: 1,
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowOffsetY: 0,
					shadowColor: 'rgba(0,0,0,5)'
				}
			},
			data: []
		}, {
			name: '门诊订单数',
			type: 'bar',
			stack: 'one',
			itemStyle: {
				emphasis: {
					barBorderWidth: 1,
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowOffsetY: 0,
					shadowColor: 'rgba(0,0,0,5)'
				}
			},
			data: []
		}, {
			name: '押金订单数',
			type: 'bar',
			stack: 'one',
			itemStyle: {
				emphasis: {
					barBorderWidth: 1,
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowOffsetY: 0,
					shadowColor: 'rgba(0,0,0,5)'
				}
			},
			data: []
		} ]
	},
	subscribeOption: {
		/*
		 * title : { text : '医院关注数统计', x : 'left' },
		 */
		backgroundColor: '#FFF',
		legend: {
			data: [ '微信关注数', '支付宝关注数' ],
			align: 'right',
			right: 40
		},
		tooltip: {},
		toolbox: {
			feature: {
				saveAsImage: {
					name: '关注数统计',
					title: '保存'
				}
			},
		},
		xAxis: {
			data: [],
			name: '医院',
			axisLabel: {
				interval: 0,
				rotate: 25, // 刻度旋转45度角
				textStyle: {
					fontSize: 12
				}
			},
			silent: false,
			axisLine: {
				onZero: true
			},
			splitLine: {
				show: false
			},
			splitArea: {
				show: false
			}
		},
		yAxis: {
			name: '关注数',
			inverse: false,
			splitArea: {
				show: false
			}
		},
		grid: {
			left: 100,
			bottom: 100
		},
		visualMap: {
			type: 'continuous',
			dimension: 1,
			text: [ 'High', 'Low' ],
			inverse: true,
			itemHeight: 200,
			calculable: true,
			min: 0,
			max: 60000,
			top: 60,
			left: 10,
			inRange: {
				colorLightness: [ 0.4, 0.8 ]
			},
			outOfRange: {
				color: '#bbb'
			},
			controller: {
				inRange: {
					color: '#2f4554'
				}
			}
		},
		series: [ {
			name: '微信关注数',
			type: 'bar',
			stack: 'one',
			itemStyle: {
				emphasis: {
					barBorderWidth: 1,
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowOffsetY: 0,
					shadowColor: 'rgba(0,0,0,5)'
				}
			},
			data: []
		}, {
			name: '支付宝关注数',
			type: 'bar',
			stack: 'one',
			itemStyle: {
				emphasis: {
					barBorderWidth: 1,
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowOffsetY: 0,
					shadowColor: 'rgba(0,0,0,5)'
				}
			},
			data: []
		} ]
	},

	init: function() {
		index.hospitaChart.setOption(index.hostpitalOption);
		index.cardChart.setOption(index.cardOption);
		index.orderChart.setOption(index.orderOption);
		index.subscribeChart.setOption(index.subscribeOption);
		index.loadDatas();
	},

	loadDatas: function() {
		$
				.get('/getAllResume')
				.done(
						function(data) {
							console.log(data);
							if (data.status == "OK") {
								var detail = data.message;
								// 医院总数
								$('#hospitalCount').text(detail.hospitalCount);

								// 区域医院(饼图)
								$.each(detail.areaData, function(k, val) {
									val.name = val.name + "：" + val.value + "家医院";
								});
								index.hospitaChart.setOption({
									legend: {
										data: detail.areaData,
										formatter: '{name}'
									},
									series: [ {
										data: detail.areaData
									} ]
								});

								// 关注数
								$('#wxSubscribe').text(detail.wxSubscribe != 0 ? yxw.number_format(detail.wxSubscribe, 0, '.', ',') : '暂无');
								$('#aliSubscribe').text(detail.aliSubscribe != 0 ?  yxw.number_format(detail.aliSubscribe, 0, '.', ',') : '暂无');
								index.subscribeChart.setOption({
									xAxis: {
										data: detail.subscribeData.hospitals
									},
									series: [ {
										data: detail.subscribeData.wxSubscribeCount
									}, {
										data: detail.subscribeData.aliSubscribeCount
									} ]
								});

								// 绑卡总数
								$('#cardCount').text(yxw.number_format(detail.cardCount, 0, '.', ','));

								// 绑卡(图)
								index.cardChart.setOption({
									xAxis: {
										data: detail.cardData.hospitals
									},
									series: [ {
										data: detail.cardData.wxCardCount
									}, {
										data: detail.cardData.aliCardCount
									} ]
								});

								// 订单总数
								var depositCountArr = [], clinicCountArr = [], regCountArr = [];// 总订单数，不区分微信支付宝
								var depositAmountArr = [], clinicAmountArr = [], regAmountArr = [];// 总订单金额，不区分微信支付宝
								var wxOrderCount = 0, aliOrderCount = 0, orderAmount = 0;
								var depositWxCountArr = [], clinicWxCountArr = [], regWxCountArr = [], depositAliCountArr = [], clinicAliCountArr = [], regAliCountArr = [];
								var hospitals = [];
								$.each(detail.depositInfos, function(k, val) {
									depositCountArr.push(val.cumulateNoPayCount + val.cumulateTotalCount);
									depositAmountArr.push(val.cumulateTotalAmount);
									depositWxCountArr.push(val.cumulateWxTotalCount + val.cumulateWxNoPayCount);
									depositAliCountArr.push(val.cumulateAliTotalCount + val.cumulateAliNoPayCount);
								});
								// console.log(depositCountArr);
								$.each(detail.clinicInfos, function(k, val) {
									hospitals.push(k);
									clinicCountArr.push(val.cumulateNoPayCount + val.cumulateTotalCount);
									clinicAmountArr.push(val.cumulateTotalAmount);
									clinicWxCountArr.push(val.cumulateWxTotalCount + val.cumulateWxNoPayCount);
									clinicAliCountArr.push(val.cumulateAliTotalCount + val.cumulateAliNoPayCount);
								});
								// console.log(clinicCountArr);
								$.each(detail.regInfos, function(k, val) {
									regCountArr.push(val.cumulateNoPayCount + val.cumulateTotalCount);
									regAmountArr.push(val.cumulateTotalAmount);
									regWxCountArr.push(val.cumulateWxTotalCount + val.cumulateWxNoPayCount);
									regAliCountArr.push(val.cumulateAliTotalCount + val.cumulateAliNoPayCount);
								});
								// console.log(regCountArr);

								// console.log(depositAliCountArr);
								// console.log(clinicAmountArr);
								// console.log(regAmountArr);
								for (var i = 0; i < depositWxCountArr.length; i++) {
									wxOrderCount += depositWxCountArr[i];
									wxOrderCount += clinicWxCountArr[i];
									wxOrderCount += regWxCountArr[i];

									aliOrderCount += depositAliCountArr[i];
									aliOrderCount += clinicAliCountArr[i];
									aliOrderCount += regAliCountArr[i];

									orderAmount += depositAmountArr[i];
									orderAmount += clinicAmountArr[i];
									orderAmount += regAmountArr[i];
								}

								$('#wxOrderCount').text(yxw.number_format(wxOrderCount, 0, '.', ','));
								$('#aliOrderCount').text(yxw.number_format(aliOrderCount, 0, '.', ','));
//								$('#orderAmount').text('￥' + ((orderAmount) / 100).toFixed(2) + '元');
								$('#orderAmount').text('￥' + yxw.number_format(((orderAmount) / 100).toFixed(2), 2, '.', ',') + '元');

								index.orderChart.setOption({
									xAxis: {
										data: hospitals
									},
									series: [ {
										data: regCountArr
									}, {
										data: clinicCountArr
									}, {
										data: depositCountArr
									} ]
								});
							}
						});

	},
};

$(function() {

	index.init();
});

String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};