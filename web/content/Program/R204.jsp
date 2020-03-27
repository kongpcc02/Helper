<%-- 
    Document   : R204
    Created on : Mar 27, 2020, 11:14:30 AM
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
            $("#dateFrom").datepicker();
            $("#dateTo").datepicker();
            $("#bt").click(function () {
                if ($("#dateFrom").val() == "") {
                    alert("กรุณาระบุวันที่");
                    $("#dateFrom").focus();
                } else {
                    $('#rss').html("Loading...");
                    $("#bt").attr('disabled', 'disabled');
                    var tempDate = $("#dateTo").val();
                    if ($("#dateTo").val() == "") {
                        tempDate = $("#dateFrom").val();
                    }
                    $.post("/Helper/R204Controller", {
                        dateFrom: $("#dateFrom").val(),
                        dateTo: tempDate,
                        type: $("#type").val()
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
    <div class="nav"  >โปรแกรม &raquo; RR204EXATB รายงานปริมาณจราจรและรายได้ค่าผ่านทางแยกตามด่าน แสดงเฉพาะรายได้ กทพ. สายทางกาญจนาภิเษก</div>
    <input  type="button" value="กลับหน้าแรก" onclick="history.back()"  >
    <input id="bt"  type="button" value=" ออกรายงาน ">
    <span id="rss"></span>
    <table width="50%"  class="form" cellpadding="5" cellspacing="0">
        <tr>
            <td  align="right" width="30%">วันที่ : </td>
            <td>
                <input  id="dateFrom" type="text" size="15"  autocomplete="off"/>
                ถึง
                <input  id="dateTo" type="text" size="15"  autocomplete="off"/>
            </td>
        </tr>
        <tr>
            <td align="right" width="30%">ระบบ</td>
            <td>
                <select id="type">
                    <option value="mtc">MTC</option>
                    <option value="etc">ETC</option>
                    <option value="all">MTC/ETC</option>
                </select>
            </td>
        </tr>

    </table>
</body>
</html>