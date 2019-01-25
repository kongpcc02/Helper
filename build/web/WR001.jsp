<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="../../css/style.css">
        <link rel="stylesheet" href="../../css/themes/base/jquery.ui.all.css">
        <script src="../../js/jquery-1.4.2.min.js"></script>
        <script src="../../js/ui/jquery.ui.core.js"></script>
        <script src="../../js/ui/jquery.ui.datepicker.js"></script>
        <script src="../../js/ui/jquery.ui.widget.js" type="text/javascript"></script>
        <script src="../../js/ui/jquery.ui.mouse.js" type="text/javascript"></script>
        <script src="../../js/ui/jquery.ui.draggable.js" type="text/javascript"></script>
        <script src="../../js/ui/jquery.ui.position.js" type="text/javascript"></script>
        <script src="../../js/ui/jquery.ui.resizable.js" type="text/javascript"></script>
        <script src="../../js/ui/jquery.ui.dialog.js" type="text/javascript"></script>
        <script src="../../js/jquery.alphanumeric.js" type="text/javascript"></script>

    </head>
    <body>
        <form method="post" action="/Helper/TestServlet"  >
            <h1>WR001 รายงานตรวจสอบสวัสดิการ</h1>
            <h2>เลือกเงื่อนไขในการค้นหาเพื่อออกรายงานการตรวจสอบสวัสดิการ</h2>
            <div class="toolbar">
                <input type="submit" value="ประมวลผล" id="find" >
                <input type="button" value="ยกเลิก" id="cancel">
                <input type="hidden" name="mode" value="CALL_REPORT">
                <span id="POINT1"></span>
            </div>
            <table border="0" class="tbl-form" cellpadding="5" width="95%">
                <tr>
                    <td width="15%" align="right">ประเภทสวัสดิการ :</td>
                    <td width="35%">
                        <select id="welType"><option value=""></option></select><span id="POINT2"></span>
                        <font color="red">*</font>
                    </td>
                    <td align="right">ประจำงวดวันที่ :</td>
                    <td><input type="text" size="10" name="pDate" id="pDate"><font color="red">*</font> </td>
                </tr>
                <tr>
                    <td align="right">จากวันที่ :</td>
                    <td><input type="text" size="10"  id="fDate"><font color="red">*</font> </td>
                </tr>
                <tr>
                    <td align="right">ถึงวันที่ :</td>
                    <td><input size="10" type="text"  id="tDate">  <font color="red">*</font></td>
                </tr>
            </table>
        </form>
    </body>
</html>