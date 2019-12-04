<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
    /*
     * if ((String) session.getAttribute("ssion_userid") == null) {
     * response.sendRedirect(request.getContextPath()); }
     */
%>

<html>
    <head> 
        <title>ผู้ช่วยเหลือ | ระบบตรวจสอบรายได้ </title>
    </head>
    <body>
        <div style="font-size: 32px;font-weight: bold;margin: 20px 0 20px 0;">
            Helper System :: การทางพิเศษแห่งประเทศไทย
        </div>
        <div style="font-size: 13px;margin: 0 0 20px 0;font-family:tahoma;">
            <span style="background-color: #CDE2FF;padding: 3px"> V 3.0.2 Select a Menu</span>&nbsp;&nbsp;&rarr;&nbsp;&nbsp;
            <select onchange="window.location=this.value;" style="padding: 0px; font-size: 13px;">
                <option>Menu</option>
                <optgroup label="Process data" >
                    <option value="<%=request.getContextPath() + "/content/Program/P101.jsp"%>">P101 PORTABLE</option>
                    <option style="background-color: #FFFF99" value="<%=request.getContextPath() + "/content/Program/P102.jsp"%>">P102 นำเข้าข้อมูลด่วน 2 </option>
                    <option value="<%=request.getContextPath() + "/content/Program/P103.jsp"%>">P103 นำเข้าข้อมูลด่วน 3</option>
                    <option value="<%=request.getContextPath() + "/content/Program/P104.jsp"%>">P104 นำเข้าข้อมูลจากระบบแบ่งรายได้</option>
                    <option value="<%=request.getContextPath() + "/content/Program/P105.jsp"%>">P105 ลบข้อมูลนำเข้า (Interface)</option>
                    <option value="<%=request.getContextPath() + "/content/Program/P106.jsp"%>">P106 แปลงข้อมูลสายทางบางพลี - สุขสวัสดิ์</option>
                    <option style="background-color: #ffcccc "  value="<%=request.getContextPath() + "/content/Program/P107.jsp"%>">P107 ส่งออกข้อมูลจากระบบตรวจสอบรายได้</option>
                    <option    value="<%=request.getContextPath() + "/content/Program/P108.jsp"%>">P108 ส่งออกข้อมูล DOC ของทางด่วนศรีรัช ส่วน D</option>
                </optgroup>
                <optgroup label="Report" >
                    <option value="<%=request.getContextPath() + "/content/Report/R101.jsp"%>">R101 รายงานรายได้การให้บริการ</option>
                </optgroup>
            </select>&nbsp;&nbsp;
        </div>
    </body>
</html>