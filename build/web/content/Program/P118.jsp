<%-- 
    Document   : P118
    Created on : Sep 3, 2019, 4:14:15 PM
    Author     : chonpisit_klo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../header.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../../css/beer_calendar.css" />
        <script type="text/javascript" src="../../js/beer_calendar.js"></script>
        <script type="text/javascript" src="../../js/jquery-1.4.2.min.js"></script>
        <link rel="stylesheet" type="text/css" href="../../css/content.css" />
        <script type="text/javascript">
            var dp_cal;
            window.onload = function () {
                dp_cal = new Epoch('epoch_popup', 'popup', document.getElementById('dateFrom'));
                dp_cal = new Epoch('epoch_popup', 'popup', document.getElementById('dateTo'));
            };
            $(document).ready(function () {
                var replace = false;
                $("#spinner").bind("ajaxSend", function () {
                    $(this).show();
                }).bind("ajaxStop", function () {
                    $(this).hide();
                }).bind("ajaxError", function () {
                    $(this).hide();
                });
                $("#process").click(function () {
                    $("#result").html("");
                    if ($("#dateFrom").val() === "" && $("#dateTo").val() === "") {
                        alert("กรุณาระบุวันที่");
                        $("#dateFrom").focus();
                        return;
                    }
                    var dateTo;
                    if ($("#dateTo").val() === "") {
                        dateTo = $("#dateFrom").val();
                    } else {
                        dateTo = $("#dateTo").val();
                    }

                    $('#process').attr('disabled', 'disabled');
                    $('#spinner').show();
                    $.post("/Helper/P117Controller", {
                        dateFrom: $("#dateFrom").val(),
                        dateTo: dateTo,
                        version: $("#version").val()
                    }, function (res) {
                        $('#process').removeAttr('disabled');
                        $('#spinner').hide();
                        $("#result").html(res);
                    });
                });
            });
        </script>
    </head>
    <body>
        <div class="nav"  >โปรแกรม &raquo; P118 แปลงข้อมูลกรณีข้อมูลเลนยาวเกินกำหนด</div>
        <input  type="button" value="กลับหน้าแรก" onclick="history.back()"  >
        <input  type="button" value="ประมวลผล" id="process"  >
        <br><br>
        <fieldset>
            <legend >สายทาง</legend>
            <table border="0"  width="100%" cellpadding="5" >
                <thead>
                    <tr>
                        <td width="20%" align="right"  > สายทาง :   </td>
                        <td>
                            <select id="section">
                                <option value="01">เฉลิมมหานคร</option>
                                <option value="02">ศรีรัช</option>
                                <option value="03">ฉลองรัช</option>
                                <option value="04">บูรพาวิถี</option>
                                <option value="05">อุดรรัถยา</option>
                                <option value="06">กาญจนาภิเษก</option>
                                <option value="07">ทล. 7</option>
                                <option value="08">ศรีรัช - วงแหวน</option>
                                <option value="09">ทล. 9</option>
                            </select>
                            <!--                            <input  id="dateFrom" type="text" size="15"  autocomplete="off"/>
                                                        ถึง
                                                        <input  id="dateTo" type="text" size="15"  autocomplete="off"/>-->
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right"  >
                            ระบบ :
                        </td>
                        <td>
                            <select id="section">
                                <option value="MTC">เงินสด</option>
                                <option value="ETC">Easy Pass</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td width="20%" align="right"  > เวอร์ชั่นไฟล์ :   </td>
                        <td>
                            <select id="version">
                                <option value="0">0</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <!--                                <option value="4">4</option>
                                                                <option value="5">5</option>-->
                            </select>
                        </td>
                    </tr>
                </thead>
            </table>
        </fieldset>
        <div align="center">
            <p id="result"/>
        </div>
        <div id="spinner" class="spinner" style="display:none;" align="center">
            <img id="img-spinner" src="../../images/spinner.gif" alt="Loading"/>
        </div>
    </body>
</html>