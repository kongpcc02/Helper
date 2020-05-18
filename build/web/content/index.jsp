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
                            <input  disabled="disabled" type="button" value="P101 แปลงข้อมูลของกรมทางหลวง" onclick=window.location="<%=request.getContextPath() + "/content/Program/P101.jsp"%>">
                         <!--   <input   disabled="disabled"  style="background-color: orange;"  type="button" value="P102 นำเข้าข้อมูลด่วนศรีรัช" onclick=window.location="<%=request.getContextPath() + "/content/Program/P102.jsp"%>">-->
                            <input   style="background-color: orange;"  type="button" value="P102 นำเข้าข้อมูลด่วนศรีรัชจาก Gateway" onclick=window.location="<%=request.getContextPath() + "/content/Program/P102_1.jsp"%>">
                           <!-- <input  disabled="disabled" type="button" value="P103 นำเข้าข้อมูลด่วนทางด่วนเฉลิมมหานครและฉลองรัช" onclick=window.location="<%=request.getContextPath() + "/content/Program/P103.jsp"%>">-->
                            <input   type="button" value="P104 นำเข้าข้อมูลจากระบบแบ่งรายได้" onclick=window.location="<%=request.getContextPath() + "/content/Program/P104.jsp"%>">
                            <input   type="button" value="P105 ลบข้อมูลนำเข้า (Interface)" onclick=window.location="<%=request.getContextPath() + "/content/Program/P105.jsp"%>">
                            <input disabled="disabled"  type="button" value="P109 ลบ ERROR ของด่านปู่เจ้าสมิงพลาย" onclick=window.location="<%=request.getContextPath() + "/content/Program/P109.jsp"%>">
                           <!-- <input disabled="disabled"  type="button" value="R101 รายงานรายได้การให้บริการ " onclick=window.location="<%=request.getContextPath() + "/content/Report/R101.jsp"%>">-->
                            <input  disabled="disabled" type="button" value="P110 สร้างข้อมูลนำเข้า ETC เฉลิมหานครและฉลองรัช " onclick=window.location="<%=request.getContextPath() + "/content/Program/P110.jsp"%>">
                            <input   type="button" value="P111 เปรียบเทียบข้อมูลจาก Gateway" onclick=window.location="<%=request.getContextPath() + "/content/Program/P111.jsp"%>">
                            <input   type="button" value="P112 นำเข้าข้อมูลสายทางบูรพาวิถี(ETC)" onclick=window.location="<%=request.getContextPath() + "/content/Program/P112.jsp"%>">
                            <input disabled="disabled"  type="button" value="P113 อัพเดทข้อมุลจากระบบจัดเก็บ" onclick=window.location="<%=request.getContextPath() + "/content/Program/P113.jsp"%>">
                            <input   type="button" value="P114 แปลงข้อมูลของสายทาง 7,9" onclick=window.location="<%=request.getContextPath() + "/content/Program/P114.jsp"%>">
                            <input   type="button" value="P115 แปลงข้อมูลทางด่วนขั้นที่ 2 ศรีรัช" onclick=window.location="<%=request.getContextPath() + "/content/Program/P115.jsp"%>">
                            <input   type="button" value="P116 นำเข้าข้อมูลการให้บริการและเงินนำส่งธนาคาร(ETC) ศรีรัช-วงแหวน" onclick=window.location="<%=request.getContextPath() + "/content/Program/P116.jsp"%>">
                            <input disabled="disabled"  type="button" value="P117 ส่งออกข้อมูลรายได้ค่าผ่านทางเงินสด(แยก กทพ.และทล.)ไปยังระบบ SAP" onclick=window.location="<%=request.getContextPath() + "/content/Program/P117.jsp"%>">
                            <input   type="button" value="P119 โปรแกรมแปลง ETC(โปรโมชั่น)ข้อมูลด่าน 129-1 เป็น 129-2" onclick=window.location="<%=request.getContextPath() + "/content/Program/P119.jsp"%>">
                        </fieldset>
                    </td>
                    <td width="30%" valign="top">
                        <fieldset> 
                            <input  type="button" disabled="disabled" value="P107 อัพเดท VAT การขาย =0" onclick=window.location="<%=request.getContextPath() + "/content/Program/P107.jsp"%>">
                            <input   disabled="disabled" type="button" value="P108 ส่งออกข้อมูล DOC ของทางด่วนศรีรัช ส่วน D" onclick=window.location="<%=request.getContextPath() + "/content/Program/P108.jsp"%>">
                            <input disabled="disabled"   type="button" value="P108 ส่งออกข้อมูลปริมาณจราจรและรายได้" onclick=window.location="<%=request.getContextPath() + "/content/Program/P108.jsp"%>">
                            <input  type="button" disabled="disabled" value="P201 ลบ Interface" onclick=window.location="<%=request.getContextPath() + "/content/Program/P201.jsp"%>">
                        </fieldset>
                    </td>
                    <td width="30%" valign="top">
                        <fieldset> 
                            <!--<p align="center">สำหรับ แผนกตรวจสอบรายได้3</p>-->
                            <input type="button" value="R301 รายงานสรุปค่าผ่านทางประเภท EMV ตามยอดนำส่งธนาคาร สายทางกาญจนาภิเษก" onclick=window.location="<%=request.getContextPath() + "/content/Program/R301.jsp"%>">
                            <input type="button" value="R301A รายงานสรุปค่าผ่านทางประเภท EMV ตามยอดนำส่งธนาคาร" onclick=window.location="<%=request.getContextPath() + "/content/Program/R301A.jsp"%>">
                            <input type="button" value="R302 รายงานสรุปค่าผ่านทางประเภท EMV สายทางกาญจนาภิเษก" onclick=window.location="<%=request.getContextPath() + "/content/Program/R302.jsp"%>">
                            <input type="button" value="R302A รายงานสรุปค่าผ่านทางประเภท EMV" onclick=window.location="<%=request.getContextPath() + "/content/Program/R302A.jsp"%>">
                            <input type="button" value="RR204EXATB รายงานปริมาณจราจรและรายได้ค่าผ่านทางแยกตามด่าน แสดงเฉพาะรายได้ กทพ." onclick=window.location="<%=request.getContextPath() + "/content/Program/R204.jsp"%>">
                            
                        </fieldset>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
