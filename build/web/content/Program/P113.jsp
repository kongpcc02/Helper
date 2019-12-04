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
                dp_cal = new Epoch('epoch_popup', 'popup', document.getElementById('dd'));
                dp_cal = new Epoch('epoch_popup', 'popup', document.getElementById('dd2'));
            };
            function rev() {
                $('#log').html("Loading...")
                $.post("/Helper/P113Controller",
                        {
                            d1: $('#dd').val(),
                            d2: $('#dd2').val(),
                            line: $('#line').val()
                        },
                function (data) {
                    $('#log').html(data)
                });
            }
            function process() {
                if ($("#dd").val() == "" || $("#dd2").val() == "") {
                    alert("โปรดใส่วันที่ด้วยครับ !!");

                } else {
                    rev();
                }
            }
        </script>
    </head>
    <body> 
        <div class="nav"  >โปรแกรม &rarr; P113 Update int to trx</div>
        <input  type="button" value="กลับหน้าแรก" onclick="history.back()"  >
        <input   type="button" value="ประมวลผล" onclick="process();">
        <input   type="reset" value="ยกเลิก" >
        <table width="50%" class="form" >
            <tr >
                <td align="right"><b>วันที่ :</b></td>
                <td><input  id="dd" type="text" onclick="this.value = ''" autocomplete = "off"/> - <input  id="dd2" type="text" onclick="this.value = ''" autocomplete = "off"/>

                </td>
            </tr> <tr>
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
                    </select>

                </td>
            </tr>
        </table>
        Show log
        <div id="log">

        </div>
    </body>
</html>