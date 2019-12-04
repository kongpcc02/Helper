<%-- 
    Document   : test
    Created on : 18 ส.ค. 2554, 10:28:26
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="<%=request.getContextPath() + "/js/jquery-1.4.2.min.js"%>" type="text/javascript"></script>
        <link rel="stylesheet" href="<%=request.getContextPath() + "/css/style.css"%>">
        <script type="text/javascript">
            $(document).ready(function(){
                $(".trigger").click(function(){
                    $(".menu").toggle("fast");
                    $(this).toggleClass("active");
                    return false;
                });
            });
        </script>
    </head>
    <body style="margin: 0 ;border-top: 5px #F4951D solid" >
        <div class="menu">
            <h3>HPS Menu</h3>
            <ul>
                <li><a href="<%=request.getContextPath() + "/content/Program/P101.jsp"%>" title="Twitter">P101 Portable</a></li>
                <li><a href="<%=request.getContextPath() + "/content/Program/P102.jsp"%>" title="DesignBump">P102 นำเข้าข้อมูล ขั้น 2</a></li>
                <li><a href="<%=request.getContextPath() + "/content/Program/P103.jsp"%>" title="DesignBump">P103 นำเข้าข้อมูล ขั้น 1,3</a></li>
                <li><a href="<%=request.getContextPath() + "/content/Program/P104.jsp"%>" title="DesignBump">P102 นำเข้าข้อมูล Sec D</a></li><br>
                <li><a href="<%=request.getContextPath() + "/content/Report/R101.jsp"%>" title="DesignMoo">R101 รายงานรายได้การให้บริการ</a></li>
            </ul>
        </div>
        <a class="trigger" href="#"><img src="<%=request.getContextPath() + "/images/menu-bt.jpg"%>"> </a>
        <div id="wrapper">
            <div  align="center"><img src="<%=request.getContextPath() + "/images/header.jpg"%>" alt="exat" ></div>
            <div class="user">ผู้ใช้ 1520055252 : นายศิริเดช สุขคณะ</div>
            <div class="control">
                <table  cellpadding="5" cellspacing="0" width="100%" border="0">
                    <tr>
                        <td colspan="2">
                            <input type="button" value="ประมวลผล">
                            <input type="button" value="ยกเลิก">
                        </td>
                    </tr>
                    <tr>
                        <td width="15%" align="right">วันที่ : </td>
                        <td><input type="text"></td>
                    </tr>
                    <tr>
                        <td align="right">
                            ประเภทการให้บรอก่าร : </td>
                        <td>
                            <select >
                                <option>--ประเภทการให้บริการ-ssss-</option>
                            </select>
                        </td>
                    </tr>
                </table>
            </div>


