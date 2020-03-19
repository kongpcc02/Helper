<%-- 
    Document   : P119
    Created on : Jan 16, 2020, 10:02:39 AM
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
                    $.post("/Helper/P119Controller", {
                        dateFrom: $("#dateFrom").val(),
                        dateTo: dateTo
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
        <div class="nav"  >โปรแกรม &raquo; P119 โปรแกรมแปลง ETC(โปรโมชั่น)ข้อมูลด่าน 129-1 เป็น 129-2</div>
        <input  type="button" value="กลับหน้าแรก" onclick="history.back()"  >
        <input  type="button" value="ประมวลผล" id="process"  >
        <br><br>
        <fieldset>
            <legend >เฉลิมมหานคร (โปรโมชั่น)</legend>
            <table border="0"  width="100%" cellpadding="5" >
                <thead>
                    <tr>
                        <td width="20%" align="right"  > วันที่ :   </td>
                        <td>
                            <input  id="dateFrom" type="text" size="15"  autocomplete="off"/>
                            ถึง
                            <input  id="dateTo" type="text" size="15"  autocomplete="off"/>
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