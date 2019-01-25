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
                $("#d" ).datepicker();
                $.post("/Helper/LineSelect" ,function(data){$("#ln").html(data);});

                $("#ln").change(function(){
                    $.post("/Helper/StationSelect",{id:$("#ln").val()} ,function(data){$("#stt").html(data);});
                });

                $("#loading").ajaxStart(function(){
                    $(this).show();
                });
                $("#loading").ajaxStop(function(){
                    $(this).hide();
                });

                $("#p").click(function(){
                    if($("#d").val() == "" || $("#stt").val() == null || $("#stt").val() == 0){
                        alert("ใส่วันที่และเลือกด่านด้วยครับ");
                    }else{
                        $.post("/Helper/P105Controller",{
                            d:$("#d").val(),
                            stt:$("#stt").val()
                        } ,function(data){
                            alert(data);
                            t = $("#d").val().split("/");
                            n = t[1]+"/"+t[0]+"/"+t[2];
                            $.post("/Helper/ETCRevProcess",{fDate:n} ,function(data){
                                alert(data);
                                $.post("/Helper/EasyPassUsingProcess",{fDate:n} ,function(data){
                                    alert(data);
                                    $.post("/Helper/P105ResultDisplay",{fDate:n,ln:$("#ln").val(),stt:$("#stt").val()} ,function(data){
                                        alert(data);
                                    });
                                });
                            });
                        });
                    }
                });
            });
        </script>
    </head>
    <body>
        <div class="nav"  >โปรแกรม &raquo; P105 ลบข้อมูลนำเข้า (Interface)</div>
        <input  type="button" value="กลับหน้าแรก" onclick="history.back()"  >
        <input  id="p" type="button" value="ประมวลผล">
        <input type="button" value="ยกเลิก">
        <table width="50%"  class="form" cellpadding="5" cellspacing="0">
            <tr>
                <td  align="right" width="30%">วันที่ : </td>
                <td>
                    <input id="d" type="text"/>
                </td>
            </tr>
            <tr>
                <td  align="right">สายทาง : </td>
                <td><select id="ln"></select></td>
            </tr>
            <tr>
                <td  align="right">ด่าน : </td>
                <td><select id="stt"></select></td>
            </tr>
        </table>
        <span id="loading" class="loading">Loading...</span>
    </body>
</html>