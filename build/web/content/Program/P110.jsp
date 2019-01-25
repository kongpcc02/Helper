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
        <title>โปรแกรม » P110 สร้างข้อมูลนำเข้า ETC เฉลิมหานครและฉลองรัช</title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/beer_calendar.css" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/content.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/themes/smoothness/jquery.ui.all.css">
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/ui/jquery.ui.core.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/ui/jquery.ui.datepicker.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.validate.min.js"></script>

        <script type="text/javascript">
            $(document).ready(function() {
                if('${result}' == 'success'){
                    alert('บันทึกข้อมูลเสร็จสมบูรณ์');
                } else if('${result}' != ''){
                    alert('${result}');
                }
                
                $("#p110_form").validate({
        
                    rules: {
                        d1: "required",
                        file1: "required",
                        file3: "required"
                    },
                    messages: {
                        d1: " กรุณาเลือกวันที่",
                        file1: " กรุณาเลือกแฟ้มสายทางที่ 1",
                        file3: " กรุณาเลือกแฟ้มสายทางที่ 3"
                    }
                });
                
                $("#d1" ).datepicker();
                
                $("#submitBtn").click(function(){
                    if($("#d1").val() != "" && $("#file1").val() != "" && $("#file3").val() != ""){
                        $("#submitBtn").prop("disabled", true);
                        $("#submitBtn").prop('value', 'กรุณารอซักครู่...');
                    }
                    submit();
                });
                
                $(window).load(function() {
                    //alert('loaded');
                    $("#submitBtn").removeAttr("disabled");
                });

                //---$("#p").click(function(){
                //---if($("#d1").val() == "" || $("#d2").val() == ""){
                //---alert("ใส่วันที่ด้วยครับ");
                //---}else{
                //---$("#loading").addClass("loading").html("Loading...");
                //$.post("/Helper/TestUpload", $("#p110_form").serialize(), function(data){
                //    $("#loading").removeAttr("class").html("");
                //    alert(data); 
                //});
                        
                //---$.post("/Helper/P110Controller", $("#p110_form").serialize());
                        
                /*$.post("/Helper/TestUpload",{
                            d1:$("#d1").val(),
                            d2:$("#d2").val()
                        } ,function(data){
                            $("#loading").removeAttr("class").html("");
                            alert(data); 
                        });*/
                //--- }
                //--- });
            });
            
            function submit(){
                $("#p110_form").submit();
            }
        </script>
    </head>
    <body>
        <!-- เวลาสร้างฟอร์มที่มีการอัพโหลด จะต้องสร้างฟอร์มแบบ enctype="multipart/form-data" ด้วย เพราะมันจะส่งเป็น Stream ของ Byte แทนที่จะส่งเป็น Text -->
        <form action="/Helper/P110Controller" id="p110_form" method="post" enctype="multipart/form-data">
            <div class="nav"  >โปรแกรม &raquo; P110 สร้างข้อมูลนำเข้า ETC เฉลิมหานครและฉลองรัช</div>
            <input  type="button" value="กลับหน้าแรก" onclick="history.back()"  >
            <!--<input  id="p" type="button" value="ประมวลผล_TESTAJAX">-->
            <input  id="submitBtn" type="button" value="ประมวลผล">
            <input type="button" value="ยกเลิก">
            <!--<a href="/Helper/TestPushFile">PDF</a>-->
            <table width="55%"  class="form" cellpadding="5" cellspacing="0">
                <tr>
                    <td  align="right" width="30%">วันที่ : </td>
                    <td>
                        <input name="d1" id="d1" type="text" />
                    </td>
                </tr>
                <tr>
                    <td  align="right" width="30%">เลือกเอกสาร สายทาง 1 : </td>
                    <td>
                        <input type="file" name="file1" />
                    </td>
                </tr>
                <tr>
                    <td  align="right" width="30%">เลือกเอกสาร สายทาง 3 : </td>
                    <td>
                        <input type="file" name="file3" />
                    </td>
                </tr>
            </table>

            <span id="loading"></span>
        </form>
    </body>
</html>