<%-- 
    Document   : R302A
    Created on : May 18, 2020, 1:14:30 PM
    Author     : chonpisit_klo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="../header.jsp" %>
<html>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Helper</title>
    <link rel="stylesheet" type="text/css" href="../../css/beer_calendar.css" />
    <link rel="stylesheet" type="text/css" href="../../css/content.css" />
    <link rel="stylesheet" href="../../css/themes/smoothness/jquery.ui.all.css">
    <script type="text/javascript" src="../../js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="../../js/ui/jquery.ui.core.js"></script>
    <script type="text/javascript" src="../../js/ui/jquery.ui.datepicker.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#d1").datepicker();
            $("#bt").click(function () {
                if ($("#d1").val() == "") {
                    alert("กรุณาระบุวันที่");
                    $("#d1").focus();
                } else {
                    $('#rss').html("Loading...");
                    $("#bt").attr('disabled', 'disabled');

                    $.post("/Helper/R302AController", {
                        date: $("#d1").val(),
                        lineCode: $("#lineCode").val()
                    }, function (data) {
                        $("#bt").removeAttr('disabled');
                        $("#rss").html(data);
                    });
                }
            });
        });
    </script>
</head>
<body>
    <div class="nav"  >โปรแกรม &raquo; R302A รายงานสรุปค่าผ่านทางประเภท EMV</div>
    <input  type="button" value="กลับหน้าแรก" onclick="history.back()"  >
    <input id="bt"  type="button" value=" ออกรายงาน ">
    <span id="rss"></span>
    <table width="50%"  class="form" cellpadding="5" cellspacing="0">
        <tr>
            <td  align="right" width="30%">วันที่ : </td>
            <td>
                <input id="d1" type="text" autocomplete="off"/>
            </td>
        </tr>
        <tr>
            <td  align="right" width="30%">ทางพิเศษ : </td>
            <td>
                <select name="line" id="lineCode"> 
                    <option  value="01">01 : เฉลิมมหานคร</option>
                    <option  value="02">02 : ศรีรัช</option>
                    <option  value="03">03 : ฉลองรัช</option>
                    <option  value="04">04 : บูรพาวิถี</option>
                    <option  value="05">05 : อุดรรัถยา</option>
                    <option  value="06">06 : กาญจนาภิเษก</option>
                    <option  value="07">07 : ทางหลวงพิเศษหมายเลข 7</option>
                    <option  value="08">08 : ศรีรัช-วงแหวนฯ</option>
                    <option  value="09">09 : ทางหลวงพิเศษหมายเลข 9</option>
                </select>

            </td>
        </tr>
    </table>
</body>
</html>

