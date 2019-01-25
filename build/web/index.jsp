<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> </title>
        <script src="js/jquery-1.4.2.min.js"></script>
        <link rel="stylesheet" href="css/content.css">
        <script type="text/javascript">
            function load() {
                $("#user").focus();
            }
            function login() {
                $.post("/Helper/Login",
                        {
                            username: $('#user').val(),
                            password: $('#pass').val()
                        },
                function(data) {

                    if (data == 2) {
                        alert("ไม่สามารถเข้าสู่ระบบได้");
                    } else if (data == 9) {
                        alert("กรอกข้อมูลให้สมบูรณ์ด้วยครับ");
                    } else if (data == 4) {
                        alert("รหัสของท่านโดนระงับใช้ชั่วคราว โปรติดต่อ 1303 !!");
                    } else if (data == 0) {
                        window.location = '<%=request.getContextPath() + "/content/index.jsp"%>';
                    } else if (data == -1) {
                        window.location = '<%=request.getContextPath() + "/content/index.jsp"%>';

                    }
                }
                );
            }

            $(function() {
                /*  สามารถเปลี่ยนจาก cardNo_ เป็นค่าที่ต้องการ  */
                $("#user").keyup(function(event) {
                    if (event.keyCode == 13) {
                        $("#pass").focus();
                    }
                });
                $("#pass").keyup(function(event) {
                    if (event.keyCode == 13) {

                        login();
                    }
                });
            });
        </script>

    </head>
    <body  onload="load();"   style="font-family:Arial, Helvetica, Sans-Serif;margin: 0;">
        <table width="100%" border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td width="50px"><img alt="" src="images/LOGO.jpg"></td>
                <td style="font-size: 36px">การทางพิเศษแห่งประเทศไทย</td>
            </tr>
            <tr>

        </table>
        <div style="margin: 20px 0 10px 10px">
            ระบบ Helper Version 3.1.7
        </div>
        <table style="margin-left: 25px" border="0" cellpadding="7" cellspacing="0">
            <tr style="font-weight: bold;font-size:14px">
                <td align="left" colspan="2">เข้าสู่ระบบ (Login)</td>
            </tr>
            <tr style="font-size: 85%">
                <td align="left"><b>Username :</b></td>
                <td>
                    <input type="text" name="user" id="user">
                </td>
            </tr>
            <tr style="font-size: 85%">
                <td align="left">
                    <b>Password :  </b></td>
                <td><input type="password" name="pass" id="pass" >
                </td>
            </tr>
            <tr >
                <td></td>
                <td colspan="2">
                    <input style="background-color: white;border: black solid 1px" id="ok" type="button" value=" Log In " onclick="login();"></td>
            </tr>
        </table>
        <div style="margin: 10px 0 0 25px;font-weight: normal;font-family:Arial, Helvetica, Sans-Serif;   font-size:90%;">(ติดต่อ : ฝ่ายสารสนเทศ แผนกระบบงานคอมพิวเตอร์ 1 โทร 1303.)</div>

    </body>
</html>
