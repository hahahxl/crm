<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script>
        $(function () {
            /*清空文本框的内容*/
            $("#loginAct").val("");
            /*让文本框自动获得焦点*/
            $("#loginAct").focus();

            $("#subButton").click(function () {
                login();
            });
            /*敲回车登录*/
            $(window).keydown(function (event) {
                if (event.keyCode == 13) {
                    login();
                }
            })
        });
        function login() {
            var loginAct = $.trim($("#loginAct").val());
            var loginPwd = $.trim($("#loginPwd").val());
            if (loginPwd == "" || loginAct == "") {
                $("#msg").html("用户名或密码不能为空");
                /*
                 如果用户名或密码不能为空，接下来的代码无需继续运行
                 所以应该及时制止
                 */
                return false;
            }
            $.ajax({
                url: "${pageContext.request.contextPath}/user/setting/login.do",
                type: "post",
                dataType: "json",
                data: {"loginAct": loginAct, "loginPwd": loginPwd},
                success: function (data) {

                    if (data.success) {
                        window.location.href = "workbench/index.jsp"
                    } else {
                        $("#msg").html(data.msg);
                    }
                }
            });

        }
    </script>
</head>
<body>
<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
    <img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
    <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">
        CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
    <div style="position: absolute; top: 0px; right: 60px;">
        <div class="page-header">
            <h1>登录</h1>
        </div>
        <form action="workbench/index.jsp" class="form-horizontal" role="form">
            <div class="form-group form-group-lg">
                <div style="width: 350px;">
                    <input class="form-control" type="text" id="loginAct" placeholder="用户名">
                </div>
                <div style="width: 350px; position: relative;top: 20px;">
                    <input class="form-control" type="password" id="loginPwd" placeholder="密码">
                </div>
                <div class="checkbox" style="position: relative;top: 30px; left: 10px;">

                    <span id="msg" style="color:red;"></span>

                </div>
                <button type="button" id="subButton" class="btn btn-primary btn-lg btn-block"
                        style="width: 350px; position: relative;top: 45px;">登录
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>