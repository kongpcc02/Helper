<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
<div id="download">
    <img src="../images/download.png" border="0" alt="">
    <span class="txtHead">ดาวน์โหลดคู่มือ</span><br><br>
    <li>
        <a href="<%=request.getContextPath()%>\manual\portable.pdf" target="_blank">ระบบ Portable</a>
    </li>
    <li>
        <a href="<%=request.getContextPath()%>\manual\rmt.pdf" target="_blank">ระบบรายได้การให้บริการในระบบ ETC</a>
    </li>
    <li>
        <a href="<%=request.getContextPath()%>\manual\using.pdf" target="_blank">ระบบการใช้บัตร Easy Pass ผ่านทาง (ระบบเปิด) </a>
    </li>
</div>
<%@include file="footer.jsp" %>
