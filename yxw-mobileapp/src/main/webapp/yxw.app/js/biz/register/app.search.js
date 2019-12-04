var search = {
    init: function() {
        /**初始化绑定事件*/
        $("#searchKeyword").focus();

        $('#search_btn').click(function() {
            var type = $(this).attr("row-data");
            if (type == 'dept') {
                var searchKeyword = $("#searchKeyword").val();
                search.forDepts(searchKeyword, 'btn');
            } else if (type == 'doctor') {
                var searchKeyword = $("#searchKeyword").val();
                search.forDoctors(searchKeyword, 'btn');
            } else {
                return;
            }
        });
    },

    forDoctors: function(searchStr, controllerType) {
        if (searchStr == "") {
            return;
        } else {
            if (!controllerType || controllerType != 'btn') {
                var reg = /^[0-9]+|[\u4E00-\u9FA5]+$/;
                if (!reg.test(searchStr)) {
                    return false;
                }
            }
            $("#searchList").html("")
            $("#searchStr").val(searchStr);
            var url = base.appPath + "easyhealth/register/doctor/searchDoctors";
            var datas = $("#paramsForm").serializeArray();
            $Y.loading.show();
            jQuery.ajax({
                type: 'POST',
                url: url,
                data: datas,
                dataType: 'json',
                timeout: 120000,
                success: function(data) {
                    $Y.loading.hide();
                    var htmlStr = "";
                    var doctors = data.message.doctors;
                    if (doctors.length > 0) {
                        htmlStr += '<ul class="yx-list result-hospital-list" >';
                        $.each(doctors, function(k, doctor) {
                            var doctorCode = doctor.doctorCode,
                                deptCode = doctor.deptCode,
                                doctorName = doctor.doctorName,
                                deptName = doctor.deptName;

                            htmlStr += "<li doctorCode-data=" + doctorCode + " deptCode-data=" + deptCode + " >";
                            htmlStr += "<span class='title'>" + doctorName + "</span>&nbsp"
                            if (deptName != null && deptName != "null" && deptName != "") {
                                htmlStr += "<span class='label skinBgColor'>" + deptName + "</span>"
                            }
                            htmlStr += "</li>";
                        });
                        htmlStr = htmlStr + '</ul>';
                    } else {
                        htmlStr += '<div class="recent-search-empty">没有搜到该医生，请重新输入搜索值</div>';
                    }
                    $("#searchList").html(htmlStr);

                    search.bindDoctorEvent();
                },
                error: function(data) {
                    $Y.loading.hide();
                }
            });
        }
    },

    bindDoctorEvent: function() {
        if ($('#searchList ul li').length > 0) {
            $('#searchList ul li').click(function() {
                var doctorCode = $(this).attr('doctorCode-data');
                var deptCode = $(this).attr('deptCode-data');
                search.toRegSourceInfo(doctorCode, deptCode);
            });
        }
    },

    toRegSourceInfo: function(doctorCode, deptCode) {
        $("#deptCode").val(deptCode);
        $("#doctorCode").val(doctorCode);
        $("#paramsForm").attr("action", base.appPath + "easyhealth/register/doctor/index");
        $("#paramsForm").submit();
    },

    forDepts: function(searchStr, controllerType) {
        if (searchStr == "") {
            return;
        } else {
            if (!controllerType || controllerType != 'btn') {
                //var reg = /^[0-9]+|[\u4E00-\u9FA5]+|[a-z]+|[A-Z]+$/; 
                var reg = /^[0-9]+|[\u4E00-\u9FA5]+$/;
                if (!reg.test(searchStr)) {
                    return false;
                }
            }
            $("#searchList").html("");
            $("#searchStr").val(searchStr);
            var datas = $("#paramsForm").serializeArray();
            $Y.loading.show();
            jQuery.ajax({
                type: 'POST',
                url: base.appPath + "easyhealth/register/doctor/searchDepts",
                data: datas,
                dataType: 'json',
                timeout: 120000,
                success: function(data) {
                    $Y.loading.hide();
                    var listDom = $("#searchList");
                    var htmlStr = search.showDepts(data);
                    $(listDom).html(htmlStr);

                    search.bindDeptEvent();
                },
                error: function(data) {
                    $Y.loading.hide();
                }
            });
        }
    },

    showDepts: function(data) {
        var htmlStr = "",
            target = "";
        var entityList = data.message.depts;
        if (entityList && entityList.length > 0) {
            htmlStr += '<ul class="yx-list result-hospital-list" >';
            for (var i = 0; i < entityList.length; i++) {
                var liTagItem = [
                    '<li class="arrow" row-data=' + entityList[i] + '>',
                    '<div class="keshi-info">',
                    '<p class="hospital-name">' + entityList[i] + '</p>',
                    '</div>',
                    '</li>'
                ];

                target += liTagItem.join("");
            }
            htmlStr += target + '</ul>';
        } else {
            htmlStr += '<div class="recent-search-empty">没有搜到该科室,请重新输入搜索值</div>';
        }
        return htmlStr;
    },

    bindDeptEvent: function() {
        if ($('#searchList ul li').length > 0) {
            $('#searchList ul li').click(function() {
                var deptNameKeyVal = $(this).attr('row-data');
                search.goSearchDeptNext(deptNameKeyVal);
            });
        }
    },

    goSearchDeptNext: function(obj) {
        $("#deptNameKey").val(obj);
        var datas = $("#paramsForm").serializeArray();
        $Y.loading.show();
        jQuery.ajax({
            type: 'POST',
            url: base.appPath + "easyhealth/register/doctor/deptInfo",
            data: datas,
            dataType: 'json',
            timeout: 120000,
            success: function(data) {
                $Y.loading.hide();
                if (data.message.dept) {
                    var dept = data.message.dept;
                    if (data.message.isGoRegInfo) {
                        search.goDeptRegInfo(dept);
                    } else {
                        search.geSubDepts(dept);
                    }
                }
            },
            error: function(data) {
                $Y.loading.hide();
            }
        });
    },

    goDeptRegInfo: function(obj) {
//        console.log(obj);
        $("#paramsForm").formEdit(obj);
        $("#paramsForm").attr("action", base.appPath + "easyhealth/register/doctor/index");
        $("#paramsForm").submit();
    },

    geSubDepts: function(obj) {
        $("#paramsForm").formEdit(obj);
        $("#parentDeptCode").val(obj.deptCode);
        $("#parentDeptName").val(obj.deptName);
        $("#paramsForm").attr("action", base.appPath + "easyhealth/register/doctor/querySubDepts");
        $("#paramsForm").submit();
    }
};

$(function() {
    search.init();
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