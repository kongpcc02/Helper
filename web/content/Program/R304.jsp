<%-- 
    Document   : P301
    Created on : Jul 5, 2020, 8:37:00 PM
    Author     : EXAT
--%>

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
                $("#dateFrom").datepicker({
                    dateFormat: 'dd/mm/yy',
                    changeMonth: true,
                    changeYear: true,
                    onSelect: function (date) {
                        $("#dateTo").datepicker().datepicker("setDate", date);
                    }
                });
                $("#dateTo").datepicker({
                    dateFormat: 'dd/mm/yy',
                    changeMonth: true,
                    changeYear: true
                });
                $.post("/Helper/R304Controller", {
                    trxType: 'GET_MST_ETC'
                }, function (data) {
                    $('#serviceId').html(data);
                });
                $.post("/Helper/R304Controller", {
                    trxType: 'GET_MST_LINE'
                }, function (data) {
                    $('#line').html(data);
                });
                $("#bt").click(function () {
                    var dateTo = $('#dateTo').val();
                    var dateFrom = $('#dateFrom').val();
                    if (dateFrom === '' || dateFrom === null) {
                        alert('กรุณาระบุวันที่ด้วยครับ');
                        $('#dateFrom').focus();
                        $('#log').html("");
                        return;
                    }
                    $('#log').html("กำลังประมวลผล......");
                    if (dateTo === '' || dateTo === null) {
                        dateTo = dateFrom;
                        $('#dateTo').val(dateTo);
                    }
                    $("#bt").attr('disabled', 'disabled');
                    $.post("/Helper/R304Controller", {
                        trxType: 'EXECUTE_REPORT',
                        lineCode: $("#line").val(),
                        fromDate: dateFrom,
                        toDate: dateTo,
                        serviceId: $('#serviceId').val()
                    }, function (data) {
                        $("#bt").removeAttr('disabled');
                        $('#log').html(data)
                    });
                });
            });
        </script>
    </head>
    <body>
        <div class="nav"  >โปรแกรม &raquo; R304 รายงานการให้บริการ Easy Pass (เฉพาะประเภทการชำระจากเครื่อง EDC)</div>
        <input  type="button" value="กลับหน้าแรก" onclick="history.back()"  >
        <input id="bt"  type="button" value=" Execute ">
        <span id="log" ></span>
        <table width="70%"  class="form" cellpadding="5" cellspacing="0">
            <tr>
                <td align="right"><b>ทางพิเศษ :</b></td>
                <td>
                    <select name="line" id="line"> 
                    </select>
                </td>
                <td>
                    <span id="loadLine"></span>
                </td>
            </tr>
            <tr>
                <td  align="right" width="30%">ประเภทการให้บริการ : </td>
                <td>
                    <select name="serviceId" id="serviceId"> 
                    </select>
                </td>
                <td>
                    <span id="loadEtc"></span>
                </td>
            </tr>
            <tr>
                <td  align="right" width="30%">วันที่ : </td>
                <td>
                    <input id="dateFrom" type="text" autocomplete="off"/>
                </td>
            </tr>
            <tr>
                <td  align="right" width="30%">ถึงวันที่ : </td>
                <td>
                    <input id="dateTo" type="text"  autocomplete="off"/>
                </td>
            </tr>

        </table>
    </body>
</html>
