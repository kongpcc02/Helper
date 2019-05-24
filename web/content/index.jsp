<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ผู้ช่วยเหลือ | ระบบตรวจสอบรายได้ </title>
        <link rel="stylesheet" href="../css/content.css">

    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="nav"><b>Helper 3.0 : </b>เลือกโปรแกรม</div>
        <!--<iframe  width="100%" src="content.html"></iframe>-->
        <div width="100%"  align="left">
            <table width="90%" border="0" cellspacing="1" cellpadding="10">
                <tr>
                    <td width="30%">
                        <fieldset> 
                            <input  type="button" value="P101 แปลงข้อมูลของกรมทางหลวง" onclick=window.location="<%=request.getContextPath() + "/content/Program/P101.jsp"%>">
                         <!--   <input   disabled="disabled"  style="background-color: orange;"  type="button" value="P102 นำเข้าข้อมูลด่วนศรีรัช" onclick=window.location="<%=request.getContextPath() + "/content/Program/P102.jsp"%>">-->
                            <input   style="background-color: orange;"  type="button" value="P102 นำเข้าข้อมูลด่วนศรีรัชจาก Gateway" onclick=window.location="<%=request.getContextPath() + "/content/Program/P102_1.jsp"%>">
                           <!-- <input  disabled="disabled" type="button" value="P103 นำเข้าข้อมูลด่วนทางด่วนเฉลิมมหานครและฉลองรัช" onclick=window.location="<%=request.getContextPath() + "/content/Program/P103.jsp"%>">-->
                            <input   type="button" value="P104 นำเข้าข้อมูลจากระบบแบ่งรายได้" onclick=window.location="<%=request.getContextPath() + "/content/Program/P104.jsp"%>">
                            <input   type="button" value="P105 ลบข้อมูลนำเข้า (Interface)" onclick=window.location="<%=request.getContextPath() + "/content/Program/P105.jsp"%>">
                            <input   type="button" value="P109 ลบ ERROR ของด่านปู่เจ้าสมิงพลาย" onclick=window.location="<%=request.getContextPath() + "/content/Program/P109.jsp"%>">
                           <!-- <input disabled="disabled"  type="button" value="R101 รายงานรายได้การให้บริการ " onclick=window.location="<%=request.getContextPath() + "/content/Report/R101.jsp"%>">-->
                            <input   type="button" value="P110 สร้างข้อมูลนำเข้า ETC เฉลิมหานครและฉลองรัช " onclick=window.location="<%=request.getContextPath() + "/content/Program/P110.jsp"%>">
                            <input   type="button" value="P111 เปรียบเทียบข้อมูลจาก Gateway" onclick=window.location="<%=request.getContextPath() + "/content/Program/P111.jsp"%>">
                            <input   type="button" value="P112 นำเข้าข้อมูลสายทางบูรพาวิถี" onclick=window.location="<%=request.getContextPath() + "/content/Program/P112.jsp"%>">
                            <input   type="button" value="P113 อัพเดทข้อมุลจากระบบจัดเก็บ" onclick=window.location="<%=request.getContextPath() + "/content/Program/P113.jsp"%>">
                            <input   type="button" value="P114 แปลงข้อมูลของสายทาง 7,9" onclick=window.location="<%=request.getContextPath() + "/content/Program/P114.jsp"%>">
                            <input   type="button" value="P115 แปลงข้อมูลทางด่วนขั้นที่ 2 ศรีรัช" onclick=window.location="<%=request.getContextPath() + "/content/Program/P115.jsp"%>">
                            <input   type="button" value="P116 นำเข้าข้อมูลการให้บริการและเงินนำส่งธนาคาร(ETC) ศรีรัช-วงแหวน" onclick=window.location="<%=request.getContextPath() + "/content/Program/P116.jsp"%>">
                        </fieldset>
                    </td>
                    <td width="30%" valign="top">
                        <fieldset> 
                            <input  type="button" value="P107 อัพเดท VAT การขาย =0" onclick=window.location="<%=request.getContextPath() + "/content/Program/P107.jsp"%>">
                            <input disabled="disabled"   type="button" value="P108 ส่งออกข้อมูลปริมาณจราจรและรายได้" onclick=window.location="<%=request.getContextPath() + "/content/Program/P108.jsp"%>">
                            <input  type="button" value="P201 ลบ Interface" onclick=window.location="<%=request.getContextPath() + "/content/Program/P201.jsp"%>">
                        </fieldset>
                    </td>
                    <td width="30%" valign="top">
                        <fieldset> 
                            <input   disabled="disabled" type="button" value="P108 ส่งออกข้อมูล DOC ของทางด่วนศรีรัช ส่วน D" onclick=window.location="<%=request.getContextPath() + "/content/Program/P108.jsp"%>">
                        </fieldset>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
