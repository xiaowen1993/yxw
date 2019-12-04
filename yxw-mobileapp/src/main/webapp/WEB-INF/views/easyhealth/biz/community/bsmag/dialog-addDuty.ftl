
<!--弹窗内容-->
<div id="addDuty-dialog" class="addDuty-dialog">

    <div class="widget-content form-check">
        <div class="row-fluid">
            <div class="controls mCustomScrollbar_u form-check">
                <table class="table table-bordered table-textCenter table-striped table-hover">
                <thead>
                <tr>
                    <th width="12%">星期</th>
                    <th width="10%">午段</th>
                    <th width="13%">专家</th>
                    <th width="15%">职称</th>
                    <th width="15%">专科</th>
                    <th width="20%">派出医院</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td style="text-align: center;">
                        <div class="my_select">
                        <select name="week" id="week" class="span10">
                            <option value="1" <#if week == "1">selected="selected"</#if>>星期一</option>
                            <option value="2" <#if week == "2">selected="selected"</#if>>星期二</option>
                            <option value="3" <#if week == "3">selected="selected"</#if>>星期三</option>
                            <option value="4" <#if week == "4">selected="selected"</#if>>星期四</option>
                            <option value="5" <#if week == "5">selected="selected"</#if>>星期五</option>
                            <option value="6" <#if week == "6">selected="selected"</#if>>星期六</option>
                            <option value="7" <#if week == "7">selected="selected"</#if>>星期天</option>
                        </select>
                    </div>
                    </td>
                    <td style="text-align: center;">
                        <div class="my_select">
                            <select name="timeSlot" id="timeSlot" class="span10">
                                <option value="1" <#if timeSlot == "1">selected="selected"</#if>>上午</option>
                                <option value="2" <#if timeSlot == "2">selected="selected"</#if>>下午</option>
                            </select>
                        </div>
                    </td>
                    <td><input type="text" class="span10 input-sheK center" name="doctorName" value="${doctorName}" id="doctorName" placeholder="" style="height: 38px;"/></td>
                    <td><input type="text" class="span10 input-sheK center" name="position" value="${position}"  id="position"  placeholder="" style="height: 38px;"/></td>
                    <td><input type="text" class="span10 input-sheK center" name="specialty" value="${specialty}" id="specialty"  placeholder="" style="height: 38px;"/></td>
                    <td><input type="text" class="span10 input-sheK center" name="hospitalName" value="${hospitalName}" id="hospitalName"  placeholder="" style="height: 38px;"/></td>
                    <input type="hidden" name="id" id="id" value="${id }"/>
                </tr>
                <tr></tr><tr></tr>
                </tbody>
            </table>
            </div>
        </div>
    </div>

<div class="controlsBtnBox rowBg center">
    <button class="btn-save">保存</button>
    <div class="spaceW15"></div>
    <button class="btn-remove" >取消</button>
</div>

</div>
<!--弹窗内容-->
