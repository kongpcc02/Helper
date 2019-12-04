<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%

    if ((String) session.getAttribute("ssion_userid") == null) {
       // response.sendRedirect(request.getContextPath());
    }

%>

<html>
    <head> 
        <title>ผู้ช่วยเหลือ | ระบบตรวจสอบรายได้ </title>
    </head>
    <body>
        <h1 style="margin-bottom: 5px">Helper System : การทางพิเศษแห่งประเทศไทย</h1>
        <div style="margin-bottom: 10px"><b>ติดต่อ : กองระบบงานคอมพิวเตอร์ ฝ่ายสารสนเทศ โทร. 1303, 1308</b></div>
    </body>
</html>