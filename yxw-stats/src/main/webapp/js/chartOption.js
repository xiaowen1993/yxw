var option = {
	line : {
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : [],
			left : '20%',
			right : '10%'
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		xAxis : [ {
			type : 'category',
			boundaryGap : false,
			data : []
		} ],
		yAxis : [ {
			type : 'value'
		} ],
		series : []
	},
	bargraph : {
		backgroundColor : '#FFF',
		legend : {
			data : [ '微信', '支付宝' ],
			align : 'right',
			right : 10
		},
		tooltip : {},
		xAxis : {
			data : [],
			name : '',
			axisLabel : {
				rotate : 0, // 刻度旋转45度角
				textStyle : {
					fontSize : 10
				}
			},
			silent : false,
			axisLine : {
				onZero : true
			},
			splitLine : {
				show : false
			},
			splitArea : {
				show : false
			}
		},
		yAxis : {
			name : '',
			inverse : false,
			splitArea : {
				show : false
			}
		},
		grid : {
			left : 100
		},
		series : [ {
			name : '微信绑卡数',
			type : 'bar',
			stack : 'one',
			itemStyle : {
				emphasis : {
					barBorderWidth : 1,
					shadowBlur : 10,
					shadowOffsetX : 0,
					shadowOffsetY : 0,
					shadowColor : 'rgba(0,0,0,5)'
				}
			},
			data : []
		}, {
			name : '支付宝绑卡数',
			type : 'bar',
			stack : 'one',
			itemStyle : {
				emphasis : {
					barBorderWidth : 1,
					shadowBlur : 10,
					shadowOffsetX : 0,
					shadowOffsetY : 0,
					shadowColor : 'rgba(0,0,0,5)'
				}
			},
			data : []
		} ]
	},
	pie : {
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} ({d}%)"
		},
		legend : {
			orient : 'horizontal',
			left : 150,
			right : 10,
			data : []
		},
		series : [ {
			name : '区域',
			type : 'pie',
			radius : '55%',
			center : [ '50%', '60%' ],
			data : [],
			itemStyle : {
				emphasis : {
					shadowBlur : 10,
					shadowOffsetX : 0,
					shadowColor : 'rgba(0, 0, 0, 0.5)'
				}
			}
		} ]
	}
}
