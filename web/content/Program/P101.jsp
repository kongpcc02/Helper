

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../header.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <link rel="stylesheet" type="text/css" href="../../css/beer_calendar.css" />
        <script type="text/javascript" src="../../js/beer_calendar.js"></script>
        <script type="text/javascript" src="../../js/jquery-1.4.2.min.js"></script>
        <link rel="stylesheet" type="text/css" href="../../css/content.css" />
        <script type="text/javascript">
            var dp_cal;
            window.onload = function() {
                dp_cal = new Epoch('epoch_popup', 'popup', document.getElementById('d'));
            };

            $(document).ready(function() {
                $("#loading").ajaxStart(function() {
                    $(this).show();
                });
                $("#loading").ajaxStop(function() {
                    $(this).hide();
                });
                $("#p").click(function() {
                    if ($("#d").val() == "") {
                        alert("เลือกวันที่ด้วยครับ");
                    } else {
                        $("#rs").html("<div class=loading>กำลังโหลด...</div>");
                        $.post("/Helper/P101Controller", {
                            d: $("#d").val(),
                            stt: $("#stt").val()},
                                function(data) {
                                    $("#rs").html("");
                                    alert(data);
                                });
                    }
                });
            });
        </script>
    </head>
    <body>
        <div class="nav"  >โปรแกรม &raquo; P101 แปลงข้อมูลของกรมทางหลวง</div>
        <input  type="button" value="กลับหน้าแรก" onclick="history.back()"  >
        <input  type="button" value="ประมวลผล" id="p"  >
        <table cellpadding="5"  width="50%" class="form"cellspacing="0"  >
            <tr>
                <td width="20%" align="right"  > วันที่ :   </td>
                <td>
                    <input id="d" type="text"/>

                </td>
            </tr>
            <tr>
                <td width="20%" align="right"  > ด่าน :   </td>
                <td>
                    <input id="stt" type="text" maxlength="4" size="4"/>
                    <span style="color: red;font-weight: normal;font-style: italic">* ถ้าเลือกทุกด่านให้เว้นช่องว่างไว้</span>

                </td>
            </tr>
        </table>
        <div id="rs"></div>
    </body>
</html>