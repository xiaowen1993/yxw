var mainDiagnosis = {
    init: function() {
        /**初始化绑定事件*/
        $("#searchKeyword").focus();

        $('#search_btn').click(function(event) {
        	event.stopPropagation();
    		event.preventDefault();
        	var searchKeyword = $("#searchKeyword").val();
        	mainDiagnosis.matchMainDiagnosisName(searchKeyword);
        });
    },

    matchMainDiagnosisName: function(searchStr) {
    	console.log(searchStr);
    	$("#searchList").html("")
    	 
    	var htmlStrNone = '<div class="recent-search-empty">没有搜到相关疾病名称，请重新输入搜索值</div>';
    	
        if (searchStr == "") {
            return;
        } else {
            
        	var reg = /[\u4E00-\u9FA5]+$/;
            if (!reg.test(searchStr)) {
            	$("#searchList").html(htmlStrNone);
            	$("#searchList").show();
                return;
            }
            
           
            $("#searchStr").val(searchStr);
            var url = base.appPath + "api/matchMainDiagnosisName";
            var datas = {keyword: searchStr};
            $Y.loading.show();
            jQuery.ajax({
                type: 'POST',
                url: url,
                data: datas,
                dataType: 'json',
                timeout: 120000,
                success: function(data) {
                    $Y.loading.hide();
                    console.log(data);
                    var htmlStr = "";
                    var results = data;
                    if (results.length > 0) {
                        htmlStr += '<ul class="yx-list result-hospital-list" >';
                        $.each(results, function(k, v) {
                        	var mainDiagnosisArr = v.split("=");
                            var mainDiagnosisCode = mainDiagnosisArr[0];
                            var mainDiagnosisName = mainDiagnosisArr[1];

                            htmlStr += "<li mainDiagnosisCode-data=" + mainDiagnosisCode + " mainDiagnosisName-data=" + mainDiagnosisName + " >";
                            htmlStr += "<span class='title'>" + mainDiagnosisName + "</span>&nbsp"
                            
                            htmlStr += "</li>";
                        });
                        htmlStr = htmlStr + '<li><span></span>&nbsp</li>' + '</ul>';
                    } else {
                        htmlStr += '<div class="recent-search-empty">没有搜到相关疾病名称，请重新输入搜索值</div>';
                    }
                    $("#searchList").html(htmlStr);
                    $("#searchList").show();
                    
                    mainDiagnosis.bindEvent();
                },
                error: function(data) {
                    $Y.loading.hide();
                }
            });
        }
    },

    bindEvent: function() {
        if ($('#searchList ul li').length > 0) {
            $('#searchList ul li').click(function() {
                var mainDiagnosisCode = $(this).attr('mainDiagnosisCode-data');
                var mainDiagnosisName = $(this).attr('mainDiagnosisName-data');
                if(!mainDiagnosisCode){
                	return;
                }
                
                $('#mainDiagnosisCode').val(mainDiagnosisCode);
                $('#mainDiagnosisName').val(mainDiagnosisName);
                
                $("#searchKeyword").val(mainDiagnosisName);
                
                $("#searchList").hide();
            });
        }
    },

};

$(function() {
	mainDiagnosis.init();
});

$.fn.formEdit = function(data) {
    return this.each(function() {
        var elementDom;
        var elementDomName;
        if (data == null) {
            this.reset();
            return;
        }
        for (var i = 0; i < this.length; i++) {
            elementDom = this.elements[i];
            elementDomName = elementDom.name;
            if (data[elementDomName] == undefined) {
                continue;
            }
            elementDom.value = data[elementDomName];
        }
    });
};