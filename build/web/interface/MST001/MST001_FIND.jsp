<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="bin.OCI_CONN" %>
<%@page import="bin.NUM_OF_LIST" %>
<%@page import="data.EVM_MST_CHART_OF_ACCOUNT" %>
<%@include file="../../config.jsp" %>
<%
//-----รับค่า POST-----//
String VAR_ACC_ID = request.getParameter("acc_id");
String VAR_ACC_NO = request.getParameter("txtAccNo");
String VAR_ACC_NAME = request.getParameter("txtAccName");
String VAR_PAGE = request.getParameter("PAGE");
//-----ติดต่อฐานข้อมูล-----//
OCI_CONN CONN = new OCI_CONN();
CONN.connect(CONFIG_HOST, CONFIG_SID, CONFIG_USERNAME, CONFIG_PASSWORD);
//-----คิวรี่ข้อมูล-----//
String SQL = " SELECT * " +
             " FROM EVM_MST_CHART_OF_ACCOUNT  " +
             " WHERE ACC_NO LIKE '%" + VAR_ACC_NO + "%'";
        if(VAR_ACC_ID != ""){
            SQL = SQL + "AND ACC_ID ='" + VAR_ACC_ID + "'";
        }
        SQL = SQL + " AND ACC_NAME LIKE '%" + VAR_ACC_NAME + "%'";
        SQL = SQL + " ORDER BY ACC_NO , ACC_NAME ";

//-----Validate ตัวแแปล-----//
if(VAR_PAGE == null){
    VAR_PAGE = "0";
}//end if


//-----แสดงการแบ่งหน้า-----//
out.println("<fieldset style=\"background-color:#F2F2F2\">");
CONN.padding(SQL ,CONN.count(SQL), Integer.parseInt(VAR_PAGE), CONFIG_PADDING);
//out.println(CONN.getSQL());
out.println(CONN.footer("../images" , "MST001_FIND.jsp" , ""));
//-----หัวของตาราง-----//
out.println("<table border=\"0\"  cellpadding=\"4\" cellspacing=\"1\">");
out.println("<tr >");
out.println("<td  width=\"50px\" align=\"center\" bgcolor=\"#A3C1E4\" class=\"ms_meduim_bold\" > " +
            "<input type=\"checkbox\" name=\"all\" id=\"all\" value=\"\" onclick='doChkAll();' /></td>");
out.println("<td  width=\"50px\" align=\"center\" bgcolor=\"#A3C1E4\" class=\"ms_meduim_bold\">ลำดับ</td>");
out.println("<td  width=\"80px\" align=\"center\" bgcolor=\"#A3C1E4\" class=\"ms_meduim_bold\">รหัสบัญชี</td>");
out.println("<td  width=\"300px\" align=\"center\" bgcolor=\"#A3C1E4\" class=\"ms_meduim_bold\">ชื่อบัญชี</td>");
out.println("<td  width=\"100px\" align=\"center\" bgcolor=\"#A3C1E4\" class=\"ms_meduim_bold\">สถานะ</td>");
out.println("</tr >");
//-----ID ของ TABLE-----//
int TABLE_ID = 1;
String COLOR1 = "#E5F2F8";
String COLOR2 = "#FFFFFF";
String BGCOLOR="";
//-----NUM_OF_LIST-----//
NUM_OF_LIST LIST = new NUM_OF_LIST();
int NUM_OF_LIST = LIST.list(Integer.parseInt(VAR_PAGE), 10);
//----INSTANCE CLASS EVM_MST_CHART_OF_ACCOUNT-----//
EVM_MST_CHART_OF_ACCOUNT CLS_ACCOUNT = new EVM_MST_CHART_OF_ACCOUNT();

while(CONN.resultNext()) {
    //-----สลับสีตาราง-----//
    BGCOLOR = (BGCOLOR==COLOR2)? COLOR1:COLOR2;
    
    out.println("<tr id=\"" + TABLE_ID + "\" bgcolor=\"" + BGCOLOR + "\" " +
                "onmousemove='over(\"" + TABLE_ID + "\")'" +
                "onmouseout='out(\"" + TABLE_ID + "\",\"" + BGCOLOR + "\")'  >");
    out.println("<td align=\"center\" class=\"ms_meduim\" >" +
                "<input type=\"checkbox\" name=\"id[]\" value=\"" + CONN.result().getInt("ACC_NO") + "\" /></td>");
    out.println("<td align=\"center\" class=\"ms_meduim\" >" + NUM_OF_LIST + "</td>");
    out.println("<td align=\"center\" class=\"ms_meduim\" >" + CONN.result().getInt("ACC_NO") + "</td>");
    out.println("<td align=\"left\" class=\"ms_meduim\" >" + CONN.result().getString("ACC_NAME") + "</td>");
    out.println("<td align=\"center\" class=\"ms_meduim\" >" + CLS_ACCOUNT.flag_status(CONN.result().getString("ACC_FLAG")) + "</td>");
    out.println("</tr >");
    TABLE_ID ++;
    NUM_OF_LIST++;
}
out.println("</table >");
out.println("</fieldset>");
//-----ปิดการเชื่อมต่อ-----//
CONN.close();
%>