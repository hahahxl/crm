<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <link href="../../jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="../../jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>
    <script type="text/javascript" src="../../jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="../../jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="../../jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="../../jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <link rel="stylesheet" type="text/css" href="../../jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="../../jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="../../jquery/bs_pagination/en.js"></script>

    <script type="text/javascript">
        /*
         * 市场活动的创建
         * */
        $(function () {
            pageList(1, 5);
            $("#addBtn").click(function () {
                $(".time").datetimepicker({
                    minView: "month",
                    language: 'zh-CN',
                    format: 'yyyy-mm-dd',
                    autoclose: true,
                    todayBtn: true,
                    pickerPosition: "bottom-left"
                });

                $.ajax({
                    url: "${pageContext.request.contextPath}/workbench/activity/getUserList.do",
                    type: "get",
                    dataType: "json",
                    data: {},
                    success: function (data) {
                        var html = "";
                        $.each(data, function (i, n) {
                            html += "<option value='" + n.id + "'>" + n.name + "</option>"
                        });
                        $("#create-marketActivityOwner").html(html);

                    }
                });
                /*设置下拉框自动填充当前登录用户*/
                var id = "${user.id}"
                $("#create-marketActivityOwner").val(id);
                $("#createActivityModal").modal("show");
            });
            $("#savaBtn").click(function () {

                $.ajax({
                    url: "${pageContext.request.contextPath}/workbench/activity/save.do",
                    type: "post",
                    dataType: "json",
                    data: {
                        "owner": $.trim($("#create-marketActivityOwner").val()),
                        "name": $.trim($("#create-marketActivityName").val()),
                        "startDate": $.trim($("#create-startTime").val()),
                        "endDate": $.trim($("#create-endTime").val()),
                        "cost": $.trim($("#create-cost").val()),
                        "description": $.trim($("#create-describe").val())
                    },
                    success: function (data) {
                        if (data.success) {
                            /*  成功
                             2、刷新列表
                             1、关闭模态窗口
                             */
                            pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
                                , $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                            $("#addForm")[0].reset()
                            $("#createActivityModal").modal("hide");
                            //alert("创建成功");
                        } else {
                            alert("创建失败");
                        }
                    }
                });
            });

            /*复选框的选中与全选*/
            $("#qx").click(function () {
                $("input[name='xz']").prop("checked", this.checked);
            });
            //因为动态生成的元素，是不能够以普通绑定事件的形式来进行操作的
            /*
             动态生成的元素，我们要以on方法的形式来触发事件
             语法：
             $(需要绑定元素的有效的外层元素).on(绑定事件的方式,需要绑定的元素的jquery对象,回调函数)
             */
            $("#activityBody").on("click", $("input[name='xz']"), function () {
                $("#qx").prop("checked", ($("input[name='xz']:checked").length ) == ( $("input[name='xz']").length));
            });

        });
        /**
         * 市场活动的修改
         */
        $(function () {
            $("#editBtn").click(function () {
                var $xz = $("input[name='xz']:checked");
                var param = "";
                if ($xz.length <= 0) {
                    alert("请选择你要修改的记录！！！");
                }
                if ($xz.length > 1) {
                    alert("请选择一项进行修改！！！");
                }
                var id = $xz.val();

                $.ajax({
                    url: "${pageContext.request.contextPath}/workbench/activity/getUserListAndActivity.do",
                    type: "post",
                    dataType: "json",
                    data: {"id": id},
                    success: function (data) {
                        var html = "";
                        $.each(data.userList, function (i, n) {
                            html += " <option value='" + n.id + "'>" + n.name + "</option>";
                        });
                        $("#edit-owner").html(html);
                        $("#edit-id").val(data.activity.id);
                        $("#edit-name").val(data.activity.name);
                        $("#edit-owner").val(data.activity.owner)
                        $("#edit-startTime").val(data.activity.startDate);
                        $("#edit-endTime").val(data.activity.endDate);
                        $("#edit-cost").val(data.activity.cost);
                        $("#edit-describe").val(data.activity.description);

                        $("#editActivityModal").modal("show");
                    }
                });

            });
            /*修改活动列表*/
            $("#updateBtn").click(function () {
                $.ajax({
                    url: "${pageContext.request.contextPath}/workbench/activity/update.do",
                    type: "post",
                    dataType: "json",
                    data: {
                        "id": $("#edit-id").val(),
                        "owner": $.trim($("#edit-owner").val()),
                        "name": $.trim($("#edit-name").val()),
                        "startDate": $.trim($("#edit-startTime").val()),
                        "endDate": $.trim($("#edit-endTime").val()),
                        "cost": $.trim($("#edit-cost").val()),
                        "description": $.trim($("#edit-describe").val())

                    },
                    success: function (data) {
                        if (data.success) {
                            /*  成功
                             2、刷新列表
                             1、关闭模态窗口
                             */
                            pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
                                , $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));


                            //pageList(1, 5);
                            //alert("修改成功");
                            $("#editActivityModal").modal("hide");

                        } else {
                            alert("创建失败");
                        }
                    }
                });
            });
        });
        /**
         * 市场活动的删除
         */
        $(function () {
            /*
             * 执行市场活动的删除*/
            $("#deleteBtn").click(function () {
                var $xz = $("input[name='xz']:checked");
                var param = "";
                if ($xz.length <= 0) {
                    alert("请选中你要删除的记录");
                } else {
                    if (confirm("你确定要删除吗？")) {
                        for (var i = 0; i < $xz.length; i++) {
                            param += "id=" + $($xz[i]).val();
                            if (i < $xz.length - 1) {
                                param += "&";
                            }
                        }
                    }

                    $.ajax({
                        url: "${pageContext.request.contextPath}/workbench/activity/delete.do",
                        type: "post",
                        dataType: "json",
                        data: param,
                        success: function (data) {
                            if (data.success) {
                                /*  成功
                                 2、刷新列表

                                 */
                                pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));


                                // pageList(1, 5);
                            } else {
                                alert("删除失败");
                            }
                        }
                    });
                }

            });
        });
        /**
         * 市场活动的条件查询
         */
        $(function () {
            $("#seachBtn").click(function () {
                pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
            });
        });
        /**
         * pageList方法
         * */
        function pageList(pageNo, pageSize) {
            $("#qz").prop("checked", false);
            $.ajax({
                url: "${pageContext.request.contextPath}/workbench/activity/pageList.do",
                type: "get",
                dataType: "json",
                data: {
                    "pageNo": pageNo,
                    "pageSize": pageSize,
                    "name": $.trim($("#search-name").val()),
                    "owner": $.trim($("#search-owner").val()),
                    "startDate": $.trim($("#search-startDate").val()),
                    "endDate": $.trim($("#search-endDate").val())
                },
                success: function (data) {

                    var html = "";
                    $.each(data.dataList, function (i, n) {
                        // alert("id值" + n.id+"n的owner"+n.owner);
                        html += '<tr class="active">';
                        html += '<td><input name="xz" value="' + n.id + '" type="checkbox"/></td>';
                        html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'${pageContext.request.contextPath}/workbench/activity/detail.do?id=' + n.id + '\';">' + n.name + '</a>';
                        html += '</td>';
                        html += '<td>' + n.owner + '</td>';
                        html += '<td>' + n.startDate + '</td>';
                        html += '<td>' + n.endDate + '</td>';
                        html += '</tr>';
                    });
                    $("#activityBody").html(html);
                    /*计算总页数*/
                    var totalPages = (data.total % pageSize) == 0 ? data.total / pageSize : parseInt(data.total / pageSize) + 1
                    /*数据处理完毕后加入分页插件*/
                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.total, // 总记录条数

                        visiblePageLinks: 5, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        //该回调函数时在，点击分页组件的时候触发的
                        onChangePage: function (event, data) {
                            pageList(data.currentPage, data.rowsPerPage);
                        }
                    });
                }
            });
        }

    </script>
