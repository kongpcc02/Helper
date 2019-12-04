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
            function pcs() {
                $('#log').html("Loading...")
                $.post("/Helper/P111Controller",
                        {
                            dt: $('#dt').val(),
                            line: $('#line').val()
                        },
                function (data) {
                    $('#log').html(data)
                }
                );
            }

        </script>
    </head>
    <body> 
        <div class="nav"  >โปรแกรม &rarr; P111 เปรียบเทียบข้อมูลกับ Gateway</div>
        <input  type="button" value="กลับหน้าแรก" onclick="history.back()"  >
        <input   type="button" value="ประมวลผล" onclick="pcs();">
        <input   type="reset" value="ยกเลิก" >
        <table width="50%" class="form" >
            <tr >
                <td align="right"><b>วันที่ :</b></td>
                <td><input  id="dt" type="text" onclick="this.value = ''"/>

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
                    </select>

                </td>
            </tr>
        </table><br><hr>
        Show log
        <div id="log">

        </div>


    </body>
</html>