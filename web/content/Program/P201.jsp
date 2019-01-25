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
        <link rel="stylesheet" href="../../css/themes/smoothness/jquery.ui.all.css">
        <script type="text/javascript" src="../../js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="../../js/ui/jquery.ui.core.js"></script>
        <script type="text/javascript" src="../../js/ui/jquery.ui.datepicker.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#d1").datepicker();
                $("#d2").datepicker();

                $("#bt").click(function () {
                    if ($("#d1").val() == "" || $("#d2").val() == "") {
                        alert("ใส่วันที่ด้วยครับ");
                    } else {
                        $('#log').html("Loading...");
                        $.post("/Helper/P201Controller", {
                            d1: $("#d1").val(),
                            d2: $("#d2").val(),
                            line: $("#line").val()
                        }, function (data) {
                            $('#log').html(data);
                        });
                    }
                });
            });
        </script>
    </head>
    <body>
        <div class="nav"  >โปรแกรม &raquo; P201 ลบ Interface</div>
        <input  type="button" value="กลับหน้าแรก" onclick="history.back()"  >
        <input id="bt"  type="button" value=" Process ">
        <table width="50%"  class="form" cellpadding="5" cellspacing="0">
            <tr>
                <td  align="right" width="30%">วันที่ : </td>
                <td>
                    <input id="d1" type="text"/>
                </td>
            </tr>
            <tr>
                <td  align="right" width="30%">ถึงวันที่ : </td>
                <td>
                    <input id="d2" type="text"/>
                </td>
            </tr>
            <tr>
                <td align="right"><b>ทางพิเศษ :</b></td>
                <td>
                    <select name="line" id="line"> 
                        <option  value="1">01 : เฉลิมมหานคร</option>
                        <option  value="2">02 : ศรีรัช</option>
                        <option  value="3">03 : ฉลองรัช</option>
                        <option  value="4">04 : บูรพาวิถี</option>
                        <option  value="5">05 : อุดรรัถยา</option>
                        <option  value="6">06 : บางพลี-สุขสวัสดิ์</option>
                        <option  value="7">07 : ทางหลวงพิเศษหมายเลข 7</option>
                        <option  value="8">08 : ศรีรัช-วงแหวนฯ</option>
                        <option  value="9">09 : ทางหลวงพิเศษหมายเลข 9</option>
                        <option  value="9">99 : หน่วยงานภายนอก</option>
                    </select>

                </td>
            </tr>
        </table>
        <span id="log" ></span>
    </body>
</html>