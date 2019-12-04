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
        <script type="text/javascript" src="../../js/beer_calendar.js"></script>
        <script type="text/javascript" src="../../js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript">
            var dp_cal;
            window.onload = function () {
                dp_cal  = new Epoch('epoch_popup','popup',document.getElementById('dd'));
            };
            function rev(){
                $.post("/Helper/ETCRevProcess",
                {
                    fDate:$('#dd').val()
                },
                function(data){
                    alert(data);
                }
            );
            }
            function trf(){
                $.post("/Helper/EasyPassUsingProcess",
                {
                    fDate:$('#dd').val()
                },
                function(data){
                    alert(data);
                }
            );}
            function process(){
                if($("#dd").val()==""){
                    alert("โปรดใส่วันที่ด้วยครับ !!");

                }else{
                    rev();
                    trf();
                }
            }
        </script>
    </head>
    <body> 
        <div class="nav"  >โปรแกรม &rarr; P102 นำเข้าข้อมูลของทางด่วนขั้นที่ 2</div>
        <input  type="button" value="กลับหน้าแรก" onclick="history.back()"  >
        <input   type="button" value="ประมวลผล" onclick="process();">
        <input   type="reset" value="ยกเลิก" >
        <table width="50%" class="form" >
            <tr >
                <td align="right"><b>วันที่ :</b></td>
                <td><input  id="dd" type="text" onclick="this.value=''"/>

                </td>
            </tr>
        </table>
    </body>
</html>