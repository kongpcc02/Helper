<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../header.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="../../css/beer_calendar.css" />
        <link rel="stylesheet" type="text/css" href="../../css/content.css" />
        <script type="text/javascript" src="../../js/beer_calendar.js"></script>
        <script type="text/javascript" src="../../js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript">
            var dp_cal;
            window.onload = function () {
                dp_cal = new Epoch('epoch_popup', 'popup', document.getElementById('dt'));
            };
            function rev() {
                $('#log').html("Loading...")
                $.post("/Helper/P114Controller",
                        {
                            dt: $('#dt').val(),
                            line: $('#line').val()
                        },
                function (data) {
                    $('#log').html(data)
                });
            }
            function process() {
                if ($("#dt").val() == "") {
                    alert("โปรดใส่วันที่ด้วยครับ !!");

                } else {
                    rev();
                }
            }
        </script>
    </head>
    <body> 
        <div class="nav"  >โปรแกรม &rarr; P114 แปลงข้อมูลของสายทางสาย 7,9</div>
        <input  type="button" value="กลับหน้าแรก" onclick="history.back()"  >
        <input   type="button" value="ประมวลผล" onclick="process();">
        <input   type="reset" value="ยกเลิก" >
        <table width="50%" class="form" >
            <tr >
                <td align="right"><b>สายทาง :</b></td>
                <td>
                    <select id="line">
                        <option value="7">7</option>
                        <option value="9">9</option>
                    </select>
                </td>
            </tr>
            <tr >
                <td align="right"><b>วันที่ :</b></td>
                <td><input  id="dt" type="text" onclick="this.value = ''"/>

                </td>
            </tr>
        </table>
        Show log
        <div id="log">

        </div>
    </body>
</html>