</head>
<body>

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form id="addForm" class="form-horizontal" role="form">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-marketActivityOwner">

                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-marketActivityName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-startTime">
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-endTime">
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="savaBtn">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">
                    <input type="hidden" id="edit-id">
                    <input type="hidden" id="editby">
                    <div class="form-group">
                        <label for="edit-owner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-owner">
                                <%--  <option>zhajjjjjjjjjjjjngsan</option>
                                  <option>lisi</option>
                                  <option>wangwu</option>--%>
                            </select>
                        </div>
                        <label for="edit-name" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-startTime">
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-endTime">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="edit-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="updateBtn">更新</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" id="search-name" type="text">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" id="search-owner" type="text">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control" type="text" id="search-startDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control" type="text" id="search-endDate">
                    </div>
                </div>

                <button type="button" class="btn btn-default" id="seachBtn">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">

                <%--data-toggle  表示将要打开一个模态窗口
                     data-target 表示将要打开那个模态窗口 通过#id形式找到该窗口
                     data-toggle="modal" data-target="#createActivityModal"
                     data-toggle="modal" data-target="#editActivityModal"
                     --%>
                <button type="button" id="addBtn" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>
                    创建
                </button>
                <button type="button" id="editBtn" class="btn btn-default"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" id="deleteBtn" class="btn btn-danger"><span
                        class="glyphicon glyphicon-minus"></span> 删除
                </button>
            </div>

        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input id="qx" type="checkbox"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="activityBody">
                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">
            <div id="activityPage"></div>
            <div id="ssss"></div>
        </div>

    </div>

</div>
</body>
</html>