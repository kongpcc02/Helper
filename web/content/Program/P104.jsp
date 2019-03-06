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
        <title>JSP Page</title><link rel="stylesheet" type="text/css" href="../../css/beer_calendar.css" />
        <link rel="stylesheet" type="text/css" href="../../css/content.css" />
        <script type="text/javascript" src="../../js/beer_calendar.js"></script>
        <script type="text/javascript" src="../../js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript">
            var dp_cal;
            window.onload = function () {
                dp_cal  = new Epoch('epoch_popup','popup',document.getElementById('d'));
            };

            function process(){
                if($("#d").val()==""){
                    alert("โปรดใส่วันที่ด้วยครับ !!");
                }else{
                    $("#rs").addClass("loading");
                    $("#rs").html("Loading...");

                    $.post("/Helper/P104Controller",
                    {
                        d:$('#d').val()
                    },
                    function(data){
                        $("#rs").removeAttr("class");
                        $("#rs").html(data);
                    }
                );
                }
            }
            function look(){
                $("#rs").addClass("loading");
                $("#rs").html("Loading...");

                $.post("/Helper/CheckDateOfMonthNoInsert",
                {
                    m:$('#m').val(),
                    y:$('#y').val()
                },
                function(data){
                    $("#rs").removeAttr("class");
                    $("#rs").html(data);
                });
            }

        </script>
    </head>
    <body>
        <div class="nav"  >โปรแกรม &raquo; P104 นำเข้าข้อมูล Sector D</div>
        <input  type="button" value="กลับหน้าแรก" onclick="history.back()"  >
        <table width="50%"  class="form" cellpadding="5" cellspacing="0" >
            <tr>
                <td >วันที่ : <input class="r" id="d" type="text" onclick="this.value=''"/>
                    <input onclick="process();" type="button" value=" นำเข้าข้อมูล ">

                </td>
        </table>

        <table width="50%"  class="form" cellpadding="5" cellspacing="0" >
            <tr>
                <td >
                    ตรวจสอบการนำเข้า:  

                    <select id="m">
                        <option value="01">ม.ค</option>
                        <option value="02">ก.พ</option>
                        <option value="03">มี.ค</option>
                        <option value="04">เม.ย</option>
                        <option value="05">พ.ค</option>
                        <option value="06">มิ.ย</option>
                        <option value="07">ก.ค</option>
                        <option value="08">ส.ค</option>
                        <option value="09">ก.ย</option>
                        <option value="10">ต.ค</option>
                        <option value="11">พ.ย</option>
                        <option value="12">ธ.ค</option>
                    </select>
                    <select id="y">
                        <option value="2011">2554</option>
                        <option value="2012">2555</option>
                        <option value="2013">2556</option>
                        <option value="2014">2557</option>
                        <option value="2015">2558</option>
                        <option value="2016">2559</option>
                        <option value="2017">2560</option>
                        <option value="2018">2561</option>
                        <option value="2019">2562</option>
                    </select>
                    <input onclick="look();" type="button" value=" ตรวจสอบ ">

                </td>
        </table>
        <br><br>
        <div id="rs" ></div>
    </body>
</html>