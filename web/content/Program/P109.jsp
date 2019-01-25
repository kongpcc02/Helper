<%-- 
    Document   : P105
    Created on : 31 พ.ค. 2554, 9:51:33
    Author     : Administrator
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
            $(document).ready(function() {
                $("#d1" ).datepicker();
                $("#d2" ).datepicker();

                $("#p").click(function(){
                    if($("#d1").val() == "" || $("#d2").val() == ""){
                        alert("ใส่วันที่ด้วยครับ");
                    }else{
                        $("#loading").addClass("loading").html("Loading...");
                        $.post("/Helper/P109Controller",{
                            d1:$("#d1").val(),
                            d2:$("#d2").val()
                        } ,function(data){
                            $("#loading").removeAttr("class").html("");
                            alert(data); 
                        });
                    }
                });
            });
        </script>
    </head>
    <body>
        <div class="nav"  >โปรแกรม &raquo; P109 ลบ ERROR ของด่านปู่เจ้าสมิงพลาย</div>
        <input  type="button" value="กลับหน้าแรก" onclick="history.back()"  >
        <input  id="p" type="button" value="ประมวลผล">
        <input type="button" value="ยกเลิก">
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
        </table>
        <span id="loading"></span>
    </body>
</html>