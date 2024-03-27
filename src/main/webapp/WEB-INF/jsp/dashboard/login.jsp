<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="/Common/css/jquery.toast.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script type="text/javascript" src="/Common/js/common.js"></script>
    <script type="text/javascript" src="/Common/js/jquery.toast.js"></script>
</head>

<body>
Employee Login Page :-<br><br>
<input type='text' id="username" name="username" class='form-control' placeholder='Enter Username'>
<label class='label_input' id='username_label_input'>Enter Username</label>
<br>

<input type='password' class='form-control' id="password" name="password" placeholder='Enter Password'>
<img onclick="showPassword()" src="https://resources.edunexttechnologies.com/web-data/edunext-website/img/eye-off.svg" id="EYE" class="calendar_icon">
<br><br>
<button id="user_login_btn">Sign In</button>

<br><br>
<button id="user_create_btn">Create New Account</button>

<br>
<br>
<button class="btn btn-lg btn-primary" id="submit" onclick="location.href ='/mvc/dashboard/employee'" >
    Employees List
</button>


<%--<script>--%>
<%--    $(document).ready(function () {--%>

<%--        $("#user_login_btn").click(function () {--%>
<%--                var username1 = $("#username").val();--%>
<%--                var password1 = $("#password").val();--%>
<%--                try {--%>
<%--                    if(username1 === ''){--%>
<%--                        showValidationMessage("ERROR", "error", "Enter User Name");--%>
<%--                        return;--%>
<%--                    }--%>
<%--                    if(password1 === ''){--%>
<%--                        showValidationMessage("ERROR", "error", "Enter Password");--%>
<%--                        return;--%>
<%--                    }--%>
<%--                    checkLoginDetail()--%>
<%--                } catch (e) {--%>
<%--                    console.log('Unable to login', e);--%>
<%--                }--%>
<%--            }--%>
<%--        );--%>

<%--        $("#user_create_btn").click(function () {--%>
<%--            window.location.href="/mvc/dashboard/createaccount";--%>
<%--        })--%>


<%--    });--%>

<%--    function checkLoginDetail() {--%>
<%--        var username = $("#username").val();--%>
<%--        var password = $("#password").val();--%>
<%--        var domain = window.location.origin;--%>
<%--        var dataurl = domain + "/api/testing/login";--%>

<%--        var datapass = {--%>
<%--            "username":username,--%>
<%--            "password":password,--%>
<%--        }--%>

<%--        $.ajax({--%>
<%--            type:"POST",--%>
<%--            data:{--%>
<%--                "username":username,--%>
<%--                "password":password,--%>
<%--            },--%>
<%--            contentType:"application/json",--%>
<%--            url:"/api/testing/login",--%>
<%--            success:function (response) {--%>
<%--                console.log(response)--%>
<%--                if(response.message == "success"){--%>
<%--                    //showValidationMessage("Success", "success", "Login Success");--%>
<%--                    localStorage.setItem("username", username);--%>
<%--                    localStorage.setItem("password", Base64.encode(password));--%>

<%--                    window.location.href="/mvc/dashboard/employeedetail";--%>

<%--                } else {--%>
<%--                    showValidationMessage("ERROR", "error", "Wrong login information");--%>
<%--                }--%>
<%--            },--%>
<%--            complete: function (res) {--%>
<%--                //showValidationMessage("Success", "success", "Login Success");--%>
<%--            },--%>
<%--            error: function (error) {--%>
<%--                showValidationMessage("ERROR", "error", "Wrong login information");--%>
<%--            },--%>

<%--        })--%>


<%--    }--%>
<%--</script>--%>
</body>

</html>